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
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class EditAttributeMenu extends CustomMenu implements Submenu, Closeable, Refreshable {

	private SimpleItem item;
	private Attribute attribute;
	private int level = 0;
	
	public EditAttributeMenu(SimpleItem item, Attribute attribute) {
		super(9);
		this.item = item;
		this.attribute = attribute;
		setTitle("Change Attribute" + attribute);
	}
	
	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		
		DoubleWrapper<ItemStack, Runnable> item = createReturnItem(p, "Return to Select");
		returnMap.put(8, item);
		
		item = createGuiItem(() -> {}, Material.BLUE_CONCRETE, "Current Amount", "Current value of the Attribute", "" +level);
		returnMap.put(3, item);
		item = createGuiItem(() -> level++, Material.GREEN_CONCRETE, "Increase Setting", "Increase Attribute Setting by 1");
		returnMap.put(4, item);
		item = createGuiItem(() -> level+=10, Material.GREEN_CONCRETE, "Increase Setting", "Increase Attribute Setting by 10");
		returnMap.put(5, item);
		item = createGuiItem(() -> level+=100, Material.GREEN_CONCRETE, "Increase Setting", "Increase Attribute Setting by 100");
		returnMap.put(6, item);
		item = createGuiItem(() -> level--, Material.RED_CONCRETE, "Decrease Setting", "Decrease Attribute Setting by 1");
		returnMap.put(2, item);
		item = createGuiItem(() -> level-=10, Material.RED_CONCRETE, "Decrease Setting", "Decrease Attribute Setting by 10");
		returnMap.put(1, item);
		item = createGuiItem(() -> level-=100, Material.RED_CONCRETE, "Decrease Setting", "Decrease Attribute Setting by 100");
		returnMap.put(0, item);	
		
		return returnMap;
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason reason) {
		if (reason == CloseReason.RETRUNPREVIOUS) {
			item.setAttribute(attribute, level);
		}
	}

}
