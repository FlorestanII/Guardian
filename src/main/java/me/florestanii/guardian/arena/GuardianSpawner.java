package me.florestanii.guardian.arena;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GuardianSpawner {
    private final GuardianArena arena;
    private final Location spawn;
    private final int delay;
    private int scheduler = -1;

    public GuardianSpawner(GuardianArena arena, int delay, Location spawn) {
        this.arena = arena;
        this.delay = delay;
        this.spawn = spawn;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void startSpawner() {
        if (scheduler == -1) {
            scheduler = arena.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(arena.getPlugin(), new Runnable() {

                @Override
                public void run() {
                    if (spawn != null) {
                        IronGolem irongolem = (IronGolem) spawn.getWorld().spawnEntity(spawn, EntityType.IRON_GOLEM);
                        irongolem.setCustomName("Guardian");
                        irongolem.setCustomNameVisible(false);
                        irongolem.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 1000000, 4));

                    }

                }
            }, 0, delay);

        }
    }

    public void stopSpawner() {
        if (scheduler != -1) {
            arena.getPlugin().getServer().getScheduler().cancelTask(scheduler);
            scheduler = -1;
        }
    }
}
