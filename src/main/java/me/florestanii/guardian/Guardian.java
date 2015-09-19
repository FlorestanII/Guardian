package me.florestanii.guardian;

import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.GuardianArenaState;
import me.florestanii.guardian.arena.specialitems.teleportpowder.TeleportPowderHandler;
import me.florestanii.guardian.listerners.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public class Guardian extends JavaPlugin {
    GuardianArena arena;

    @Override
    public void onLoad() {
        getConfig();
        getConfig().options().copyDefaults(true);

        saveConfig();

        arena = new GuardianArena(this);
    }

    @Override
    public void onEnable() {
        Iterator<String> keys = getConfig().getKeys(true).iterator();

        Location leavePos = new Location(null, 0, 0, 0);
        Location lobbySpawn = new Location(null, 0, 0, 0);
        Location redSpawn = new Location(null, 0, 0, 0);
        Location redRespawnblock1 = new Location(null, 0, 0, 0);
        Location redRespawnblock2 = new Location(null, 0, 0, 0);
        Location blueSpawn = new Location(null, 0, 0, 0);
        Location blueRespawnblock1 = new Location(null, 0, 0, 0);
        Location blueRespawnblock2 = new Location(null, 0, 0, 0);
        Location arenaMiddle = new Location(null, 0, 0, 0);

        while (keys.hasNext()) {
            String key = keys.next();
            //TODO Refactor this!
            switch (key) {
                case "arena.world":
                    arena.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;

                case "arena.leave.world":
                    leavePos.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;
                case "arena.leave.x":
                    leavePos.setX(getConfig().getDouble(key));
                    break;
                case "arena.leave.y":
                    leavePos.setY(getConfig().getDouble(key));
                    break;
                case "arena.leave.z":
                    leavePos.setZ(getConfig().getDouble(key));
                    break;
                case "arena.leave.yaw":
                    leavePos.setYaw((float) getConfig().getDouble(key));
                    break;
                case "arena.leave.pitch":
                    leavePos.setPitch((float) getConfig().getDouble(key));
                    break;

                case "arena.lobby.spawn.world":
                    lobbySpawn.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;
                case "arena.lobby.spawn.x":
                    lobbySpawn.setX(getConfig().getDouble(key));
                    break;
                case "arena.lobby.spawn.y":
                    lobbySpawn.setY(getConfig().getDouble(key));
                    System.out.println(getConfig().getDouble(key));
                    break;
                case "arena.lobby.spawn.z":
                    lobbySpawn.setZ(getConfig().getDouble(key));
                    break;
                case "arena.lobby.spawn.yaw":
                    lobbySpawn.setYaw((float) getConfig().getDouble(key));
                    break;
                case "arena.lobby.spawn.pitch":
                    lobbySpawn.setPitch((float) getConfig().getDouble(key));
                    break;


                case "arena.team.red.spawn.world":
                    redSpawn.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;
                case "arena.team.red.spawn.x":
                    redSpawn.setX(getConfig().getDouble(key));
                    break;
                case "arena.team.red.spawn.y":
                    redSpawn.setY(getConfig().getDouble(key));
                    break;
                case "arena.team.red.spawn.z":
                    redSpawn.setZ(getConfig().getDouble(key));
                    break;
                case "arena.team.red.spawn.yaw":
                    redSpawn.setYaw((float) getConfig().getDouble(key));
                    break;
                case "arena.team.red.spawn.pitch":
                    redSpawn.setPitch((float) getConfig().getDouble(key));
                    break;


                case "arena.team.red.respawnblock1.world":
                    redRespawnblock1.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;
                case "arena.team.red.respawnblock1.x":
                    redRespawnblock1.setX(getConfig().getDouble(key));
                    break;
                case "arena.team.red.respawnblock1.y":
                    redRespawnblock1.setY(getConfig().getDouble(key));
                    break;
                case "arena.team.red.respawnblock1.z":
                    redRespawnblock1.setZ(getConfig().getDouble(key));
                    break;


                case "arena.team.red.respawnblock2.world":
                    redRespawnblock2.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;
                case "arena.team.red.respawnblock2.x":
                    redRespawnblock2.setX(getConfig().getDouble(key));
                    break;
                case "arena.team.red.respawnblock2.y":
                    redRespawnblock2.setY(getConfig().getDouble(key));
                    break;
                case "arena.team.red.respawnblock2.z":
                    redRespawnblock2.setZ(getConfig().getDouble(key));
                    break;


                case "arena.team.blue.spawn.world":
                    blueSpawn.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;
                case "arena.team.blue.spawn.x":
                    blueSpawn.setX(getConfig().getDouble(key));
                    break;
                case "arena.team.blue.spawn.y":
                    blueSpawn.setY(getConfig().getDouble(key));
                    break;
                case "arena.team.blue.spawn.z":
                    blueSpawn.setZ(getConfig().getDouble(key));
                    break;
                case "arena.team.blue.spawn.yaw":
                    blueSpawn.setYaw((float) getConfig().getDouble(key));
                    break;
                case "arena.team.blue.spawn.pitch":
                    blueSpawn.setPitch((float) getConfig().getDouble(key));
                    break;


                case "arena.team.blue.respawnblock1.world":
                    blueRespawnblock1.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;
                case "arena.team.blue.respawnblock1.x":
                    blueRespawnblock1.setX(getConfig().getDouble(key));
                    break;
                case "arena.team.blue.respawnblock1.y":
                    blueRespawnblock1.setY(getConfig().getDouble(key));
                    break;
                case "arena.team.blue.respawnblock1.z":
                    blueRespawnblock1.setZ(getConfig().getDouble(key));
                    break;


                case "arena.team.blue.respawnblock2.world":
                    blueRespawnblock2.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;
                case "arena.team.blue.respawnblock2.x":
                    blueRespawnblock2.setX(getConfig().getDouble(key));
                    break;
                case "arena.team.blue.respawnblock2.y":
                    blueRespawnblock2.setY(getConfig().getDouble(key));
                    break;
                case "arena.team.blue.respawnblock2.z":
                    blueRespawnblock2.setZ(getConfig().getDouble(key));
                    break;

                case "arena.middle.world":
                    arenaMiddle.setWorld(getServer().getWorld(UUID.fromString(getConfig().getString(key))));
                    break;
                case "arena.middle.x":
                    arenaMiddle.setX(getConfig().getDouble(key));
                    break;
                case "arena.middle.y":
                    arenaMiddle.setY(getConfig().getDouble(key));
                    break;
                case "arena.middle.z":
                    arenaMiddle.setZ(getConfig().getDouble(key));
                    break;

//				case "arena.minPlayers":
//					System.out.println(arena.getLobby().getMinPlayers());
//					System.out.println(getConfig().getInt(key));
//					arena.getLobby().setMinPlayers(getConfig().getInt(key));
//					System.out.println(arena.getLobby().getMinPlayers());
//					break;
//				case "arena.maxPlayers":
//					arena.getLobby().setMaxPlayers(getConfig().getInt(key));
//					break;
//					
                case "arena.spawner.emerald.spawnerCount":
                    int emeraldSpawnerCount = getConfig().getInt("arena.spawner.emerald.spawnerCount");

                    for (int i = 0; i < emeraldSpawnerCount; i++) {

                        World world = getServer().getWorld(UUID.fromString(getConfig().getString("arena.spawner.emerald." + i + ".world")));
                        double x = getConfig().getDouble("arena.spawner.emerald." + i + ".x");
                        double y = getConfig().getDouble("arena.spawner.emerald." + i + ".y");
                        double z = getConfig().getDouble("arena.spawner.emerald." + i + ".z");

                        arena.getEmeraldSpawner().addLocation(new Location(world, x, y, z));

                    }

                    break;
                case "arena.spawner.diamond.spawnerCount":
                    int diamondSpawnerCount = getConfig().getInt("arena.spawner.diamond.spawnerCount");

                    for (int i = 0; i < diamondSpawnerCount; i++) {

                        World world = getServer().getWorld(UUID.fromString(getConfig().getString("arena.spawner.diamond." + i + ".world")));
                        double x = getConfig().getDouble("arena.spawner.diamond." + i + ".x");
                        double y = getConfig().getDouble("arena.spawner.diamond." + i + ".y");
                        double z = getConfig().getDouble("arena.spawner.diamond." + i + ".z");

                        arena.getDiamondSpawner().addLocation(new Location(world, x, y, z));

                    }

                    break;
            }
        }

        if (leavePos.getWorld() != null) arena.setLeavePos(leavePos);
        if (lobbySpawn.getWorld() != null) arena.setLobbySpawn(lobbySpawn);
        if (arenaMiddle.getWorld() != null) arena.getGuardianSpawner().setSpawn(arenaMiddle);

        if (redSpawn.getWorld() != null) arena.getTeamRed().setSpawn(redSpawn);
        arena.getTeamRed().setRespawnBlocks(Arrays.asList(redRespawnblock1, redRespawnblock2));
        if (blueSpawn.getWorld() != null) arena.getTeamBlue().setSpawn(blueSpawn);
        arena.getTeamBlue().setRespawnBlocks(Arrays.asList(blueRespawnblock1, blueRespawnblock2));

        if (arena.isArenaReady()) {
            arena.setState(GuardianArenaState.LOBBY);
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

        if (cmd.getName().equalsIgnoreCase("guardian")) {
            if (args[0].equalsIgnoreCase("join")) {
                if (arena.isArenaReady()) {
                    arena.setState(GuardianArenaState.LOBBY);
                    arena.joinPlayer(p);
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "Diese Arena ist noch nicht fertig eingerichtet. Falls du denkst dies ist ein Fehler, kontaktiere bitte einen Server Administrator!");
                }
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (arena.isPlayerInArena(p)) {
                    arena.kickPlayer(p, p.getDisplayName() + " hat das Spiel verlassen.");
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "Du befindest dich in keiner Guardian-Runde!");
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                //Spieler hilfe senden.
            } else if (args[0].equalsIgnoreCase("reload")) {
                arena.getEmeraldSpawner().getLocations().clear();
                reloadConfig();
            } else {
                p.sendMessage("Unknown command. Type /guardian help for a full list of the commands.");
            }
        } else if (cmd.getName().equalsIgnoreCase("start")) {
            if (p.hasPermission("guardian.start")) {
                if (arena.getArenaState() == GuardianArenaState.LOBBY) {
                    arena.getLobby().setCountdown(3);
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "Das Spiel befindet sich nicht in der Lobby Phase!");
                }
            } else {
                p.sendMessage(ChatColor.DARK_RED + "Du hast f�r diesen Befehl keine Rechte!");
            }
        }

        return true;
    }

    @Deprecated
    public GuardianArena getArena() {
        return arena;
    }

    public GuardianArena getArena(String name) {
        return arena;
    }

    public Collection<GuardianArena> getArenas() {
        return Arrays.asList(arena);
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
