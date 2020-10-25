package de.aestis.dndreloaded.itemManager.enchants.implemet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.KillEffect;

public class LootQualityCore implements KillEffect {

	@Override
	public String getStringRepresentation() {
		return "Loot Quality";
	}

	@Override
	public String getLevelRepresentation(int level) {
		return "??";
	}

	@Override
	public boolean isLevelPrefix() {
		return false;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		return ChatColor.WHITE;
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
		//TODO add Loottable integration
	}

}
