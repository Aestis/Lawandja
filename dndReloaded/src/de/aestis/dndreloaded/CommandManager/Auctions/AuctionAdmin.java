package de.aestis.dndreloaded.CommandManager.Auctions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.Player;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Auctions.AuctionHandler;
import de.aestis.dndreloaded.Auctions.Util.AuctionCategory;

public class AuctionAdmin implements CommandExecutor {
	
	private final Main Plugin = Main.instance;
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		Player player = (Player) Bukkit.getPlayer(sender.getName());
		
		
		if (args.length == 0)
		{
			sender.sendMessage("§cMissing Arguments! Usage: /setauctionator <category>");
			
			return true;
		}
		
		/**
		 * Command /questadmin reload
		 * is used to reload/rollback current
		 * QuestData from Database
		 */
		if (cmd.getName().equalsIgnoreCase("setauctionator"))
		{
			if (args.length == 1)
			{
				
				if (EnumUtils.isValidEnum(AuctionCategory.class, args[0]))
				{
					AuctionCategory cat = AuctionCategory.valueOf(args[0]);
					
					AuctionHandler.setupAuctionator(Main.instance.SelectedNPC.get(player), cat);
					sender.sendMessage("§2Auctionator '" + Plugin.SelectedNPC.get(player) + "' successfully set to category '" + cat.toString() + "'!");
					
					return true;
				} else
				{
					sender.sendMessage("§cUnknown category type! Valid categories are: " + getCategories());
					
					return true;
				}
				
			} else
			{
				sender.sendMessage("§cMissing Arguments! Usage: /setauctionator <category>");
				
				return true;
			}
		}
		
		return false;
	}
	
	private String getCategories() {
		
		String cat = "";
		
		for (AuctionCategory a : AuctionCategory.values())
		{
			cat += a.toString() + ", ";
		}
		
		return cat.substring(0, cat.length() - 2);
	}
}