package de.aestis.dndreloaded.itemManager.enchants.implemet;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.AttackEffect;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;

public class EffectWeaponsCore implements AttackEffect {

	private PotionEffectType type;
	private int amplifierPerLevel;
	private int baseAmplifier;
	private double timeAddedPerLevelSeconds;
	private double baseTimeSeconds;
	private ChatColor color;

	
	/**
	 * @param type the type of potioneffect to apply
	 * @param baseAmplifier the base level of the potioneffect
	 * @param baseTimeSeconds the base time the potion effect should be applied for
	 * @param addedAmplifierPerLevel the amount of levels that should be added per level of the enchantment
	 * @param timeAddedPerLevelSeconds the amount of seconds that should be added per level of the enchantment
	 * @param color the Color that the enchantment should have in the lore
	 */
	public EffectWeaponsCore(PotionEffectType type, int baseAmplifier, double baseTimeSeconds, int addedAmplifierPerLevel, double timeAddedPerLevelSeconds, ChatColor color) {
		this.type = type;
		this.baseAmplifier = baseAmplifier;
		this.amplifierPerLevel = addedAmplifierPerLevel;
		this.timeAddedPerLevelSeconds = timeAddedPerLevelSeconds;
		this.baseTimeSeconds = baseTimeSeconds;
		this.color = color;
	}
	
	public EffectWeaponsCore(PotionEffectType type, int baseAmplifier, double baseTimeSeconds, int amplifierPerLevel, double timeAddedPerLevelSeconds) {
		this(type,baseAmplifier,baseTimeSeconds,amplifierPerLevel,timeAddedPerLevelSeconds,ChatColor.YELLOW);
	}

	@Override
	public String getStringRepresentation() {
		return Arrays.stream(type.getName().replaceAll("_", " ").split("\\s+")).map(t -> t.substring(0, 1).toUpperCase() + t.substring(1).toLowerCase()).collect(Collectors.joining(" "));
	}

	@Override
	public String getLevelRepresentation(int level) {
		return ((int)(baseTimeSeconds + timeAddedPerLevelSeconds * level)) + "s";
	}

	@Override
	public boolean isLevelPrefix() {
		return false;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		return color;
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
		PotionEffect potion = new PotionEffect(type, (int) ((timeAddedPerLevelSeconds * level + baseTimeSeconds) * 20), Math.max(0, ((level - 1) * amplifierPerLevel) + baseAmplifier - 1));
		defender.addPotionEffect(potion);
	}

}
