package me.florestanii.guardian.arena.spawner;

import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.config.ItemSpawnerConfig;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class ItemSpawner {
    private final GuardianArena arena;
    private final Location location;
    private final int id;
    private final byte data;
    private final int delay;

    int spawnScheduler = -1;

    public ItemSpawner(GuardianArena arena, ItemSpawnerConfig config) {
        this.arena = arena;
        location = config.getLocation();
        id = config.getId();
        data = (byte) config.getData();
        delay = 20 * 8; //TODO make configurable
    }

    public void startSpawner() {
        spawnScheduler = arena.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(arena.getPlugin(), new Runnable() {
            @Override
            public void run() {
                location.getWorld().dropItemNaturally(location, new ItemStack(id, 1, data));
            }
        }, 0, delay);
    }

    public void stopSpawner() {
        if (spawnScheduler == -1) return;
        arena.getPlugin().getServer().getScheduler().cancelTask(spawnScheduler);
        spawnScheduler = -1;
    }
}
