package de.aestis.dndreloaded.Players;

import org.bukkit.entity.Entity;

public class BattleData {

	private final Integer ID;
	
	private Entity Target;
	private Integer TargetAggro;
	private Integer DamageDealt;
	
	public BattleData (Integer ID) {this.ID = ID;}
	
	public Integer getID() {return this.ID;}
	
}
