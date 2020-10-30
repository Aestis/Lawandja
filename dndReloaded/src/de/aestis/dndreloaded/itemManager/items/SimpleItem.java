package de.aestis.dndreloaded.itemManager.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.enchants.EnchantmentWrapper;
import de.aestis.dndreloaded.itemManager.items.set.ItemSet;
import net.md_5.bungee.api.ChatColor;
import oxolotel.utils.DoubleWrapper;

/**
 * All methods in this class may have side effects on the item represented by any objects even if not stated in the documentation. Those changes are made to represent the changes made to the object.
 */
public class SimpleItem extends CustomItem {

	private ItemStack item;
	private Map<Attribute,Integer> attributes = new HashMap<>();
	private HashMap<CustomEnchantment, Integer> customEnchants;
	
	@SuppressWarnings("unchecked")
	protected SimpleItem(SimpleItem item) {
		super(item);
		this.item = item.item.clone();
		if (item.hasCustomEnchantments()) {
			this.customEnchants = (HashMap<CustomEnchantment, Integer>) item.customEnchants.clone();
		}
	}
	
	protected SimpleItem(ItemID id, ItemStack item) {
		super(id, null, ItemFlag.get(item, ItemFlag.ATTACK_SPEED, AttackSpeed.SUPER_FAST), extractLore(item, id), ItemFlag.get(item, ItemFlag.VALUE, 0), 0);
		
		this.item = item;
		setItemID(id);
		setAttackSpeed(attackSpeed);
		getCustomEnchantmentLevel();
		
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
	}
	
	protected SimpleItem(ItemID id, ItemSet set,AttackSpeed attackSpeed , ArrayList<String> lore, int value, int damage, HashMap<CustomEnchantment, Integer> customEnchants) {
		super(id, set, attackSpeed , lore, value, damage);
		
		this.item = new ItemStack(id.getMaterial());
		
		if (customEnchants != null) {
			for (CustomEnchantment ench:customEnchants.keySet()) {
				addEnchantment(ench, customEnchants.get(ench));
			}
		}
		
		setValue(value);
		setItemID(id);
		setDamage(damage);
		setAttackSpeed(attackSpeed);
		setDisplayName(id.getItemName());
		
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		
		regenerateLore();
	}
	
	/**
	 * @return a abstract representation of this item
	 */
	public AbstractItem toAbstractItem() {
		HashMap<CustomEnchantment,DoubleWrapper<Integer,Integer>> abstractEnchants = new HashMap<>();
		for (CustomEnchantment ench:customEnchants.keySet()) {
			abstractEnchants.put(ench,new DoubleWrapper<>(customEnchants.get(ench), customEnchants.get(ench)));
		}
		
		return CustomItem.createAbstractItem(id, lore, value, damage, abstractEnchants);
	}
	
	/**
	 * sets the amount of the item of this Custom item to the specified amount
	 * @param amount the new amount of items represented by this CutomItem
	 */
	public void setAmount(int amount) {
		item.setAmount(amount);
	}

	/**
	 * regenerates the Lore of the item
	 */
	public ArrayList<String> regenerateLore() {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>(this.lore);
		lore = addGroupLore(lore,id);
		lore = addEnchantmentLore(lore,customEnchants);
		if (lore.get(lore.size()-1).isEmpty()) {
			lore.remove(lore.size()-1);
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return lore;
	}
	
	/**
	 * resets all custom enchantments to their predefined values. adds and removes missing/additional enchantments
	 */
	public void fixCustomEnchants() {
		Map<Enchantment, Integer> presentEnchs = item.getEnchantments();
		if (presentEnchs.size() > 0) {
			for (Enchantment e:presentEnchs.keySet()) {
				if (e instanceof EnchantmentWrapper) {
					item.removeEnchantment(e);
				}
			}
		}
		
		Map<CustomEnchantment,Integer> customEnchants = getCustomEnchantmentLevel();
		if (customEnchants != null) {
			for (CustomEnchantment ench : customEnchants.keySet()) {
				addEnchantment(ench, customEnchants.get(ench));
			}
		}
	}
	
	/**
	 * @return a map containing all Custom Enchantments on this Itemstack and their levels. Changing the map has no effect
	 */
	@SuppressWarnings("unchecked")
	public HashMap<CustomEnchantment, Integer> getCustomEnchantmentLevel() {
		if (!hasCustomEnchantments()) {
			return null;
		}
		if (customEnchants != null) {
			return (HashMap<CustomEnchantment, Integer>) customEnchants.clone();
		}
		HashMap<CustomEnchantment, Integer> rtnMap = new HashMap<>();
		Map<Enchantment, Integer> enchantments = item.getEnchantments();
		for (Enchantment ench: enchantments.keySet()) {
			if (ench instanceof EnchantmentWrapper) {
				rtnMap.put(CustomEnchantment.getCustomEnchant(ench), enchantments.get(ench));
			}
		}
		customEnchants = rtnMap;
		return (HashMap<CustomEnchantment, Integer>) rtnMap.clone();
	}

	/**
	 * adds a Vanilla enchantment to the custom item represented by this Object
	 * @param ench the enchantment to add
	 * @param level the level of the enchantment
	 */
	public void addEnchantment(Enchantment ench, Integer level) {
		item.addUnsafeEnchantment(ench, level);
	}

	/**
	 * adds a Custom Enchantment to the custom item represented by this Object and adds a lore representing the Enchant
	 * @param ench the enchantment to add
	 * @param level the level of the enchantment
	 */
	public void addEnchantment(CustomEnchantment ench, Integer level) {
		addEnchantment(ench, level, !ench.isInvisible());
	}

	/**
	 * adds a Custom Enchantment to the custom item represented by this Object. Calls changeEnchantmentLevel if enchantment is present
	 * @param ench the enchantment to add
	 * @param level the level of the enchantment
	 * @param addLore if a lore should be added to the Itemstack that represents the enchant and level
	 */
	public void addEnchantment(CustomEnchantment ench, Integer level, boolean addLore) {
		if (customEnchants == null) {
			setHasCustomEnchants(true);
			customEnchants = new HashMap<>();
		} else if (customEnchants.containsKey(ench)) {
			changeEnchantmentLevel(ench, level);
			return;
		}
		level = Math.min(level, ench.getMaxLevel());
		item.addUnsafeEnchantment(ench.getSpigotEnchantment(), level);
		customEnchants.put(ench, level);
		if (addLore) {
			regenerateLore();
		}
	}
	
	/**
	 * @param ench the enchantment to set
	 * @param newLevel the new level of the enchantment
	 * @param addLore if the lore should be reset
	 */
	public void changeEnchantmentLevel(CustomEnchantment ench, Integer newLevel) {
		if (customEnchants == null) {
			addEnchantment(ench, newLevel);
			return;
		}
		if (newLevel <= 0) {
			customEnchants.remove(ench);
			item.removeEnchantment(ench.getSpigotEnchantment());
			if (customEnchants.size() == 0) {
				customEnchants = null;
				setHasCustomEnchants(false);
			}
		} else {
			customEnchants.put(ench, newLevel);
			item.addUnsafeEnchantment(ench.getSpigotEnchantment(), newLevel);
		}
		if (!ench.isInvisible()) {
			regenerateLore();
		}
	}

	/**
	 * @param hasEnchant set the Custom Enchantment flag on the item
	 */
	private void setHasCustomEnchants(boolean hasEnchant) {
		if (hasEnchant) {
			ItemFlag.set(item, ItemFlag.HAS_CUSTOM_ENCHANT, Boolean.valueOf(hasEnchant));
		} else {
			ItemFlag.unSet(item, ItemFlag.HAS_CUSTOM_ENCHANT);
		}
		
	}

	/**
	 * @return true if the Custom Item has any Custom Enchantments
	 */
	public boolean hasCustomEnchantments() {
		return customEnchants == null ? ItemFlag.get(item, ItemFlag.HAS_CUSTOM_ENCHANT, Boolean.valueOf(false)) : true;
	}
	
	public void setAttribute(Attribute attribute, int amount) {
		ItemMeta meta = item.getItemMeta();
		
		AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), attribute.name(), amount, AttributeModifier.Operation.ADD_NUMBER);
		meta.addAttributeModifier(attribute, modifier);
		meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		attributes.put(attribute, amount);
		
		
	}
	
	public <T> void setItemFlag(ItemFlag<T> flag, T value) {
		if (flag == ItemFlag.ITEM_ID) {
			setItemID((ItemID) value);
		} else if(flag == ItemFlag.VALUE) {
			setValue((int) value);
		} else {
			ItemFlag.set(item, flag, value);
		}
	}

	@Override
	public void setGroup(ItemGroup group) {
		super.setGroup(group);
		setItemID(id);
		regenerateLore();
	}
	
	@Override
	public void setDisplayName(String name) {
		super.setDisplayName(name);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}

	@Override
	public void setMaterial(Material material) {
		super.setMaterial(material);
		item.setType(material);
	}
	
	@Override
	public void setDamage(int damage) {
		super.setDamage(damage);
		if (item.getItemMeta() != null && item.getItemMeta() instanceof Damageable) {
			Damageable meta = (Damageable) item.getItemMeta();
			meta.setDamage(damage);
		}
	}
	
	@Override
	public void setItemID(ItemID id) {
		super.setItemID(id);
		ItemFlag.set(item, ItemFlag.ITEM_ID, id);
		regenerateLore();
	}

	/**
	 * sets the lore and regenerates the Lore on the ItemStack
	 */
	@Override
	public void setLore(ArrayList<String> lore) {
		super.setLore(lore);
		regenerateLore();
	}
	
	@Override
	public void setAttackSpeed(AttackSpeed attackSpeed) {
		super.setAttackSpeed(attackSpeed);
		ItemFlag.set(item, ItemFlag.ATTACK_SPEED, attackSpeed);
	}
	
	@Override
	public void setValue(int value) {
		super.setValue(value);
		ItemFlag.set(item, ItemFlag.VALUE, this.value);
	}

	@Override
	public Integer getValue() {
		if (value != 0) {
			return value;
		} else {
			value = ItemFlag.get(item, ItemFlag.VALUE, Integer.valueOf(0));
			return value;
		}
	}

	@Override
	public ItemStack getSpigotItem() {
		return item.clone();
	}
	
	@Override
	public ItemStack getEditorItem() {
		ItemStack itemStack = getSpigotItem();
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) {
			lore = new ArrayList<String>();
		}
		lore.add(ChatColor.RED + "Flags:");
		for (ItemFlag<?> flag:ItemFlag.values()) {
			Object value = ItemFlag.get(itemStack, flag, null);
			if (value != null) {
				lore.add(ChatColor.RED + flag.getName() + ":" + value);
			}
		}
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	@Override
	public List<CustomEnchantment> getCustomEnchants() {
		return new ArrayList<CustomEnchantment>(customEnchants.keySet());
	}

	@Override
	public SimpleItem aply(ItemStack item, boolean applyFix) {
		SimpleItem rtnItem = new SimpleItem(this);
		rtnItem.item = item;
		if (applyFix) {
			rtnItem.fixCustomEnchants();
			rtnItem.regenerateLore();
		}
		return rtnItem;
	}

	@Override
	public CustomItem clone() {
		return new SimpleItem(this);
	}
	
	@Override
	public String toString() {
		return super.toString().replace("}", ", customeEnchants=" + customEnchants + "}");
	}
}
