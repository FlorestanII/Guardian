package me.florestanii.guardian.arena.shopspecialitems;

import me.florestanii.guardian.Guardian;

import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;

public class TeleportPowderCountdown {

	Guardian plugin;
	
	int lenght;
	int countdown = lenght;
	
	int scheduler = -1;
	
	Runnable finishAction;
	
	Player p;
		
	public TeleportPowderCountdown(Guardian plugin, int lenght, Player p, Runnable finishAction){
		this.plugin = plugin;
		this.lenght = lenght;
		this.countdown = lenght;
		this.finishAction = finishAction;
		this.p = p;
	}
	public void stop(){
		plugin.getServer().getScheduler().cancelTask(scheduler);
		countdown = lenght;
		scheduler = -1;
		p.setLevel(0);
	}
	public void start(){
		scheduler = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				
				if(countdown != 0){
					p.setLevel(countdown);
					p.playNote(p.getLocation(), Instrument.BASS_GUITAR, new Note(10));
					p.sendMessage(ChatColor.YELLOW + "Du wirst in " + ChatColor.RED + countdown + ChatColor.YELLOW + " teleportiert!");
					countdown--;
				}else{
					stop();
					finishAction.run();
					p.setLevel(0);
					p.playNote(p.getLocation(), Instrument.BASS_GUITAR, new Note(20));
					TeleportPowder.stopCountdownForPlayer(p.getUniqueId());
				}
				
			}
		}, 0, 20);
	}
	public Player getPlayer(){
		return p;
	}
}
