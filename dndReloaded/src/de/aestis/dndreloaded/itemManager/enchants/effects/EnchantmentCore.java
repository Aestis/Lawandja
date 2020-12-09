package de.aestis.dndreloaded.itemManager.enchants.effects;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.maths.RomanNumerals;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.enchants.EnchantmentData;
import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.events.PlayerConsumeItemEvent;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;

public interface EnchantmentCore extends Listener, Comparable<EnchantmentCore>{
	
	/**
	 * @return String representation of the Enchantment to be placed on the Item
	 */
	String getStringRepresentation();
	
	/**
	 * @param level the level of the enchantment to represent
	 * @return String representation of the Level of the enchantment
	 */
	String getLevelRepresentation(int level);
	
	/**
	 * @return true if the level representation should appear in front of the String representation
	 */
	boolean isLevelPrefix();
	
	/**
	 * @param level the level of the enchantment
	 * @return the Color that the Enchantment should be shown in
	 */
	ChatColor getDisplayColor(int level);

	/**
	 * @return true if the enchantment should be displayed on the itemstack
	 */
	public boolean isInvisible();
	
	/**
	 * @return the Group this Enchantment belongs to
	 */
	EnchantmentGroup getGroup();
	
	/**
	 * @param level the level to convert to Roman literals
	 * @return the Roman literals
	 */
	default String toRoman(int level) {
		return RomanNumerals.toRoman(level);
	}
	
	/**
	 * @return true if the enchantment is currently in cooldown for the specified player
	 */
	default boolean isInCooldown(Player p) {
		EnchantmentData data = Main.instance.Players.get(p).getEnchantmentData();
		Map<CustomEnchantment, Long> cooldowns = data.getCooldowns();
		CustomEnchantment ench = CustomEnchantment.getByCore(this);
		if (cooldowns.containsKey(ench)) {
			if (cooldowns.get(ench) > System.currentTimeMillis()) {
				return true;
			} else {
				cooldowns.remove(ench);
			}
		}
		return false;
	}
	
	/**
	 * sets a cooldown for this enchantment for the specified time and player
	 * @param p the player to set the cooldown for
	 * @param milisEndCooldown the miliseconds that the cooldown ends at
	 */
	default void setCooldown(Player p, int secondsCooldown) {
		Main.instance.Players.get(p).getEnchantmentData().getCooldowns().put(CustomEnchantment.getByCore(this), System.currentTimeMillis() + secondsCooldown * 1000);
	}
	
	default void callItemConsumeEvent(Player consumer, SimpleItem consumed) {
		PlayerConsumeItemEvent customEvent = new PlayerConsumeItemEvent(consumer,consumed);
		Bukkit.getServer().getPluginManager().callEvent(customEvent);
	}

	default int compareTo(EnchantmentCore o) {
		if (this.getGroup() == o.getGroup()) {
			return this.getStringRepresentation().compareTo(o.getStringRepresentation());
		}
		return this.getGroup().compareTo(o.getGroup());
	}
}
