package de.aestis.dndreloaded.Overrides.Packets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
/*import java.util.Objects;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;*/
import org.bukkit.entity.Player;
/*import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;*/

import de.aestis.dndreloaded.Players.PlayerData;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.Packet;
import net.minecraft.server.v1_16_R1.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_16_R1.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_16_R1.PacketPlayOutScoreboardScore;
/*import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.IScoreboardCriteria;
import net.minecraft.server.v1_15_R1.Packet;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_15_R1.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_15_R1.Scoreboard;
import net.minecraft.server.v1_15_R1.ScoreboardObjective;
import net.minecraft.server.v1_15_R1.ScoreboardServer;*/
import net.minecraft.server.v1_16_R1.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_16_R1.Scoreboard;
import net.minecraft.server.v1_16_R1.ScoreboardScore;
import net.minecraft.server.v1_16_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R1.IScoreboardCriteria;
import net.minecraft.server.v1_16_R1.IScoreboardCriteria.EnumScoreboardHealthDisplay;

public class TitleBar {

	
	
	private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private static void setField(Object packet, Field field, Object value) {
        field.setAccessible(true);

        try {
            field.set(packet, value);
        } catch (IllegalAccessException | IllegalArgumentException var4) {
            var4.printStackTrace();
        }

        field.setAccessible(!field.isAccessible());
    }

    private static Field getField(Class<?> classs, String fieldname) {
        try {
            return classs.getDeclaredField(fieldname);
        } catch (SecurityException | NoSuchFieldException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static void sendNameTagPlayers(Player player, String prefix, String suffix, Player players, int priority) {
        String pname = player.getName();
        String name = "" + priority + pname.charAt(0) + "df734jmg9a40";
        List<String> pl = new ArrayList();
        pl.add(player.getName());
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        Class<? extends PacketPlayOutScoreboardTeam> clas = packet.getClass();
   
        IChatBaseComponent pre = ChatSerializer.a("{\"text\":\"" + prefix + "\"}");
        IChatBaseComponent suf = ChatSerializer.a("{\"text\":\"" + suffix + "\"}");
        
        IChatBaseComponent rnd = ChatSerializer.a("{\"text\":\"" + "hehohehoheho" + "\"}");
        
        setField(packet, getField(clas, "a"), name); //Team Name
        setField(packet, getField(clas, "b"), rnd); //Team Display Name
        setField(packet, getField(clas, "c"), pre); //Real Prefix
        setField(packet, getField(clas, "d"), suf); //Real Suffix
        setField(packet, getField(clas, "e"), "ALWAYS"); //ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS
        setField(packet, getField(clas, "f"), rnd);
        setField(packet, getField(clas, "h"), pl);
        setField(packet, getField(clas, "i"), 0);

        sendPacket(players, packet);
    }

    public static void sendNameTags(Player player, String prefix, String suffix, int priority) {

        for (Player all : Bukkit.getOnlinePlayers()) {
            sendNameTagPlayers(player, prefix, suffix, all, priority);
        }

    }
    
    public static void test(final Player player) {
    	
    	String name = "Testhehohehoheho";//the string next to the name will be called test
    	IChatBaseComponent obj = ChatSerializer.a("{\"text\":\"" + name + "\"}");
    	
    	Scoreboard sb = new Scoreboard();//Create new scoreboard
    	sb.registerObjective(name, IScoreboardCriteria.DUMMY, obj, EnumScoreboardHealthDisplay.INTEGER);

    	PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective(sb.getObjective(name), 0);//Create Scoreboard create packet
    	PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(2, sb.getObjective(name));//Create display packet set to under name mode
    	 
    	ScoreboardScore scoreItem1 = sb.getPlayerScoreForObjective(player.getDisplayName(), sb.getObjective(name));//Create a new item with the players name
    	scoreItem1.setScore(42);//this will set the integer under to the player's name, who ran the command to 42
    	 
    	PacketPlayOutScoreboardScore pScoreItem1 = new PacketPlayOutScoreboardScore();//Create score packet 1
    	
    	for(Player p : Bukkit.getOnlinePlayers())
    	{ //send to all the players on the server
    	sendPacket(player, packet);//Send Scoreboard create packet
    	sendPacket(player, display);//Send the display packet
    	sendPacket(player, pScoreItem1);//Send score update packet
    	
    	}
    }
	
	
	
	
	/*public void appendTitleBar (Player player, PlayerData pd) {
		
		
	}

	private static void sendPacket(Player player, Packet<?> packet) {
		
		try
		{
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
    }

    private static void setField(Object packet, Field field, Object value) {
        field.setAccessible(true);

        try {
            field.set(packet, value);
        } catch (IllegalAccessException | IllegalArgumentException var4) {
            var4.printStackTrace();
        }

        field.setAccessible(!field.isAccessible());
    }

    private static Field getField(Class<?> classs, String fieldname) {
        try {
            return classs.getDeclaredField(fieldname);
        } catch (SecurityException | NoSuchFieldException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static void sendNameTagPlayers(Player player, String text) {

        List<String> pl = new ArrayList();
        pl.add(player.getName());
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        Class<? extends PacketPlayOutScoreboardTeam> clas = packet.getClass();
   
        IChatBaseComponent comp = new IChatBaseComponent.ChatSerializer().b(player.getName());
        
        setField(packet, getField(clas, "a"), player.getName());
        setField(packet, getField(clas, "b"), comp);
        setField(packet, getField(clas, "c"), comp);
        setField(packet, getField(clas, "d"), comp);
        setField(packet, getField(clas, "e"), "ALWAYS");
        setField(packet, getField(clas, "h"), pl);
        setField(packet, getField(clas, "i"), 0);

        sendPacket(player, packet);
    }

    public static void sendNameTags(Player player, String text) {

        for (Player all : Bukkit.getOnlinePlayers()) {
            sendNameTagPlayers(player, text);
        }

    }*/
	
	
	
	
	/*private void setPacketScoreboard(Player player, String title) {
		
		Scoreboard scoreboard = new Scoreboard();
		
		ScoreboardObjective objective;

        objective = new ScoreboardObjective((net.minecraft.server.v1_15_R1.Scoreboard) scoreboard
        , "Test §<-- geht", IScoreboardCriteria.DUMMY, IChatBaseComponent.b(Objects.requireNonNull(IChatBaseComponent.ChatSerializer.b("Test §<-- geht"))),
                IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
        
        PacketPlayOutScoreboardObjective removepack = new PacketPlayOutScoreboardObjective(objective, 1);
        PacketPlayOutScoreboardObjective createpack = new PacketPlayOutScoreboardObjective(objective, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, objective);

        objective.setDisplayName(IChatBaseComponent.b(Objects.requireNonNull(IChatBaseComponent.ChatSerializer.b("Test §<-- geht"))));

        PacketPlayOutScoreboardScore placeholder3 = new PacketPlayOutScoreboardScore(ScoreboardServer.Action.CHANGE, "Test §<-- geht", "   ", 6);
        PacketPlayOutScoreboardScore statusName = new PacketPlayOutScoreboardScore(ScoreboardServer.Action.CHANGE, "Test §<-- geht", "§f§lStatus", 5);
        PacketPlayOutScoreboardScore status = new PacketPlayOutScoreboardScore(ScoreboardServer.Action.CHANGE, "Test §<-- geht", "§8- §7status <---", 4);
        PacketPlayOutScoreboardScore placeholder2 = new PacketPlayOutScoreboardScore(ScoreboardServer.Action.CHANGE, "Test §<-- geht", "  ", 3);
        PacketPlayOutScoreboardScore webName = new PacketPlayOutScoreboardScore(ScoreboardServer.Action.CHANGE, "Test §<-- geht", "§f§lWebsite", 2);
        PacketPlayOutScoreboardScore web = new PacketPlayOutScoreboardScore(ScoreboardServer.Action.CHANGE, "Test §<-- geht", "§8- §7deine seite", 1);
        PacketPlayOutScoreboardScore placeholder1 = new PacketPlayOutScoreboardScore(ScoreboardServer.Action.CHANGE, "Test §<-- geht", " ", 0);
        
        
        sendPacket(player, removepack);
        sendPacket(player, createpack);
        sendPacket(player, display);
        sendPacket(player, placeholder3);
        sendPacket(player, statusName);
        sendPacket(player, status);
        sendPacket(player, placeholder2);
        sendPacket(player, webName);
        sendPacket(player, web);
        sendPacket(player, placeholder1);
	}
	
	private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }*/
	
}
