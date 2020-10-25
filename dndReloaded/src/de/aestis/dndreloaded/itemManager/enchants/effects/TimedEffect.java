package de.aestis.dndreloaded.itemManager.enchants.effects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.itemManager.ItemLoader;
import de.aestis.dndreloaded.itemManager.events.ArmorEquipEvent;
import oxolotel.utils.DoubleWrapper;

/**
 * should only be used on Armor items. For other items use InteractEffect
 */
public interface TimedEffect extends EquipEffect{

	static JavaPlugin plugin = Main.instance;
	HashMap<TimedEffect,Map<String,DoubleWrapper<Integer, Integer>>> activeEffects = new HashMap<>();
	Set<String> activePlayers = new HashSet<>();
	BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {for(TimedEffect e:activeEffects.keySet()) for(String s:activeEffects.get(e).keySet()) e.onActivate(Bukkit.getPlayer(s),activeEffects.get(e).get(s).getValue1(),activeEffects.get(e).get(s).getValue2());}, 10, 2);
	
	/**
	 * Will be called every 2 Ingame ticks.
	 * @param p the player that hat the enchantment equipped
	 * @param level the level of the enchantment
	 * @param amountOfActiveItems the amount of items contributing to the enchantment level
	 */
	void onActivate(Player p, int level, int amountOfActiveItems);
	
	/**
	 * Cancels the task to tick all enchantments. Warning cant be restarted
	 */
	default void deactivateTimer() {
		task.cancel();
	}
	
	@Override
	default void onEquipChange(ArmorEquipEvent evt, Player p, ItemStack newArmor, ItemStack oldArmor, int oldLevel, int newLevel, int amountOfActiveItems) {
		if (!activeEffects.containsKey(this)) {
			activeEffects.put(this, new HashMap<>());
		}
		if (newLevel > 0) {
			activeEffects.get(this).put(p.getName(), new DoubleWrapper<Integer, Integer>(newLevel, amountOfActiveItems));
			activePlayers.add(p.getName());
		} else {
			activeEffects.get(this).remove(p.getName());
		}
	}
	
	@EventHandler
	default void onLogout(PlayerQuitEvent evt) {
		for (TimedEffect e:activeEffects.keySet()) {
			activeEffects.get(e).remove(evt.getPlayer().getName());
		}
		activePlayers.remove(evt.getPlayer().getName());
	}
}
