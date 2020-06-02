package de.aestis.dndreloaded.Helpers;

import java.util.Random;

public class MathHelpers {
	
	private static MathHelpers instance;
	
	/*
	 * Setting up the instance
	 * DO NOT CHANGE ANYTHING!
	 * */
	
	public static MathHelpers getInstance() {
		if (instance == null) {
			instance = new MathHelpers();
		}
		return instance;
	}

	public int getRndInt(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("Maximum value must be greater than minimum!");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
