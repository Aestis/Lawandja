package de.aestis.dndreloaded.Entites;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import de.aestis.dndreloaded.Main;

public class EntityHandler {

	public static void initializeTrackerSetup() {

		for (World w : Bukkit.getServer().getWorlds())
		{
			Integer entcnt = 0;
			
			Main.instance.getLogger().info("Initialize EntityTracker for World '" + w.getName() + "'.");
			
			for (Entity ent : w.getEntities())
			{
				if (ent.getType().equals(EntityType.ARMOR_STAND)) {
					
					Main.instance.getLogger().severe("ARMORSTAND got blocked from Tracker!");
					continue;
				}
				
				EntityData ed = new EntityData(ent.getUniqueId());
				HashMap<World, EntityData> map = new HashMap<World, EntityData>();
				
				map.put(w, ed);
				Main.instance.TrackedEntities.put(ent.getUniqueId(), map);
				
				entcnt++;
			}
			
			Main.instance.getLogger().info(" > Successfully registered " + entcnt + " Entites!");
		}
		
		Main.instance.getLogger().info("Total of " + Main.instance.TrackedEntities.size() + " Entites tracked!");
	}
	
}
