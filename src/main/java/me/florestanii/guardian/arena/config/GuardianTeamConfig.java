package me.florestanii.guardian.arena.config;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuardianTeamConfig {
    private final String name;
    private final ChatColor color;
    private final Location spawn;
    private final List<Location> respawnBlocks = new ArrayList<>();

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

    public ChatColor getColor() {
        return color;
    }

    public Location getSpawn() {
        return spawn;
    }

    public List<Location> getRespawnBlocks() {
        return Collections.unmodifiableList(respawnBlocks);
    }
}
