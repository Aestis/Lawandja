package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface AttackEffect extends EnchantmentCore {

	/**
	 * called whenever a player attacks a entity and has a enchantment with this core equipped
	 * @param evt 
	 * @param attacker the player that is attacking
	 * @param defender the defending entity
	 * @param level the level of the enchantment that should be applied
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onAttack(CustomDamageEvent evt, Player attacker, LivingEntity defender, int level, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public default void onPlayerAttack(CustomDamageEvent evt) {
		if (!(evt.getDamager() instanceof Player)) {
			return;
		}
		Player damager = (Player) evt.getDamager();
		LivingEntity damaged = evt.getDamaged();
		int level;
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(damager,CustomEnchantment.getByCore(this));
		if ((level = itemLevel.getValue2()) == 0) {
			return;
		}
		onAttack(evt, damager, damaged, level, itemLevel.getValue1());
	}
	
}
