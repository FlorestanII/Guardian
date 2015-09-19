package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.team.GuardianPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakHandler implements Listener {
    private final Guardian plugin;

    public BlockBreakHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final GuardianArena arena = plugin.getArena(e.getPlayer());

        if (arena != null) {
            if (arena.getLobby().isPlayerInLobby(e.getPlayer())) {
                e.setCancelled(true);
            } else {
                if (e.getBlock().getType() != Material.DIAMOND_BLOCK) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.DARK_RED + "Du darfst den Block " + ChatColor.YELLOW + e.getBlock().getType() + ChatColor.DARK_RED + " nicht zerstören!");
                } else {
                    GuardianPlayer player = arena.getPlayer(e.getPlayer().getUniqueId());
                    if (arena.getTeamOfPlayer(player.getUniqueId()).isOwnRespawnblock(e.getBlock().getLocation())) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "Du kannst deinen eigenen respawnblock nicht abbauen!");
                    } else if (arena.getRivalTeamOfPlayer(player.getUniqueId()).isOwnRespawnblock(e.getBlock().getLocation())) {

                        if (arena.getTeamRed().isPlayerInTeam(player.getUniqueId())) {
                            arena.broadcastMessage(ChatColor.BLUE + "Ein Respawnblock von Team Blau wurde von " + ChatColor.RED + e.getPlayer().getDisplayName() + ChatColor.BLUE + " zerstört!");
                        } else {
                            arena.broadcastMessage(ChatColor.RED + "Ein Respawnblock von Team Rot wurde von " + ChatColor.BLUE + e.getPlayer().getDisplayName() + ChatColor.RED + " zerstört!");
                        }
                        arena.broadcastSound(Sound.WITHER_DEATH);
                        e.getBlock().setType(Material.AIR);
                        e.setCancelled(true);
                    } else {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "Du darfst diesen Block nicht zerstören, da dies keine Respawnblock ist.");
                    }
                }
            }
        } else {
            if (e.getPlayer().hasPermission("guardian.admin.build")) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        }
    }
}
