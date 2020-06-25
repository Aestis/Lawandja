package de.aestis.dndreloaded.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.Quests.QuestHandler;
import de.aestis.dndreloaded.Util.Misc.GUIItem;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.ShiftClickable;
import oxolotel.utils.DoubleWrapper;

public class QuestMenu extends CustomMenu implements Closeable, ShiftClickable {
	
	private Quest q;
	
	private final Main Plugin = Main.instance;
	
	public QuestMenu(Quest quest) {
		super(27);
		this.q = quest;
		setTitle("§7Quest " + "§6" + q.getTitle());
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player player) {
		
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> rtn = new HashMap<Integer, DoubleWrapper<ItemStack, Runnable>>();
		DoubleWrapper<ItemStack, Runnable> dw;
		GUIItem gui = GUIItem.getInstance();
		
		for (int i = 0; i < 27; i++) {
			if (i < 10) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.AIR, 1, " ", ""), null);
				rtn.put(i, dw);
			}
			if (i == 10) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.PAPER, 1, "§6" + q.getShort(), q.getDescription()), null);
				rtn.put(i, dw);
			}
			if (i == 11) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.AIR, 1, " ", ""), null);
				rtn.put(i, dw);
			}
			if (i == 12) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.COMPASS, 1, "§6Questziel", q.getTarget()), null);
				rtn.put(i, dw);
			}
			if (i == 13) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.AIR, 1, " ", ""), null);
				rtn.put(i, dw);
			}
			if (i == 14) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.FILLED_MAP, 1, "§6Kategorie", q.getType()), null);
				rtn.put(i, dw);
			}
			if (i == 15) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.AIR, 1, " ", ""), null);
				rtn.put(i, dw);
			}
			if (i == 16) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.ACACIA_SIGN, 1, "§6Zugehörigkeit", q.getFaction()), null);
				rtn.put(i, dw);
			}
			if (i == 17) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.AIR, 1, " ", ""), null);
				rtn.put(i, dw);
			}
			if (i == 18) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.LIME_BANNER, 1, "§2Quest annehmen!", "§6Starte die Quest §2" + q.getTitle() + "§6!"), ()-> {
					
					this.onQuestAccept(player, q);
					
					System.out.println("CODE TO DECLINE QUEST (" + q.getID() + ") = " + q.getTitle());
				});
				rtn.put(i, dw);
			}
			if (i > 18 && i < 26) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.AIR, 1, " ", ""), null);
				rtn.put(i, dw);
			}
			if (i == 26) {
				dw = new DoubleWrapper<>(gui.getGUIItem(Material.RED_BANNER, 1, "§cAbbrechen", "§6Ich habs mir anders überlegt!"), ()->{
					
					this.onQuestDecline(player, q);
					
					System.out.println("CODE TO DECLINE QUEST (" + q.getID() + ") = " + q.getTitle());
				});
				rtn.put(i, dw);
			}
		}
				
		return rtn;
	}
	
	
	public void onQuestAccept(Player player, Quest quest) {
		
		QuestHandler qh = Plugin.getQuestHandler();
		
		if (qh.setPlayerQuest(player, quest))
		{
			player.sendMessage(quest.getMessageAccept());
		} else
		{
			player.sendMessage(Plugin.getConfig().getString("Localization.Quests.Inventories.Messages.fulljournal"));
			return;
		}
	}
	
	private void onQuestDecline(Player player, Quest quest) {
		
		player.sendMessage(quest.getMessageDecline());	
	}


	@Override
	public void onClose() {
		
	}
}

