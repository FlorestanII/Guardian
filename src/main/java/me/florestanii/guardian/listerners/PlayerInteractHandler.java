package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractHandler implements Listener {
    private final Guardian plugin;

    public PlayerInteractHandler(Guardian plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
            GuardianArena arena = plugin.getArena(e.getPlayer());
            if (arena != null) {
                if (arena.getLobby().isPlayerInLobby(e.getPlayer())) {
                    e.setCancelled(true);
                } else {
                    e.setCancelled(true);
                }
            } else {
                if (e.getPlayer().hasPermission("guardian.arena.admin")) {
                    e.setCancelled(false);
                } else {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.getInventory().getHolder() instanceof Player && plugin.isPlayerInArena((Player) e.getInventory().getHolder())) {
            //players may not craft inside arenas
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Villager) {
            Player p = e.getPlayer();
            if (plugin.isPlayerInArena(p)) {
                e.setCancelled(true);

                switch (e.getRightClicked().getCustomName() == null ? "" : e.getRightClicked().getCustomName()) {
                    case "Shop":
                        Inventory shopInv = plugin.getServer().createInventory(null, 27, "Shop");

                        for (int i = 0; i < 10; i++) {
                            shopInv.setItem(i, Util.renameItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData()), " "));
                        }

                        shopInv.setItem(10, Util.renameItemStack(new ItemStack(Material.IRON_SWORD), ChatColor.RESET + "Weapons"));
                        shopInv.setItem(11, Util.renameItemStack(new ItemStack(Material.CHAINMAIL_CHESTPLATE), ChatColor.RESET + "Armor"));
                        shopInv.setItem(12, Util.renameItemStack(new ItemStack(Material.IRON_PICKAXE), ChatColor.RESET + "Pickaxes"));
                        shopInv.setItem(13, Util.renameItemStack(new ItemStack(Material.BOW), ChatColor.RESET + "Bows"));
                        shopInv.setItem(14, Util.renameItemStack(new ItemStack(Material.POTION), ChatColor.RESET + "Potions"));
                        shopInv.setItem(15, Util.renameItemStack(new ItemStack(Material.APPLE), ChatColor.RESET + "Food"));
                        shopInv.setItem(16, Util.renameItemStack(new ItemStack(Material.ENDER_PEARL), ChatColor.RESET + "Special"));

                        for (int i = 17; i < 27; i++) {
                            shopInv.setItem(i, Util.renameItemStack(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData()), " "));
                        }

                        p.openInventory(shopInv);
                        break;
                }
            }
        }
    }
}
