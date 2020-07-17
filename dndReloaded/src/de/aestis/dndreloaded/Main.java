package de.aestis.dndreloaded;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.Quests.QuestHandler;
import de.aestis.dndreloaded.Quests.QuestMap;
import de.aestis.dndreloaded.Chat.ChatHandler;
import de.aestis.dndreloaded.Chat.Bungee.BungeeCordBridge;
import de.aestis.dndreloaded.Chat.Util.ChatMode;
import de.aestis.dndreloaded.CommandManager.CommandManager;
import de.aestis.dndreloaded.CommandManager.Quests.QuestAdmin;
import de.aestis.dndreloaded.CommandManager.Quests.QuestEditor;
import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Players.PlayerHandler;
import de.aestis.dndreloaded.Players.Professions.ProfessionHandler;
import de.aestis.dndreloaded.Players.Professions.Listeners.ListenerHerbalist;
import de.aestis.dndreloaded.Players.Professions.Listeners.ListenerWoodcutter;
import de.aestis.dndreloaded.Players.Professions.Recipes.RecipeHandler;
import de.aestis.dndreloaded.Database.Mysql;
import de.aestis.dndreloaded.Entites.EntityData;
import de.aestis.dndreloaded.Entites.EntityHandler;
import de.aestis.dndreloaded.Entites.Listeners.ListenerEntityEvents;
import de.aestis.dndreloaded.Helpers.BookHelpers;
import de.aestis.dndreloaded.Helpers.InventoryHelpers;
import de.aestis.dndreloaded.Helpers.MathHelpers;
import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Helpers.External.GriefPreventionHelper;
import de.aestis.dndreloaded.Helpers.External.HolographicDisplaysHelper;
import de.aestis.dndreloaded.Helpers.External.WorldGuardHelper;
import de.aestis.dndreloaded.Helpers.External.WorldGuard.WorldGuardRegionEventListener;
import de.aestis.dndreloaded.Listeners.EntityDamageByEntityEventHandler;
import de.aestis.dndreloaded.Listeners.PlayerInteractEntityEventHandler;
import de.aestis.dndreloaded.Listeners.PlayerLoginEventHandler;
import de.aestis.dndreloaded.Listeners.PlayerQuitEventHandler;
import de.aestis.dndreloaded.Listeners.PlayerRegionEnterEvent;
import de.aestis.dndreloaded.Quests.Listeners.TypeEntityKill;
import de.aestis.dndreloaded.Overrides.BlockBreak;
import de.aestis.dndreloaded.Players.PlayerData;

public class Main extends JavaPlugin {
	
	public static String Version = "0.4.0";
	
	public static Main instance;
	
	private BlockBreak BlockBreakOverride;
	private InventoryHelpers InventoryHelper;
	private ScoreboardHelpers ScoreboardHelper;
	private MathHelpers MathHelper;
	private BookHelpers BookHelper;
	private DatabaseHandler DatabaseHnd;
	private QuestHandler QuestHnd;
	private PlayerHandler PlayerHnd;
	private ProfessionHandler ProfessionHnd;
	private RecipeHandler RecipeHnd;
	private DataSync DataSn;
	private GameTicks GameTcs;
	
	private GriefPreventionHelper GPHelper;
	private WorldGuardHelper WGHelper;
	
	private BungeeCordBridge BungeeBridge;
	
	private Mysql Database;
	
	/*
	 * Local Storage for
	 * every global Method
	 */
	
	public HashMap<Player, String> SelectedNPC = new HashMap<Player, String>();
	public HashMap<Player, Quest> SelectedQuest = new HashMap<Player, Quest>();
	
	public HashMap<Player, PlayerData> Players = new HashMap<Player, PlayerData>();
	public HashMap<Player, ChatMode> PlayerChannel = new HashMap<Player, ChatMode>();
	public QuestMap QuestData = new QuestMap();
	
	public HashMap<UUID, Hologram> HoloStorage = new HashMap<UUID, Hologram>();
	public HashMap<UUID, HashMap<World, EntityData>> TrackedEntities = new HashMap<UUID, HashMap<World, EntityData>>();

	/*
	 * Enable the whole plugin
	 * (Here we go!)
	 * */
	
	public void onLoad() {
		
		/*
		 * Starting up external Handlers
		 * or Hooks which are only accessible
		 * before Plugin enable sequence
		 */

		getLogger().info("Launching pre-load sequence...");
		
		WorldGuard api = WorldGuardHelper.getWorldGuardAPI();
		
		if (api != null
			|| WorldGuard.getInstance() != null)
		{
			WorldGuardHelper.setupCustomFlag(api);
			getLogger().info("Custom Flags successfully initialized!");
		} else
		{
			getLogger().severe("Plugin could not find hook for WorldGuard! Is it running?");
		}
	}
	
	public void onEnable() {
				
		instance=this;
		
		/*
		 * Clear local storage if
		 * it somehow is filled with
		 * something at this point
		 */
		
		if (!SelectedNPC.isEmpty()
			|| !SelectedQuest.isEmpty()
			|| !Players.isEmpty()
			|| !PlayerChannel.isEmpty()
			|| !QuestData.isEmpty()
			|| !HoloStorage.isEmpty()
			|| !TrackedEntities.isEmpty())
		{
			SelectedNPC.clear();
			SelectedQuest.clear();
			Players.clear();
			PlayerChannel.clear();
			QuestData.clear();
			HoloStorage.clear();
			TrackedEntities.clear();
		}
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		try
		{
			/*
			 * Setup or load config file(s)
			 * (Current state is a bit messy)
			 */
			
			setupConfigs();
		} catch (Exception ex)
		{
			getLogger().severe("Error while setting up Config-File: " + ex);
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
		Database = new Mysql();
		
		if(!Database.connect())
		{
			/*
			 * Initialize Database connection (essential!)
			 * */
			
			getLogger().severe("No Mysql Connection, Shutting Down...");
			Bukkit.getPluginManager().disablePlugin(this);
		    return;
		} else
		{
			getLogger().fine("Successfully set Up MySQL Connection.");
		}
		
		try
		{
			/*
			 * Initialize additional, internal plugins and extentions
			 * (Overrides -> Helpers -> Handlers -> Main Components (order is important!)
			 * */
			
			//Overrides
			setBlockBreakOverride();
			
			//Helpers
			setInventoryHelper();
			setMathHelper();
			setScoreboardHelper();
			setBookHelper();
			//External
			setGriefPreventionHelper();
			
			//Handlers
			setDatabaseHandler();
			setQuestHandler();
			setPlayerHandler();
			setProfessionHandler();
			setRecipeHandler();
			
			//Main Components
			setDataSync();
			setGameTicks();
			
			//Bridges
			setBungeeCordBridge();
			
			/*
			 * To be removed (soon)
			 */
			QuestHnd.initialize();
			
			getLogger().info("Sucessfully Enabled All Addons.");
		} catch (Exception ex)
		{
			getLogger().severe("Error Whilst Initializing Addons: " + ex);
			return;
		}
		
		try {
			
			/*
			 * Create tables if not existing + Hook everything into place :)
			 * */
			
			DatabaseHnd.initializeDatabase();
			
			getServer().getPluginManager().registerEvents((Listener) new EventListener(), this);
			//...
			//getServer().getPluginManager().registerEvents((Listener) new PlayerInteractEntityEventHandler(), this);
			getServer().getPluginManager().registerEvents((Listener) new EntityDamageByEntityEventHandler(), this);
			getServer().getPluginManager().registerEvents((Listener) new PlayerLoginEventHandler(), this);
			getServer().getPluginManager().registerEvents((Listener) new PlayerQuitEventHandler(), this);
			getServer().getPluginManager().registerEvents((Listener) new PlayerRegionEnterEvent(), this);
			
			/*
			 * Register all Profession
			 * based EventListeners
			 */
			
			getServer().getPluginManager().registerEvents((Listener) new ListenerWoodcutter(), this);
			//...
			getServer().getPluginManager().registerEvents((Listener) new ListenerHerbalist(), this);
			
			//ChatHandler
			
			getServer().getPluginManager().registerEvents((Listener) new ChatHandler(), this);
			
			//Entity Stuff
			
			getServer().getPluginManager().registerEvents((Listener) new ListenerEntityEvents(), this);
			
			
			//WorldGuard Stuff
			getServer().getPluginManager().registerEvents((Listener) new WorldGuardRegionEventListener(), this);
			
			/*
			 * Register all Quest
			 * based EventListeners
			 * 
			 */
			
			getServer().getPluginManager().registerEvents((Listener) new TypeEntityKill(), this);
			
			
			getCommand("questadmin").setExecutor((CommandExecutor) new QuestAdmin());
			getCommand("questedit").setExecutor((CommandExecutor) new QuestEditor());
			
			getCommand("dnd").setExecutor((CommandExecutor) new CommandManager());
			getLogger().info( "Set Up Main Functionality (EventListener + CommandExecutor)");
		} catch (Exception ex) {
			getLogger().severe("Error whilst enabling EventListener/CommandExecutor: " + ex);
			return;
		}
		
		try {
			
			/*
			 * Load PlayerData initially and after reload
			 * */
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {

				@Override
				public void run() {	

					for (Player p : Bukkit.getServer().getOnlinePlayers())
					{
						DataSn.loadPlayerData(p);
						PlayerHnd.setupPlayerProfessions(Players.get(p));
					}
				}
				
			}, 40L);
			
			/*
			 * Assign Entity Data after reload
			 */
			
			EntityHandler.initializeTrackerSetup();
			
			HolographicDisplaysHelper.createHolos(this);
			
			/*Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {

				@Override
				public void run() {	

					
				}
				
			}, 10L);*/
			
			/*
			 * Load Custom Recipes
			 */
			
			RecipeHnd.loadCustomRecipes();
			
			GameTcs.startSyncTask();
			//GameTcs.startRefreshScoreboardsTask();
			GameTcs.startEntityRangeTask();
			GameTcs.startHoloUpdateTask();

		} catch (Exception ex) {
			getLogger().severe("Could Not Load Player Data: " + ex);
		}
		
		getLogger().fine("dndReloaded v" + Version + " successfully enabled!");
	}
	
	public void onDisable() {
		
		/*
		 * Synchronize PlayerData to
		 * prevent Data loss on shutdown
		 */
		
		for (Player p : Bukkit.getServer().getOnlinePlayers())
		{
			DataSn.savePlayerData(p);
			Players.remove(p);
		}
		
		getLogger().info("Lawandja CORE v " + Version + " shutting down...");
		
		/*
		 * Resetting all local variables
		 * to prevent memory leaks
		 */
		
		this.BlockBreakOverride = null;
		this.InventoryHelper = null;
		this.MathHelper = null;
		this.ScoreboardHelper = null;
		this.MathHelper = null;
		this.ScoreboardHelper = null;
		this.BookHelper = null;
		this.DatabaseHnd = null;
		this.QuestHnd = null;
		this.PlayerHnd = null;
		this.ProfessionHnd = null;
		this.RecipeHnd = null;
		this.DataSn = null;
		this.GameTcs = null;
		
		Main.instance = null;
	}
	
	/*
	 * Pass to external classes (mainly Database)
	 * */
	
	public Connection getConnection() {
		return Database.getConnection();
	}
	
	/*
	 * Handling class instances from Main.class
	 * */
	
	//Overrides
	private void setBlockBreakOverride() {this.BlockBreakOverride = BlockBreak.getInstance();}
	
	//Helpers
	private void setInventoryHelper() {this.InventoryHelper = InventoryHelpers.getInstance();}
	private void setMathHelper() {this.MathHelper = MathHelpers.getInstance();}
	private void setScoreboardHelper() {this.ScoreboardHelper = ScoreboardHelpers.getInstance();}
	private void setBookHelper() {this.BookHelper = BookHelpers.getInstance();}
	
	//Handlers
	private void setDatabaseHandler() {this.DatabaseHnd = DatabaseHandler.getInstance();}
	private void setQuestHandler() {this.QuestHnd = QuestHandler.getInstance();}
	private void setPlayerHandler() {this.PlayerHnd = PlayerHandler.getInstance();}
	private void setProfessionHandler() {this.ProfessionHnd = ProfessionHandler.getInstance();}
	private void setRecipeHandler() {this.RecipeHnd = RecipeHandler.getInstance();}
	//External
	private void setGriefPreventionHelper() {this.GPHelper = GriefPreventionHelper.getInstance();}
	
	//Main Components
	private void setDataSync() {this.DataSn = DataSync.getInstance();}
	private void setGameTicks() {this.GameTcs = GameTicks.getInstance();}
	
	//Bridges
	private void setBungeeCordBridge() {this.BungeeBridge = BungeeCordBridge.getInstance();}
	
	
	//Overrides
	public BlockBreak getBlockBreakOverride() {return this.BlockBreakOverride;}
	
	//Helpers
	public InventoryHelpers getInventoryHelper() {return this.InventoryHelper;}
	public MathHelpers getMathHelper() {return this.MathHelper;}
	public ScoreboardHelpers getScoreboardHelper() {return this.ScoreboardHelper;}
	public BookHelpers getBookHelper() {return this.BookHelper;}
	//External
	public GriefPreventionHelper getGriefPreventionHandler() {return this.GPHelper;}
	public WorldGuardHelper getWorldGuardHelper() {return this.WGHelper;}
	
	//Handlers
	public DatabaseHandler getDatabaseHandler() {return this.DatabaseHnd;}
	public QuestHandler getQuestHandler() {return this.QuestHnd;}
	public PlayerHandler getPlayerHandler() {return this.PlayerHnd;}
	public ProfessionHandler getProfessionHandler() {return this.ProfessionHnd;}
	public RecipeHandler getRecipeHandler() {return this.RecipeHnd;}
	
	//Main Components
	public DataSync getDataSync() {return this.DataSn;}
	public GameTicks getGameTicks() {return this.GameTcs;}
	
	//Bridges
	public BungeeCordBridge getBungeeCordBridge() {return this.BungeeBridge;}
	
	
	private void setupConfigs() {
		
		/*
		 * Setting up (default) configs
		 * */
		
        FileConfiguration config = getConfig();
        
        if(!config.isSet("Mysql.host")){config.set("Mysql.host", "localhost");}
        if(!config.isSet("Mysql.database")){config.set("Mysql.database", "quests");}
        if(!config.isSet("Mysql.port")){config.set("Mysql.port", 3306);}
        if(!config.isSet("Mysql.username")){config.set("Mysql.username", "user");}
        if(!config.isSet("Mysql.password")){config.set("Mysql.password", "supersafepassword");}
        
        if(!config.isSet("Tasks.Sync.interval")){config.set("Tasks.Sync.interval", 6000);}
        
        if (!config.isSet("Items.Tools.Durability.enabled")) {config.set("Items.Tools.Durability.enabled", true);}
        if (!config.isSet("Items.Tools.Durability.multiplier")) {config.set("Items.Tools.Durability.multiplier", 3);}
        if (!config.isSet("Items.Tools.Durability.Messages.cancelled")) {config.set("Items.Tools.Durability.Messages.cancelled", "§eDas Ding ist kaputt!");}
        
        if (!config.isSet("Items.Tools.Sharpness.enabled")) {config.set("Items.Tools.Sharpness.enabled", true);}
        if (!config.isSet("Items.Tools.Sharpness.chance")) {config.set("Items.Tools.Sharpness.chance", 3);}
        //Sharpness Will Be Accounted For When Calculating Tool Damage

        if (!config.isSet("Block.Drop.Wood.enabled")) {config.set("Block.Drop.Wood.enabled", true);}
        if (!config.isSet("Block.Drop.Wood.usesilktouch")) {config.set("Block.Drop.Wood.usesilktouch", true);}
        if (!config.isSet("Block.Drop.Wood.Amount.min")) {config.set("Block.Drop.Wood.Amount.min", 1);}
        if (!config.isSet("Block.Drop.Wood.Amount.max")) {config.set("Block.Drop.Wood.Amount.max", 3);}
        
        //TODO
        
        /*
         * Setup Blocks / Recipes / Stuff
         * for specific Professions (Woodcutter)
         * 
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * !!! IMPLEMENT XP GAIN BLACKLIST TO AVOID ABUSE !!!
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * 
         */
        
        if (!config.isSet("Profession.Woodcutter.Experience.Pickup.min")) {config.set("Profession.Woodcutter.Experience.Pickup.min", 1);}
        if (!config.isSet("Profession.Woodcutter.Experience.Pickup.max")) {config.set("Profession.Woodcutter.Experience.Pickup.max", 2);}
        if (!config.isSet("Profession.Woodcutter.Experience.Crafting.min")) {config.set("Profession.Woodcutter.Experience.Crafting.min", 5);}
        if (!config.isSet("Profession.Woodcutter.Experience.Crafting.max")) {config.set("Profession.Woodcutter.Experience.Crafting.max", 9);}
        
        List<String> woodcutterBlocks = new ArrayList<>();
        woodcutterBlocks.add("minecraft:oak_log");
        woodcutterBlocks.add("minecraft:spruce_log");
        woodcutterBlocks.add("minecraft:birch_log");
        woodcutterBlocks.add("minecraft:jungle_log");
        woodcutterBlocks.add("minecraft:acacia_log");
        woodcutterBlocks.add("minecraft:dark_oak_log");
        woodcutterBlocks.add("minecraft:stripped_oak_log");
        woodcutterBlocks.add("minecraft:stripped_spruce_log");
        woodcutterBlocks.add("minecraft:stripped_birch_log");
        woodcutterBlocks.add("minecraft:stripped_jungle_log");
        woodcutterBlocks.add("minecraft:stripped_acacia_log");
        woodcutterBlocks.add("minecraft:stripped_dark_oak_log");
        woodcutterBlocks.add("minecraft:stripped_oak_wood");
        woodcutterBlocks.add("minecraft:stripped_spruce_wood");
        woodcutterBlocks.add("minecraft:stripped_birch_wood");
        woodcutterBlocks.add("minecraft:stripped_jungle_wood");
        woodcutterBlocks.add("minecraft:stripped_acacia_wood");
        woodcutterBlocks.add("minecraft:stripped_dark_oak_wood");
        if (!config.isSet("Profession.Woodcutter.blocks")) {config.set("Profession.Woodcutter.blocks", woodcutterBlocks);}
        
        List<String> woodcutterCrafting = new ArrayList<>();
        woodcutterCrafting.add("minecraft:oak_slab");
        woodcutterCrafting.add("minecraft:spruce_slab");
        woodcutterCrafting.add("minecraft:birch_slab");
        woodcutterCrafting.add("minecraft:jungle_slab");
        woodcutterCrafting.add("minecraft:acacia_slab");
        woodcutterCrafting.add("minecraft:dark_oak_slab");
        woodcutterCrafting.add("minecraft:oak_stairs");
        woodcutterCrafting.add("minecraft:spruce_stairs");
        woodcutterCrafting.add("minecraft:birch_stairs");
        woodcutterCrafting.add("minecraft:jungle_stairs");
        woodcutterCrafting.add("minecraft:acacia_stairs");
        woodcutterCrafting.add("minecraft:dark_oak_stairs"); 
        woodcutterCrafting.add("minecraft:oak_fence");
        woodcutterCrafting.add("minecraft:spruce_fence");
        woodcutterCrafting.add("minecraft:birch_fence");
        woodcutterCrafting.add("minecraft:jungle_fence");
        woodcutterCrafting.add("minecraft:acacia_fence");
        woodcutterCrafting.add("minecraft:dark_oak_fence");
        woodcutterCrafting.add("minecraft:oak_sign");
        woodcutterCrafting.add("minecraft:spruce_sign");
        woodcutterCrafting.add("minecraft:birch_sign");
        woodcutterCrafting.add("minecraft:jungle_sign");
        woodcutterCrafting.add("minecraft:acacia_sign");
        woodcutterCrafting.add("minecraft:dark_oak_sign");
        if (!config.isSet("Profession.Woodcutter.crafting")) {config.set("Profession.Woodcutter.crafting", woodcutterCrafting);}
        
        /*
         * Setup Blocks / Recipes / Stuff
         * for specific Professions (Blacksmith)
         */
        
        if (!config.isSet("Profession.Blacksmith.Experience.Pickup.min")) {config.set("Profession.Blacksmith.Experience.Pickup.min", 0);}
        if (!config.isSet("Profession.Blacksmith.Experience.Pickup.max")) {config.set("Profession.Blacksmith.Experience.Pickup.max", 0);}
        if (!config.isSet("Profession.Blacksmith.Experience.Crafting.min")) {config.set("Profession.Blacksmith.Experience.Crafting.min", 6);}
        if (!config.isSet("Profession.Blacksmith.Experience.Crafting.max")) {config.set("Profession.Blacksmith.Experience.Crafting.max", 11);}
        
        List<String> blacksmithBlocks = new ArrayList<>();
        blacksmithBlocks.add("minecraft:gold_block");
        blacksmithBlocks.add("minecraft:iron_block");
        blacksmithBlocks.add("minecraft:diamond_block");
        blacksmithBlocks.add("minecraft:coal_block");      
        blacksmithBlocks.add("minecraft:iron_bars");
        blacksmithBlocks.add("minecraft:anvil");
        blacksmithBlocks.add("minecraft:chipped_anvil");
        blacksmithBlocks.add("minecraft:damaged_anvil");
        blacksmithBlocks.add("minecraft:smoker");
        blacksmithBlocks.add("minecraft:blast_furnace");     
        blacksmithBlocks.add("minecraft:grindstone");
        blacksmithBlocks.add("minecraft:smithing_table");
        blacksmithBlocks.add("minecraft:stonecutter");
        blacksmithBlocks.add("minecraft:bell");        
        blacksmithBlocks.add("minecraft:lantern");
        if (!config.isSet("Profession.Blacksmith.blocks")) {config.set("Profession.Blacksmith.blocks", blacksmithBlocks);}
        
        List<String> blacksmithCrafting = new ArrayList<>();
        blacksmithCrafting.add("minecraft:iron_shovel");
        blacksmithCrafting.add("minecraft:iron_pickaxe");
        blacksmithCrafting.add("minecraft:iron_axe");
        blacksmithCrafting.add("minecraft:iron_hoe");
        blacksmithCrafting.add("minecraft:diamond_shovel");
        blacksmithCrafting.add("minecraft:diamond_pickaxe");
        blacksmithCrafting.add("minecraft:diamond_axe");
        blacksmithCrafting.add("minecraft:diamond_hoe");
        blacksmithCrafting.add("minecraft:gold_shovel");
        blacksmithCrafting.add("minecraft:gold_pickaxe");
        blacksmithCrafting.add("minecraft:gold_axe");
        blacksmithCrafting.add("minecraft:gold_hoe");
        blacksmithCrafting.add("minecraft:iron_sword");
        blacksmithCrafting.add("minecraft:iron_helmet");
        blacksmithCrafting.add("minecraft:iron_chestplate");
        blacksmithCrafting.add("minecraft:iron_leggings");
        blacksmithCrafting.add("minecraft:iron_boots");
        blacksmithCrafting.add("minecraft:diamond_sword");
        blacksmithCrafting.add("minecraft:diamond_helmet");
        blacksmithCrafting.add("minecraft:diamond_chestplate");
        blacksmithCrafting.add("minecraft:diamond_leggings");
        blacksmithCrafting.add("minecraft:diamond_boots");
        blacksmithCrafting.add("minecraft:gold_sword");
        blacksmithCrafting.add("minecraft:gold_helmet");
        blacksmithCrafting.add("minecraft:gold_chestplate");
        blacksmithCrafting.add("minecraft:gold_leggings");
        blacksmithCrafting.add("minecraft:gold_boots");
        if (!config.isSet("Profession.Blacksmith.crafting")) {config.set("Profession.Blacksmith.crafting", blacksmithCrafting);}
        
        /*
         * Setup Blocks / Recipes / Stuff
         * for specific Professions (Stonecutter)
         */
        
        if (!config.isSet("Profession.Stonecutter.Experience.Pickup.min")) {config.set("Profession.Stonecutter.Experience.Pickup.min", 2);}
        if (!config.isSet("Profession.Stonecutter.Experience.Pickup.max")) {config.set("Profession.Stonecutter.Experience.Pickup.max", 3);}
        if (!config.isSet("Profession.Stonecutter.Experience.Crafting.min")) {config.set("Profession.Stonecutter.Experience.Crafting.min", 4);}
        if (!config.isSet("Profession.Stonecutter.Experience.Crafting.max")) {config.set("Profession.Stonecutter.Experience.Crafting.max", 10);}

        List<String> stonecutterBlocks = new ArrayList<>();
        stonecutterBlocks.add("minecraft:stone");
        stonecutterBlocks.add("minecraft:granite");
        stonecutterBlocks.add("minecraft:diorite");
        stonecutterBlocks.add("minecraft:andesite");
        stonecutterBlocks.add("minecraft:polished_granite");
        stonecutterBlocks.add("minecraft:polished_diorite");
        stonecutterBlocks.add("minecraft:polished_andesite");
        stonecutterBlocks.add("minecraft:sandstone");  
        stonecutterBlocks.add("minecraft:chiseled_sandstone");  
        stonecutterBlocks.add("minecraft:cut_sandstone"); 
        stonecutterBlocks.add("minecraft:smooth_red_sandstone");  
        stonecutterBlocks.add("minecraft:smooth_sandstone"); 
        stonecutterBlocks.add("minecraft:red_sandstone");  
        stonecutterBlocks.add("minecraft:chiseled_red_sandstone");  
        stonecutterBlocks.add("minecraft:cut_red_sandstone");
        stonecutterBlocks.add("minecraft:cobblestone");  
        stonecutterBlocks.add("minecraft:mossy_cobblestone");  
        stonecutterBlocks.add("minecraft:bricks");  
        stonecutterBlocks.add("minecraft:stone_bricks"); 
        stonecutterBlocks.add("minecraft:mossy_stone_bricks");  
        stonecutterBlocks.add("minecraft:cracked_stone_bricks");
        stonecutterBlocks.add("minecraft:chiseled_stone_bricks");
        stonecutterBlocks.add("minecraft:smooth_stone");
        stonecutterBlocks.add("minecraft:clay");

        stonecutterBlocks.add("minecraft:stone_slab");
        stonecutterBlocks.add("minecraft:smooth_stone_slab");
        stonecutterBlocks.add("minecraft:cobblestone_slab");
        stonecutterBlocks.add("minecraft:stone_brick_slab");
        stonecutterBlocks.add("minecraft:mossy_stone_brick_slab");
        stonecutterBlocks.add("minecraft:sandstone_slab");
        stonecutterBlocks.add("minecraft:cut_sandstone_slab");
        stonecutterBlocks.add("minecraft:brick_slab");
        stonecutterBlocks.add("minecraft:red_sandstone_slab");
        stonecutterBlocks.add("minecraft:cut_red_sandstone_slab");
        stonecutterBlocks.add("minecraft:polished_granite_slab");
        stonecutterBlocks.add("minecraft:smooth_red_sandstone_slab");
        stonecutterBlocks.add("minecraft:polished_diorite_slab");
        stonecutterBlocks.add("minecraft:mossy_cobblestone_slab");
        stonecutterBlocks.add("minecraft:granite_slab");
        stonecutterBlocks.add("minecraft:smooth_sandstone_slab");
        stonecutterBlocks.add("minecraft:andesite_slab");
        stonecutterBlocks.add("minecraft:polished_andesite_slab");
        stonecutterBlocks.add("minecraft:diorite_slab");

        stonecutterBlocks.add("minecraft:cobblestone_stairs");
        stonecutterBlocks.add("minecraft:brick_stairs");
        stonecutterBlocks.add("minecraft:stone_brick_stairs");
        stonecutterBlocks.add("minecraft:sandstone_stairs");
        stonecutterBlocks.add("minecraft:polished_granite_stairs");
        stonecutterBlocks.add("minecraft:smooth_red_sandstone_stairs");
        stonecutterBlocks.add("minecraft:red_sandstone_stairs");
        stonecutterBlocks.add("minecraft:mossy_stone_brick_stairs");
        stonecutterBlocks.add("minecraft:polished_diorite_stairs");
        stonecutterBlocks.add("minecraft:stone_stairs");
        stonecutterBlocks.add("minecraft:smooth_sandstone_stairs");
        stonecutterBlocks.add("minecraft:granite_stairs");
        stonecutterBlocks.add("minecraft:andesite_stairs");
        stonecutterBlocks.add("minecraft:polished_andesite_stairs");
        stonecutterBlocks.add("minecraft:diorite_stairs");

        stonecutterBlocks.add("minecraft:cobblestone_wall");
        stonecutterBlocks.add("minecraft:mossy_cobblestone_wall");
        stonecutterBlocks.add("minecraft:brick_wall");
        stonecutterBlocks.add("minecraft:red_sandstone_wall");
        stonecutterBlocks.add("minecraft:mossy_stone_brick_wall");
        stonecutterBlocks.add("minecraft:granite_wall");
        stonecutterBlocks.add("minecraft:stone_brick_wall");
        stonecutterBlocks.add("minecraft:andesite_wall");
        stonecutterBlocks.add("minecraft:sandstone_wall");
        stonecutterBlocks.add("minecraft:diorite_wall");
        if (!config.isSet("Profession.Stonecutter.blocks")) {config.set("Profession.Stonecutter.blocks", stonecutterBlocks);}

        List<String> stonecutterCrafting = new ArrayList<>();
        stonecutterCrafting.add("minecraft:stone");
        stonecutterCrafting.add("minecraft:polished_granite");
        stonecutterCrafting.add("minecraft:polished_diorite");
        stonecutterCrafting.add("minecraft:polished_andesite");
        stonecutterCrafting.add("minecraft:chiseled_sandstone");  
        stonecutterCrafting.add("minecraft:cut_sandstone"); 
        stonecutterCrafting.add("minecraft:smooth_red_sandstone");  
        stonecutterCrafting.add("minecraft:smooth_sandstone"); 
        stonecutterCrafting.add("minecraft:red_sandstone");  
        stonecutterCrafting.add("minecraft:chiseled_red_sandstone");  
        stonecutterCrafting.add("minecraft:cut_red_sandstone");
        stonecutterCrafting.add("minecraft:mossy_cobblestone");  
        stonecutterCrafting.add("minecraft:bricks");  
        stonecutterCrafting.add("minecraft:stone_bricks"); 
        stonecutterCrafting.add("minecraft:mossy_stone_bricks");  
        stonecutterCrafting.add("minecraft:cracked_stone_bricks");
        stonecutterCrafting.add("minecraft:chiseled_stone_bricks");
        stonecutterCrafting.add("minecraft:smooth_stone");

        stonecutterCrafting.add("minecraft:stone_slab");
        stonecutterCrafting.add("minecraft:smooth_stone_slab");
        stonecutterCrafting.add("minecraft:cobblestone_slab");
        stonecutterCrafting.add("minecraft:stone_brick_slab");
        stonecutterCrafting.add("minecraft:mossy_stone_brick_slab");
        stonecutterCrafting.add("minecraft:sandstone_slab");
        stonecutterCrafting.add("minecraft:cut_sandstone_slab");
        stonecutterCrafting.add("minecraft:brick_slab");
        stonecutterCrafting.add("minecraft:red_sandstone_slab");
        stonecutterCrafting.add("minecraft:cut_red_sandstone_slab");
        stonecutterCrafting.add("minecraft:polished_granite_slab");
        stonecutterCrafting.add("minecraft:smooth_red_sandstone_slab");
        stonecutterCrafting.add("minecraft:polished_diorite_slab");
        stonecutterCrafting.add("minecraft:mossy_cobblestone_slab");
        stonecutterCrafting.add("minecraft:granite_slab");
        stonecutterCrafting.add("minecraft:smooth_sandstone_slab");
        stonecutterCrafting.add("minecraft:andesite_slab");
        stonecutterCrafting.add("minecraft:polished_andesite_slab");
        stonecutterCrafting.add("minecraft:diorite_slab");

        stonecutterCrafting.add("minecraft:cobblestone_stairs");
        stonecutterCrafting.add("minecraft:brick_stairs");
        stonecutterCrafting.add("minecraft:stone_brick_stairs");
        stonecutterCrafting.add("minecraft:sandstone_stairs");
        stonecutterCrafting.add("minecraft:polished_granite_stairs");
        stonecutterCrafting.add("minecraft:smooth_red_sandstone_stairs");
        stonecutterCrafting.add("minecraft:red_sandstone_stairs");
        stonecutterCrafting.add("minecraft:mossy_stone_brick_stairs");
        stonecutterCrafting.add("minecraft:polished_diorite_stairs");
        stonecutterCrafting.add("minecraft:stone_stairs");
        stonecutterCrafting.add("minecraft:smooth_sandstone_stairs");
        stonecutterCrafting.add("minecraft:granite_stairs");
        stonecutterCrafting.add("minecraft:andesite_stairs");
        stonecutterCrafting.add("minecraft:polished_andesite_stairs");
        stonecutterCrafting.add("minecraft:diorite_stairs");

        stonecutterCrafting.add("minecraft:cobblestone_wall");
        stonecutterCrafting.add("minecraft:mossy_cobblestone_wall");
        stonecutterCrafting.add("minecraft:brick_wall");
        stonecutterCrafting.add("minecraft:red_sandstone_wall");
        stonecutterCrafting.add("minecraft:mossy_stone_brick_wall");
        stonecutterCrafting.add("minecraft:granite_wall");
        stonecutterCrafting.add("minecraft:stone_brick_wall");
        stonecutterCrafting.add("minecraft:andesite_wall");
        stonecutterCrafting.add("minecraft:sandstone_wall");
        stonecutterCrafting.add("minecraft:diorite_wall");
        if (!config.isSet("Profession.Stonecutter.crafting")) {config.set("Profession.Stonecutter.crafting", stonecutterCrafting);}
        
        /*
         * Setup Blocks / Recipes
         * for specific Professions (Herbalist)
         */
        
        if (!config.isSet("Profession.Herbalist.Experience.Pickup.min")) {config.set("Profession.Herbalist.Experience.Pickup.min", 2);}
        if (!config.isSet("Profession.Herbalist.Experience.Pickup.max")) {config.set("Profession.Herbalist.Experience.Pickup.max", 5);}
        if (!config.isSet("Profession.Herbalist.Experience.Crafting.min")) {config.set("Profession.Herbalist.Experience.Crafting.min", 4);}
        if (!config.isSet("Profession.Herbalist.Experience.Crafting.max")) {config.set("Profession.Herbalist.Experience.Crafting.max", 6);}
        
        List<String> herbalistBlocks = new ArrayList<>();
        herbalistBlocks.add("minecraft:dandelion");
        herbalistBlocks.add("minecraft:poppy");
        herbalistBlocks.add("minecraft:blue_orchid");
        herbalistBlocks.add("minecraft:allium");
        herbalistBlocks.add("minecraft:azure_bluet");
        herbalistBlocks.add("minecraft:red_tulip");
        herbalistBlocks.add("minecraft:orange_tulip");
        herbalistBlocks.add("minecraft:white_tulip");
        herbalistBlocks.add("minecraft:pink_tulip");
        herbalistBlocks.add("minecraft:oxeye_daisy");
        herbalistBlocks.add("minecraft:cornflower");
        herbalistBlocks.add("minecraft:lily_of_the_valley");
        herbalistBlocks.add("minecraft:wither_rose");
        herbalistBlocks.add("minecraft:brown_mushroom");
        herbalistBlocks.add("minecraft:red_mushroom");
        herbalistBlocks.add("minecraft:chorus_plant");
        herbalistBlocks.add("minecraft:chorus_flower");
        herbalistBlocks.add("minecraft:cactus");
        herbalistBlocks.add("minecraft:lily_pad");
        herbalistBlocks.add("minecraft:sunflower");
        herbalistBlocks.add("minecraft:lilac");
        herbalistBlocks.add("minecraft:rose_bush");
        herbalistBlocks.add("minecraft:peony");
        if (!config.isSet("Profession.Herbalist.blocks")) {config.set("Profession.Herbalist.blocks", herbalistBlocks);}
        
        //TODO
        //FOR TESTING PURPOSE ONLY... POTIONS DONT BELONG TO HERBALIST PROFESSION!
        if (!config.isSet("Profession.Herbalist.Recipes.Absorption_I.name")) {config.set("Profession.Herbalist.Recipes.Absorption_I.name", "Trank der Absorption I");}
        if (!config.isSet("Profession.Herbalist.Recipes.Absorption_I.skill")) {config.set("Profession.Herbalist.Recipes.Absorption_I.skill", 500);}
        if (!config.isSet("Profession.Herbalist.Recipes.Absorption_I.Crafting.sequence")) {config.set("Profession.Herbalist.Recipes.Absorption_I.Crafting.sequence", false);}

        if (!config.isSet("Profession.Herbalist.Recipes.Absorption_I.Crafting.Materials.Material1.material")) {config.set("Profession.Herbalist.Recipes.Absorption_I.Crafting.Materials.Material1.material", "minecraft:potion");}
        if (!config.isSet("Profession.Herbalist.Recipes.Absorption_I.Crafting.Materials.Material2.material")) {config.set("Profession.Herbalist.Recipes.Absorption_I.Crafting.Materials.Material2.material", "minecraft:cornflower");}
        
        /*
         * Setup Blocks / Recipes / Stuff
         * for specific Professions (Inscriber)
         */
        
        //TODO
        
        /*
         * Setup Blocks / Recipes / Stuff
         * for specific Professions (Alchemist)
         */
        
        //TODO
        
        /*
         * Setup Blocks / Recipes / Stuff
         * for specific Professions (Farmer)
         */
        
        List<String> farmerBlocks = new ArrayList<>();
        farmerBlocks.add("minecraft:podzol");
        farmerBlocks.add("minecraft:pumpkin");
        farmerBlocks.add("minecraft:dried_kelp_block");
        farmerBlocks.add("minecraft:bee_nest");
        farmerBlocks.add("minecraft:beehive");
        farmerBlocks.add("minecraft:wheat");
        farmerBlocks.add("minecraft:potatoes");
        farmerBlocks.add("minecraft:carrots");
        farmerBlocks.add("minecraft:beetroots");
        
        //TODO
        
        /*
         * Setup Blocks / Recipes / Stuff
         * for specific Professions (Tanner)
         */
        
        //TODO
        
        if (!config.isSet("Localization.Professions.woodcutter")) {config.set("Localization.Professions.woodcutter", "Holzfäller");}
        if (!config.isSet("Localization.Professions.blacksmith")) {config.set("Localization.Professions.blacksmith", "Waffenschmied");}
        if (!config.isSet("Localization.Professions.stonecutter")) {config.set("Localization.Professions.stonecutter", "Steinmetz");}
        if (!config.isSet("Localization.Professions.herbalist")) {config.set("Localization.Professions.herbalist", "Kräutersammler");}
        if (!config.isSet("Localization.Professions.inscriber")) {config.set("Localization.Professions.inscriber", "Gelehrter");}
        if (!config.isSet("Localization.Professions.alchemist")) {config.set("Localization.Professions.alchemist", "Alchemist");}
        if (!config.isSet("Localization.Professions.farmer")) {config.set("Localization.Professions.farmer", "Bauer");}
        
        if (!config.isSet("Localization.Professions.General.notallowed")) {config.set("Localization.Professions.General.notallowed", "§cDu weißt nicht, wie das geht!");}
        
        if (!config.isSet("Localization.Quests.Inventories.Messages.fulljournal")) {config.set("Localization.Quests.Inventories.Messages.fulljournal", "§cDu kannst nicht mehr Quests gleichzeitig verfolgen!");}
        
        if (!config.isSet("Localization.Quests.Inventories.selector")) {config.set("Localization.Quests.Inventories.selector", "Quest auswählen");}
        
        if (!config.isSet("Localization.Players.Welcome.returning")) {config.set("Localization.Players.Welcome.returning", "Willkommen zurück in der Welt von Lawandja!");}
        if (!config.isSet("Localization.Players.Welcome.new")) {config.set("Localization.Players.Welcome.new", "Herzlich Willkommen in der Welt von Lawandja! Ein großes Abenteuer wartet auf dich!");}
        
        
        if (!config.isSet("Chat.enabled")) {config.set("Chat.enabled", true);}
        if (!config.isSet("Chat.Channels.1.enabled")) {config.set("Chat.Channels.1.enabled", true);}
        if (!config.isSet("Chat.Channels.1.formatting")) {config.set("Chat.Channels.1.formatting", "§a[1. Global][{PLAYER_NAME}]§f");}
        if (!config.isSet("Chat.Channels.1.name")) {config.set("Chat.Channels.1.name", "[1. Global]");}
        if (!config.isSet("Chat.Channels.2.enabled")) {config.set("Chat.Channels.2.enabled", true);}
        if (!config.isSet("Chat.Channels.2.formatting")) {config.set("Chat.Channels.2.formatting", "§a[2. Lawandja][{PLAYER_NAME}]§f");}
        if (!config.isSet("Chat.Channels.2.name")) {config.set("Chat.Channels.2.name", "[2. Lawandja]");}
        if (!config.isSet("Chat.Channels.3.enabled")) {config.set("Chat.Channels.3.enabled", true);}
        if (!config.isSet("Chat.Channels.3.formatting")) {config.set("Chat.Channels.3.formatting", "§6[3. {REGION_NAME}][{PLAYER_NAME}]§f");}
        if (!config.isSet("Chat.Channels.3.name")) {config.set("Chat.Channels.3.name", "[3. Region]");}
        if (!config.isSet("Chat.Channels.4.enabled")) {config.set("Chat.Channels.4.enabled", true);}
        if (!config.isSet("Chat.Channels.4.formatting")) {config.set("Chat.Channels.4.formatting", "[{PLAYER_NAME}]");}
        if (!config.isSet("Chat.Channels.4.name")) {config.set("Chat.Channels.4.name", "[4. Lokal]");}
        
        List<String> denyMessages = new ArrayList<>();
        denyMessages.add("Komm später noch einmal vorbei!");
        denyMessages.add("Ich habe gerade nichts für dich zu tun!");
        denyMessages.add("Ich kann jetzt nicht!");
        denyMessages.add("Siehst du nicht, dass ich gerade beschäftigt bin?");
        denyMessages.add("Stör jemand Anderen!");
        denyMessages.add("Hast du nicht etwas Anderes zu erledigen?");
        if (!config.isSet("Localization.Quests.General.Messages.missingrequiredquests")) {config.set("Localization.Quests.General.Messages.missingrequiredquests", denyMessages);}
        
        saveConfig();
    }
}
