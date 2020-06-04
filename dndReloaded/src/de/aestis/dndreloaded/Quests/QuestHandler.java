package de.aestis.dndreloaded.Quests;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Util.QuestSelectorMenu;

import oxolotel.inventoryMenuManager.InventoryMenuManager;

public class QuestHandler {
	
	private static QuestHandler instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public static QuestHandler getInstance() {
		if (instance == null) {
			instance = new QuestHandler();
		}
		return instance;
	}
	
	
	public void initialize()  {

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){

            @Override
            public void run(){
            	Bukkit.getServer().broadcastMessage("Fetching Questgivers...");
            	Bukkit.broadcastMessage("");
            	for (World w : Bukkit.getServer().getWorlds()) {
        			Bukkit.broadcastMessage("Showing Questgivers for world = " + w.getName());
        			for (Entity e : w.getEntities()) {
        				if (e.getType() == EntityType.PLAYER) {
        					Bukkit.broadcastMessage(" - " + e.getCustomName() + " #" + e.getEntityId() + " || " + e.getUniqueId() + " (" + e.getType() + ")");
        					Bukkit.broadcastMessage("Fetching Quests for: " + e.getUniqueId() + "...");
        					Bukkit.broadcastMessage("");
        					
        					DatabaseHandler db = Plugin.getDatabaseHandler();
        					
        					List<Quest> quests = db.getQuestDataNEW(e.getUniqueId().toString());
        					
        					for (Quest q : quests) {
        						Plugin.QuestData.insertQuest(q.getNpcID(), q.getID(), q);
        						/*
        						 * Debug Stuff
        						 * Remove Later!
        						 */
        						String title = Plugin.QuestData.getQuestByID(q.getNpcID(), q.getID()).getTitle();
        						String npc = Plugin.QuestData.getQuestByID(q.getNpcID(), q.getID()).getNpcID();			
        						Bukkit.broadcastMessage("§c Quest: '" + title + "' from NPC " + npc);
        					}
        				}	
        			}
        		}            	
            }
        }, 50L);
	}
	
	
	//EventListener
	
	public void handleQuestgiverInteraction (PlayerInteractEntityEvent event) {

		if (event.getHand() == EquipmentSlot.OFF_HAND) return;	
		
		Entity npc = event.getRightClicked();
		String npcUUID = npc.getUniqueId().toString();
		Player player = event.getPlayer();

		Bukkit.broadcastMessage("§6Player §2" + player.getName() + "§6 selected Entity §2" + npc.getUniqueId());
		Plugin.SelectedNPC.put(player, npc.getUniqueId().toString());
	
		if (npc.getCustomName() != null) {
			
			Bukkit.broadcastMessage("§6Checking for §2" + npc.getUniqueId().toString() + "§6...");
			
			if (Plugin.QuestData.hasQuests(npcUUID)) {
				
				Bukkit.broadcastMessage("§2NPC Has Something. Let Me Fetch It For You...");
				
				List<HashMap<Integer, Quest>> quests = Plugin.QuestData.getAllQuestsFromNPC(npcUUID);
				InventoryMenuManager.getInstance().openMenu(player, new QuestSelectorMenu(quests));
				
			} else {
				Bukkit.broadcastMessage("§cNPC Hasn't Got Any Quest For You, Yet.");
			}
		}
		
		System.out.println(Plugin.SelectedNPC);
	}
}
