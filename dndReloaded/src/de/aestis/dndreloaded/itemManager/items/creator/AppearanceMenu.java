package de.aestis.dndreloaded.itemManager.items.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemGroup;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CommandModifyable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class AppearanceMenu extends CustomMenu implements Closeable, Subdevideable, Submenu, CommandModifyable, Refreshable{

	private CustomItem item;
	private int currentGroup = 0;
	
	public AppearanceMenu(CustomItem item) {
		super(9);
		this.item = item;
		setTitle("Change Item appereance");
		ItemGroup[] groups = ItemGroup.values();
		for (int i = 0; i < groups.length; i++) {
			if (groups[i] == item.getItemID().getGroup()) {
				currentGroup = i;
			}
		}
	}

	@Override
	public CustomMenu getSubmenu(int slot) {
		if (slot == 3) {
			return new DurabilityMenu(item);
		} else if (slot == 1) {
			return new ChangeMaterialMenu(item);
		}
		return null;
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot == 3 && item.getSpigotItem().getItemMeta() != null && item.getSpigotItem().getItemMeta() instanceof Damageable) {
			return true;
		} else if (slot == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void onClose(Player p, ItemStack[] items, CloseReason reason) {
		item.setGroup(ItemGroup.values()[currentGroup]);
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		
		ItemStack newItem = new ItemStack(Material.PAPER);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName("Change Group:");
		List<String> lores = new ArrayList<>();
		lores.add("Klick to change the Group of the Item");
		lores.add("Currently this item is in Group:");
		lores.add(ItemGroup.values()[currentGroup].getGroupLore());
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(0, new DoubleWrapper<>(newItem, () -> currentGroup = (currentGroup + 1) % ItemGroup.values().length));

		newItem = new ItemStack(Material.IRON_INGOT);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Change Material:");
		lores = new ArrayList<>();
		lores.add("Klick to change the material of the Item");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(1, new DoubleWrapper<>(newItem, null));
		
		newItem = new ItemStack(Material.PAPER);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Change Display Name:");
		lores = new ArrayList<>();
		lores.add("Klick to change the DisplayName of the Item");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(2, new DoubleWrapper<>(newItem, () -> awaitCommand(p)));

		if (item.getSpigotItem().getItemMeta() != null && item.getSpigotItem().getItemMeta() instanceof Damageable) {
			newItem = new ItemStack(Material.DIAMOND_AXE);
			meta = newItem.getItemMeta();
			meta.setDisplayName("Change Damage:");
			lores = new ArrayList<>();
			lores.add("Klick to change the Damage of the Item");
			meta.setLore(lores);
			((Damageable)meta).setDamage(50);
			newItem.setItemMeta(meta);
			returnMap.put(3, new DoubleWrapper<>(newItem, null));
		}
		
		newItem = item.getSpigotItem();
		returnMap.put(6, new DoubleWrapper<>(newItem, null));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Back:");
		lores = new ArrayList<>();
		lores.add("Return to the Item Edit Menu");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(8, new DoubleWrapper<>(newItem, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		
		return returnMap;
	}

	@Override
	public String getCommand() {
		return "display";
	}

	@Override
	public String getCommandHelp() {
		return "Use /" + getCommand() + " to change the Displayname of the Item";
	}

	@Override
	public void processCommand(String[] args) {
		item.setDisplayName(String.join(" ", args));
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}

}
