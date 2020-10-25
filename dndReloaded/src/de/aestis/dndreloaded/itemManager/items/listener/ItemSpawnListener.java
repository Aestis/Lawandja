package de.aestis.dndreloaded.itemManager.items.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemFlag;
import de.aestis.dndreloaded.itemManager.items.ItemGroup;
import de.aestis.dndreloaded.itemManager.items.ItemID;

public class ItemSpawnListener implements Listener {

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent evt) {
		ItemStack item = evt.getEntity().getItemStack();
		if (!ItemFlag.isSet(item, ItemFlag.ITEM_ID)) {
			CustomItem.loadCustomItem(item, true);
		}
	}
	
	@EventHandler
	public void craftEvent(PrepareItemCraftEvent evt) {
	    ItemStack[] contents = evt.getInventory().getContents();
	    ItemStack item = contents[0];
	    
	    if (contents.length == 0 || item == null || item.getType() == Material.AIR) {
	    	return;
	    }
	    
	    CustomItem.loadCustomItem(item, new ItemID(ItemGroup.CRAFTED, item.getType(), "-"), true);
	}
}
