package de.aestis.dndreloaded.Helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.aestis.dndreloaded.Helpers.External.WorldGuardHelper;

public class RegionHelpers {

	public static List<ProtectedRegion> getPlayerRegions(Player player) {
		
		World world = BukkitAdapter.adapt(player.getWorld());
        RegionManager rm = WorldGuardHelper.getWorldGuardAPI().getPlatform().getRegionContainer().get(world);
        BlockVector3 pos = BukkitAdapter.asBlockVector(player.getLocation());
        
        List<ProtectedRegion> regions = new ArrayList<ProtectedRegion>();
        
        for (ProtectedRegion r : rm.getApplicableRegions(pos))
        {
        	regions.add(r);
        }
        
        return regions;
	}
	
}
