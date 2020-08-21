package de.aestis.dndreloaded.Helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Quests.Quest;

public class BookHelpers {
	
	public static List<String> getQuestTitles (String quests) {
		
		List<String> bundle = new ArrayList<String>();
		String[] spliterator = quests.split(",");
		List<String> qs = Arrays.asList(spliterator);
		Collections.reverse(qs);
		
		for (String quest : qs)
		{
			Quest data = Main.instance.QuestData.getQuestByID(Integer.valueOf(quest));
			bundle.add("§d§l" + data.getTitle() + "\n\n§r" + data.getShort() + "\n\n§a" + data.getCompletionText());
		}
		
		return bundle;
	}
	
	public static ItemStack createNewBook (String title, String author, List<String> content) {
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		
		meta.setTitle(title);
		meta.setAuthor(author);
		for (String s : content) {
			meta.addPage(s);
		}
		
		book.setItemMeta(meta);		
		return book;		
	}
	
}
