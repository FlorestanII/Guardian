package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathHandler implements Listener{
	
	Guardian plugin;
	
	public PlayerDeathHandler(Guardian plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		final Player p = e.getEntity();
		if(plugin.getArena().isPlayerInArena(p)){
			e.getDrops().clear();
			e.setNewLevel(0);
			e.setNewExp(0);
			e.setDeathMessage("");
			e.setDroppedExp(0);
			
			if(p.getKiller() == null){
				plugin.getArena().broadcastMessage(plugin.getArena().getTeamOfPlayer(p.getUniqueId()).getChatColor() + p.getDisplayName() + ChatColor.GRAY + " ist gestorben.");
			}else{
				plugin.getArena().broadcastMessage(plugin.getArena().getTeamOfPlayer(p.getUniqueId()).getChatColor() + p.getDisplayName() + ChatColor.GRAY + " wurde von " + plugin.getArena().getTeamOfPlayer(p.getKiller().getUniqueId()).getChatColor() + p.getKiller().getDisplayName() + ChatColor.GRAY + "getötet.");
			}
			
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
				@Override
				public void run() {
					PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
					((CraftPlayer)p).getHandle().playerConnection.a(packet);
				}
					
			}, 20);
			
		}
	}
	
}
