package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class ValueMenu extends CustomMenu implements Closeable, Submenu, Refreshable{
	
	private int value;
	private CustomItem item;
	
	public ValueMenu(CustomItem item) {
		super(9);
		this.item = item;
		this.value = item.getValue();
		setTitle("Choose Value");
	}
	
	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason reason) {
		if (reason == CloseReason.RETRUNPREVIOUS) {
			item.setValue(value);
		}
	}
	
	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();

		ItemStack newItem = new ItemStack(Material.GREEN_CONCRETE);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName("Aply edit:");
		List<String> lores = new ArrayList<>();
		lores.add("Sets the Value to the Current Configuration");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(8, new DoubleWrapper<>(newItem, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		
		newItem = new ItemStack(Material.BLUE_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Current Value:");
		lores = new ArrayList<>();
		lores.add("The current Value of this item is:");
		lores.add("" + value);
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(3, new DoubleWrapper<>(newItem, null));

		newItem = new ItemStack(Material.RED_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Decrease Value");
		lores = new ArrayList<>();
		lores.add("Decrease Value by 10");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(2, new DoubleWrapper<>(newItem, () -> value = value - 10 ));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Increase Value");
		lores = new ArrayList<>();
		lores.add("Increase Value by 10");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(4, new DoubleWrapper<>(newItem, () -> value = value + 10 ));

		newItem = new ItemStack(Material.RED_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Decrease Value");
		lores = new ArrayList<>();
		lores.add("Decrease Value by 100");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(1, new DoubleWrapper<>(newItem, () -> value = value - 100 ));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Increase Value");
		lores = new ArrayList<>();
		lores.add("Increase Value by 100");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(5, new DoubleWrapper<>(newItem, () -> value = value + 100 ));

		newItem = new ItemStack(Material.RED_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Decrease Value");
		lores = new ArrayList<>();
		lores.add("Decrease Value by 1000");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(0, new DoubleWrapper<>(newItem, () -> value = value - 1000 ));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Increase Value");
		lores = new ArrayList<>();
		lores.add("Increase Value by 1000");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(6, new DoubleWrapper<>(newItem, () -> value = value + 1000 ));

		returnMap.put(7, new DoubleWrapper<ItemStack, Runnable>(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), () -> {}));
		return returnMap;
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}
}




