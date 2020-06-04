package de.aestis.dndreloaded.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.Quests.Quest;
import de.aestis.dndreloaded.Util.Misc.GUIItem;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.ShiftClickable;
import oxolotel.inventoryMenuManager.menus.SlotCondition;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.utils.DoubleWrapper;

public class QuestSelectorMenu extends CustomMenu implements Closeable, Subdevideable, ShiftClickable, SlotCondition {
	
	private List<HashMap<Integer, Quest>> q;
	private List<Quest> list = new ArrayList<Quest>();
	
	public QuestSelectorMenu(List<HashMap<Integer, Quest>> quests) {
		super(quests.size());
		this.q = quests;
		setTitle("§6Quests auswählen");
		
		for (HashMap<Integer, Quest> map : quests) {
			for (Quest q : map.values()) {
				list.add(q);
			}
		}
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player arg0) {

		Map<Integer, DoubleWrapper<ItemStack, Runnable>> rtn = new HashMap<Integer, DoubleWrapper<ItemStack, Runnable>>();
		DoubleWrapper<ItemStack, Runnable> dw;
		GUIItem gui = GUIItem.getInstance();
		
		for (int i = 0; i < list.size(); i++) {
			dw = new DoubleWrapper<>(gui.getGUIItem(list.get(i).getIcon().getType(), 1, "§l+ §6" + list.get(i).getTitle(), "§o§7" + list.get(i).getShort()), null);
			rtn.put(i, dw);
		}
		
		return rtn;
	}

	@Override
	public CustomMenu getSubmenu(int slot) {
		
		return new QuestMenu(list.get(slot));
	}

	@Override
	public boolean hasSubmenu(int slot) {

		if (slot >= 0 && slot < q.size()) return true;		
		return false;
	}

	@Override
	public void onClose() {
		
	}

	@Override
	public boolean isClickAllowed(Player arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
