package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;

import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveHandler implements Listener{

	Guardian plugin;
	
	public PlayerMoveHandler(Guardian plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		if(plugin.getArena().isPlayerInArena(e.getPlayer())){
			if(plugin.getArena().getLobby().isPlayerInLobby(e.getPlayer())){
				
			}else{
				if(plugin.getArena().getGuardianSpawner().getSpawn().distance(e.getPlayer().getLocation()) <= 10){
					for(Entity entity : e.getPlayer().getWorld().getEntities()){
						if(entity instanceof IronGolem){
							((IronGolem) entity).setTarget(e.getPlayer());
						}
					}
				}
			}
		}
	}
	
}
