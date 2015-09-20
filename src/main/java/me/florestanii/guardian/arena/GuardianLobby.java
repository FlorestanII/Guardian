package me.florestanii.guardian.arena;

import me.florestanii.guardian.arena.team.GuardianPlayer;
import me.florestanii.guardian.util.Util;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GuardianLobby {
    private GuardianArena arena;
    HashMap<UUID, GuardianPlayer> players = new HashMap<UUID, GuardianPlayer>();

    int maxPlayers = 8;
    int minPlayers = 4;

    int countdownScheduler = -1;
    final int startCountdown = 30;
    int countdown = startCountdown;

    boolean controlPlayerCount = true;
    private Location location;

    public GuardianLobby(GuardianArena arena, Location spawn, int maxPlayers, int minPlayers) {
        this.arena = arena;
        this.location = spawn;
        this.maxPlayers = maxPlayers;
    }

    public void startCountdown() {
        countdownScheduler = arena.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(arena.getPlugin(), new Runnable() {

            @Override
            public void run() {
                setLevelOfAllPlayers(countdown);
                if (countdown != 0) {

                    if (countdown == startCountdown || countdown == (float) (startCountdown / 3 * 2) || countdown <= (float) (startCountdown / 3)) {
                        broadcastMessage(ChatColor.YELLOW + "Die Runde startet in " + ChatColor.RED + countdown + ChatColor.YELLOW + " Sekunden!");
                        if (countdown <= (float) (startCountdown / 3))
                            broadcastNote(Instrument.BASS_GUITAR, new Note(10));
                    }
                    countdown--;

                } else {
                    cancelCountdown(true);
                    arena.startArena(getPlayers());
                    broadcastNote(Instrument.PIANO, new Note(24));
                    broadcastMessage(ChatColor.RED + "Die Runde startet nun, Viel Glück!");
                    System.out.println("Arena started!");
                    resetLobby();
                }
            }
        }, 0, 20);
        System.out.println("Countdown started!");
    }

    public boolean cancelCountdown(boolean isArenaStarted) {

        if (countdownScheduler == -1) {
            return false;
        } else {
            arena.getPlugin().getServer().getScheduler().cancelTask(countdownScheduler);
            resetCountdown();
            if (!isArenaStarted) broadcastMessage(ChatColor.RED + "Der Countdown wurde abgebrochen!");
            countdownScheduler = -1;
            return true;
        }
    }

    public void resetCountdown() {
        countdown = startCountdown;
    }

    public boolean isCountdownStarted() {
        if (countdownScheduler == -1) return false;
        else return true;
    }

    public void joinPlayer(GuardianPlayer player) {
        if (players.size() < maxPlayers) {
            players.put(player.getUniqueId(), player);
            arena.getPlugin().getServer().getPlayer(player.getUniqueId()).teleport(arena.getLobbySpawn());
            arena.getPlugin().getServer().getPlayer(player.getUniqueId()).setGameMode(GameMode.SURVIVAL);
            arena.getPlugin().getServer().getPlayer(player.getUniqueId()).getInventory().clear();
            arena.getPlugin().getServer().getPlayer(player.getUniqueId()).setPlayerListName(ChatColor.GREEN + player.getDisplayName());
            Util.setTagColor(arena.getPlugin(), arena.getPlugin().getServer().getPlayer(player.getUniqueId()), ChatColor.GREEN);
            if (getPlayerCount() >= minPlayers && controlPlayerCount && !isCountdownStarted()) {
                startCountdown();
            }
        } else {
            arena.getPlugin().getServer().getPlayer(player.getUniqueId()).sendMessage(ChatColor.DARK_RED + "Diese Arena ist schon voll!");
        }
    }

    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    public void leftPlayer(Player player) {
        players.remove(player.getUniqueId());
        if (getPlayerCount() < minPlayers && controlPlayerCount && isCountdownStarted()) {
            cancelCountdown(false);
        }
    }

    public void resetLobby() {
        players.clear();
        countdown = startCountdown;
        countdownScheduler = -1;
        controlPlayerCount = true;
    }

    public void kickAllPlayers() {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(
                this.players.values());
        for (GuardianPlayer p : players) {
            arena.kickPlayer(arena.getPlugin().getServer().getPlayer(p.getUniqueId()), null);
        }
    }

    public GuardianPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isPlayerInLobby(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public int getPlayerCount() {
        return players.size();
    }

    public ArrayList<GuardianPlayer> getPlayers() {
        return new ArrayList<GuardianPlayer>(this.players.values());
    }

    public HashMap<UUID, GuardianPlayer> getPlayerHashMap() {
        return players;
    }

    public void broadcastMessage(String msg) {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(
                this.players.values());
        for (GuardianPlayer p : players) {
            arena.getPlugin().getServer().getPlayer(p.getUniqueId()).sendMessage(msg);
        }
    }

    public void setLevelOfAllPlayers(int level) {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(
                this.players.values());
        for (GuardianPlayer p : players) {
            arena.getPlugin().getServer().getPlayer(p.getUniqueId()).setLevel(level);
        }
    }

    public void broadcastSound(Sound sound) {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(this.players.values());
        for (GuardianPlayer p : players) {
            arena.getPlugin().getServer().getPlayer(p.getUniqueId()).playSound(arena.getPlugin().getServer().getPlayer(p.getUniqueId()).getLocation(), sound, 3, 2);
        }
    }

    public void broadcastNote(Instrument instrument, Note note) {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(
                this.players.values());
        for (GuardianPlayer p : players) {
            arena.getPlugin().getServer().getPlayer(p.getUniqueId()).playNote(arena.getPlugin().getServer().getPlayer(p.getUniqueId()).getLocation(), instrument, note);
        }
    }

    public void setCountdown(int newCountdown) {
        this.countdown = newCountdown;
    }

    public int getCountdown() {
        return countdown;
    }

    public Location getLocation() {
        return location;
    }
}
