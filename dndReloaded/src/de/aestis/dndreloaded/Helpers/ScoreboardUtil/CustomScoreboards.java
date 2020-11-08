package de.aestis.dndreloaded.Helpers.ScoreboardUtil;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.MathHelpers;
import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Quests.QuestTypes;

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
		
		Scoreboard scb = ScoreboardHelpers.prepareScoreboard(player, "Testboard", "Willkommen in Lawandja!", DisplaySlot.SIDEBAR);
		PlayerData pd = Plugin.Players.get(player.getPlayer());
		
		ScoreboardHelpers.addToScoreboard(scb, "Testboard", " - §6§lDein Titel:", 20);
		ScoreboardHelpers.addToScoreboard(scb, "Testboard", " > §7" + pd.getName() + ", " + pd.getTitle(), 19);
		ScoreboardHelpers.addToScoreboard(scb, "Testboard", "  ", 18);
		ScoreboardHelpers.addToScoreboard(scb, "Testboard", " - §6§l" + pd.getFaction(), 17);
		ScoreboardHelpers.addToScoreboard(scb, "Testboard", " > §7" + pd.getReputation() + " Ruf", 16);
		ScoreboardHelpers.addToScoreboard(scb, "Testboard", "  ", 15);
		
		if (pd.getProfessionPrimary() != null)
		{
			String skillpoints = MathHelpers.shortifyNumber(pd.getProfessionPrimary().getCurrentExperience()) + "k";
			ScoreboardHelpers.addToScoreboard(scb, "Testboard", " + §7" + pd.getProfessionPrimary().getName() + " (" + skillpoints + "/50 xp)", 14);
		}	
		if (pd.getProfessionSecondary() != null)
		{
			ScoreboardHelpers.addToScoreboard(scb, "Testboard", " + §7" + pd.getProfessionSecondary().getName() + " (" + pd.getProfessionSecondary().getCurrentExperience() + "/50 xp)", 13);
		}
		
		if (pd.getQuestActive1() != null)
		{
			ScoreboardHelpers.addToScoreboard(scb, "Testboard", "- ", 12);
			
			if (pd.getQuestActive1().getType().equals(QuestTypes.BLOCK_BREAK)
				|| pd.getQuestActive1().getType().equals(QuestTypes.BLOCK_BREAK_AREA)
				|| pd.getQuestActive1().getType().equals(QuestTypes.BLOCK_PLACE)
				|| pd.getQuestActive1().getType().equals(QuestTypes.BLOCK_PLACE_AREA)
				|| pd.getQuestActive1().getType().equals(QuestTypes.KILL_MOBS)
				|| pd.getQuestActive1().getType().equals(QuestTypes.KILL_MOBS_AREA))
			{
				if (pd.getQuestVariable1() >= pd.getQuestActive1().getVariable())
				{
					/*
					 * Quest is completed
					 * show specified String instead!
					 */
					String quick = " §2§lFertig!";
					ScoreboardHelpers.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive1().getTitle() + quick, 11);
				} else
				{
					String quick = " (" + pd.getQuestVariable1() + "/" + pd.getQuestActive1().getVariable() + ")";
					ScoreboardHelpers.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive1().getTitle() + quick, 11);
				}
			} else if (pd.getQuestActive1().getType().equals(QuestTypes.DELIVER_ITEM))
			{
				String quick = " (" + pd.getQuestActive1().getVariable() + " " + pd.getQuestActive1().getItem().getItemID().getMaterial() + ")";
				ScoreboardHelpers.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive1().getTitle() + quick, 11);
			} else
			{
				ScoreboardHelpers.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive1().getTitle(), 11);
			}

		}
		if (pd.getQuestActive2() != null)
		{
			ScoreboardHelpers.addToScoreboard(scb, "Testboard", " ", 10);
			
			if (pd.getQuestActive2().getType().equals(QuestTypes.BLOCK_BREAK)
				|| pd.getQuestActive2().getType().equals(QuestTypes.BLOCK_BREAK_AREA)
				|| pd.getQuestActive2().getType().equals(QuestTypes.BLOCK_PLACE)
				|| pd.getQuestActive2().getType().equals(QuestTypes.BLOCK_PLACE_AREA)
				|| pd.getQuestActive2().getType().equals(QuestTypes.KILL_MOBS)
				|| pd.getQuestActive2().getType().equals(QuestTypes.KILL_MOBS_AREA))
			{
				if (pd.getQuestVariable2() >= pd.getQuestActive2().getVariable())
				{
					/*
					 * Quest is completed
					 * show specified String instead!
					 */
					String quick = " §2§lFertig!";
					ScoreboardHelpers.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive2().getTitle() + quick, 9);
				} else
				{
					String quick = " (" + pd.getQuestVariable2() + "/" + pd.getQuestActive2().getVariable() + ")";
					ScoreboardHelpers.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive2().getTitle() + quick, 9);
				}
			} else if (pd.getQuestActive1().getType().equals(QuestTypes.DELIVER_ITEM))
			{
				String quick = " (" + pd.getQuestActive2().getVariable() + " " + pd.getQuestActive2().getItem().getItemID().getMaterial() + ")";
				ScoreboardHelpers.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive2().getTitle() + quick, 9);
			} else
			{
				ScoreboardHelpers.addToScoreboard(scb, "Testboard", " ? §7" + pd.getQuestActive2().getTitle(), 9);
			}
			
		}
		
		return scb;
	}
}
