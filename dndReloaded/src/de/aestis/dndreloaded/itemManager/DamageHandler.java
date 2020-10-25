package de.aestis.dndreloaded.itemManager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent.DamageType;

public class DamageHandler implements Listener {

	/**
	 * @param damage a map containing the amount of damage that is dealt per type
	 * @param damager the Entity that is dealing the damage
	 * @param damaged the Entity that is taking damage
	 * @return the total amount of damage done after all modifiers were applied
	 */
	public static CustomDamageEvent callDamage(Map<DamageType,Double> damage, LivingEntity damager, LivingEntity damaged) {
		return callDamage(damage, damager, damaged, DamageReason.OTHER);
	}

	/**
	 * @param damage a map containing the amount of damage that is dealt per type
	 * @param damager the Entity that is dealing the damage
	 * @param damaged the Entity that is taking damage
	 * @param reason the reason why the damage is dealt
	 * @return the total amount of damage done after all modifiers were applied
	 */
	public static CustomDamageEvent callDamage(Map<DamageType,Double> damage, LivingEntity damager, LivingEntity damaged, DamageReason reason) {
		return callDamage(damage, damager, damaged, reason, false);
	}
	
	/**
	 * @param damage a map containing the amount of damage that is dealt per type
	 * @param damager the Entity that is dealing the damage
	 * @param damaged the Entity that is taking damage
	 * @param reason the reason why the damage is dealt
	 * @param calledByEvent if the method was called by a event handler
	 * @return the total amount of damage done after all modifiers were applied
	 */
	private static CustomDamageEvent callDamage(Map<DamageType,Double> damage, LivingEntity damager, LivingEntity damaged, DamageReason reason, boolean calledByEvent) {
		CustomDamageEvent customEvent = new CustomDamageEvent(damager,damaged, damage, reason);
		Bukkit.getServer().getPluginManager().callEvent(customEvent);
		if(customEvent.isCancelled()){
			return customEvent;
		}
		if (!customEvent.isKill() || !calledByEvent) {
			double newHealth = damaged.getHealth() - customEvent.getFinalDamage();
			newHealth = Math.min(newHealth, damaged.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			newHealth = Math.max(newHealth, 0);
			damaged.setLastDamageCause(new EntityDamageByEntityEvent(damager, damaged, DamageCause.CUSTOM, 0));
			damaged.setHealth(newHealth);
			damaged.playEffect(EntityEffect.HURT);
			return customEvent;
		} else {
			damaged.setHealth(0.1);
			return customEvent;
		}
	}

	/**
	 * @param damage a map containing the amount of damage that is dealt per type
	 * @param damaged the Entity that is taking damage
	 * @return the total amount of damage done after all modifiers were applied
	 */
	public static CustomDamageEvent callNaturalDamage(Map<DamageType,Double> damage, LivingEntity damaged) {
		return callNaturalDamage(damage, damaged, DamageCause.CUSTOM);
	}

	/**
	 * @param damage a map containing the amount of damage that is dealt per type
	 * @param damaged the Entity that is taking damage
	 * @param reason the reason why the damage is dealt
	 * @return the total amount of damage done after all modifiers were applied
	 */
	public static CustomDamageEvent callNaturalDamage(Map<DamageType,Double> damage, LivingEntity damaged, DamageCause reason) {
		return callNaturalDamage(damage, damaged, reason, false);
	}
	
	/**
	 * @param damage a map containing the amount of damage that is dealt per type
	 * @param damaged the Entity that is taking damage
	 * @param reason the reason why the damage is dealt
	 * @param calledByEvent if the method was called by a event handler
	 * @return the total amount of damage done after all modifiers were applied
	 */
	private static CustomDamageEvent callNaturalDamage(Map<DamageType,Double> damage, LivingEntity damaged, DamageCause reason, boolean calledByEvent) {
		CustomDamageEvent customEvent = new CustomDamageEvent(damaged, damage, reason);
		Bukkit.getServer().getPluginManager().callEvent(customEvent);
		if(customEvent.isCancelled()){
			return customEvent;
		}
		if (!customEvent.isKill() || !calledByEvent) {
			double newHealth = damaged.getHealth() - customEvent.getFinalDamage();
			newHealth = Math.min(newHealth, damaged.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			newHealth = Math.max(newHealth, 0);
			damaged.setLastDamageCause(new EntityDamageEvent(damaged, DamageCause.CUSTOM, 0));
			damaged.setHealth(newHealth);
			damaged.playEffect(EntityEffect.HURT);
			return customEvent;
		} else {
			damaged.setHealth(0.1);
			return customEvent;
		}
	}

	/**
	 * Cancels all Entity Damage events and calls custom ones. If getCause() == DamageCause.CUSTOM it will adjust the health of the reciever.
	 * @param evt
	 */
	@EventHandler(ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent evt) {
		CustomDamageEvent resultingDamage = null;
		if (evt instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent evtCast = (EntityDamageByEntityEvent) evt;
			
			if (!(evtCast.getEntity() instanceof LivingEntity)) {
				return;
			}
			
			Map<DamageType, Double> damage = new HashMap<>();
			damage.put(DamageType.VANILLA, evt.getDamage());
			
			if (evtCast.getDamager() instanceof Player) {
				resultingDamage = callDamage(damage, (LivingEntity) evtCast.getDamager(), (LivingEntity) evt.getEntity(), DamageReason.PLAYER, true);
			} else if (evtCast.getDamager() instanceof Projectile){
				Projectile damager = (Projectile) evtCast.getDamager();
				if (damager.getShooter() instanceof LivingEntity) {
					resultingDamage = callDamage(damage, (LivingEntity) damager.getShooter(), (LivingEntity) evt.getEntity(), DamageReason.ENTITY, true);
				}
			} else if (evtCast.getDamager() instanceof LightningStrike){
				resultingDamage = callNaturalDamage(damage, (LivingEntity) evt.getEntity(), DamageCause.LIGHTNING, true);
			} else {
				resultingDamage = callDamage(damage, (LivingEntity) evtCast.getDamager(), (LivingEntity) evt.getEntity(), DamageReason.ENTITY, true);
			}
		} else {
			if (evt.getEntity() instanceof LivingEntity) {
				Map<DamageType, Double> damage = new HashMap<>();
				damage.put(DamageType.VANILLA, evt.getFinalDamage());
				resultingDamage = callNaturalDamage(damage, (LivingEntity) evt.getEntity(), evt.getCause(), true);
			} else {
				//dont know when this should be possible but dont do anything then
				return;
			}
		}
		if (!resultingDamage.isCancelled() && resultingDamage.isKill()) {
			evt.setDamage(100000);
		} else {
			evt.setDamage(0);
		}
	}
}
