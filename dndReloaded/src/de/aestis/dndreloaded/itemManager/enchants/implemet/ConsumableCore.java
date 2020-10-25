package de.aestis.dndreloaded.itemManager.enchants.implemet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.enchants.EnchantmentGroup;
import de.aestis.dndreloaded.itemManager.enchants.effects.ConsumableEffect;
import de.aestis.dndreloaded.itemManager.events.PlayerConsumeItemEvent;
import de.aestis.dndreloaded.itemManager.items.ItemManager;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;

public class ConsumableCore implements ConsumableEffect {

	@Override
	public String getStringRepresentation() {
		return "Verwendungen übrig";
	}

	@Override
	public String getLevelRepresentation(int level) {
		return "" + level;
	}

	@Override
	public boolean isLevelPrefix() {
		return true;
	}

	@Override
	public ChatColor getDisplayColor(int level) {
		return ChatColor.WHITE;
	}

	@Override
	public boolean isInvisible() {
		return false;
	}

	@Override
	public EnchantmentGroup getGroup() {
		return EnchantmentGroup.STATUS;
	}

	@Override
	public void onConsume(PlayerConsumeItemEvent evt, Player interactor, int level, int amountOfActiveItems) {
		SimpleItem consumed = evt.getItem();
		ItemStack spigot = consumed.getSpigotItem();
		if (level == 1) {
			if (spigot.getAmount() > 1) {
				consumed.setAmount(spigot.getAmount()-1);
			} else {
				consumed.setAmount(0);
			}
		} else {
			if (spigot.getAmount() == 1) {
				consumed.changeEnchantmentLevel(CustomEnchantment.getByCore(this), level - 1);
			} else {
				SimpleItem usedItem = (SimpleItem) consumed.clone();
				usedItem.setAmount(1);
				usedItem.changeEnchantmentLevel(CustomEnchantment.getByCore(this), level - 1);
				consumed.setAmount(spigot.getAmount() - 1);

				if (interactor.getInventory().firstEmpty() != -1) {
					interactor.getInventory().addItem(usedItem.getSpigotItem());
				} else {
					ItemManager.getInstance().spawnItem(interactor.getLocation(), usedItem);
				}
			}
		}
	}

}
