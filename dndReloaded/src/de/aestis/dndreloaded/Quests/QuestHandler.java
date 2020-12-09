package de.aestis.dndreloaded.Quests;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Helpers.MathHelpers;
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
	
	/**
	 * Initializes everything Quest related.
	 * Checking for whitelisted NPCs in servers worlds
	 * and binds available Quests to each of them
	 */
	public void initialize()  {

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){

            @Override
            public void run() {
            	
            	Integer worldCount = Bukkit.getServer().getWorlds().size();
            	
            	/*
            	 * Prevent crashes and bugs on weird
            	 * MultiWorld/MultiVerse configurations
            	 */
            	if (worldCount == 0)
            	{
            		Plugin.getLogger().warning("Server doesn't contain any useable worlds! Stopping the Plugin...");
            		Plugin.getPluginLoader().disablePlugin(null);
            		return;
            	}
            	
            	Plugin.getLogger().info("Checking for potential Questgiver NPCs on " + worldCount + " worlds...");

            	for (World w : Bukkit.getServer().getWorlds())
            	{
            		Plugin.getLogger().info("Found World '" + w.getName() + "'! Fetching Entities...");
            		
        			for (Entity e : w.getEntities())
        			{
        				
        				/*
        				 * Exclude hostile Mobs and other, common MobTypes
        				 * Currently available:
        				 * PLAYER, ARMOR_STAND, CAT, WOLF, IRON_GOLEM, ITEM_FRAME,
        				 * SKELETON, VILLAGER, WANDERING_TRADER
        				 */
        				if (e.getType() == EntityType.PLAYER ||
    						e.getType() == EntityType.ARMOR_STAND ||
    						e.getType() == EntityType.CAT ||
    						e.getType() == EntityType.WOLF ||
    						e.getType() == EntityType.IRON_GOLEM ||
    						e.getType() == EntityType.ITEM_FRAME ||
    						e.getType() == EntityType.SKELETON ||
    						e.getType() == EntityType.VILLAGER ||
    						e.getType() == EntityType.WANDERING_TRADER) {
        					
        					Plugin.getLogger().info("Fetching Quests for '" + e.getUniqueId() + "'...");
        					
        					DatabaseHandler db = Plugin.getDatabaseHandler();
        					List<Quest> quests = db.getQuestData(e.getUniqueId().toString());
        					
        					if (quests.size() == 0)
        					{
        						Plugin.getLogger().fine("No Quests found for '" + e.getUniqueId() + "'! Continuing...");
        						continue;
        					}
        					
        					for (Quest q : quests)
        					{
        						/*
        						 * Inserting quest into local storage
        						 * to prevent further Database access
        						 */
        						Plugin.QuestData.insertQuest(q.getNpcID(), q.getID(), q);
        						Plugin.getLogger().info(" > Loaded Quest {" + q.getID() + "} '" + q.getTitle() + "' for this NPC.");
        					}
        				}	
        			}
        		}            	
            }
        }, 20L);
	}
	
	/**
	 * Mainly Error handler for Slots 1 and 2
	 * @param player (Players Name)
	 * @return Quest or NULL
	 */
	public Quest getPlayerQuestPrimary (Player player) {
		
		if (Plugin.Players.get(player) != null)
		{
			Quest quest = Plugin.Players.get(player).getQuestActive1();
			
			if (quest != null)
			{
				return quest;
			}
		}
		
		return null;
	}
	
	/**
	 * Mainly Error handler for Slots 1 and 2
	 * @param player (Players Name)
	 * @return Quest or NULL
	 */
	public Quest getPlayerQuestSecondary (Player player) {
		
		if (Plugin.Players.get(player) != null)
		{
			Quest quest = Plugin.Players.get(player).getQuestActive2();
			
			if (quest != null)
			{
				return quest;
			}
		}
		
		return null;
	}
	
	/**
	 * Prevents overwriting a currently
	 * active Quest from Player for Slot 1 and 2
	 * DON'T USE TO UNSET PRIMARY QUEST!
	 * @param player (Players Name)
	 * @param quest (Quest 1 from Player)
	 * @return true/false
	 */
	private boolean setPlayerQuestPrimary (Player player, Quest quest) {
		
		if (Plugin.Players.get(player) != null)
		{
			
			if (Plugin.Players.get(player).getQuestActive1() == null)
			{
				Plugin.Players.get(player).setQuestActive1(quest);
				Plugin.Players.get(player).setQuestVariable2(quest.getVariable());
				return true;
			} else
			{
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Prevents overwriting a currently
	 * active Quest from Player for Slot 1 and 2
	 * DON'T USE TO UNSET SECONDARY QUEST!
	 * @param player (Players Name)
	 * @param quest (Quest 2 from Player)
	 * @return true/false
	 */
	private boolean setPlayerQuestSecondary (Player player, Quest quest) {
		
		if (Plugin.Players.get(player) != null)
		{
			
			if (Plugin.Players.get(player).getQuestActive2() == null)
			{
				Plugin.Players.get(player).setQuestActive2(quest);
				Plugin.Players.get(player).setQuestVariable2(quest.getVariable());
				return true;
			} else
			{
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Unsets Players Quest Slot 1 if
	 * there's anything to unset
	 * @param player (Players Name)
	 * @return true/false
	 */
	public boolean unsetPlayerQuestPrimary (Player player) {
		
		if (Plugin.Players.get(player) != null)
		{
			
			if (Plugin.Players.get(player).getQuestActive1() == null)
			{
				return false;
			} else
			{
				Plugin.Players.get(player).setQuestActive1(null);
				Plugin.Players.get(player).setQuestVariable1(0);
			}
		}
		
		return false;
	}
	
	/**
	 * Unsets Players Quest Slot 2 if
	 * there's anything to unset
	 * @param player (Players Name)
	 * @return true/false
	 */
	public boolean unsetPlayerQuestSecondary (Player player) {
		
		if (Plugin.Players.get(player) != null)
		{
			
			if (Plugin.Players.get(player).getQuestActive2() == null)
			{
				return false;
			} else
			{
				Plugin.Players.get(player).setQuestActive2(null);
				Plugin.Players.get(player).setQuestVariable2(0);
			}
		}
		
		return false;
	}
	
	/**
	 * Automatically handles inserting Quests
	 * into primary or secondary Slot
	 * @param player (Players Name)
	 * @param quest (Quest to insert)
	 * @return true/false
	 */
	public boolean setPlayerQuest (Player player, Quest quest) {
		
		if (getPlayerQuestPrimary(player) == null)
		{
			setPlayerQuestPrimary(player, quest);
			return true;
		} else if (getPlayerQuestSecondary(player) == null)
		{
			setPlayerQuestSecondary(player, quest);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Used to get the QuestSlot from
	 * a specific Quest the player is
	 * currently active on
	 * @param player (Players Name)
	 * @param quest (Quest to compare)
	 * @return QuestSlot/null
	 */
	public QuestSlot getSlotByQuest (Player player, Quest quest) {
		
		if (this.getPlayerQuestPrimary(player) == quest) return QuestSlot.SLOT_PRIMARY;
		if (this.getPlayerQuestSecondary(player) == quest) return QuestSlot.SLOT_SECONDARY;
		
		return null;
	}
	
	public void handleQuestEvents (Player player, Event event) {
		
		if (Plugin.Players.get(player).getQuestActive1() != null
			|| Plugin.Players.get(player).getQuestActive2() != null)
		{
			switch (event.getEventName())
			{
				case "PlayerInteractEntity":
					//TODO
					
					/*
					 * Register custom EventListener 
					 */
					
					//event.getHandlers().register(listener);
					break;
			}
		} else
		{
			return;
		}
	}
	
	/**
	 * Fetches random message from NPC when not
	 * having completed the required Quest(line) before
	 * @return Message
	 */
	public String getNPCDenyMessage() {
		
		if (Plugin.getConfig().isSet("Localization.Quests.General.Messages.missingrequiredquests"))
		{
			@SuppressWarnings("unchecked")
			List<String> messages = (List<String>) Plugin.getConfig().getList("Localization.Quests.General.Messages.missingrequiredquests");
			
			return messages.get(MathHelpers.getRndInt(0, messages.size() - 1));
		} else
		{
			Plugin.getLogger().warning("Messages for 'Localization.Quests.General.Messages.missingrequiredquests' not set up yet!");
			
			return null;
		}
	}	
	
	//EventListener
	//OUTSOURCE
	
	public void handleQuestgiverInteraction (PlayerInteractEntityEvent event) {
		
		/*
		 * Block second trigger (OFF_HAND)
		 * Without Event would be triggered twice
		 */
		
		if (event.getHand() == EquipmentSlot.OFF_HAND) return;	
		
		Entity npc = event.getRightClicked();
		String npcUUID = npc.getUniqueId().toString();
		Player player = event.getPlayer();

		if (player.isOp())
		{
			player.sendMessage("§6Player §2" + player.getName() + "§6 selected Entity §2" + npc.getUniqueId());
		}
		
		/*
		 * Store this NPC into local memory
		 * for further operation
		 */
		Plugin.SelectedNPC.put(player, npc.getUniqueId().toString());
	
		if (npc.getCustomName() != null)
		{
			/*
			 * Quest givers can't exist without a custom Name!
			 * Checking them is not necessary
			 */
			
			if (player.isOp())
			{
				player.sendMessage("§6Checking for §2" + npc.getUniqueId().toString() + "§6...");
			}
			
			//TODO
			/*
			 * Checking via DB is extremely inefficient!
			 * Use already built-in QuestMap instead! @oxo :P
			 */
			if (Plugin.QuestData.hasQuests(npcUUID))
			{
				if (player.isOp())
				{
					player.sendMessage("§2NPC Has Something. Let Me Fetch It For You...");
				}
				
				/*
				 * Play sound to Player to mimic "speaking" to the selected NPC.
				 * Open up Quest-Selection-Menu afterwards
				 */
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);

				List<HashMap<Integer, Quest>> quests = Plugin.QuestData.getAllQuestsFromNPC(npcUUID);
				InventoryMenuManager.getInstance().openMenu(player, new QuestSelectorMenu(quests));
			} else
			{
				if (player.isOp())
				{
					player.sendMessage("§cNPC Hasn't Got Any Quest For You, Yet.");
				}
			}
		}
		
		//TODO
		//REMOVE AFTER IMPLEMENTING AT THE RIGHT LOCATION!
		//event.getPlayer().sendMessage(getNPCDenyMessage());
	}
	
}
