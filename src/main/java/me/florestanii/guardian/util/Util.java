package me.florestanii.guardian.util;

import me.florestanii.guardian.Guardian;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static void setTagColor(Guardian plugin, Player p, ChatColor color) {
        Scoreboard board = plugin.getServer().getScoreboardManager().getMainScoreboard();
        Team team = board.getTeam(p.getName());
        if (team == null) {
            team = board.registerNewTeam(p.getName());
        }
        team.setPrefix(color + "");
        team.addPlayer(p);
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.setScoreboard(board);
        }
    }

    public static void healPlayer(Player p) {
        p.setHealth(20.0);
        p.setFoodLevel(20);
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
    }

    public static ItemStack getColeredLeatherArmor(Material mat, Color color) {
        ItemStack item = new ItemStack(mat);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack renameItemStack(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static void removeInventoryItems(Inventory inv, Material type, int amount) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.remove(is);
                    amount = -newamount;
                    if (amount == 0)
                        break;
                }
            }
        }
    }
}
