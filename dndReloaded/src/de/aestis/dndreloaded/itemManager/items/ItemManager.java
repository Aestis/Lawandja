package de.aestis.dndreloaded.itemManager.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.itemManager.items.listener.AttackSpeedListener;
import de.aestis.dndreloaded.itemManager.items.listener.ItemDropListener;
import de.aestis.dndreloaded.itemManager.items.listener.ItemPickupListener;
import de.aestis.dndreloaded.itemManager.items.listener.ItemSpawnListener;

public class ItemManager implements Listener{

	static JavaPlugin plugin = Main.instance;
	private static ItemManager instance;
	private Map<ItemID,CustomItem> itemIds = new HashMap<ItemID, CustomItem>();
	private Map<ItemGroup,List<ItemID>> registeredItems = new HashMap<>();

	private ItemManager() {
		CustomItem item = CustomItem.createSimpleItem(new ItemID(ItemGroup.MONEY, Material.BRICK, "Kupfer"), 1);
		registerItem(item);
		item = CustomItem.createSimpleItem(new ItemID(ItemGroup.MONEY,Material.IRON_INGOT, "Silber"), 10);
		registerItem(item);
		item = CustomItem.createSimpleItem(new ItemID(ItemGroup.MONEY,Material.GOLD_INGOT, "Gold"), 100);
		registerItem(item);
		
		if (!Main.isStandaloneProgram) {
			Bukkit.getServer().getPluginManager().registerEvents(new AttackSpeedListener(), plugin);
			Bukkit.getServer().getPluginManager().registerEvents(new ItemSpawnListener(), plugin);
			Bukkit.getServer().getPluginManager().registerEvents(new ItemDropListener(), plugin);
			Bukkit.getServer().getPluginManager().registerEvents(new ItemPickupListener(), plugin);
		}
	}

	/**
	 * registers a CustomItem and stores it.
	 * @param item the item to register
	 * @return the item that had the same ItemID that was saved earlier
	 */
	public CustomItem registerItem(CustomItem item) {
		ItemID itemID = item.getItemID();
		CustomItem oldItem = null;
		ItemGroup group = itemID.getGroup();
		if (itemIds.containsKey(itemID)) {
			oldItem = unregisterItem(itemID);
		}
		if (registeredItems.containsKey(group)) {
			if (!registeredItems.get(group).contains(itemID)) {
				registeredItems.get(group).add(itemID);
			}
		} else {
			List<ItemID> ids = new ArrayList<>();
			ids.add(itemID);
			registeredItems.put(group, ids);
		}
		itemIds.put(itemID, item);
		return oldItem;
	}

	/**
	 * @return all registered ItemIDs mapped to each ItemGroup
	 */
	public Map<ItemGroup,List<ItemID>> getItemRegistrations(){
		return registeredItems;
	}

	/**
	 * @return a list containing all CustomItems registered
	 */
	public List<CustomItem> getRegisteredItems() {
		return new ArrayList<CustomItem>(itemIds.values());
	}

	/**
	 * @param item the item to unregister
	 * @return the unregistered item
	 */
	public CustomItem unregisterItem(CustomItem item) {
		return unregisterItem(item.getItemID());
	}

	/**
	 * @param id the ItemID to unregister
	 * @return the unregistered item
	 */
	public CustomItem unregisterItem(ItemID id) {
		CustomItem item = null;
		ItemGroup group = id.getGroup();
		if (itemIds.containsKey(id)) {
			item = itemIds.remove(id);
			if (registeredItems.containsKey(group)) {
				registeredItems.get(group).remove(id);
			}
		}
		return item;

	}

	/**
	 * @param item the item to check
	 * @return true if the given item is registered
	 */
	public boolean isRegistered(CustomItem item) {
		return isRegistered(item.getItemID());
	}

	/**
	 * @param item the ItemID to check
	 * @return true if the given item is registered
	 */
	public boolean isRegistered(ItemID id) {
		return itemIds.containsKey(id);
	}

	/**
	 * @return the instance of the ItemManager or creates a new one if none is present
	 */
	public static ItemManager getInstance() {
		if (instance == null) {
			instance = new ItemManager();
		}
		return instance;
	}

	/**
	 * @param id the ItemID to get a item from
	 * @return the item matching the id or null if none present
	 */
	public CustomItem getItemByID(ItemID id) {
		CustomItem item = itemIds.get(id);
		return item == null ? null : item.clone();
	}

	/**
	 * @param group the group to get all items from
	 * @return a list of items contained in the ItemGroup
	 */
	public List<CustomItem> getItemsByGroup(ItemGroup group) {
		List<ItemID> ids = registeredItems.get(group);
		List<CustomItem> rtnList = new ArrayList<CustomItem>(ids.size());
		for (ItemID id:ids) {
			rtnList.add(itemIds.get(id).clone());
		}
		return rtnList;
	}

	/**
	 * @param loc the location to spawn the item at
	 * @param item the item to spawn
	 */
	public void spawnItem(Location loc, CustomItem item) {
		World world = loc.getWorld();
		world.dropItemNaturally(loc, item.getSpigotItem());
	}

	/**
	 * @param loc the location to spawn the item at
	 * @param item the item to spawn. if item does not have a ID it will be given a default one
	 */
	public void spawnItem(Location loc, ItemStack item) {
		spawnItem(loc, CustomItem.loadCustomItem(item, true));
	}

	public List<CustomItem> createMoney(int amount) {
		List<CustomItem> items = new ArrayList<CustomItem>(3);
		while (amount > 100) {
			int gold = Math.min(amount / 100, 64);
			SimpleItem item = (SimpleItem) getItemByID(new ItemID(ItemGroup.MONEY, Material.GOLD_INGOT, "Gold")).clone();
			item.setAmount(gold);
			items.add(item);
			amount = amount - (gold * 100);
		}
		int silver = amount/10;
		SimpleItem item = (SimpleItem) getItemByID(new ItemID(ItemGroup.MONEY, Material.IRON_INGOT, "Silber")).clone();
		item.setAmount(silver);
		items.add(item);
		amount = amount - (silver * 10);
		int copper = amount;
		item = (SimpleItem) getItemByID(new ItemID(ItemGroup.MONEY, Material.BRICK, "Kupfer")).clone();
		item.setAmount(copper);
		items.add(item);
		return items;
	}

	/**
	 * @param item the item to parse
	 * @return the money amount represented by the item or 0 if item is not a money item
	 */
	public static int parseMoney(CustomItem item) {
		if (ItemFlag.get(item.getSpigotItem(), ItemFlag.ITEM_ID, new ItemID(ItemGroup.UNKNOWN, Material.AIR, "-")).getGroup() == ItemGroup.MONEY) {
			ItemStack stack = item.getSpigotItem();
			int value = ItemFlag.get(stack, ItemFlag.VALUE, 0);
			return value * stack.getAmount();
		}
		return 0;
	}

	
	
	
}
