package de.aestis.dndreloaded.CommandManager.Auctions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Auctions.Util.AuctionMenu;
import de.aestis.dndreloaded.Auctions.Util.Auctionator;
import oxolotel.inventoryMenuManager.InventoryMenuManager;

public class AuctionsDummy implements CommandExecutor {
	
	private final Main Plugin = Main.instance;
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		Player player = (Player) Bukkit.getPlayer(sender.getName());
		
		
		if (args.length == 0)
		{
			sender.sendMessage("§cMissing Arguments! Usage: /auction <buy/sell> <uuid>");
			
			return true;
		}
		
		/**
		 * Command /questadmin reload
		 * is used to reload/rollback current
		 * QuestData from Database
		 */
		if (cmd.getName().equalsIgnoreCase("acd"))
		{
			if (args.length == 2)
			{
				String id = args[1];
				UUID uuid = null;
				Entity ent = null;
				
				if (UUID.fromString(id) != null) uuid = UUID.fromString(id);
				
				if (Bukkit.getEntity(uuid) == null)
				{
					sender.sendMessage("§cMissing Arguments! Usage: /auction <buy/sell> <uuid>");
					
					return true;
				} else
				{
					ent = Bukkit.getEntity(uuid);
					
					if (ent.getWorld() != player.getWorld()
						|| ent.getServer() != player.getServer())
					{
						sender.sendMessage("§cMissing Arguments! Usage: /auction <buy/sell> <uuid>");
						
						return true;
					}
				}
				
				if (args[0].equalsIgnoreCase("sell"))
				{
					openSellMenu(player, ent);
					
					return true;
				} else if (args[0].equalsIgnoreCase("buy"))
				{
					openBuyMenu(player, ent);
					
					return true;
				} else
				{
					sender.sendMessage("§cMissing Arguments! Usage: /auction <buy/sell> <uuid>");
					
					return true;
				}
			} else
			{
				sender.sendMessage("§cMissing Arguments! Usage: /auction <buy/sell> <uuid>");
				
				return true;
			}
		}
		
		return false;
	}
	
	
	private void openSellMenu(Player player, Entity ent) {
		
		Auctionator auctionator = Main.instance.Auctionators.get(ent);
		
		InventoryMenuManager.getInstance().openMenu(player, new AuctionMenu(auctionator));
	}

	private void openBuyMenu(Player player, Entity ent) {
		
		Auctionator auctionator = Main.instance.Auctionators.get(ent);
		
		InventoryMenuManager.getInstance().openMenu(player, new AuctionMenu(auctionator));
	}
}
