package de.aestis.dndreloaded.Players.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDataUnloadedEvent extends Event {

	private static final HandlerList handlerList = new HandlerList();
	
	private Player player;
	private boolean isCancellable;
	
	public PlayerDataUnloadedEvent(Player player) {
		
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
	
	public Player getPlayer() {
		
		return this.player;
	}
	
	public boolean isCancellable() {
		
		return this.isCancellable;
	}
	
}