package de.aestis.dndreloaded.itemManager.items.set.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.items.set.ItemSet;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class ChangeBonusLevel extends CustomMenu implements Closeable, Submenu, Refreshable {

	private ItemSet set;
	private CustomEnchantment customEnchantment;
	private boolean simpleItem;
	private int minLevel = 0;
	private int itemAmount = 0;
	
	public ChangeBonusLevel(ItemSet set, int itemAmount, CustomEnchantment customEnchantment) {
		super(9);
		this.set = set;
		this.customEnchantment = customEnchantment;
		List<Map<Enchantment, Integer>> enchantmentLevel = set.getEnchantmentLevel();
		if (enchantmentLevel.size()<itemAmount) {
			for (int i = enchantmentLevel.size() ; i < itemAmount; i++) {
				enchantmentLevel.add(new HashMap<Enchantment, Integer>());
			}
		}
		Map<Enchantment, Integer> enchs = enchantmentLevel.get(itemAmount-1);
		if (enchs != null && enchs.containsKey(customEnchantment.getSpigotEnchantment())) {
			minLevel = enchs.get(customEnchantment.getSpigotEnchantment());
		}
		
		setTitle("Change " + customEnchantment.getName());
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason reason) {
		if (reason == CloseReason.RETRUNPREVIOUS) {
			set.getEnchantsOfLevel(itemAmount).put(customEnchantment.getSpigotEnchantment(), minLevel);
		}
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		String minLevelString = "Level";
		
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
		
		return returnMap;
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}

}
