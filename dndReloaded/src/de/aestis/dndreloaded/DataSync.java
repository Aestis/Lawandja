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
		
		if (Database.playerRegistered(player.getName()))
		{
			Bukkit.broadcastMessage("§2Spielerdaten von " + player.getName() + " existieren.");
			Bukkit.broadcastMessage("§6Spielerdaten von " + player.getName() + " werden geladen...");
			
			Main.instance.Players.put(player, Database.getPlayerData(player.getName()));
			
			Bukkit.broadcastMessage("§2Spielerdaten von " + player.getName() + " wurden erfolgreich geladen!");
		} else
		{
			Bukkit.broadcastMessage("§cSpielerdaten von " + player.getName() + " nicht gefunden.");
			Bukkit.broadcastMessage("§6Registriere " + player.getName() + "...");
			
			if (Database.registerPlayer(player.getName()))
			{
				Plugin.getLogger().fine("Player '" + player.getName() + "' successfully added to Database!");
			} else
			{
				Plugin.getLogger().warning("Player '" + player.getName() + "' couldnt be added to Database!");
			}
		}
	}
	
	public void savePlayerData(Player player) {
		
		DatabaseHandler Database = Plugin.getDatabaseHandler();
		PlayerData pd = Plugin.Players.get(player);
		
		Database.savePlayerData(player.getName(), pd);
		Plugin.getLogger().info("Synchronizing '" + player.getName() + "' PlayerData to Database...");
	}
	
}
