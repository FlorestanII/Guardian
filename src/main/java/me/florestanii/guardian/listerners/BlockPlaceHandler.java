package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceHandler implements Listener {
    private final Guardian plugin;

    public BlockPlaceHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (plugin.isPlayerInArena(e.getPlayer())) {
            e.setCancelled(true);
        } else if (e.getPlayer().hasPermission("guardian.arena.admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }
}
