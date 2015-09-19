package me.florestanii.guardian.arena.specialitems.teleportpowder;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class TeleportPowderHandler implements Listener {
    private final HashMap<UUID, TeleportPowderCountdown> countdowns = new HashMap<>();
    private final Guardian plugin;

    public TeleportPowderHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            final GuardianArena arena = plugin.getArena(e.getPlayer());

            if (arena != null) {
                if (e.getItem() != null && e.getItem().getType() == Material.SULPHUR) {
                    if (!hasCountdown(e.getPlayer())) {
                        e.getPlayer().sendMessage(ChatColor.YELLOW + "Bewege dich nun nicht mehr, sonst wird die Teleportation abgebrochen!");
                        TeleportPowderCountdown countdown = new TeleportPowderCountdown(6, e.getPlayer(), new Runnable() {

                            @Override
                            public void run() {
                                e.getPlayer().teleport(arena.getTeamOfPlayer(e.getPlayer().getUniqueId()).getSpawn());
                                Util.removeInventoryItems(e.getPlayer().getInventory(), Material.SULPHUR, 1);
                            }

                        });
                        countdown.runTaskTimer(plugin, 0, 20);
                        countdowns.put(e.getPlayer().getUniqueId(), countdown);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (hasCountdown(e.getPlayer())) {
            if (e.getFrom().distanceSquared(e.getTo()) > 0) {
                e.getPlayer().sendMessage(ChatColor.RED + "Die Teleportation wurde abgebrochen!");
                stopCountdown(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (hasCountdown(p)) {
                p.sendMessage(ChatColor.RED + "Die Teleportation wurde abgebrochen!");
                stopCountdown(p);
            }
        }
    }

    private boolean hasCountdown(Player player) {
        return countdowns.containsKey(player.getUniqueId());
    }

    private void stopCountdown(Player player) {
        TeleportPowderCountdown countdown = countdowns.get(player.getUniqueId());
        if (countdown != null) {
            countdown.cancel();
        }
    }
}
