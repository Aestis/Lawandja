package de.aestis.dndreloaded.Helpers.External;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Entites.EntityData;
import de.aestis.dndreloaded.Helpers.EntityHelpers;

public class HolographicDisplaysHelper {
	
	public static void createHolos(Plugin plugin) {
	
		Main.instance.getLogger().info("Creating Holos for tracked Entities (" + Main.instance.TrackedEntities.size() + ")...");
		
		for (Entry<UUID, HashMap<World, EntityData>> entry : Main.instance.TrackedEntities.entrySet())
		{
			
			/*
			 * Check if Entity is valid
			 * and in the list of those
			 * Entites that should get
			 * tracked by this plugin
			 */
			
			Entity ent = Bukkit.getEntity(entry.getKey());
			
			if (ent == null || !ent.isValid())
			{
				Main.instance.getLogger().warning("Skipped Entity by UUID " + entry.getKey() + "!");
				continue;
			}
			
			Main.instance.HoloStorage.put(entry.getKey(), setupHolo(Main.instance, ent));
		}
		
		
		for (Player p : Bukkit.getOnlinePlayers())
		{
			for (Hologram holo : Main.instance.HoloStorage.values())
			{
				if (holo != null
					&& p != null)
				{
					holo.getVisibilityManager().hideTo(Bukkit.getPlayer(p.getName()));
				}
			}
		}
	}
	
	private static Hologram setupHolo(Plugin plugin, Entity ent) {
		
		if (!ent.isValid()) return null;
		if (!EntityHelpers.isLivingEntity(ent)) return null;
		
		Hologram holo = null;
		Double height = ent.getHeight();
		Double offsetY = 0.5;
		Location loc = ent.getLocation();
		
		loc.setY(loc.getY() + height + offsetY);
		
		holo = HologramsAPI.createHologram(plugin, loc);
		holo.appendTextLine(ent.getType().name());
		holo.appendTextLine("§7" + ent.getTicksLived());
		
		VisibilityManager mgr = holo.getVisibilityManager();
		mgr.setVisibleByDefault(false);
		
		return holo;
	}
	
	public static void createHolo(Plugin plugin, Entity ent) {
		
		if (!ent.isValid()) return;
		if (!EntityHelpers.isLivingEntity(ent)) return;
		
		Hologram holo = null;
		Double height = ent.getHeight();
		Double offsetY = 0.5;
		Location loc = ent.getLocation();
		
		loc.setY(loc.getY() + height + offsetY);
		
		holo = HologramsAPI.createHologram(plugin, loc);
		//holo.appendTextLine(ent.getName() + ", §d#" + ent.getEntityId());
		holo.appendTextLine("Schoko stinkt, §d#" + ent.getEntityId());
		holo.appendTextLine("§7" + ent.getTicksLived());
		
		VisibilityManager mgr = holo.getVisibilityManager();
		mgr.setVisibleByDefault(false);
		
		Main.instance.HoloStorage.put(ent.getUniqueId(), holo);
	}
	
	public static void moveHolo(Entity ent, Hologram holo) {
		
		Double height = ent.getHeight();
		Double offsetY = 0.5;
		Location loc = ent.getLocation();
		
		loc.setY(loc.getY() + height + offsetY);
		
		holo.getLine(1).removeLine();
		holo.insertTextLine(1, translateHealthToASCII(ent));
		
		holo.teleport(loc);
	}
	
	
	private static String translateHealthToASCII(Entity ent) {
		
		if (ent instanceof LivingEntity)
		{
			String output = "";
			Double health = ((LivingEntity) ent).getHealth();
			Double maxhealth = ((LivingEntity) ent).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
			
			Double diff = maxhealth / health;
			
			if (diff == 1.0)
			{
				output = "§2|||||||||||||||||||| §f[" + health + "/" + maxhealth + " ♥]"; 
			} else
			{
				int total = (int) Math.round(maxhealth * diff);

				output += "§2";
				for (int i = 0; i < (20 - total); i++) output += "|";
				
				output = "§c";
				for (int i = 0; i < total; i++) output += "|";
				
				output += " §f[" + health + "/" + maxhealth + " ♥]"; 
			}
			
			return output;
		}

		return null;
	}	
}
