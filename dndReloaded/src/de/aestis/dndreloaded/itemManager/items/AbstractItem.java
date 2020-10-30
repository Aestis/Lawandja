package de.aestis.dndreloaded.itemManager.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.items.set.ItemSet;
import net.md_5.bungee.api.ChatColor;
import oxolotel.utils.DoubleWrapper;

public class AbstractItem extends CustomItem{
	
	private HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>> customEnchants;
	
	@SuppressWarnings("unchecked")
	public AbstractItem(AbstractItem item) {
		super(item);
		if (item.customEnchants != null) {
			this.customEnchants = (HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>>) item.customEnchants.clone();
		}
	}
	
	public AbstractItem(ItemID id, ItemSet set, AttackSpeed attackSpeed, ArrayList<String> lore, int value, int damage, HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>> customEnchants) {
		super(id, set, attackSpeed, lore, value, damage);
		this.customEnchants = customEnchants;
	}

	/**
	 * @return a simple version of this item with randomized values for the enchantments
	 */
	public SimpleItem toSimpleItem() {
		HashMap<CustomEnchantment, Integer> simpleEnchant = new HashMap<CustomEnchantment, Integer>();
		if (customEnchants != null) {
			for (CustomEnchantment ench: customEnchants.keySet()) {
				Integer min = customEnchants.get(ench).getValue1();
				Integer max = customEnchants.get(ench).getValue2();
				simpleEnchant.put(ench, (int) (Math.random() * (max - min)) + min);
			}
		}
		return new SimpleItem(id, set,attackSpeed , lore, value, damage, simpleEnchant);
	}
	
	/**
	 * @return a map containing all Custom Enchantments on this Itemstack and their levels. Changing the map has no effect
	 */
	@SuppressWarnings("unchecked")
	public HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>> getCustomEnchantmentLevel() {
		if (customEnchants != null) {
			return (HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>>) customEnchants.clone();
		}
		return new HashMap<>();
	}
	
	/**
	 * @param ench the enchantment to set
	 * @param newMinLevel the new minimum level of the enchantment
	 * @param newMaxLevel the new maximum level of the enchantment
	 * @param addLore if the lore should be reset
	 */
	public void changeEnchantmentLevel(CustomEnchantment ench, Integer newMinLevel, Integer newMaxLevel) {
		if (customEnchants == null) {
			customEnchants = new HashMap<>();
		}
		if (newMinLevel <= 0 && newMaxLevel <= 0) {
			customEnchants.remove(ench);
			if (customEnchants.size() == 0) {
				customEnchants = null;
			}
			return;
		}
		customEnchants.put(ench, new DoubleWrapper<>(newMinLevel, newMaxLevel));
	}

	@Override
	public ItemStack getSpigotItem() {
		return toSimpleItem().getSpigotItem();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemStack getEditorItem() {
		ItemStack item = new ItemStack(id.getMaterial());
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = this.lore == null ? new ArrayList<String>() : (ArrayList<String>) this.lore.clone();
		addGroupLore(lore, id);
		addEnchantmentLoreRanged(lore, customEnchants);
		if (lore.get(lore.size()-1).isEmpty()) {
			lore.remove(lore.size()-1);
		}
		meta.setLore(lore);
		if (meta instanceof Damageable) {
			((Damageable) meta).setDamage(damage);
		}
		item.setItemMeta(meta);
		lore.add(ChatColor.RED + "Flags:");
		lore.add(ChatColor.RED + "Value: " + value);
		return item;
	}
	
	@Override
	public List<CustomEnchantment> getCustomEnchants() {
		return new ArrayList<CustomEnchantment>(customEnchants.keySet());
	}

	@Override
	public SimpleItem aply(ItemStack item, boolean applyFix) {
		HashMap<CustomEnchantment, Integer> simpleEnchant = new HashMap<CustomEnchantment, Integer>();
		for (CustomEnchantment ench: customEnchants.keySet()) {
			int level = item.getEnchantmentLevel(ench.getSpigotEnchantment());
			Integer min = customEnchants.get(ench).getValue1();
			Integer max = customEnchants.get(ench).getValue2();
			if (level != 0) {
				simpleEnchant.put(ench, Math.max(min, Math.min(level, max)));
			} else {
				simpleEnchant.put(ench, (int) (Math.random() * max - min) + min);
			}
		}
		SimpleItem newItem = new SimpleItem(id, set, attackSpeed ,lore, value, damage, simpleEnchant);
		return newItem.aply(item, applyFix);
	}

	@Override
	public CustomItem clone() {
		return new AbstractItem(this);
	}

	@Override
	public String toString() {
		return super.toString().replace("}", ", customeEnchants=" + customEnchants + "}");
	}
}
