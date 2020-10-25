package de.aestis.dndreloaded.itemManager.enchants.implemet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.InteractEffect;
import de.aestis.dndreloaded.itemManager.items.CustomItem;

public class LightningCore implements InteractEffect{

	@Override
	public String getStringRepresentation() {
		return "Lightning Strike";
	}

	@Override
	public String getLevelRepresentation(int level) {
		return "I";
	}

	@Override
	public boolean isLevelPrefix() {
		return false;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		return ChatColor.DARK_RED;
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
	public void onInteract(PlayerInteractEvent evt, Player interactor, int level, ItemStack item, int amountOfActiveItems) {
		if (!isItemInCooldown(item)) {
			Location loc = getLookedAtLocation(interactor, 50);
			loc.getWorld().strikeLightning(loc);
			setItemCooldown(item, 5);
			callItemConsumeEvent(interactor, CustomItem.loadCustomItem(item, false));
			evt.setCancelled(true);
		} else {
			interactor.sendMessage("This Item is currently in Cooldown");
			evt.setCancelled(true);
		}
	}

	@Override
	public void onInteractEntity(PlayerInteractEntityEvent evt, Player interactor, int level, ItemStack item, int amountOfActiveItems) {
		onInteract(null, interactor, level, item, amountOfActiveItems);
	}

	
	
}
