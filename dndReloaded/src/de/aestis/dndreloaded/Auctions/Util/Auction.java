package de.aestis.dndreloaded.Auctions.Util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.Auctions.Util.AuctionCategory.AuctionCategories;

public class Auction {

	private final Integer ID;
	
	private AuctionCategories Category;
	private String Faction;
	private ItemStack Item;
	private Integer Amount;
	private Double Price;
	private Boolean Bidding;
	private List<Bid> Biddings = new ArrayList<Bid>();
	private Player Seller;
	
	private Integer Timestamp;
	private Integer Duration;
	
	public Auction (Integer ID) {this.ID = ID;}
	
	public Integer getID() {return this.ID;}
	
	public AuctionCategories getCategory() {return this.Category;}
	public void setCategory(AuctionCategories Category) {this.Category = Category;}
	
	public String getFaction() {return this.Faction;}
	public void setFaction(String Faction) {this.Faction = Faction;}
	
	public ItemStack getItem() {return this.Item;}
	public void setItem(ItemStack Item) {this.Item = Item;}
	
	public Integer getAmount() {return this.Amount;}
	public void setAmount(Integer Amount) {this.Amount = Amount;}
	
	public Double getPrice() {return this.Price;}
	public void setPrice(Double Price) {this.Price = Price;}
	
	public Player getSeller() {return this.Seller;}
	public void setSeller(Player Seller) {this.Seller = Seller;}
	
	public Integer getTimestamp() {return this.Timestamp;}
	public void setTimestamp(Integer Timestamp) {this.Timestamp = Timestamp;}

	public Integer getDuration() {return this.Duration;}
	public void setDuration(Integer Duration) {this.Duration = Duration;}
	
}