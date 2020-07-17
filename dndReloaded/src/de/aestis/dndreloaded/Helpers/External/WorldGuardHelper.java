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
			 * Create boolean Flag to
			 * determine if 3rd-Party
			 * Region Name should be
			 * utilized or not
			 */
			
			StateFlag use = new StateFlag("lc-use-name", false);
			registry.register(use);
			LC_USE_NAME = use;
			
			/*
			 * Next create String Flag
			 * to later input separate
			 * Nametag for this Region
			 */
			
			StringFlag name = new StringFlag("lc-region-name");
			registry.register(name);
			LC_REGION_NAME = name;
		} catch (FlagConflictException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static StateFlag getLCUse() {
		
		return LC_USE_NAME;
	}
	
	public static StringFlag getLCName() {
		
		return LC_REGION_NAME;
	}
	
}
