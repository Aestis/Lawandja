package de.aestis.dndreloaded.Players.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.aestis.dndreloaded.Players.PlayerData;

public class PlayerDataLoadedEvent extends Event {

	private static final HandlerList handlerList = new HandlerList();
	
	private PlayerData data;
	private Player player;
	private boolean isCancellable;
	
	public PlayerDataLoadedEvent(PlayerData data, Player player) {
		
		this.data = data;
		this.player = player;
		this.isCancellable = false;
	}
	
	@Override
    public HandlerList getHandlers() {
		
        return handlerList;
    }
	
	public static HandlerList getHandlerList()
    {
        return handlerList;
    }
	
	public PlayerData getPlayerData() {
		
		return this.data;
	}
	
	public Player getPlayer() {
		
		return this.player;
	}
	
	public boolean isCancellable() {
		
		return this.isCancellable;
	}
	
}
