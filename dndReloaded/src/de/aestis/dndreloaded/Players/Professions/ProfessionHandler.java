package de.aestis.dndreloaded.Players.Professions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Players.PlayerHandler;

public class ProfessionHandler {

	private static ProfessionHandler instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
		
	private final Main Plugin = Main.instance;
	
	public static ProfessionHandler getInstance() {
		
		if (instance == null) {
			instance = new ProfessionHandler();
		}
		return instance;
	}
	
	public void addProfessionXP (Player player, Profession profession, Integer xp) {
		
		PlayerData playerData = Plugin.Players.get(player);
		PlayerHandler playerHandler = Plugin.getPlayerHandler();
		
		if (playerHandler.hasProfession(playerData, profession.getName())) {
			profession.setCurrentExperience(profession.getCurrentExperience() + xp);
		}
	}
	
	
	/*
	 * Actual Profession Handlers
	 * for each possible Profession
	 */
	
	public void handleProfessionEvent (Player player, Event event) {
		
		Profession primary = Plugin.Players.get(player).getProfessionPrimary();
		Profession secondary = Plugin.Players.get(player).getProfessionSecondary();
		
		if (primary != null
			|| secondary != null) {
			
			/*
			 * Switching both Professions
			 * (if available)
			 * ... I know its ugly this way
			 */
			
			if (primary != null) {
				
				switch (primary.getName()) {
					
					case "WOODCUTTER":
						//TODO
						break;
					
					case "BLACKSMITH":
						//TODO
						break;
						
					case "STONECUTTER":
						//TODO
						break;
						
					case "HERBALIST":
						//TODO
						break;
						
					case "INSCRIBER":
						//TODO
						break;
						
					case "ALCHEMIST":
						//TODO
						break;
						
					case "FARMER":
						//TODO
						break;
						
					case "TANNER":
						//IMPLEMENT FIRST!
						//TODO
						break;
				}
			}
			
			if (secondary != null) {
				
				switch (secondary.getName()) {
				
					case "WOODCUTTER":
						//TODO
						break;
					
					case "BLACKSMITH":
						//TODO
						break;
						
					case "STONECUTTER":
						//TODO
						break;
						
					case "HERBALIST":
						//TODO
						break;
						
					case "INSCRIBER":
						//TODO
						break;
						
					case "ALCHEMIST":
						//TODO
						break;
						
					case "FARMER":
						//TODO
						break;
						
					case "TANNER":
						//IMPLEMENT FIRST!
						//TODO
						break;
				}
			}
			
		} else {
			
			return;
		}
		
	}
}
