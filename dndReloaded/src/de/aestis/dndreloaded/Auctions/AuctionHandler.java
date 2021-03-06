package de.aestis.dndreloaded.Auctions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Auctions.Events.AuctionCreatedEvent;
import de.aestis.dndreloaded.Auctions.Util.Auction;
import de.aestis.dndreloaded.Auctions.Util.AuctionCategory;
import de.aestis.dndreloaded.Auctions.Util.Auctionator;

public class AuctionHandler {

	public static void fetchAuctions() {
		
		if (Main.instance.Auctions != null)
		{
			for (Auction a : Main.instance.getDatabaseHandler().getAuctionData())
			{
				Main.instance.Auctions.add(a);
			}
		}
	}
	
	public static void addAuction(Auction auction) {
		
		if (auction == null) return;
		
		//AuctionCreateEvent createEvent = new AuctionCreateEvent(auction);
		//Main.instance.getServer().getPluginManager().callEvent(createEvent);
		Main.instance.Auctions.add(auction);
	}
	
	public static void saveAuctions() {
		
		
	}
	
	public static void setupAuctionators() {
		
		//TODO
		//First setup from Database
	}
	
	public static void setupAuctionator(String _uuid, AuctionCategory category) {
		
		UUID uuid = UUID.fromString(_uuid);
		Entity ent = Bukkit.getEntity(uuid);
		
		if (ent == null)
		{
			
		} else
		{
			if (!doesAuctionatorExist(ent))
			{
				Main.instance.Auctionators.put(ent, new Auctionator(1));
			}
			
		}
	}
	
	public static void saveAuctionators() {
		
		//TODO
		//Save to Database
	}
	
	
	public static List<Auction> getAuctionsByCategory(Entity ent, AuctionCategory category) {
		
		ArrayList<Auction> list = new ArrayList<Auction>();
		
		if (doesAuctionatorExist(ent)
			&& getAuctionatorCategory(ent).equals(category))
		{
			for (Auction a : Main.instance.Auctions)
			{
				if (a.getCategory().equals(category)) list.add(a);
			}
		}
		
		if (!list.isEmpty())
		{
			return list;
		} else
		{
			return null;
		}
	}
	
	public static boolean doesAuctionatorExist(Entity ent) {
		
		if (Main.instance.Auctionators.containsKey(ent))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public static AuctionCategory getAuctionatorCategory(Entity ent) {
		
		if (doesAuctionatorExist(ent))
		{
			return Main.instance.Auctionators.get(ent).getAuctionCategory();
		} else
		{
			return null;
		}
	}
}
