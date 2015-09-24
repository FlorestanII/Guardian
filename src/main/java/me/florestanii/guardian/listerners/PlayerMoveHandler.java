package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
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

        if (arena != null && !arena.getLobby().isPlayerInLobby(e.getPlayer())) {
            if (arena.getGuardianSpawner().getSpawn().distanceSquared(e.getPlayer().getLocation()) <= 100) {
                for (IronGolem entity : e.getPlayer().getWorld().getEntitiesByClass(IronGolem.class)) { //TODO Only get nearby entities!
                    entity.setTarget(e.getPlayer());
                }
            }
        }
    }
}