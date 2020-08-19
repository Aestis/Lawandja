package de.aestis.dndreloaded.Overrides;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Helpers.MathHelpers;

public class BlockBreak {

	private static BlockBreak instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;	
	
	public static BlockBreak getInstance() {
		if (instance == null) {
			instance = new BlockBreak();
			
		}
		return instance;
	}
	
	public void blockBreakCustomItems(BlockBreakEvent event) {
		
		Block block = event.getBlock();
		
		if (block.getType() == Material.OAK_LOG ||
				block.getType() == Material.BIRCH_LOG ||
				block.getType() == Material.SPRUCE_LOG ||
				block.getType() == Material.JUNGLE_LOG ||
				block.getType() == Material.DARK_OAK_LOG ||
				block.getType() == Material.ACACIA_LOG) {
			
			FileConfiguration Config = Plugin.getConfig();
			
			if (Config.getBoolean("Block.Drop.Wood.enabled")) {
				
				//Prevent Item From Dropping
				event.setDropItems(false);
				
				//Initialize Custom Item :)
				int size = MathHelpers.getRndInt(Config.getInt("Block.Drop.Wood.Amount.min"), Config.getInt("Block.Drop.Wood.Amount.max"));
				ItemStack drop = null;
				switch (block.getType()) {
					case OAK_LOG:
						drop = new ItemStack(Material.OAK_PLANKS, size);
						break;
					case BIRCH_LOG:
						drop = new ItemStack(Material.BIRCH_PLANKS, size);
						break;
					case SPRUCE_LOG:
						drop = new ItemStack(Material.SPRUCE_PLANKS, size);
						break;
					case JUNGLE_LOG:
						drop = new ItemStack(Material.JUNGLE_PLANKS, size);
						break;
					case DARK_OAK_LOG:
						drop = new ItemStack(Material.DARK_OAK_PLANKS, size);
						break;
					case ACACIA_LOG:
						drop = new ItemStack(Material.ACACIA_PLANKS, size);
						break;
					default:
						drop = null;
						break;
				}
				
				//Drop Custom Item
				if (drop != null) {
					block.getWorld().dropItemNaturally(block.getLocation(), drop);
				}
			}
		}
	}
	
	
	public boolean blockBreakCustomDurability(BlockBreakEvent event) {
		
		FileConfiguration Config = Plugin.getConfig();
		
		if (Config.getBoolean("Items.Tools.Durability.enabled")) {
			ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
			ItemMeta meta = tool.getItemMeta();
			
			//Get Tool Durability
			if (meta instanceof Damageable) {
				
				Integer dmg = ((Damageable) meta).getDamage();
				Integer max = (int) tool.getType().getMaxDurability();
				Integer multi = Config.getInt("Items.Tools.Durability.multiplier");
				
				//Set Tool Durability Or Cancel
				if (max - dmg > multi * 2) {
					
					((Damageable) meta).setDamage(dmg + (1 * multi));
					tool.setItemMeta(meta);
					return true;
				} else {
					
					((Damageable) meta).setDamage(max - 5);
					tool.setItemMeta(meta);
					event.getPlayer().sendMessage(Config.getString("Items.Tools.Durability.Messages.cancelled"));
					event.setCancelled(true);
					return false;
				}				
			}
		}	
		return true;
	}
	
	public void blockBreakToolSharpness(BlockBreakEvent event) {
		
		FileConfiguration Config = Plugin.getConfig();
		
		if (Config.getBoolean("Items.Tools.Sharpness.enabled")) {
			
			ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
			Integer dice = MathHelpers.getRndInt(1, 100);
			
			if (tool.getType() == Material.STONE_AXE ||
				tool.getType() == Material.IRON_AXE ||
				tool.getType() == Material.STONE_SHOVEL ||
				tool.getType() == Material.IRON_SHOVEL) {
				
				if (dice <= Config.getInt("Items.Tools.Sharpness.chance")) {
					//ItemMeta meta = tool.getItemMeta();
					
					
				}
			}
		}
	}

}
