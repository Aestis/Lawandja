package de.aestis.dndreloaded;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class GameTicks {

	private final Main Plugin = Main.instance;
	private static GameTicks instance;
	
	public int generalTaskID = -1;
	public BukkitTask syncTaskID = null;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
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
				
				System.out.println(Plugin.Players);
			}
			
		}, 500, 500);
	}
	
	public BukkitTask getSyncTask() {
		
		if (syncTaskID != null) return syncTaskID;
		return null;
	}
	
}
