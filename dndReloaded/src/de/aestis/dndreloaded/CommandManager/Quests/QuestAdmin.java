package de.aestis.dndreloaded.CommandManager.Quests;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Overrides.Packets.TitleBar;
import de.aestis.dndreloaded.Quests.Quest;

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
		 * Command /questadmin reload
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
		 * Command /questadmin save
		 * is used to save current
		 * QuestData to Database
		 */
		if (cmd.getName().equalsIgnoreCase("questadmin") && args[0].equalsIgnoreCase("save"))
		{
			Integer size = Plugin.QuestData.size();
			Plugin.getLogger().info(player.getName() + " is saving QuestData (" + size + ").");
			sender.sendMessage("§cFetching currently available Quests...");
			
			DatabaseHandler dbh = Plugin.getDatabaseHandler();
			
			for (Quest q : Plugin.QuestData.getAllQuests())
			{
				sender.sendMessage("§e > Saving Quest '" + q.getTitle() + "'...");
				dbh.saveQuestData(q);
			}
		}
		
		/**
		 * Command /questadmin info
		 * is used to display all
		 * current Data of interest
		 */
		if (cmd.getName().equalsIgnoreCase("questadmin") && args[0].equalsIgnoreCase("info"))
		{		
			BukkitTask sync = Plugin.getGameTicks().getSyncTask();
			BukkitTask score = Plugin.getGameTicks().getScoreboardTask();
			BukkitTask range = Plugin.getGameTicks().getEntityRangeTask();
			BukkitTask update = Plugin.getGameTicks().getHoloUpdateTask();
			
			sender.sendMessage("Showing §5LW-Core v" + Plugin.Version + " §fInfo:");
			sender.sendMessage("§6Loaded Quests: §f" + Plugin.QuestData.size());
			
			if (sync != null)
			{
				sender.sendMessage("§6Sync Task: §fRUNNING §7@" + sync.getTaskId() + " (" + sync.getClass().getName() + ")");
			} else
			{
				sender.sendMessage("§6Sync Task: §cHALTED");
			}
			
			if (score != null)
			{
				sender.sendMessage("§6Score Task: §fRUNNING §7@" + score.getTaskId() + " (" + score.getClass().getName() + ")");
			} else
			{
				sender.sendMessage("§6Score Task: §cHALTED");
			}
			
			if (range != null)
			{
				sender.sendMessage("§6Entity-Range Task: §fRUNNING §7@" + range.getTaskId() + " (" + range.getClass().getName() + ")");
			} else
			{
				sender.sendMessage("§6Entity-Range Task: §cHALTED");
			}
			
			if (update != null)
			{
				sender.sendMessage("§6Holo-Update Task: §fRUNNING §7@" + update.getTaskId() + " (" + update.getClass().getName() + ")");
			} else
			{
				sender.sendMessage("§6Holo-Update Task: §cHALTED");
			}
		}
		
		
		
		if (cmd.getName().equalsIgnoreCase("questadmin") && args[0].equalsIgnoreCase("admin"))
		{

			//TitleBar.sendNameTags(player, "", " der Raecher der Untoten und der Lebenden", 100);
			TitleBar.test(player);
		}
		
		if (cmd.getName().equalsIgnoreCase("questadmin") && args[0].equalsIgnoreCase("check"))
		{

			UUID uid = UUID.fromString(args[1]);
			
			Entity ent = Bukkit.getEntity(uid);
			
			if (ent == null)
			{
				sender.sendMessage("Entity is §cEMPTY!");
			}
			
			if (ent.isValid())
			{
				sender.sendMessage("Entity is §2VALID");
			} else
			{
				sender.sendMessage("Entity is §cINVALID");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("questadmin") && args[0].equalsIgnoreCase("holo"))
		{

			UUID uid = UUID.fromString(Main.instance.SelectedNPC.get(player));
			
			if (!Main.instance.HoloStorage.containsKey(uid))
			{
				Bukkit.broadcastMessage("Holo is not existing!");
				
				return true;
			}
			
			Boolean visible = Main.instance.HoloStorage.get(uid).getVisibilityManager().isVisibleTo(player);
			
			
			if (visible)
			{
				Bukkit.broadcastMessage("Attempting to hide Holo for " + uid + "...");
				Main.instance.HoloStorage.get(uid).getVisibilityManager().hideTo(player);
			} else
			{
				Bukkit.broadcastMessage("Attempting to show Holo for " + uid + "...");
				Main.instance.HoloStorage.get(uid).getVisibilityManager().showTo(player);
			}
		}
		
		
		return true;
	}
	
}
