package de.aestis.dndreloaded.Auctions.Util;

public class Auctionator {

	private final Integer ID;
	
	private java.util.UUID UUID;
	private org.bukkit.Server Server;
	private org.bukkit.World World;	
	private AuctionCategory Category;
	
	public Auctionator (Integer ID) {this.ID = ID;}
	
	public Integer getID() {return this.ID;}
	
	public java.util.UUID getUUID() {return this.UUID;}
	public void setUUID(java.util.UUID UUID) {this.UUID = UUID;}
	
	public org.bukkit.Server getServer() {return this.Server;}
	public void setServer(org.bukkit.Server Server) {this.Server = Server;}
	
	public org.bukkit.World getWorld() {return this.World;}
	public void setWorld(org.bukkit.World World) {this.World = World;}
	
	public AuctionCategory getAuctionCategory() {return this.Category;}
	public void setAuctionCategory(AuctionCategory AuctionCategory) {this.Category = AuctionCategory;}
}
