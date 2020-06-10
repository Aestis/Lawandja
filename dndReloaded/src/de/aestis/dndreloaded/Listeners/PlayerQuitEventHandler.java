package de.aestis.dndreloaded.Listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.aestis.dndreloaded.DataSync;
import de.aestis.dndreloaded.Main;

public class PlayerQuitEventHandler implements Listener {

	private final Main Plugin = Main.instance;	
	private final FileConfiguration Config = Plugin.getConfig();
	
	@EventHandler
	public void playerDisconnectEvent(PlayerQuitEvent event) {
		
		/*
		 * Synchronize first and then
		 * remove PlayerData from local storage
		 */
		
		DataSync Synchronizer = Plugin.getDataSync();
		Synchronizer.savePlayerData(event.getPlayer());
		Plugin.Players.remove(event.getPlayer());
		
		Plugin.getLogger().info("Unloaded PlayerData of Player '" + event.getPlayer().getName() + "'.");
	}
}
