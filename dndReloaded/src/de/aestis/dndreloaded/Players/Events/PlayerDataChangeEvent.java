package de.aestis.dndreloaded.Players.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import de.aestis.dndreloaded.Players.PlayerData;

public abstract class PlayerDataChangeEvent extends PlayerEvent {

	private static final HandlerList handlerList = new HandlerList();
	
	private boolean cancelled, cancellable;
	
	private PlayerData data;
	private PlayerEvent parent;
	
	public PlayerDataChangeEvent(Player player, PlayerData pd, PlayerEvent event) {
		
		super(player);
		
		this.data = pd;
		this.parent = event;
		
		cancelled = false;
		cancellable = true;
		
		if (player.isBanned() ||
			player.isEmpty() ||
			!player.isOnline() ||
			pd == null)
		{
			cancelled = true;
		}
	}

	
}
