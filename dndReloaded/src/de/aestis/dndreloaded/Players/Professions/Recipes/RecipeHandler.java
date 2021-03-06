package de.aestis.dndreloaded.Players.Professions.Recipes;

import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import de.aestis.dndreloaded.Main;
import de.aestis.dndreloaded.Players.Professions.Profession;

public class RecipeHandler {

	private static RecipeHandler instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public static RecipeHandler getInstance() {
		if (instance == null) {
			instance = new RecipeHandler();
		}
		return instance;
	}
	
	public void loadCustomRecipes() {
		
		//TODO
		
		Set<String> professions = Plugin.getConfig().getConfigurationSection("Profession").getKeys(true);
		
		for (String p : professions) {
			System.out.println("PROFESSION FOUND: " + p);
		}
		
		//String[] professions = "Woodcutter", "Stonecutter", "Stonecutter", "Herbalist", "Inscriber", "Alchemist", "Farmer", "Tanner";
		
		/*for (String p : professions)
		{
			
			Plugin.getLogger().info("Trying to load Recipes for Profession '" + p.name() + "'...");
			
			if (!Plugin.getConfig().isSet("Profession." + p.name().toLowerCase() + ".Recipes"))
			{
				Plugin.getLogger().warning("Recipes for Profession '" + p.name() + "' not initialized!");
				continue;
			}
			
			@SuppressWarnings("unchecked")
			List<String> recipes = (List<String>) Plugin.getConfig().getList("Profession." + p.name().toLowerCase() + ".Recipes");
			
			if (recipes.size() == 0 || recipes == null)
			{
				Plugin.getLogger().fine(recipes.size() + "Recipes for Profession '" + p.name() + "' initialized!");
			}
		}*/
		
	}
	
	public List<CustomRecipe> getRecipesByProfession(Profession profession) {
		
		//TODO
		return null;
	}
	
	public List<CustomRecipe> getRecipesByCrafting(ItemStack ... slot) {
		
		//TODO
		return null;
	}
	
	public CustomRecipe getExactRecipeByCrafting(CraftingInventory inv) {
		
		//TODO
		return null;
	}
}
