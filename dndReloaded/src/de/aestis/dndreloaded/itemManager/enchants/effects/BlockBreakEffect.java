package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface BlockBreakEffect extends EnchantmentCore{

	/**
	 * called whenever a player breaks a block and has a enchantment with this core equipped
	 * @param evt 
	 * @param breaker the player that is breaking a block
	 * @param brokenBlock the block that is being broken
	 * @param level the level of the enchantment that should be applied
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onBlockBreak(BlockBreakEvent evt, Player breaker, Block brokenBlock, int level, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	default void onPlayerEquip(BlockBreakEvent evt) {
		Player p = evt.getPlayer();
		int level;
		DoubleWrapper<Integer, Integer> itemlevel = PlayerInventoryHelper.calculateLevelAll(p,CustomEnchantment.getByCore(this));
		if ((level = itemlevel.getValue2()) == 0) {
			return;
		}
		onBlockBreak(evt,p, evt.getBlock(), level, itemlevel.getValue1());
	}
	
}
