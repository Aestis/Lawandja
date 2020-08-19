package de.aestis.dndreloaded.Helpers;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookHelpers {
	
	public static ItemStack createNewBook (String title, String author, List<String> content) {
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		
		meta.setTitle(title);
		meta.setAuthor(author);
		for (String s : content) {
			meta.addPage(s);
		}
		
		book.setItemMeta(meta);		
		return book;		
	}
	
}
