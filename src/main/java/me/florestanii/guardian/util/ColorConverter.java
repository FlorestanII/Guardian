package me.florestanii.guardian.util;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.util.Map;

public class ColorConverter {
    private static Map<ChatColor, Color> chatColorColor;

    static {
        chatColorColor = Maps.newHashMap();
        chatColorColor.put(ChatColor.BLACK, Color.BLACK);
        chatColorColor.put(ChatColor.DARK_BLUE, Color.BLUE);
        chatColorColor.put(ChatColor.DARK_GREEN, Color.GREEN);
        chatColorColor.put(ChatColor.DARK_AQUA, Color.AQUA);
        chatColorColor.put(ChatColor.DARK_RED, Color.RED);
        chatColorColor.put(ChatColor.DARK_PURPLE, Color.PURPLE);
        chatColorColor.put(ChatColor.GOLD, Color.OLIVE);
        chatColorColor.put(ChatColor.GRAY, Color.GRAY);
        chatColorColor.put(ChatColor.DARK_GRAY, Color.NAVY);
        chatColorColor.put(ChatColor.BLUE, Color.BLUE);
        chatColorColor.put(ChatColor.GREEN, Color.GREEN);
        chatColorColor.put(ChatColor.AQUA, Color.AQUA);
        chatColorColor.put(ChatColor.RED, Color.RED);
        chatColorColor.put(ChatColor.LIGHT_PURPLE, Color.PURPLE);
        chatColorColor.put(ChatColor.YELLOW, Color.YELLOW);
        chatColorColor.put(ChatColor.WHITE, Color.WHITE);
    }

    public static Color convertToColor(ChatColor color) {
        return chatColorColor.get(color);
    }

    private static Map<ChatColor, DyeColor> dyeColorColor;

    static {
        dyeColorColor = Maps.newHashMap();
        dyeColorColor.put(ChatColor.BLACK, DyeColor.BLACK);
        dyeColorColor.put(ChatColor.DARK_BLUE, DyeColor.BLUE);
        dyeColorColor.put(ChatColor.DARK_GREEN, DyeColor.GREEN);
        dyeColorColor.put(ChatColor.DARK_AQUA, DyeColor.CYAN);
        dyeColorColor.put(ChatColor.DARK_RED, DyeColor.RED);
        dyeColorColor.put(ChatColor.DARK_PURPLE, DyeColor.PURPLE);
        dyeColorColor.put(ChatColor.GOLD, DyeColor.ORANGE);
        dyeColorColor.put(ChatColor.GRAY, DyeColor.GRAY);
        dyeColorColor.put(ChatColor.DARK_GRAY, DyeColor.GRAY);
        dyeColorColor.put(ChatColor.BLUE, DyeColor.BLUE);
        dyeColorColor.put(ChatColor.GREEN, DyeColor.GREEN);
        dyeColorColor.put(ChatColor.AQUA, DyeColor.CYAN);
        dyeColorColor.put(ChatColor.RED, DyeColor.RED);
        dyeColorColor.put(ChatColor.LIGHT_PURPLE, DyeColor.PURPLE);
        dyeColorColor.put(ChatColor.YELLOW, DyeColor.YELLOW);
        dyeColorColor.put(ChatColor.WHITE, DyeColor.WHITE);
    }

    public static DyeColor convertToDyeColor(ChatColor color) {
        return dyeColorColor.get(color);
    }
}
