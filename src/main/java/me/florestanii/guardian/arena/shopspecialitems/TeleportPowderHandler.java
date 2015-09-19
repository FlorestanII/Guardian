package me.florestanii.guardian.arena.shopspecialitems;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.Util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TeleportPowderHandler implements Listener{

	Guardian plugin;
	
	public TeleportPowderHandler(Guardian plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
			if(plugin.getArena().isPlayerInArena(e.getPlayer())){
				if(e.getItem() == null)return;
				if(e.getItem().getType() == Material.SULPHUR){
					if(e.getItem().getAmount() >= 1){
						if(!TeleportPowder.hasPlayerACountdown(e.getPlayer().getUniqueId())){
							
							e.getPlayer().sendMessage(ChatColor.YELLOW + "Bewege dich nun nicht mehr, sonst wird der Teleportations-Vorgang abgebrochen!");
							TeleportPowder.startCountdownForPlayer(new TeleportPowderCountdown(plugin, 6, e.getPlayer(), new Runnable() {
								
								@Override
								public void run() {
									e.getPlayer().teleport(plugin.getArena().getTeamOfPlayer(e.getPlayer().getUniqueId()).getSpawn());
									Util.removeInventoryItems(e.getPlayer().getInventory(), Material.SULPHUR, 1);
								}
								
							}));
							
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		if(TeleportPowder.hasPlayerACountdown(e.getPlayer().getUniqueId())){
			if(new Location(e.getFrom().getWorld(), e.getFrom().getX(), e.getFrom().getY(), e.getFrom().getZ()).equals(new Location(e.getTo().getWorld(), e.getTo().getX(), e.getTo().getY(), e.getTo().getZ()))){
				
			}else{
				e.getPlayer().sendMessage(ChatColor.RED + "Die Telepotation wurde abgebrochen!");
				TeleportPowder.stopCountdownForPlayer(e.getPlayer().getUniqueId());
			}
		}
	}
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			if(TeleportPowder.hasPlayerACountdown(p.getUniqueId())){
				p.sendMessage(ChatColor.RED + "Die Telepotation wurde abgebrochen!");
				TeleportPowder.stopCountdownForPlayer(p.getUniqueId());
			}
		}
	}
	
}
