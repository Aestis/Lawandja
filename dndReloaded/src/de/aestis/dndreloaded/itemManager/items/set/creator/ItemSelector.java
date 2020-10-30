package de.aestis.dndreloaded.itemManager.items.set.creator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemManager;
import de.aestis.dndreloaded.itemManager.items.set.ItemSet;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Pageable;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class ItemSelector extends CustomMenu implements Closeable, Submenu,Pageable, Refreshable{

	private List<CustomItem> items;
	private ItemSet set;
	
	public ItemSelector(ItemSet set) {
		super(54);
		items = ItemManager.getInstance().getRegisteredItems();
		items.sort((a,b) -> a.compareTo(b));
		this.set = set;
		setTitle("Item List");
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason arg2) {}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		
		for (int i = 0; i<items.size(); i++) {
			ItemStack item = items.get(i).getEditorItem();
			int j = i;
			Runnable run = () -> {
				CustomItem customItem = items.get(j);
				Bukkit.broadcastMessage("Is Member: " + set.isMember(customItem));
				if (set.isMember(customItem)) {
					set.removeMember(customItem.getItemID());
				} else {
					set.addMember(customItem.getItemID());
					Bukkit.broadcastMessage("Added member");
				}
				InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS);
			};
			
			returnMap.put(i, createGuiItem(run, item));
		}
		
		return returnMap;
	}

	@Override
	public double getRefreshTime() {
		return 1.5;
	}

	@Override
	public void onRefresh() {}

}