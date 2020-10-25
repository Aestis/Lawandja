package de.aestis.dndreloaded.itemManager.enchants.implemet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.InteractEffect;

public class LeapCore implements InteractEffect{

	@Override
	public String getStringRepresentation() {
		return "Sprung";
	}

	@Override
	public String getLevelRepresentation(int level) {
		return "" + level;
	}

	@Override
	public boolean isLevelPrefix() {
		return false;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		return ChatColor.AQUA;
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
	public void onInteract(PlayerInteractEvent evt, Player p, int level, ItemStack itemstacks, int amountOfActiveItems) {
		p.setVelocity(p.getLocation().getDirection().multiply(1 + (0.25 * level)));
	}

	@Override
	public void onInteractEntity(PlayerInteractEntityEvent evt, Player p, int level, ItemStack itemstacks, int amountOfActiveItems) {
		p.setVelocity(p.getLocation().getDirection().multiply(1 + (0.25 * level)));
	}
}
