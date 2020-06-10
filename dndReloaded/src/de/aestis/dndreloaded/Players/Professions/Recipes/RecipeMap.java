package de.aestis.dndreloaded.Players.Professions.Recipes;

import java.util.HashMap;

public class RecipeMap {

	private HashMap<String, HashMap<Integer, CustomRecipe>> map;
	
	public RecipeMap() {
    	
        map = new HashMap<String, HashMap<Integer, CustomRecipe>>();
    }
	
	public void insertRecipe(String profession, Integer recipeID, CustomRecipe recipe){
    	
        if(map.get(profession) == null) map.put(profession, new HashMap<Integer, CustomRecipe>());
        map.get(profession).put(recipeID, recipe);
    }
}
