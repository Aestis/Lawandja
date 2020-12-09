package de.aestis.dndreloaded.itemManager.enchants.implemet;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.AttackEffect;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;

public class ParalizeCore implements AttackEffect{

	@Override
	public String getStringRepresentation() {
		return "Lähmung";
	}

	@Override
	public String getLevelRepresentation(int level) {
		return toRoman(level);
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
		return EnchantmentGroup.NORMAL;
	}

	@Override
	public void onAttack(CustomDamageEvent evt, Player attacker, LivingEntity defender, int level, int amountOfActiveItems) {
		defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 20, 7, true, true));
		defender.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, level * 20, 128, true, true));
	}

}
