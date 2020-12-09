package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface JumpEffect extends EnchantmentCore {

	/**
	 * called whenever a Player is jumping and has a enchantment with this core equipped
	 * @param evt 
	 * @param p the player that is jumping
	 * @param level the level of the enchantment that should be applied
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onJump(PlayerJumpEvent evt, Player p, int level, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Enchantment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	default void onPlayerMove(PlayerJumpEvent evt) {
		Player p = evt.getPlayer();
		int level;
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(p, CustomEnchantment.getByCore(this));
		if ((level = itemLevel.getValue2()) == 0) {
			return;
		}
		onJump(evt, p, level, itemLevel.getValue1());
	}
	
}
