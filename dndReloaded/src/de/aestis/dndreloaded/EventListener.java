package de.aestis.dndreloaded;

import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Helpers.InventoryHelpers;
import de.aestis.dndreloaded.Helpers.MathHelpers;
import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Overrides.BlockBreak;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Quests.QuestHandler;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

public class EventListener implements Listener {
	
	private final Main Plugin = Main.instance;
	
	FileConfiguration Config = Plugin.getConfig();
	
	BlockBreak BlockBreakOverride = Plugin.getBlockBreakOverride();
	
	InventoryHelpers InventoryHelper = Plugin.getInventoryHelper();
	MathHelpers MathHelper = Plugin.getMathHelper();
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
		
		if (BlockBreakOverride.blockBreakCustomDurability(event)) {
			//Dura Check True
			BlockBreakOverride.blockBreakCustomItems(event);
		}	
	}
	
	
	@EventHandler
	public void playerInteractEvent(PlayerInteractEntityEvent event) {
		
		/*
		 * Fetch and show quests
		 * */
		
		QuestHnd.handleQuestgiverInteraction(event);
		
		//TEST!!!
		Scoreboard scb = ScoreboardHelper.prepareScoreboard(event.getPlayer(), "Testboard", "Willkommen in Lawandja!", DisplaySlot.SIDEBAR);
		PlayerData pd = Plugin.Players.get(event.getPlayer());
		
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " ", 12);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " - §6§lDein Titel:", 11);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " > §7" + pd.getName() + ", " + pd.getTitle(), 10);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " ", 9);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " - §6§l" + pd.getFaction(), 8);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " > §7" + pd.getReputation() + " Ruf", 7);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " ", 6);
		ScoreboardHelper.addToScoreboard(scb, "Testboard", " - §6§lPrimärberuf:", 5);
		if (pd.getProfessionPrimary() != null) {
			ScoreboardHelper.addToScoreboard(scb, "Testboard", " > §7" + pd.getProfessionPrimary().getName() + " (" + pd.getProfessionPrimary().getCurrentExperience() + "xp", 4);
		} else {
			ScoreboardHelper.addToScoreboard(scb, "Testboard", " - §7 - Kein Beruf -", 4);
		}
		
		ScoreboardHelper.setScoreboard(event.getPlayer(), scb);
	}

	
	@EventHandler
	public void playerHitEntityEvent(EntityDamageByEntityEvent event) {

		if (event.getDamager() instanceof Player) {
			
			//OUTSOURCE LATER!
			
			Player player = (Player) event.getDamager();
			Entity entity = event.getEntity();
			
			if (entity instanceof LivingEntity) {
				
				String name = entity.getCustomName();	
				if (name == null) name = entity.getType().name();
				Double health = ((LivingEntity) entity).getHealth();
				Double maxHealth = ((LivingEntity) entity).getMaxHealth();
				
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§d" + name + "§f: §c" + health + "§f/§2" + maxHealth + " §c♥"));
			} else {
				return;
			}
			
		} else {
			return;
		}
		
	}
	
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		
		Plugin.Players.remove(event.getPlayer());
		
		Bukkit.broadcastMessage("§cSpielerdaten von " + event.getPlayer().getName() + " entladen.");
	}
	
	
	@EventHandler
	public void onPlayerConnect(PlayerLoginEvent event) {
		
		/*
		 * OUTSOURCE!
		 * */
		
		DatabaseHandler Database = Plugin.getDatabaseHandler();
		
		String playerName = event.getPlayer().getName();
		
		if (Database.playerRegistered(playerName)) {
			Bukkit.broadcastMessage("§2Spielerdaten von " + playerName + " existieren.");
			Bukkit.broadcastMessage("§6Spielerdaten von " + playerName + " werden geladen...");
			
			Plugin.Players.put(event.getPlayer(), Database.getPlayerData(playerName));
			
			Bukkit.broadcastMessage("§2Spielerdaten von " + playerName + " wurden erfolgreich geladen!");
			
			PlayerData pd = Main.instance.Players.get(event.getPlayer());
			
			Bukkit.broadcastMessage("Player   = " + pd.getName());
			Bukkit.broadcastMessage("Faction  = " + pd.getFaction());
			Bukkit.broadcastMessage("Title    = " + pd.getTitle());
		} else {
			Bukkit.broadcastMessage("§cSpielerdaten von " + playerName + " nicht gefunden.");
			Bukkit.broadcastMessage("§6Registriere " + playerName + "...");
			if (Database.registerPlayer(playerName)) {
				Bukkit.broadcastMessage("§2Spieler " + playerName + " erfolgreich registriert!");
				
				Bukkit.broadcastMessage("§6Spielerdaten von " + playerName + " werden geladen...");
				
				Main.instance.Players.put(event.getPlayer(), Database.getPlayerData(playerName));
				
				Bukkit.broadcastMessage("§2Spielerdaten von " + playerName + " wurden erfolgreich geladen!");
			} else {
				Bukkit.broadcastMessage("§cSpielerdaten konnten nicht geschrieben werden.");
			}
		}
	}

}
