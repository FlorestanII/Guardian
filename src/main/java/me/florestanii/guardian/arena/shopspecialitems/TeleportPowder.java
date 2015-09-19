package me.florestanii.guardian.arena.shopspecialitems;

import java.util.HashMap;
import java.util.UUID;

public class TeleportPowder {

	private static HashMap<UUID, TeleportPowderCountdown> countdowns = new HashMap<UUID, TeleportPowderCountdown>();
	
	public static void startCountdownForPlayer(TeleportPowderCountdown countdown){
		countdowns.put(countdown.getPlayer().getUniqueId(), countdown);
		countdowns.get(countdown.getPlayer().getUniqueId()).start();
	}
	public static boolean hasPlayerACountdown(UUID uuid){
		return countdowns.containsKey(uuid);
	}
	public static boolean stopCountdownForPlayer(UUID uuid){
		if(hasPlayerACountdown(uuid)){
			countdowns.get(uuid).stop();
			countdowns.remove(uuid);
			return true;
		}else{
			return false;
		}
	}
}
