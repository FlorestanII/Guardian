package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.util.ReflectionUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathHandler implements Listener {
    private final Guardian plugin;

    public PlayerDeathHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        final Player p = e.getEntity();
        GuardianArena arena = plugin.getArena(p);

        if (arena != null) {
            e.getDrops().clear();
            e.setNewLevel(0);
            e.setNewExp(0);
            e.setDeathMessage("");
            e.setDroppedExp(0);

            if (p.getKiller() == null) {
                arena.broadcastMessage(arena.getTeamOfPlayer(p).getChatColor() + p.getName() + ChatColor.GRAY + " ist gestorben.");
            } else {
                arena.broadcastMessage(arena.getTeamOfPlayer(p).getChatColor() + p.getName() + ChatColor.GRAY + " wurde von " + arena.getTeamOfPlayer(p.getKiller()).getChatColor() + p.getKiller().getName() + ChatColor.GRAY + " getötet.");
            }

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    try {
                        ReflectionUtils.sendRespawnPacket(p);
                    } catch (Exception e1) {
                        plugin.getLogger().warning("Could not send respawn packet.");
                    }
                }
            }, 20);
        }
    }
}
