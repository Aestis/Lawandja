package de.aestis.dndreloaded.itemManager.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.enchants.EnchantmentData;
import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemFlag;
import de.aestis.dndreloaded.itemManager.items.ItemGroup;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;
import oxolotel.utils.DoubleWrapper;

public class PlayerInventoryHelper {

	private PlayerInventoryHelper() {
	}
	
	/**
	 * @param p the player to get the items from
	 * @return all items the player has equipped as armor
	 */
	public static ItemStack[] getEquippedItems(Player p) {
		return  p.getInventory().getArmorContents();
	}

	/**
	 * @param p the player to get the items from
	 * @return all items the player has equipped as armor or in his hands
	 */
	public static ItemStack[] getActiveItems(Player p) {
		ItemStack[] items = new ItemStack[6];
		PlayerInventory inv = p.getInventory();
		ItemStack[] contents = inv.getArmorContents();
		for (int i = 0; i<4;i++) {
			items[i] = contents[i];
		}
		items[4] = inv.getItemInMainHand();
		items[5] = inv.getItemInOffHand();
		return items;
	}

	/**
	 * @param p the player to get the items from
	 * @return all items the player has equipped in his hands
	 */
	public static ItemStack[] getItemsinHands(Player p) {
		PlayerInventory inv = p.getInventory();
		return new ItemStack[] {inv.getItemInMainHand(),inv.getItemInOffHand()};
	}
	
	/**
	 * @param p the player to get the enchantment level of the CustomEnchantment of 
	 * @param ench the enchantment to calculate the level of
	 * @return the level of the enchantment in the players hands
	 */
	public static DoubleWrapper<List<ItemStack>, Integer> calculateLevelHands(Player p, CustomEnchantment ench) {
		ItemStack[] items = PlayerInventoryHelper.getItemsinHands(p);
		List<ItemStack> usedItems = new ArrayList<ItemStack>();
		int level = 0;
		for (ItemStack item:items) {
			if (item == null) {
				continue;
			}
			Map<Enchantment, Integer> enchants = item.getEnchantments();
			if (enchants.containsKey(ench.getSpigotEnchantment())) {
				level = level + enchants.get(ench.getSpigotEnchantment());
				usedItems.add(item);
			}
		}
		return new DoubleWrapper<>(usedItems, level);
	}

	/**
	 * @param p the player to get the enchantment level of the CustomEnchantment of
	 * @param ench the enchantment to calculate the level of
	 * @return the level of the enchantment in the players armor
	 */
	public static DoubleWrapper<List<ItemStack>, Integer> calculateLevelArmor(Player p, CustomEnchantment ench) {
		ItemStack[] items = PlayerInventoryHelper.getEquippedItems(p);
		List<ItemStack> usedItems = new ArrayList<ItemStack>();
		int level = 0;
		for (ItemStack item:items) {
			if (item == null) {
				continue;
			}
			Map<Enchantment, Integer> enchants = item.getEnchantments();
			if (enchants.containsKey(ench.getSpigotEnchantment())) {
				level = level + enchants.get(ench.getSpigotEnchantment());
				usedItems.add(item);
			}
		}
		return new DoubleWrapper<>(usedItems, level);
	}
	
	/**
	 * @param p the player to get the enchantment level of the CustomEnchantment of
	 * @param ench the enchantment to calculate the level of
	 * @return the number of items in value 1 the level of the enchantment in all active slots of the player in value 2
	 */
	public static DoubleWrapper<Integer, Integer> calculateLevelAll(Player p, CustomEnchantment ench) {
		EnchantmentData enchantmentData = Main.instance.Players.get(p).getEnchantmentData();
		Map<Enchantment,Integer> enchants = enchantmentData.getEnchantmentLevel();
		
		int level = 0;
		int pieces = 0;
		if (enchants.containsKey(ench.getSpigotEnchantment())) {
			level = enchants.get(ench.getSpigotEnchantment());
			pieces = enchantmentData.getPiecesWithEnchantment(ench.getSpigotEnchantment());
		}
		
		DoubleWrapper<List<ItemStack>, Integer> calculateLevelHands = calculateLevelHands(p, ench);
		pieces += calculateLevelHands.getValue1().size();
		level += calculateLevelHands.getValue2();
		return new DoubleWrapper<>(pieces, level);
	}
	
	/**
	 * @param item the item to get the given enchantment level of
	 * @param ench the enchantment to calculate the level of
	 * @return the level of the enchantment in the players armor
	 */
	public static int calculateLevel(ItemStack item, CustomEnchantment ench) {
		int level = 0;
		Map<Enchantment, Integer> enchants = item.getEnchantments();
		if (enchants.containsKey(ench.getSpigotEnchantment())) {
			level = enchants.get(ench.getSpigotEnchantment());
		}
		return level;
	}
	
	public static void fixItems(Player p) {
		ItemStack[] armor = p.getInventory().getArmorContents();
		ItemStack[] inventory = p.getInventory().getContents();
		ItemStack[] extraContent = p.getInventory().getExtraContents();
		
		for (ItemStack item: armor) {
			if (item == null || !ItemFlag.isSet(item, ItemFlag.ITEM_ID)) {
				continue;
			}
			SimpleItem custom = CustomItem.loadCustomItem(item, true);
			if (custom.getItemID().getGroup() == ItemGroup.TEST) {
				custom.setAmount(0);
			}
		}
		for (ItemStack item: inventory) {
			if (item == null || !ItemFlag.isSet(item, ItemFlag.ITEM_ID)) {
				continue;
			}
			SimpleItem custom = CustomItem.loadCustomItem(item, true);
			if (custom.getItemID().getGroup() == ItemGroup.TEST) {
				custom.setAmount(0);
			}
		}
		for (ItemStack item: extraContent) {
			if (item == null || !ItemFlag.isSet(item, ItemFlag.ITEM_ID)) {
				continue;
			}
			SimpleItem custom = CustomItem.loadCustomItem(item, true);
			if (custom.getItemID().getGroup() == ItemGroup.TEST) {
				custom.setAmount(0);
			}
		}
	}

}
