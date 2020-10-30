package de.aestis.dndreloaded.itemManager.enchants.implemet;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.DefenceEffect;
import de.aestis.dndreloaded.itemManager.enchants.effects.EnchantmentCore;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent.DamageType;

public class DefenceCore implements DefenceEffect {

	private DamageType type;
	
	public DefenceCore(DamageType type) {
		this.type = type;
	}
	
	@Override
	public String getStringRepresentation() {
		return Arrays.stream(type.name().replaceAll("_", " ").split("\\s+")).map(t -> t.substring(0, 1).toUpperCase() + t.substring(1).toLowerCase()).collect(Collectors.joining(" ")) + " Defence";
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
		return EnchantmentGroup.DAMAGE;
	}

	@Override
	public void onDefence(CustomDamageEvent evt, Player defender, LivingEntity attacker, int level, int amountOfActiveItems) {
		evt.setDamageReduction(type, level * 0.5);
	}

	@Override
	public int compareTo(EnchantmentCore o) {
		int rtn = DefenceEffect.super.compareTo(o);
		if (!(o instanceof DefenceCore) || rtn != 0) {
			return rtn;
		} else {
			return this.type.compareTo( ((DefenceCore)o).type );
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			if (!(obj instanceof DefenceCore)) {
				return false;
			} else {
				DefenceCore other = (DefenceCore) obj;
				return this.type == other.type;
			}
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getClass().getName(),type);
	}
}
