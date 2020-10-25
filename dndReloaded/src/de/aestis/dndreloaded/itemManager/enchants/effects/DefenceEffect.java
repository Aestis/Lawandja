package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import de.aestis.dndreloaded.itemManager.DamageReason;
import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface DefenceEffect extends EnchantmentCore {

	/**
	 * called whenever a player is defending against a hit and has a enchantment with this core equipped
	 * @param evt 
	 * @param defender the player that is defending
	 * @param attacker the Entity that is attacking
	 * @param level the level of the enchantment that should be applied
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onDefence(CustomDamageEvent evt, Player defender, LivingEntity attacker, int level, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	default void onPlayerDefence(CustomDamageEvent evt) {
		if (!(evt.getDamaged() instanceof Player) || (evt.getDamageReason() != DamageReason.ENTITY && evt.getDamageReason() != DamageReason.PLAYER) ) {
			return;
		}
		LivingEntity damager = evt.getDamager();
		Player damaged = (Player) evt.getDamaged();
		int level;
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(damaged,CustomEnchantment.getByCore(this));
		if ((level = itemLevel.getValue2()) == 0) {
			return;
		}
		onDefence(evt, damaged, damager, level, itemLevel.getValue1());
	}
		
}
