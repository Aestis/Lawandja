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
	
	/**
	 * Helper to check if quest is finished or not
	 * and increase the current Slot's variable
	 * @param playerData (Players Data)
	 * @param questSlot (Selected Quest Slot)
	 * @param increment (Amount to increase)
	 * @return true/false
	 */
	public boolean increaseQuestVariable(PlayerData playerData, Integer questSlot, Integer increment) {
		
		if (questSlot == 1)
		{
			if (playerData.getQuestActive1() != null)
			{
				Integer value = playerData.getQuestVariable1();
				Integer required = playerData.getQuestActive1().getVariable();
				
				if (required == null ||
					required == 0 ||
					required == -1)
				{
					/*
					 * Quest doesn't require any
					 * value to increase
					 */
					return false;
				} else
				{
					if (required == value)
					{
						/*
						 * Quest is finished,
						 * don't increase value
						 */
						return false;
					} else
					{
						playerData.setQuestVariable1(value + increment);
						return true;
					}
				}
			} else
			{
				/*
				 * Quest Slot 1 is not set
				 */
				return false;
			}
		}
		
		if (questSlot == 2)
		{
			if (playerData.getQuestActive2() != null)
			{
				Integer value = playerData.getQuestVariable2();
				Integer required = playerData.getQuestActive2().getVariable();
				
				if (required == null ||
					required == 0 ||
					required == -1)
				{
					/*
					 * Quest doesn't require any
					 * value to increase
					 */
					return false;
				} else
				{
					if (required == value)
					{
						/*
						 * Quest is finished,
						 * don't increase value
						 */
						return false;
					} else
					{
						playerData.setQuestVariable2(value + increment);
						return true;
					}
				}
			} else
			{
				/*
				 * Quest Slot 1 is not set
				 */
				return false;
			}
		}
		
		return false;
	}
	
}
