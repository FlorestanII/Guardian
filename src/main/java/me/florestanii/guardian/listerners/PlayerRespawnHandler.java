package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.team.GuardianTeam;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnHandler implements Listener {
    private final Guardian plugin;

    public PlayerRespawnHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        final GuardianArena arena = plugin.getArena(p);

        if (arena != null) {
            if (arena.getTeamOfPlayer(p).canRespawn()) {
                e.setRespawnLocation(arena.getTeamOfPlayer(p).getSpawn());
                p.sendMessage(ChatColor.YELLOW + "Du konntest respawnen, weil du noch mindestens einen Respawnblock hast.");
                arena.givePlayerLeatherArmor(p);
            } else {
                e.setRespawnLocation(arena.getLeavePos());
                p.sendMessage(ChatColor.DARK_RED + "Du bist nun ausgeschieden!");
                final GuardianTeam team = arena.getTeamOfPlayer(p);
                arena.kickPlayer(p, team.getChatColor() + p.getDisplayName() + ChatColor.GRAY + " ist nun ausgeschieden!");
            }
        }
    }
}
