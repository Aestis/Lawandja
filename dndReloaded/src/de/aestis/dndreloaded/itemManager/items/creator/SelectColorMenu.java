package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.Helpers.ColorHelper;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class SelectColorMenu extends CustomMenu implements Submenu, Closeable {

	public static class ColorWrapper {
		ChatColor color = ChatColor.LIGHT_PURPLE;	
	}
	
	ColorWrapper wrapper;
	ArrayList<String> lore;
	
	public SelectColorMenu(ColorWrapper wrapper, ArrayList<String> lore) {
		super(27);
		this.wrapper = wrapper;
		this.lore = lore;
		setTitle("Select Lore Color");
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason arg2) {
		if (wrapper != null) {
			for (int i = 0;i< lore.size();i++) {
				lore.set(i, wrapper.color + ChatColor.stripColor(lore.get(i)));
			}
		}
		
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		
		ItemStack newItem = new ItemStack(Material.GREEN_CONCRETE);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName("Aply edit:");
		List<String> lores = new ArrayList<>();
		lores.add("Sets the Color to the Current Configuration");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(26, new DoubleWrapper<>(newItem, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		
		for (int i = 0; i < ChatColor.values().length; i++) {
			newItem = new ItemStack(ColorHelper.toMaterial(ChatColor.values()[i]));
			int j = i;
			returnMap.put(i, new DoubleWrapper<>(newItem, () -> {InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS); wrapper.color = ChatColor.values()[j];}));
		}
		return returnMap;
	}

}
