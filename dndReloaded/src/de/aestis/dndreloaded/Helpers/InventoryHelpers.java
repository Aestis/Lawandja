package de.aestis.dndreloaded.Helpers;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryHelpers {
	
	private static InventoryHelpers instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private HashMap<Player, Inventory> PlayerInventories = new HashMap<Player, Inventory>();
	
	public static InventoryHelpers getInstance() {
		
		if (instance == null) {
			instance = new InventoryHelpers();
		}
		return instance;
	}
	
	public boolean isInventoryEmpty(Player player) {
		
		for(ItemStack item : player.getInventory().getContents()) {
			if(item != null) return false;
		}
		return true;
	}
	
	
	public Inventory getInventory(Player player) {
		
		if (PlayerInventories.containsKey(player)) {
			return PlayerInventories.get(player);
		} else {
			return null;
		}
	}
	
	/*public Player getPlayer(Inventory inv) {
		
		for (Player e : PlayerInventories.keySet()) {
			
		}
	}*/
	
	public void pushInventory(Player player, Inventory inv) {
		
		if (PlayerInventories.containsKey(player)) {
			PlayerInventories.replace(player, inv);
		} else {
			PlayerInventories.put(player, inv);
		}
		
		System.out.println(PlayerInventories);
	}
}
