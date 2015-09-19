package me.florestanii.guardian.arena.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import me.florestanii.guardian.Util;
import me.florestanii.guardian.arena.GuardianArena;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GuardianTeam {
	
	GuardianArena arena;
	
	String name;
	
	HashMap<UUID, GuardianPlayer> players = new HashMap<UUID, GuardianPlayer>();
	
	Location respawnblock1;
	Location respawnblock2;
	
	Location spawn;
	
	ChatColor chatColor = ChatColor.WHITE;
	
	public GuardianTeam(GuardianArena arena, Location spawn, String name){
		this.arena = arena;
		this.spawn = spawn;
		this.name = name;
	}
	
	public GuardianTeam(GuardianArena arena, String name) {
		this.arena = arena;
		this.name = name;
	}
	
	public void setSpawn(Location spawn){
		this.spawn = spawn;
	}
	public ChatColor getChatColor(){
		return chatColor;
	}
	public void setChatColor(ChatColor chatColor){
		this.chatColor = chatColor;
	}
	public void joinPlayer(GuardianPlayer player){
		players.put(player.getUniqueId(), player);
		arena.getPlugin().getServer().getPlayer(player.getUniqueId()).teleport(spawn);
		arena.getPlugin().getServer().getPlayer(player.getUniqueId()).setPlayerListName(chatColor + player.getDisplayName());
		Util.setTagColor(arena.getPlugin(), arena.getPlugin().getServer().getPlayer(player.getUniqueId()), chatColor);
	}
	public void leftPlayer(GuardianPlayer player){
		players.remove(player.getUniqueId());
		arena.getPlugin().getServer().getPlayer(player.getUniqueId()).setPlayerListName(player.getDisplayName());
	}
	public void leftPlayer(final UUID uuid){
		players.remove(uuid);
		arena.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(arena.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				try{
					arena.getPlugin().getServer().getPlayer(uuid).setPlayerListName(arena.getPlugin().getServer().getPlayer(uuid).getDisplayName());		
				}catch(Exception e){}
			}
		}, 20);
	}
	
	public String getName(){
		return name;
	}
	public void kickAllPlayers(){
		ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(
				this.players.values());
		for (GuardianPlayer p : players) {
			arena.kickPlayer(arena.getPlugin().getServer().getPlayer(p.getUniqueId()), null);
		}
	}
	public boolean isOwnRespawnblock(Location loc){
		if(respawnblock1.equals(loc) || respawnblock2.equals(loc)){
			return true;
		}else{
			return false;
		}
	}
	public GuardianPlayer getPlayer(UUID uuid){
		return players.get(uuid);
	}
	
	public boolean isPlayerInTeam(Player player){
		return players.containsKey(player.getUniqueId());
	}
	public boolean isPlayerInTeam(UUID uuid){
		return players.containsKey(uuid);
	}
	public int getPlayerCount(){
		return players.size();
	}
	public ArrayList<GuardianPlayer> getPlayers(){
		return new ArrayList<GuardianPlayer>(players.values());
	}
	public Location getRespawnblock1(){
		return respawnblock1;
	}
	public Location getRespawnblock2(){
		return respawnblock2;
	}
	public void setRespawnblock1(Location location){
		this.respawnblock1 = location;
	}
	public void setRespawnblock2(Location location){
		this.respawnblock2 = location;
	}
	public void broadcastMessage(String msg){
		ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(this.players.values());
		for(GuardianPlayer p : players){
			arena.getPlugin().getServer().getPlayer(p.getUniqueId()).sendMessage(msg);
		}
	}
	public void broadcastSound(Sound sound){
		ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(this.players.values());
		for(GuardianPlayer p : players){
			arena.getPlugin().getServer().getPlayer(p.getUniqueId()).playSound(arena.getPlugin().getServer().getPlayer(p.getUniqueId()).getLocation(), sound, 3, 2);
		}
	}
	public Location getSpawn(){
		return spawn;
	}
	public boolean hasAlLeastOneRespawnblock(){
		if(respawnblock1.getBlock().getType() == Material.DIAMOND_BLOCK || respawnblock2.getBlock().getType() == Material.DIAMOND_BLOCK)
			return true;
		else 
			return false;
	}
	public boolean isReady(){
		return arena != null && chatColor != null && name != null && respawnblock1 != null && respawnblock2 != null && spawn != null;
	}
}
