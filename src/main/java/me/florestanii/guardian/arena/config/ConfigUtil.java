package me.florestanii.guardian.arena.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigUtil {
    public static Location getFullLocation(ConfigurationSection config) {
        return new Location(Bukkit.getWorld(config.getString("world")),
                config.getDouble("x"),
                config.getDouble("y"),
                config.getDouble("z"),
                (float) config.getDouble("yaw"),
                (float) config.getDouble("pitch"));
    }

    public static Location getLocation(ConfigurationSection config) {
        return new Location(Bukkit.getWorld(config.getString("world")),
                config.getDouble("x"),
                config.getDouble("y"),
                config.getDouble("z"));
    }
}
