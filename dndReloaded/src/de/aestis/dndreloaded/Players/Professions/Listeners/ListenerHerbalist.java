package de.aestis.dndreloaded.Players.Professions.Listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.Professions.Profession;

public class ListenerHerbalist implements Listener {

	private final Main Plugin = Main.instance;
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		Profession primary = Plugin.Players.get(player).getProfessionPrimary();
		Profession secondary = Plugin.Players.get(player).getProfessionSecondary();
		Boolean hasProfession = false;
		
		if (primary == null && secondary == null) return;
		
		if (primary != null && primary.getName() == Plugin.getConfig().getString("Localization.Professions.herbalist")) hasProfession = true;
		if (secondary != null && secondary.getName() == Plugin.getConfig().getString("Localization.Professions.herbalist")) hasProfession = true;
		
		@SuppressWarnings("unchecked")
		List<String> blocks = (List<String>) Plugin.getConfig().getList("Profession.Herbalist.blocks");
		
		for (String s : blocks) {
			
			Material evtBlock = event.getBlock().getType();
			Material block = Material.matchMaterial(s);
			
			if (evtBlock == block && hasProfession) {
				
				player.sendMessage("Du kannst das :P");
				return;
			}
		}

		event.setDropItems(false);
		player.sendMessage("Du weiﬂt nicht, wie man das abbaut!");
		return;
	}
	
}
