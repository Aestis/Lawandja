package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Modifyable;
import oxolotel.inventoryMenuManager.menus.SlotCondition;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class ChangeMaterialMenu extends CustomMenu implements Closeable, Modifyable, SlotCondition, Submenu{

	CustomItem item;
	
	public ChangeMaterialMenu(CustomItem item) {
		super(9);
		this.item = item;
		setTitle("Change Material");
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		for (int i = 0;i<9;i++) {
			if (i == 4) {
				continue;
			}
			returnMap.put(i, new DoubleWrapper<ItemStack, Runnable>(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), null));
		}
		ItemStack item = new ItemStack(Material.RED_CONCRETE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Speichern und zurück");
		item.setItemMeta(meta);
		
		returnMap.put(8, new DoubleWrapper<>(item, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		return returnMap;
	}

	@Override
	public void onClose(Player p, ItemStack[] items, CloseReason reason) {
		if (reason == CloseReason.RETRUNPREVIOUS) {
			if (items[4] != null && items[4].getType() != Material.AIR && items[4].getAmount() > 0) {
				item.setMaterial(items[4].getType());
			}
		}
	}

	@Override
	public boolean isClickAllowed(Player p, int slot) {
		if (slot == 4 || slot == 8) {
			return true;
		}
		return false;
	}

}
