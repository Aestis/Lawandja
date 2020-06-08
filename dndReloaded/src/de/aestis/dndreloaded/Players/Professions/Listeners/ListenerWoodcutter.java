package de.aestis.dndreloaded.Players.Professions.Listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Recipe;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.Professions.Profession;

public class ListenerWoodcutter implements Listener {

	private final Main Plugin = Main.instance;
	
	
	private void killBlockBreak(BlockBreakEvent event) {
		
		event.setDropItems(false);
		event.getPlayer().sendMessage(Plugin.getConfig().getString("Localization.Profession.General.notallowed"));
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		Profession primary = Plugin.Players.get(player).getProfessionPrimary();
		Profession secondary = Plugin.Players.get(player).getProfessionSecondary();
		Boolean hasProfession = false;
		
		if (primary == null && secondary == null) {
			
			killBlockBreak(event);
			return;
		}
		
		if (primary != null && primary.getName() == Plugin.getConfig().getString("Localization.Professions.woodcutter")) hasProfession = true;
		if (secondary != null && secondary.getName() == Plugin.getConfig().getString("Localization.Professions.woodcutter")) hasProfession = true;
		
		@SuppressWarnings("unchecked")
		List<String> blocks = (List<String>) Plugin.getConfig().getList("Profession.Woodcutter.blocks");
		Boolean isBlock = false;
		
		for (String s : blocks) {
			
			Material evtBlock = event.getBlock().getType();
			Material block = Material.matchMaterial(s);
			
			if (evtBlock == block) isBlock = true;
		}
		
		if (isBlock && hasProfession) {
			
			if (Plugin.getBlockBreakOverride().blockBreakCustomDurability(event)) {
				//Dura Check True
				Plugin.getBlockBreakOverride().blockBreakCustomItems(event);
			}
			//TODO
						
		} else if (isBlock && !hasProfession) {
			
			killBlockBreak(event);
			return;
		}
	}
	
	@EventHandler
	public void onCraftingTableChange(PrepareItemCraftEvent event) {
		
		CraftingInventory inv = event.getInventory();
		Recipe rec = event.getRecipe();
		
		//TODO
	}
	
}
