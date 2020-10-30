package de.aestis.dndreloaded.itemManager.items.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.bukkit.enchantments.Enchantment;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemID;
import de.aestis.dndreloaded.itemManager.items.ItemManager;

public class ItemSet {

	private static Map<String,ItemSet> registeredSets = new HashMap<>();

	private String name;
	private List<ItemID> items = new ArrayList<>();
	private List<Map<Enchantment,Integer>> enchantmentLevel = new ArrayList<>();

	public static List<ItemSet> getRegisteredSets() {
		return new ArrayList<ItemSet>(registeredSets.values());
	}
	
	public static ItemSet loadSet(String s) {
		return registeredSets.get(s);
	}

	public static ItemSet register(ItemSet set) {
		return registeredSets.put(set.name, set);
	}
	
	public static void unregister(ItemSet itemSet) {
		registeredSets.remove(itemSet.getName());
	}

	public ItemSet(String name, List<Map<Enchantment,Integer>> enchants, ItemID... members) {
		this.name = name;
		this.enchantmentLevel = enchants;
		this.items = new ArrayList<ItemID>(Arrays.asList(members));
	}
	
	public String getName() {
		return name;
	}

	public void addMember(ItemID item) {
		CustomItem custom = ItemManager.getInstance().getItemByID(item);
		ItemSet set = custom.getItemSet();
		if (custom.getItemSet() != null) {
			set.removeMember(item);
		}
		custom.setItemSet(this);
		ItemManager.getInstance().registerItem(custom);
		items.add(item);
	}

	public void removeMember(ItemID item) {
		CustomItem custom = ItemManager.getInstance().getItemByID(item);
		custom.setItemSet(null);
		ItemManager.getInstance().registerItem(custom);
		items.remove(item);
	}

	public List<ItemID> getMembers() {
		return items;
	}
	
	public boolean isMember(CustomItem item) {
		return isMember(item.getItemID());
	}

	public boolean isMember(ItemID id) {
		return items.contains(id);
	}

	public List<Map<Enchantment, Integer>> getEnchantmentLevel() {
		return enchantmentLevel;
	}

	public void setEnchantsOfLevel(int level, Map<Enchantment,Integer> enchants) {
		enchantmentLevel.set(level, enchants);
	}

	public Map<Enchantment, Integer> getEnchantsOfLevel(int level){
		if (enchantmentLevel.size() > level) {
			Map<Enchantment, Integer> enchants = enchantmentLevel.get(level);
			if (enchants != null) {
				return enchants;
			}
		}
		return new HashMap<>();
		
	}
	
	@Override
	public String toString() {
		return String.join("", "{name=", name, ",Items=", items.toString());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(items.toArray());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (obj instanceof ItemSet) {
			if (items.containsAll(((ItemSet) obj).items)) {
				return true;
			}
		}
		return false;
	}

	public void setName(String newName) {
		ItemSet.unregister(this);
		this.name = newName;
		ItemSet.register(this);
	}
	
}
