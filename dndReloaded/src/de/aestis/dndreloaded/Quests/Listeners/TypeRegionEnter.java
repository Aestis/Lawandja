package de.aestis.dndreloaded.Quests.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.External.WorldGuard.RegionEnterEvent;
import de.aestis.dndreloaded.Players.PlayerData;

public class TypeRegionEnter implements Listener {

	private final Main Plugin = Main.instance;
	
	@EventHandler
	public void playerRegionEnterQuestEvent (RegionEnterEvent event) {

		ProtectedRegion region = event.getRegion();
		
		if (region != null)
		{
			Player player = event.getPlayer();
			PlayerData pd = Plugin.Players.get(player);
			
			if (pd.getQuestActive1().getTarget() != null
				|| pd.getQuestActive2().getTarget() != null)
			{
				if (region.getId().equalsIgnoreCase(pd.getQuestActive1().getTarget()))
				{
					
					//Entered Questregion 1
					
				} else if (region.getId().equalsIgnoreCase(pd.getQuestActive1().getTarget()))
				{
					
					//Entered Questregion 2
					
				}
			}
		}
	}
}
