package de.aestis.dndreloaded.Chat;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Chat.Util.ChatMode;

public class ChatHandler implements Listener {

	private final Main Plugin = Main.instance;
	
	public HashMap<Player, ChatMode> PlayerChannel = new HashMap<Player, ChatMode>();
	
	@EventHandler
	public void playerInitChannelChangeEvent(AsyncPlayerChatEvent event) {
		
		Player player = event.getPlayer();
		
		if (!event.getMessage().startsWith("+")) return;
		
		if (PlayerChannel.containsKey(player))
		{
			ChatMode cm = PlayerChannel.get(player);
			
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
					cm.setChannelID(1);
					break;
				default:
					cm.setChannelID(1);
					break;
			}
			
			String name = Plugin.getConfig().getString("Chat.Channels." + cm.getChannelID() + ".name");
			player.sendMessage("!In den Channel " + name + " gewechselt.");
			
			event.setCancelled(true);
			
		} else
		{
			initializePlayerChat(player);
			
			event.setCancelled(true);
		}		
	}
	
	private void initializePlayerChat(Player player) {
		
		if (!PlayerChannel.containsKey(player))
		{
			ChatMode cm = new ChatMode(Plugin.Players.get(player).getID() + 1000000000);
			String name = Plugin.getConfig().getString("Chat.Channels." + cm.getChannelID() + ".name");
			
			cm.setChannelID(4);
			PlayerChannel.put(player, cm);
			
			player.sendMessage("In den Channel " + name + " gewechselt.");
		}
	}
}
