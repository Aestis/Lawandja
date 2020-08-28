package de.aestis.dndreloaded.Scripting;

import java.util.HashMap;

import de.aestis.dndreloaded.Main;

public class ScriptStorage {

	private static ScriptStorage instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public static ScriptStorage getInstance() {
		if (instance == null) {
			instance = new ScriptStorage();
		}
		return instance;
	}
	
	private HashMap<String, String> scripts = new HashMap<String, String>();
	
	public String getScript(String name) {
		
		if (scripts.containsKey(name))
		{
			return scripts.get(name);
		}
		
		return null;
	}
	
}
