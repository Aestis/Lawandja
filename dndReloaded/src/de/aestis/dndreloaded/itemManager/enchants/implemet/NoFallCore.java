package de.aestis.dndreloaded.itemManager.enchants.implemet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.NaturalDamageEffect;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;

public class NoFallCore implements NaturalDamageEffect{

	@Override
	public String getStringRepresentation() {
		return "No Fall";
	}

	@Override
	public String getLevelRepresentation(int level) {
		return "";
	}

	@Override
	public boolean isLevelPrefix() {
		return false;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		return ChatColor.DARK_RED;
	}

	@Override
	public boolean isInvisible() {
		return false;
	}

	@Override
	public EnchantmentGroup getGroup() {
		return EnchantmentGroup.SPECIAL;
	}

	@Override
	public void onNaturalDamage(CustomDamageEvent evt, Player p, int level, int amountOfActiveItems) {
		if (evt.getNaturalDamageReason() == DamageCause.FALL) {
			evt.setCancelled(true);
		}
	}

}
