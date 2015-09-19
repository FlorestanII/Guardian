package me.florestanii.guardian.arena.team;

import java.util.UUID;

public class GuardianPlayer {
	
	UUID uuid;
	String displayName;
	
	public GuardianPlayer(UUID uuid, String displayName){
		this.uuid = uuid;
		this.displayName = displayName;
	}
	
	public UUID getUniqueId(){
		return uuid;
	}
	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}
	public String getDisplayName(){
		return displayName;
	}
}
