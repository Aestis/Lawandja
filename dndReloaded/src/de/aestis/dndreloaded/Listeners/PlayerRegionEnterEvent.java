package de.aestis.dndreloaded.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
		
		State usename = region.getFlag(WorldGuardHelper.getLCUseRegionTitle());
		String stringname = region.getFlag(WorldGuardHelper.getLCRegionTitleString());
		
		State usesound = region.getFlag(WorldGuardHelper.getLCUseRegionSound());
		String soundenum = region.getFlag(WorldGuardHelper.getLCRegionSoundEnum());
		
		/*
		 * Check if the Region-Flag
		 * LC_USE_NAME is set to ALLOW
		 * before showing anything
		 */
		if (usename == State.ALLOW)
		{
			if (stringname == ""
				|| stringname.isEmpty())
			{
				player.sendMessage("LC_REGION_NAME is empty! Please type in a text or set Flag LC_USE_NAME to deny.");
			} else
			{
				player.sendTitle("", stringname, 4, 32, 8);
			}
		}
		
		if (usesound == State.ALLOW)
		{
			try
			{
				Sound sound = Sound.valueOf(soundenum);
				player.playSound(player.getLocation(), sound, 1, 1);
			} catch (Exception ex)
			{
				ex.printStackTrace();
				Bukkit.broadcastMessage("&cSetup Error on Region '" + region.getId() + "'. " + soundenum.toUpperCase() + " is not a valid sound!");
			}
		}
	}
	
}
