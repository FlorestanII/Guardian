package me.florestanii.guardian.arena.config;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

public class ItemSpawnerConfig {
    private Location location;
    private int id;
    private int data;
    private int delay;

    public ItemSpawnerConfig() {
    }

    public ItemSpawnerConfig(ConfigurationSection config) {
        location = ConfigUtil.getLocation(config);
        id = config.getInt("itemId");
        data = config.getInt("itemData", 0);
        delay = config.getInt("delay", 8);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @SuppressWarnings("deprecation")
    public void setType(Material material) {
        id = material.getId();
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public ConfigurationSection getConfig() {
        ConfigurationSection config = new MemoryConfiguration();
        ConfigUtil.setLocation(config, getLocation());
        config.set("itemId", getId());
        config.set("itemData", getData());
        config.set("delay", getDelay());
        return config;
    }
}
