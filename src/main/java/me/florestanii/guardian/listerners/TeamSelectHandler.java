package me.florestanii.guardian.listerners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.team.GuardianTeam;
import me.florestanii.guardian.util.ColorConverter;
import me.florestanii.guardian.util.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.craften.plugins.mcguilib.text.TextBuilder;

public class TeamSelectHandler implements Listener{
	
	Guardian plugin;
	
	public TeamSelectHandler(Guardian plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		GuardianArena arena = plugin.getArena(e.getPlayer());
		
		if(arena != null){
			
			Player p = e.getPlayer();
			if(arena.getLobby().isPlayerInLobby(p)){
				if(e.getItem() == null)return;
				if(e.getItem().getType() == Material.NETHER_STAR){
					e.setCancelled(true);
					Collection<GuardianTeam> teams = arena.getTeams();
					
					Inventory inv = plugin.getServer().createInventory(p, ((int)(teams.size()/9)+1)*9, "Team Auswahl");
					for(GuardianTeam team : teams){
						ItemStackBuilder itemBuilder = ItemStackBuilder.builder().setType(Material.WOOL).setAmount(1).setDamage(ColorConverter.convertToDyeColor(team.getChatColor()).getWoolData()).setDisplayName(TextBuilder.create(team.getName()).color(team.getChatColor()).getSingleLine());
						List<String> lore = new ArrayList<String>();
						for(Player player : arena.getLobby().getAllPlayersOfPreTeamSelection(team)){
							lore.add(TextBuilder.create(player.getName()).color(ChatColor.GRAY).getSingleLine());
						}
						itemBuilder.setLore(lore);
						
						ItemStack item = itemBuilder.build();
						inv.addItem(item);
					}
					e.getPlayer().openInventory(inv);
				}
			}
			
		}
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		if(e.getInventory().getHolder() instanceof Player){
			Player p = (Player) e.getInventory().getHolder();
			GuardianArena arena = plugin.getArena(p);
			if(arena != null){
				if(e.getInventory().getTitle().equalsIgnoreCase("Team Auswahl")){
					e.setCancelled(true);
					if(e.getCurrentItem() == null)
						return;
					ItemStack item = e.getCurrentItem();
					GuardianTeam team = null;
					for(GuardianTeam t : arena.getTeams()){
						if(item.getItemMeta() == null)break;
						if(item.getItemMeta().getDisplayName().contains(t.getName())){
							team = t;
						}
					}
					if(team != null){
						
						if(arena.getLobby().getPreSelectionOfPlayer(p) != null && arena.getLobby().getPreSelectionOfPlayer(p).equals(team)){
							arena.getLobby().setPreTeamSelection(p, null);
							p.sendMessage(TextBuilder.create().append("Du hast das Team ").color(ChatColor.GRAY).append(team.getName()).color(team.getChatColor()).append(" verlassen!").color(ChatColor.GRAY).getSingleLine());
						}else if(arena.getLobby().getAllPlayersOfPreTeamSelection(team).size() < (float)arena.getLobby().getPlayerCount()/(float)arena.getTeams().size()){
							arena.getLobby().setPreTeamSelection(p, team);	
							p.sendMessage(TextBuilder.create().append("Du bist dem Team ").color(ChatColor.GRAY).append(team.getName()).color(team.getChatColor()).append(" beigetreten!").color(ChatColor.GRAY).getSingleLine());
						}else{
							p.sendMessage(TextBuilder.create("Dieses Team ist voll").color(ChatColor.DARK_RED).getSingleLine());
						}
						p.closeInventory();
					}
					
					
				}else{			
					if(arena.getLobby().isPlayerInLobby(p)){
						e.setCancelled(true);
					}
				}
			}
			
		}
	}
}
