package de.aestis.dndreloaded.itemManager.enchants;

import java.util.HashMap;
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

public class EnchantmentData implements Listener{

	private String player;
	private Map<ArmorType, ItemStack> equippedItems = new HashMap<>();
	private Map<ArmorType,Map<Enchantment,Integer>> itemLevel = new HashMap<>();
	private Map<Enchantment,Integer> setLevel = new HashMap<>();
	private Map<Enchantment,Integer> activeLevel = new HashMap<>();
	
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
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerEquip(ArmorEquipEvent evt) {
		if (evt.getNewArmorPiece() == null) {
			unequipArmor(evt.getType());
			return;
		} else {
			equipArmor(evt.getNewArmorPiece());
		}
	}
}
