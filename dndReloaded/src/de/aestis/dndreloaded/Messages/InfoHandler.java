package de.aestis.dndreloaded.Messages;

import java.util.ArrayList;
import java.util.List;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;

public class InfoHandler {

	private static InfoHandler instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public static InfoHandler getInstance() {
		if (instance == null) {
			instance = new InfoHandler();
		}
		return instance;
	}
	
	public List<String> getQuestInfo(Quest quest) {
		
		List<String> info = new ArrayList<>();
		
		info.add("§eShowing info for Quest '" + quest.getTitle() + "' {" + quest.getID() + "}...");
		info.add("§eLinked NPC: §2" + quest.getNpcID());
		
		if (quest.getRequired() == 0 || quest.getRequired() == null)
		{
			info.add("§eQuest Required: §cFALSE");
		} else
		{
			info.add("§eQuest Required: §2TRUE {" + quest.getRequired() + "}");
		}
		
		if (quest.getFaction() == null)
		{
			info.add("§eQuest Faction: §cUNSET §7(FREE, etc, etc) §d*required");
		} else
		{
			info.add("§eQuest Faction: §2" + quest.getFaction());
		}
		
		if (quest.getMinReputation() == null)
		{
			info.add("§eQuest Minimum Reputation: §cUNSET §7(0 or > 0) §d*required");
		} else
		{
			info.add("§eQuest Minimum Reputation: §2" + quest.getMinReputation());
		}
		
		if (quest.getIcon() == null)
		{
			info.add("§eQuest Icon: §cUNSET §7(minecraft:material) §d*required");
		} else
		{
			info.add("§eQuest Icon: §2" + quest.getIcon().getType().name());
		}
		
		if (quest.getDescription() == null)
		{
			info.add("§eQuest Description: §cUNSET §7(text) §d*required");
		} else
		{
			Integer length = 45;
			if (quest.getDescription().length() < 45) length = quest.getDescription().length();
			info.add("§eQuest Description: §2" + quest.getDescription().substring(0, length));
		}
		
		if (quest.getTarget() == null)
		{
			info.add("§eQuest Target: §cFALSE");
		} else
		{
			info.add("§eQuest Target: §2" + quest.getTarget());
		}
		
		if (quest.getShort() == null)
		{
			info.add("§eQuest Short: §cUNSET §7(text) §d*required");
		} else
		{
			info.add("§eQuest Target: §2" + quest.getShort());
		}
		
		if (quest.getMessageAccept() == null)
		{
			info.add("§eQuest Msg Accept: §cUNSET §7(text) §d*required");
		} else
		{
			info.add("§eQuest Msg Accept: §2" + quest.getMessageAccept());
		}
		
		if (quest.getMessageDecline() == null)
		{
			info.add("§eQuest Msg Decline: §cUNSET §7(text) §d*required");
		} else
		{
			info.add("§eQuest Msg Decline: §2" + quest.getMessageDecline());
		}
		
		if (quest.getMessageRunning() == null)
		{
			info.add("§eQuest Msg Running: §cUNSET §7(text) §d*required");
		} else
		{
			info.add("§eQuest Msg Running: §2" + quest.getMessageRunning());
		}
		
		if (quest.getMessageFail() == null)
		{
			info.add("§eQuest Msg Fail: §cUNSET §7(text) §d*required");
		} else
		{
			info.add("§eQuest Msg Fail: §2" + quest.getMessageFail());
		}
		
		if (quest.getMessageSuccess() == null)
		{
			info.add("§eQuest Msg Success: §cUNSET §7(text) §d*required");
		} else
		{
			info.add("§eQuest Msg Success: §2" + quest.getMessageSuccess());
		}
		
		if (quest.getStarterItem() == null)
		{
			info.add("§eQuest Starter Item: §cFALSE");
		} else
		{
			info.add("§eQuest Starter Item: §2" + quest.getStarterItem().getItemID().getMaterial() + " x" + (quest.getStarterItem() instanceof SimpleItem ? quest.getStarterItem().getSpigotItem().getAmount() : 1));
		}
		
		return info;
	}
	
}
