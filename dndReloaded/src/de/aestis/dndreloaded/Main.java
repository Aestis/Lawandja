package de.aestis.dndreloaded;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.aestis.dndreloaded.Quests.QuestHandler;
import de.aestis.dndreloaded.Util.QuestMap;
import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Players.PlayerHandler;
import de.aestis.dndreloaded.Players.Professions.ProfessionHandler;
import de.aestis.dndreloaded.Players.Professions.Listeners.ListenerHerbalist;
import de.aestis.dndreloaded.Players.Professions.Listeners.ListenerWoodcutter;
import de.aestis.dndreloaded.Database.Mysql;
import de.aestis.dndreloaded.Helpers.BookHelpers;
import de.aestis.dndreloaded.Helpers.InventoryHelpers;
import de.aestis.dndreloaded.Helpers.MathHelpers;
import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Overrides.BlockBreak;
import de.aestis.dndreloaded.Players.PlayerData;

public class Main extends JavaPlugin {
	
	public static String Version = "0.12.45";
	
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
	private DataSync DataSn;
	private GameTicks GameTcs;
	
	private Mysql Database;
	
	public HashMap<Player, String> SelectedNPC = new HashMap<Player, String>();
	public HashMap<Player, PlayerData> Players = new HashMap<Player, PlayerData>();
	public QuestMap QuestData = new QuestMap();
	
	/*
	 * Enable the whole plugin
	 * (Here we go!)
	 * */
	
	public void onEnable() {
		
		instance=this;
		
		try {
			
			/*
			 * Setup or load config file(s)
			 * */
			
			setupConfigs();
			
			/*FileConfiguration config = this.getConfig();
	        config.options().copyDefaults(true);
	        saveConfig();*/
		} catch (Exception ex) {
			getLogger().severe("Error Whilst Setting Up Configs, Shutting Down...: " + ex);
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
		Database = new Mysql();
		if(!Database.connect()){
			
			/*
			 * Initialize Database connection (essential!)
			 * */
			
			getLogger().severe("No Mysql Connection, Shutting Down...");
			Bukkit.getPluginManager().disablePlugin(this);
		    return;
		} else {
			getLogger().fine("Successfully set Up MySQL Connection.");
		}
		
		try {
			
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
			
			//Handlers
			setDatabaseHandler();
			setQuestHandler();
			setPlayerHandler();
			setProfessionHandler();
			
			//Main Components
			setDataSync();
			setGameTicks();
			
			/*
			 * To be removed (soon)
			 */
			QuestHnd.initialize();
			
			getLogger().info("Sucessfully Enabled All Addons.");
		} catch (Exception ex) {
			getLogger().severe("Error Whilst Initializing Addons: " + ex);
			return;
		}
		
		try {
			
			/*
			 * Create tables if not existing + Hook everything into place :)
			 * */
			
			DatabaseHnd.initializeDatabase();
			
			getServer().getPluginManager().registerEvents((Listener) new EventListener(), this);
			
			/*
			 * Register all Profession
			 * based EventListeners
			 */
			
			getServer().getPluginManager().registerEvents((Listener) new ListenerWoodcutter(), this);
			//...
			getServer().getPluginManager().registerEvents((Listener) new ListenerHerbalist(), this);
			
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
			
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				DataSn.loadPlayerData(p);
				PlayerHnd.setupPlayerProfessions(Players.get(p));
			}
			
			GameTcs.startSyncTask();
			
		} catch (Exception ex) {
			getLogger().severe("Could Not Load Player Data: " + ex);
		}
		
		getLogger().fine("dndReloaded v" + Version + " successfully enabled!");
	}
	
	public void onDisable() {
		getLogger().severe("Lawandja CORE v " + Version + " shutting down...");
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
	
	//Main Components
	private void setDataSync() {this.DataSn = DataSync.getInstance();}
	private void setGameTicks() {this.GameTcs = GameTicks.getInstance();}
	
	
	//Overrides
	public BlockBreak getBlockBreakOverride() {return this.BlockBreakOverride;}
	
	//Helpers
	public InventoryHelpers getInventoryHelper() {return this.InventoryHelper;}
	public MathHelpers getMathHelper() {return this.MathHelper;}
	public ScoreboardHelpers getScoreboardHelper() {return this.ScoreboardHelper;}
	public BookHelpers getBookHelper() {return this.BookHelper;}
	
	//Handlers
	public DatabaseHandler getDatabaseHandler() {return this.DatabaseHnd;}
	public QuestHandler getQuestHandler() {return this.QuestHnd;}
	public PlayerHandler getPlayerHandler() {return this.PlayerHnd;}
	public ProfessionHandler getProfessionHandler() {return this.ProfessionHnd;}
	
	//Main Components
	public DataSync getDataSync() {return this.DataSn;}
	public GameTicks getGameTicks() {return this.GameTcs;}
	
	
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
        if (!config.isSet("Items.Tools.Durability.Messages.cancelled")) {config.set("Items.Tools.Durability.Messages.cancelled", "ßeDas Ding ist kaputt!");}
        
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
        woodcutterBlocks.add("minecraft:oak_slab");
        woodcutterBlocks.add("minecraft:spruce_slab");
        woodcutterBlocks.add("minecraft:birch_slab");
        woodcutterBlocks.add("minecraft:jungle_slab");
        woodcutterBlocks.add("minecraft:acacia_slab");
        woodcutterBlocks.add("minecraft:dark_oak_slab");
        woodcutterBlocks.add("minecraft:oak_stairs");
        woodcutterBlocks.add("minecraft:spruce_stairs");
        woodcutterBlocks.add("minecraft:birch_stairs");
        woodcutterBlocks.add("minecraft:jungle_stairs");
        woodcutterBlocks.add("minecraft:acacia_stairs");
        woodcutterBlocks.add("minecraft:dark_oak_stairs"); 
        woodcutterBlocks.add("minecraft:oak_fence");
        woodcutterBlocks.add("minecraft:spruce_fence");
        woodcutterBlocks.add("minecraft:birch_fence");
        woodcutterBlocks.add("minecraft:jungle_fence");
        woodcutterBlocks.add("minecraft:acacia_fence");
        woodcutterBlocks.add("minecraft:dark_oak_fence");
        woodcutterBlocks.add("minecraft:oak_sign");
        woodcutterBlocks.add("minecraft:spruce_sign");
        woodcutterBlocks.add("minecraft:birch_sign");
        woodcutterBlocks.add("minecraft:jungle_sign");
        woodcutterBlocks.add("minecraft:acacia_sign");
        woodcutterBlocks.add("minecraft:dark_oak_sign");
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
        if (!config.isSet("Profession.Herbalist.blocks")) {config.set("Profession.Herbalist.blocks", blacksmithBlocks);}
        
        /*
         * Setup Blocks / Recipes / Stuff
         * for specific Professions (Stonecutter)
         */
        
        //TODO
        
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
        
        //TODO
        
        /*
         * Setup Blocks / Recipes / Stuff
         * for specific Professions (Tanner)
         */
        
        //TODO
        
        if (!config.isSet("Localization.Professions.woodcutter")) {config.set("Localization.Professions.woodcutter", "Holzf‰ller");}
        if (!config.isSet("Localization.Professions.blacksmith")) {config.set("Localization.Professions.blacksmith", "Waffenschmied");}
        if (!config.isSet("Localization.Professions.stonecutter")) {config.set("Localization.Professions.stonecutter", "Steinmetz");}
        if (!config.isSet("Localization.Professions.herbalist")) {config.set("Localization.Professions.herbalist", "Kr‰utersammler");}
        if (!config.isSet("Localization.Professions.inscriber")) {config.set("Localization.Professions.inscriber", "Gelehrter");}
        if (!config.isSet("Localization.Professions.alchemist")) {config.set("Localization.Professions.alchemist", "Alchemist");}
        if (!config.isSet("Localization.Professions.farmer")) {config.set("Localization.Professions.farmer", "Bauer");}
        
        if (!config.isSet("Localization.Professions.General.notallowed")) {config.set("Localization.Professions.General.notallowed", "ßcDu weiﬂt nicht, wie das geht!");}
        
        if (!config.isSet("Localization.Quests.Inventories.selector")) {config.set("Localization.Quests.Inventories.selector", "Quest ausw‰hlen");}
        
        saveConfig();
    }
}
