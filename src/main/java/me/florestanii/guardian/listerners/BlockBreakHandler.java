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
                                arena.broadcastMessage(ChatColor.DARK_RED + "Ein Respawnblock von Team " + team.getChatColor() + team.getName() + ChatColor.DARK_RED + 
                                        " wurde von " + arena.getTeamOfPlayer(e.getPlayer()).getChatColor() + e.getPlayer().getName() + ChatColor.DARK_RED + " zerstört!");
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
        } else {
            if (e.getPlayer().hasPermission("guardian.admin.build")) { //TODO only block building if the block is inside of an arena
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        }
    }
}
