package me.florestanii.guardian.arena.config;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class ItemSpawnerConfig {
    private final Location location;
    private final int id;
    private final int data;

    public ItemSpawnerConfig(ConfigurationSection config) {
        location = ConfigUtil.getLocation(config);
        id = config.getInt("itemId");
        data = config.getInt("itemData", 0);
    }

    public Location getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }

    public int getData() {
        return data;
    }
}
