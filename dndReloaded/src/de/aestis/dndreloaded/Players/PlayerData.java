package de.aestis.dndreloaded.Players;

import java.util.Date;
import java.util.HashMap;

import org.bukkit.entity.Entity;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Entites.EntityData;
import de.aestis.dndreloaded.Players.Professions.Profession;
import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.itemManager.enchants.EnchantmentData;

public class PlayerData {
	
	private final Integer ID;
	
	private String Name;
	private String Faction;
	private Integer Reputation;
	private String Title;
	
	private Integer RoleArcher;
	private Integer RoleKnight;
	private Integer RoleBerserk;
	private Integer RoleShield;
	private Integer RoleTank;
	private Integer RoleHealer;
	
	private Integer ProfessionWoodcutter;
	private Integer ProfessionBlacksmith;
	private Integer ProfessionStonecutter;
	private Integer ProfessionHerbalist;
	private Integer ProfessionInscriber;
	private Integer ProfessionAlchemist;
	private Integer ProfessionFarmer;
	private Integer ProfessionTanner;
	
	private Profession ProfessionPrimary;
	private Profession ProfessionSecondary;
	
	private Integer KillsFriendly;
	private Integer KillsEnemy;
	private Quest QuestActive1;
	private Quest QuestActive2;
	private Integer QuestVariable1;
	private Integer QuestVariable2;
	private String QuestsCompleted;
	private Integer Deaths;
	private Integer Punishment;
	private Date Joined;
	
	private EnchantmentData enchantmentData;
	
	private HashMap<Entity, EntityData> EntitiesInRange = new HashMap<Entity, EntityData>();
	
	public PlayerData (Integer ID) {this.ID = ID;}
	
	public Integer getID() {return this.ID;}
	
	public String getName() {return this.Name;}
	public void setName(String Name) {this.Name = Name;}
	
	public String getFaction() {return this.Faction;}
	public void setFaction(String Faction) {this.Faction = Faction;}
	
	public Integer getReputation() {return this.Reputation;}
	public void setReputation(Integer Reputation) {this.Reputation = Reputation;}
	
	public String getTitle() {return this.Title;}
	public void setTitle(String Title) {this.Title = Title;}
	
	
	public Integer getRoleArcher() {return this.RoleArcher;}
	public void setRoleArcher(Integer RoleArcher) {this.RoleArcher = RoleArcher;}
	
	public Integer getRoleKnight() {return this.RoleKnight;}
	public void setRoleKnight(Integer RoleKnight) {this.RoleKnight = RoleKnight;}
	
	public Integer getRoleBerserk() {return this.RoleBerserk;}
	public void setRoleBerserk(Integer RoleBerserk) {this.RoleBerserk = RoleBerserk;}
	
	public Integer getRoleShield() {return this.RoleShield;}
	public void setRoleShield(Integer RoleShield) {this.RoleShield = RoleShield;}
	
	public Integer getRoleTank() {return this.RoleTank;}
	public void setRoleTank(Integer RoleTank) {this.RoleTank = RoleTank;}
	
	public Integer getRoleHealer() {return this.RoleHealer;}
	public void setRoleHealer(Integer RoleHealer) {this.RoleHealer = RoleHealer;}
	
	
	public Integer getProfessionWoodcutter() {return this.ProfessionWoodcutter;}
	public void setProfessionWoodcutter(Integer ProfessionWoodcutter) {this.ProfessionWoodcutter = ProfessionWoodcutter;}
	
	public Integer getProfessionBlacksmith() {return this.ProfessionBlacksmith;}
	public void setProfessionBlacksmith(Integer ProfessionBlacksmith) {this.ProfessionBlacksmith = ProfessionBlacksmith;}
	
	public Integer getProfessionStonecutter() {return this.ProfessionStonecutter;}
	public void setProfessionStonecutter(Integer ProfessionStonecutter) {this.ProfessionStonecutter = ProfessionStonecutter;}
	
	public Integer getProfessionHerbalist() {return this.ProfessionHerbalist;}
	public void setProfessionHerbalist(Integer ProfessionHerbalist) {this.ProfessionHerbalist = ProfessionHerbalist;}
	
	public Integer getProfessionInscriber() {return this.ProfessionInscriber;}
	public void setProfessionInscriber(Integer ProfessionInscriber) {this.ProfessionInscriber = ProfessionInscriber;}
	
	public Integer getProfessionAlchemist() {return this.ProfessionAlchemist;}
	public void setProfessionAlchemist(Integer ProfessionAlchemist) {this.ProfessionAlchemist = ProfessionAlchemist;}
	
	public Integer getProfessionFarmer() {return this.ProfessionFarmer;}
	public void setProfessionFarmer(Integer ProfessionFarmer) {this.ProfessionFarmer = ProfessionFarmer;}
	
	public Integer getProfessionTanner() {return this.ProfessionTanner;}
	public void setProfessionTanner(Integer ProfessionTanner) {this.ProfessionTanner = ProfessionTanner;}
	
	public Profession getProfessionPrimary() {return this.ProfessionPrimary;}
	public void setProfessionPrimary(Profession ProfessionPrimary) {this.ProfessionPrimary = ProfessionPrimary;}
	
	public Profession getProfessionSecondary() {return this.ProfessionSecondary;}
	public void setProfessionSecondary(Profession ProfessionSecondary) {this.ProfessionSecondary = ProfessionSecondary;}
	
	
	public Integer getKillsFriendly() {return this.KillsFriendly;}
	public void setKillsFriendly(Integer KillsFriendly) {this.KillsFriendly = KillsFriendly;}
	
	public Integer getKillsEnemy() {return this.KillsEnemy;}
	public void setKillsEnemy(Integer KillsEnemy) {this.KillsEnemy = KillsEnemy;}
	
	public Quest getQuestActive1() {return this.QuestActive1;}
	public void setQuestActive1(Quest QuestActive1) {this.QuestActive1 = QuestActive1;}
	
	public Quest getQuestActive2() {return this.QuestActive2;}
	public void setQuestActive2(Quest QuestActive2) {this.QuestActive2 = QuestActive2;}
	
	public Integer getQuestVariable1() {return this.QuestVariable1;}
	public void setQuestVariable1(Integer QuestVariable1) {this.QuestVariable1 = QuestVariable1;}
	
	public Integer getQuestVariable2() {return this.QuestVariable2;}
	public void setQuestVariable2(Integer QuestVariable2) {this.QuestVariable2 = QuestVariable2;}
	
	public String getQuestsCompleted() {return this.QuestsCompleted;}
	public void setQuestsCompleted(String QuestsCompleted) {this.QuestsCompleted = QuestsCompleted;}
	
	public Integer getDeaths() {return this.Deaths;}
	public void setDeaths(Integer Deaths) {this.Deaths = Deaths;}
	
	public Integer getPunishment() {return this.Punishment;}
	public void setPunishment(Integer Punishment) {this.Punishment = Punishment;}
	
	public Date getJoined() {return this.Joined;}
	public void setJoined(Date Joined) {this.Joined = Joined;}
	
	public EnchantmentData getEnchantmentData() {return enchantmentData;}
	public void setEnchantmentData(EnchantmentData enchantmentData) {this.enchantmentData = enchantmentData;}
	
	public HashMap<Entity, EntityData> getEntitiesInRange() {return this.EntitiesInRange;}
	public void setEntitiesInRange(Entity entity, EntityData data) {this.EntitiesInRange.put(entity, data);}
	
	
	public void playerDataChangeEvent() {
		//TODO
		
		/*
		 * Implements EventListener on PlayerData change
		 * to tidy up Display on Scoreboards for example
		 */
		
		Main.instance.getServer().getPluginManager().callEvent(null);
	}
}
