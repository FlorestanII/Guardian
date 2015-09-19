package me.florestanii.guardian.arena.team;

import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class GuardianTeam {
    private GuardianArena arena;
    private String name;
    private HashMap<UUID, GuardianPlayer> players = new HashMap<>();

    private List<Location> respawnBlocks;
    private Location spawn;

    ChatColor chatColor = ChatColor.WHITE;

    public GuardianTeam(GuardianArena arena, Location spawn, String name) {
        this.arena = arena;
        this.spawn = spawn;
        this.name = name;
    }

    public GuardianTeam(GuardianArena arena, String name) {
        this.arena = arena;
        this.name = name;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public void setChatColor(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    public void joinPlayer(GuardianPlayer player) {
        players.put(player.getUniqueId(), player);
        arena.getPlugin().getServer().getPlayer(player.getUniqueId()).teleport(spawn);
        arena.getPlugin().getServer().getPlayer(player.getUniqueId()).setPlayerListName(chatColor + player.getDisplayName());
        Util.setTagColor(arena.getPlugin(), arena.getPlugin().getServer().getPlayer(player.getUniqueId()), chatColor);
    }

    public void removePlayer(GuardianPlayer player) {
        players.remove(player.getUniqueId());
        arena.getPlugin().getServer().getPlayer(player.getUniqueId()).setPlayerListName(player.getDisplayName());
    }

    public void removePlayer(final UUID uuid) {
        players.remove(uuid);
        arena.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(arena.getPlugin(), new Runnable() {

            @Override
            public void run() {
                try {
                    arena.getPlugin().getServer().getPlayer(uuid).setPlayerListName(arena.getPlugin().getServer().getPlayer(uuid).getDisplayName());
                } catch (Exception e) {
                }
            }
        }, 20);
    }

    public String getName() {
        return name;
    }

    public void kickAllPlayers() {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(
                this.players.values());
        for (GuardianPlayer p : players) {
            arena.kickPlayer(arena.getPlugin().getServer().getPlayer(p.getUniqueId()), null);
        }
    }

    public boolean isOwnRespawnblock(Location loc) {
        for (Location respawnLocation : respawnBlocks) {
            if (respawnLocation.equals(loc)) {
                return true;
            }
        }
        return false;
    }

    public GuardianPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public boolean isPlayerInTeam(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public boolean isPlayerInTeam(UUID uuid) {
        return players.containsKey(uuid);
    }

    public int getPlayerCount() {
        return players.size();
    }

    public ArrayList<GuardianPlayer> getPlayers() {
        return new ArrayList<GuardianPlayer>(players.values());
    }

    public void setRespawnBlocks(List<Location> locations) {
        this.respawnBlocks = locations;
    }

    public void broadcastMessage(String msg) {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(this.players.values());
        for (GuardianPlayer p : players) {
            arena.getPlugin().getServer().getPlayer(p.getUniqueId()).sendMessage(msg);
        }
    }

    public void broadcastSound(Sound sound) {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(this.players.values());
        for (GuardianPlayer p : players) {
            arena.getPlugin().getServer().getPlayer(p.getUniqueId()).playSound(arena.getPlugin().getServer().getPlayer(p.getUniqueId()).getLocation(), sound, 3, 2);
        }
    }

    public Location getSpawn() {
        return spawn;
    }

    public boolean isReady() {
        return arena != null && chatColor != null && name != null && respawnBlocks != null && canRespawn() && spawn != null;
    }

    /**
     * Check if this team has any respawn blocks left.
     *
     * @return true if there is at least one respawn block, false if not
     */
    public boolean canRespawn() {
        for (Location location : respawnBlocks) {
            if (location.getBlock().getType() != Material.AIR) {
                return true;
            }
        }
        return false;
    }

    public Collection<Location> getRespawnBlocks() {
        return respawnBlocks;
    }
}
