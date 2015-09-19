package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.team.GuardianTeam;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnHandler implements Listener{

	Guardian plugin;
	
	public PlayerRespawnHandler(Guardian plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e){
		final Player p = e.getPlayer();
		if(plugin.getArena().isPlayerInArena(p)){
			if(plugin.getArena().getTeamOfPlayer(p.getUniqueId()).hasAlLeastOneRespawnblock()){
				e.setRespawnLocation(plugin.getArena().getTeamOfPlayer(p.getUniqueId()).getSpawn());
				p.sendMessage(ChatColor.YELLOW + "Du konntest respawnen, weil du noch mindestens einen Respawnblock hast.");
				plugin.getArena().givePlayerLeatherArmor(p);
			}else{
				e.setRespawnLocation(plugin.getArena().getLeavePos());
				p.sendMessage(ChatColor.DARK_RED + "Du bist nun ausgeschieden!");
				final GuardianTeam team = plugin.getArena().getTeamOfPlayer(p.getUniqueId());
//				final GuardianTeam rivalTeam = plugin.getArena().getRivalTeamOfPlayer(p.getUniqueId());
//				p.sendMessage(ChatColor.GREEN + "Team " + rivalTeam.getChatColor() + rivalTeam.getName() + ChatColor.GREEN + " hat gewonnen!");
//				
				plugin.getArena().kickPlayer(p, team.getChatColor() + p.getDisplayName() + ChatColor.GRAY + " ist nun ausgeschieden!");
			}
		}
	}
}
