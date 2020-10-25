package de.aestis.dndreloaded.Players.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.aestis.dndreloaded.Players.PlayerData;

public class PlayerDataBeforeUnloadEvent extends Event {

	private static final HandlerList handlerList = new HandlerList();
	
	private Player player;
	private PlayerData data;
	private boolean isCancellable;
	
	public PlayerDataBeforeUnloadEvent(PlayerData data, Player player) {
		
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
	
	public PlayerData getPlayerData() {
		
		return this.data;
	}
	
	public boolean isCancellable() {
		
		return this.isCancellable;
	}
	
}