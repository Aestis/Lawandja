package de.aestis.dndreloaded.Quests.Listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.RegionHelpers;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Players.PlayerHandler;
import de.aestis.dndreloaded.Quests.QuestSlot;
import de.aestis.dndreloaded.Quests.QuestTypes;

public class TypeEntityKillArea implements Listener {

	private final Main Plugin = Main.instance;
	
	@EventHandler
	public void entityKillQuestEvent (EntityDeathEvent event) {

		LivingEntity mob = event.getEntity();

		if (mob == null) return;
		if (mob.getKiller() == null) return;
		
        if (mob.getKiller().getType() == EntityType.PLAYER)
        {
        	
        	Player player = (Player) mob.getKiller();
        	PlayerData pd = Plugin.Players.get(player);
        	
        	/*
    		 * Get Player Region first
    		 * and compare to both
    		 * defined regions in Quest
    		 */
    		
    		List<ProtectedRegion> regions = RegionHelpers.getPlayerRegions(player);
    		
    		for (ProtectedRegion r : regions)
    		{
    			if (!(pd.getQuestActive1() != null && pd.getQuestActive1().getTarget().equalsIgnoreCase(r.getId()))
					&& !(pd.getQuestActive2() != null && pd.getQuestActive2().getTarget().equalsIgnoreCase(r.getId())))
    			{
    				return;
    			}
    		}

    		
        	/*
        	 * Check if killed Mob is inside
        	 * the specified WorldGuard Region
        	 */
        	
	
        	if ((pd.getQuestActive1() != null && pd.getQuestActive1().getType().equals(QuestTypes.KILL_MOBS_AREA)) ||
    			(pd.getQuestActive2() != null && pd.getQuestActive2().getType().equals(QuestTypes.KILL_MOBS_AREA)))
        	{
        		
        		if (pd.getQuestActive1().getMobType() == mob.getType() ||
    				pd.getQuestActive2().getMobType() == mob.getType())
        		{
        			PlayerHandler ph = Plugin.getPlayerHandler();
        			
        			if (pd.getQuestActive1() != null && pd.getQuestActive1().getMobType() == mob.getType())
        			{
        				ph.increaseQuestVariable(pd, QuestSlot.SLOT_PRIMARY, 1);
        			} else
        			{
        				ph.increaseQuestVariable(pd, QuestSlot.SLOT_SECONDARY, 1);
        			}       			
        		} else
        		{
        			
        			Bukkit.broadcastMessage("KILL_MOBS_AREA | Falsches Vieh :(");
        			
        		}
        		
        	} else
        	{
        		return;
        	}
        } else
        {
        	return;
        }
	}
}