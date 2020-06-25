package de.aestis.dndreloaded.Helpers.External;

import de.aestis.dndreloaded.Main;

public class GriefPreventionHelper {

	private static GriefPreventionHelper instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	private final Main Plugin = Main.instance;
	
	public static GriefPreventionHelper getInstance() {
		if (instance == null) {
			instance = new GriefPreventionHelper();
		}
		return instance;
	}
	
	public boolean loadWorldGuardAPI() {
		
		//TODO
		return false;
	}
	
}
