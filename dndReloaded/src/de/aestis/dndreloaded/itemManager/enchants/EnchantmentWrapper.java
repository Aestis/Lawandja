package de.aestis.dndreloaded.itemManager.enchants;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class EnchantmentWrapper extends Enchantment {

	private int maxLevel;
	private EnchantmentTarget target;
	private String name;
	
	public EnchantmentWrapper(String name, int maxLevel, EnchantmentTarget target) {
		super(NamespacedKey.minecraft(name));
		this.name = name;
		this.maxLevel = maxLevel;
		this.target = target;
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return target.includes(arg0.getType());
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return target;
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

}
