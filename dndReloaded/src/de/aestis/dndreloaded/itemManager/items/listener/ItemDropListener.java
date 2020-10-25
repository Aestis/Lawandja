package de.aestis.dndreloaded.itemManager.items.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.items.ItemFlag;
import de.aestis.dndreloaded.itemManager.items.ItemGroup;
import de.aestis.dndreloaded.itemManager.items.ItemID;

public class ItemDropListener implements Listener{

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent evt) {
		ItemStack item = evt.getItemDrop().getItemStack();
		if (ItemFlag.get(item, ItemFlag.ITEM_ID, new ItemID(ItemGroup.UNKNOWN, Material.AIR, "-")).getGroup() == ItemGroup.QUEST) {
			evt.setCancelled(true);
			evt.getPlayer().sendMessage(ChatColor.DARK_RED + "Du kannst dieses Item nicht Droppen.");
		}
	}
}
