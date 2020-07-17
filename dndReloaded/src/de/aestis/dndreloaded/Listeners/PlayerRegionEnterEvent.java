package de.aestis.dndreloaded.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.aestis.dndreloaded.Helpers.External.WorldGuardHelper;
import de.aestis.dndreloaded.Helpers.External.WorldGuard.RegionEnterEvent;

public class PlayerRegionEnterEvent implements Listener {

	@EventHandler
	public void playerRegionEnterEvent(RegionEnterEvent event) {
		
		Player player = event.getPlayer();
		ProtectedRegion region = event.getRegion();
		State use = region.getFlag(WorldGuardHelper.getLCUse());
		String title = region.getFlag(WorldGuardHelper.getLCName());
		
		/*
		 * Check if the Region-Flag
		 * LC_USE_NAME is set to ALLOW
		 * before showing anything
		 */
		if (use == State.ALLOW)
		{
			if (title == ""
				|| title.isEmpty())
			{
				player.sendMessage("LC_REGION_NAME is empty! Please type in a text or set Flag LC_USE_NAME to deny.");
			} else
			{
				player.sendTitle("", title, 4, 32, 8);
			}
		}
		
	}
	
}
