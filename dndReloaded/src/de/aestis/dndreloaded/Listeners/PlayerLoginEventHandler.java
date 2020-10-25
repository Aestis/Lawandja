package de.aestis.dndreloaded.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Players.PlayerHandler;
import de.aestis.dndreloaded.Players.Attributes.Health.Health;

public class PlayerLoginEventHandler implements Listener {

	private final Main Plugin = Main.instance;	
	private final FileConfiguration Config = Plugin.getConfig();
	
	@EventHandler
	public void setPlayerJoinMessage(PlayerJoinEvent event) {
		
		/*
		 * Cancel Join Message
		 */
		event.setJoinMessage(null);
	}
	
	@EventHandler
	public void playerBeforeJoinEvent(PlayerLoginEvent event) {
		
		/*
		 * Check if Quests have been
		 * loaded first to avoid errors!
		 */
		
		if (Plugin.QuestData == null)
		{
			event.getPlayer().kickPlayer(Config.getString("Localization.General.Events.Join.beforeload"));
		}
		
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
			PlayerHandler ph = Main.instance.getPlayerHandler();
			ph.setupPlayerProfessions(pd);
			
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
				PlayerHandler ph = Main.instance.getPlayerHandler();
				ph.setupPlayerProfessions(Plugin.Players.get(event.getPlayer()));
				
				Bukkit.broadcastMessage("§2Spielerdaten von " + playerName + " wurden erfolgreich geladen!");				
			} else
			{
				Bukkit.broadcastMessage("§cSpielerdaten konnten nicht geschrieben werden.");
				return;
			}
		}
		
		/*
		 * Real Join Messages
		 * comes here!
		 */
		
		//TODO
		//Regex
		
		PlayerData pd = Plugin.Players.get(event.getPlayer());
		
		Bukkit.broadcastMessage("§6" + pd.getName() + "§7, " + pd.getTitle() + " §f ist Lawandja beigetreten!");
	}
}
