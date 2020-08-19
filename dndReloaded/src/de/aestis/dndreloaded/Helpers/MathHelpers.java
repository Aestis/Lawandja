package de.aestis.dndreloaded.Helpers;

import java.util.Random;

public class MathHelpers {

	public static int getRndInt(int min, int max) {
		
		if (min >= max)
		{
			return max;
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static String shortifyNumber(int value) {
		
		if (value < 1000)
		{
			return String.valueOf(value);
		} else if (value >= 1000 && value < 1000000)
		{
			return Math.ceil(value / 1000) + "k";
		} else if (value >= 1000000)
		{
			return Math.ceil(value / 1000) + "m";
		} else
		{
			return null;
		}
	}
}
