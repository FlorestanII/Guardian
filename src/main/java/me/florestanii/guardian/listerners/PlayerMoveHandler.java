package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveHandler implements Listener {
    private final Guardian plugin;

    public PlayerMoveHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        GuardianArena arena = plugin.getArena(e.getPlayer());

        if (arena != null) {
            if (!arena.getLobby().isPlayerInLobby(e.getPlayer())) {
                if (arena.getGuardianSpawner().getSpawn().distance(e.getPlayer().getLocation()) <= 10) {
                    for (Entity entity : e.getPlayer().getWorld().getEntities()) {
                        if (entity instanceof IronGolem) {
                            ((IronGolem) entity).setTarget(e.getPlayer());
                        }
                    }
                }
            }
        }
    }

}
