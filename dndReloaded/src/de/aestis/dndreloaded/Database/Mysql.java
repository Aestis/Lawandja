package de.aestis.dndreloaded.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import de.aestis.dndreloaded.Main;

public class Mysql {

	private Connection con;
	
	public Connection getConnection() {
		return con;
	}
	
	public boolean connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			Main.instance.getLogger().info("No MySQL Driver Detected!");
			return false;
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + Main.instance.getConfig().getString("Mysql.host")
					+ ":" + Main.instance.getConfig().getString("Mysql.port") + "/"
					+ Main.instance.getConfig().getString("Mysql.database") + "?user="
					+ Main.instance.getConfig().getString("Mysql.username") + "&password="
					+ Main.instance.getConfig().getString("Mysql.password") + "&autoReconnect=true&useSSL=false");
			if (!(con.isClosed())) {
				Main.instance.getLogger().info("MySQL Connection Established!");
			}
		} catch (SQLException e) {
			Main.instance.getLogger().info("Error Whilst Connecting to Database: " + e.getMessage());
			return false;
		}
		keepAlive();
		return true;
	}

	public void close() {
		try {
			if (con != null && (!(con.isClosed()))) {
				con.close();
				if (con.isClosed()) {
					Main.instance.getLogger().info("MySQL Connection Closed.");
				}
			}
		} catch (SQLException e) {
			Main.instance.getLogger().info("Error Whilst Closing Database Connection.");
		}
	}
	
	private void keepAlive() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, new Runnable() {
			@Override
			public void run() {
				PreparedStatement stmt = null;
				try {
					stmt = con.prepareStatement("SHOW TABLES");
					stmt.executeQuery();
					Main.instance.getLogger().info("Database Heartbeat <3");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}, 2500, 2500);
	}
}
