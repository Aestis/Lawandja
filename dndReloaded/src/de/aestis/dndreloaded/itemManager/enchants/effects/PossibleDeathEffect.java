package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import de.aestis.dndreloaded.itemManager.DamageReason;
import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface PossibleDeathEffect extends EnchantmentCore {

	/**
	 * called whenever a player is possibly dying and has a enchantment with this core equipped
	 * @param evt 
	 * @param dyingPlayer the player that is possibly dying
	 * @param attacker the attacking Entity or null if none present
	 * @param reason the reason why the player is taking damage
	 * @param level the level of the enchantment that should be applied
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onPossibleDeath(CustomDamageEvent evt, Player dyingPlayer, Entity attacker, DamageReason reason,  int level, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	default void onPlayerPossibleDeath(CustomDamageEvent evt) {
		if (!(evt.getDamaged() instanceof Player) || !evt.isKill()) {
			return;
		}
		Player damaged = null;
		if (evt.isKill()) {
			damaged = (Player) evt.getDamaged();
		} else {
			return;
		}
		int level;
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(damaged,CustomEnchantment.getByCore(this));
		if ((level = itemLevel.getValue2()) == 0) {
			return;
		}
		onPossibleDeath(evt, damaged, evt.getDamager(), evt.getDamageReason() , level, itemLevel.getValue1());
	}
	
}
