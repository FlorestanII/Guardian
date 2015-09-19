package me.florestanii.guardian;

import java.util.Iterator;
import java.util.UUID;

import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.GuardianArenaState;
import me.florestanii.guardian.arena.shopspecialitems.TeleportPowderHandler;
import me.florestanii.guardian.listerners.BlockBreakHandler;
import me.florestanii.guardian.listerners.BlockPlaceHandler;
import me.florestanii.guardian.listerners.EntityDeathHandler;
import me.florestanii.guardian.listerners.PlayerAttackHandler;
import me.florestanii.guardian.listerners.PlayerChatHandler;
import me.florestanii.guardian.listerners.PlayerDeathHandler;
import me.florestanii.guardian.listerners.PlayerInteractHandler;
import me.florestanii.guardian.listerners.PlayerMoveHandler;
import me.florestanii.guardian.listerners.PlayerQuitHandler;
import me.florestanii.guardian.listerners.PlayerRespawnHandler;
import me.florestanii.guardian.listerners.PlayerShopInventoryClickHandler;
import net.minecraft.server.v1_8_R1.CraftingManager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Guardian extends JavaPlugin{
	
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
		
		CraftingManager.getInstance().recipes.clear();
		
		try{
			
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
			
			while(keys.hasNext()){
				String key = keys.next();
				
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
					leavePos.setYaw((float)getConfig().getDouble(key));
					break;
				case "arena.leave.pitch":
					leavePos.setPitch((float)getConfig().getDouble(key));
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
					lobbySpawn.setYaw((float)getConfig().getDouble(key));
					break;
				case "arena.lobby.spawn.pitch":
					lobbySpawn.setPitch((float)getConfig().getDouble(key));
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
					redSpawn.setYaw((float)getConfig().getDouble(key));
					break;
				case "arena.team.red.spawn.pitch":
					redSpawn.setPitch((float)getConfig().getDouble(key));
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
					blueSpawn.setYaw((float)getConfig().getDouble(key));
					break;
				case "arena.team.blue.spawn.pitch":
					blueSpawn.setPitch((float)getConfig().getDouble(key));
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
					
					for(int i = 0; i < emeraldSpawnerCount; i++){
						
						World world = getServer().getWorld(UUID.fromString(getConfig().getString("arena.spawner.emerald." + i + ".world")));
						double x = getConfig().getDouble("arena.spawner.emerald." + i + ".x");
						double y = getConfig().getDouble("arena.spawner.emerald." + i + ".y");
						double z = getConfig().getDouble("arena.spawner.emerald." + i + ".z");
						
						arena.getEmeraldSpawner().addLocation(new Location(world, x, y, z));
						
					}
					
					break;
				case "arena.spawner.diamond.spawnerCount":
					int diamondSpawnerCount = getConfig().getInt("arena.spawner.diamond.spawnerCount");
					
					for(int i = 0; i < diamondSpawnerCount; i++){
						
						World world = getServer().getWorld(UUID.fromString(getConfig().getString("arena.spawner.diamond." + i + ".world")));
						double x = getConfig().getDouble("arena.spawner.diamond." + i + ".x");
						double y = getConfig().getDouble("arena.spawner.diamond." + i + ".y");
						double z = getConfig().getDouble("arena.spawner.diamond." + i + ".z");
						
						arena.getDiamondSpawner().addLocation(new Location(world, x, y, z));
						
					}
					
					break;
					
				default:
					break;
				}
				
			}
			
			if(leavePos.getWorld() != null)arena.setLeavePos(leavePos);
			if(lobbySpawn.getWorld() != null)arena.setLobbySpawn(lobbySpawn);
			if(redSpawn.getWorld() != null)arena.getTeamRed().setSpawn(redSpawn);
			if(redRespawnblock1.getWorld() != null)arena.getTeamRed().setRespawnblock1(redRespawnblock1);
			if(redRespawnblock2.getWorld() != null)arena.getTeamRed().setRespawnblock2(redRespawnblock2);
			if(blueSpawn.getWorld() != null)arena.getTeamBlue().setSpawn(blueSpawn);
			if(blueRespawnblock1.getWorld() != null)arena.getTeamBlue().setRespawnblock1(blueRespawnblock1);
			if(blueRespawnblock2.getWorld() != null)arena.getTeamBlue().setRespawnblock2(blueRespawnblock2);
			if(arenaMiddle.getWorld() != null)arena.getGuardianSpawner().setSpawn(arenaMiddle);
			
			if(arena.isArenaReady()){
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
			
			
		}catch(Exception e){}
		
	}
	
	@Override
	public void onDisable() {
		saveConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.DARK_RED + "This plugin has no commands for the console!");
			return true;
		}
		Player p = (Player)sender;
		
		if(cmd.getName().equalsIgnoreCase("guardian")){
			try{
				
				if(args[0].equalsIgnoreCase("join")){
						
					if(arena.isArenaReady()){
						arena.setState(GuardianArenaState.LOBBY);
						arena.joinPlayer(p);	
					}else{
						p.sendMessage(ChatColor.DARK_RED + "Diese Arena ist noch nicht fertig eingerichtet. Falls du denkst dies ist ein Fehler, kontaktiere bitte einen Server Administrator!");
					}
					
				}else if(args[0].equalsIgnoreCase("leave")){
					
					if(arena.isPlayerInArena(p)){
						arena.kickPlayer(p, p.getDisplayName() + " hat das Spiel verlassen.");
					}else{
						p.sendMessage(ChatColor.DARK_RED + "Du befindest dich in keiner Guardian-Runde!");
					}
					
				}
				else if(args[0].equalsIgnoreCase("set")){
					if(!p.hasPermission("guardian.admin"))return true;
					if(args[1].equalsIgnoreCase("world")){
						arena.setWorld(p.getWorld());
						
						getConfig().set("arena.world", p.getWorld().getUID().toString());
						saveConfig();
						
						p.sendMessage("World set.");
					}else if(args[1].equalsIgnoreCase("leavePos")){
						arena.setLeavePos(p.getLocation());
						
						getConfig().set("arena.leave.world", p.getWorld().getUID().toString());
						getConfig().set("arena.leave.x", p.getLocation().getX());
						getConfig().set("arena.leave.y", p.getLocation().getY());
						getConfig().set("arena.leave.z", p.getLocation().getZ());
						getConfig().set("arena.leave.yaw", p.getLocation().getYaw());
						getConfig().set("arena.leave.pitch", p.getLocation().getPitch());
						saveConfig();
						
						p.sendMessage("LeavePos set.");
					}else if(args[1].equalsIgnoreCase("team")){
						
						if(args[2].equalsIgnoreCase("red")){
							
							if(args[3].equalsIgnoreCase("respawnblock")){
								
								if(args[4] == null){           
									if(arena.getTeamRed().getRespawnblock1() == null){
										arena.getTeamRed().setRespawnblock1(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
										getConfig().set("arena.team.red.respawnblock1.world", p.getWorld().getUID().toString());
										getConfig().set("arena.team.red.respawnblock1.x", p.getLocation().getBlockX());
										getConfig().set("arena.team.red.respawnblock1.y", p.getLocation().getBlockY());
										getConfig().set("arena.team.red.respawnblock1.z", p.getLocation().getBlockZ());
										saveConfig();
										p.sendMessage(ChatColor.RED + "Respawnblock 1 set.");
									}else if(arena.getTeamRed().getRespawnblock2() == null){
										arena.getTeamRed().setRespawnblock2(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));									
										getConfig().set("arena.team.red.respawnblock2.world", p.getWorld().getUID().toString());
										getConfig().set("arena.team.red.respawnblock2.x", p.getLocation().getBlockX());
										getConfig().set("arena.team.red.respawnblock2.y", p.getLocation().getBlockY());
										getConfig().set("arena.team.red.respawnblock2.z", p.getLocation().getBlockZ());
										saveConfig();
										p.sendMessage(ChatColor.RED + "Respawnblock 2 set.");
									}
								}else if(args[4].equals("1")){
									arena.getTeamRed().setRespawnblock1(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
									getConfig().set("arena.team.red.respawnblock1.world", p.getWorld().getUID().toString());
									getConfig().set("arena.team.red.respawnblock1.x", p.getLocation().getBlockX());
									getConfig().set("arena.team.red.respawnblock1.y", p.getLocation().getBlockY());
									getConfig().set("arena.team.red.respawnblock1.z", p.getLocation().getBlockZ());
									saveConfig();
									p.sendMessage(ChatColor.RED + "Respawnblock 1 set.");
								}else if(args[4].equals("2")){
									arena.getTeamRed().setRespawnblock2(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));									
									getConfig().set("arena.team.red.respawnblock2.world", p.getWorld().getUID().toString());
									getConfig().set("arena.team.red.respawnblock2.x", p.getLocation().getBlockX());
									getConfig().set("arena.team.red.respawnblock2.y", p.getLocation().getBlockY());
									getConfig().set("arena.team.red.respawnblock2.z", p.getLocation().getBlockZ());
									saveConfig();
									p.sendMessage(ChatColor.RED + "Respawnblock 2 set.");
								}else{
									p.sendMessage(ChatColor.DARK_RED + "Bei diesen Team sind schon beide Respawnblöcke gesetzt!");
								}
								
							}else if(args[3].equalsIgnoreCase("spawn")){
								arena.getTeamRed().setSpawn(p.getLocation());
								getConfig().set("arena.team.red.spawn.world", p.getLocation().getWorld().getUID().toString());
								getConfig().set("arena.team.red.spawn.x", p.getLocation().getX());
								getConfig().set("arena.team.red.spawn.y", p.getLocation().getY());
								getConfig().set("arena.team.red.spawn.z", p.getLocation().getZ());
								getConfig().set("arena.team.red.spawn.yaw", p.getLocation().getYaw());
								getConfig().set("arena.team.red.spawn.pitch", p.getLocation().getPitch());
								p.sendMessage(ChatColor.RED + "Spawn set.");
							}
							
						}else if(args[2].equalsIgnoreCase("blue")){
							
							if(args[3].equalsIgnoreCase("respawnblock")){
								
								if(args[4].equals("1")){
									arena.getTeamBlue().setRespawnblock1(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
									getConfig().set("arena.team.blue.respawnblock1.world", p.getWorld().getUID().toString());
									getConfig().set("arena.team.blue.respawnblock1.x", p.getLocation().getBlockX());
									getConfig().set("arena.team.blue.respawnblock1.y", p.getLocation().getBlockY());
									getConfig().set("arena.team.blue.respawnblock1.z", p.getLocation().getBlockZ());
									saveConfig();
									p.sendMessage(ChatColor.BLUE + "Respawnblock 1 set.");
								}else if(args[4].equals("2")){
									arena.getTeamBlue().setRespawnblock2(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));									
									getConfig().set("arena.team.blue.respawnblock2.world", p.getWorld().getUID().toString());
									getConfig().set("arena.team.blue.respawnblock2.x", p.getLocation().getBlockX());
									getConfig().set("arena.team.blue.respawnblock2.y", p.getLocation().getBlockY());
									getConfig().set("arena.team.blue.respawnblock2.z", p.getLocation().getBlockZ());
									saveConfig();
									p.sendMessage(ChatColor.BLUE + "Respawnblock 2 set.");
								}else if(arena.getTeamBlue().getRespawnblock1() == null){
									arena.getTeamBlue().setRespawnblock1(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));
									getConfig().set("arena.team.blue.respawnblock1.world", p.getWorld().getUID().toString());
									getConfig().set("arena.team.blue.respawnblock1.x", p.getLocation().getBlockX());
									getConfig().set("arena.team.blue.respawnblock1.y", p.getLocation().getBlockY());
									getConfig().set("arena.team.blue.respawnblock1.z", p.getLocation().getBlockZ());
									saveConfig();
									p.sendMessage(ChatColor.BLUE + "Respawnblock 1 set.");
								}else if(arena.getTeamBlue().getRespawnblock2() == null){
									arena.getTeamBlue().setRespawnblock2(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()));									
									getConfig().set("arena.team.blue.respawnblock2.world", p.getWorld().getUID().toString());
									getConfig().set("arena.team.blue.respawnblock2.x", p.getLocation().getBlockX());
									getConfig().set("arena.team.blue.respawnblock2.y", p.getLocation().getBlockY());
									getConfig().set("arena.team.blue.respawnblock2.z", p.getLocation().getBlockZ());
									saveConfig();
									p.sendMessage(ChatColor.BLUE + "Respawnblock 2 set.");
								}else{
									p.sendMessage(ChatColor.DARK_RED + "Bei diesen Team sind schon beide Respawnblöcke gesetzt!");
								}
								
							}else if(args[3].equalsIgnoreCase("spawn")){
								arena.getTeamBlue().setSpawn(p.getLocation());
								getConfig().set("arena.team.blue.spawn.world", p.getLocation().getWorld().getUID().toString());
								getConfig().set("arena.team.blue.spawn.x", p.getLocation().getX());
								getConfig().set("arena.team.blue.spawn.y", p.getLocation().getY());
								getConfig().set("arena.team.blue.spawn.z", p.getLocation().getZ());
								getConfig().set("arena.team.blue.spawn.yaw", p.getLocation().getYaw());
								getConfig().set("arena.team.blue.spawn.pitch", p.getLocation().getPitch());
								saveConfig();
								p.sendMessage(ChatColor.BLUE + "Spawn set.");
							}
							
							
						}
						
					}else if(args[1].equalsIgnoreCase("lobby")){
						if(args[2].equalsIgnoreCase("spawn")){
							arena.setLobbySpawn(p.getLocation());
							getConfig().set("arena.lobby.spawn.world", p.getWorld().getUID().toString());
							getConfig().set("arena.lobby.spawn.x", p.getLocation().getX());
							getConfig().set("arena.lobby.spawn.y", p.getLocation().getY());
							getConfig().set("arena.lobby.spawn.z", p.getLocation().getZ());
							getConfig().set("arena.lobby.spawn.yaw", p.getLocation().getYaw());
							getConfig().set("arena.lobby.spawn.pitch", p.getLocation().getPitch());
							p.sendMessage("Lobby Spawn set.");
							saveConfig();
						}
					}else if(args[1].equalsIgnoreCase("arenaMiddle")){
						arena.getGuardianSpawner().setSpawn(p.getLocation());
						getConfig().set("arena.middle.world", p.getWorld().getUID().toString());
						getConfig().set("arena.middle.x", p.getLocation().getX());
						getConfig().set("arena.middle.y", p.getLocation().getY());
						getConfig().set("arena.middle.z", p.getLocation().getZ());
						p.sendMessage("Arena Middle set.");
						saveConfig();
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
					}else if(args[1].equalsIgnoreCase("list")){
						//Spieler Liste senden.
					}else{
						p.sendMessage("Unknown args. Type \"/guardian set list\" for a full list of args");
					}
				}else if(args[0].equalsIgnoreCase("add")){
					if(!p.hasPermission("guardian.admin"))return true;
					if(args[1].equalsIgnoreCase("spawner")){
						if(args[2].equalsIgnoreCase("emerald")){
							getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".world", p.getWorld().getUID().toString());
							getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".x", p.getLocation().getBlockX());
							getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".y", p.getLocation().getBlockY());
							getConfig().set("arena.spawner.emerald." + arena.getEmeraldSpawner().getLocations().size() + ".z", p.getLocation().getBlockZ());
							arena.getEmeraldSpawner().addLocation(p.getLocation());
							getConfig().set("arena.spawner.emerald.spawnerCount", arena.getEmeraldSpawner().getLocations().size());
							saveConfig();
							p.sendMessage("Add Emeraldspawner.");
						}else if(args[2].equalsIgnoreCase("diamond")){
							getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".world", p.getWorld().getUID().toString());
							getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".x", p.getLocation().getBlockX());
							getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".y", p.getLocation().getBlockY());
							getConfig().set("arena.spawner.diamond." + arena.getDiamondSpawner().getLocations().size() + ".z", p.getLocation().getBlockZ());
							arena.getDiamondSpawner().addLocation(p.getLocation());
							getConfig().set("arena.spawner.diamond.spawnerCount", arena.getDiamondSpawner().getLocations().size());
							saveConfig();
							p.sendMessage("Add Diamondspawner.");
						}
					}
				}else if(args[0].equalsIgnoreCase("help")){
					//Spieler hilfe senden.
				}else if(args[0].equalsIgnoreCase("reload")){
					arena.getEmeraldSpawner().getLocations().clear();
					reloadConfig();
				}else{
					p.sendMessage("Unknown command. Type /guardian help for a full list of the commands.");
				}
				
			}catch(Exception e){}
		}else if(cmd.getName().equalsIgnoreCase("start")){
			if(p.hasPermission("guardian.start")){
				if(arena.getArenaState() == GuardianArenaState.LOBBY){
					arena.getLobby().setCountdown(3);
				}else{
					p.sendMessage(ChatColor.DARK_RED + "Das Spiel befindet sich nicht in der Lobby Phase!");
				}
			}else{
				p.sendMessage(ChatColor.DARK_RED + "Du hast für diesen Befehl keine Rechte!");
			}
		}
		
		return true;
	}
	public GuardianArena getArena(){
		return arena;
	}
}
