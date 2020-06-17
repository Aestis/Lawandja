package de.aestis.dndreloaded.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.aestis.dndreloaded.Helpers.External.WorldGuard.RegionEnterEvent;

public class PlayerRegionEnterEvent implements Listener {

	@EventHandler
	public void regionEnterEvent(RegionEnterEvent event) {
		
		Bukkit.broadcastMessage(event.getPlayer().getName() + " entered Region " + event.getRegion().getId());
		
	}
	
}
