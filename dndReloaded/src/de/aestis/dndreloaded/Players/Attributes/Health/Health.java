package de.aestis.dndreloaded.Players.Attributes.Health;

import org.bukkit.entity.LivingEntity;

public class Health {

	private final Integer ID;
	
	private LivingEntity Player;
	private Double Health;
	private Double MaxHealth;
	
	public Health (Integer ID) {this.ID = ID;}
	
	public Integer getID() {return this.ID;}
	
	public LivingEntity getPlayer() {return this.Player;}
	public void setPlayer(LivingEntity Player) {this.Player = Player;}
	
	public Double getHealth() {return this.Health;}
	public void setHealth(Double Health) {this.Health = Health;}
	
	public Double getMaxHealth() {return this.MaxHealth;}
	public void setMaxHealht(Double MaxHealth) {this.MaxHealth = MaxHealth;}
}
