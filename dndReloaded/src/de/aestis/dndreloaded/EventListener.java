package de.aestis.dndreloaded;

import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Helpers.ScoreboardUtil.CustomScoreboards;
import de.aestis.dndreloaded.Overrides.BlockBreak;
import de.aestis.dndreloaded.Quests.QuestHandler;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scoreboard.Scoreboard;

public class EventListener implements Listener {
	
	private final Main Plugin = Main.instance;
	
	FileConfiguration Config = Plugin.getConfig();
	
	BlockBreak BlockBreakOverride = Plugin.getBlockBreakOverride();
	
	QuestHandler QuestHnd = Plugin.getQuestHandler();

	/*
	 * Pass Events separately!
	 * */
	
	@EventHandler
	public void blockBreakEvent(BlockBreakEvent event) {
		
		/*
		 * Might outsource logic later?
		 * */
		
		if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) {
			return;
		}
		
		/*if (BlockBreakOverride.blockBreakCustomDurability(event)) {
			//Dura Check True
			BlockBreakOverride.blockBreakCustomItems(event);
		}*/
	}
	
	
	@EventHandler
	public void playerInteractEvent(PlayerInteractEntityEvent event) {
		
		/*
		 * Fetch and show quests
		 * */
		
		QuestHnd.handleQuestgiverInteraction(event);
		
		//TEST!!!
		
		Scoreboard scb = CustomScoreboards.getInstance().getMainPlayerScoreboard(event.getPlayer());
		ScoreboardHelpers.setScoreboard(event.getPlayer(), scb);
	}

}
