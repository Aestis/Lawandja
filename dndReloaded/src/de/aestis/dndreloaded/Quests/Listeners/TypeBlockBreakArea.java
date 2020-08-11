package de.aestis.dndreloaded.Quests.Listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.RegionHelpers;
import de.aestis.dndreloaded.Players.PlayerData;

public class TypeBlockBreakArea {

	private final Main Plugin = Main.instance;
	
	@EventHandler
	public void blockBreakAreaQuestEvent (BlockBreakEvent event) {
		
		Player player = (Player) event.getPlayer();
		
		if (player == null) return;
		
		if (event.getBlock() != null
			|| event.getBlock().getType() != Material.AIR)
		{
			PlayerData pd = Plugin.Players.get(player);
			List<ProtectedRegion> regions = RegionHelpers.getPlayerRegions(player);
    		
    		for (ProtectedRegion r : regions)
    		{
    			if (!(pd.getQuestActive1() != null && pd.getQuestActive1().getTarget().equalsIgnoreCase(r.getId()))
					&& !(pd.getQuestActive2() != null && pd.getQuestActive2().getTarget().equalsIgnoreCase(r.getId())))
    			{
    				return;
    			}
    		}
    		
    		//TODO			
		}
	}
}
