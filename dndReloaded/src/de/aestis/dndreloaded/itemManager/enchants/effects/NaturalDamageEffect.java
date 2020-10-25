package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import de.aestis.dndreloaded.itemManager.DamageReason;
import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface NaturalDamageEffect extends EnchantmentCore {

	/**
	 * called whenever a player is taking natural damage and has a enchantment with this core equipped
	 * @param evt 
	 * @param p the player that is taking damage
	 * @param level the level of the enchantment that should be applied
	 * @return a enchantment result reflecting the changes that should be made to the corresponding event
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onNaturalDamage(CustomDamageEvent evt, Player p, int level, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	default void onNaturalDamage(CustomDamageEvent evt) {
		if (!(evt.getDamaged() instanceof Player) || evt.getDamageReason() != DamageReason.NATURAL) {
			return;
		}
		Player damaged = null;
		if (evt.getDamaged() instanceof Player) {
			damaged = (Player) evt.getDamaged();
		} else {
			return;
		}
		int level;
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(damaged,CustomEnchantment.getByCore(this));
		if ((level = itemLevel.getValue2()) == 0) {
			return;
		}
		onNaturalDamage(evt, damaged, level, itemLevel.getValue1());
	}
}
