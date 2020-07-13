package de.aestis.dndreloaded.CommandManager.Quests;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Main;

public class QuestEditor implements CommandExecutor {

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
		if (cmd.getName().equalsIgnoreCase("questedit") && args[0].equalsIgnoreCase("reload"))
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
		
		
		return true;
	}
	
}
