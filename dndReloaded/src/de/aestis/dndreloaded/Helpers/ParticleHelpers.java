package de.aestis.dndreloaded.Helpers;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ParticleHelpers {

	
	public void spawnParticleClient(Player player, Particle particle, Vector vec, double speed, double count, Boolean forced) {
		
		//player.spawnParticle(particle, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
		
		Location loc = player.getLocation();
		player.spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), vec.getBlockX(), vec.getY(), vec.getZ(), speed, count);
	//                                   posx, posy, posz, widthx, height, widthz, speed, count, force
	}
	
	public void spawnParticleServer() {
		
		//TODO
	}
	
}
