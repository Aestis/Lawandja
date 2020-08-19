package de.aestis.dndreloaded.Players.Professions.Listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Recipe;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.MathHelpers;
import de.aestis.dndreloaded.Players.Professions.Profession;
import de.aestis.dndreloaded.Players.Professions.ProfessionHandler;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public class ListenerInscriber implements Listener {

	private final Main Plugin = Main.instance;
	
	private void killBlockBreak(BlockBreakEvent event) {
		
		event.setDropItems(false);
		event.getPlayer().sendMessage(Plugin.getConfig().getString("Localization.Professions.General.notallowed"));
	}
	
	private boolean isBlockIncluded(List<String> blocks, Block comparison) {
		
		for (String s : blocks)
		{	
			Material compare = comparison.getType();
			Material input = Material.matchMaterial(s);
			
			if (compare == input) return true;
		}
		return false;
	}
	
	
	/*
	 * Inscriber Block Break Event
	 * (usually increase Profession XP)
	 */
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		/*
		 * Check if Player is located in a Claim
		 * Cancel Event if TRUE
		 */
		Claim claim = GriefPrevention.instance.dataStore.getClaimAt(event.getBlock().getLocation(), true, null);
		if (claim != null) return;
		
		Player player = event.getPlayer();
		Profession primary = Plugin.Players.get(player).getProfessionPrimary();
		Profession secondary = Plugin.Players.get(player).getProfessionSecondary();
		Boolean hasProfession = false;
		
		if (primary == null && secondary == null)
		{	
			killBlockBreak(event);
			return;
		}
		
		if (primary != null && primary.getName() == Plugin.getConfig().getString("Localization.Professions.inscriber"))
		{
			hasProfession = true;
			secondary = null;
		}
		if (secondary != null && secondary.getName() == Plugin.getConfig().getString("Localization.Professions.inscriber"))
		{
			hasProfession = true;
			primary = null;
		}
		
		@SuppressWarnings("unchecked")
		Boolean isBlock = isBlockIncluded((List<String>) Plugin.getConfig().getList("Profession.Inscriber.blocks"), event.getBlock());

		if (isBlock && hasProfession)
		{	
			ProfessionHandler ProfHandler = Plugin.getProfessionHandler();
			Profession prof = null;
			
			if (primary == null) prof = secondary;
			if (secondary == null) prof = primary;
			
			if (prof != null)
			{	
				Integer min = Plugin.getConfig().getInt("Profession.Inscriber.Experience.Pickup.min");
				Integer max = Plugin.getConfig().getInt("Profession.Inscriber.Experience.Pickup.max");
				ProfHandler.addProfessionXP(player, prof, MathHelpers.getRndInt(min, max));
			} else
			{	
				Bukkit.broadcastMessage("Oops! Something went terribly wrong...");
			}
			
		} else if (isBlock && !hasProfession)
		{	
			killBlockBreak(event);
			return;
		}
	}
	
	
	/*
	 * Inscriber Block Place Event
	 * (usually decrease Profession XP)
	 */
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		/*
		 * Check if Player is located in a Claim
		 * Cancel Event if TRUE
		 */
		Claim claim = GriefPrevention.instance.dataStore.getClaimAt(event.getBlock().getLocation(), true, null);
		if (claim != null) return;
		
		Player player = event.getPlayer();
		Profession primary = Plugin.Players.get(player).getProfessionPrimary();
		Profession secondary = Plugin.Players.get(player).getProfessionSecondary();
		Boolean hasProfession = false;
		
		if (primary == null && secondary == null) return;
		
		if (primary != null && primary.getName() == Plugin.getConfig().getString("Localization.Professions.inscriber"))
		{
			hasProfession = true;
			secondary = null;
		}
		if (secondary != null && secondary.getName() == Plugin.getConfig().getString("Localization.Professions.inscriber"))
		{
			hasProfession = true;
			primary = null;
		}
		
		@SuppressWarnings("unchecked")
		Boolean isBlock = isBlockIncluded((List<String>) Plugin.getConfig().getList("Profession.Inscriber.blocks"), event.getBlock());

		if (isBlock && hasProfession)
		{
			ProfessionHandler ProfHandler = Plugin.getProfessionHandler();
			Profession prof = null;
			
			if (primary == null) prof = secondary;
			if (secondary == null) prof = primary;
			
			if (prof != null)
			{	
				Integer max = Plugin.getConfig().getInt("Profession.Inscriber.Experience.Pickup.max");
				
				if (!ProfHandler.removeProfessionXP(player, prof, max))
				{	
					event.setCancelled(true);
					return;
				}
			}
		} else if (isBlock && !hasProfession)
		{
			return;
		}
	}
	
	@EventHandler
	public void onCraftingTableChange(PrepareItemCraftEvent event) {
		
		CraftingInventory inv = event.getInventory();
		Recipe rec = event.getRecipe();
		
		//TODO
	}
	
	public void doInscriberSpellEvent() {
		//TODO
	}
	
}
