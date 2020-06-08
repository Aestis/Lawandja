package de.aestis.dndreloaded.Players;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.Professions.Profession;

public class PlayerHandler {

	private static PlayerHandler instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	FileConfiguration Config = Plugin.getConfig();
	
	public static PlayerHandler getInstance() {
		if (instance == null) {
			instance = new PlayerHandler();
		}
		return instance;
	}
	
	
	private HashMap<String, Integer> getPlayerProfessions(PlayerData playerData) {
		
		HashMap<String, Integer> professions = new HashMap<String, Integer>();
		
		if (playerData.getProfessionWoodcutter() != -1) professions.put(Config.getString("Localization.Professions.woodcutter"), playerData.getProfessionWoodcutter());
		if (playerData.getProfessionBlacksmith() != -1) professions.put(Config.getString("Localization.Professions.blacksmith"), playerData.getProfessionBlacksmith());
		if (playerData.getProfessionStonecutter() != -1) professions.put(Config.getString("Localization.Professions.stonecutter"), playerData.getProfessionStonecutter());
		if (playerData.getProfessionHerbalist() != -1) professions.put(Config.getString("Localization.Professions.herbalist"), playerData.getProfessionHerbalist());
		if (playerData.getProfessionInscriber() != -1) professions.put(Config.getString("Localization.Professions.inscriber"), playerData.getProfessionInscriber());
		if (playerData.getProfessionAlchemist() != -1) professions.put(Config.getString("Localization.Professions.alchemist"), playerData.getProfessionAlchemist());
		if (playerData.getProfessionFarmer() != -1) professions.put(Config.getString("Localization.Professions.farmer"), playerData.getProfessionFarmer());
		
		return professions;
	}
	
	public void setupPlayerProfessions(PlayerData playerData) {
		
		HashMap<String, Integer> professions = getPlayerProfessions(playerData);
		Integer count = 0;
		
		playerData.setProfessionPrimary(null);
		playerData.setProfessionSecondary(null);
		
		System.out.println(playerData.getName() + " profession count: " + professions.size());
		
		for (Map.Entry<String, Integer> entry : professions.entrySet()) {
			
			String key = entry.getKey();
			Integer value = entry.getValue();
			
			if (count == 0) {
				
				System.out.println("Prof 1: " + value);
				
				Profession prof = new Profession(playerData.getID() + 1000000000);
				prof.setName(key);
				prof.setCurrentExperience(value);
				playerData.setProfessionPrimary(prof);
			} else if (count == 1) {
				Profession prof = new Profession(playerData.getID() + 1000000001);
				prof.setName(key);
				prof.setCurrentExperience(value);
				playerData.setProfessionSecondary(prof);
			}			
			
			System.out.println(" >> " + key + " @ " + value + " xp");
			
			count++;
		}	
	}
	
	public boolean hasProfession(PlayerData playerData, String profession) {
		
		if (playerData.getProfessionPrimary().getName().equalsIgnoreCase(profession)
			|| playerData.getProfessionSecondary().getName().equalsIgnoreCase(profession)) return true;
		return false;
	}
	
}
