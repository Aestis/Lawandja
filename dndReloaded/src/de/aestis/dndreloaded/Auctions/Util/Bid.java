package de.aestis.dndreloaded.Auctions.Util;

import org.bukkit.entity.Player;

public class Bid {

	private final Integer ID;
	
	private Player Bidder;
	private Double Amount;
	private Integer Timestamp;
	
	public Bid (Integer ID) {this.ID = ID;}
	
	public Integer getID() {return this.ID;}
	
	public Player getPlayer() {return this.Bidder;}
	public void setPlayer(Player Bidder) {this.Bidder = Bidder;}
	
	public Double getAmount() {return this.Amount;}
	public void setAmount(Double Amount) {this.Amount = Amount;}
	
	public Integer getTimestamp() {return this.Timestamp;}
	public void setTimestamp(Integer Timestamp) {this.Timestamp = Timestamp;}
}
