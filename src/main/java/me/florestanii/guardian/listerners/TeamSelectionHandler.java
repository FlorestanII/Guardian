package me.florestanii.guardian.listerners;

import de.craften.plugins.mcguilib.text.TextBuilder;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TeamSelectionHandler implements Listener {
    private final Guardian plugin;

    public TeamSelectionHandler(Guardian plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        GuardianArena arena = plugin.getArena(e.getPlayer());

        if (arena != null) {
            Player p = e.getPlayer();
            if (arena.getLobby().isPlayerInLobby(p)) {
                if (e.getItem() != null && e.getItem().getType() == Material.NETHER_STAR) {
                    e.setCancelled(true);
                    Collection<GuardianTeam> teams = arena.getTeams();

                    Inventory inv = plugin.getServer().createInventory(p, ((int) (teams.size() / 9) + 1) * 9, "Teamauswahl");
                    for (GuardianTeam team : teams) {
                        ItemStackBuilder itemBuilder = ItemStackBuilder.builder()
                                .setType(Material.WOOL)
                                .setAmount(1)
                                .setDamage(ColorConverter.convertToDyeColor(team.getChatColor()).getWoolData())
                                .setDisplayName(TextBuilder.create(team.getName()).color(team.getChatColor()).getSingleLine());
                        List<String> lore = new ArrayList<String>();
                        for (Player player : arena.getLobby().getAllPlayersOfPreTeamSelection(team)) {
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
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof Player) {
            Player p = (Player) e.getInventory().getHolder();
            GuardianArena arena = plugin.getArena(p);
            if (arena != null) {
                if (e.getInventory().getTitle().equalsIgnoreCase("Teamauswahl")) {
                    e.setCancelled(true);
                    if (e.getCurrentItem() == null)
                        return;
                    ItemStack item = e.getCurrentItem();
                    GuardianTeam team = null;
                    for (GuardianTeam t : arena.getTeams()) {
                        if (item.getItemMeta() != null && item.getItemMeta().getDisplayName().contains(t.getName())) {
                            team = t;
                            break;
                        }
                    }
                    if (team != null) {
                        if (arena.getLobby().getPreSelectionOfPlayer(p) != null && arena.getLobby().getPreSelectionOfPlayer(p).equals(team)) {
                            arena.getLobby().removePreselectedTeam(p);
                            TextBuilder.create("Du hast das Team ").gray()
                                    .append(team.getName()).color(team.getChatColor())
                                    .append(" verlassen!").gray()
                                    .sendTo(p);
                        } else if (arena.getLobby().getAllPlayersOfPreTeamSelection(team).size() < (float) arena.getLobby().getPlayerCount() / (float) arena.getTeams().size()) {
                            arena.getLobby().preselectTeam(p, team);
                            TextBuilder.create("Du bist dem Team ").gray()
                                    .append(team.getName()).color(team.getChatColor())
                                    .append(" beigetreten!").gray()
                                    .sendTo(p);
                        } else {
                            TextBuilder.create("Dieses Team ist schon voll.").darkRed().sendTo(p);
                        }
                        p.closeInventory();
                    }
                } else if (arena.getLobby().isPlayerInLobby(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
