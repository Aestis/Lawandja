package de.aestis.dndreloaded;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Players.PlayerData;

public class DataSync {
	
	private static DataSync instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public static DataSync getInstance() {
		if (instance == null) {
			instance = new DataSync();
		}
		return instance;
	}
	
	
	public void loadPlayerData(Player player) {
		
		DatabaseHandler Database = Plugin.getDatabaseHandler();
		
		if (Database.playerRegistered(player.getName())) {
			Bukkit.broadcastMessage("§2Spielerdaten von " + player.getName() + " existieren.");
			Bukkit.broadcastMessage("§6Spielerdaten von " + player.getName() + " werden geladen...");
			
			Main.instance.Players.put(player, Database.getPlayerData(player.getName()));
			
			Bukkit.broadcastMessage("§2Spielerdaten von " + player.getName() + " wurden erfolgreich geladen!");
			
			PlayerData pd = Main.instance.Players.get(player);
			
			Bukkit.broadcastMessage("Player   = " + pd.getName());
			Bukkit.broadcastMessage("Faction  = " + pd.getFaction());
			Bukkit.broadcastMessage("Title    = " + pd.getTitle());
		} else {
			Bukkit.broadcastMessage("§cSpielerdaten von " + player.getName() + " nicht gefunden.");
			Bukkit.broadcastMessage("§6Registriere " + player.getName() + "...");
			if (Database.registerPlayer(player.getName())) {
				Bukkit.broadcastMessage("§2Spieler " + player.getName() + " erfolgreich registriert!");
			} else {
				Bukkit.broadcastMessage("§cSpielerdaten konnten nicht geschrieben werden.");
			}
		}
	}
	
	public void savePlayerData(Player player) {
		
		//TODO
		
	}
	
}
