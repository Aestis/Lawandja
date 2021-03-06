package de.aestis.dndreloaded.Quests.Listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.RegionHelpers;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Players.PlayerHandler;
import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.Quests.QuestHandler;
import de.aestis.dndreloaded.Quests.QuestSlot;
import de.aestis.dndreloaded.Quests.QuestTypes;

public class TypeBlockBreakArea implements Listener {

	private final Main Plugin = Main.instance;
	
	@EventHandler
	public void blockBreakAreaQuestEvent (BlockBreakEvent event) {
		
		Player player = (Player) event.getPlayer();
		
		if (player == null) return;
		
		if (event.getBlock() != null
			|| event.getBlock().getType() != Material.AIR)
		{
			
			/*
			 * 1. Check for QuestTypes
			 * 2. Check for Regions
			 * 3. Check for Block
			 */

			PlayerData pd = Plugin.Players.get(player);
			
			if (pd.getQuestActive1() == null
					&& pd.getQuestActive2() == null) return;
			
			List<ProtectedRegion> regions = RegionHelpers.getPlayerRegions(player);
			Quest q = null;
			
			if (pd.getQuestActive1() != null && pd.getQuestActive1().getType().equals(QuestTypes.BLOCK_BREAK_AREA))
			{
				q = pd.getQuestActive1();
			} else if (pd.getQuestActive2() != null && pd.getQuestActive2().getType().equals(QuestTypes.BLOCK_BREAK_AREA))
			{
				q = pd.getQuestActive2();
			}
			
			if (q == null) return;

				
			for (ProtectedRegion r : regions)
    		{
    			if (q.getRegion().equalsIgnoreCase(r.getId()))
    			{
    				if (event.getBlock().getType().equals(q.getBlockMaterial()))
	    			{
    					
    					PlayerHandler ph = Plugin.getPlayerHandler();
    					QuestHandler qh = Plugin.getQuestHandler();
    					QuestSlot slot = qh.getSlotByQuest(player, q);
    					
    					if (slot == null) return;
    					if (slot == QuestSlot.SLOT_PRIMARY)
    					{
    						ph.increaseQuestVariable(pd, slot, 1);
    						player.sendMessage("Quest Slot = Primary");
    					} else if (slot == QuestSlot.SLOT_SECONDARY)
    					{
    						ph.increaseQuestVariable(pd, slot, 1);
    						player.sendMessage("Quest Slot = Secondary");
    					}
    					
    					player.sendMessage("Richtiger block AREA! -> " + event.getBlock().getType().name());
    				}	
    			}
        	}
		}
	}
}
