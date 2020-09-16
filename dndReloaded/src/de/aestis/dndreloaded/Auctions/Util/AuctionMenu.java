package de.aestis.dndreloaded.Auctions.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.Auctions.AuctionHandler;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.ShiftClickable;
import oxolotel.utils.DoubleWrapper;

public class AuctionMenu extends CustomMenu implements Closeable, ShiftClickable {

	private Auctionator auctionator;
	
	public AuctionMenu(Auctionator a) {
		super(27);
		this.auctionator = a;
		setTitle("Auktionshaus " + this.auctionator.getAuctionCategory().toString());
	}
	
	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player player) {
		
		//TODO
		//List Auctions for this Category
		
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> rtn = new HashMap<Integer, DoubleWrapper<ItemStack, Runnable>>();
		DoubleWrapper<ItemStack, Runnable> dw;
		List<Auction> auctions = AuctionHandler.getAuctionsByCategory(Bukkit.getEntity(auctionator.getUUID()), auctionator.getAuctionCategory());
		
		//TODO
		
		return null;
	}
	
	public void onAuctionView(Player player, Auction auction) {
		
		//TODO
		//Auction Submenu
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason arg2) {
		
		//TODO
		//Output Auctionator Message
	}
}
