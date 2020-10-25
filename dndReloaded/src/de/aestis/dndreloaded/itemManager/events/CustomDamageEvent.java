package de.aestis.dndreloaded.itemManager.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.aestis.dndreloaded.itemManager.DamageReason;

public class CustomDamageEvent extends Event implements Cancellable{

	public enum DamageType{
		ALL, // should only be used in multipliers
		VANILLA, // used for all Vanilla damage
		TEST
	}
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private Map<DamageType,Double> damage;
	private Map<DamageType,Double> damageMultiplier = new HashMap<CustomDamageEvent.DamageType, Double>();
	private Map<DamageType,Double> damageReduction = new HashMap<CustomDamageEvent.DamageType, Double>();
	private boolean updateDamage = true;
	private boolean updateKill = true;
	private boolean isKill = false;
	private double finalDamage = 0;
	private LivingEntity damager;
	private LivingEntity damaged;
	private double damagedHp = -1;
	private DamageReason reason;
	private DamageCause naturalReason;
	
	private CustomDamageEvent(LivingEntity damager, LivingEntity damaged, Map<DamageType,Double> damage, DamageReason reason, DamageCause naturalReason) {
		this.damagedHp = damaged.getHealth();
		this.damager = damager;
		this.damaged = damaged;
		this.damage = damage;
		if (reason != null) {
			this.reason = reason;
			this.naturalReason = DamageCause.ENTITY_ATTACK;
		}
		if (naturalReason != null) {
			this.naturalReason = naturalReason;
			this.reason = DamageReason.NATURAL;
		}
		
		
	}
	
	public CustomDamageEvent(LivingEntity damager, LivingEntity damaged, DamageType type, Double damage, DamageReason reason) {
		this(damager, damaged, new HashMap<>(), reason, null);
		setDamageByType(type, damage);
	}
	
	public CustomDamageEvent(LivingEntity damager, LivingEntity damaged, Map<DamageType,Double> damage, DamageReason reason) {
		this(damager, damaged, damage, reason,null);
	}
	
	public CustomDamageEvent(LivingEntity damaged, DamageType type, Double damage, DamageCause reason) {
		this(null, damaged, new HashMap<>(), null, reason);
		setDamageByType(type, damage);
	}
	
	public CustomDamageEvent(LivingEntity damaged, Map<DamageType,Double> damage, DamageCause reason) {
		this(null, damaged, damage, null, reason);
	}

	/**
	 * Gets a list of handlers handling this event.
	 *
	 * @return A list of handlers handling this event.
	 */
	public static HandlerList getHandlerList(){
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
		
	}

	public Map<DamageType,Double> getDamage(){
		return damage;
	}
	
	public double getDamageByType(DamageType type){
		return damage.getOrDefault(type,0d);
	}
	
	public void setDamageByType(DamageType type, double damage){
		updateDamage = true;
		updateKill = true;
		this.damage.put(type,damage);
	}
	
	public void setDamageMultiplierByType(DamageType type, double damageMultiplier) {
		updateDamage = true;
		updateKill = true;
		this.damageMultiplier.put(type, damageMultiplier);
	}
	
	public Map<DamageType,Double> getDamageMultiplier() {
		return damageMultiplier;
	}
	
	public Double getDamageMultiplierByType(DamageType type){
		return damageMultiplier.getOrDefault(type,1d);
	}
	
	public void setDamageReduction(DamageType type, double damageReduction) {
		updateDamage = true;
		updateKill = true;
		this.damageReduction.put(type,damageReduction);
	}
	
	public Map<DamageType,Double> getDamageReduction() {
		return damageReduction;
	}

	public Double getDamageReductionByType(DamageType type){
		return damageReduction.getOrDefault(type,0d);
	}
	
	public LivingEntity getDamaged() {
		return damaged;
	}
	
	public LivingEntity getDamager() {
		return damager;
	}
	
	public DamageReason getDamageReason() {
		return reason;
	}
	
	public void setDamageReason(DamageReason reason) {
		this.reason = reason;
	}
	
	public boolean isNatural() {
		return naturalReason != null;
	}
	
	public DamageCause getNaturalDamageReason() {
		return naturalReason;
	}
	
	public boolean isKill() {
		if (updateKill) {
			isKill = getFinalDamage() > damagedHp;
			updateKill = false;
		}
		return isKill;
	}
	
	public Map<DamageType,Double> getFinalDamagePerType() {
		Map<DamageType,Double> rtnMap = new HashMap<CustomDamageEvent.DamageType, Double>();
		for (DamageType type:DamageType.values()) {
			if (type == DamageType.ALL) {
				continue;
			}
			rtnMap.put(type, calculateDamage(type));
		}
		return rtnMap;
	}
	
	public double getFinalDamage() {
		if (updateDamage) {
			for (DamageType type:DamageType.values()) {
				if (type == DamageType.ALL || damage.get(type) == null) {
					continue;
				}
				finalDamage += Math.max(calculateDamage(type), 0);
			}
			updateDamage = false;
		}
		return finalDamage;
	}
	
	private double calculateDamage(DamageType type) {
		double damageRaw = (damage.getOrDefault(type,0d) * (damageMultiplier.getOrDefault(type,1d) * damageMultiplier.getOrDefault(DamageType.ALL, 1d)));
		double defensePercent = calcDefensePercent(type);
		
		return damageRaw * (1 - defensePercent) * (1 - calcDefensePercent(DamageType.ALL));
	}
	
	private double calcDefensePercent(DamageType type) {
		double def = damageReduction.getOrDefault(type, 0d);
		if (def < 50) {
			def = (Math.log( (def+1)-(def/2) ) + (def/75) )/6;
		} else {
			def = Math.log(def + 1)/6;
		}
		return def;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
