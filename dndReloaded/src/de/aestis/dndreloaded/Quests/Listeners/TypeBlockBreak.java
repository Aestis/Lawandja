package de.aestis.dndreloaded.Quests.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.PlayerData;

public class TypeBlockBreak {

	private final Main Plugin = Main.instance;
	
	@EventHandler
	public void blockBreakQuestEvent (BlockBreakEvent event) {
		
		Player player = (Player) event.getPlayer();
		
		if (player == null) return;
		
		if (event.getBlock() != null
			|| event.getBlock().getType() != Material.AIR)
		{
			PlayerData pd = Plugin.Players.get(player);
			
			//TODO
		}
	}
}
