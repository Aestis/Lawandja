package de.aestis.dndreloaded.itemManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.Events.PlayerDataBeforeUnloadEvent;
import de.aestis.dndreloaded.Players.Events.PlayerDataLoadedEvent;
import de.aestis.dndreloaded.itemManager.enchants.EnchantmentData;

public class EnchantmentLoader implements Listener{
	
	@EventHandler
	public void onPlayerDataLoad(PlayerDataLoadedEvent evt) {
		evt.getPlayerData().setEnchantmentData(new EnchantmentData(evt.getPlayer(), Main.instance));
	}
	
	@EventHandler
	public void onPlayerDataUnload(PlayerDataBeforeUnloadEvent evt) {
		evt.getPlayerData().getEnchantmentData().unload();
	}
}
