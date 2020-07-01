package de.aestis.dndreloaded.Helpers.External;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import de.aestis.dndreloaded.Main;

public class WorldGuardHelper {

	private static WorldGuardHelper instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	private WorldGuard API = WorldGuard.getInstance();

	private static StateFlag LC_USE_NAME;
	private static StringFlag LC_REGION_NAME;
	
	public static WorldGuardHelper getInstance() {
		if (instance == null) {
			instance = new WorldGuardHelper();
		}
		return instance;
	}
	
	public boolean isWorldGuardInitialized() {
		
		if (API != null)
		{
			return true;
		} else
		{
			API = WorldGuard.getInstance();
			return false;
		}
	}
	
	public WorldGuard getAPI() {
		
		if (isWorldGuardInitialized())
		{
			return API;
		} else
		{
			return API;
		}
	}
	
	
	public boolean entityInsideRegion (Entity entity) {
		
		if (entity == null) return false;
		
		Location loc = entity.getLocation();
		World world = entity.getWorld();
		
		
		
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager regions = container.get((com.sk89q.worldedit.world.World) world);
		
		
		
		//for (ProtectedRegion r : API.)
		
		return false;
	}
	
	
	public void setupCustomFlag() {
		
		API.disable();
		
		FlagRegistry registry = API.getFlagRegistry();
		
		try
		{
			/*
			 * Create Boolean Flag first
			 */
			
			StateFlag use = new StateFlag("lc-use-name", false);
			registry.register(use);
			LC_USE_NAME = use;
			
			/*
			 * Next create String Flag
			 */
			StringFlag name = new StringFlag("lc-region-name");
			registry.register(name);
			LC_REGION_NAME = name;
			
			API.setup();
			
			Plugin.getLogger().severe("Custom Flags ('lc-region-name', 'lc-region-name') successfully set up!"); 
			
		} catch (FlagConflictException ex)
		{
			
		}
	}
	
}
