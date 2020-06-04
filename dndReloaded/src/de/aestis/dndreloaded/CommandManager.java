package de.aestis.dndreloaded;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Database.DatabaseHandler;

public class CommandManager implements CommandExecutor {

	private final Main Plugin = Main.instance;
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] argArr) {

		Player player = Bukkit.getPlayer(sender.getName());
		
		if (cmd.getName().equalsIgnoreCase("dnd")) {
			
			if (argArr[0].equalsIgnoreCase("help")) {
				
				player.openWorkbench(player.getLocation(), true);
				
				sender.sendMessage("Hello World :)");
				return true;
			}
			
			
			if (argArr[0].equalsIgnoreCase("book")) {
				
				sender.sendMessage("Erstelle Questlog...");
				
				List<String> text = new ArrayList<String>();
				text.add("�d[1. Abmeddeln]");
				text.add("Der Drachenlord ist sehr dankbar f�r deine Dienste! Gut gemacht! Und jetzt ab zum n�chsten Kaschber, sonder schma�t dir der Ongl Alfred am Mondach noch die Anzeigne naus!");
				
				player.getInventory().addItem(Plugin.getBookHelper().createNewBook("Questlog von" + player.getName(), "Jenominers.de", text));
				
				return true;
			}
			
			
			if (argArr[0].equalsIgnoreCase("createquest")) {
				
				if (sender.hasPermission("dnd.quests.create")) {
					
					if (argArr[1] != null) {
						
						if (Main.instance.SelectedNPC.get(player) != null) {
							
							System.out.println("UUID: " + Main.instance.SelectedNPC.get(player));
							
							DatabaseHandler Database = DatabaseHandler.getInstance();
							Database.registerQuest(Main.instance.SelectedNPC.get(player), argArr[1], player.getName());			
						} else {
							/*
							 * ERROR_HANDLER: No NPC selected!
							 * */
						}						
					} else {
						/*
						 * ERROR_HANDLER: Title too short!
						 * */
					}
				} else {
					/*
					 * ERROR_HANDLER: No permission!
					 * */
				}
				return true;
			}
			
			return true;
		}
		return false;
	}
}
