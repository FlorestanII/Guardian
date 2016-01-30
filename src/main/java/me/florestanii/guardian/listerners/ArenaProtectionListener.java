package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.team.GuardianPlayer;
import me.florestanii.guardian.arena.team.GuardianTeam;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ArenaProtectionListener implements Listener {
    private final Guardian plugin;

    public ArenaProtectionListener(Guardian plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final GuardianArena arena = plugin.getArena(e.getPlayer());

        if (arena != null) {
            if (arena.getLobby().isPlayerInLobby(e.getPlayer())) {
                e.setCancelled(true);
            } else {
                if (e.getBlock().getType() != Material.DIAMOND_BLOCK) {
                    e.getPlayer().sendMessage(ChatColor.DARK_RED + "Du darfst diesen Block nicht zerstören.");
                    e.setCancelled(true);
                } else {
                    GuardianPlayer player = arena.getPlayer(e.getPlayer());
                    if (arena.getTeamOfPlayer(player.getBukkitPlayer()).isOwnRespawnblock(e.getBlock().getLocation())) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "Du kannst deinen eigenen Respawnblock nicht abbauen!");
                    } else {
                        for (GuardianTeam team : arena.getRivalTeamsOfPlayer(player.getBukkitPlayer())) {
                            if (team.isOwnRespawnblock(e.getBlock().getLocation())) {
                                arena.broadcastMessage(Guardian.prefix()
                                        .append("Ein Respawnblock von ").darkRed()
                                        .append("Team " + team.getName()).color(team.getChatColor())
                                        .append(" wurde von ").darkRed()
                                        .append(e.getPlayer().getName()).color(arena.getTeamOfPlayer(e.getPlayer()).getChatColor())
                                        .append(" zerstört.").darkRed()
                                        .getSingleLine());
                                arena.broadcastSound(Sound.WITHER_DEATH);
                                e.getBlock().setType(Material.AIR);
                                e.setCancelled(true);
                                return;
                            }
                        }

                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "Du darfst diesen Block nicht zerstören.");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (plugin.isPlayerInArena(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
