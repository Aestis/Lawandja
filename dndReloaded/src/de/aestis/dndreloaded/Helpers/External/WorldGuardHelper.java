package de.aestis.dndreloaded.Helpers.External;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import de.aestis.dndreloaded.Main;

public class WorldGuardHelper {

	private static StateFlag LC_USE_NAME;
	private static StringFlag LC_REGION_NAME;
	private static StateFlag LC_USE_SOUND;
	private static StringFlag LC_SOUND_ENUM;
	
	public static WorldGuardPlugin getWorldGuard() {
		
		Plugin plugin = Main.instance.getServer().getPluginManager().getPlugin("WorldGuard");
		
		if (plugin == null
			|| !(plugin instanceof WorldGuardPlugin))
		{
			return null;
		}
		
		return (WorldGuardPlugin) plugin;
	}
	
	public static WorldGuard getWorldGuardAPI() {
		
		return WorldGuard.getInstance();
	}
	
	/**
	 * Used to setup pre-defined
	 * 3rd-Party Flags when running
	 * compatible WorldGuard Plugin
	 * @param api (WorldGuardAPI)
	 */
	public static void setupCustomFlag(WorldGuard api) {
	
		FlagRegistry registry = api.getFlagRegistry();
		
		try
		{
			/*
			 * Create custom Flags to
			 * determine if 3rd-Party
			 * RegionEnter titles should
			 * be used or not
			 */
			
			StateFlag usename = new StateFlag("lc-use-name", false);
			registry.register(usename);
			LC_USE_NAME = usename;

			StringFlag stringname = new StringFlag("lc-region-name");
			registry.register(stringname);
			LC_REGION_NAME = stringname;
			
			
			
			/*
			 * Create custom Flags to
			 * determine if 3rd-Party
			 * RegionEnter sounds should
			 * be used or not
			 */
			
			StateFlag usesound = new StateFlag("lc-use-sound", false);
			registry.register(usesound);
			LC_USE_SOUND = usesound;
			
			StringFlag stringsoundenum = new StringFlag("lc-sound-enum");
			registry.register(stringsoundenum);
			LC_SOUND_ENUM = stringsoundenum;
		} catch (FlagConflictException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static StateFlag getLCUseRegionTitle() {
		
		return LC_USE_NAME;
	}
	
	public static StringFlag getLCRegionTitleString() {
		
		return LC_REGION_NAME;
	}
	
	public static StateFlag getLCUseRegionSound() {
		
		return LC_USE_SOUND;
	}
	
	public static StringFlag getLCRegionSoundEnum() {
		
		return LC_SOUND_ENUM;
	}
	
}
