package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitHandler implements Listener{

	Guardian plugin;
	
	public PlayerQuitHandler(Guardian plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		
		if(plugin.getArena().isPlayerInArena(e.getPlayer())){
			plugin.getArena().leavePlayer(e.getPlayer());
		}else{
			return;
		}
		
	}
	
}
