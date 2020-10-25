package de.aestis.dndreloaded.Helpers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.aestis.dndreloaded.Main;

public class Temporaleffect {

	private static Plugin plugin = Main.instance;
	private Runnable activate;
	private Runnable deactivate;
	private double timeTilDeactivation;
	private boolean isRunning;

	public Temporaleffect(Runnable activate, Runnable deactivate, double timeTilDeactivation) {
		this.activate = activate;
		this.deactivate = deactivate;
		this.timeTilDeactivation = timeTilDeactivation;
	}

	/**
	 * @return the activate
	 */
	public Runnable getActivate() {
		return activate;
	}

	/**
	 * @param activate the activate to set
	 */
	public void setActivate(Runnable activate) {
		if (isRunning) {
			throw new IllegalStateException("Temporal effect is already active");
		}
		this.activate = activate;
	}

	/**
	 * @return the deactivate
	 */
	public Runnable getDeactivate() {
		return deactivate;
	}

	/**
	 * @param deactivate the deactivate to set
	 */
	public void setDeactivate(Runnable deactivate) {
		if (isRunning) {
			throw new IllegalStateException("Temporal effect is already active");
		}
		this.deactivate = deactivate;
	}

	/**
	 * @return the timeTilDeactivation
	 */
	public double getTimeTilDeactivation() {
		return timeTilDeactivation;
	}

	/**
	 * @param timeTilDeactivation the timeTilDeactivation to set
	 */
	public void setTimeTilDeactivation(double timeTilDeactivation) {
		if (isRunning) {
			throw new IllegalStateException("Temporal effect is already active");
		}
		this.timeTilDeactivation = timeTilDeactivation;
	}
	
	/**
	 * @return is already running
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	/**
	 * starts the runnable activate and deactivate in timeTilDeactivation seconds
	 */
	public void activate() {
		isRunning = true;
		activate.run();
		Bukkit.getScheduler().runTaskLater(plugin, deactivate, (long) (timeTilDeactivation * 20));
	}
}
