package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerAttackHandler implements Listener{

	Guardian plugin;
	
	public PlayerAttackHandler(Guardian plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerAttack(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			
			if(plugin.getArena().isPlayerInArena(p)){
				if(plugin.getArena().getLobby().isPlayerInLobby(p)){
					e.setCancelled(true);
				}
			}else{
				e.setCancelled(true);
			}
			
		}
	}
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			
			if(plugin.getArena().isPlayerInArena(p)){
				if(plugin.getArena().getLobby().isPlayerInLobby(p)){
					e.setCancelled(true);
					return;
				}else if(e.getDamager() instanceof Player){
					Player damager = (Player)e.getDamager();
					if(!plugin.getArena().isPlayerInArena(damager)){
						e.setCancelled(true);
						return;
					}
					if(plugin.getArena().getTeamOfPlayer(p.getUniqueId()).getName().equals(plugin.getArena().getTeamOfPlayer(damager.getUniqueId()).getName())){
						e.setCancelled(true);
						return;
					}
				}
			}else{
				e.setCancelled(true);
				return;
			}
			
		}else if(e.getEntity() instanceof Villager){
			if(e.getDamager() instanceof Player){
				Player damager = (Player)e.getDamager();
				
				if(plugin.getArena().isPlayerInArena(damager)){
					e.setCancelled(true);
				}else if(damager.hasPermission("guardian.admin")){
					e.setCancelled(false);
					return;
				}else{
					e.setCancelled(true);
					return;
				}
				
			}else{
				e.setCancelled(true);
				return;
			}
		}
	}
	
}

