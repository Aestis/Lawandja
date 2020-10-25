package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface KillEffect extends EnchantmentCore {

	/**
	 * called whenever a player kills a entity and has a enchantment with this core equipped
	 * @param evt 
	 * @param killer the player that is attacking and killing
	 * @param killer the killed entity
	 * @param level the level of the enchantment that should be applied
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onKill(EntityDeathEvent evt, Player killer, Entity killed, int level, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	default void onPlayerKill(EntityDeathEvent evt) {
		EntityDamageEvent lastDamage = evt.getEntity().getLastDamageCause();
		if (lastDamage instanceof EntityDamageByEntityEvent) {
			Entity damager = ((EntityDamageByEntityEvent) lastDamage).getDamager();
			if (damager instanceof Player) {
				Player killer = (Player) damager;
				int level;
				DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(killer, CustomEnchantment.getByCore(this));
				if ((level = itemLevel.getValue2()) == 0) {
					return;
				}
				onKill(evt, killer, evt.getEntity(), level, itemLevel.getValue1());
			}
			
		}
		
	}
	
}
