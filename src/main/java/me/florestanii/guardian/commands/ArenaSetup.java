package me.florestanii.guardian.commands;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;

public class ArenaSetup { //TODO WIP
    private final GuardianArena arena;
    private ConfigurationSection config;

    public ArenaSetup(Guardian plugin) {
        this.arena = new GuardianArena(plugin);
        this.config = new MemoryConfiguration();
    }

    public boolean onCommand(Player p, String[] args) {
        if (args[0].equalsIgnoreCase("set")) {
            if (!p.hasPermission("guardian.admin")) return true;
            if (args[1].equalsIgnoreCase("world")) {
                arena.setWorld(p.getWorld());

                getConfig().set("arena.world", p.getWorld().getUID().toString());

                p.sendMessage("World set.");
            } else if (args[1].equalsIgnoreCase("leavePos")) {
                arena.setLeavePos(p.getLocation());

                getConfig().set("arena.leave.world", p.getWorld().getUID().toString());
                getConfig().set("arena.leave.x", p.getLocation().getX());
                getConfig().set("arena.leave.y", p.getLocation().getY());
                getConfig().set("arena.leave.z", p.getLocation().getZ());
                getConfig().set("arena.leave.yaw", p.getLocation().getYaw());
                getConfig().set("arena.leave.pitch", p.getLocation().getPitch());

                p.sendMessage("LeavePos set.");
            } else if (args[1].equalsIgnoreCase("team")) {

                if (args[2].equalsIgnoreCase("red")) {

                    if (args[3].equalsIgnoreCase("respawnblock")) {

//                        if (args[4] == null) {
//                            if (arena.getTeamRed().getRespawnblock1() == null) {
//                                arena.getTeamRed().setRespawnblock1(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
//                                getConfig().set("arena.team.red.respawnblock1.world", p.getWorld().getUID().toString());
//                                getConfig().set("arena.team.red.respawnblock1.x", p.getLocation().getBlockX());
//                                getConfig().set("arena.team.red.respawnblock1.y", p.getLocation().getBlockY());
//                                getConfig().set("arena.team.red.respawnblock1.z", p.getLocation().getBlockZ());
//                                p.sendMessage(ChatColor.RED + "Respawnblock 1 set.");
//                            } else if (arena.getTeamRed().getRespawnblock2() == null) {
//                                arena.getTeamRed().setRespawnblock2(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
//                                getConfig().set("arena.team.red.respawnblock2.world", p.getWorld().getUID().toString());
//                                getConfig().set("arena.team.red.respawnblock2.x", p.getLocation().getBlockX());
//                                getConfig().set("arena.team.red.respawnblock2.y", p.getLocation().getBlockY());
//                                getConfig().set("arena.team.red.respawnblock2.z", p.getLocation().getBlockZ());
//                                p.sendMessage(ChatColor.RED + "Respawnblock 2 set.");
//                            }
//                        } else if (args[4].equals("1")) {
//                            arena.getTeamRed().setRespawnblock1(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
//                            getConfig().set("arena.team.red.respawnblock1.world", p.getWorld().getUID().toString());
//                            getConfig().set("arena.team.red.respawnblock1.x", p.getLocation().getBlockX());
//                            getConfig().set("arena.team.red.respawnblock1.y", p.getLocation().getBlockY());
//                            getConfig().set("arena.team.red.respawnblock1.z", p.getLocation().getBlockZ());
//                            p.sendMessage(ChatColor.RED + "Respawnblock 1 set.");
//                        } else if (args[4].equals("2")) {
//                            arena.getTeamRed().setRespawnblock2(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
//                            getConfig().set("arena.team.red.respawnblock2.world", p.getWorld().getUID().toString());
//                            getConfig().set("arena.team.red.respawnblock2.x", p.getLocation().getBlockX());
//                            getConfig().set("arena.team.red.respawnblock2.y", p.getLocation().getBlockY());
//                            getConfig().set("arena.team.red.respawnblock2.z", p.getLocation().getBlockZ());
//                            p.sendMessage(ChatColor.RED + "Respawnblock 2 set.");
//                        } else {
//                            p.sendMessage(ChatColor.DARK_RED + "Bei diesen Team sind schon beide Respawnbl�cke gesetzt!");
//                        }

                    } else if (args[3].equalsIgnoreCase("spawn")) {
                        arena.getTeamRed().setSpawn(p.getLocation());
                        getConfig().set("arena.team.red.spawn.world", p.getLocation().getWorld().getUID().toString());
                        getConfig().set("arena.team.red.spawn.x", p.getLocation().getX());
                        getConfig().set("arena.team.red.spawn.y", p.getLocation().getY());
                        getConfig().set("arena.team.red.spawn.z", p.getLocation().getZ());
                        getConfig().set("arena.team.red.spawn.yaw", p.getLocation().getYaw());
                        getConfig().set("arena.team.red.spawn.pitch", p.getLocation().getPitch());
                        p.sendMessage(ChatColor.RED + "Spawn set.");
                    }

                } else if (args[2].equalsIgnoreCase("blue")) {

                    if (args[3].equalsIgnoreCase("respawnblock")) {

//                        if (args[4].equals("1")) {
//                            arena.getTeamBlue().setRespawnblock1(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
//                            getConfig().set("arena.team.blue.respawnblock1.world", p.getWorld().getUID().toString());
//                            getConfig().set("arena.team.blue.respawnblock1.x", p.getLocation().getBlockX());
//                            getConfig().set("arena.team.blue.respawnblock1.y", p.getLocation().getBlockY());
//                            getConfig().set("arena.team.blue.respawnblock1.z", p.getLocation().getBlockZ());
//                            p.sendMessage(ChatColor.BLUE + "Respawnblock 1 set.");
//                        } else if (args[4].equals("2")) {
//                            arena.getTeamBlue().setRespawnblock2(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
//                            getConfig().set("arena.team.blue.respawnblock2.world", p.getWorld().getUID().toString());
//                            getConfig().set("arena.team.blue.respawnblock2.x", p.getLocation().getBlockX());
//                            getConfig().set("arena.team.blue.respawnblock2.y", p.getLocation().getBlockY());
//                            getConfig().set("arena.team.blue.respawnblock2.z", p.getLocation().getBlockZ());
//                            p.sendMessage(ChatColor.BLUE + "Respawnblock 2 set.");
//                        } else if (arena.getTeamBlue().getRespawnblock1() == null) {
//                            arena.getTeamBlue().setRespawnblock1(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
//                            getConfig().set("arena.team.blue.respawnblock1.world", p.getWorld().getUID().toString());
//                            getConfig().set("arena.team.blue.respawnblock1.x", p.getLocation().getBlockX());
//                            getConfig().set("arena.team.blue.respawnblock1.y", p.getLocation().getBlockY());
//                            getConfig().set("arena.team.blue.respawnblock1.z", p.getLocation().getBlockZ());
//                            p.sendMessage(ChatColor.BLUE + "Respawnblock 1 set.");
//                        } else if (arena.getTeamBlue().getRespawnblock2() == null) {
//                            arena.getTeamBlue().setRespawnblock2(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
//                            getConfig().set("arena.team.blue.respawnblock2.world", p.getWorld().getUID().toString());
//                            getConfig().set("arena.team.blue.respawnblock2.x", p.getLocation().getBlockX());
//                            getConfig().set("arena.team.blue.respawnblock2.y", p.getLocation().getBlockY());
//                            getConfig().set("arena.team.blue.respawnblock2.z", p.getLocation().getBlockZ());
//                            p.sendMessage(ChatColor.BLUE + "Respawnblock 2 set.");
//                        } else {
//                            p.sendMessage(ChatColor.DARK_RED + "Bei diesen Team sind schon beide Respawnbl�cke gesetzt!");
//                        }

                    } else if (args[3].equalsIgnoreCase("spawn")) {
                        arena.getTeamBlue().setSpawn(p.getLocation());
                        getConfig().set("arena.team.blue.spawn.world", p.getLocation().getWorld().getUID().toString());
                        getConfig().set("arena.team.blue.spawn.x", p.getLocation().getX());
                        getConfig().set("arena.team.blue.spawn.y", p.getLocation().getY());
                        getConfig().set("arena.team.blue.spawn.z", p.getLocation().getZ());
                        getConfig().set("arena.team.blue.spawn.yaw", p.getLocation().getYaw());
                        getConfig().set("arena.team.blue.spawn.pitch", p.getLocation().getPitch());
                        p.sendMessage(ChatColor.BLUE + "Spawn set.");
                    }


                }

            } else if (args[1].equalsIgnoreCase("lobby")) {
                if (args[2].equalsIgnoreCase("spawn")) {
                    arena.setLobbySpawn(p.getLocation());
                    getConfig().set("arena.lobby.spawn.world", p.getWorld().getUID().toString());
                    getConfig().set("arena.lobby.spawn.x", p.getLocation().getX());
                    getConfig().set("arena.lobby.spawn.y", p.getLocation().getY());
                    getConfig().set("arena.lobby.spawn.z", p.getLocation().getZ());
                    getConfig().set("arena.lobby.spawn.yaw", p.getLocation().getYaw());
                    getConfig().set("arena.lobby.spawn.pitch", p.getLocation().getPitch());
                    p.sendMessage("Lobby Spawn set.");
                }
            } else if (args[1].equalsIgnoreCase("arenaMiddle")) {
                arena.getGuardianSpawner().setSpawn(p.getLocation());
                getConfig().set("arena.middle.world", p.getWorld().getUID().toString());
                getConfig().set("arena.middle.x", p.getLocation().getX());
                getConfig().set("arena.middle.y", p.getLocation().getY());
                getConfig().set("arena.middle.z", p.getLocation().getZ());
                p.sendMessage("Arena Middle set.");
//					}else if(args[1].equalsIgnoreCase("minplayers")){
//						try{
//							arena.getLobby().setMinPlayers(Integer.parseInt(args[2]));
//							getConfig().set("arena.minPlayers", args[2]);
//							saveConfig();
//							p.sendMessage("Set MinPlayers to " + args[2]);
//						}catch(Exception e){
//							p.sendMessage("Unknow Number.");
//						}
//					}else if(args[1].equalsIgnoreCase("maxplayers")){
//						try{
//							arena.getLobby().setMaxPlayers(Integer.parseInt(args[2]));
//							getConfig().set("arena.maxPlayers", args[2]);
//							saveConfig();
//							p.sendMessage("Set MaxPlayers to " + args[2]);
//						}catch(Exception e){
//							p.sendMessage("Unknow Number.");
//						}
            } else if (args[1].equalsIgnoreCase("list")) {
                //Spieler Liste senden.
            } else {
                p.sendMessage("Unknown args. Type \"/guardian set list\" for a full list of args");
            }
        } else if (args[0].equalsIgnoreCase("add")) {
            if (!p.hasPermission("guardian.admin")) return true;
            if (args[1].equalsIgnoreCase("spawner")) {
                if (args[2].equalsIgnoreCase("emerald")) {
                    getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".world", p.getWorld().getUID().toString());
                    getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".x", p.getLocation().getBlockX());
                    getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".y", p.getLocation().getBlockY());
                    getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".z", p.getLocation().getBlockZ());
                    arena.getEmeraldSpawner().addLocation(p.getLocation());
                    getConfig().set("arena.spawner.emerald.spawnerCount", arena.getEmeraldSpawner().getLocations().size());
                    p.sendMessage("Add Emeraldspawner.");
                } else if (args[2].equalsIgnoreCase("diamond")) {
                    getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".world", p.getWorld().getUID().toString());
                    getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".x", p.getLocation().getBlockX());
                    getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".y", p.getLocation().getBlockY());
                    getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".z", p.getLocation().getBlockZ());
                    arena.getDiamondSpawner().addLocation(p.getLocation());
                    getConfig().set("arena.spawner.diamond.spawnerCount", arena.getDiamondSpawner().getLocations().size());
                    p.sendMessage("Add Diamondspawner.");
                }
            }
        }

        return true;
    }

    public ConfigurationSection getConfig() {
        return config;
    }
}
