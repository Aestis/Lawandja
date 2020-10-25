package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.items.creator.SelectColorMenu.ColorWrapper;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Modifyable;
import oxolotel.inventoryMenuManager.menus.SlotCondition;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class CopyLoreMenu extends CustomMenu implements Closeable, Submenu, Modifyable, SlotCondition {

	ArrayList<String> lore;
	ColorWrapper color;
	
	public CopyLoreMenu(ColorWrapper color, ArrayList<String> lore) {
		super(9);
		this.lore = lore;
		this.color = color;
		setTitle("Copy Lore");
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		for (int i = 0;i<9;i++) {
			if (i == 4 || i == 8) {
				continue;
			}
			returnMap.put(i, new DoubleWrapper<ItemStack, Runnable>(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), null));
		}
		ItemStack item = new ItemStack(Material.RED_CONCRETE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Save and return");
		item.setItemMeta(meta);
		returnMap.put(8, new DoubleWrapper<>(item, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		
		return returnMap;
	}

	@Override
	public boolean isClickAllowed(Player arg0, int slot) {
		if (slot == 4 || slot == 8) {
			return true;
		}
		return false;
	}

	@Override
	public void onClose(Player arg0, ItemStack[] items, CloseReason reason) {
		if (reason == CloseReason.RETRUNPREVIOUS) {
			if (items[4] != null && items[4].getType() != Material.AIR && items[4].getAmount() > 0 && items[4].getItemMeta() != null && items[4].getItemMeta().getLore() != null) {
				lore.clear();
				lore.addAll(items[4].getItemMeta().getLore());
				if (color != null) {
					for (int i = 0;i< lore.size();i++) {
						lore.set(i, color.color + ChatColor.stripColor(lore.get(i)));
					}
				}
			}
		}
	}

	
}
