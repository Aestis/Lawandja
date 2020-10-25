package de.aestis.dndreloaded.itemManager.enchants.implemet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.TimedEffect;

public class ForceFieldCore implements TimedEffect{

private boolean tick = false;
	
	@Override
	public String getStringRepresentation() {
		return "ForceField";
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
		return ChatColor.RED;
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
	public void onActivate(Player p, int level, int amountOfActiveItems) {
		if (tick) {
			for (Entity e:p.getNearbyEntities(7, 4, 7)) {
				if (e instanceof Player) {
					e.setVelocity(e.getLocation().toVector().subtract(p.getLocation().toVector()).multiply(0.1));
				}
			}
		}
		tick = !tick;
	}
}
