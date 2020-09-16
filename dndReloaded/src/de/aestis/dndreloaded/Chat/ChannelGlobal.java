package de.aestis.dndreloaded.Chat;

import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.craftbukkit.libs.org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Main;

public class ChannelGlobal {

	private static String _CHANNEL = "Global";
	
	public static void sendMessage(Player player, String message) {
		
		Main.instance.getLogger().severe("Sending msg to BungeeCord...");
		
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        
        try
        {
			out.writeUTF("SendGlobalMessage");
			out.writeUTF(Main.instance.getServer().getName());
			out.writeUTF(player.getName());
			out.writeUTF(message);
			out.writeUTF(_CHANNEL);
			
			player.sendPluginMessage(Main.instance, "lawandja:lbchat", stream.toByteArray());
	        
	        out.close();
		} catch (IOException e)
        {
			e.printStackTrace();
		}
    }
}
