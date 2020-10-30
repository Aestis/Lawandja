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
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class SelectEnchantment extends CustomMenu implements Closeable, Subdevideable, Submenu, Refreshable{

	private ItemSet set;
	private int itemAmount;
	
	public SelectEnchantment(ItemSet set, int itemAmount) {
		super(CustomEnchantment.getRegisteredCustomEnchantments().size()+1);
		this.set = set;
		this.itemAmount = itemAmount;
		setTitle("Select Enchantment");
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		List<CustomEnchantment> enchants = new ArrayList<>(CustomEnchantment.getRegisteredCustomEnchantments());

		Map<Enchantment, Integer> activeEnchants = set.getEnchantsOfLevel(itemAmount-1);
		
		for (int i = 0; i < enchants.size(); i++) {
			ItemStack newItem = new ItemStack(Material.PAPER);
			ItemMeta meta = newItem.getItemMeta();
			CustomEnchantment enchant = enchants.get(i);
			meta.setDisplayName(enchant.getEnchantmentCore().getStringRepresentation());
			List<String> lores = new ArrayList<>();
			lores.add("Klick to change the Level of " + enchant.getEnchantmentCore().getStringRepresentation());
			lores.add("on this item.");
			String levelString = "-";
			Integer level;
			if (activeEnchants != null && (level = activeEnchants.get(enchant.getSpigotEnchantment())) != null) {
				levelString = "" + level;
			}
			lores.add("Current Level: " + levelString);
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			
			returnMap.put(i, new DoubleWrapper<>(newItem, null));
		}
		
		{
			ItemStack newItem = new ItemStack(Material.GREEN_CONCRETE);
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Return to Item Editor");
			newItem.setItemMeta(meta);
			returnMap.put(CustomEnchantment.getRegisteredCustomEnchantments().size(), new DoubleWrapper<>(newItem, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		}
		
		return returnMap;
	}

	@Override
	public CustomMenu getSubmenu(int slot) {
		if (hasSubmenu(slot)) {
			return new ChangeBonusLevel(set, itemAmount, new ArrayList<>(CustomEnchantment.getRegisteredCustomEnchantments()).get(slot));
		}
		return null;
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot >= 0 && slot < CustomEnchantment.getRegisteredCustomEnchantments().size()) {
			return true;
		}
		return false;
	}

	@Override
	public void onClose(Player p, ItemStack[] items, CloseReason arg2) {}

	@Override
	public double getRefreshTime() {
		return 1.5;
	}

	@Override
	public void onRefresh() {}
}
