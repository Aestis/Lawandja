package de.aestis.dndreloaded.Helpers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryHelpers {
	
	public static boolean isInventoryEmpty(Player player) {
		
		for (ItemStack item : player.getInventory().getContents())
		{
			if (item != null) return false;
		}
		return true;
	}
	
	public static boolean hasInventorySpaceLeft(Player player) {

		for (ItemStack item : player.getInventory().getContents())
		{
			if (item != null)
			{
				continue;
			} else
			{
				return true;
			}
		}
		
		return true;
	}
	
	public static Integer inventorySlotsEmpty(Player player) {
		
		Integer cnt = 0;
		
		for (ItemStack item : player.getInventory().getContents())
		{
			if (item != null) cnt++;
		}
		
		return cnt;
	}

}
