package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathHandler implements Listener {
    Guardian plugin;

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
                arena.broadcastMessage(arena.getTeamOfPlayer(p).getChatColor() + p.getDisplayName() + ChatColor.GRAY + " ist gestorben.");
            } else {
                arena.broadcastMessage(arena.getTeamOfPlayer(p).getChatColor() + p.getDisplayName() + ChatColor.GRAY + " wurde von " + arena.getTeamOfPlayer(p.getKiller()).getChatColor() + p.getKiller().getDisplayName() + ChatColor.GRAY + "getötet.");
            }

            //TODO auto-respawn the player
            /*
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
				@Override
				public void run() {
					PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
					((CraftPlayer)p).getHandle().playerConnection.a(packet);
				}
					
			}, 20);
			*/
        }
    }

}
