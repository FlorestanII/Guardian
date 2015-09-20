package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatHandler implements Listener {

    Guardian plugin;

    public PlayerChatHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        GuardianArena arena = plugin.getArena(p);

        if (arena != null) {
            String msg = e.getMessage();
            if (arena.getLobby().isPlayerInLobby(p)) {
                arena.getLobby().broadcastMessage(ChatColor.GREEN + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + msg);
                e.setCancelled(true);
            } else {
                if (msg.startsWith("@all")) {
                    arena.broadcastMessage(ChatColor.GRAY + "[GLOBAL] " + arena.getTeamOfPlayer(p).getChatColor() + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + msg.replace("@all", ""));
                    e.setCancelled(true);
                } else {
                    arena.getTeamOfPlayer(p).broadcastMessage(ChatColor.GRAY + "[TEAM] " + arena.getTeamOfPlayer(p).getChatColor() + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + msg);
                    e.setCancelled(true);
                }
            }
        } else {
            e.setCancelled(true);
        }
    }

}
