package de.aestis.dndreloaded.CommandManager.Quests;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Main;

public class QuestAdmin implements CommandExecutor {

	private final Main Plugin = Main.instance;
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		Player player = (Player) Bukkit.getPlayer(sender.getName());
		
		
		if (args.length == 0)
		{
			sender.sendMessage("§cMissing Arguments! Usage: /questadmin <reload/save>");
			
			return true;
		}
		
		/**
		 * Command /questreload reload
		 * is used to reload/rollback current
		 * QuestData from Database
		 */
		if (cmd.getName().equalsIgnoreCase("questadmin") && args[0].equalsIgnoreCase("reload"))
		{
			Plugin.getLogger().info(player.getName() + " is reloading QuestData.");
			sender.sendMessage("§cReloading QuestData...");
			
			/*
			 * Shutting down running Schedulers
			 * (if active) and re-initialize
			 * Quest Getters and Setters
			 */
			if (Plugin.getGameTicks().getSyncTask() != null)
			{
				Plugin.getGameTicks().getSyncTask().cancel();
				Plugin.getQuestHandler().initialize();
				Plugin.getGameTicks().startSyncTask();
			} else
			{
				Plugin.getLogger().severe("Error whilst reloading Quests: Plugin isn't initialized yet!");
				return true;
			}
			
			sender.sendMessage("§2Quests reloaded!");
			
			return true;
		}
		
		/**
		 * Command /questreload save
		 * is used to save current
		 * QuestData to Database
		 */
		if (cmd.getName().equalsIgnoreCase("questadmin") && args[0].equalsIgnoreCase("save"))
		{
			Integer size = Plugin.QuestData.size();
			Plugin.getLogger().info(player.getName() + " is saving QuestData (" + size + ").");
			sender.sendMessage("§cReloading QuestData...");
			
			//TODO
		}
		
		
		return true;
	}
	
}
