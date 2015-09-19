package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatHandler implements Listener{

	Guardian plugin;
	
	public PlayerChatHandler(Guardian plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		if(plugin.getArena().isPlayerInArena(p)){
			String msg = e.getMessage();
			System.out.println(p.getDisplayName() + ": " + msg);
			if(plugin.getArena().getLobby().isPlayerInLobby(p)){
				plugin.getArena().getLobby().broadcastMessage(ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + msg);
				e.setCancelled(true);
			}else{
				if(msg.startsWith("@all")){
					plugin.getArena().broadcastMessage(ChatColor.GRAY + "[GLOBAL] " + plugin.getArena().getTeamOfPlayer(p.getUniqueId()).getChatColor() + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + msg.replace("@all", ""));
					e.setCancelled(true);
				}else{
					plugin.getArena().getTeamOfPlayer(p.getUniqueId()).broadcastMessage(ChatColor.GRAY + "[TEAM] " + plugin.getArena().getTeamOfPlayer(p.getUniqueId()).getChatColor() + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + msg);
					e.setCancelled(true);
				}
			}
		}else{
			e.setCancelled(true);
		}
	}
	
}
