package de.aestis.dndreloaded.itemManager.items;

import org.bukkit.ChatColor;

public enum ItemGroup {
	
	UNKNOWN("Unbekannt"), // If the item group is unknown, Item may not have a unique ID
	QUEST(ChatColor.RED + "Quest Item"), //for quest items
	JUNK(ChatColor.GRAY + "Müll"), //for all junk items
	LEGENDARY(ChatColor.AQUA + "Legendär"), //for legendary items
	CRAFTED(ChatColor.DARK_AQUA + "Hergestellt"), // for crafted items
	SET(ChatColor.LIGHT_PURPLE + "Set Item"), // for crafted items
	MONEY(ChatColor.GOLD + "Währung"), // Representing all Economy related Items
	TEST(ChatColor.RED + "TEST ITEM"); //for testing Items
	
	
	private final String lore;
	
	/**
	 * @param lore a String that describes the ItemGroup
	 */
	private ItemGroup(String lore) {
		this.lore = lore;
	}
	
	public String getGroupLore() {
		return lore;
	}
	
}