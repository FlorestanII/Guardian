package me.florestanii.guardian.arena;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.config.GuardianArenaConfig;
import me.florestanii.guardian.arena.config.GuardianTeamConfig;
import me.florestanii.guardian.arena.team.GuardianPlayer;
import me.florestanii.guardian.arena.team.GuardianTeam;
import me.florestanii.guardian.util.ColorConverter;
import me.florestanii.guardian.util.Util;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GuardianArena {
    private final Guardian plugin;
    private final World world;
    private final Location leavePos;
    private final GuardianLobby lobby;
    private final Map<String, GuardianTeam> teams;
    private GuardianArenaState state = GuardianArenaState.LOBBY;
    EmeraldSpawner emeraldSpawner;
    DiamondSpawner diamondSpawner;
    GuardianSpawner guardianSpawner;

    int playerCount = 0;
    int endCountdownScheduler = -1;
    int endStartCountdown = 10;
    int endCountdown = endStartCountdown;

    public GuardianArena(Guardian plugin, GuardianArenaConfig config) {
        this.plugin = plugin;

        teams = new HashMap<>();
        for (Map.Entry<String, GuardianTeamConfig> teamConfig : config.getTeams().entrySet()) {
            teams.put(teamConfig.getKey(), new GuardianTeam(this, teamConfig.getValue()));
        }

        lobby = new GuardianLobby(this, config.getLobbyLocation(), 8, 2);
        leavePos = config.getLeaveLocation();

        emeraldSpawner = new EmeraldSpawner(this, (int) (20 * 2f));
        diamondSpawner = new DiamondSpawner(this, (int) (20 * 45f));

        guardianSpawner = new GuardianSpawner(this, (int) (20 * 120));

        this.world = config.getWorld();

    }

    public World getWorld() {
        return world;
    }

    public void joinPlayer(Player player) {
        if (lobby.isFull()) {
            player.sendMessage(ChatColor.DARK_RED + "Diese Arena ist schon voll!");
            return;
        } else {

            if (state != GuardianArenaState.LOBBY) {
                player.sendMessage("Du kannst in diese Arena nicht rein, da sie schon läuft oder offline ist");
                return;
            }

            playerCount++;
            lobby.joinPlayer(new GuardianPlayer(player.getUniqueId(), player.getDisplayName()));
            player.getInventory().clear();
            player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
            player.getInventory().setHeldItemSlot(0);
            Util.healPlayer(player);
        }
    }

    public void leavePlayer(final Player p) {
        if (isPlayerInArena(p)) {
            playerCount--;

            if (lobby.isPlayerInLobby(p)) {
                lobby.leftPlayer(p.getUniqueId());
            } else {
                final List<GuardianTeam> rivalTeams = getRivalTeamsOfPlayer(p);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                    @Override
                    public void run() {
                        //player has left and thus the rival team wins if there is only one team left
                        if (rivalTeams.size() == 1) {
                            GuardianTeam winner = rivalTeams.get(0);
                            broadcastMessage(ChatColor.GREEN + "Team " + winner.getChatColor() + winner.getName() + ChatColor.GREEN + " hat gewonnen!");
                            broadcastSound(Sound.FIREWORK_LAUNCH);
                            startEndCountdown();

                        }
                    }
                }, 25);

                getTeamOfPlayer(p).removePlayer(p);
            }

            p.getInventory().clear();
            p.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
            p.teleport(leavePos);
            p.setPlayerListName(p.getDisplayName());
            Util.setTagColor(plugin, p, ChatColor.WHITE);
            Util.healPlayer(p);

            if (playerCount == 0) {
                resetArena();
            }

        } else {
            p.sendMessage(ChatColor.DARK_RED + "Du befindest dich in keiner Guardian-Runde!");
        }
    }

    public void joinAsSpectator(Player player) {

    }

    public void givePlayerStartKit(Player p) {
        givePlayerLeatherArmor(p);
        p.getInventory().setHeldItemSlot(0);
        p.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
    }

    public void givePlayerLeatherArmor(Player player) {
        Color color = ColorConverter.convertToColor(getTeamOfPlayer(player).getChatColor());
        player.getInventory().setHelmet(Util.getColeredLeatherArmor(Material.LEATHER_HELMET, color));
        player.getInventory().setChestplate(Util.getColeredLeatherArmor(Material.LEATHER_CHESTPLATE, color));
        player.getInventory().setLeggings(Util.getColeredLeatherArmor(Material.LEATHER_LEGGINGS, color));
        player.getInventory().setBoots(Util.getColeredLeatherArmor(Material.LEATHER_BOOTS, color));
    }

    public void startEndCountdown() {
        if (endCountdownScheduler == -1) {
            endCountdownScheduler = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

                @Override
                public void run() {

                    if (endCountdown != 0) {

                        if (endCountdown <= 5) {
                            broadcastMessage(ChatColor.RED + "Die Arena wird in " + ChatColor.YELLOW + endCountdown + ChatColor.RED + " Sekunden neu gestartet!");
                            System.out.println(endCountdown);
                        }

                        endCountdown--;

                    } else {
                        cancleEndCountdown();
                        System.out.println("Arena stoped!");
                        resetArena();
                    }

                }
            }, 0, 20);
        }
    }

    public void cancleEndCountdown() {
        plugin.getServer().getScheduler().cancelTask(endCountdownScheduler);
        endCountdownScheduler = -1;
    }

    public void resetArena() {
        lobby.kickAllPlayers();
        for (GuardianTeam team : teams.values()) {
            team.kickAllPlayers();
        }

        state = GuardianArenaState.OFFLINE;

        playerCount = 0;
        emeraldSpawner.stopSpawner();
        diamondSpawner.stopSpawner();

        guardianSpawner.stopSpawner();

        for (GuardianTeam team : teams.values()) {
            for (Location location : team.getRespawnBlocks()) {
                location.getBlock().setType(Material.DIAMOND);
            }
        }

        for (Entity e : world.getEntities()) { //TODO only remove entities that are inside the arena
            if (!(e instanceof Villager) && !(e instanceof Player)) {
                e.remove();
            }
        }


        state = GuardianArenaState.LOBBY;
    }

    public void startArena(List<GuardianPlayer> players) {
        state = GuardianArenaState.INGAME;
        Random r = new Random();
        resetArena();

        List<GuardianTeam> teams = new ArrayList<>(this.teams.values());
        int i = 0;
        for (GuardianPlayer player : players) {
            plugin.getServer().getPlayer(player.getUniqueId()).getInventory().clear();
            Util.healPlayer(plugin.getServer().getPlayer(player.getUniqueId()));
            teams.get(i).joinPlayer(player);
            i = (i + 1) % teams.size(); //assign teams using round-robin
            givePlayerStartKit(plugin.getServer().getPlayer(player.getUniqueId()));
        }

        emeraldSpawner.startSpawner();
        diamondSpawner.startSpawner();
        guardianSpawner.startSpawner();
    }

    public Location getLobbySpawn() {
        return lobby.getLocation();
    }

    public GuardianLobby getLobby() {
        return lobby;
    }

    public boolean isPlayerInArena(Player p) {
        return lobby.isPlayerInLobby(p) || getTeamOfPlayer(p) != null;
    }

    public GuardianTeam getTeam(String name) {
        return teams.get(name);
    }

    public EmeraldSpawner getEmeraldSpawner() {
        return emeraldSpawner;
    }

    public DiamondSpawner getDiamondSpawner() {
        return diamondSpawner;
    }

    public GuardianSpawner getGuardianSpawner() {
        return guardianSpawner;
    }

    public GuardianPlayer getPlayer(Player player) {
        return getTeamOfPlayer(player).getPlayer(player);
    }

    public GuardianTeam getTeamOfPlayer(Player player) {
        for (GuardianTeam team : teams.values()) {
            if (team.isPlayerInTeam(player)) {
                return team;
            }
        }
        return null;
    }

    public List<GuardianTeam> getRivalTeamsOfPlayer(Player player) {
        List<GuardianTeam> rivalTeams = new ArrayList<>(teams.size() - 1);
        for (GuardianTeam team : teams.values()) {
            if (!team.isPlayerInTeam(player)) {
                rivalTeams.add(team);
            }
        }
        return rivalTeams;
    }

    public Guardian getPlugin() {
        return plugin;
    }

    public void kickPlayer(Player p, String reason) {
        leavePlayer(p);
        if (reason != null) {
            broadcastMessage(reason);
        }

        if (playerCount == 0) {
            resetArena();
        }
    }

    public Location getLeavePos() {
        return leavePos;
    }

    public void broadcastMessage(String msg) {
        for (GuardianTeam team : teams.values()) {
            team.broadcastMessage(msg);
        }
        lobby.broadcastMessage(msg);
    }

    public void broadcastSound(Sound sound) {
        for (GuardianTeam team : teams.values()) {
            team.broadcastSound(sound);
        }
        lobby.broadcastSound(sound);
    }

    public GuardianArenaState getArenaState() {
        return state;
    }

    public void setState(GuardianArenaState state) {
        this.state = state;
    }
}
