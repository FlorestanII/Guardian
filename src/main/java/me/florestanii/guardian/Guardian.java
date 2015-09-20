package me.florestanii.guardian;

import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.GuardianArenaState;
import me.florestanii.guardian.arena.config.GuardianArenaConfig;
import me.florestanii.guardian.arena.specialitems.teleportpowder.TeleportPowderHandler;
import me.florestanii.guardian.listerners.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Map;

public class Guardian extends JavaPlugin {
    private Map<String, GuardianArena> arenas;

    @Override
    public void onLoad() {
        getConfig();
        getConfig().options().copyDefaults(true);

        saveConfig();
    }

    @Override
    public void onEnable() {
        Location leavePos = new Location(null, 0, 0, 0);
        Location lobbySpawn = new Location(null, 0, 0, 0);
        Location redSpawn = new Location(null, 0, 0, 0);
        Location redRespawnblock1 = new Location(null, 0, 0, 0);
        Location redRespawnblock2 = new Location(null, 0, 0, 0);
        Location blueSpawn = new Location(null, 0, 0, 0);
        Location blueRespawnblock1 = new Location(null, 0, 0, 0);
        Location blueRespawnblock2 = new Location(null, 0, 0, 0);
        Location arenaMiddle = new Location(null, 0, 0, 0);

        ConfigurationSection arenasConfig = getConfig().getConfigurationSection("arenas");
        for (String key : arenasConfig.getKeys(false)) {
            arenas.put(key, new GuardianArena(this, new GuardianArenaConfig(arenasConfig.getConfigurationSection(key))));
        }

        new BlockBreakHandler(this);
        new BlockPlaceHandler(this);
        new PlayerDeathHandler(this);
        new PlayerRespawnHandler(this);
        new PlayerQuitHandler(this);
        new PlayerChatHandler(this);
        new PlayerInteractHandler(this);
        new PlayerAttackHandler(this);
        new PlayerMoveHandler(this);
        new EntityDeathHandler(this);
        new PlayerShopInventoryClickHandler(this);

        new TeleportPowderHandler(this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "This plugin has no commands for the console!");
            return true;
        }
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("guardian") && args.length >= 1) {
            if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                GuardianArena arena = getArena(args[1]);
                if (arena != null) {
                    if (arena.getArenaState() == GuardianArenaState.LOBBY) {
                        arena.joinPlayer(p);
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "Die Guardian-Runde läuft bereits, du musst warten, bis sie vorbei ist.");
                    }
                }
            } else if (args[0].equalsIgnoreCase("leave")) {
                GuardianArena arena = getArena((Player) sender);
                if (arena.isPlayerInArena(p)) {
                    arena.kickPlayer(p, p.getDisplayName() + " hat das Spiel verlassen.");
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "Du befindest dich in keiner Guardian-Runde!");
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                //TODO Spieler hilfe senden.
            } else if (args[0].equalsIgnoreCase("reload")) {
                for (GuardianArena arena : arenas.values()) {
                    arena.getEmeraldSpawner().getLocations().clear();
                }
                reloadConfig();
            } else {
                p.sendMessage("Unknown command. Type /guardian help for a full list of the commands.");
            }
        } else if (cmd.getName().equalsIgnoreCase("start") && args.length == 1) {
            if (p.hasPermission("guardian.start")) {
                GuardianArena arena = getArena(args[0]);
                if (arena != null) {
                    if (arena.getArenaState() == GuardianArenaState.LOBBY) {
                        arena.getLobby().setCountdown(3);
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "Das Spiel befindet sich nicht in der Lobby Phase!");
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "Die angegebene Arena existiert nicht.");
                }
            } else {
                p.sendMessage(ChatColor.DARK_RED + "Du hast für diesen Befehl keine Rechte!");
            }
        }

        return true;
    }

    public GuardianArena getArena(String name) {
        return arenas.get(name);
    }

    public Collection<GuardianArena> getArenas() {
        return arenas.values();
    }

    public GuardianArena getArena(Player player) {
        for (GuardianArena arena : getArenas()) {
            if (arena.isPlayerInArena(player)) {
                return arena;
            }
        }
        return null;
    }

    public boolean isPlayerInArena(Player player) {
        return getArena(player) != null;
    }
}
