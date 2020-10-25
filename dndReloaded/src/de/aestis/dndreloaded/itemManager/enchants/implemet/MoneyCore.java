package de.aestis.dndreloaded.itemManager.enchants.implemet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.KillEffect;
import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemManager;

public class MoneyCore implements KillEffect {

	@Override
	public String getStringRepresentation() {
		return "Diebstahl";
	}

	@Override
	public String getLevelRepresentation(int level) {
		return "+" + (level * 5) + "%";
	}

	@Override
	public boolean isLevelPrefix() {
		return true;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		return ChatColor.BLACK;
	}

	@Override
	public boolean isInvisible() {
		return false;
	}

	@Override
	public EnchantmentGroup getGroup() {
		return EnchantmentGroup.NORMAL;
	}

	@Override
	public void onKill(EntityDeathEvent evt, Player killer, Entity killed, int level, int amountOfActiveItems) {
		if ((int)(Math.random() * 100) > 100 - 5 * level) {
			for (CustomItem item:ItemManager.getInstance().createMoney(2)) {
				killer.getInventory().addItem(item.getSpigotItem());
			}
		}
	}
	
}
