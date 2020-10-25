package de.aestis.dndreloaded.Auctions.Listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import de.aestis.dndreloaded.Auctions.AuctionHandler;
import de.aestis.dndreloaded.Util.Misc.ChatComponent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class NPCInteractEventListener implements Listener {

	
	@EventHandler
	public void onNPCInteractEventListener(PlayerInteractEntityEvent event) {
		
		if (event.isCancelled()) return;
		if (event.getRightClicked() == null) return;
		
		Player player = event.getPlayer();
		Entity ent = event.getRightClicked();
		
		if (AuctionHandler.doesAuctionatorExist(ent))
		{
			player.sendMessage(ent.getCustomName() + " fragt dich, was du tun möchtest:");
			
			TextComponent sell = ChatComponent.ComponentBuilder("[Gegenstände kaufen]", false, ChatColor.RED, "Sieh dir an, was es gerade so im Angebot gibt!", "/acd sell " + ent.getUniqueId());
			TextComponent buy = ChatComponent.ComponentBuilder("[Etwas verkaufen]", false, ChatColor.RED, "Sieh dir an, was es gerade so im Angebot gibt!", "/acd buy " + ent.getUniqueId());
			
			//PUT CMD TO RUN METHOD
			
			player.spigot().sendMessage(sell);
			player.spigot().sendMessage(buy);
		}
	}
}
