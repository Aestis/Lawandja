package de.aestis.dndreloaded;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.Quests.QuestHandler;
import de.aestis.dndreloaded.Util.QuestMap;
import de.aestis.dndreloaded.Database.DatabaseHandler;
import de.aestis.dndreloaded.Players.PlayerHandler;
import de.aestis.dndreloaded.Players.Professions.Listeners.ListenerHerbalist;
import de.aestis.dndreloaded.Database.Mysql;
import de.aestis.dndreloaded.Helpers.BookHelpers;
import de.aestis.dndreloaded.Helpers.InventoryHelpers;
import de.aestis.dndreloaded.Helpers.MathHelpers;
import de.aestis.dndreloaded.Helpers.ScoreboardHelpers;
import de.aestis.dndreloaded.Overrides.BlockBreak;
import de.aestis.dndreloaded.Players.PlayerData;

public class Main extends JavaPlugin {
	
	public static String Version = "0.11.10";
	
	public static Main instance;
	
	private BlockBreak BlockBreakOverride;
	private InventoryHelpers InventoryHelper;
	private ScoreboardHelpers ScoreboardHelper;
	private MathHelpers MathHelper;
	private BookHelpers BookHelper;
	private DatabaseHandler DatabaseHnd;
	private QuestHandler QuestHnd;
	private PlayerHandler PlayerHnd;
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
        if (!config.isSet("Items.Tools.Durability.Messages.cancelled")) {config.set("Items.Tools.Durability.Messages.cancelled", "§eDas Ding ist kaputt!");}
        
        if (!config.isSet("Items.Tools.Sharpness.enabled")) {config.set("Items.Tools.Sharpness.enabled", true);}
        if (!config.isSet("Items.Tools.Sharpness.chance")) {config.set("Items.Tools.Sharpness.chance", 3);}
        //Sharpness Will Be Accounted For When Calculating Tool Damage

        if (!config.isSet("Block.Drop.Wood.enabled")) {config.set("Block.Drop.Wood.enabled", true);}
        if (!config.isSet("Block.Drop.Wood.usesilktouch")) {config.set("Block.Drop.Wood.usesilktouch", true);}
        if (!config.isSet("Block.Drop.Wood.Amount.min")) {config.set("Block.Drop.Wood.Amount.min", 1);}
        if (!config.isSet("Block.Drop.Wood.Amount.max")) {config.set("Block.Drop.Wood.Amount.max", 3);}
        
        /*
         * Setup Blocks / Recipes
         * for specific Professions (Woodcutter)
         */
        
        List<String> woodcutter = new ArrayList<>();
        woodcutter.add("minecraft:oak_log");
        woodcutter.add("minecraft:spruce_log");
        woodcutter.add("minecraft:birch_log");
        woodcutter.add("minecraft:jungle_log");
        woodcutter.add("minecraft:acacia_log");
        woodcutter.add("minecraft:dark_oak_log");
        woodcutter.add("minecraft:stripped_oak_log");
        woodcutter.add("minecraft:stripped_spruce_log");
        woodcutter.add("minecraft:stripped_birch_log");
        woodcutter.add("minecraft:stripped_jungle_log");
        woodcutter.add("minecraft:stripped_acacia_log");
        woodcutter.add("minecraft:stripped_dark_oak_log");
        woodcutter.add("minecraft:stripped_oak_wood");
        woodcutter.add("minecraft:stripped_spruce_wood");
        woodcutter.add("minecraft:stripped_birch_wood");
        woodcutter.add("minecraft:stripped_jungle_wood");
        woodcutter.add("minecraft:stripped_acacia_wood");
        woodcutter.add("minecraft:stripped_dark_oak_wood");
        if (!config.isSet("Profession.Woodcutter.blocks")) {config.set("Profession.Woodcutter.blocks", woodcutter);}
        
        /*
         * Setup Blocks / Recipes
         * for specific Professions (Herbalist)
         */
        
        List<String> herbalist = new ArrayList<>();
        herbalist.add("minecraft:dandelion");
        herbalist.add("minecraft:poppy");
        herbalist.add("minecraft:blue_orchid");
        herbalist.add("minecraft:allium");
        herbalist.add("minecraft:azure_bluet");
        herbalist.add("minecraft:red_tulip");
        herbalist.add("minecraft:orange_tulip");
        herbalist.add("minecraft:white_tulip");
        herbalist.add("minecraft:pink_tulip");
        herbalist.add("minecraft:oxeye_daisy");
        herbalist.add("minecraft:cornflower");
        herbalist.add("minecraft:lily_of_the_valley");
        herbalist.add("minecraft:wither_rose");
        herbalist.add("minecraft:brown_mushroom");
        herbalist.add("minecraft:red_mushroom");
        herbalist.add("minecraft:chorus_plant");
        herbalist.add("minecraft:chorus_flower");
        herbalist.add("minecraft:cactus");
        herbalist.add("minecraft:lily_pad");
        herbalist.add("minecraft:sunflower");
        herbalist.add("minecraft:lilac");
        herbalist.add("minecraft:rose_bush");
        herbalist.add("minecraft:peony");
        if (!config.isSet("Profession.Herbalist.blocks")) {config.set("Profession.Herbalist.blocks", herbalist);}
        
        if (!config.isSet("Localization.Professions.woodcutter")) {config.set("Localization.Professions.woodcutter", "Holzfäller");}
        if (!config.isSet("Localization.Professions.blacksmith")) {config.set("Localization.Professions.blacksmith", "Waffenschmied");}
        if (!config.isSet("Localization.Professions.stonecutter")) {config.set("Localization.Professions.stonecutter", "Steinmetz");}
        if (!config.isSet("Localization.Professions.herbalist")) {config.set("Localization.Professions.herbalist", "Kräutersammler");}
        if (!config.isSet("Localization.Professions.inscriber")) {config.set("Localization.Professions.inscriber", "Gelehrter");}
        if (!config.isSet("Localization.Professions.alchemist")) {config.set("Localization.Professions.alchemist", "Alchemist");}
        if (!config.isSet("Localization.Professions.farmer")) {config.set("Localization.Professions.farmer", "Bauer");}
        
        if (!config.isSet("Localization.Quests.Inventories.selector")) {config.set("Localization.Quests.Inventories.selector", "Quest auswählen");}
        
        saveConfig();
    }
}
