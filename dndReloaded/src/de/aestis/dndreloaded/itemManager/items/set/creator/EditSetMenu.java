package de.aestis.dndreloaded.itemManager.items.set.creator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lmax.disruptor.SleepingWaitStrategy;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemID;
import de.aestis.dndreloaded.itemManager.items.set.ItemSet;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CommandModifyable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.utils.DoubleWrapper;

public class EditSetMenu extends CustomMenu implements Closeable, Subdevideable, Refreshable, CommandModifyable {

	private ItemSet set;
	
	public EditSetMenu(ItemSet itemSet) {
		super(18);
		this.set = itemSet;
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> rtnMap = new HashMap<>();

		rtnMap.put(8, createReturnItem(p, "Return to Set List"));
		rtnMap.put(0, createGuiItem(null, Material.ENCHANTED_BOOK, "Change Enchantments"));
		rtnMap.put(1, createGuiItem(null, Material.IRON_SWORD, "Change Item Member"));
		rtnMap.put(3, createGuiItem(() -> awaitCommand(p), Material.PAPER, "Change Name"));
		
		List<ItemID> items = set.getMembers();
		
		for (int i = 0; i<items.size() ;i++) {
			rtnMap.put(9+i, createGuiItem(null, CustomItem.loadCustomItem(items.get(i)).getEditorItem()));
		}
		
		return rtnMap;
	}

	@Override
	public double getRefreshTime() {
		return 0;
	}

	@Override
	public void onRefresh() {}

	@Override
	public CustomMenu getSubmenu(int slot) {
		if (slot == 0) {
			return new SelectItemAmount(set);
		} else if (slot == 1) {
			return new ItemSelector(set);
		}
		return null;
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot == 0 || slot == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason arg2) {}

	@Override
	public String getCommand() {
		return "DisplayName";
	}

	@Override
	public String getCommandHelp() {
		return "Type /" + getCommand() + " <Name> to change the name of the set";
	}

	@Override
	public void processCommand(String[] args) {
		set.setName(String.join(" ", args));
	}

}
