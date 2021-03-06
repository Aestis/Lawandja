package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface DeathEffect extends EnchantmentCore {

	/**
	 * called whenever a player is dying and has a enchantment with this core equipped
	 * @param evt 
	 * @param p the player that is dying
	 * @param level the level of the enchantment that should be applied
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onDeath(PlayerDeathEvent evt, Player p, int level, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	default
	void onPlayerDeath(PlayerDeathEvent evt) {
		Player p = evt.getEntity();
		int level;
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(p,CustomEnchantment.getByCore(this));
		if ((level = itemLevel.getValue2()) == 0) {
			return;
		}
		onDeath(evt, p, level, itemLevel.getValue1());
	}
	
}
