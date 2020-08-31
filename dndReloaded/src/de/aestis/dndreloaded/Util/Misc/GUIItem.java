package de.aestis.dndreloaded.Util.Misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

public class GUIItem {

	public static ItemStack getGUIItem (Material material, Integer amount, String name, String lore) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		
		if (material != Material.AIR) {
			meta.setDisplayName(name);
			List<String> wrapped = new ArrayList<String>();
			for (String str : ChatPaginator.wordWrap(lore, 30)) {
				wrapped.add(str);
			}
			meta.setLore(wrapped);
			item.setItemMeta(meta);
		}
		
		return item;
	}
}
