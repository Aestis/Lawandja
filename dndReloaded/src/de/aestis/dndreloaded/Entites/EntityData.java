package de.aestis.dndreloaded.Entites;

import java.util.UUID;
import com.gmail.filoghost.holographicdisplays.api.Hologram;

import de.aestis.dndreloaded.Players.BattleData;

public class EntityData {
	
	private final UUID UUID;
	
	private BattleData BattleData;
	
	public EntityData (UUID UUID) {this.UUID = UUID;}
	
	public UUID getUUID() {return this.UUID;}
	
	public BattleData getBattleData() {return this.BattleData;}
	public void setBattleData(BattleData BattleData) {this.BattleData = BattleData;}
	
}
