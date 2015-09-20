package me.florestanii.guardian.arena.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class GuardianArenaConfig {
    private World world;
    private Location leaveLocation;
    private Location lobbyLocation;
    private Location arenaMiddle;
    private Map<String, GuardianTeamConfig> teams;
    private List<ItemSpawnerConfig> itemSpawners;

    public GuardianArenaConfig(ConfigurationSection config) {
        this.world = Bukkit.getWorld(config.getString("world"));
        this.leaveLocation = ConfigUtil.getFullLocation(config.getConfigurationSection("leave"));
        this.lobbyLocation = ConfigUtil.getFullLocation(config.getConfigurationSection("lobby"));
        this.arenaMiddle = ConfigUtil.getFullLocation(config.getConfigurationSection("middle"));

        teams = new HashMap<>();
        ConfigurationSection teamsConfig = config.getConfigurationSection("teams");
        for (String teamKey : teamsConfig.getKeys(false)) {
            teams.put(teamKey, new GuardianTeamConfig(teamsConfig.getConfigurationSection(teamKey)));
        }

        itemSpawners = new ArrayList<>();
        ConfigurationSection itemSpawnersConfig = config.getConfigurationSection("spawners");
        for (String itemSpawnerKey : itemSpawnersConfig.getKeys(false)) {
            itemSpawners.add(new ItemSpawnerConfig(itemSpawnersConfig.getConfigurationSection(itemSpawnerKey)));
        }

        //TODO minPlayers and maxPlayers
    }

    public GuardianArenaConfig() {
        teams = new HashMap<>();
        itemSpawners = new ArrayList<>();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Location getLeaveLocation() {
        return leaveLocation;
    }

    public void setLeaveLocation(Location leaveLocation) {
        this.leaveLocation = leaveLocation;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
    }

    public Location getArenaMiddle() {
        return arenaMiddle;
    }

    public void setArenaMiddle(Location arenaMiddle) {
        this.arenaMiddle = arenaMiddle;
    }

    public Map<String, GuardianTeamConfig> getTeams() {
        return Collections.unmodifiableMap(teams);
    }

    public void addTeam(String name, GuardianTeamConfig config) {
        teams.put(name, config);
    }

    public void removeTeam(String name) {
        teams.remove(name);
    }

    public List<ItemSpawnerConfig> getItemSpawners() {
        return Collections.unmodifiableList(itemSpawners);
    }

    public void addItemSpawner(ItemSpawnerConfig config) {
        itemSpawners.add(config);
    }

    public void removeItemSpawner(int i) {
        if (i >= 0 && i < itemSpawners.size()) {
            itemSpawners.remove(i);
        }
    }
}
