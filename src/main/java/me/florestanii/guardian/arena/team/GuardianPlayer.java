package me.florestanii.guardian.arena.team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GuardianPlayer {
    private UUID uuid;
    private String displayName;

    public GuardianPlayer(UUID uuid, String displayName) {
        this.uuid = uuid;
        this.displayName = displayName;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
