package de.aestis.dndreloaded.itemManager.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import de.aestis.dndreloaded.itemManager.items.SimpleItem;

public class PlayerConsumeItemEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	private SimpleItem item;
	
	public PlayerConsumeItemEvent(Player who, SimpleItem item) {
		super(who);
		this.item = item;
	}

	/**
	 * @return the item that will be consumed
	 */
	public SimpleItem getItem() {
		return item;
	}
	
	/**
	 * @param item the item that should be consumed
	 */
	public void setItem(SimpleItem item) {
		this.item = item;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	/**
	 * Gets a list of handlers handling this event.
	 *
	 * @return A list of handlers handling this event.
	 */
	public static HandlerList getHandlerList(){
		return handlers;
	}
}
