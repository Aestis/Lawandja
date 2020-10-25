package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface SneakEffect extends EnchantmentCore {

	public void onSneak(PlayerToggleSneakEvent evt, Player sneakingPlayer, boolean newStatus, int level, int amountOfActiveItems);

	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	default void onToggleSneakEvent(PlayerToggleSneakEvent evt) {
		Player p = evt.getPlayer();
		int level;
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(p,CustomEnchantment.getByCore(this));
		if ((level = itemLevel.getValue2()) == 0) {
			return;
		}
		onSneak(evt, p, evt.isSneaking(), level, itemLevel.getValue1());
	}
}
