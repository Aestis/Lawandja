package de.aestis.dndreloaded.itemManager.enchants.implemet;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.InteractEffect;

public class TeleportCore implements InteractEffect{

	public static int DISTANCE = 10;
	
	@Override
	public String getStringRepresentation() {
		return "Teleport";
	}

	@Override
	public String getLevelRepresentation(int level) {
		String s = "";
		for (int i = 0; i<level;i++) {
			s = s + "I";
		}
		return s;
	}

	@Override
	public boolean isLevelPrefix() {
		return false;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		return ChatColor.YELLOW;
	}

	@Override
	public boolean isInvisible() {
		return false;
	}

	@Override
	public EnchantmentGroup getGroup() {
		return EnchantmentGroup.NORMAL;
	}

	@Override
	public void onInteract(PlayerInteractEvent evt, Player interactor, int level, ItemStack itemstacks, int amountOfActiveItems) {
		List<Block> blocks = interactor.getLineOfSight(Sets.newHashSet(new Material[]{Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.TALL_GRASS, Material.GRASS}), level * DISTANCE);


		for (int i = 0; i<blocks.size();i++) {
			Location loc = blocks.get(i).getLocation();
			if (!blocks.get(i).getType().isSolid()) {
				if (!loc.add(0, 1, 0).getBlock().getType().isSolid()) {
					loc.setYaw(interactor.getLocation().getYaw());
					loc.setPitch(interactor.getLocation().getPitch());
					interactor.teleport(loc);
				} else if(!loc.add(0, -1, 0).getBlock().getType().isSolid()) {
					loc = loc.add(0,-1,0);
					loc.setYaw(interactor.getLocation().getYaw());
					loc.setPitch(interactor.getLocation().getPitch());
					interactor.teleport(loc);
				}
			} else {
				if (!loc.add(0, 1, 0).getBlock().getType().isSolid() && !loc.add(0, 2, 0).getBlock().getType().isSolid()) {
					loc = loc.add(0,-1,0);
					loc.setYaw(interactor.getLocation().getYaw());
					loc.setPitch(interactor.getLocation().getPitch());
					interactor.teleport(loc);
				}
			}
		}
		evt.setCancelled(true);
	}

	@Override
	public void onInteractEntity(PlayerInteractEntityEvent evt, Player interactor, int level, ItemStack itemstacks, int amountOfActiveItems) {}

}
