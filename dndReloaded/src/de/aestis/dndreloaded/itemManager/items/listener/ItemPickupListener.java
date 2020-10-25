package de.aestis.dndreloaded.itemManager.items.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.EconomyManager;
import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemManager;

public class ItemPickupListener implements Listener {

	@EventHandler
	public void onItemPickup(EntityPickupItemEvent evt) {
		if (!(evt.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) evt.getEntity();
		ItemStack item = evt.getItem().getItemStack();
		
		EconomyManager.addMoney(p, ItemManager.parseMoney(CustomItem.loadCustomItem(item, false)));
	}
}
