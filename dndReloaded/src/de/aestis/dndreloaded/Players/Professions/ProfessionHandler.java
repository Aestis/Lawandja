package de.aestis.dndreloaded.Players.Professions;

import org.bukkit.entity.Player;

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
	
}
