package de.aestis.dndreloaded.itemManager.enchants;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.itemManager.ItemLoader;
import de.aestis.dndreloaded.itemManager.enchants.effects.EnchantmentCore;

public class CustomEnchantment implements Comparable<CustomEnchantment>{

	static JavaPlugin plugin = Main.instance;
	private static Map<EnchantmentCore,CustomEnchantment> byCore = new HashMap<>();
	private static Set<CustomEnchantment> registeredEnchants = new TreeSet<>();
	private Enchantment enchantment;
	private EnchantmentCore core;
	private String name;
	private int minLevel;
	private int maxLevel;

	/**
	 * sets minLevel to 0
	 * @param name the name of the enchantment to create
	 * @param maxLevel the maximum level the enchantment should have
	 * @param target to what type of item the enchantment should be able to be applied
	 * @param core the main part of the enchantment. Includes functionality of the enchantment
	 */
	public CustomEnchantment(String name, int maxLevel, EnchantmentTarget target , EnchantmentCore core) {
		this(name, 0, maxLevel, target, core);
	}

	/**
	 * @param name the name of the enchantment to create
	 * @param maxLevel the maximum level the enchantment should have
	 * @param minValue the minimum Level the enchantment should have
	 * @param target to what type of item the enchantment should be able to be applied
	 * @param core the main part of the enchantment. Includes functionality of the enchantment
	 */
	public CustomEnchantment(String name, int minValue, int maxValue, EnchantmentTarget target , EnchantmentCore core) {
		this.maxLevel = maxValue;
		this.minLevel = minValue;
		this.enchantment = new EnchantmentWrapper(name.toLowerCase(), maxLevel, target);
		this.core = core;
		this.name = name;
	}

	/**
	 * @param enchant the enchantment to check
	 * @return true if enchantment is registered
	 */
	public static boolean isRegistered(CustomEnchantment enchant) {
		return Arrays.asList(Enchantment.values()).contains(enchant.getSpigotEnchantment());
	}

	/**
	 * @param enchant the enchantment to register
	 * @return if the enchantment was registered, false on error or if already registered
	 */
	public static boolean register(CustomEnchantment enchant) {
		if (isRegistered(enchant)) {
			return false;
		}
		boolean registered = true;
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(enchant.getSpigotEnchantment());
			registeredEnchants.add(enchant);
			Bukkit.getServer().getPluginManager().registerEvents(enchant.core, plugin);
			Enchantment.stopAcceptingRegistrations();
		} catch (Exception e) {
			registered = false;
			e.printStackTrace();
		}
		byCore.put(enchant.core, enchant);

		return registered;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void unregister(CustomEnchantment enchantment) {
		try {
			Field byKeyField = Enchantment.class.getDeclaredField("byKey");
			Field byNameField = Enchantment.class.getDeclaredField("byName");
			byKeyField.setAccessible(true);
			byNameField.setAccessible(true);
			Map<NamespacedKey, Enchantment> byKey = (Map<NamespacedKey, Enchantment>) byKeyField.get(null);
			Map<String, Enchantment> byName = (Map<String, Enchantment>) byNameField.get(null);
			byKey.remove(enchantment.getSpigotEnchantment().getKey());
			byName.remove(enchantment.getSpigotEnchantment().getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		HandlerList.unregisterAll(enchantment.core);
		registeredEnchants.remove(enchantment);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void unregisterAll() {
		try {
			Field byKeyField = Enchantment.class.getDeclaredField("byKey");
			Field byNameField = Enchantment.class.getDeclaredField("byName");
			byKeyField.setAccessible(true);
			byNameField.setAccessible(true);
			Map<NamespacedKey, Enchantment> byKey = (Map<NamespacedKey, Enchantment>) byKeyField.get(null);
			Map<String, Enchantment> byName = (Map<String, Enchantment>) byNameField.get(null);
			for (CustomEnchantment enchantment:registeredEnchants) {
				byKey.remove(enchantment.getSpigotEnchantment().getKey());
				byName.remove(enchantment.getSpigotEnchantment().getName());
				HandlerList.unregisterAll(enchantment.core);
			}
			registeredEnchants.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @return a list of all registered Custom enchants
	 */
	public static Set<CustomEnchantment> getRegisteredCustomEnchantments() {
		return registeredEnchants;
	}

	public static CustomEnchantment getByCore(EnchantmentCore core) {
		return byCore.get(core);
	}

	/**
	 * @param ench the Spigot enchantment to get a CustomEnchantmet equivalent of
	 * @return the CustomEnchantment if it exists, null otherwise
	 */
	public static CustomEnchantment getCustomEnchant(Enchantment ench) {
		for (CustomEnchantment custom:registeredEnchants) {
			if (custom.getSpigotEnchantment().equals(ench)) {
				return custom;
			}
		}
		return null;
	}

	/**
	 * @return the spigot enchantment object represented by this object
	 */
	public Enchantment getSpigotEnchantment() {
		return enchantment;
	}

	/**
	 * @return the encahntment core of this CustomEnchantment
	 */
	public EnchantmentCore getEnchantmentCore() {
		return core;
	}

	/**
	 * @return the maximum level of the enchantment
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * @return the minimum level of the enchantment
	 */
	public int getMinLevel() {
		return minLevel;
	}

	/**
	 * @return a random level between minLevel and maxLevel
	 */
	public int getRandomLevel() {
		return ((int)(Math.random()*maxLevel-minLevel)) + minLevel;
	}

	/**
	 * @return true if the enchantment should be displayed on the itemstack
	 */
	public boolean isInvisible() {
		return core.isInvisible();
	}

	/**
	 * @return the name of the enchantment
	 */
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(CustomEnchantment o) {
		return this.core.compareTo(o.core);
	}
}
