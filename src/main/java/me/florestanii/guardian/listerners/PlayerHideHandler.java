package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerHideHandler implements Listener{

	Guardian plugin;
	
	public PlayerHideHandler(Guardian plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		for(Player player : plugin.getServer().getOnlinePlayers()){
        	if(!plugin.isPlayerInArena(player)){
        		player.showPlayer(e.getPlayer());
        	}else{
        		player.hidePlayer(e.getPlayer());
        	}
        }
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		for(Player player : plugin.getServer().getOnlinePlayers()){
        	player.showPlayer(e.getPlayer());
        }
	}
	
}
