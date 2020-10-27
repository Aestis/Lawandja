package de.aestis.dndreloaded.itemManager.enchants.effects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.events.ArmorEquipEvent;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;
import oxolotel.utils.DoubleWrapper;


/**
 * should only be used on Armor items
 */
public interface EquipEffect extends EnchantmentCore {

	/**
	 * called whenever a player equipping or unequipping a piece of armor and has a enchantment with this core equipped
	 * @param evt 
	 * @param p the player equipping the armor
	 * @param newArmor the ItemStack that is newly equipped
	 * @param level the level of the enchantment after the piece of armor is swapped
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	public void onEquipChange(ArmorEquipEvent evt, Player p, ItemStack newArmor, ItemStack oldArmor, int oldLevel, int newLevel, int amountOfActiveItems);
	
	/**
	 * Executes The effects of the Enchantment on the fitting event
	 * @param evt the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	default void onPlayerEquip(ArmorEquipEvent evt) {
		Player p = evt.getPlayer();
		CustomEnchantment ench = CustomEnchantment.getByCore(this);
		DoubleWrapper<Integer, Integer> itemLevel = PlayerInventoryHelper.calculateLevelAll(p, ench);
		int levelAll = itemLevel.getValue2();
		int levelOld = evt.getOldArmorPiece() == null ? 0 : PlayerInventoryHelper.calculateLevel(evt.getOldArmorPiece(), ench);
		int levelNew = evt.getNewArmorPiece() == null ? 0 : PlayerInventoryHelper.calculateLevel(evt.getNewArmorPiece(), ench);
		
		if (levelOld == levelNew) {
			return;
		}
		
		onEquipChange(evt, p, evt.getNewArmorPiece() , evt.getOldArmorPiece() , levelAll - levelNew + levelOld, levelAll, itemLevel.getValue1());
		
	}
	
}
