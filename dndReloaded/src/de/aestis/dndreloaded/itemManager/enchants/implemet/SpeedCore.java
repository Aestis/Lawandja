package de.aestis.dndreloaded.itemManager.enchants.implemet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.EquipEffect;
import de.aestis.dndreloaded.itemManager.events.ArmorEquipEvent;
import de.aestis.dndreloaded.itemManager.helper.PlayerInventoryHelper;

public class SpeedCore implements EquipEffect{

	@Override
	public String getStringRepresentation() {
		return "Bewegungsgeschwindigkeit";
	}

	@Override
	public String getLevelRepresentation(int level) {
		if (level == 100) {
			return "+0%";
		} else if (level < 100) {
			return "-" + (100 - level) + "%";
		} else {
			return "+" + level + "%";
		}
	}

	@Override
	public boolean isLevelPrefix() {
		return true;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		if (level < 100) {
			return ChatColor.RED;
		} else {
			return ChatColor.GREEN;
		}
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
	public void onEquipChange(ArmorEquipEvent evt, Player p, ItemStack newArmor, ItemStack oldArmor, int oldLevel, int newLevel, int amountOfActiveItems) {
		newLevel = newLevel/Math.max(amountOfActiveItems, 1);
		if (newLevel == 0) {
			p.setWalkSpeed(0.2f);
		} else if (newLevel < 100) {
			p.setWalkSpeed(0.2f * (newLevel/100f));
		} else {
			p.setWalkSpeed(Math.max(0.2f * (newLevel/100f), 1));
		}
	}

	
}
