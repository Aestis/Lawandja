package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemGroup;
import de.aestis.dndreloaded.itemManager.items.ItemID;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.utils.DoubleWrapper;

public class CreateItem extends CustomMenu implements Subdevideable, Closeable{

	public CreateItem() {
		super(9);
	}

	@Override
	public void onClose(Player p, ItemStack[] arg1, CloseReason reason) {
		if (reason == CloseReason.CHANGEMENU) {
			p.sendMessage("Please go to the Appereance Submenu and change the appereance of the item or it will not be saved");
		}
	}

	@Override
	public CustomMenu getSubmenu(int slot) {
		if (slot == 2) {
			return new EditItemMenu(CustomItem.createSimpleItem(new ItemID(ItemGroup.UNKNOWN, Material.DIRT, "-")), true);
		} else if (slot == 6) {
			return new EditItemMenu(CustomItem.createAbstractItem(new ItemID(ItemGroup.UNKNOWN, Material.DIRT, "-")), true);
		}
		return null;
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot == 2 || slot == 6) {
			return true;
		}
		return false;
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player arg0) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		
		ItemStack newItem = new ItemStack(Material.DIAMOND);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName("Create Simple Item");
		List<String> lore = new ArrayList<>();
		lore.add("Click to create a Simple Item.");
		lore.add("Simple Items may only have a");
		lore.add("fixed level of each Enchantment");
		meta.setLore(lore);
		newItem.setItemMeta(meta);
		returnMap.put(2, new DoubleWrapper<>(newItem, null));
		
		newItem = new ItemStack(Material.EMERALD);
		newItem.setAmount(64);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Create Abstract Item");
		lore = new ArrayList<>();
		lore.add("Click to create a Abstract Item.");
		lore.add("Abstract Items may have a level");
		lore.add("range for each Enchantment");
		meta.setLore(lore);
		newItem.setItemMeta(meta);
		returnMap.put(6, new DoubleWrapper<>(newItem, null));
		
		return returnMap;
	}

}
