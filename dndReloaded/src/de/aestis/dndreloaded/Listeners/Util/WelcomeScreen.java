package de.aestis.dndreloaded.Listeners.Util;

import java.util.List;

import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.PlayerData;

public class WelcomeScreen {

	private static WelcomeScreen instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public static WelcomeScreen getInstance() {
		if (instance == null) {
			instance = new WelcomeScreen();
		}
		return instance;
	}
	
	public void sendReturningPlayerWelcomeScreen(Player player, PlayerData pd) {
		
		if (player.isValid() && pd != null)
		{
			/*
			 * Greets for the (returning) Player
			 */
			player.sendMessage(Plugin.getConfig().getString("Localization.Players.Welcome.returning"));
			
			List<String> motd = (List<String>) Plugin.getConfig().getList("Localization.Server.motd");
			
			if (motd != null && motd.size() >= 1)
			{
				for (String s : motd)
				{
					player.sendMessage(s);
				}
			}
		}
		
	}
	
	public void sendNewPlayerWelcomeScreen(Player player, PlayerData pd) {
		
		if (player.isValid() && pd != null)
		{
			/*
			 * Greets for the (new) Player
			 */
			player.sendMessage(Plugin.getConfig().getString("Localization.Players.Welcome.new"));
		}
		
	}
	
}
