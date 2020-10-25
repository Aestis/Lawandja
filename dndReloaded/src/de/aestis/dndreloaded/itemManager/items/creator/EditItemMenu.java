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
import de.aestis.dndreloaded.itemManager.items.ItemManager;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Subdevideable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class EditItemMenu extends CustomMenu implements Closeable, Subdevideable, Submenu, Refreshable{

	private CustomItem item;
	private boolean deleteConfirm;
	private boolean simpleItem;
	private boolean registerOnClose;
	
	public EditItemMenu(CustomItem item) {
		this(item, false);
	}
	
	public EditItemMenu(CustomItem item, boolean registerOnClose) {
		super(18);
		this.item = item;
		this.registerOnClose = registerOnClose;
		setTitle("Select Submenu");
		
		simpleItem = item instanceof SimpleItem;
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason reason) {
		if (registerOnClose && reason != CloseReason.CHANGEMENU && item.getItemID().getGroup() != ItemGroup.UNKNOWN) {
			ItemManager.getInstance().registerItem(item);
		}
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();
		
		{
			ItemStack newItem = new ItemStack(Material.GOLDEN_PICKAXE);
			ItemMeta meta = newItem.getItemMeta();
			Damageable spigotItemMeta = (Damageable) meta;
			spigotItemMeta.setDamage(15);
			meta.setDisplayName("Edit Appereance");
			List<String> lore = new ArrayList<>();
			lore.add("Klick to change the Appereance of the item");
			meta.setLore(lore);
			newItem.setItemMeta(meta);
			returnMap.put(0, new DoubleWrapper<>(newItem, null));
		}
		
		{
			ItemStack newItem = new ItemStack(Material.PAPER);
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Edit Lore");
			newItem.setItemMeta(meta);
			returnMap.put(1, new DoubleWrapper<>(newItem, null));
		}
		
		{
			ItemStack newItem = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Edit Enchantments");
			newItem.setItemMeta(meta);
			returnMap.put(2, new DoubleWrapper<>(newItem, null));
		}
		
		if (simpleItem) {
			ItemStack newItem = new ItemStack(Material.DIAMOND);
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Edit Flags");
			newItem.setItemMeta(meta);
			returnMap.put(3, new DoubleWrapper<>(newItem, null));
		}
		
		if (simpleItem) {
			ItemStack newItem = new ItemStack(Material.EMERALD);
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Edit Attributes");
			newItem.setItemMeta(meta);
			returnMap.put(4, new DoubleWrapper<>(newItem, null));
		}
		
		{
			ItemStack newItem = new ItemStack(Material.BRICK);
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Edit Value");
			List<String> lore = new ArrayList<>();
			lore.add(ItemGroup.MONEY.getGroupLore());
			meta.setLore(lore);
			newItem.setItemMeta(meta);
			returnMap.put(5, new DoubleWrapper<>(newItem, null));
		}

		{
			ItemStack newItem = item.getEditorItem();
			returnMap.put(14, new DoubleWrapper<>(newItem, null));
		}
		
		{
			ItemStack newItem = new ItemStack(Material.ANVIL);
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Create Item");
			newItem.setItemMeta(meta);
			returnMap.put(15, new DoubleWrapper<>(newItem, () -> p.getInventory().addItem(item.getSpigotItem())));
		}

		{
			ItemStack newItem = new ItemStack(Material.GREEN_CONCRETE);
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Return to Item List");
			newItem.setItemMeta(meta);
			returnMap.put(16, new DoubleWrapper<>(newItem, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		}
		
		{
			ItemStack newItem;
			if (deleteConfirm) {
				newItem = new ItemStack(Material.GREEN_DYE);
			} else {
				newItem = new ItemStack(Material.RED_CONCRETE);
			}
			ItemMeta meta = newItem.getItemMeta();
			meta.setDisplayName("Delete Item");
			newItem.setItemMeta(meta);
			if (deleteConfirm) {
				returnMap.put(17, new DoubleWrapper<>(newItem, () -> {InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS); ItemManager.getInstance().unregisterItem(item);}));
			} else {
				returnMap.put(17, new DoubleWrapper<>(newItem, () -> deleteConfirm = true));
			}
			
		}
		
		return returnMap;
	}

	@Override
	public CustomMenu getSubmenu(int slot) {
		if (slot == 0) {
			return new AppearanceMenu(item);
		} else if (slot == 1) {
			return new LoreMenu(item);
		} else if (slot == 2) {
			return new EnchantmentMenu(item);
		} else if (slot == 3 && simpleItem) {
			return new ItemFlagMenu((SimpleItem) item);
		} else if (slot == 4 && simpleItem) {
			return new AttributeMenu((SimpleItem) item);
		} else if (slot == 5) {
			return new ValueMenu(item);
		}
		return null;
	}

	@Override
	public boolean hasSubmenu(int slot) {
		if (slot > -1 && slot < 6 && !(slot == 3)) {
			return true;
		} else if (slot == 3 && simpleItem) {
			return true;
		}
		return false;
	}

	@Override
	public double getRefreshTime() {
		return 1.5;
	}

	@Override
	public void onRefresh() {}

}
