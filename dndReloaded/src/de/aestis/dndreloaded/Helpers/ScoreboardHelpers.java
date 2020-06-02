package de.aestis.dndreloaded.Helpers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardHelpers {
	
	private static ScoreboardHelpers instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	public static ScoreboardHelpers getInstance() {
		if (instance == null) {
			instance = new ScoreboardHelpers();
		}
		return instance;
	}
	
	@SuppressWarnings("deprecation")
	public Scoreboard prepareScoreboard (Player player, String objective, String title, DisplaySlot slot) {
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
	
	public void addToScoreboard (final Scoreboard board, String objective, String text, Integer entry) {
		Objective obj = board.getObjective(objective);
		obj.getScore(text).setScore(entry);
	}
	
	public void setScoreboard (Player player, final Scoreboard board) {
		player.setScoreboard(board);
	}
	
	
	/*
	 * Scoreboard Helpers
	 * Cleaning up and checking
	 * */
	
	public boolean checkForScoreboard (Player player, String objective) {
		return false;
	}
	
	public void removeAllScoreboards (String objective) {
		
	}
	
}
