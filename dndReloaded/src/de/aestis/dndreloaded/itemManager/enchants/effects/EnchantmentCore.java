package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

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
	 * @return true if the enchantment is currently in cooldown for the specified player
	 */
	default boolean isInCooldown(Player p) {
		//TODO add cooldown lookup
		return false;
	}
	
	/**
	 * sets a cooldown for this enchantment for the specified time and player
	 * @param p the player to set the cooldown for
	 * @param milisEndCooldown the miliseconds that the cooldown ends at
	 */
	default void setCooldown(Player p, int secondsCooldown) {
		//TODO add cooldown setting
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
