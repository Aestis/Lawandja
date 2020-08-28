package de.aestis.dndreloaded.Chat.Util;

public class ChatMode {
	
	private final Integer ID;
	
	private Integer ChannelID;
	private Integer LastChannelID;
	
	public ChatMode (Integer ID) {
		
		this.ID = ID;
		this.ChannelID = 1;
		this.LastChannelID = 0;
	}
	
	public Integer getID() {return this.ID;}
	
	public Integer getChannelID() {return this.ChannelID;}
	public void setChannelID(Integer ChannelID) {
		
		if (this.ChannelID != null)
		{
			setLastChannelID(this.ChannelID);
		}
		this.ChannelID = ChannelID;
	}
	
	public Integer getLastChannelID() {return this.LastChannelID;}
	public void setLastChannelID(Integer LastChannelID) {this.LastChannelID = LastChannelID;}
	
}
