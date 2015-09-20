package me.florestanii.guardian.commands;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.config.GuardianArenaConfig;
import org.bukkit.entity.Player;

public class ArenaSetup { //TODO WIP
    private final GuardianArenaConfig arena;

    public ArenaSetup(Guardian plugin) {
        this.arena = new GuardianArenaConfig();
    }

    public boolean onCommand(Player p, String[] args) {
        if (args[0].equalsIgnoreCase("set")) {
            if (!p.hasPermission("guardian.admin")) return true;
            if (args[1].equalsIgnoreCase("world")) {
                arena.setWorld(p.getWorld());
                p.sendMessage("World set.");
            } else if (args[1].equalsIgnoreCase("leavePos")) {
                arena.setLeaveLocation(p.getLocation());
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
//                        arena.getTeamRed().setSpawn(p.getLocation());
//                        getConfig().set("arena.team.red.spawn.world", p.getLocation().getWorld().getUID().toString());
//                        getConfig().set("arena.team.red.spawn.x", p.getLocation().getX());
//                        getConfig().set("arena.team.red.spawn.y", p.getLocation().getY());
//                        getConfig().set("arena.team.red.spawn.z", p.getLocation().getZ());
//                        getConfig().set("arena.team.red.spawn.yaw", p.getLocation().getYaw());
//                        getConfig().set("arena.team.red.spawn.pitch", p.getLocation().getPitch());
//                        p.sendMessage(ChatColor.RED + "Spawn set.");
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
//                        arena.getTeamBlue().setSpawn(p.getLocation());
//                        getConfig().set("arena.team.blue.spawn.world", p.getLocation().getWorld().getUID().toString());
//                        getConfig().set("arena.team.blue.spawn.x", p.getLocation().getX());
//                        getConfig().set("arena.team.blue.spawn.y", p.getLocation().getY());
//                        getConfig().set("arena.team.blue.spawn.z", p.getLocation().getZ());
//                        getConfig().set("arena.team.blue.spawn.yaw", p.getLocation().getYaw());
//                        getConfig().set("arena.team.blue.spawn.pitch", p.getLocation().getPitch());
//                        p.sendMessage(ChatColor.BLUE + "Spawn set.");
                    }


                }

            } else if (args[1].equalsIgnoreCase("lobby")) {
                if (args[2].equalsIgnoreCase("spawn")) {
                    arena.setLobbyLocation(p.getLocation());
                    p.sendMessage("Lobby set.");
                }
            } else if (args[1].equalsIgnoreCase("arenaMiddle")) {
                arena.setArenaMiddle(p.getLocation());
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
//            if (args[1].equalsIgnoreCase("spawner")) {
//                if (args[2].equalsIgnoreCase("emerald")) {
//                    getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".world", p.getWorld().getUID().toString());
//                    getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".x", p.getLocation().getBlockX());
//                    getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".y", p.getLocation().getBlockY());
//                    getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".z", p.getLocation().getBlockZ());
//                    arena.getEmeraldSpawner().addLocation(p.getLocation());
//                    getConfig().set("arena.spawner.emerald.spawnerCount", arena.getEmeraldSpawner().getLocations().size());
//                    p.sendMessage("Add Emeraldspawner.");
//                } else if (args[2].equalsIgnoreCase("diamond")) {
//                    getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".world", p.getWorld().getUID().toString());
//                    getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".x", p.getLocation().getBlockX());
//                    getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".y", p.getLocation().getBlockY());
//                    getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".z", p.getLocation().getBlockZ());
//                    arena.getDiamondSpawner().addLocation(p.getLocation());
//                    getConfig().set("arena.spawner.diamond.spawnerCount", arena.getDiamondSpawner().getLocations().size());
//                    p.sendMessage("Add Diamondspawner.");
//                }
//            }
        }

        return true;
    }
}
