package de.aestis.dndreloaded;

import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Helpers.InventoryHelpers;
import de.aestis.dndreloaded.Helpers.MathHelpers;
import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Helpers.External.HolographicDisplaysHelper;
import de.aestis.dndreloaded.Helpers.External.WorldGuardHelper;
import de.aestis.dndreloaded.Helpers.External.WorldGuard.RegionEnterEvent;
import de.aestis.dndreloaded.Helpers.External.WorldGuard.RegionEvent;
import de.aestis.dndreloaded.Helpers.ScoreboardUtil.CustomScoreboards;
import de.aestis.dndreloaded.Overrides.BlockBreak;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Players.PlayerHandler;
import de.aestis.dndreloaded.Quests.QuestHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class EventListener implements Listener {
	
	private final Main Plugin = Main.instance;
	
	FileConfiguration Config = Plugin.getConfig();
	
	BlockBreak BlockBreakOverride = Plugin.getBlockBreakOverride();
	
	ScoreboardHelpers ScoreboardHelper = Plugin.getScoreboardHelper();
	
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
		ScoreboardHelper.setScoreboard(event.getPlayer(), scb);
	}

}
