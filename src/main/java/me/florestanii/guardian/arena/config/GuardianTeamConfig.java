package me.florestanii.guardian.arena.config;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuardianTeamConfig {
    private String name;
    private ChatColor color;
    private Location spawn;
    private List<Location> respawnBlocks = new ArrayList<>();

    public GuardianTeamConfig() {
    }

    public GuardianTeamConfig(ConfigurationSection config) {
        name = config.getString("name");
        color = ChatColor.valueOf(config.getString("color"));
        spawn = ConfigUtil.getFullLocation(config.getConfigurationSection("spawn"));

        ConfigurationSection respawnBlocksConfig = config.getConfigurationSection("respawnBlocks");
        for (String blockKey : respawnBlocksConfig.getKeys(false)) {
            respawnBlocks.add(ConfigUtil.getLocation(respawnBlocksConfig.getConfigurationSection(blockKey)));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public List<Location> getRespawnBlocks() {
        return Collections.unmodifiableList(respawnBlocks);
    }

    public void addRespawnBlock(Location location) {
        respawnBlocks.add(location);
    }

    public void removeRespawnBlock(Location location) {
        respawnBlocks.remove(location);
    }

    public boolean isRespawnBlock(Location location) {
        return respawnBlocks.contains(location);
    }

    public ConfigurationSection getConfig() {
        ConfigurationSection config = new MemoryConfiguration();
        config.set("name", getName());
        config.set("color", getColor().name());
        ConfigUtil.setFullLocation(config.createSection("spawn"), getSpawn());
        config.createSection("respawnBlocks");

        int i = 0;
        for (Location respawnBlock : getRespawnBlocks()) {
            ConfigUtil.setLocation(config.getConfigurationSection("respawnBlocks").createSection("block" + i), respawnBlock);
            i++;
        }

        return config;
    }
}
