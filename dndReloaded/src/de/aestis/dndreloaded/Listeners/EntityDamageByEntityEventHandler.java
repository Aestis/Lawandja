package de.aestis.dndreloaded.Listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.aestis.dndreloaded.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class EntityDamageByEntityEventHandler implements Listener {

	private final Main Plugin = Main.instance;	
	private final FileConfiguration Config = Plugin.getConfig();
	
	@EventHandler
	public void playerHitEntityEvent(EntityDamageByEntityEvent event) {

		if (event.getDamager() instanceof Player)
		{
			Player player = (Player) event.getDamager();
			Entity entity = event.getEntity();
			
			if (entity instanceof LivingEntity)
			{				
				String name = entity.getCustomName();
				
				if (name == null) name = entity.getType().name();
				Double health = ((LivingEntity) entity).getHealth();
				Double maxHealth = ((LivingEntity) entity).getMaxHealth();
				
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§d" + name + "§f: §c" + health + "§f/§2" + maxHealth + " §c♥"));
			} else
			{
				return;
			}
		} else
		{
			return;
		}
		
	}
	
}
