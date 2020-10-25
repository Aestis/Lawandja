package de.aestis.dndreloaded.itemManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.potion.PotionEffectType;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.CommandManager.items.ItemEditorCommand;
import de.aestis.dndreloaded.itemManager.enchants.CustomEnchantment;
import de.aestis.dndreloaded.itemManager.enchants.implemet.AttackPercentCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.AttackRawCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.ConsumableCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.DefenceCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.EffectWeaponsCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.ElytraFlightCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.ForceFieldCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.LeapCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.LightningCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.MagnetCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.MoneyCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.NoFallCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.SpeedCore;
import de.aestis.dndreloaded.itemManager.enchants.implemet.TeleportCore;
import de.aestis.dndreloaded.itemManager.events.ArmorListener;
import de.aestis.dndreloaded.itemManager.events.CustomDamageEvent.DamageType;
import de.aestis.dndreloaded.itemManager.events.DispenserArmorListener;
import de.aestis.dndreloaded.itemManager.items.CustomItem;
import de.aestis.dndreloaded.itemManager.items.ItemGroup;
import de.aestis.dndreloaded.itemManager.items.ItemManager;
import de.aestis.dndreloaded.itemManager.items.SimpleItem;

public class ItemLoader{

	public static Main plugin = Main.instance;
	
	@SuppressWarnings("deprecation")
	public static void loadItems() {
		CustomEnchantment ench = new CustomEnchantment("poison", 5, EnchantmentTarget.WEAPON, new EffectWeaponsCore(PotionEffectType.POISON, 1, 3d, 0, 3d));
		CustomEnchantment ench2 = new CustomEnchantment("levitaition", 5, EnchantmentTarget.WEAPON, new EffectWeaponsCore(PotionEffectType.LEVITATION, 1, 3d, 0, 2d));
		CustomEnchantment ench3 = new CustomEnchantment("teleport", 5, EnchantmentTarget.ALL, new TeleportCore());
		CustomEnchantment ench4 = new CustomEnchantment("lightningstrike", 1, EnchantmentTarget.ALL, new LightningCore());
		CustomEnchantment ench7 = new CustomEnchantment("money", 50, EnchantmentTarget.WEAPON, new MoneyCore());
		CustomEnchantment ench10 = new CustomEnchantment("consumeable", 10, EnchantmentTarget.ALL, new ConsumableCore());
		CustomEnchantment ench11 = new CustomEnchantment("elytra", 50, EnchantmentTarget.ALL, new ElytraFlightCore());
		CustomEnchantment ench12 = new CustomEnchantment("noFall", 50, EnchantmentTarget.ALL, new NoFallCore());
		CustomEnchantment ench13 = new CustomEnchantment("leap", 50, EnchantmentTarget.ALL, new LeapCore());
		CustomEnchantment ench14 = new CustomEnchantment("magnet", 50, EnchantmentTarget.ALL, new MagnetCore());
		CustomEnchantment ench15 = new CustomEnchantment("forcefield", 50, EnchantmentTarget.ALL, new ForceFieldCore());
		CustomEnchantment ench16 = new CustomEnchantment("speed", 50, EnchantmentTarget.ALL, new SpeedCore());
		
		for (DamageType type: DamageType.values()) {
			if (type == DamageType.ALL) {
				continue;
			}
			CustomEnchantment e = new CustomEnchantment(type.name().toLowerCase() + "def", 100, EnchantmentTarget.ARMOR, new DefenceCore(type));
			CustomEnchantment.register(e);
			e = new CustomEnchantment(type.name().toLowerCase() + "rawatk", 100, EnchantmentTarget.ALL, new AttackRawCore(type));
			CustomEnchantment.register(e);
			e = new CustomEnchantment(type.name().toLowerCase() + "pctatk", 100, EnchantmentTarget.ALL, new AttackPercentCore(type));
			CustomEnchantment.register(e);
		}
		
		CustomEnchantment.register(ench);
		CustomEnchantment.register(ench2);
		CustomEnchantment.register(ench3);
		CustomEnchantment.register(ench4);
		CustomEnchantment.register(ench7);
		CustomEnchantment.register(ench10);
		CustomEnchantment.register(ench11);
		CustomEnchantment.register(ench12);
		CustomEnchantment.register(ench13);
		CustomEnchantment.register(ench14);
		CustomEnchantment.register(ench15);
		CustomEnchantment.register(ench16);
		
		SimpleItem item = CustomItem.createSimpleItem(Material.DIAMOND_AXE, ItemGroup.TEST, "Poison");
		item.addEnchantment(ench, 4, true);
		ItemManager.getInstance().registerItem(item);
		
		item = CustomItem.createSimpleItem(Material.DIAMOND_AXE, ItemGroup.TEST, "Levitation");
		item.addEnchantment(ench2, 4, true);
		ItemManager.getInstance().registerItem(item);
		
		item = CustomItem.createSimpleItem(Material.STICK, ItemGroup.TEST, "Teleport");
		item.addEnchantment(ench3, 2, true);
		ItemManager.getInstance().registerItem(item);
		
		item = CustomItem.createSimpleItem(Material.STICK, ItemGroup.TEST, "Lightning");
		item.addEnchantment(ench4, 1, true);
		ItemManager.getInstance().registerItem(item);
		
		item = CustomItem.createSimpleItem(Material.DIAMOND_CHESTPLATE, ItemGroup.TEST, "Defence");
		item.addEnchantment(CustomEnchantment.getByCore(new DefenceCore(DamageType.VANILLA)), 40, true);
		ItemManager.getInstance().registerItem(item);
		
		item = CustomItem.createSimpleItem(Material.DIAMOND_SWORD, ItemGroup.TEST, "Money");
		item.addEnchantment(ench7, 13, true);
		ItemManager.getInstance().registerItem(item);
		
		item = CustomItem.createSimpleItem(Material.PAPER, ItemGroup.TEST, "Elytra");
		item.addEnchantment(ench11, 3, true);
		item.addEnchantment(ench12, 1, true);
		item.addEnchantment(ench13, 5, true);
		ItemManager.getInstance().registerItem(item);
		
		Bukkit.getServer().getPluginManager().registerEvents(new DamageHandler(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(ItemManager.getInstance(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new ArmorListener(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new DispenserArmorListener(), plugin);
		
		plugin.getCommand("ItemEditor").setExecutor(new ItemEditorCommand());
	}
	
	public static void unloadItems() {
		CustomEnchantment.unregisterAll();
	}
}
