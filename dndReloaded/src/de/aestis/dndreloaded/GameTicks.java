package de.aestis.dndreloaded;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Helpers.ScoreboardUtil.CustomScoreboards;

public class GameTicks {
	
	private static GameTicks instance;
		
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public int generalTaskID = -1;
	public BukkitTask syncTaskID = null;
	public BukkitTask refreshScoreboards = null;
	
	public static GameTicks getInstance() {
		if (instance == null) {
			instance = new GameTicks();
		}
		return instance;
	}
	
	public void startSyncTask() {
			
		syncTaskID = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, new Runnable() {

			@Override
			public void run() {
				
				Bukkit.broadcastMessage("§cSynchronizing Local Data With Database...");
				DataSync Synchronizer = Plugin.getDataSync();
				
				for (Player p : Bukkit.getServer().getOnlinePlayers())
				{					
					Synchronizer.savePlayerData(p);
				}
				
				System.out.println(Plugin.Players);
			}
			
		}, 1000, 500);
	}
	
	public void startRefreshScoreboardsTask() {
		
		syncTaskID = Bukkit.getScheduler().runTaskTimer(Main.instance, new Runnable() {

			@Override
			public void run() {
				
				Bukkit.broadcastMessage("§cRefreshing Scoreboards for " + Bukkit.getServer().getOnlinePlayers().size() + " Players()...");
				
				for (Player p : Bukkit.getServer().getOnlinePlayers())
				{
					ScoreboardHelpers ScoreboardHelper = Plugin.getScoreboardHelper();
					Scoreboard scb = CustomScoreboards.getInstance().getMainPlayerScoreboard(p);
					ScoreboardHelper.setScoreboard(p, scb);
				}
				
				System.out.println(Plugin.Players);
			}
			
		}, 1000, 20);
	}
	
	public BukkitTask getSyncTask() {
		
		if (syncTaskID != null) return syncTaskID;
		return null;
	}
	
}
