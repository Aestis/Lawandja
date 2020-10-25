package de.aestis.dndreloaded.itemManager.enchants.effects;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import de.aestis.dndreloaded.itemManager.items.AttackSpeed;
import de.aestis.dndreloaded.itemManager.items.ItemFlag;
import oxolotel.utils.DoubleWrapper;

public interface InteractEffect extends EnchantmentCore {

	public void onInteract(PlayerInteractEvent evt, Player interactor, int level, ItemStack itemstacks, int amountOfActiveItems);
	
	public void onInteractEntity(PlayerInteractEntityEvent evt, Player interactor, int level, ItemStack itemstacks, int amountOfActiveItems);
	
	/**
	 * @param p the Player to get the target Location from
	 * @param maxDistance the maximum distance to look for a block (maximum of 120)
	 * @return the Location found or a location in maxDistance in viewing direction from the player
	 */
	default Location getLookedAtLocation(Player p, int maxDistance) {
		return p.getTargetBlock(null, maxDistance).getLocation();
	}
	
	/**
	 * @param item the item to check if it is in cooldown
	 * @return true if the item is currently in cooldown false otherwise
	 */
	default boolean isItemInCooldown(ItemStack item) {
		if (ItemFlag.get(item, ItemFlag.COOLDOWN, Long.valueOf(0)) > System.currentTimeMillis()) {
			return true;
		} else {
			ItemFlag.unSet(item, ItemFlag.COOLDOWN);
			return false;
		}
	}
		
	/**
	 * sets the given cooldown to the item
	 * @param item the item to set the cooldown on
	 * @param cooldownSeconds the cooldown to be added to the item in seconds
	 */
	default void setItemCooldown(ItemStack item, int cooldownSeconds) {
		ItemFlag.set(item, ItemFlag.COOLDOWN, System.currentTimeMillis() + cooldownSeconds * 1000);
	}
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGH)
	default void onPlayerInteract(PlayerInteractEvent evt) {
		Player interactor = (Player) evt.getPlayer();
		ItemStack item = interactor.getInventory().getItemInMainHand();
		if (item == null || item.getAmount() == 0 || item.getType() == Material.AIR) {
			return;
		}
		if (interactor.getCooldown(item.getType()) == ItemFlag.get(item, ItemFlag.ATTACK_SPEED, AttackSpeed.SUPER_FAST).getCooldownTicks()) {
			DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(interactor,CustomEnchantment.getByCore(this));
			int level = itemLevel.getValue2();
			if (level == 0) {
				return;
			}
			onInteract(evt, interactor, level, item, itemLevel.getValue1());
		}
	}
	
	/**
	 * Executes The effects of the Encahntment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGH)
	default void onPlayerInteractEntity(PlayerInteractEntityEvent evt) {
		Player interactor = (Player) evt.getPlayer();
		ItemStack item = interactor.getInventory().getItemInMainHand();
		if (item == null || item.getAmount() == 0 || item.getType() == Material.AIR) {
			return;
		}
		if (interactor.getCooldown(item.getType()) == ItemFlag.get(item, ItemFlag.ATTACK_SPEED, AttackSpeed.SUPER_FAST).getCooldownTicks()) {
			DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(interactor,CustomEnchantment.getByCore(this));
			int level = itemLevel.getValue2();
			if (level == 0) {
				return;
			}
			onInteractEntity(evt, interactor, level, item, itemLevel.getValue1());
		}
	}
}
