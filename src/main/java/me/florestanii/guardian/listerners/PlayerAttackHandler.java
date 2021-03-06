package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerAttackHandler implements Listener {
    private final Guardian plugin;

    public PlayerAttackHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            GuardianArena arena = plugin.getArena(p);
            if (arena != null && arena.getLobby().isPlayerInLobby(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            GuardianArena arena = plugin.getArena(p);

            if (arena != null) {
                if (arena.getLobby().isPlayerInLobby(p)) {
                    e.setCancelled(true);
                } else if (e.getDamager() instanceof Player) {
                    Player damager = (Player) e.getDamager();
                    if (!arena.isPlayerInArena(damager)) {
                        e.setCancelled(true);
                        return;
                    }
                    if (arena.getTeamOfPlayer(p).equals(arena.getTeamOfPlayer(damager))) {
                        e.setCancelled(true); //disable hitting players of the own team
                    }
                } else if (e.getDamager() instanceof Arrow) {
                    Arrow arrow = (Arrow) e.getDamager();
                    if (arrow.getShooter() instanceof Player) {
                        Player shooter = (Player) arrow.getShooter();
                        if (!arena.isPlayerInArena(shooter)) {
                            e.setCancelled(true);
                        } else if (arena.getTeamOfPlayer(shooter).equals(arena.getTeamOfPlayer(p))) {
                            e.setCancelled(true); //disable shooting players of the own team
                        }
                    }
                }
            }
        } else if (e.getEntity() instanceof Villager) {
            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();

                if (plugin.isPlayerInArena(damager)) {
                    e.setCancelled(true);
                }
            } else if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player shooter = (Player) arrow.getShooter();
                    if (plugin.isPlayerInArena(shooter)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}

