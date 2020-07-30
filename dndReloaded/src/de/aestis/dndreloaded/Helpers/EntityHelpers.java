package de.aestis.dndreloaded.Helpers;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class EntityHelpers {

	public static boolean isLivingEntity(Entity ent) {
		
		if (ent instanceof LivingEntity) return true;
		
		return false;
	}

	public static boolean isEntityThere(Entity ent) {
		
		if (ent.isDead()
			|| ent.isEmpty()
			|| ent.isValid()) return true;
		
		return false;
	}
	
	
	public static void flushEntities(World world, Boolean living) {
		
		List<Entity> entities = world.getEntities();
		
		for (Entity e : entities)
		{
			if (!living && e instanceof LivingEntity)
			{
				e.remove();
			} else if (living && !(e instanceof LivingEntity))
			{
				e.remove();
			}
		}
	}
	
}
