package de.aestis.dndreloaded.Helpers;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookHelpers {
	
	private static BookHelpers instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	public static BookHelpers getInstance() {
		if (instance == null) {
			instance = new BookHelpers();
		}
		return instance;
	}
	
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
