package de.aestis.dndreloaded.Chat;

import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.craftbukkit.libs.org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Chat.Bungee.BungeeCordBridge;
import de.aestis.dndreloaded.Chat.Util.ChatMode;

public class ChatHandler implements Listener {

	private final Main Plugin = Main.instance;

	/*
	 * Outsource Event Handlers in
	 * Extra Class to prevent confusion :D
	 */
	
	public ChatMode getPlayerChatMode(Player player) {
		
		if (player == null) return null;
		
		if (Plugin.PlayerChannel.containsKey(player))
		{
			return Plugin.PlayerChannel.get(player);
		} else
		{
			Plugin.getLogger().warning("Player '" + player.getName() + "' currently is not assigned to a Channel!");
			return null;
		}
	}
	
	/**
	 * Handles chat messages via Channel-Modes
	 * (for example sending stuff to Bungee)
	 * @param event
	 */
	@EventHandler
	public void playerChatEvent(AsyncPlayerChatEvent event) {
		
		Player player = (Player) event.getPlayer();
		
		if (player == null) return;		
		if (event.getMessage().startsWith("+")) return;
		
		Plugin.getLogger().severe("Requesting ChatMode for Player " + player.getName());
		
		ChatMode cm = this.getPlayerChatMode(player);
		
		if (cm == null)
		{
			//TODO
			//Implement config msg
			player.sendMessage("§cPLAYER NOT FOUND!");
			event.setCancelled(true);
			return;
		}
		
		if (cm.getChannelID() != null)
		{
			player.sendMessage("Channel: " + cm.getChannelID());
		} else
		{
			//TODO
			//Implement config msg
			player.sendMessage("§cNO CHANNEL SELECTED!");
			event.setCancelled(true);
			return;
		}
		
		if (cm.getChannelID() == 1)
		{
			ChannelGlobal.sendMessage(event.getPlayer(), event.getMessage());
			event.setCancelled(true);
		}
		
		if (cm.getChannelID() == 4)
		{
			ChannelLocal.sendMessage(event.getPlayer(), event.getMessage());
			event.setCancelled(true);
		}
	}
	
	/**
	 * Event for cycling through available
	 * Chat-Channels set up in config.yml
	 * @param event
	 */
	@EventHandler
	public void playerInitChannelChangeEvent(AsyncPlayerChatEvent event) {

		Player player = (Player) event.getPlayer();
		
		if (player == null) return;		
		if (!event.getMessage().startsWith("+")) return;

		if (Plugin.PlayerChannel.containsKey(player))
		{
			ChatMode cm = Plugin.PlayerChannel.get(player);
			
			switch (cm.getChannelID())
			{
				case 1:
					cm.setChannelID(2);
					break;
				case 2:
					cm.setChannelID(3);
					break;
				case 3:
					cm.setChannelID(4);
					break;
				case 4:
					cm.setChannelID(5);
					break;
				case 5:
					cm.setChannelID(1);
					break;
				default:
					cm.setChannelID(1);
					break;
			}
			
			String name = Plugin.getConfig().getString("Chat.Channels." + cm.getChannelID() + ".name");
			
			//TODO
			//Add Config msg
			player.sendMessage("!In den Channel " + name + " gewechselt.");
			
			event.setCancelled(true);
			
		} else
		{
			initializePlayerChat(player);
			
			event.setCancelled(true);
		}		
	}

	//@EventHandler
	public void playerChatEventOLD(AsyncPlayerChatEvent event) {
		
		if (event.getMessage().startsWith("+")) return;
		
		Plugin.getLogger().info("playerChatEvent triggered! Awaiting further instrucitons...");
		
		/*BungeeCordBridge bcb = Plugin.getBungeeCordBridge();
		
		if (!bcb.hasRegisteredChannel("Lawandja"))
		{
			bcb.registerNewChannel("Lawandja");
		}*/

		ChatMode cm = getPlayerChatMode(event.getPlayer());
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(stream);
		
		try
		{
			out.writeUTF("Message");
			out.writeUTF("Tuxel");
			out.writeUTF(event.getMessage());

			Plugin.getLogger().info("Sending data to BungeeCord: '" + event.getMessage() + "'...");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Plugin.getServer().sendPluginMessage(Plugin, "BungeeCord", stream.toByteArray());
		
		/*Plugin.getServer().getPluginManager().
		bcb.getBungeeCordNode().sendData("Lawandja", stream.toByteArray());
		
		
		switch (cm.getChannelID())
		{
			case 1:
				break;
		}
		
		event.getPlayer().sendMessage("WIP");
		event.setCancelled(true);*/
	}
	
	private void initializePlayerChat(Player player) {
		
		if (!Plugin.PlayerChannel.containsKey(player))
		{
			ChatMode cm = new ChatMode(Plugin.Players.get(player).getID() + 1000000000);
			String name = Plugin.getConfig().getString("Chat.Channels." + cm.getChannelID() + ".name");
			
			cm.setChannelID(4);
			Plugin.PlayerChannel.put(player, cm);
			
			player.sendMessage("In den Channel " + name + " gewechselt.");
		}
	}
}
