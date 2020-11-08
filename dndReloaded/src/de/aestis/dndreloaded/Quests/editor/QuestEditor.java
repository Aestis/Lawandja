package de.aestis.dndreloaded.Quests.editor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.Quests.QuestTypes;
import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemGroup;
import de.aestis.dndreloaded.itemManager.items.ItemID;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;

@SuppressWarnings("serial")
public class QuestEditor extends JFrame {
	private JLabel lNPCID = new JLabel();
	private JLabel lReqiredQuest = new JLabel();
	private JLabel lFaction = new JLabel();
	private JLabel lMinReputation = new JLabel();
	private JLabel lQuestTitle = new JLabel();
	private JLabel lQuestIcon = new JLabel();
	private JLabel lQuestDescription = new JLabel();
	private JLabel lQuestDescriptionShort = new JLabel();
	private JLabel lQuestGoalMessage = new JLabel();
	private JLabel lQuestAcceptMessage = new JLabel();
	private JTextField npcId = new JTextField();
	private JTextField reqiredQuest = new JTextField();
	private JTextField minRep = new JTextField();
	private JTextField qTitle = new JTextField();
	private JLabel lQuestDeclineMessage = new JLabel();
	private JLabel lQuestrunningMessage1 = new JLabel();
	private JLabel lQuestfailMessage1 = new JLabel();
	private JLabel lQuestsuccesssMessage1 = new JLabel();
	private JLabel lStarteritem = new JLabel();
	private JLabel lStarterItemAmount = new JLabel();
	private JLabel lQuestType = new JLabel();
	private JLabel lVariable = new JLabel();
	private JLabel lQuestitem = new JLabel();
	private JLabel lQuestitemAmount = new JLabel();
	private JLabel lDestination = new JLabel();
	private JLabel lMobtype = new JLabel();
	private JLabel lBlockMaterial = new JLabel();
	private JLabel lRewardXP = new JLabel();
	private JLabel lRewardReputation = new JLabel();
	private JLabel lBonusRewardType = new JLabel();
	private JLabel lBonusReward = new JLabel();
	private JComboBox<String> faction = new JComboBox<String>();
	private DefaultComboBoxModel<String> factionModel = new DefaultComboBoxModel<String>();
	private JTextArea qDescription = new JTextArea("");
	private JScrollPane qDescriptionScrollPane = new JScrollPane(qDescription);
	private JTextArea qDescriptionShort = new JTextArea("");
	private JScrollPane qDescriptionShortScrollPane = new JScrollPane(qDescriptionShort);
	private JTextArea qGoal = new JTextArea("");
	private JScrollPane qGoalScrollPane = new JScrollPane(qGoal);
	private JTextField qAccept = new JTextField();
	private JTextField qDecline = new JTextField();
	private JTextField qRunning = new JTextField();
	private JTextField qFail = new JTextField();
	private JTextArea qSuccess = new JTextArea("");
	private JScrollPane qSuccessScrollPane = new JScrollPane(qSuccess);
	private JComboBox<String> startItem = new JComboBox<String>();
	private DefaultComboBoxModel<String> startItemModel = new DefaultComboBoxModel<String>();
	private JComboBox<String> qType = new JComboBox<String>();
	private DefaultComboBoxModel<String> qTypeModel = new DefaultComboBoxModel<String>();
	private JComboBox<String> mobType = new JComboBox<String>();
	private DefaultComboBoxModel<String> mobTypeModel = new DefaultComboBoxModel<String>();
	private JComboBox<String> bonusRewardType = new JComboBox<String>();
	private DefaultComboBoxModel<String> bonusRewardTypeModel = new DefaultComboBoxModel<String>();
	private JTextField startItemAmount = new JTextField();
	private JTextField qVariable = new JTextField();
	private JTextField qItem = new JTextField();
	private JTextField qItemAmount = new JTextField();
	private JTextField qDestination = new JTextField();
	private JTextField rewardXp = new JTextField();
	private JTextField rewardRep = new JTextField();
	private JTextField bonusReward = new JTextField();
	private JButton bCheck = new JButton();
	private JLabel lCheckInputs = new JLabel();
	private JTextField output = new JTextField();
	private JComboBox<String> blockMaterial = new JComboBox<String>();
	private DefaultComboBoxModel<String> blockMaterialModel = new DefaultComboBoxModel<String>();
	private JComboBox<String> qIcon = new JComboBox<String>();
	private DefaultComboBoxModel<String> qIconModel = new DefaultComboBoxModel<String>();
	private Quest q;
	
	private QuestEditor() { 
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		int frameWidth = 1087; 
		int frameHeight = 897;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		setTitle("QuestEditor2");
		setResizable(false);
		Container cp = getContentPane();
		cp.setLayout(null);

		lNPCID.setBounds(25, 25, 110, 20);
		lNPCID.setText("NPC ID");
		cp.add(lNPCID);
		lReqiredQuest.setBounds(25, 65, 110, 20);
		lReqiredQuest.setText("Reqired Quest");
		cp.add(lReqiredQuest);
		lFaction.setBounds(25, 105, 110, 20);
		lFaction.setText("Faction");
		cp.add(lFaction);
		lMinReputation.setBounds(25, 145, 110, 20);
		lMinReputation.setText("Min Reputation");
		cp.add(lMinReputation);
		lQuestTitle.setBounds(25, 185, 110, 20);
		lQuestTitle.setText("Quest Title");
		cp.add(lQuestTitle);
		lQuestIcon.setBounds(25, 225, 110, 20);
		lQuestIcon.setText("Quest Icon");
		cp.add(lQuestIcon);
		lQuestDescription.setBounds(25, 265, 110, 20);
		lQuestDescription.setText("Quest Description");
		cp.add(lQuestDescription);
		lQuestDescriptionShort.setBounds(25, 385, 140, 20);
		lQuestDescriptionShort.setText("Quest Description Short");
		cp.add(lQuestDescriptionShort);
		lQuestGoalMessage.setBounds(25, 465, 121, 20);
		lQuestGoalMessage.setText("Quest Goal Message");
		cp.add(lQuestGoalMessage);
		lQuestAcceptMessage.setBounds(25, 585, 136, 20);
		lQuestAcceptMessage.setText("Quest Accept Message");
		cp.add(lQuestAcceptMessage);
		npcId.setBounds(180, 25, 310, 20);
		cp.add(npcId);
		reqiredQuest.setBounds(180, 65, 310, 20);
		cp.add(reqiredQuest);
		minRep.setBounds(180, 145, 310, 20);
		cp.add(minRep);
		qTitle.setBounds(180, 185, 310, 20);
		cp.add(qTitle);
		lQuestDeclineMessage.setBounds(25, 625, 138, 20);
		lQuestDeclineMessage.setText("Quest Decline Message");
		cp.add(lQuestDeclineMessage);
		lQuestrunningMessage1.setBounds(25, 665, 140, 20);
		lQuestrunningMessage1.setText("Quest running Message");
		cp.add(lQuestrunningMessage1);
		lQuestfailMessage1.setBounds(25, 785, 114, 20);
		lQuestfailMessage1.setText("Quest fail Message");
		cp.add(lQuestfailMessage1);
		lQuestsuccesssMessage1.setBounds(25, 705, 153, 20);
		lQuestsuccesssMessage1.setText("Quest successs Message");
		cp.add(lQuestsuccesssMessage1);
		lStarteritem.setBounds(535, 25, 110, 20);
		lStarteritem.setText("Starter item");
		cp.add(lStarteritem);
		lStarterItemAmount.setBounds(535, 65, 116, 20);
		lStarterItemAmount.setText("StarterItem Amount");
		cp.add(lStarterItemAmount);
		lQuestType.setBounds(535, 105, 110, 20);
		lQuestType.setText("Quest Type");
		cp.add(lQuestType);
		lVariable.setBounds(535, 145, 110, 20);
		lVariable.setText("Variable");
		cp.add(lVariable);
		lQuestitem.setBounds(535, 185, 110, 20);
		lQuestitem.setText("Quest item");
		cp.add(lQuestitem);
		lQuestitemAmount.setBounds(535, 225, 110, 20);
		lQuestitemAmount.setText("Questitem Amount");
		cp.add(lQuestitemAmount);
		lDestination.setBounds(535, 265, 110, 20);
		lDestination.setText("Destination");
		cp.add(lDestination);
		lMobtype.setBounds(535, 305, 110, 20);
		lMobtype.setText("Mobtype");
		cp.add(lMobtype);
		lBlockMaterial.setBounds(535, 345, 110, 20);
		lBlockMaterial.setText("Block Material");
		cp.add(lBlockMaterial);
		lRewardXP.setBounds(535, 385, 110, 20);
		lRewardXP.setText("Reward XP");
		cp.add(lRewardXP);
		lRewardReputation.setBounds(535, 425, 112, 20);
		lRewardReputation.setText("Reward Reputation");
		cp.add(lRewardReputation);
		lBonusRewardType.setBounds(535, 465, 117, 20);
		lBonusRewardType.setText("Bonus Reward Type");
		cp.add(lBonusRewardType);
		lBonusReward.setBounds(535, 505, 110, 20);
		lBonusReward.setText("Bonus Reward");
		cp.add(lBonusReward);

		faction.setModel(factionModel);
		faction.setBounds(180, 104, 310, 20);
		cp.add(faction);
		qDescriptionScrollPane.setBounds(180, 265, 310, 100);
		cp.add(qDescriptionScrollPane);
		qDescriptionShortScrollPane.setBounds(180, 385, 310, 60);
		cp.add(qDescriptionShortScrollPane);
		qGoalScrollPane.setBounds(180, 465, 310, 100);
		cp.add(qGoalScrollPane);
		qAccept.setBounds(180, 585, 310, 20);
		cp.add(qAccept);
		qDecline.setBounds(180, 625, 310, 20);
		cp.add(qDecline);
		qRunning.setBounds(180, 665, 310, 20);
		cp.add(qRunning);
		qFail.setBounds(180, 785, 310, 20);
		cp.add(qFail);
		qSuccessScrollPane.setBounds(180, 705, 310, 60);
		cp.add(qSuccessScrollPane);
		startItem.setModel(startItemModel);
		startItem.setBounds(690, 25, 310, 20);
		cp.add(startItem);
		qType.setBounds(690, 105, 310, 20);
		qType.setModel(qTypeModel);
		cp.add(qType);
		mobType.setModel(mobTypeModel);
		mobType.setBounds(690, 305, 310, 20);
		cp.add(mobType);
		bonusRewardType.setModel(bonusRewardTypeModel);
		bonusRewardType.setBounds(690, 465, 310, 20);
		cp.add(bonusRewardType);
		startItemAmount.setBounds(690, 65, 310, 20);
		cp.add(startItemAmount);
		qVariable.setBounds(690, 145, 310, 20);
		cp.add(qVariable);
		qItem.setBounds(690, 185, 310, 20);
		cp.add(qItem);
		qItemAmount.setBounds(690, 225, 310, 20);
		cp.add(qItemAmount);
		qDestination.setBounds(690, 265, 310, 20);
		cp.add(qDestination);
		rewardXp.setBounds(690, 385, 310, 20);
		cp.add(rewardXp);
		rewardRep.setBounds(690, 425, 310, 20);
		cp.add(rewardRep);
		bonusReward.setBounds(690, 505, 310, 20);
		cp.add(bonusReward);
		bCheck.setBounds(690, 745, 310, 25);
		bCheck.setText("Check");
		bCheck.setMargin(new Insets(2, 2, 2, 2));
		bCheck.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent evt) { 
				bCheck_ActionPerformed(evt);
			}
		});
		cp.add(bCheck);
		lCheckInputs.setBounds(535, 745, 110, 20);
		lCheckInputs.setText("Check Inputs");
		cp.add(lCheckInputs);
		output.setBounds(535, 785, 465, 20);
		output.setEditable(false);
		cp.add(output);
		blockMaterial.setModel(blockMaterialModel);
		blockMaterial.setBounds(690, 345, 310, 20);
		cp.add(blockMaterial);
		qIcon.setModel(qIconModel);
		qIcon.setBounds(180, 225, 310, 20);
		cp.add(qIcon);

		addOptionsToDropDown();

		setVisible(true);
	}
	
	public QuestEditor(Quest q) {
		this(q.getNpcID(), q.getTitle(), q.getDescription(), q.getShort(), q.getTarget(), q.getMessageAccept(), q.getMessageDecline(), q.getMessageRunning(), q.getMessageSuccess(), q.getMessageFail(),
				q.getDestination(), q.getFaction(), q.getIcon() == null ? null : q.getIcon().getType(), q.getStarterItem() == null ? null : q.getStarterItem().getItemID().getMaterial(), q.getBlockMaterial(),
				q.getMobType(), q.getType(), q.getRequired(), q.getMinReputation(), q.getStarterItem() == null ? 1 : q.getStarterItem().getSpigotItem().getAmount(), q.getReputationGain(), q.getRewardXP());
		this.q = q;
	}

	private QuestEditor (String npcID, String questTitle, String questDescription, String questShort, String questGoal, String questAccept, String questDecline, String questRunning, String questSuccess, String questFail,
			String destination, String faction, Material icon, Material starterItem, Material blockMaterial, EntityType mobType, QuestTypes questType, int reqQuest, int minRep, int startItemAmount,	int rewardRep, int rewardXp) {
		this();
		this.npcId.setText(npcID);
		this.qTitle.setText(questTitle);
		this.qDescription.setText(questDescription);
		this.qDescriptionShort.setText(questShort);
		this.qGoal.setText(questGoal);
		this.qAccept.setText(questAccept);
		this.qDecline.setText(questDecline);
		this.qRunning.setText(questRunning);
		this.qSuccess.setText(questSuccess);
		this.qFail.setText(questFail);
		this.qDestination.setText(destination);
		
		//TODO add Faction Enum to traverse over
		String[] factions = new String[0];
		for (int i = 0; i< factions.length; i++) {
			if (factions[i].equals(faction)) {
				this.faction.setSelectedIndex(i);
			}
		}
		this.qIcon.setSelectedIndex(Arrays.asList(Material.values()).indexOf(icon));
		this.startItem.setSelectedIndex(Arrays.asList(Material.values()).indexOf(starterItem));
		this.blockMaterial.setSelectedIndex(Arrays.asList(Material.values()).indexOf(blockMaterial));
		
		this.mobType.setSelectedIndex(Arrays.asList(EntityType.values()).indexOf(mobType));
		this.qType.setSelectedIndex(Arrays.asList(QuestTypes.values()).indexOf(questType));
		
		this.reqiredQuest.setText("" + reqQuest);
		this.minRep.setText("" + minRep);
		this.startItemAmount.setText("" + startItemAmount);
		this.rewardRep.setText("" + rewardRep);
		this.rewardXp.setText("" + rewardXp);
		
	}
	
	private void addOptionsToDropDown() {
		//TODO add Faction Enum to traverse over
		for (String faction:new String[0]) {
			this.faction.addItem(faction);
		}
		for (Material m:Material.values()) {
			this.startItem.addItem(m.name());
			this.qIcon.addItem(m.name());
			this.blockMaterial.addItem(m.name());
		}
		for (EntityType t: EntityType.values()) {
			this.mobType.addItem(t.name());
		}
		for (QuestTypes q: QuestTypes.values()) {
			this.qType.addItem(q.name());
		}
		//TODO add reward type Enum to traverse over
		for (String reward: new String[0]) {
			this.bonusRewardType.addItem(reward);
		}
	}

	public void bCheck_ActionPerformed(ActionEvent evt) {
		String npcID = npcId.getText();
		String questTitle = qTitle.getText();
		String questDescription = qDescription.getText();
		String questShort = qDescriptionShort.getText();
		String questGoal = qGoal.getText();
		String questAccept = qAccept.getText();
		String questDecline = qDecline.getText();
		String questRunning = qRunning.getText();
		String questSuccess = qSuccess.getText();
		String questFail = qFail.getText();
		String destination = qDestination.getText();
		String faction = this.faction.getItemAt(this.faction.getSelectedIndex());
		Material icon = null;
		try {
			icon = Material.valueOf(this.qIcon.getItemAt(this.qIcon.getSelectedIndex()));
		} catch (NullPointerException e) {}
		Material starterItem = null;
		try {
			starterItem = Material.valueOf(this.startItem.getItemAt(this.startItem.getSelectedIndex()));
		} catch (NullPointerException e) {}
		Material blockMaterial = null;
		try {
			blockMaterial = Material.valueOf(this.blockMaterial.getItemAt(this.blockMaterial.getSelectedIndex()));
		} catch (NullPointerException e) {}
		
		EntityType mobType = null;
		try {
			mobType = EntityType.valueOf(this.mobType.getItemAt(this.mobType.getSelectedIndex()));
		} catch (NullPointerException e) {}
		
		QuestTypes questType = QuestTypes.valueOf(qType.getItemAt(qType.getSelectedIndex()));
		int reqQuest = 0;
		int minRep = 0;
		int startItemAmount = 0;
		int rewardRep = 0;
		int rewardXp = 0;
		
		try {
			reqQuest = Integer.valueOf(this.reqiredQuest.getText());
		} catch (Exception e) {
			output.setText("Required Quest is Empty or not a number!");
			return;
		}
		
		try {
			minRep = Integer.valueOf(this.minRep.getText());
		} catch (Exception e) {
			output.setText("Min Reputation is Empty or not a number!");
			return;
		}
		
		try {
			startItemAmount = Integer.valueOf(this.startItemAmount.getText());
		} catch (Exception e) {
			output.setText("StarterItem Amount is Empty or not a number!");
			return;
		}
		
		try {
			rewardRep = Integer.valueOf(this.rewardRep.getText());
		} catch (Exception e) {
			output.setText("Reward Reputation is Empty or not a number!");
			return;
		}
		
		try {
			rewardXp = Integer.valueOf(this.rewardXp.getText());
		} catch (Exception e) {
			output.setText("Reward XP is Empty or not a number!");
			return;
		}
		
		output.setText("Updated Quest Successfully!");
		
		q.setNpcID(npcID);
		q.setRequired(reqQuest);
		q.setFaction(faction);
		q.setMinReputation(minRep);
		q.setTitle(questTitle);
		q.setIcon(icon == null ? null : new ItemStack(icon));
		q.setDescription(questDescription);
		q.setTarget(questGoal);
		q.setShort(questShort);
		q.setMessageAccept(questAccept);
		q.setMessageDecline(questDecline);
		q.setMessageRunning(questRunning);
		q.setMessageFail(questFail);
		q.setMessageSuccess(questSuccess);
		if (starterItem != null) {
			q.setStarterItem(CustomItem.loadCustomItem(new ItemID(ItemGroup.UNKNOWN, starterItem, "-")));
			if (q.getStarterItem() instanceof SimpleItem) {
				((SimpleItem) q.getStarterItem()).setAmount(startItemAmount);
			}
		}
		q.setType(questType);
//		q.setVariable();
//		q.setItem();
		q.setDestination(destination);
//		q.setRegion();
		q.setMobType(mobType);
		q.setBlockMaterial(blockMaterial);
		q.setRewardXP(rewardXp);
		q.setReputationGain(rewardRep);
//		q.setBonusRewardType();
//		q.setBonusReward();
//		q.setCompletionText();
//		q.setDoesFollow();
//		q.setFollowID();
//		q.setCreator();
//		q.setCreated();
//		q.setActive();
	}
}
