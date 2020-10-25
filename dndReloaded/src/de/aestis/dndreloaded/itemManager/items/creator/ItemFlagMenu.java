package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.items.ItemFlag;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class ItemFlagMenu extends CustomMenu implements Closeable, Submenu, Subdevideable, Refreshable {

	private SimpleItem item;

	public ItemFlagMenu(SimpleItem item) {
		super(ItemFlag.values().length + 1);
		this.item = item;
		setTitle("Select Flag");
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason arg2) {}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();

		ItemFlag<?>[] values = ItemFlag.values();
		for (int i = 0; i < ItemFlag.values().length; i++) {
			ItemStack newItem = new ItemStack(Material.PAPER);
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Change " + values[i].getName());
			ArrayList<String> lores = new ArrayList<>();
			lores.add("Change the value of the ItemFlag");
			lores.add("Current Value: " + ItemFlag.get(item.getSpigotItem(), values[i], null));
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			returnMap.put(i, new DoubleWrapper<>(newItem, null));
		}
		
		ItemStack newItem = new ItemStack(Material.GREEN_CONCRETE);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName("Return to Item Editor");
		newItem.setItemMeta(meta);
		returnMap.put(values.length, new DoubleWrapper<>(newItem, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		
		return returnMap;
	}

	@Override
	public CustomMenu getSubmenu(int slot) {
		if (slot < ItemFlag.values().length) {
			ItemFlag<?> flag = ItemFlag.values()[slot];
			if (flag.equals(ItemFlag.VALUE)) {
				return new ValueMenu(item);
			} else if (flag.equals(ItemFlag.ITEM_ID)) {
				return new AppearanceMenu(item);
			} else if (flag.equals(ItemFlag.HAS_CUSTOM_ENCHANT)) {
					return new AppearanceMenu(item);
			}
			return new EditFlagMenu(item, flag);
		}
		return null;
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot < ItemFlag.values().length) {
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
