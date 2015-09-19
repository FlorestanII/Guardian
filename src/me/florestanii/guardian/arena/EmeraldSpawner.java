package me.florestanii.guardian.arena;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EmeraldSpawner {
	
	GuardianArena arena;
	
	final int delay;
	
	int spawnScheduler = -1;
	
	ArrayList<Location> spawnLocations = new ArrayList<Location>();
	
	public EmeraldSpawner(GuardianArena arena, int delayInTicks){
		this.arena = arena;
		this.delay = delayInTicks;
	}
	
	public void startSpawner(){
		spawnScheduler = arena.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(arena.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				
				for(Location loc : spawnLocations){
					
					loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.EMERALD, 1));
					
				}
				
			}
			
		}, 0, delay);
	}
	public void stopSpawner(){
		if(spawnScheduler == -1)return;
		arena.getPlugin().getServer().getScheduler().cancelTask(spawnScheduler);
		spawnScheduler = -1;
	}
	
	public ArrayList<Location> getLocations(){
		return spawnLocations;
	}
	public void addLocation(Location loc){
		spawnLocations.add(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
	}
	public void removeLocation(Location loc){
		spawnLocations.remove(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
	}
}
