package de.aestis.dndreloaded.itemManager.enchants.implemet;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.Temporaleffect;
import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.InteractEffect;

public class ElytraFlightCore implements InteractEffect{

	private static Plugin plugin = Main.instance;
	static Set<String> glidingPlayers = new HashSet<>();
	
	public static class GlideHandler implements Listener {
		
		@EventHandler
		public void onGlideToggle(EntityToggleGlideEvent evt) {
			Player p = (Player) evt.getEntity();
			if (glidingPlayers.contains(p.getName()) && !evt.isGliding()) {
				if (isOnGround(p)) {
					glidingPlayers.remove(p.getName());
					return;
				}
				evt.setCancelled(true);
			}
		}
		
		public boolean isOnGround(Player p) {
			Location loc = p.getLocation();
			if (loc.add(0,-0.1,0).getBlock().getType() != Material.AIR) {
				return true;
			}
			return false;
		}
	}

	public ElytraFlightCore() {
		Bukkit.getServer().getPluginManager().registerEvents(new GlideHandler(), plugin);
	}
	
	@Override
	public String getStringRepresentation() {
		return "Elytra Flug";
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
		return EnchantmentGroup.SPECIAL;
	}

	@Override
	public void onInteract(PlayerInteractEvent evt, Player p, int level, ItemStack itemstack, int amountOfActiveItems) {
		if (!isItemInCooldown(itemstack)) {
			p.setVelocity(p.getLocation().getDirection());
			glidingPlayers.add(p.getName());
			setItemCooldown(itemstack, 5 * level);
			new Temporaleffect(() -> p.setGliding(true), () -> {p.setGliding(false); glidingPlayers.remove(p.getName());}, 5 * level).activate();
		}
	}

	@Override
	public void onInteractEntity(PlayerInteractEntityEvent evt, Player p, int level, ItemStack itemstack, int amountOfActiveItems) {
		onInteract(null, p, level, itemstack, amountOfActiveItems);
	}

}
