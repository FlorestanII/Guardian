package me.florestanii.guardian.arena;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.util.Util;
import me.florestanii.guardian.arena.team.GuardianPlayer;
import me.florestanii.guardian.arena.team.GuardianTeam;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class GuardianArena {

    Guardian plugin;

    World world;

    Location leavePos;

    GuardianLobby lobby;

    GuardianTeam blue;
    GuardianTeam red;

    GuardianArenaState state = GuardianArenaState.OFFLINE;

    EmeraldSpawner emeraldSpawner;
    DiamondSpawner diamondSpawner;

    GuardianSpawner guardianSpawner;

    int playerCount = 0;

    int endCountdownScheduler = -1;
    int endStartCountdown = 10;
    int endCountdown = endStartCountdown;

    public GuardianArena(Guardian plugin, World world) {

        this(plugin);
        this.world = world;

    }

    public GuardianArena(Guardian plugin) {
        this.plugin = plugin;

        blue = new GuardianTeam(this, "Blau");
        blue.setChatColor(ChatColor.BLUE);

        red = new GuardianTeam(this, "Rot");
        red.setChatColor(ChatColor.RED);

        lobby = new GuardianLobby(this, 8, 2);

        emeraldSpawner = new EmeraldSpawner(this, (int) (20 * 2f));
        diamondSpawner = new DiamondSpawner(this, (int) (20 * 45f));

        guardianSpawner = new GuardianSpawner(this, (int) (20 * 120));

    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
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
                final GuardianTeam team = plugin.getArena().getTeamOfPlayer(p.getUniqueId());
                final GuardianTeam rivalTeam = plugin.getArena().getRivalTeamOfPlayer(p.getUniqueId());
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                    @Override
                    public void run() {
                        if (team.getPlayerCount() - 1 <= 0) {

                            broadcastMessage(ChatColor.GREEN + "Team " + rivalTeam.getChatColor() + rivalTeam.getName() + ChatColor.GREEN + " hat gewonnen!");
                            broadcastSound(Sound.FIREWORK_LAUNCH);
                            startEndCountdown();

                        } else if (rivalTeam.getPlayerCount() - 1 <= 0) {

                            broadcastMessage(ChatColor.GREEN + "Team " + team.getChatColor() + team.getName() + ChatColor.GREEN + " hat gewonnen!");
                            broadcastSound(Sound.FIREWORK_LAUNCH);
                            startEndCountdown();

                        }
                    }
                }, 25);
                if (red.isPlayerInTeam(p)) {
                    red.leftPlayer(p.getUniqueId());
                } else if (blue.isPlayerInTeam(p)) {
                    blue.leftPlayer(p.getUniqueId());
                }
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

    public void givePlayerLeatherArmor(Player p) {
        if (getTeamRed().isPlayerInTeam(p)) {
            p.getInventory().setHelmet(Util.getColeredLeatherArmor(Material.LEATHER_HELMET, org.bukkit.Color.RED));
            p.getInventory().setChestplate(Util.getColeredLeatherArmor(Material.LEATHER_CHESTPLATE, org.bukkit.Color.RED));
            p.getInventory().setLeggings(Util.getColeredLeatherArmor(Material.LEATHER_LEGGINGS, org.bukkit.Color.RED));
            p.getInventory().setBoots(Util.getColeredLeatherArmor(Material.LEATHER_BOOTS, org.bukkit.Color.RED));
        } else {
            p.getInventory().setHelmet(Util.getColeredLeatherArmor(Material.LEATHER_HELMET, org.bukkit.Color.BLUE));
            p.getInventory().setChestplate(Util.getColeredLeatherArmor(Material.LEATHER_CHESTPLATE, org.bukkit.Color.BLUE));
            p.getInventory().setLeggings(Util.getColeredLeatherArmor(Material.LEATHER_LEGGINGS, org.bukkit.Color.BLUE));
            p.getInventory().setBoots(Util.getColeredLeatherArmor(Material.LEATHER_BOOTS, org.bukkit.Color.BLUE));
        }
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
        red.kickAllPlayers();
        blue.kickAllPlayers();

        state = GuardianArenaState.OFFLINE;

        playerCount = 0;
        emeraldSpawner.stopSpawner();
        diamondSpawner.stopSpawner();

        guardianSpawner.stopSpawner();

        world.getBlockAt(red.getRespawnblock1()).setType(Material.DIAMOND_BLOCK);
        world.getBlockAt(red.getRespawnblock2()).setType(Material.DIAMOND_BLOCK);
        world.getBlockAt(blue.getRespawnblock1()).setType(Material.DIAMOND_BLOCK);
        world.getBlockAt(blue.getRespawnblock2()).setType(Material.DIAMOND_BLOCK);

        for (Entity e : world.getEntities()) {
            if (!(e instanceof Villager) && !(e instanceof Player)) {
                e.remove();
            }
        }


        state = GuardianArenaState.LOBBY;
    }

    public void startArena(ArrayList<GuardianPlayer> players) {
        state = GuardianArenaState.INGAME;
        Random r = new Random();

        for (Entity e : world.getEntities()) {
            if (e instanceof Item || e instanceof IronGolem) {
                e.remove();
            }
        }

        world.getBlockAt(red.getRespawnblock1()).setType(Material.DIAMOND_BLOCK);
        world.getBlockAt(red.getRespawnblock2()).setType(Material.DIAMOND_BLOCK);
        world.getBlockAt(blue.getRespawnblock1()).setType(Material.DIAMOND_BLOCK);
        world.getBlockAt(blue.getRespawnblock2()).setType(Material.DIAMOND_BLOCK);

        for (GuardianPlayer player : players) {
            plugin.getServer().getPlayer(player.getUniqueId()).getInventory().clear();
            Util.healPlayer(plugin.getServer().getPlayer(player.getUniqueId()));
            if (r.nextInt(2) == 0) {
                if (red.getPlayerCount() < (int) (playerCount / 2)) {
                    red.joinPlayer(player);
                } else {
                    blue.joinPlayer(player);
                }
            } else {
                if (blue.getPlayerCount() < (int) (playerCount / 2)) {
                    blue.joinPlayer(player);
                } else {
                    red.joinPlayer(player);
                }

            }
            givePlayerStartKit(plugin.getServer().getPlayer(player.getUniqueId()));

        }

        emeraldSpawner.startSpawner();
        diamondSpawner.startSpawner();

        guardianSpawner.startSpawner();

    }

    public void setLeavePos(Location leavePos) {
        this.leavePos = leavePos;
    }

    public void setLobbySpawn(Location spawn) {
        lobby.setSpawn(spawn);
    }

    public Location getLobbySpawn() {
        return lobby.getSpawn();
    }

    public GuardianLobby getLobby() {
        return lobby;
    }

    public boolean isPlayerInArena(Player p) {
        if (red.isPlayerInTeam(p) || blue.isPlayerInTeam(p) || lobby.isPlayerInLobby(p)) {
            return true;
        }
        return false;
    }

    public GuardianTeam getTeamBlue() {
        return blue;
    }

    public GuardianTeam getTeamRed() {
        return red;
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

    public GuardianPlayer getPlayer(UUID uuid) {
        if (red.isPlayerInTeam(uuid)) {
            return red.getPlayer(uuid);
        } else if (blue.isPlayerInTeam(uuid)) {
            return blue.getPlayer(uuid);
        } else {
            return null;
        }
    }

    public GuardianTeam getTeamOfPlayer(UUID uuid) {
        if (red.isPlayerInTeam(uuid)) {
            return red;
        } else if (blue.isPlayerInTeam(uuid)) {
            return blue;
        } else {
            return null;
        }
    }

    public GuardianTeam getRivalTeamOfPlayer(UUID uuid) {
        if (red.isPlayerInTeam(uuid)) {
            return blue;
        } else if (blue.isPlayerInTeam(uuid)) {
            return red;
        } else {
            return null;
        }
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
        red.broadcastMessage(msg);
        blue.broadcastMessage(msg);
        lobby.broadcastMessage(msg);
    }

    public void broadcastSound(Sound sound) {
        red.broadcastSound(sound);
        blue.broadcastSound(sound);
        lobby.broadcastSound(sound);
    }

    public boolean isArenaReady() {
        return leavePos != null && blue != null && red != null && lobby != null && world != null && plugin != null && red.isReady() && blue.isReady() && lobby.isReady();
    }

    public GuardianArenaState getArenaState() {
        return state;
    }

    public void setState(GuardianArenaState state) {
        this.state = state;
    }
}
