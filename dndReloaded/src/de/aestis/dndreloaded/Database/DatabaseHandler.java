package de.aestis.dndreloaded.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Auctions.Util.Auction;
import de.aestis.dndreloaded.Auctions.Util.AuctionCategory;
import de.aestis.dndreloaded.Auctions.Util.AuctionCategory.AuctionCategories;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.Quests.QuestHandler;
import de.aestis.dndreloaded.Quests.QuestTypes;

public class DatabaseHandler {
	
	private static DatabaseHandler instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	private Connection con = Plugin.getConnection();
	
	public static DatabaseHandler getInstance() {
		if (instance == null) {
			instance = new DatabaseHandler();
		}
		return instance;
	}
	
	/**
	 * Initialize Database on startup
	 * (if not exists)
	 */
	public void initializeDatabase() {
		
		PreparedStatement stmt;
		try
		{
			stmt = con.prepareStatement("CREATE TABLE `players` (" + 
					"  `PlayerID` int(11) NOT NULL," + 
					"  `PlayerName` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `PlayerFaction` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'FREE'," + 
					"  `PlayerReputation` int(12) NOT NULL DEFAULT '0'," + 
					"  `PlayerTitle` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Neuling'," + 
					"  `PlayerRoleArcher` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerRoleKnight` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerRoleBerserk` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerRoleShield` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerRoleTank` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerRoleHealer` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerProfessionWoodcutter` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerProfessionBlacksmith` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerProfessionStonecutter` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerProfessionHerbalist` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerProfessionInscriber` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerProfessionAlchemist` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerProfessionFarmer` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerProfessionTanner` int(16) NOT NULL DEFAULT '-1'," + 
					"  `PlayerKillsFriendly` int(11) NOT NULL DEFAULT '0'," + 
					"  `PlayerKillsEnemy` int(11) NOT NULL DEFAULT '0'," + 
					"  `PlayerQuestActive1` int(8) DEFAULT NULL," + 
					"  `PlayerQuestActive2` int(8) DEFAULT NULL," + 
					"  `PlayerQuestVariable1` int(12) DEFAULT NULL," + 
					"  `PlayerQuestVariable2` int(12) DEFAULT NULL," + 
					"  `PlayerQuestsCompleted` longtext COLLATE utf8mb4_unicode_ci," + 
					"  `PlayerDeaths` int(11) NOT NULL DEFAULT '0'," + 
					"  `PlayerPunishment` int(11) NOT NULL DEFAULT '0'," + 
					"  `PlayerJoined` datetime DEFAULT NULL" + 
					"  PRIMARY KEY (PlayerID)" + 
					") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;");
			
			stmt.executeUpdate();
			Main.instance.getLogger().info("Required table 'players' successfully created.");			
		} catch (SQLException e)
		{
			Main.instance.getLogger().info("Required table 'players' already exist. Nothing changed.");
		}
		
		try
		{
			stmt = con.prepareStatement("CREATE TABLE `quests` (" + 
					"  `QuestID` int(8) NOT NULL," + 
					"  `NpcID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestRequired` int(8) NOT NULL DEFAULT '0'," + 
					"  `QuestFaction` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestMinReputation` int(12) DEFAULT NULL," + 
					"  `QuestTitle` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestDescription` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestTarget` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestShort` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestMessageAccept` varchar(192) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestMessageDecline` varchar(192) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestMessageRunning` varchar(192) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestMessageFail` varchar(192) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestMessageSuccess` varchar(192) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestStarterItem` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestStarterItemAmount` int(3) NOT NULL DEFAULT '0'," + 
					"  `QuestType` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestVariable` int(12) DEFAULT NULL," + 
					"  `QuestItem` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestItemAmount` int(3) NOT NULL DEFAULT '0'," + 
					"  `QuestDestination` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestRegion` varchar(96) COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
					"  `QuestMobType` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
					"  `QuestBlockMaterial` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
					"  `QuestRewardXP` int(16) NOT NULL DEFAULT '0'," + 
					"  `QuestReputationGain` int(11) DEFAULT NULL," + 
					"  `QuestBonusRewardType` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestBonusReward` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestBonusRewardAmount` int(3) DEFAULT '0'," + 
					"  `QuestCompletionText` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestDoesFollow` tinyint(1) NOT NULL DEFAULT '0'," + 
					"  `QuestFollowID` int(8) DEFAULT '0'," + 
					"  `QuestCreator` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `QuestCreated` datetime DEFAULT NULL," + 
					"  `DevQuestReportedStatus` int(11) DEFAULT '1'," + 
					"  `DevQuestReportedAsignee` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL," + 
					"  `DevQuestReportedChange` date DEFAULT NULL," + 
					"  `QuestActive` tinyint(1) NOT NULL DEFAULT '0'" + 
					"  PRIMARY KEY (QuestID)" + 
					") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;");	
			stmt.executeUpdate();
			Main.instance.getLogger().info("Required table 'quests' successfully created.");			
			stmt.close();
		} catch (SQLException e)
		{
			Main.instance.getLogger().info("Required table 'quests' already exist. Nothing changed.");
		}
	}
	
	/**
	 * Simple player related getters/setters
	 * Handling not required
	 * @param player (Players Name)
	 * @return true/false
	 */
	public boolean playerRegistered (String player) {
		
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT * FROM `players` WHERE PlayerName = ?");
			stmt.setString(1, player);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() == false)
			{
				rs.close();
				stmt.close();
				return false;
			} else
			{
				rs.close();
				stmt.close();
				return true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Registers player directly to
	 * Database if not exists
	 * @param player (Players Name)
	 * @return true/false
	 */
	public boolean registerPlayer (String player) {
		
		PreparedStatement stmt;
		try
		{
			stmt = con.prepareStatement("INSERT INTO `players` (PlayerName, PlayerJoined) VALUES (?, ?)");
			stmt.setString(1, player);
			stmt.setString(2, this.getDatetime());
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Saves PlayerData asynchronously to
	 * Database (avoiding lags)
	 * @param player (Players Name)
	 * @param pd (Players PlayerData)
	 * @return true/false
	 */
	public boolean savePlayerData (String player, PlayerData pd) {
		
		PreparedStatement stmt;
		try
		{
			stmt = con.prepareStatement("UPDATE `players` SET " +
										"`PlayerFaction` = ?, " +
										"`PlayerReputation` = ?, " +
										"`PlayerTitle` = ?, " +
										"`PlayerRoleArcher` = ?, " +
										"`PlayerRoleKnight` = ?, " +
										"`PlayerRoleBerserk` = ?, " +
										"`PlayerRoleShield` = ?, " +
										"`PlayerRoleTank` = ?, " +
										"`PlayerRoleHealer` = ?, " +
										"`PlayerProfessionWoodcutter` = ?, " +
										"`PlayerProfessionBlacksmith` = ?, " +
										"`PlayerProfessionStonecutter` = ?, " +
										"`PlayerProfessionHerbalist` = ?, " +
										"`PlayerProfessionInscriber` = ?, " +
										"`PlayerProfessionAlchemist` = ?, " +
										"`PlayerProfessionFarmer` = ?, " +
										"`PlayerProfessionTanner` = ?, " +
										"`PlayerKillsFriendly` = ?, " +
										"`PlayerKillsEnemy` = ?, " +
										"`PlayerQuestActive1` = ?, " +
										"`PlayerQuestActive2` = ?, " +
										"`PlayerQuestVariable1` = ?, " +
										"`PlayerQuestVariable2` = ?, " +
										"`PlayerQuestsCompleted` = ?, " +
										"`PlayerDeaths` = ?, " +
										"`PlayerPunishment` = ? " +
										"WHERE PlayerID = ?");
			stmt.setString(1, pd.getFaction());
			stmt.setInt(2, pd.getReputation());
			stmt.setString(3, pd.getTitle());
			stmt.setInt(4, pd.getRoleArcher());
			stmt.setInt(5, pd.getRoleKnight());
			stmt.setInt(6, pd.getRoleBerserk());
			stmt.setInt(7, pd.getRoleShield());
			stmt.setInt(8, pd.getRoleTank());
			stmt.setInt(9, pd.getRoleHealer());
			stmt.setInt(10, pd.getProfessionWoodcutter());
			stmt.setInt(11, pd.getProfessionBlacksmith());
			stmt.setInt(12, pd.getProfessionStonecutter());
			stmt.setInt(13, pd.getProfessionHerbalist());
			stmt.setInt(14, pd.getProfessionInscriber());
			stmt.setInt(15, pd.getProfessionAlchemist());
			stmt.setInt(16, pd.getProfessionFarmer());
			stmt.setInt(17, pd.getProfessionTanner());
			stmt.setInt(18, pd.getKillsFriendly());
			stmt.setInt(19, pd.getKillsEnemy());
			
			if (pd.getQuestActive1() != null)
			{
				stmt.setInt(20, pd.getQuestActive1().getID());
			} else
			{
				stmt.setInt(20, -1);
			}
			
			if (pd.getQuestActive2() != null)
			{
				stmt.setInt(21, pd.getQuestActive2().getID());
			} else
			{
				stmt.setInt(21, -1);
			}
			
			stmt.setInt(22, pd.getQuestVariable1());
			stmt.setInt(23, pd.getQuestVariable2());
			stmt.setString(24, pd.getQuestsCompleted());
			stmt.setInt(25, pd.getDeaths());
			stmt.setInt(26, pd.getPunishment());
			stmt.setInt(27, pd.getID());
			stmt.executeUpdate();
			
			Plugin.getLogger().info("Sucessfully synchronized PlayerData for Player " + player + "!");
			
			stmt.close();
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean saveQuestData (Quest q) {
		
		PreparedStatement stmt;
		try
		{
			stmt = con.prepareStatement("UPDATE `quests` SET " +
										"`NpcID` = ?, " +
										"`QuestRequired` = ?, " +
										"`QuestFaction` = ?, " +
										"`QuestMinReputation` = ?, " +
										"`QuestTitle` = ?, " +
										"`QuestIcon` = ?, " +
										"`QuestDescription` = ?, " +
										"`QuestTarget` = ?, " +
										"`QuestShort` = ?, " +
										"`QuestMessageAccept` = ?, " +
										"`QuestMessageDecline` = ?, " +
										"`QuestMessageRunning` = ?, " +
										"`QuestMessageFail` = ?, " +
										"`QuestMessageSuccess` = ?, " +
										"`QuestStarterItem` = ?, " +
										"`QuestStarterItemAmount` = ?, " +
										"`QuestType` = ?, " +
										"`QuestVariable` = ?, " +
										"`QuestItem` = ?, " +
										"`QuestItemAmount` = ?, " +
										"`QuestDestination` = ?, " +
										"`QuestRegion` = ?, " +
										"`QuestMobType` = ?, " +
										"`QuestBlockMaterial` = ?, " +
										"`QuestRewardXP` = ?, " +
										"`QuestReputationGain` = ?, " +
										"`QuestBonusRewardType` = ?, " +
										"`QuestBonusReward` = ?, " +
										"`QuestBonusRewardAmount` = ?, " +
										"`QuestCompletionText` = ?, " +
										"`QuestDoesFollow` = ?, " +
										"`QuestFollowID` = ?, " +
										"`QuestCreator` = ?, " +
										//"`QuestCreated` = ? " +
										//"`DevQuestReportedStatus` = ? " +
										//"`DevQuestReportedAsignee` = ? " +
										//"`DevQuestReportedChange` = ? " +
										"`QuestActive` = ? " +
										"WHERE QuestID = ?");
			stmt.setString(1, q.getNpcID());
			stmt.setInt(2, q.getRequired());
			stmt.setString(3, q.getFaction());
			stmt.setInt(4, q.getMinReputation());
			stmt.setString(5, q.getTitle());
			
			if (q.getIcon() != null)
			{
				stmt.setString(6, "minecraft:" + q.getIcon().getType().name());
			} else
			{
				stmt.setString(6, null);
			}
			
			stmt.setString(7, q.getDescription());
			stmt.setString(8, q.getTarget());
			stmt.setString(9, q.getShort());
			stmt.setString(10, q.getMessageAccept());
			stmt.setString(11, q.getMessageDecline());
			stmt.setString(12, q.getMessageRunning());
			stmt.setString(13, q.getMessageFail());
			stmt.setString(14, q.getMessageSuccess());
			
			if (q.getStarterItem() != null)
			{
				stmt.setString(15, "minecraft:" + q.getStarterItem().getType().name());
				stmt.setInt(16, q.getStarterItem().getAmount());
			} else
			{
				stmt.setString(15, null);
				stmt.setInt(16, 0);
			}
			
			stmt.setString(17, q.getType().name());
			stmt.setInt(18, q.getVariable());
			
			if (q.getItem() != null)
			{
				stmt.setString(19, "minecraft:" + q.getItem().getType().name());
				stmt.setInt(20, q.getItem().getAmount());
			} else
			{
				stmt.setString(19, null);
				stmt.setInt(20, 0);
			}
			
			stmt.setString(21, q.getDestination());
			stmt.setString(22, q.getRegion());
			
			if (q.getMobType() != null)
			{
				stmt.setString(23, q.getMobType().name());
			} else
			{
				stmt.setString(23, null);
			}
				
			if (q.getBlockMaterial() != null)
			{
				stmt.setString(24, "minecraft:" + q.getBlockMaterial().name());
			} else
			{
				stmt.setString(24, null);
			}

			stmt.setInt(25, q.getRewardXP());
			stmt.setInt(26, q.getReputationGain());
			stmt.setString(27, q.getBonusRewardType());
			
			if (q.getBonusReward() != null)
			{
				stmt.setString(28, q.getBonusReward().getType().name());
				stmt.setInt(29, q.getBonusReward().getAmount());
			} else
			{
				stmt.setString(28, null);
				stmt.setInt(29, 0);
			}
		
			stmt.setString(30, q.getCompletionText());
			stmt.setInt(31, q.getDoesFollow() ? 1 : 0);
			stmt.setInt(32, q.getFollowID());
			stmt.setString(33, q.getCreator());
			//stmt.setString(34, q.getCreated());
			//stmt.setString(35, q.);
			//stmt.setString(36, q.);
			//stmt.setString(37, q.);
			stmt.setInt(34, q.getActive() ? 1 : 0);
			stmt.setInt(35, q.getID());
			
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * Gets PlayerData directly from
	 * Database (prioritized!)
	 * @param player (Players Name)
	 * @return PlayerData
	 */
	public PlayerData getPlayerData(String player) {
		
		PreparedStatement stmt;
		try
		{
			stmt = con.prepareStatement("SELECT * FROM `players` WHERE PlayerName = ?");
			stmt.setString(1, player);
			ResultSet rs = stmt.executeQuery();
			PlayerData playerData = preparePlayerData(rs);
			rs.close();
			stmt.close();
			return playerData;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private PlayerData preparePlayerData(ResultSet rs) throws SQLException {
		
		while (rs.next())
		{
			PlayerData d = new PlayerData(rs.getInt("PlayerID"));
			d.setName(rs.getString("PlayerName"));
			d.setFaction(rs.getString("PlayerFaction"));
			d.setReputation(rs.getInt("PlayerReputation"));
			d.setTitle(rs.getString("PlayerTitle"));
			d.setRoleArcher(rs.getInt("PlayerRoleArcher"));
			d.setRoleKnight(rs.getInt("PlayerRoleKnight"));
			d.setRoleBerserk(rs.getInt("PlayerRoleBerserk"));
			d.setRoleShield(rs.getInt("PlayerRoleShield"));
			d.setRoleTank(rs.getInt("PlayerRoleTank"));
			d.setRoleHealer(rs.getInt("PlayerRoleHealer"));	
			d.setProfessionWoodcutter(rs.getInt("PlayerProfessionWoodcutter"));
			d.setProfessionBlacksmith(rs.getInt("PlayerProfessionBlacksmith"));
			d.setProfessionStonecutter(rs.getInt("PlayerProfessionStonecutter"));
			d.setProfessionHerbalist(rs.getInt("PlayerProfessionHerbalist"));
			d.setProfessionInscriber(rs.getInt("PlayerProfessionInscriber"));
			d.setProfessionAlchemist(rs.getInt("PlayerProfessionAlchemist"));
			d.setProfessionFarmer(rs.getInt("PlayerProfessionFarmer"));	
			d.setProfessionTanner(rs.getInt("PlayerProfessionTanner"));
			d.setKillsFriendly(rs.getInt("PlayerKillsFriendly"));
			d.setKillsEnemy(rs.getInt("PlayerKillsEnemy"));
			
			if (rs.getInt("PlayerQuestActive1") == -1 ||
				rs.getInt("PlayerQuestActive1") == 0)
			{
				d.setQuestActive1(null);
			} else {
				Quest questActive1 = Plugin.QuestData.getQuestByID(rs.getInt("PlayerQuestActive1"));
				
				if (questActive1 != null)
				{
					d.setQuestActive1(questActive1);
				} else
				{
					Plugin.getLogger().severe("QUEST DATA {SLOT_1} CORRUPTED!");
				}
			}
				
			if (rs.getInt("PlayerQuestActive2") == -1 ||
				rs.getInt("PlayerQuestActive2") == 0)
			{
				d.setQuestActive2(null);
			} else {
				Quest questActive2 = Plugin.QuestData.getQuestByID(rs.getInt("PlayerQuestActive2"));
				
				if (questActive2 != null)
				{
					d.setQuestActive2(questActive2);
				} else
				{
					Plugin.getLogger().severe("QUEST DATA {SLOT_2] CORRUPTED!");
				}
				
				d.setQuestActive2(questActive2);
			}
			
			d.setQuestVariable1(rs.getInt("PlayerQuestVariable1"));
			d.setQuestVariable2(rs.getInt("PlayerQuestVariable2"));
			d.setQuestsCompleted(rs.getString("PlayerQuestsCompleted"));
			d.setDeaths(rs.getInt("PlayerDeaths"));
			d.setPunishment(rs.getInt("PlayerPunishment"));
			d.setJoined(rs.getDate("PlayerJoined"));
			return d;
		}
		return null;
	}
	
	/**
	 * Checks if the selected NPC (uuid) has quests
	 * directly from Database (not recommended in most usecases)
	 * @param uuid (NPCs UUID)
	 * @return true/false
	 */
	public boolean hasQuests (String uuid) {
		
		/*
		 * Handle DB access here to prevent crashes
		 * or lags on incorrect handling.
		 * NOTE: Wrong access always returns FALSE!
		 */
		
		if (Plugin.QuestData != null) return false;
		
		PreparedStatement stmt;
		try
		{
			stmt = con.prepareStatement("SELECT * FROM quests WHERE NpcID = ?");
			stmt.setString(1, uuid);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() == false)
			{
				rs.close();
				stmt.close();
				return false;
			} else
			{
				rs.close();
				stmt.close();
				return true;
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Registers new Quest directly to the
	 * Database (prioritized!)
	 * @param npc (NPCs UUID)
	 * @param title (Title of the Quest)
	 * @param creator (Creators Name)
	 * @return true/false
	 */
	public boolean registerQuest (String npc, String title, String creator) {
		
		PreparedStatement stmt;
		try
		{
			stmt = con.prepareStatement("INSERT INTO quests (NpcID, QuestTitle, QuestCreator, QuestCreated) VALUES (?, ?, ?, ?)");
			stmt.setString(1, npc);
			stmt.setString(2, title);
			stmt.setString(3, creator);
			stmt.setString(4, this.getDatetime());
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
		
	/**
	 * Fetches all available Quests from the NPC
	 * by using his UUID (prioritized!)
	 * @param uuid (NPCs UUID)
	 * @return QuestList
	 */
	public List<Quest> getQuestData(String uuid) {
		
		PreparedStatement stmt;
		try
		{
			stmt = con.prepareStatement("SELECT * FROM Quests WHERE NpcID = ?");
			stmt.setString(1, uuid);
			ResultSet rs = stmt.executeQuery();
			
			List<Quest> list = new ArrayList<Quest>();
			list.addAll(prepareQuestData(rs));	
			for (Quest q : list) Plugin.getLogger().info("Quest {" + q.getID() + "} '" + q.getTitle() + "' added to list!");
			
			rs.close();
			stmt.close();
			
			return list;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Quest> prepareQuestData(ResultSet rs) throws SQLException {
		
		List<Quest> list = new ArrayList<Quest>();
		
		while (rs.next())
		{
			ItemStack itm = null;
			Quest d = new Quest(rs.getInt("QuestID"));
			d.setNpcID(rs.getString("NpcID"));
			d.setRequired(rs.getInt("QuestRequired"));
			d.setFaction(rs.getString("QuestFaction"));
			d.setMinReputation(rs.getInt("QuestMinReputation"));
			d.setTitle(rs.getString("QuestTitle"));
			
			if (rs.getString("QuestIcon") != null)
			{
				itm = new ItemStack(Material.matchMaterial(rs.getString("QuestIcon")), 1);
				d.setIcon(itm);
			} else
			{
				d.setIcon(null);
			}
			
			d.setDescription(rs.getString("QuestDescription"));
			d.setTarget(rs.getString("QuestTarget"));
			d.setShort(rs.getString("QuestShort"));		
			d.setMessageAccept(rs.getString("QuestMessageAccept"));
			d.setMessageDecline(rs.getString("QuestMessageDecline"));
			d.setMessageRunning(rs.getString("QuestMessageRunning"));
			d.setMessageFail(rs.getString("QuestMessageFail"));
			d.setMessageSuccess(rs.getString("QuestMessageSuccess"));
			
			if (rs.getString("QuestStarterItem") != null)
			{
				itm = new ItemStack(Material.matchMaterial(rs.getString("QuestStarterItem")), rs.getInt("QuestStarterItemAmount"));
				d.setStarterItem(itm);
			} else
			{
				d.setStarterItem(null);
			}
			
			d.setType(QuestTypes.valueOf(rs.getString("QuestType")));
			d.setVariable(rs.getInt("QuestVariable"));
			
			if (rs.getString("QuestItem") != null)
			{
				itm = new ItemStack(Material.matchMaterial(rs.getString("QuestItem")), rs.getInt("QuestItemAmount"));
				d.setItem(itm);
			} else
			{
				d.setItem(null);
			}
			
		 	d.setDestination(rs.getString("QuestDestination"));
		 	d.setRegion(rs.getString("QuestRegion"));
		 	
		 	if (rs.getString("QuestMobType") != null)
		 	{
		 		d.setMobType(EntityType.valueOf(rs.getString("QuestMobType")));
		 	} else
		 	{
		 		d.setMobType(null);
		 	}
		 	
		 	if (rs.getString("QuestBlockMaterial") != null)
		 	{
		 		d.setBlockMaterial(Material.matchMaterial(rs.getString("QuestBlockMaterial")));
		 	} else
		 	{
		 		d.setBlockMaterial(null);
		 	}
		 	
		 	d.setRewardXP(rs.getInt("QuestRewardXP"));
		 	d.setReputationGain(rs.getInt("QuestReputationGain"));
		 	d.setBonusRewardType(rs.getString("QuestBonusRewardType"));
		 	
		 	if (rs.getString("QuestBonusReward") != null)
		 	{
		 		itm = new ItemStack(Material.matchMaterial(rs.getString("QuestBonusReward")), rs.getInt("QuestBonusRewardAmount"));
		 		d.setBonusReward(itm);
		 	} else
		 	{
				d.setBonusReward(null);
			}
		 	
		 	d.setCompletionText(rs.getString("QuestCompletionText"));
		 	d.setDoesFollow(rs.getBoolean("QuestDoesFollow"));
		 	d.setFollowID(rs.getInt("QuestFollowID"));
		 	d.setCreator(rs.getString("QuestCreator"));
		 	d.setCreated(rs.getDate("QuestCreated"));
		 	d.setActive(rs.getBoolean("QuestActive"));
			
		 	list.add(d);
		}
		
		return list;
	}
	
	/**
	 * Fetches all available Quests from the NPC
	 * by using his UUID (prioritized!)
	 * @param uuid (NPCs UUID)
	 * @return QuestList
	 */
	public List<Auction> getAuctionData(String uuid) {
		
		PreparedStatement stmt;
		try
		{
			stmt = con.prepareStatement("SELECT * FROM Auctions WHERE AuctionActive = 1");
			ResultSet rs = stmt.executeQuery();
			
			List<Auction> list = new ArrayList<Auction>();
			list.addAll(prepareAuctionData(rs));	
			for (Auction a : list) Plugin.getLogger().info("Auction {" + a.getID() + "} by '" + a.getSeller().getName() + "' in Category " + a.getCategory() + " added to list!");
			
			rs.close();
			stmt.close();
			
			return list;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Auction> prepareAuctionData(ResultSet rs) throws SQLException {
		
		List<Auction> list = new ArrayList<Auction>();
		
		while (rs.next())
		{
			ItemStack itm = null;
			Auction d = new Auction(rs.getInt("AuctionID"));
			
			AuctionCategories cat = AuctionCategories.valueOf(rs.getString("AuctionCategory"));
			
			if (cat != null)
			{
				d.setCategory(cat);
			} else
			{
				d.setCategory(null);
			}
			
			d.setFaction(rs.getString("AuctionFaction"));
			
			if (rs.getString("AuctionItem") != null)
			{
				itm = new ItemStack(Material.matchMaterial(rs.getString("AuctionItem")), rs.getInt("AuctionAmount"));
				d.setItem(itm);
			}
			
			d.setPrice(rs.getDouble("AuctionPrice"));
			d.setSeller(Bukkit.getPlayer(rs.getString("AuctionSeller")));
			//d.setTimestamp(rs.getDate("AuctionDuration").);
			d.setDuration(rs.getInt("AuctionDuration"));
			
		 	list.add(d);
		}
		
		return list;
	}
	
	/**
	 * Little helper to use localized and
	 * prettified timestamp (GER)
	 * @return Prettified DateTime
	 */
	private String getDatetime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	   LocalDateTime now = LocalDateTime.now(); 
	   return dtf.format(now);
	}
	
}
