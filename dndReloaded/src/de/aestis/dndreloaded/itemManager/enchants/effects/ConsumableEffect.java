package de.aestis.dndreloaded.itemManager.enchants.effects;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.events.PlayerConsumeItemEvent;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;

public interface ConsumableEffect extends EnchantmentCore {

	public void onConsume(PlayerConsumeItemEvent evt, Player interactor, int level, int amountOfActiveItems);

	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	default void onPlayerInteract(PlayerConsumeItemEvent evt) {
		Player interactor = (Player) evt.getPlayer();
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(interactor,CustomEnchantment.getByCore(this));
		int level = itemLevel.getValue2();
		if (level == 0) {
			return;
		}
		onConsume(evt, interactor, level, itemLevel.getValue1());
	}
}
