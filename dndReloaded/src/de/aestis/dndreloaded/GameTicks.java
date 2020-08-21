package de.aestis.dndreloaded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import de.aestis.dndreloaded.Entites.EntityData;
import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Helpers.External.HolographicDisplaysHelper;
import de.aestis.dndreloaded.Helpers.ScoreboardUtil.CustomScoreboards;
import de.aestis.dndreloaded.Players.PlayerData;

public class GameTicks {
	
	private static GameTicks instance;
		
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public int generalTaskID = -1;
	public BukkitTask syncTaskID = null;
	public BukkitTask scoreUpdateTaskID = null;
	public BukkitTask entityRangeTaskID = null;
	public BukkitTask holoUpdateTaskID = null;
	
	public static GameTicks getInstance() {
		if (instance == null) {
			instance = new GameTicks();
		}
		return instance;
	}
	
	public void startSyncTask() {
			
		Plugin.getLogger().info("GameTicks Info: Starting 'syncTaskTimer'!");
		
		syncTaskID = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, new Runnable() {

			@Override
			public void run() {
				
				//Bukkit.broadcastMessage("§cSynchronizing Local Data With Database...");
				DataSync Synchronizer = Plugin.getDataSync();
				
				for (Player p : Bukkit.getServer().getOnlinePlayers())
				{					
					Synchronizer.savePlayerData(p);
				}
				
				System.out.println(Plugin.Players);
			}
			
		}, 500, 500);
	}
	
	public void startRefreshScoreboardsTask() {
		
		Plugin.getLogger().info("GameTicks Info: Starting 'scoreUpdateTaskTimer'!");
		
		scoreUpdateTaskID = Bukkit.getScheduler().runTaskTimer(Main.instance, new Runnable() {

			@Override
			public void run() {
				
				Bukkit.broadcastMessage("§cRefreshing Scoreboards for " + Bukkit.getServer().getOnlinePlayers().size() + " Players()...");
				
				for (Player p : Bukkit.getServer().getOnlinePlayers())
				{
					ScoreboardHelpers ScoreboardHelper = Plugin.getScoreboardHelper();
					Scoreboard scb = CustomScoreboards.getInstance().getMainPlayerScoreboard(p);
					ScoreboardHelper.setScoreboard(p, scb);
				}
			}
			
		}, 100, 20);
	}
	
	public void startEntityRangeTask() {
		
		entityRangeTaskID = Bukkit.getScheduler().runTaskTimer(Main.instance, new Runnable() {

			@Override
			public void run() {

				try
				{
					for (Player p : Bukkit.getOnlinePlayers())
					{
						List<Entity> ent = p.getNearbyEntities(32, 8, 32);
						
						//Bukkit.broadcastMessage("Holo size: " + Main.instance.HoloStorage.size());
						
						if (ent.size() > 0)
						{
							for (Entity e : ent)
							{
								/*
								 * Check if Entity is already
								 * tracked and create new entry
								 * if not
								 */
								if (!Main.instance.TrackedEntities.containsKey(e.getUniqueId()))
								{
									EntityData ed = new EntityData(e.getUniqueId());
									HashMap<World, EntityData> map = new HashMap<World, EntityData>();
									
									map.put(e.getWorld(), ed);
									Main.instance.TrackedEntities.put(e.getUniqueId(), map);
									
									if (e.isValid() && e != null)
									{
										HolographicDisplaysHelper.createHolo(Main.instance, e);
										Main.instance.getLogger().info(" > Created Holo for Entity: " + e.getUniqueId());
									}
								} else
								{
									Main.instance.HoloStorage.get(e.getUniqueId()).getVisibilityManager().showTo(p);
								}
							}							
						}
						
						for (Entry<UUID, Hologram> entry : Main.instance.HoloStorage.entrySet())
						{
							if (!ent.contains(Bukkit.getEntity(entry.getKey())))
							{
								entry.getValue().getVisibilityManager().hideTo(p);
							}
						}
					}
				} catch (Exception ex)
				{
					ex.printStackTrace();
				}
				
			}
			
		}, 10, 50);
	}
	
	public void startHoloUpdateTask() {
		
		holoUpdateTaskID = Bukkit.getScheduler().runTaskTimer(Main.instance, new Runnable() {

			@Override
			public void run() {

				try
				{
					for (Player p : Bukkit.getOnlinePlayers())
					{
						for (Entity ent : p.getNearbyEntities(32, 8, 32))
						{
							UUID uid = ent.getUniqueId();
							
							if (Main.instance.HoloStorage.containsKey(uid))
							{
								Hologram holo = Main.instance.HoloStorage.get(uid);
								HolographicDisplaysHelper.moveHolo(ent, holo);
							}
						}
					}					
				} catch (Exception ex)
				{
					ex.printStackTrace();
				}
				
			}
			
		}, 40, 3);
	}
	
	public BukkitTask getSyncTask() {
		
		if (syncTaskID != null) return syncTaskID;
		return null;
	}
	
	public BukkitTask getScoreboardTask() {
		
		if (scoreUpdateTaskID != null) return scoreUpdateTaskID;
		return null;
	}
	
	public BukkitTask getEntityRangeTask() {
		
		if (entityRangeTaskID != null) return entityRangeTaskID;
		return null;
	}
	
	public BukkitTask getHoloUpdateTask() {
		
		if (holoUpdateTaskID != null) return holoUpdateTaskID;
		return null;
	}
	
}
