package de.aestis.dndreloaded.Helpers.ScoreboardUtil;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.MathHelpers;
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
		MathHelpers MathHelper = Plugin.getMathHelper();
		Scoreboard scb = ScoreboardHelper.prepareScoreboard(player, "Testboard", "Willkommen in Lawandja!", DisplaySlot.SIDEBAR);
		PlayerData pd = Plugin.Players.get(player.getPlayer());
		
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " - §6§lDein Titel:", 20);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " > §7" + pd.getName() + ", " + pd.getTitle(), 19);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", "  ", 18);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " - §6§l" + pd.getFaction(), 17);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " > §7" + pd.getReputation() + " Ruf", 16);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", "  ", 15);
		
		if (pd.getProfessionPrimary() != null)
		{
			String skillpoints = MathHelper.shortifyNumber(pd.getProfessionPrimary().getCurrentExperience()) + "k";
			ScoreboardHelper.addToScoreboard(scb, "Testboard", " + §7" + pd.getProfessionPrimary().getName() + " (" + skillpoints + "/50 xp)", 14);
		}	
		if (pd.getProfessionSecondary() != null)
		{
			ScoreboardHelper.addToScoreboard(scb, "Testboard", " + §7" + pd.getProfessionSecondary().getName() + " (" + pd.getProfessionSecondary().getCurrentExperience() + "/50 xp)", 13);
		}
		
		if (pd.getQuestActive1() != null)
		{
			ScoreboardHelper.addToScoreboard(scb, "Testboard", " ", 12);
			
			switch (pd.getQuestActive1().getType()) {
				
				case "KILL_MOBS": case "KILL_MOBS_AREA":
					String quick = " (" + pd.getQuestVariable1() + "/" + pd.getQuestActive1().getVariable() + ")";
					ScoreboardHelper.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive1().getTitle() + quick, 11);
					break;
					
				default:
					ScoreboardHelper.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive1().getTitle(), 11);
					break;
			
			}
			
			
		}
		if (pd.getQuestActive2() != null)
		{
			switch (pd.getQuestActive2().getType()) {
				
				case "KILL_MOBS": case "KILL_MOBS_AREA":
					String quick = " (" + pd.getQuestVariable2() + "/" + pd.getQuestActive2().getVariable() + ")";
					ScoreboardHelper.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive2().getTitle() + quick, 10);
					break;
					
				default:
					ScoreboardHelper.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive2().getTitle(), 10);
					break;
			
			}
			
			
		}
		
		return scb;
	}
}
