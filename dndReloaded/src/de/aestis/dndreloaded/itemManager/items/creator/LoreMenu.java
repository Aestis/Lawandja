package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.creator.SelectColorMenu.ColorWrapper;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.inventoryMenuManager.menus.CommandModifyable;
import oxolotel.utils.DoubleWrapper;

public class LoreMenu extends CustomMenu implements Closeable, Subdevideable, Submenu, CommandModifyable, Refreshable{

	private CustomItem item;
	private ColorWrapper color = new ColorWrapper();
	private ArrayList<String> lore;
	int line;

	public LoreMenu(CustomItem item) {
		super(18);
		this.item = item;
		lore = new ArrayList<String>(item.getLore());
		setTitle("Lore editor");
	}

	@Override
	public CustomMenu getSubmenu(int slot) {
		if (slot == 1) {
			return new SelectColorMenu(color, lore);
		} else if (slot == 4) {
			return new CopyLoreMenu(color, lore);
		}
		return null;
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot == 1 || slot == 4) {
			return true;
		}
		return false;
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason reason) {
		if (reason == CloseReason.RETRUNPREVIOUS) {
			item.setLore(lore);
		}
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();

		ItemStack newItem = new ItemStack(Material.RED_DYE);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName("Change Color:");
		ArrayList<String> lores = new ArrayList<>();
		lores.add("Change Color of the Lore");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(1, new DoubleWrapper<>(newItem, null));
		
		newItem = new ItemStack(Material.PAPER);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Current Lore:");
		lores = new ArrayList<>(lore);
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(0, new DoubleWrapper<>(newItem, null));
		
		newItem = new ItemStack(Material.PAPER);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Copy Lore:");
		newItem.setItemMeta(meta);
		returnMap.put(4, new DoubleWrapper<>(newItem, null));
		
		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Back:");
		lores = new ArrayList<>();
		lores.add("Return to the Item Edit Menu");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(8, new DoubleWrapper<>(newItem, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		
		for (int i = 10; i<17 ; i++) {
			newItem = new ItemStack(Material.PURPLE_CONCRETE);
			meta = newItem.getItemMeta();
			meta.setDisplayName("Change Line:");
			lores = new ArrayList<>();
			lores.add("Change Line " + (i-9) + " of the Lore");
			meta.setLore(lores);
			newItem.setItemMeta(meta);
			int j = i;
			returnMap.put(i, new DoubleWrapper<>(newItem, () -> {line = j-10; awaitCommand(p);}));
		}
		
		return returnMap;
	}

	@Override
	public String getCommand() {
		return "lore";
	}

	@Override
	public String getCommandHelp() {
		return "Use /" + getCommand() + " to change the lore in the selected Line.";
	}

	@Override
	public void processCommand(String[] args) {
		if (lore.size() - 1 < line) {
			for (int i = lore.size(); i < line+1; i++) {
				lore.add("");
			}
		}
		lore.set(line, color.color.toString() + String.join(" ", args));
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}

}
