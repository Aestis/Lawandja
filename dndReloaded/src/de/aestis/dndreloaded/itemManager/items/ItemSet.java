package de.aestis.dndreloaded.itemManager.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.bukkit.enchantments.Enchantment;

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

	public ItemSet(String name, List<Map<Enchantment,Integer>> enchants, ItemID... members) {
		this.name = name;
		this.enchantmentLevel = enchants;
		this.items = Arrays.asList(members);
	}
	
	public String getName() {
		return name;
	}

	public void addMember(ItemID item) {
		items.add(item);
	}

	public void removeMember(ItemID item) {
		items.remove(item);
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
		Map<Enchantment, Integer> enchants = enchantmentLevel.get(level);
		if (enchants != null) {
			return enchants;
		} else {
			return new HashMap<>();
		}
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

	
}
