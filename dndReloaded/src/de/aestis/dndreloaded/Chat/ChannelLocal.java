package de.aestis.dndreloaded.Chat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ChannelLocal {

	private static String _CHANNEL = "Lokal";
	private static Double _RANGE = 100.0;
	
	public static void sendMessage(Player player, String message) {

		World world = player.getWorld();
		Location loc = player.getLocation();
		
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (p.getWorld() != world) continue;
			
			Location buffer = p.getLocation();
			
			if (loc.distance(buffer) <= _RANGE)
			{
				p.sendMessage(message);
			}
		}
		
		player.sendMessage("[DEBUG]: " + message);
	}
}
