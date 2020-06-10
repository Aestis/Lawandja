package de.aestis.dndreloaded.Players.Professions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sun.webkit.plugin.Plugin;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Players.PlayerHandler;

public class ProfessionHandler {

	private static ProfessionHandler instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
		
	private final static Main Plugin = Main.instance;
	
	public static ProfessionHandler getInstance() {
		
		if (instance == null) {
			instance = new ProfessionHandler();
		}
		return instance;
	}
	
	public void addProfessionXP (Player player, Profession profession, Integer xp) {
		
		if (xp == 0) return;
		
		PlayerData playerData = Plugin.Players.get(player);
		PlayerHandler playerHandler = Plugin.getPlayerHandler();
		
		if (playerHandler.hasProfession(playerData, profession.getName()))
		{
			profession.setCurrentExperience(profession.getCurrentExperience() + xp);
			
			if (profession.getName() == Plugin.getConfig().getString("Localization.Professions.woodcutter"))
			{
				playerData.setProfessionWoodcutter(profession.getCurrentExperience());
			} else if (profession.getName() == Plugin.getConfig().getString("Localization.Professions.blacksmith"))
			{
				playerData.setProfessionBlacksmith(profession.getCurrentExperience());
			} else if (profession.getName() == Plugin.getConfig().getString("Localization.Professions.stonecutter"))
			{
				playerData.setProfessionStonecutter(profession.getCurrentExperience());
			} else if (profession.getName() == Plugin.getConfig().getString("Localization.Professions.herbalist"))
			{
				playerData.setProfessionHerbalist(profession.getCurrentExperience());
			} else if (profession.getName() == Plugin.getConfig().getString("Localization.Professions.inscriber"))
			{
				playerData.setProfessionInscriber(profession.getCurrentExperience());
			} else if (profession.getName() == Plugin.getConfig().getString("Localization.Professions.alchemist"))
			{
				playerData.setProfessionAlchemist(profession.getCurrentExperience());
			} else if (profession.getName() == Plugin.getConfig().getString("Localization.Professions.farmer"))
			{
				playerData.setProfessionFarmer(profession.getCurrentExperience());
			} else if (profession.getName() == Plugin.getConfig().getString("Localization.Professions.farmer"))
			{
				playerData.setProfessionTanner(profession.getCurrentExperience());
			}
			
			//REMOVE
			Bukkit.broadcastMessage("Increased XP of " + player.getName() + " in Profession " + profession.getName() + " by " + xp + " XP!");
		}
	}
	
	public boolean removeProfessionXP (Player player, Profession profession, Integer xp) {
		
		PlayerData playerData = Plugin.Players.get(player);
		PlayerHandler playerHandler = Plugin.getPlayerHandler();
		
		if (playerHandler.hasProfession(playerData, profession.getName()))
		{
			Integer current = profession.getCurrentExperience();
			
			if (current >= xp)
			{				
				profession.setCurrentExperience(profession.getCurrentExperience() - xp);
				return true;
			} else
			{
				return false;
			}
		}
		return false;
	}
	
}
