package de.aestis.dndreloaded.CommandManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Helpers.BookHelpers;
import de.aestis.dndreloaded.Messages.InfoHandler;
import de.aestis.dndreloaded.Players.PlayerData;

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
				
				PlayerData pd = Main.instance.Players.get(player);
				List<String> contents = BookHelpers.getQuestTitles(pd.getQuestsCompleted());
				
				player.getInventory().addItem(BookHelpers.createNewBook("Questlog von" + player.getName(), "Jenominers.de", contents));
				
				return true;
			}	
			
			if (argArr[0].equalsIgnoreCase("questinfo") && argArr.length > 1) {
				
				InfoHandler MsgInfo = new InfoHandler();
				
				for (String s : MsgInfo.getQuestInfo(Plugin.QuestData.getQuestByID(Integer.valueOf(argArr[1]))))
				{
					sender.sendMessage(s);
				}
				
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
