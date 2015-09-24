package me.florestanii.guardian.arena.team;

import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.config.GuardianTeamConfig;
import me.florestanii.guardian.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GuardianTeam {
    private final GuardianArena arena;
    private final String name;
    private final HashMap<UUID, GuardianPlayer> players = new HashMap<>();
    private final List<Location> respawnBlocks;
    private final Location spawn;

    ChatColor chatColor = ChatColor.WHITE;

    public GuardianTeam(GuardianArena arena, GuardianTeamConfig guardianTeamConfig) {
        this.arena = arena;
        this.name = guardianTeamConfig.getName();
        this.respawnBlocks = guardianTeamConfig.getRespawnBlocks();
        this.spawn = guardianTeamConfig.getSpawn();
        this.chatColor = guardianTeamConfig.getColor();
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

    public void removePlayer(final Player player) {
        players.remove(player.getUniqueId());
        arena.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(arena.getPlugin(), new Runnable() {

            @Override
            public void run() {
                try {
                    player.setPlayerListName(player.getName());
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

    public GuardianPlayer getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public boolean isPlayerInTeam(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public int getPlayerCount() {
        return players.size();
    }

    public ArrayList<GuardianPlayer> getPlayers() {
        return new ArrayList<GuardianPlayer>(players.values());
    }

    public void broadcastMessage(String msg) {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(this.players.values());
        for (GuardianPlayer p : players) {
            p.getBukkitPlayer().sendMessage(msg);
        }
    }

    public void broadcastSound(Sound sound) {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(this.players.values());
        for (GuardianPlayer p : players) {
            p.getBukkitPlayer().playSound(arena.getPlugin().getServer().getPlayer(p.getUniqueId()).getLocation(), sound, 3, 2);
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

    public List<Location> getRespawnBlocks() {
        return respawnBlocks;
    }
}
