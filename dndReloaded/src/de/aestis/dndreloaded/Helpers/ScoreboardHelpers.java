package de.aestis.dndreloaded.Helpers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardHelpers {
	
	@SuppressWarnings("deprecation")
	public static Scoreboard prepareScoreboard (Player player, String objective, String title, DisplaySlot slot) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj;
        if (board.getObjective(objective) != null) {
        	player.setScoreboard(board);
        	board.getObjective(objective).setDisplaySlot(slot);
        	return board;
        }
        obj = board.registerNewObjective(objective, "dummy");
        obj.setDisplaySlot(slot);
        obj.setDisplayName(title);
        return board;
	}
	
	public static void addToScoreboard (final Scoreboard board, String objective, String text, Integer entry) {
		Objective obj = board.getObjective(objective);
		
		if (obj.getScore(text).isScoreSet())
		{
			//TODO
		}
		
		obj.getScore(text).setScore(entry);
	}
	
	public static void setScoreboard (Player player, final Scoreboard board) {
		player.setScoreboard(board);
	}

	public static boolean checkForScoreboard (Player player, String objective) {
		
		if (player.getScoreboard().getObjective(objective) != null)
		{
			player.sendMessage("Hast schon...");
		}
		
		return false;
	}
	
	public static void removeAllScoreboards (String objective) {
		
	}
	
}
