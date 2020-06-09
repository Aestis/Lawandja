package de.aestis.dndreloaded.Helpers.ScoreboardUtil;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Players.PlayerData;

public class CustomScoreboards {
	
	private static CustomScoreboards instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public static CustomScoreboards getInstance() {
		if (instance == null) {
			instance = new CustomScoreboards();
		}
		return instance;
	}
	
	public Scoreboard getMainPlayerScoreboard(Player player) {
		
		ScoreboardHelpers ScoreboardHelper = Plugin.getScoreboardHelper();
		Scoreboard scb = ScoreboardHelper.prepareScoreboard(player, "Testboard", "Willkommen in Lawandja!", DisplaySlot.SIDEBAR);
		PlayerData pd = Plugin.Players.get(player.getPlayer());
		
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " ", 12);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " - §6§lDein Titel:", 11);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " > §7" + pd.getName() + ", " + pd.getTitle(), 10);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " ", 9);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " - §6§l" + pd.getFaction(), 8);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " > §7" + pd.getReputation() + " Ruf", 7);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " ", 6);
		
		if (pd.getProfessionPrimary() != null)
		{
			ScoreboardHelper.addToScoreboard(scb, "Testboard", " + §7" + pd.getProfessionPrimary().getName() + " (" + pd.getProfessionPrimary().getCurrentExperience() + "/50 xp)", 5);
		}	
		if (pd.getProfessionSecondary() != null)
		{
			ScoreboardHelper.addToScoreboard(scb, "Testboard", " + §7" + pd.getProfessionSecondary().getName() + " (" + pd.getProfessionSecondary().getCurrentExperience() + "/50 xp)", 4);
		}
		
		return scb;
	}
}
