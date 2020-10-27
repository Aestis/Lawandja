package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.items.ItemSet;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Pageable;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.utils.DoubleWrapper;

public class SetListMenu extends CustomMenu implements Closeable, Pageable, Subdevideable, Refreshable{

	private List<ItemSet> items;
	
	public SetListMenu() {
		super(54);
		items = ItemSet.getRegisteredSets();
		setTitle("Set List");
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason arg2) {}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		
		for (int i = 0; i<items.size(); i++) {
			returnMap.put(i, createGuiItem(null, Material.DIAMOND, items.get(i).getName()));
		}
		
		return returnMap;
	}

	@Override
	public CustomMenu getSubmenu(int slot) {
		return new EditSetMenu(items.get(slot));
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot > -1 || slot < items.size()) {
			return true;
		}
		return false;
	}

	@Override
	public double getRefreshTime() {
		return 1.5;
	}

	@Override
	public void onRefresh() {}

}
