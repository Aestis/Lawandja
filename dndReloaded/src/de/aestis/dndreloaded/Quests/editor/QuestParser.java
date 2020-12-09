package de.aestis.dndreloaded.Quests.editor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import de.aestis.dndreloaded.Quests.Quest;

public class QuestParser {

	private QuestParser() {
	}
	
	public static String compressAndReturnB64(String text) throws IOException {
		return new String(Base64.getEncoder().encode(compress(removeUmlaut(text))));    
	}

	public static String decompressB64(String b64Compressed) throws IOException {
		byte[] decompressedBArray = decompress(Base64.getDecoder().decode(b64Compressed));       
		return addUmlaut(new String(decompressedBArray, StandardCharsets.UTF_8));    
	}

	public static byte[] compress(String text) throws IOException {
		return compress(text.getBytes());    
	}

	public static byte[] compress(byte[] bArray) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();        
		try (DeflaterOutputStream dos = new DeflaterOutputStream(os)) {
			dos.write(bArray);        
		}
		return os.toByteArray();    
	}

	public static byte[] decompress(byte[] compressedTxt) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try (OutputStream ios = new InflaterOutputStream(os)) {
			ios.write(compressedTxt);
		}

		return os.toByteArray();
	}

	public static String readFile(Path path) throws IOException {
		byte[] encoded = Files.readAllBytes(path);
		return decompressB64(new String(encoded, StandardCharsets.UTF_8));
	}

	public static void writeFile(Path path, String s) throws IOException {
		Files.createFile(path);
		Files.write(path, compressAndReturnB64(s).getBytes());
	}

	private static Path getQuestFolder() {
		String dir = null;
		try {
			dir = new File(QuestParser.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
		} catch (URISyntaxException e2) {
			e2.printStackTrace();
			return null;
		}

		Path path = Paths.get(dir + File.separator + "Quests");
		path.toFile().mkdir();
		
		return path;
	}

	public static List<Quest> loadQuests(){
		List<Quest> quests = new ArrayList<Quest>();
		Path path = getQuestFolder();
		try (Stream<Path> paths = Files.walk(path)) {
		    paths.filter(Files::isRegularFile).forEach((p) -> {quests.add(QuestParser.loadQuest(p));});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return quests;
	}
	
	private static Quest loadQuest(Path p){
		try {
			return Quest.fromString(readFile(p));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void storeQuest(Quest q){
		Path p = getQuestFolder();
		try {
			p = Paths.get(p.toString() + File.separator + q.getTitle().replaceAll(" ", "") + q.getID() + ".quest");
			writeFile(p, q.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearQuestFolder() {
		Path p = getQuestFolder();
		try (Stream<Path> paths = Files.walk(p)) {
			paths.filter(Files::isRegularFile).forEach((path) -> {
				try {
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String removeUmlaut(String s) {
		return s.replaceAll("ü", "\\\\ue").replaceAll("ä", "\\\\ae").replaceAll("ö", "\\\\oe").replaceAll("Ü", "\\\\Ue").replaceAll("Ä", "\\\\Ae").replaceAll("Ö", "\\\\Oe").replaceAll("ß", "\\\\ss");
	}
	
	private static String addUmlaut(String s) {
		return s.replaceAll("\\\\ue", "ü").replaceAll("\\\\ae", "ä").replaceAll("\\\\oe", "ö").replaceAll("\\\\Ue", "Ü").replaceAll("\\\\Ae", "Ä").replaceAll("\\\\Oe", "Ö").replaceAll("\\\\ss", "ß");
	}

}
