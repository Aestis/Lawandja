package de.aestis.dndreloaded.itemManager.enchants.implemet;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.AttackEffect;
import de.aestis.dndreloaded.itemManager.enchants.effects.EnchantmentCore;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent.DamageType;

public class AttackRawCore implements AttackEffect {

	private DamageType type;
	
	public AttackRawCore(DamageType type) {
		this.type = type;
	}
	
	@Override
	public String getStringRepresentation() {
		return Arrays.stream(type.name().replaceAll("_", " ").split("\\s+")).map(t -> t.substring(0, 1).toUpperCase() + t.substring(1).toLowerCase()).collect(Collectors.joining(" ")) + " RawAttack";
	}

	@Override
	public String getLevelRepresentation(int level) {
		return "+" + (level * 0.5);
	}

	@Override
	public boolean isLevelPrefix() {
		return true;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		return ChatColor.GOLD;
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
		evt.setDamageByType(type, level * 0.5);
	}

	@Override
	public int compareTo(EnchantmentCore o) {
		int rtn = AttackEffect.super.compareTo(o);
		if (!(o instanceof AttackRawCore) || rtn != 0) {
			return rtn;
		} else {
			return this.type.compareTo( ((AttackRawCore)o).type );
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			if (!(obj instanceof AttackRawCore)) {
				return false;
			} else {
				AttackRawCore other = (AttackRawCore) obj;
				return this.type == other.type;
			}
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getClass().getName(),type);
	}
}
