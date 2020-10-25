package de.aestis.dndreloaded.itemManager.items.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.items.AttackSpeed;
import de.aestis.dndreloaded.itemManager.items.ItemFlag;

public class AttackSpeedListener implements Listener{

	//TODO add integration of Attack Speed modifying enchantments
	
	@EventHandler(priority = EventPriority.LOW)
	public void onInteract(PlayerInteractEvent evt) {
		Player p = evt.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		if (item != null) {
			if (!p.hasCooldown(item.getType())) {
				p.setCooldown(item.getType(), ItemFlag.get(item, ItemFlag.ATTACK_SPEED, AttackSpeed.SUPER_FAST).getCooldownTicks());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onInteractEntity(PlayerInteractEntityEvent evt) {
		Player p = evt.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		if (item != null) {
			if (!p.hasCooldown(item.getType())) {
				p.setCooldown(item.getType(), ItemFlag.get(item, ItemFlag.ATTACK_SPEED, AttackSpeed.SUPER_FAST).getCooldownTicks());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDamageEntityByEntity(EntityDamageByEntityEvent evt) {
		if (evt.getDamager() instanceof Player) {
			Player p = (Player) evt.getDamager();
			ItemStack item = p.getInventory().getItemInMainHand();
			if (item != null) {
				if (!p.hasCooldown(item.getType())) {
					p.setCooldown(item.getType(), ItemFlag.get(item, ItemFlag.ATTACK_SPEED, AttackSpeed.SUPER_FAST).getCooldownTicks());
				} else {
					evt.setCancelled(true);
				}
			}
		}
	}
}
