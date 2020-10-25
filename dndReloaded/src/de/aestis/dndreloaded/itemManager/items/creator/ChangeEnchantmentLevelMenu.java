package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.items.AbstractItem;
import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class ChangeEnchantmentLevelMenu extends CustomMenu implements Closeable, Submenu, Refreshable {

	private CustomItem item;
	private CustomEnchantment customEnchantment;
	private boolean simpleItem;
	private int minLevel = 0;
	private int maxLevel = 0;
	
	public ChangeEnchantmentLevelMenu(CustomItem item, CustomEnchantment customEnchantment) {
		super(item instanceof SimpleItem ? 9 : 18);
		this.item = item;
		this.customEnchantment = customEnchantment;
		if (item instanceof SimpleItem) {
			simpleItem = true;
			HashMap<CustomEnchantment, Integer> enchs = ((SimpleItem) item).getCustomEnchantmentLevel();
			if (enchs != null && enchs.containsKey(customEnchantment)) {
				minLevel = enchs.get(customEnchantment);
			}
		} else {
			HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>> enchs = ((AbstractItem) item).getCustomEnchantmentLevel();
			if (enchs != null && enchs.containsKey(customEnchantment)) {
				DoubleWrapper<Integer, Integer> minMax = enchs.get(customEnchantment);
				minLevel = minMax.getValue1();
				maxLevel = minMax.getValue2();
			}
		}
		
		setTitle("Change " + customEnchantment.getName());
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason reason) {
		if (reason == CloseReason.RETRUNPREVIOUS) {
			if (simpleItem) {
				SimpleItem simple = (SimpleItem) item;
				minLevel = Math.max(minLevel, 0);
				simple.changeEnchantmentLevel(customEnchantment, minLevel);
			} else {
				AbstractItem abstrct = (AbstractItem) item;
				minLevel = Math.max(minLevel, 0);
				abstrct.changeEnchantmentLevel(customEnchantment, minLevel, Math.max(minLevel, maxLevel));
			}
		}
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		String minLevelString = "Level";
		String maxLevelString = "maximum Level";
		
		if (!simpleItem) {
			minLevelString = "minimum " + minLevelString;
		}
		
		ItemStack newItem = new ItemStack(Material.GREEN_CONCRETE);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName("Aply edit:");
		List<String> lores = new ArrayList<>();
		lores.add("Sets the " + minLevelString + " to the Current Configuration");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(8, new DoubleWrapper<>(newItem, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		
		newItem = new ItemStack(Material.BLUE_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Current " + minLevelString + ":");
		lores = new ArrayList<>();
		lores.add("The current " + minLevelString + " of this item is:");
		lores.add("" + minLevel);
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(3, new DoubleWrapper<>(newItem, null));

		newItem = new ItemStack(Material.RED_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Decrease " + minLevelString);
		lores = new ArrayList<>();
		lores.add("Decrease " + minLevelString +" by 1");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(2, new DoubleWrapper<>(newItem, () -> minLevel = minLevel - 1 ));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Increase " + minLevelString);
		lores = new ArrayList<>();
		lores.add("Increase " + minLevelString + " by 1");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(4, new DoubleWrapper<>(newItem, () -> minLevel = minLevel + 1 ));

		newItem = new ItemStack(Material.RED_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Decrease " + minLevelString);
		lores = new ArrayList<>();
		lores.add("Decrease " + minLevelString + " by 10");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(1, new DoubleWrapper<>(newItem, () -> minLevel = minLevel - 10 ));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Increase " + minLevelString);
		lores = new ArrayList<>();
		lores.add("Increase " + minLevelString + " by 10");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(5, new DoubleWrapper<>(newItem, () -> minLevel = minLevel + 10 ));

		newItem = new ItemStack(Material.RED_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Decrease " + minLevelString);
		lores = new ArrayList<>();
		lores.add("Decrease " + minLevelString + " by 100");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(0, new DoubleWrapper<>(newItem, () -> minLevel = minLevel - 100 ));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Increase " + minLevelString);
		lores = new ArrayList<>();
		lores.add("Increase " + minLevelString + " by 100");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(6, new DoubleWrapper<>(newItem, () -> minLevel = minLevel + 100 ));
		
		returnMap.put(7, new DoubleWrapper<ItemStack, Runnable>(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), () -> {}));
		
		if (!simpleItem) {
			newItem = new ItemStack(Material.BLUE_CONCRETE);
			meta = newItem.getItemMeta();
			meta.setDisplayName("Current " + maxLevelString + ":");
			lores = new ArrayList<>();
			lores.add("The current " + maxLevelString + " of this item is:");
			lores.add("" + maxLevel);
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			returnMap.put(12, new DoubleWrapper<>(newItem, null));

			newItem = new ItemStack(Material.RED_CONCRETE);
			meta = newItem.getItemMeta();
			meta.setDisplayName("Decrease " + maxLevelString);
			lores = new ArrayList<>();
			lores.add("Decrease " + maxLevelString +" by 1");
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			returnMap.put(11, new DoubleWrapper<>(newItem, () -> maxLevel = maxLevel - 1 ));

			newItem = new ItemStack(Material.GREEN_CONCRETE);
			meta = newItem.getItemMeta();
			meta.setDisplayName("Increase " + maxLevelString);
			lores = new ArrayList<>();
			lores.add("Increase " + maxLevelString + " by 1");
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			returnMap.put(13, new DoubleWrapper<>(newItem, () -> maxLevel = maxLevel + 1 ));

			newItem = new ItemStack(Material.RED_CONCRETE);
			meta = newItem.getItemMeta();
			meta.setDisplayName("Decrease " + maxLevelString);
			lores = new ArrayList<>();
			lores.add("Decrease " + maxLevelString + " by 10");
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			returnMap.put(10, new DoubleWrapper<>(newItem, () -> maxLevel = maxLevel - 10 ));

			newItem = new ItemStack(Material.GREEN_CONCRETE);
			meta = newItem.getItemMeta();
			meta.setDisplayName("Increase " + maxLevelString);
			lores = new ArrayList<>();
			lores.add("Increase " + maxLevelString + " by 10");
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			returnMap.put(14, new DoubleWrapper<>(newItem, () -> maxLevel = maxLevel + 10 ));

			newItem = new ItemStack(Material.RED_CONCRETE);
			meta = newItem.getItemMeta();
			meta.setDisplayName("Decrease " + maxLevelString);
			lores = new ArrayList<>();
			lores.add("Decrease " + maxLevelString + " by 100");
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			returnMap.put(9, new DoubleWrapper<>(newItem, () -> maxLevel = maxLevel - 100 ));

			newItem = new ItemStack(Material.GREEN_CONCRETE);
			meta = newItem.getItemMeta();
			meta.setDisplayName("Increase " + maxLevelString);
			lores = new ArrayList<>();
			lores.add("Increase " + maxLevelString + " by 100");
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			returnMap.put(15, new DoubleWrapper<>(newItem, () -> maxLevel = maxLevel + 100 ));
			
			returnMap.put(17, new DoubleWrapper<ItemStack, Runnable>(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), () -> {}));
			returnMap.put(16, new DoubleWrapper<ItemStack, Runnable>(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), () -> {}));
		}
		
		return returnMap;
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}

}
