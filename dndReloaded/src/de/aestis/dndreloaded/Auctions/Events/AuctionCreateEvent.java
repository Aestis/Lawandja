package de.aestis.dndreloaded.Auctions.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class AuctionCreateEvent extends Event {

	private static final HandlerList handlerList = new HandlerList();
	
	private Player player;
	private Event parentEvent;
	private boolean isCancelled, cancellable;
	
	public AuctionCreateEvent(Player player, Event parentEvent) {
		
		this.player = player;
		this.parentEvent = parentEvent;
		this.cancellable = true;
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
		
		return this.cancellable;
	}
	
	public boolean isChancelled() {
		
		return this.isCancelled;
	}
	
	public void setCancelled(boolean isCancelled) {
		
		this.isCancelled = isCancelled;
	}
	
	public Event getParentEvent()
    {
        return this.parentEvent;
    }
}
