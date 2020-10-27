package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.items.ItemSet;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.utils.DoubleWrapper;

public class EditSetMenu extends CustomMenu {

	private ItemSet set;
	
	public EditSetMenu(ItemSet itemSet) {
		super(9);
		this.set = itemSet;
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
