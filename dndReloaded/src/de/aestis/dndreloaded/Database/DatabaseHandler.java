package de.aestis.dndreloaded.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.PlayerData;
import de.aestis.dndreloaded.Quests.Quest;

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
	
	public void initializeDatabase() {
		PreparedStatement stmt;
		try {
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
					") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;");
			
			stmt.executeUpdate();
			stmt.close();
			Main.instance.getLogger().info("Required Tables Successfully Created.");
		} catch (SQLException e) {
			Main.instance.getLogger().info("Required Tables Could Not Be Created.");
		}
	}
	
	/*
	 * Simple player related getters/setters
	 * */
	
	public boolean playerRegistered (String player) {
		
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT * FROM players WHERE PlayerName = ?");
			stmt.setString(1, player);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() == false) {
				rs.close();
				stmt.close();
				return false;
			} else {
				rs.close();
				stmt.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean registerPlayer (String player) {
		
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("INSERT INTO players (PlayerName, PlayerJoined) VALUES (?, ?)");
			stmt.setString(1, player);
			stmt.setString(2, this.getDatetime());
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/*
	 * Player related initialization
	 * Fetch -> Setup Class -> Pass
	 * */
	
	public PlayerData getPlayerData(String player) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT * FROM players WHERE PlayerName = ?");
			stmt.setString(1, player);
			ResultSet rs = stmt.executeQuery();
			PlayerData playerData = preparePlayerData(rs);
			rs.close();
			stmt.close();
			return playerData;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private PlayerData preparePlayerData(ResultSet rs) throws SQLException {
		while (rs.next()) {
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
			d.setKillsFriendly(rs.getInt("PlayerKillsFriendly"));
			d.setKillsEnemy(rs.getInt("PlayerKillsEnemy"));
			d.setQuestActive1(rs.getInt("PlayerQuestActive1"));
			d.setQuestActive2(rs.getInt("PlayerQuestActive2"));
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
	
	
	/*
	 * Simple quest related getters/setters
	 * */
	
	public boolean hasQuests (String uuid) {
		
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT * FROM quests WHERE NpcID = ?");
			stmt.setString(1, uuid);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() == false) {
				rs.close();
				stmt.close();
				return false;
			} else {
				rs.close();
				stmt.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/*
	 * Quest related initialization
	 * Fetch -> Setup Class -> Pass
	 * */
	
	public boolean registerQuest (String npc, String title, String creator) {
		
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("INSERT INTO quests (NpcID, QuestTitle, QuestCreator, QuestCreated) VALUES (?, ?, ?, ?)");
			stmt.setString(1, npc);
			stmt.setString(2, title);
			stmt.setString(3, creator);
			stmt.setString(4, this.getDatetime());
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Quest getQuestData(String uuid) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("SELECT * FROM Quests WHERE NpcID = ?");
			stmt.setString(1, uuid);
			ResultSet rs = stmt.executeQuery();
			Quest questData = prepareQuestData(rs);
			rs.close();
			stmt.close();
			return questData;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Quest prepareQuestData(ResultSet rs) throws SQLException {
		while (rs.next()) {
			ItemStack itm = null;
			Quest d = new Quest(rs.getInt("QuestID"));
			d.setNpcID(rs.getString("NpcID"));
			d.setRequired(rs.getInt("QuestRequired"));
			d.setFaction(rs.getString("QuestFaction"));
			d.setMinReputation(rs.getInt("QuestMinReputation"));
			d.setTitle(rs.getString("QuestTitle"));
			d.setDescription(rs.getString("QuestDescription"));
			d.setTarget(rs.getString("QuestTarget"));
			d.setShort(rs.getString("QuestShort"));		
			d.setMessageAccept(rs.getString("QuestMessageAccept"));
			d.setMessageDecline(rs.getString("QuestMessageDecline"));
			d.setMessageRunning(rs.getString("QuestMessageRunning"));
			d.setMessageFail(rs.getString("QuestMessageFail"));
			d.setMessageSuccess(rs.getString("QuestMessageSuccess"));
			if (rs.getString("QuestStarterItem") != null) {
				itm = new ItemStack(Material.matchMaterial(rs.getString("QuestStarterItem")), rs.getInt("QuestStarterItemAmount"));
				d.setStarterItem(itm);
			} else {
				d.setStarterItem(null);
			}	
			d.setType(rs.getString("QuestType"));
			d.setVariable(rs.getInt("QuestVariable"));
			if (rs.getString("QuestItem") != null) {
				itm = new ItemStack(Material.matchMaterial(rs.getString("QuestItem")), rs.getInt("QuestItemAmount"));
				d.setItem(itm);
			} else {
				d.setItem(null);
			}	
		 	d.setDestination(rs.getString("QuestDestination"));
		 	d.setRewardXP(rs.getInt("QuestRewardXP"));
		 	d.setReputationGain(rs.getInt("QuestReputationGain"));
		 	d.setBonusRewardType(rs.getString("QuestBonusRewardType"));
		 	if (rs.getString("QuestBonusReward") != null) {
		 		itm = new ItemStack(Material.matchMaterial(rs.getString("QuestBonusReward")), rs.getInt("QuestBonusRewardAmount"));
		 		d.setBonusReward(itm);
		 	} else {
				d.setBonusReward(null);
			}	
		 	d.setCompletionText(rs.getString("QuestCompletionText"));
		 	d.setDoesFollow(rs.getBoolean("QuestDoesFollow"));
		 	d.setFollowID(rs.getInt("QuestFollowID"));
		 	d.setCreator(rs.getString("QuestCreator"));
		 	d.setCreated(rs.getDate("QuestCreated"));
		 	d.setActive(rs.getBoolean("QuestActive"));
			return d;
		}
		return null;
	}
	
	/*
	 * Little helper(s) (might remove from here)
	 * */
	
	private String getDatetime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	   LocalDateTime now = LocalDateTime.now(); 
	   return dtf.format(now);
	}
	
}