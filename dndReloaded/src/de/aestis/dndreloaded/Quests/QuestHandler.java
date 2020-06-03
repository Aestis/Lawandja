package de.aestis.dndreloaded.Quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Database.DatabaseHandler;

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
			
			DatabaseHandler Database = Plugin.getDatabaseHandler();	
			Bukkit.broadcastMessage("§6Checking for §2" + npc.getUniqueId().toString() + "§6...");
			
			if (Plugin.QuestData.hasQuests(npcUUID)) {
				
				Bukkit.broadcastMessage("§2NPC Has Something. Let Me Fetch It For You...");
				
				List<HashMap<Integer, Quest>> quests = Plugin.QuestData.getAllQuestsFromNPC(npcUUID);
				
				Inventory gui = getQuestListInventory(quests, player, npcUUID);
				player.openInventory(gui);
				
				/*Quest q = Database.getQuestData(npc.getUniqueId().toString());

				if (q != null) {
	
					Inventory gui = getQuestSpecificInventory(q, player, npc.getUniqueId().toString(), npc.getName());
					player.openInventory(gui);
				}*/
			} else {
				Bukkit.broadcastMessage("§cNPC Hasn't Got Any Quest For You, Yet.");
			}
		}
		
		System.out.println(Plugin.SelectedNPC);
	}
	
	
	private Inventory getQuestListInventory (List<HashMap<Integer, Quest>> quests, Player player, String uuid) {
		
		final String title = Plugin.getConfig().getString("Localization.Quests.Inventories.selector");
		Integer size = 9;
		if (quests.size() > 9) size = 27;
		
		Inventory gui = Bukkit.createInventory(Bukkit.getPlayer(uuid), size, title);
		
		for (HashMap<Integer, Quest> q : quests) {
			
			for (Entry<Integer, Quest> key : q.entrySet()) {
				
				Quest quest = key.getValue();
				
				gui.addItem(getGUIItem(quest.getIcon().getType(), 1, quest.getTitle(), quest.getShort()));
				
				Bukkit.broadcastMessage(key.getValue().getTitle());
				
			}
			
		}
		
		return gui;
	}
	
	private Inventory getQuestSpecificInventory (Quest quest, Player player, String uuid, String title) {
		Inventory gui = Bukkit.createInventory(Bukkit.getPlayer(uuid), 27, title);
		
		for (int i = 0; i < 10; i++) {
			gui.addItem(getGUIItem(Material.GRAY_STAINED_GLASS_PANE, 64, " ", ""));
		}
		gui.addItem(getGUIItem(Material.PAPER, 1, "§6" + quest.getShort(), quest.getDescription()));
		gui.addItem(getGUIItem(Material.WHITE_STAINED_GLASS_PANE, 64, " ", ""));
		gui.addItem(getGUIItem(Material.COMPASS, 1, "§6Questziel", quest.getTarget()));
		gui.addItem(getGUIItem(Material.WHITE_STAINED_GLASS_PANE, 64, " ", ""));
		gui.addItem(getGUIItem(Material.FILLED_MAP, 1, "§6Kategorie", quest.getType()));
		gui.addItem(getGUIItem(Material.WHITE_STAINED_GLASS_PANE, 64, " ", ""));
		gui.addItem(getGUIItem(Material.ACACIA_SIGN, 1, "§6Zugehörigkeit", quest.getFaction()));
		gui.addItem(getGUIItem(Material.GRAY_STAINED_GLASS_PANE, 64, " ", ""));
		gui.addItem(getGUIItem(Material.LIME_BANNER, 1, "§2Quest annehmen!", "§6Starte die Quest §2" + quest.getTitle() + "§6!"));
		for (int i = 0; i < 7; i++) {
			gui.addItem(getGUIItem(Material.GRAY_STAINED_GLASS_PANE, 64, " ", ""));
		}
		gui.addItem(getGUIItem(Material.RED_BANNER, 1, "§cAbbrechen", "§6Ich habs mir anders überlegt!"));
		for (int i = 0; i < gui.getSize(); i++) {
			if (gui.getItem(i) != null) {
				if (gui.getItem(i).getType().equals(Material.GRAY_STAINED_GLASS_PANE) ||
					gui.getItem(i).getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
					gui.getItem(i).setAmount(1);
				}
			}
		}
		
		return gui;
	}
	
	private ItemStack getGUIItem (Material material, Integer amount, String name, String lore) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		List<String> wrapped = new ArrayList<String>();
		for (String str : ChatPaginator.wordWrap(lore, 30)) {
			wrapped.add(str);
		}
		meta.setLore(wrapped);
		item.setItemMeta(meta);
		
		return item;
	}
	
}
