package de.aestis.dndreloaded.itemManager.items.set.creator;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.items.set.ItemSet;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class SelectItemAmount extends CustomMenu implements Closeable, Subdevideable, Submenu, Refreshable {

	private ItemSet set;
	
	public SelectItemAmount(ItemSet set) {
		super(9);
		this.set = set;
		setTitle("Change Item Attributes");
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason arg2) {}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		
		returnMap.put(8, createReturnItem(p, "Return to Set Edit"));
		
		for (int i = 0; i<9; i++) {
			DoubleWrapper<ItemStack, Runnable> item = createGuiItem(() -> {}, Material.PAPER, "Change Enchants", "Change Enchants with " + (i+1) + " items");
			returnMap.put(i, item);
		}
		return returnMap;
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}

	@Override
	public CustomMenu getSubmenu(int slot) {
		return new SelectEnchantment(set, slot+1);
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot >= 0 && slot < 9) {
			return true;
		}
		return false;
	}

}
