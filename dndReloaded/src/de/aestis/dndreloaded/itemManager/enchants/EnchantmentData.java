package de.aestis.dndreloaded.itemManager.enchants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.aestis.dndreloaded.itemManager.events.ArmorEquipEvent;
import de.aestis.dndreloaded.itemManager.events.ArmorType;
import de.aestis.dndreloaded.itemManager.items.ItemFlag;
import de.aestis.dndreloaded.itemManager.items.ItemSet;

public class EnchantmentData implements Listener{

	private String player;
	private Map<ArmorType, ItemStack> equippedItems = new HashMap<>();
	private Map<ArmorType,Map<Enchantment,Integer>> itemLevel = new HashMap<>();
	private Map<Enchantment,Integer> setLevel = new HashMap<>();
	private Map<Enchantment,Integer> activeLevel = new HashMap<>();
	private Map<CustomEnchantment,Long> cooldowns = new HashMap<>();

	public EnchantmentData(Player p, Plugin plugin) {
		player = p.getName();

		loadEnchantments(p);
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public void unload() {
		HandlerList.unregisterAll(this);
	}

	public String getPlayer() {
		return player;
	}

	/**
	 * @return the cooldowns
	 */
	public Map<CustomEnchantment, Long> getCooldowns() {
		return cooldowns;
	}

	public Map<Enchantment, Integer> getEnchantmentLevel() {
		return activeLevel;
	}

	public Map<ArmorType, ItemStack> getEquippedItems() {
		return equippedItems;
	}

	public int getPiecesWithEnchantment(Enchantment e) {
		int amount = 0;
		for (Map<Enchantment,Integer> pieces : itemLevel.values()) {
			if (pieces.containsKey(e)) {
				amount++;
			}
		}
		return amount;
	}

	public void reloadArmor() {
		equippedItems = new HashMap<>();
		itemLevel = new HashMap<>();
		activeLevel = new HashMap<>();
		loadEnchantments(Bukkit.getPlayer(player));
	}

	public void unequipArmor(ArmorType type) {
		if (!equippedItems.containsKey(type)) {
			return;
		}
		equippedItems.remove(type);
		Map<Enchantment,Integer> oldEnchs = itemLevel.get(type);
		for (Enchantment ench:oldEnchs.keySet()) {
			Integer oldLevel = activeLevel.get(ench);
			Integer newLevel = oldLevel - oldEnchs.get(ench);
			if (newLevel < 1) {
				activeLevel.remove(ench);
			} else {
				activeLevel.put(ench, newLevel);
			}
		}
		itemLevel.remove(type);
	}

	public void equipArmor(ItemStack item) {
		if (item == null || item.getType() == Material.AIR) {
			return;
		}
		ArmorType type = ArmorType.matchType(item);
		if (type == null) {
			return;
		}
		if (equippedItems.containsKey(type)) {
			unequipArmor(type);
		}

		equippedItems.put(type, item);

		Map<Enchantment, Integer> enchs = item.getEnchantments();
		Map<Enchantment, Integer> newItemLevel = new HashMap<>();
		newItemLevel.putAll(enchs);
		itemLevel.put(type, newItemLevel);
		for (Enchantment ench:enchs.keySet()) {
			if (activeLevel.containsKey(ench)) {
				activeLevel.put(ench, activeLevel.get(ench) + enchs.get(ench));
			} else {
				activeLevel.put(ench, enchs.get(ench));
			}
		}
	}

	private void loadEnchantments(Player p) {
		for (ItemStack item:p.getInventory().getArmorContents()) {
			if (item == null ) {
				continue;
			}
			equipArmor(item);
		}
	}

	private void updateSetEnchants() {
		setLevel = new HashMap<Enchantment, Integer>();
		List<ItemStack> itemsLeft = new ArrayList<>(equippedItems.values());
		while (!itemsLeft.isEmpty()) {

			ItemStack current = itemsLeft.get(0);
			if (!isSetItem(current)) {
				itemsLeft.remove(0);
				continue;
			}
			int currentSetAmount = 0;

			ItemSet set = ItemFlag.get(current, ItemFlag.SET, null);
			for (int i = 1; i < itemsLeft.size(); i++) {
				if (set.equals(ItemFlag.get(itemsLeft.get(i), ItemFlag.SET, null))) {
					currentSetAmount++;
					itemsLeft.remove(i);
					i--;
				}
			}

			Map<Enchantment, Integer> enchants = set.getEnchantsOfLevel(currentSetAmount);
			for (Enchantment ench:enchants.keySet()) {
				if (setLevel.containsKey(ench)) {
					setLevel.put(ench, setLevel.get(ench) + enchants.get(ench));
				} else {
					setLevel.put(ench, enchants.get(ench));
				}
			}
		}
	}

	private boolean isSetItem(ItemStack item) {
		return ItemFlag.isSet(item, ItemFlag.SET);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerEquip(ArmorEquipEvent evt) {
		ItemStack newArmorPiece = evt.getNewArmorPiece();
		if (newArmorPiece == null || newArmorPiece.getType() == Material.AIR || newArmorPiece.getAmount() == 0) {
			unequipArmor(evt.getType());
			return;
		} else {
			equipArmor(newArmorPiece);
		}

		ItemStack oldArmorPiece = evt.getOldArmorPiece();
		if (isSetItem(newArmorPiece) || isSetItem(oldArmorPiece)) {
			updateSetEnchants();
		}
	}
}
