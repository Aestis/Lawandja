package de.aestis.dndreloaded.Quests;

import java.util.Date;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class Quest {
	
	private final Integer ID;
	
	private String NpcID;
	private Integer Required;
	private String Faction;
	private Integer MinReputation;
	private String Title;
	private ItemStack Icon;
	private String Description;
	private String Target;
	private String Short;
	private String MessageAccept;
	private String MessageDecline;
	private String MessageRunning;
	private String MessageFail;
	private String MessageSuccess;
	
	private ItemStack StarterItem;
	private QuestTypes Type;
	private Integer Variable;
	private ItemStack Item;
	private String Destination;
	private String Region;
	private EntityType MobType;
	private Material BlockMaterial;
	private Integer RewardXP;
	private Integer ReputationGain;
	private String BonusRewardType;
	private ItemStack BonusReward;
	private String CompletionText;
	private Boolean DoesFollow;
	private Integer FollowID;
	private String Creator;
	private Date Created;
	private Boolean Active;
	
	public Quest (Integer ID) {this.ID = ID;}
	
	public Integer getID() {return this.ID;}
	
	public String getNpcID() {return this.NpcID;}
	public void setNpcID(String NpcID) {this.NpcID = NpcID;}
	
	public Integer getRequired() {return this.Required;}
	public void setRequired (Integer Required) {this.Required = Required;}
	
	public String getFaction() {return this.Faction;}
	public void setFaction(String Faction) {this.Faction = Faction;}
	
	public Integer getMinReputation() {return this.MinReputation;}
	public void setMinReputation(Integer MinReputation) {this.MinReputation = MinReputation;}
	
	public String getTitle() {return this.Title;}
	public void setTitle(String Title) {this.Title = Title;}
	
	public ItemStack getIcon() {return this.Icon;}
	public void setIcon(ItemStack Icon) {this.Icon = Icon;}
	
	public String getDescription() {return this.Description;}
	public void setDescription(String Description) {this.Description = Description;}
	
	public String getTarget() {return this.Target;}
	public void setTarget(String Target) {this.Target = Target;}
	
	public String getShort() {return this.Short;}
	public void setShort(String Short) {this.Short = Short;}
	
	
	public String getMessageAccept() {return this.MessageAccept;}
	public void setMessageAccept(String MessageAccept) {this.MessageAccept = MessageAccept;}
	
	public String getMessageDecline() {return this.MessageDecline;}
	public void setMessageDecline(String MessageDecline) {this.MessageDecline = MessageDecline;}
	
	public String getMessageRunning() {return this.MessageRunning;}
	public void setMessageRunning(String MessageRunning) {this.MessageRunning = MessageRunning;}
	
	public String getMessageFail() {return this.MessageFail;}
	public void setMessageFail(String MessageFail) {this.MessageFail = MessageFail;}
	
	public String getMessageSuccess() {return this.MessageSuccess;}
	public void setMessageSuccess(String MessageSuccess) {this.MessageSuccess = MessageSuccess;}
	
	
	public ItemStack getStarterItem() {return this.StarterItem;}
	public void setStarterItem(ItemStack StarterItem) {this.StarterItem = StarterItem;}
	
	public QuestTypes getType() {return this.Type;}
	public void setType(QuestTypes Type) {this.Type = Type;}
	
	public Integer getVariable() {return this.Variable;}
	public void setVariable(Integer Variable) {this.Variable = Variable;}
	
	public ItemStack getItem() {return this.Item;}
	public void setItem(ItemStack Item) {this.Item = Item;}
	
	public String getDestination() {return this.Destination;}
	public void setDestination(String Destination) {this.Destination = Destination;}
	
	public String getRegion() {return this.Region;}
	public void setRegion(String Region) {this.Region = Region;}
	
	public EntityType getMobType() {return this.MobType;}
	public void setMobType(EntityType MobType) {this.MobType = MobType;}
	
	public Material getBlockMaterial() {return this.BlockMaterial;}
	public void setBlockMaterial(Material BlockMaterial) {this.BlockMaterial = BlockMaterial;}
	
	public Integer getRewardXP() {return this.RewardXP;}
	public void setRewardXP(Integer RewardXP) {this.RewardXP = RewardXP;}
	
	public Integer getReputationGain() {return this.ReputationGain;}
	public void setReputationGain(Integer ReputationGain) {this.ReputationGain = ReputationGain;}
	
	public String getBonusRewardType() {return this.BonusRewardType;}
	public void setBonusRewardType(String BonusRewardType) {this.BonusRewardType = BonusRewardType;}
	
	public ItemStack getBonusReward() {return this.BonusReward;}
	public void setBonusReward(ItemStack BonusReward) {this.BonusReward = BonusReward;}
	
	public String getCompletionText() {return this.CompletionText;}
	public void setCompletionText(String CompletionText) {this.CompletionText = CompletionText;}
	
	public Boolean getDoesFollow() {return this.DoesFollow;}
	public void setDoesFollow(Boolean DoesFollow) {this.DoesFollow = DoesFollow;}
	
	public Integer getFollowID() {return this.FollowID;}
	public void setFollowID(Integer FollowID) {this.FollowID = FollowID;}
	
	public String getCreator() {return this.Creator;}
	public void setCreator(String Creator) {this.Creator = Creator;}
	
	public Date getCreated() {return this.Created;}
	public void setCreated (Date Created) {this.Created = Created;}
	
	public Boolean getActive() {return this.Active;}
	public void setActive(Boolean Active) {this.Active = Active;}
	
}
