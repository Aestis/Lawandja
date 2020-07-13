package de.aestis.dndreloaded.Chat.Bungee;

import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Chat.Util.ChatMode;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;

public class BungeeCordBridge {

	private static BungeeCordBridge instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public static BungeeCordBridge getInstance() {
		if (instance == null) {
			instance = new BungeeCordBridge();
		}
		return instance;
	}
	
	public ServerInfo getBungeeCordNode() {
		
		if (BungeeCord.getInstance() == null ||
				!BungeeCord.getInstance().isRunning)
		{
			Plugin.getLogger().warning("BungeeCord not running!");
			return null;
		} else
		{
			String server = BungeeCord.getInstance().getName();
			return BungeeCord.getInstance().getServerInfo(server);
		}
	}
	
	public boolean hasRegisteredChannel(String channel) {
		
		if (BungeeCord.getInstance() == null ||
			!BungeeCord.getInstance().isRunning)
		{
			Plugin.getLogger().warning("BungeeCord not running!");
			return true;
		}
		if (BungeeCord.getInstance().getChannels().contains(channel)) return true;
		return false;
	}
	
	public void registerNewChannel(String channel) {
		
		if (!hasRegisteredChannel(channel))
		{
			BungeeCord.getInstance().registerChannel(channel);
			Plugin.getLogger().info("Registered Channel '" + channel + "'!");
		}
	}
	
}
