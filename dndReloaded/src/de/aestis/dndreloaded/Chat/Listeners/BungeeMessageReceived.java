package de.aestis.dndreloaded.Chat.Listeners;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.aestis.dndreloaded.Main;

public class BungeeMessageReceived implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String _channel, Player _player, byte[] _message) {
		
		Main.instance.getLogger().severe("Incoming msg on Channel: " + _channel + " from Player " + _player.getName());
		
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(_message));
		
		String server = null, player = null, message = null, channel = null;
		
		try
		{
			server = in.readUTF();
			player = in.readUTF();
			message = in.readUTF();
			channel = in.readUTF();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		if (server != null
			|| player != null
			|| message != null
			|| channel != null)
		{
			Bukkit.broadcastMessage("§a[Lawandja/Global] " + _player.getName() + ": §f" + message);
		}
	}

}
