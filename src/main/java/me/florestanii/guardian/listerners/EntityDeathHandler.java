package me.florestanii.guardian.listerners;

import me.florestanii.guardian.Guardian;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDeathHandler implements Listener{

	Guardian plugin;
	
	public EntityDeathHandler(Guardian plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onItemDrop(EntityDeathEvent e){
		Player killer = e.getEntity().getKiller();
		if(killer != null){
			if(plugin.getArena().isPlayerInArena(killer)){
				if(e.getEntityType() == EntityType.IRON_GOLEM){
					e.getDrops().clear();
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.NETHER_STAR, 1));
				}else{
					e.getDrops().clear();
				}
			}
		}
	}
}
