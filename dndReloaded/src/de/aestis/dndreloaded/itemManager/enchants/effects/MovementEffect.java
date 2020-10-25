package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface MovementEffect extends EnchantmentCore {

	/**
	 * called whenever a is moving and has a enchantment with this core equipped
	 * @param evt 
	 * @param p the player that is moving (distance moved != 0)
	 * @param level the level of the enchantment that should be applied
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onMove(PlayerMoveEvent evt, Player p, int level, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	default void onPlayerMove(PlayerMoveEvent evt) {
		Location to = evt.getTo();
		Location from = evt.getFrom();
		if (!(to.distanceSquared(from) > 0)) {
			return;
		}
		Player p = evt.getPlayer();
		int level;
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(p,CustomEnchantment.getByCore(this));
		if ((level = itemLevel.getValue2()) == 0) {
			return;
		}
		onMove(evt, p, level, itemLevel.getValue1());
	}
	
}
