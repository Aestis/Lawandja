package de.aestis.dndreloaded.itemManager.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.enchants.effects.EnchantmentCore;
import de.aestis.dndreloaded.itemManager.items.set.ItemSet;
import oxolotel.utils.DoubleWrapper;

public abstract class CustomItem implements Comparable<CustomItem>, Cloneable {
	
	protected AttackSpeed attackSpeed;
	protected ItemID id;
	protected ArrayList<String> lore;
	protected int value;
	protected int damage;
	protected ItemSet set;
	
	/**
	 * @param id the id of the item
	 * @param lore the lore of the item
	 * @param value the value of the item
	 * @param damage the damage of the item
	 */
	public CustomItem(ItemID id, ItemSet set, AttackSpeed attackSpeed, ArrayList<String> lore, int value, int damage) {
		this.id = id;
		this.lore = lore;
		this.value = value;
		this.damage = damage;
		this.attackSpeed = attackSpeed;
		this.set = set;
	}
	
	/**
	 * copies the given CustomItem
	 * @param item the CustomItem to Copy
	 */
	@SuppressWarnings("unchecked")
	public CustomItem(CustomItem item) {
		this.id = item.id;
		this.lore = (ArrayList<String>) item.lore.clone();
		this.damage = item.damage;
		this.value = item.value;
		this.set = item.set;
	}

	public static ArrayList<String> extractLore(ItemStack item, ItemID id) {
		ArrayList<String> lore;
		if (item.getItemMeta().getLore() != null) {
			lore = new ArrayList<String>(item.getItemMeta().getLore());
			if (ItemFlag.get(item, ItemFlag.HAS_CUSTOM_ENCHANT, false)) {
				for (String s : item.getItemMeta().getLore()) {
					if (s.isEmpty()) {
						break;
					}
					lore.remove(0);
				}
			}
			if (!id.getGroup().getGroupLore().isEmpty()) {
				for (int i = lore.size()-1; i >= 0 ;i--) {
					if (lore.get(i).isEmpty()) {
						lore = new ArrayList<String>(lore.subList(0, i));
						break;
					} else if (lore.get(i).equals(id.getGroup().getGroupLore())) {
						lore = new ArrayList<String>(lore.subList(0, i));
					} else {
						break;
					}
				}
			}
		} else {
			lore = new ArrayList<String>();
		}
		return lore;
	}
	
	/**
	 * @param id the id of the item to load
	 * @return the item with the given ID. If the itemGroup is UNKNOWN it will create a new Simple item from the id. If Group is not UNKNOWN and ItemID is not registered returns null
	 */
	public static CustomItem loadCustomItem(ItemID id) {
		return ItemManager.getInstance().getItemByID(id);
	}
	
	public static SimpleItem loadCustomItem(ItemStack item, ItemID id, boolean fixItem) {
		if (id == null) {
			String name = getItemName(item);
			Material material = item.getType();
			ItemGroup group = ItemGroup.UNKNOWN;
			id = new ItemID(group, material, name);
		}
		
		CustomItem customItem = loadCustomItem(id);
		if (customItem != null) {
			return customItem.aply(item, fixItem);
		} else {
			SimpleItem simpleItem = new SimpleItem(id,item);
			simpleItem.regenerateLore();
			return simpleItem;
		}		
	}
	
	public static SimpleItem loadCustomItem(ItemStack item, boolean fixItem) {
		ItemID id = ItemFlag.get(item, ItemFlag.ITEM_ID, new ItemID(ItemGroup.UNKNOWN, item.getType(), getItemName(item)));
		return loadCustomItem(item, id, fixItem);
	}
	
	public static SimpleItem createSimpleItem(Material material, ItemGroup group, String name) {
		return new SimpleItem(new ItemID(group, material, name), null, AttackSpeed.NORMAL, new ArrayList<String>(), 0, 0, null);
	}
	
	public static SimpleItem createSimpleItem(ItemID id) {
		return new SimpleItem(id, null, AttackSpeed.NORMAL, new ArrayList<String>(), 0, 0, null);
	}
	
	public static SimpleItem createSimpleItem(ItemID id, ArrayList<String> lore, HashMap<CustomEnchantment, Integer> customEnchants) {
		return new SimpleItem(id, null, AttackSpeed.NORMAL, lore, 0, 0, customEnchants);
	}
	
	public static SimpleItem createSimpleItem(ItemID id, HashMap<CustomEnchantment, Integer> customEnchants) {
		return new SimpleItem(id, null, AttackSpeed.NORMAL, new ArrayList<String>(), 0, 0, customEnchants);
	}
	
	public static SimpleItem createSimpleItem(ItemID id, ArrayList<String> lore) {
		return new SimpleItem(id, null, AttackSpeed.NORMAL, lore, 0, 0, null);
	}
	
	public static SimpleItem createSimpleItem(ItemID id, int value) {
		return new SimpleItem(id, null, AttackSpeed.NORMAL, new ArrayList<String>(), value, 0, null);
	}
	
	public static SimpleItem createSimpleItem(ItemID id, ArrayList<String> lore, int value, int damage, HashMap<CustomEnchantment, Integer> customEnchants) {
		return new SimpleItem(id, null, AttackSpeed.NORMAL, lore, value, damage, customEnchants);
	}
	
	public static SimpleItem createSimpleItem(ItemID id, ItemSet set, ArrayList<String> lore, int value, int damage, HashMap<CustomEnchantment, Integer> customEnchants) {
		return new SimpleItem(id, set, AttackSpeed.NORMAL, lore, value, damage, customEnchants);
	}
	
	public static AbstractItem createAbstractItem(ItemID id) {
		return new AbstractItem(id, null, AttackSpeed.NORMAL, new ArrayList<String>(), 0, 0, null);
	}
	
	public static AbstractItem createAbstractItem(ItemID id, ArrayList<String> lore, int value, int damage, HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>> customEnchants) {
		return new AbstractItem(id, null, AttackSpeed.NORMAL, lore, value, damage, customEnchants);
	}
	
	public static AbstractItem createAbstractItem(ItemID id, HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>> customEnchants) {
		return new AbstractItem(id, null, AttackSpeed.NORMAL, new ArrayList<String>(), 0, 0, customEnchants);
	}
	
	public static AbstractItem createAbstractItem(ItemID id, ArrayList<String> lore, HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>> customEnchants) {
		return new AbstractItem(id, null, AttackSpeed.NORMAL, lore, 0, 0, customEnchants);
	}
	
	public static AbstractItem createAbstractItem(ItemID id, ItemSet set,ArrayList<String> lore, HashMap<CustomEnchantment, DoubleWrapper<Integer, Integer>> customEnchants) {
		return new AbstractItem(id, set, AttackSpeed.NORMAL, lore, 0, 0, customEnchants);
	}
	
	/**
	 * @param item the item to get the name from
	 * @return the item name if present or "-" if none present
	 */
	protected static String getItemName(ItemStack item) {
		String itemName =  item.getItemMeta().getDisplayName();
		if (itemName == null || itemName.isEmpty()) {
			itemName = "-";
		}
		return itemName;
	}
	
	/**
	 * @param ench the enchantment to create the lore for
	 * @param level the level of the enchantment
	 * @return a lore for the enchantment with the given level
	 */
	protected static String createEnchantmentLore(CustomEnchantment ench, Integer level) {
		String levelString = "";
		EnchantmentCore core = ench.getEnchantmentCore();
		if (core.isLevelPrefix()) {
			levelString = core.getLevelRepresentation(level) + " " + core.getStringRepresentation();
		} else {
			levelString = core.getStringRepresentation() + " " + core.getLevelRepresentation(level);
		}
		levelString = core.getDisplayColor(level) + levelString;
		return levelString;
	}
	
	/**
	 * @param ench the enchantment to create the lore for
	 * @param level the level of the enchantment
	 * @return a lore for the enchantment with the given level
	 */
	protected static String createEnchantmentLore(CustomEnchantment ench, Integer minLevel, Integer maxLevel) {
		String levelString = "";
		EnchantmentCore core = ench.getEnchantmentCore();
		if (core.isLevelPrefix()) {
			levelString = core.getLevelRepresentation(minLevel) + " to " + core.getLevelRepresentation(minLevel) + " " + core.getStringRepresentation();
		} else {
			levelString = core.getStringRepresentation() + " " + core.getLevelRepresentation(minLevel) + " to " + core.getLevelRepresentation(minLevel);
		}
		levelString = core.getDisplayColor(minLevel) + levelString;
		return levelString;
	}
	
	/**
	 * modifies the given list
	 * @param lore the lore to add the Enchantment Lores to
	 * @param customEnchants the enchantments to generate the lore for
	 * @return the new List of lores
	 */
	protected static ArrayList<String> addEnchantmentLore(ArrayList<String> lore, Map<CustomEnchantment,Integer> customEnchants) {
		if (customEnchants == null) {
			return lore;
		}
		if (lore.size() > 0 && !lore.get(0).isEmpty()) {
			lore.add(0,"");
		}
		for (CustomEnchantment ench: customEnchants.keySet()) {
			if (ench.isInvisible()) {
				continue;
			}
			if (lore == null) {
				lore = new ArrayList<>();
			}
			lore.add(0, createEnchantmentLore(ench, customEnchants.get(ench)));
		}
		return lore;
	}
	
	/**
	 * modifies the given list
	 * @param lore the lore to add the Enchantment Lores to
	 * @param customEnchants the enchantments to generate the lore for
	 * @return the new List of lores
	 */
	protected static ArrayList<String> addEnchantmentLoreRanged(ArrayList<String> lore, Map<CustomEnchantment,DoubleWrapper<Integer, Integer>> customEnchants) {
		if (customEnchants == null) {
			return lore;
		}
		if (lore.size() > 0 && !lore.get(0).isEmpty()) {
			lore.add(0,"");
		}
		for (CustomEnchantment ench: customEnchants.keySet()) {
			if (ench.isInvisible()) {
				continue;
			}
			if (lore == null) {
				lore = new ArrayList<>();
			}
			lore.add(0, createEnchantmentLore(ench, customEnchants.get(ench).getValue1(),customEnchants.get(ench).getValue2()));
		}
		return lore;
	}
	
	/**
	 * modifies the given list adds the Group Lore as last Entry
	 * @param lore the lore to add the group Lores to
	 * @param id the id of the item to get the group of
	 * @return the modified list of lores
	 */
	protected static ArrayList<String> addGroupLore(ArrayList<String> lore, ItemID id) {
		String grouplore = id.getGroup().getGroupLore();
		if (grouplore == null) {
			return lore;
		}
		if (lore == null) {
			lore = new ArrayList<>();
		}
		if (lore.size() > 0 && !lore.get(lore.size()-1).isEmpty()) {
			lore.add("");
		}
		lore.add(grouplore);
		return lore;
	}

	/**
	 * changes the name and ItemId and unregisters the old ID in the ItemManager
	 * @param name the new name of the item
	 */
	public void setDisplayName(String name) {
		if (name.equals(id.getItemName())) {
			return;
		}
		ItemManager manager = ItemManager.getInstance();
		ItemID oldID = id;
		id = new ItemID(id.getGroup(), id.getMaterial(), name);
		
		if (manager.isRegistered(oldID)) {
			manager.unregisterItem(oldID);
			manager.registerItem(this);
		}
		
	}
	
	/**
	 * changes the Material and ItemId and unregisters the old ID in the ItemManager
	 * @param material the new Material of the item
	 */
	public void setMaterial(Material material) {
		ItemManager manager = ItemManager.getInstance();
		ItemID oldID = id;
		id = new ItemID(id.getGroup(), material, id.getItemName());
		
		if (manager.isRegistered(oldID)) {
			manager.unregisterItem(oldID);
			manager.registerItem(this);
		}
	}
	
	/**
	 * changes the Group and ItemId and unregisters the old ID in the ItemManager
	 * @param group the new Material of the item
	 */
	public void setGroup(ItemGroup group) {
		ItemManager manager = ItemManager.getInstance();
		ItemID oldID = id;
		id = new ItemID(group, id.getMaterial(), id.getItemName());
		
		if (manager.isRegistered(oldID)) {
			manager.unregisterItem(oldID);
			manager.registerItem(this);
		}
	}
	
	/**
	 * changes the durability of the item
	 * @param damage the damage to set the item to
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Tries to regenerate the ItemID of the given item. If the ItemID is stored on the item this is used otherwise all registered items are searched for a matching item. If the item is a vanilla item or not registered the item will get a default ID. This method may be costly
	 * @param item the item to regenerate the id of
	 */
	public static void regenerateID(CustomItem item) {
		ItemID defaultId = new ItemID(ItemGroup.UNKNOWN, item.getSpigotItem().getType(), item.getSpigotItem().getItemMeta().getDisplayName());
		if ((item.id = ItemFlag.get(item.getSpigotItem(), ItemFlag.ITEM_ID, defaultId)) == defaultId) {
			ItemManager manager = ItemManager.getInstance();
			Map<ItemGroup, List<ItemID>> registeredItems = manager.getItemRegistrations();
			for (ItemGroup group: registeredItems.keySet()) {
				for (ItemID id: registeredItems.get(group)) {
					ItemStack other = manager.getItemByID(id).getSpigotItem();
					String otherName = other.getItemMeta().getDisplayName();
					TreeSet<CustomEnchantment> otherEnchants = new TreeSet<>(manager.getItemByID(id).getCustomEnchants());
					ItemStack checked = item.getSpigotItem();
					String checkedName = other.getItemMeta().getDisplayName();
					TreeSet<CustomEnchantment> checkedEnchants = new TreeSet<>(manager.getItemByID(id).getCustomEnchants());
					if (other.getType() == checked.getType() && otherName.equals(checkedName) && otherEnchants.equals(checkedEnchants)) {
						item.setItemID(id);
						return;
					}
				}
			}
		}
	}
	
	/**
	 * @return gets the ID of the item represented by this Object
	 */
	public ItemID getItemID() {
		return id;
	}
	
	public void setItemID(ItemID id) {
		this.id = id;
	}
	
	public ItemSet getItemSet() {
		return set;
	}
	
	public void setItemSet(ItemSet set) {
		this.set = set;
	}
	
	public AttackSpeed getAttackSpeed() {
		return attackSpeed;
	}
	
	public void setAttackSpeed(AttackSpeed attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
	/**
	 * @return the value of the item or null if no value is set
	 */
	public Integer getValue() {
		return value;
	}
	
	/**
	 * @param value the value of the Item. Negative numbers will be set to 0
	 */
	public void setValue(int value) {
		if (value < 0) {
			value = 0;
		}
		this.value = value;
	}
	
	/**
	 * @return the lore of the item without Group and Enchantments
	 */
	public ArrayList<String> getLore() {
		return lore;
	}
	
	/**
	 * @param lore sets the given list as the new lore
	 */
	public void setLore(ArrayList<String> lore) {
		this.lore = lore;
	}

	/**
	 * @return a Spigot representation for this Custom item with all set Flags put into the lore.
	 */
	public abstract ItemStack getEditorItem();
	
	/**
	 * @return a clone of the item that is represented by this object that is compatible with the spigot api
	 */
	public abstract ItemStack getSpigotItem();
	
	/**
	 * @return a List containing all Custom Enchantments on this Itemstack
	 */
	public abstract List<CustomEnchantment> getCustomEnchants();
	
	/**
	 * applies the settings stored in this CustomItem to the ItemStack given. Forces a ItemFix
	 * @param item the item to apply the settings to
	 * @return the SimpleItem resulting
	 */
	public SimpleItem aply(ItemStack item) {
		return aply(item,true);
	}
	
	/**
	 * applies the settings stored in this CustomItem to the ItemStack given.
	 * @param item the item to apply the settings to
	 * @param applyFix if the item should be fixed after creation
	 * @return the SimpleItem resulting
	 */
	public abstract SimpleItem aply(ItemStack item, boolean applyFix);
	
	@Override
	public abstract CustomItem clone();
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof CustomItem) {
			ItemID otherID = ((CustomItem) obj).getItemID();
			return id.equals(otherID);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public int compareTo(CustomItem item) {
		return id.compareTo(item.id);
	}
	
	@Override
	public String toString() {
		return "{ID=" + id + ", Value=" + value + ", AttackSpeed=" + attackSpeed + ", Damage=" + damage + ", Lore=" + lore + ", Set=" + set + "}";
	}
}
