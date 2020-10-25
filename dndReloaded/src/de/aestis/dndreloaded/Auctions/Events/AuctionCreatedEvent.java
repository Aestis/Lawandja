package de.aestis.dndreloaded.Auctions.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.aestis.dndreloaded.Auctions.Util.Auction;

public abstract class AuctionCreatedEvent extends Event {

	private static final HandlerList handlerList = new HandlerList();
	
	private Auction auction;
	private Event parentEvent;
	private boolean isCancellable;
	
	public AuctionCreatedEvent(Auction auction, Event parentEvent) {
		
		this.auction = auction;
		this.parentEvent = parentEvent;
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
	
	public Auction getAuction() {
		
		return this.auction;
	}
	
	public boolean isCancellable() {
		
		return this.isCancellable;
	}
	
	public Event getParentEvent()
    {
        return this.parentEvent;
    }
}
