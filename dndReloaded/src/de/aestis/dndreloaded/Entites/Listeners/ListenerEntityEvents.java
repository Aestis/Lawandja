package de.aestis.dndreloaded.Entites.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Entites.EntityData;
import de.aestis.dndreloaded.Helpers.External.HolographicDisplaysHelper;

public class ListenerEntityEvents implements Listener {

	private final Main Plugin = Main.instance;
	
	@EventHandler
	public void entitySpawnEvent(EntitySpawnEvent event) {
		
		//TODO
		//Check if Player is nearby
	}
	
	@EventHandler
	public void entityDeathEvent(EntityDeathEvent event) {
		
		//TODO
		//Check if Entity was in someones Storage
	}
	
	@EventHandler
	public void entityDamageEvent(EntityDamageEvent event) {
		
		//TODO
		//Same as Death
	}
	
}
