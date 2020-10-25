package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.items.SimpleItem;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class AttributeMenu extends CustomMenu implements Closeable, Subdevideable, Submenu, Refreshable {

	private SimpleItem item;
	
	public AttributeMenu(SimpleItem item) {
		super(Attribute.values().length + 1);
		this.item = item;
		setTitle("Change Item Attributes");
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason arg2) {}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		Attribute[] attributes = Attribute.values(); 
		
		returnMap.put(attributes.length, createReturnItem(p, "Return to Item Edit"));
		
		for (int i = 0; i<Attribute.values().length; i++) {
			DoubleWrapper<ItemStack, Runnable> item = createGuiItem(() -> {}, Material.PAPER, "Change " + attributes[i]);
			returnMap.put(i, item);
		}
		return returnMap;
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}

	@Override
	public CustomMenu getSubmenu(int slot) {
		return new EditAttributeMenu(item, Attribute.values()[slot]);
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot >= 0 && slot < Attribute.values().length) {
			return true;
		}
		return false;
	}

}
