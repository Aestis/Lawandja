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
import oxolotel.inventoryMenuManager.InventoryMenuManager;
import oxolotel.inventoryMenuManager.menus.Closeable;
import oxolotel.inventoryMenuManager.menus.CustomMenu;
import oxolotel.inventoryMenuManager.menus.Refreshable;
import oxolotel.inventoryMenuManager.menus.Submenu;
import oxolotel.utils.DoubleWrapper;

public class DurabilityMenu extends CustomMenu implements Closeable, Submenu, Refreshable {

	private CustomItem item;
	private ItemStack spigotItem;
	private int damage;
	
	public DurabilityMenu(CustomItem item) {
		super(9);
		this.item = item;
		spigotItem = item.getSpigotItem();
		if (spigotItem.getItemMeta() != null && spigotItem.getItemMeta() instanceof Damageable) {
			damage = ((Damageable) spigotItem.getItemMeta()).getDamage();
		} else {
			throw new IllegalArgumentException("Given Item can not be damaged");
		}
		setTitle("Change Durability");
	}

	@Override
	public void onClose(Player arg0, ItemStack[] arg1, CloseReason reason) {
		if (reason == CloseReason.RETRUNPREVIOUS) {
			item.setDamage(damage);
		}
	}

	@Override
	public Map<Integer, DoubleWrapper<ItemStack, Runnable>> getContents(Player p) {
		Map<Integer, DoubleWrapper<ItemStack, Runnable>> returnMap = new HashMap<>();

		ItemStack newItem = new ItemStack(Material.GREEN_CONCRETE);
		ItemMeta meta = newItem.getItemMeta();
		meta.setDisplayName("Aply edit:");
		List<String> lores = new ArrayList<>();
		lores.add("Sets the Damage to the Current Configuration");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(8, new DoubleWrapper<>(newItem, () -> InventoryMenuManager.getInstance().removeOpenMenu(p, CloseReason.RETRUNPREVIOUS)));
		
		newItem = spigotItem;
		Damageable spigotItemMeta = (Damageable) newItem.getItemMeta();
		spigotItemMeta.setDamage(damage);
		newItem.setItemMeta((ItemMeta) spigotItemMeta);
		returnMap.put(4, new DoubleWrapper<>(newItem, null));

		newItem = new ItemStack(Material.RED_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Decrease Damage");
		lores = new ArrayList<>();
		lores.add("Decrease Damage by 10");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(3, new DoubleWrapper<>(newItem, () -> damage = Math.max(0,damage - 10) ));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Increase Damage");
		lores = new ArrayList<>();
		lores.add("Increase Damage by 10");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(5, new DoubleWrapper<>(newItem, () -> damage =  damage + 10 ));

		newItem = new ItemStack(Material.RED_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Decrease Damage");
		lores = new ArrayList<>();
		lores.add("Decrease Damage by 100");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(2, new DoubleWrapper<>(newItem, () -> damage =  Math.max(0,damage - 100) ));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Increase Damage");
		lores = new ArrayList<>();
		lores.add("Increase Damage by 100");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(6, new DoubleWrapper<>(newItem, () -> damage = damage + 100 ));

		newItem = new ItemStack(Material.RED_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Decrease Damage");
		lores = new ArrayList<>();
		lores.add("Decrease Damage by 1000");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(1, new DoubleWrapper<>(newItem, () -> damage =  Math.max(0,damage - 1000) ));

		newItem = new ItemStack(Material.GREEN_CONCRETE);
		meta = newItem.getItemMeta();
		meta.setDisplayName("Increase Damage");
		lores = new ArrayList<>();
		lores.add("Increase Damage by 1000");
		meta.setLore(lores);
		newItem.setItemMeta(meta);
		returnMap.put(7, new DoubleWrapper<>(newItem, () -> damage = damage + 1000 ));

		returnMap.put(0, new DoubleWrapper<ItemStack, Runnable>(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), () -> {}));
		return returnMap;
	}

	@Override
	public double getRefreshTime() {
		return 0.5;
	}

	@Override
	public void onRefresh() {}

}
