package de.aestis.dndreloaded.Helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorHelper {

	public static ItemStack toMaterial(ChatColor color) {
		ItemStack item;
		ItemMeta meta;
		List<String> lore = new ArrayList<>();
		switch (color) {
		case AQUA:
			item = new ItemStack(Material.LIGHT_BLUE_DYE);
			meta = item.getItemMeta();
			lore.add("AQUA");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case BLACK:
			item = new ItemStack(Material.BLACK_DYE);
			meta = item.getItemMeta();
			lore.add("BLACK");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case BLUE:
			item = new ItemStack(Material.BLUE_DYE);
			meta = item.getItemMeta();
			lore.add("BLUE");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case BOLD:
			item = new ItemStack(Material.BONE_BLOCK);
			meta = item.getItemMeta();
			lore.add("BOLD");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case DARK_AQUA:
			item = new ItemStack(Material.CYAN_DYE);
			meta = item.getItemMeta();
			lore.add("DARK_AQUA");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case DARK_BLUE:
			item = new ItemStack(Material.LAPIS_LAZULI);
			meta = item.getItemMeta();
			lore.add("DARK_BLUE");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case DARK_GRAY:
			item = new ItemStack(Material.GRAY_DYE);
			meta = item.getItemMeta();
			lore.add("DARK_GRAY");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case DARK_GREEN:
			item = new ItemStack(Material.GREEN_DYE);
			meta = item.getItemMeta();
			lore.add("BLACK");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case DARK_PURPLE:
			item = new ItemStack(Material.PURPLE_DYE);
			meta = item.getItemMeta();
			lore.add("DARK_PURPLE");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case DARK_RED:
			item = new ItemStack(Material.RED_NETHER_BRICKS);
			meta = item.getItemMeta();
			lore.add("DARK_RED");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case GOLD:
			item = new ItemStack(Material.ORANGE_DYE);
			meta = item.getItemMeta();
			lore.add("GOLD");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case GRAY:
			item = new ItemStack(Material.LIGHT_GRAY_DYE);
			meta = item.getItemMeta();
			lore.add("GRAY");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case GREEN:
			item = new ItemStack(Material.LIME_DYE);
			meta = item.getItemMeta();
			lore.add("GREEN");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case ITALIC:
			item = new ItemStack(Material.BLAZE_ROD);
			meta = item.getItemMeta();
			lore.add("ITALIC");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case LIGHT_PURPLE:
			item = new ItemStack(Material.PINK_DYE);
			meta = item.getItemMeta();
			lore.add("LIGHT_PURPLE");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case MAGIC:
			item = new ItemStack(Material.ENCHANTED_BOOK);
			meta = item.getItemMeta();
			lore.add("MAGIC");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case RED:
			item = new ItemStack(Material.RED_DYE);
			meta = item.getItemMeta();
			lore.add("RED");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case RESET:
			item = new ItemStack(Material.BONE);
			meta = item.getItemMeta();
			lore.add("RESET");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case STRIKETHROUGH:
			item = new ItemStack(Material.ARROW);
			meta = item.getItemMeta();
			lore.add("STRIKETHROUGH");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case UNDERLINE:
			item = new ItemStack(Material.SMOOTH_STONE_SLAB);
			meta = item.getItemMeta();
			lore.add("UNDERLINE");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case WHITE:
			item = new ItemStack(Material.WHITE_DYE);
			meta = item.getItemMeta();
			lore.add("UNDERLINE");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		case YELLOW:
			item = new ItemStack(Material.YELLOW_DYE);
			meta = item.getItemMeta();
			lore.add("YELLOW");
			meta.setLore(lore);
			item.setItemMeta(meta);
			break;
		default:
			return null;
		}
		meta.setDisplayName(color.name());
		return item;
	}
	
	public ChatColor toChatColor(Material material) {
		switch (material) {
		case LIGHT_BLUE_DYE:
			return ChatColor.AQUA;
		case BLACK_DYE:
			return ChatColor.BLACK;
		case BLUE_DYE:
			return ChatColor.BLUE;
		case BONE_BLOCK:
			return ChatColor.BOLD;
		case CYAN_DYE:
			return ChatColor.DARK_AQUA;
		case LAPIS_LAZULI:
			return ChatColor.DARK_BLUE;
		case GRAY_DYE:
			return ChatColor.DARK_GRAY;
		case GREEN_DYE:
			return ChatColor.DARK_GREEN;
		case PURPLE_DYE:
			return ChatColor.DARK_PURPLE;
		case RED_NETHER_BRICKS:
			return ChatColor.DARK_RED;
		case ORANGE_DYE:
			return ChatColor.GOLD;
		case LIGHT_GRAY_DYE:
			return ChatColor.GRAY;
		case LIME_DYE:
			return ChatColor.GREEN;
		case BLAZE_ROD:
			return ChatColor.ITALIC;
		case PINK_DYE:
			return ChatColor.LIGHT_PURPLE;
		case ENCHANTED_BOOK:
			return ChatColor.MAGIC;
		case RED_DYE:
			return ChatColor.RED;
		case BONE:
			return ChatColor.RESET;
		case ARROW:
			return ChatColor.STRIKETHROUGH;
		case SMOOTH_STONE_SLAB:
			return ChatColor.UNDERLINE;
		case WHITE_DYE:
			return ChatColor.WHITE;
		case YELLOW_DYE:
			return ChatColor.YELLOW;
		default:
			return null;
		}
	}
}
