package de.aestis.dndreloaded.itemManager.items;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import de.aestis.dndreloaded.Helpers.PseudoPlugin;
import de.aestis.dndreloaded.itemManager.items.dataTypes.AttackSpeedDataType;
import de.aestis.dndreloaded.itemManager.items.dataTypes.BooleanDataType;
import de.aestis.dndreloaded.itemManager.items.dataTypes.ItemIDDataType;

public class ItemFlag <V> {

	private static List<ItemFlag<?>> registeredFlags = new ArrayList<>();
	public static final ItemFlag<Boolean> HAS_CUSTOM_ENCHANT = register(new ItemFlag<>("ench", new BooleanDataType()));
	public static final ItemFlag<ItemID> ITEM_ID = register(new ItemFlag<>("id", new ItemIDDataType()));
	public static final ItemFlag<Long> COOLDOWN = register(new ItemFlag<>("cd", PersistentDataType.LONG));
	public static final ItemFlag<Integer> VALUE = register(new ItemFlag<>("val", PersistentDataType.INTEGER));
	public static final ItemFlag<AttackSpeed> ATTACK_SPEED = register(new ItemFlag<>("spd", new AttackSpeedDataType()));
	
	private static final Plugin PLUGNIN_NAMESPACE  = new PseudoPlugin("flg");
	
	private String name;
	private V defaultValue;
	private PersistentDataType<?, V> dataType;
	
	/**
	 * @param name the name and storage name of the ItemFlag
	 * @param dataType the type that is stored by the item flag
	 */
	public ItemFlag(String name, PersistentDataType<?, V> dataType) {
		this.name = name;
		this.dataType = dataType;
	}
	
	/**
	 * @param name the name and storage name of the ItemFlag
	 * @param dataType the type that is stored by the item flag
	 * @param defaultValue the default value to return if no value is found
	 */
	public ItemFlag(String name, PersistentDataType<?, V> dataType, V defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}
	
	/**
	 * @param <T> the type to store
	 * @param flagToRegister the ItemFlag that should be registered
	 * @return returns the flagToRegister param without changing it
	 */
	public static <T> ItemFlag<T> register(ItemFlag<T> flagToRegister) {
		registeredFlags.add(flagToRegister);
		return flagToRegister;
	}
	
	/**
	 * @return all registered ItemFlags
	 */
	public static ItemFlag<?>[] values() {
		return registeredFlags.toArray(new ItemFlag[0]);
	}

	/**
	 * @return the name of the ItemFlag
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type of the Value that is represented by this Flag
	 */
	public Class<V> getValueType() {
		return dataType.getComplexType();
	}
	
	/**
	 * @return the default value of this Flag
	 */
	public V getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * @return the type of the Value that is represented by this Flag
	 */
	public PersistentDataType<?, V> getDataType() {
		return dataType;
	}
	
	/**
	 * @param <V> the type of the value to unset
	 * @param item the item to remove the stored information from
	 * @param flag the flag to unset the value from
	 */
	public static <V> void unSet(ItemStack item, ItemFlag<V> flag) {
		ItemMeta itemMeta = item.getItemMeta();
		PersistentDataContainer container = itemMeta.getPersistentDataContainer();
		container.remove(new NamespacedKey(PLUGNIN_NAMESPACE, flag.getName()));
		item.setItemMeta(itemMeta);
	}
	
	/**
	 * @param <V> the type of the value to set
	 * @param item the item to store the information on
	 * @param flag the flag to set the value to
	 * @param value the value to set
	 */
	public static <V> void set(ItemStack item, ItemFlag<V> flag, V value) {
		ItemMeta itemMeta = item.getItemMeta();
		PersistentDataContainer container = itemMeta.getPersistentDataContainer();
		container.set(new NamespacedKey(PLUGNIN_NAMESPACE, flag.getName()), flag.getDataType(), value);
		item.setItemMeta(itemMeta);
	}
	
	/**
	 * @param <V> the type of the value to get
	 * @param item the item to get the value from
	 * @param flag the flag to get the value of
	 * @param defaultValue the default to return if nothing is found
	 * @return the value that is found for the flag or default value if not present or item is null
	 */
	public static <V> V get(ItemStack item, ItemFlag<V> flag, V defaultValue) {
		if (item == null || !item.hasItemMeta()) {
			return defaultValue;
		}
		ItemMeta itemMeta = item.getItemMeta();
		PersistentDataContainer container = itemMeta.getPersistentDataContainer();

		NamespacedKey key = new NamespacedKey(PLUGNIN_NAMESPACE, flag.getName());
		if (container.has(key, flag.getDataType())) {
			defaultValue = container.get(key, flag.getDataType());
		}
		return defaultValue;
	}
	
	/**
	 * @param <V> the type of the value to get
	 * @param item the item to check the value on
	 * @param flag the flag to check
	 * @return true if the flag is set on the itemstack false otherwise
	 */
	public static <V> boolean isSet(ItemStack item, ItemFlag<V> flag) {
		ItemMeta itemMeta = item.getItemMeta();
		PersistentDataContainer container = itemMeta.getPersistentDataContainer();

		NamespacedKey key = new NamespacedKey(PLUGNIN_NAMESPACE, flag.getName());
		return container.has(key, flag.getDataType());
	}
}
