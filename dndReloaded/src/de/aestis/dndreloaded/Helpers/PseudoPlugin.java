package de.aestis.dndreloaded.Helpers;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

public class PseudoPlugin implements Plugin{

	String name;
	
	/**
	 * WARNING!! use only if getName() is needed and normal plugin name is not wanted
	 * will produce unexpected behavior/errors otherwise
	 * @param name the name to use
	 */
	public PseudoPlugin(String name) {
		this.name = name;
	}
	
	public File getDataFolder() {
		return null;
	}

	public PluginDescriptionFile getDescription() {
		return null;
	}

	public FileConfiguration getConfig() {
		return null;
	}

	public InputStream getResource(String var1) {
		return null;
	}

	public void saveConfig() {
	}

	public void saveDefaultConfig() {
	}

	public void saveResource(String var1, boolean var2) {
	}

	public void reloadConfig() {
	}

	public PluginLoader getPluginLoader() {
		return null;
	}

	public Server getServer() {
		return null;
	}

	public boolean isEnabled() {
		return false;
	}

	public void onDisable() {
	}

	public void onLoad() {
	}

	public void onEnable() {
	}

	public boolean isNaggable() {
		return false;
	}

	public void setNaggable(boolean var1) {
	}

	public ChunkGenerator getDefaultWorldGenerator(String var1, String var2) {
		return null;
	}

	public Logger getLogger() {
		return null;
	}

	public String getName() {
		return name;
	}
	
	public List<String> onTabComplete(CommandSender var1, Command var2, String var3,
			String[] var4) {
		return null;
	}
	
	public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
		return false;
	}
}
