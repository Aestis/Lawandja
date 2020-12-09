package de.aestis.dndreloaded.Quests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemID;

public class Quest {
	
	private final Integer ID;
	
	private String NpcID;
	private int Required;
	private String Faction;
	private int MinReputation;
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
	
	private CustomItem StarterItem;
	private QuestTypes Type;
	private int Variable;
	private CustomItem Item;
	private String Destination;
	private String Region;
	private EntityType MobType;
	private Material BlockMaterial;
	private int RewardXP;
	private int ReputationGain;
	private String BonusRewardType;
	private CustomItem BonusReward;
	private String CompletionText;
	private Boolean DoesFollow;
	private int FollowID;
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
	
	
	public CustomItem getStarterItem() {return this.StarterItem;}
	public void setStarterItem(CustomItem StarterItem) {this.StarterItem = StarterItem;}
	
	public QuestTypes getType() {return this.Type;}
	public void setType(QuestTypes Type) {this.Type = Type;}
	
	public int getVariable() {return this.Variable;}
	public void setVariable(Integer Variable) {this.Variable = Variable;}
	
	public CustomItem getItem() {return this.Item;}
	public void setItem(CustomItem Item) {this.Item = Item;}
	
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
	
	public CustomItem getBonusReward() {return this.BonusReward;}
	public void setBonusReward(CustomItem BonusReward) {this.BonusReward = BonusReward;}
	
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
	
	
	public static Quest fromString(String s) {
		if (!s.startsWith("Quest [")) {
			return null;
		}
		Quest q = new Quest(Integer.valueOf(s.substring(s.indexOf('=') +2, s.indexOf(',') - 1)));
		
		s = s.substring(s.indexOf(',') + 2);
		for (String part: s.split("}, ")) {
			String[] fields = part.split("=\\{");
			if (fields.length == 1) {
				fields = new String[] {fields[0],null};
			}
			if (fields.length == 1 || fields[1] == null || fields[1].equals("null")) {
				continue;
			}
			switch (fields[0]) {
			case "NpcID":
				q.NpcID = fields[1];
				break;
			case "Required":
				q.Required = Integer.valueOf(fields[1]);
				break;
			case "Faction":
				q.Faction = fields[1];
				break;
			case "MinReputation":
				q.MinReputation = Integer.valueOf(fields[1]);
				break;
			case "Title":
				q.Title = fields[1];
				break;
			case "Icon":
				q.Icon = new ItemStack(Material.valueOf(fields[1].split(";")[0]));
				q.Icon.setAmount(Integer.valueOf(fields[1].split(";")[1]));
				break;
			case "Description":
				q.Description = fields[1];
				break;
			case "Target":
				q.Target = fields[1];
				break;
			case "Short":
				q.Short = fields[1];
				break;
			case "MessageAccept":
				q.MessageAccept = fields[1];
				break;
			case "MessageDecline":
				q.MessageDecline = fields[1];
				break;
			case "MessageRunning":
				q.MessageRunning = fields[1];
				break;
			case "MessageFail":
				q.MessageFail = fields[1];
				break;
			case "MessageSuccess":
				q.MessageSuccess = fields[1];
				break;
			case "StarterItem":
				q.StarterItem = CustomItem.loadCustomItem(ItemID.valueOf(fields[1]));
				break;
			case "Type":
				q.Type = QuestTypes.valueOf(fields[1]);
				break;
			case "Variable":
				q.Variable = Integer.valueOf(fields[1]);
				break;
			case "Item":
				q.Item = CustomItem.loadCustomItem(ItemID.valueOf(fields[1]));
				break;
			case "Destination":
				q.Destination = fields[1];
				break;
			case "Region":
				q.Region = fields[1];
				break;
			case "MobType":
				q.MobType = EntityType.valueOf(fields[1]);
				break;
			case "BlockMaterial":
				q.BlockMaterial = Material.valueOf(fields[1]);
				break;
			case "RewardXP":
				q.RewardXP = Integer.valueOf(fields[1]);
				break;
			case "ReputationGain":
				q.ReputationGain = Integer.valueOf(fields[1]);
				break;
			case "BonusRewardType":
				q.BonusRewardType = fields[1];
				break;
			case "BonusReward":
				q.BonusReward = CustomItem.loadCustomItem(ItemID.valueOf(fields[1]));
				break;
			case "CompletionText":
				q.CompletionText = fields[1];
				break;
			case "DoesFollow":
				q.DoesFollow = Boolean.valueOf(fields[1]);
				break;
			case "FollowID":
				q.FollowID = Integer.valueOf(fields[1]);
				break;
			case "Creator":
				q.Creator = fields[1];
				break;
			case "Created":
				Calendar c = Calendar.getInstance();
				c.set(Integer.valueOf(fields[1].split("-")[0]), Integer.valueOf(fields[1].split("-")[1]), Integer.valueOf(fields[1].split("-")[2]));
				q.Created = c.getTime();
				break;
			case "Active":
				q.Active = Boolean.valueOf(fields[1].substring(0, fields[1].length()-2));
				break;
			default:
				return null;
			}
		}
		return q;
	}

	@Override
	public String toString() {
		return "Quest [ID={" + ID + "}, NpcID={" + NpcID + "}, Required={" + Required + "}, Faction={" + Faction
				+ "}, MinReputation={" + MinReputation + "}, Title={" + Title + "}, Icon={" + Icon.getType() + ";" + Icon.getAmount() + "}, Description={"
				+ Description + "}, Target={" + Target + "}, Short={" + Short + "}, MessageAccept={" + MessageAccept
				+ "}, MessageDecline={" + MessageDecline + "}, MessageRunning={" + MessageRunning + "}, MessageFail={"
				+ MessageFail + "}, MessageSuccess={" + MessageSuccess + "}, StarterItem={" + (StarterItem == null ? "null" : StarterItem.getItemID()) + "}, Type={" + Type
				+ "}, Variable={" + Variable + "}, Item={" + (Item == null ? "null" : Item.getItemID()) + "}, Destination={" + Destination + "}, Region={" + Region
				+ "}, MobType={" + MobType + "}, BlockMaterial={" + BlockMaterial + "}, RewardXP={" + RewardXP
				+ "}, ReputationGain={" + ReputationGain + "}, BonusRewardType={" + BonusRewardType + "}, BonusReward={"
				+ (BonusReward == null ? "null" : BonusReward.getItemID()) + "}, CompletionText={" + CompletionText + "}, DoesFollow={" + DoesFollow + "}, FollowID={"
				+ FollowID + "}, Creator={" + Creator + "}, Created={" + new SimpleDateFormat("YYYY-MM-dd").format(Created) + "}, Active={" + Active + "}]";
	}
	
}
