package me.florestanii.guardian.arena;

import de.craften.plugins.mcguilib.text.TextBuilder;
import me.florestanii.guardian.arena.team.GuardianPlayer;
import me.florestanii.guardian.arena.team.GuardianTeam;
import me.florestanii.guardian.util.ItemStackBuilder;
import me.florestanii.guardian.util.TitleAPI;
import me.florestanii.guardian.util.Util;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuardianLobby {
    private GuardianArena arena;
    HashMap<UUID, GuardianPlayer> players = new HashMap<UUID, GuardianPlayer>();

    Map<Player, GuardianTeam> preTeamSelection = new HashMap<Player, GuardianTeam>();

    final int maxPlayers;
    final int minPlayers;

    int countdownScheduler = -1;
    final int startCountdown = 30;
    int countdown = startCountdown;

    boolean controlPlayerCount = true;
    private final Location location;

    public GuardianLobby(GuardianArena arena, Location spawn, int maxPlayers, int minPlayers) {
        this.arena = arena;
        this.location = spawn;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
    }

    public void startCountdown() {
        countdownScheduler = arena.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(arena.getPlugin(), new Runnable() {

            @Override
            public void run() {
                setLevelOfAllPlayers(countdown);
                if (countdown != 0) {

                    if (countdown == startCountdown || countdown == (float) (startCountdown / 3 * 2) || countdown <= (float) (startCountdown / 3)) {
                        broadcastTitle(ChatColor.RED + "" + countdown, "", 5, 13, 2);
                        broadcastMessage(ChatColor.YELLOW + "Die Runde startet in " + ChatColor.RED + countdown + ChatColor.YELLOW + " Sekunden!");
                        if (countdown <= (float) (startCountdown / 3))
                            broadcastNote(Instrument.BASS_GUITAR, new Note(10));
                    }
                    countdown--;

                } else {
                    cancelCountdown(true);
                    arena.startArena(getPlayers(), preTeamSelection);
                    broadcastTitle(ChatColor.GREEN + "GO!", "", 5, 40, 5);
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
        return countdownScheduler != -1;
    }

    public void joinPlayer(GuardianPlayer player) {
        if (players.size() < maxPlayers) {
            players.put(player.getUniqueId(), player);
            arena.getPlugin().getServer().getPlayer(player.getUniqueId()).teleport(location);
            arena.getPlugin().getServer().getPlayer(player.getUniqueId()).setGameMode(GameMode.SURVIVAL);
            arena.getPlugin().getServer().getPlayer(player.getUniqueId()).getInventory().clear();
            arena.getPlugin().getServer().getPlayer(player.getUniqueId()).getInventory().addItem(ItemStackBuilder.builder().setType(Material.NETHER_STAR).setAmount(1).setDisplayName("Wähle dein Team aus").addEnchantment(Enchantment.DURABILITY).build());
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
        preTeamSelection.clear();
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

    public int getMinPlayers() {
        return minPlayers;
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

    public void preselectTeam(Player p, GuardianTeam team) {
        preTeamSelection.put(p, team);
        p.setPlayerListName(TextBuilder.create(p.getName()).color(ChatColor.GREEN).append(" [").color(ChatColor.WHITE).append(team.getName()).color(team.getChatColor()).append("]").color(ChatColor.WHITE).getSingleLine());
    }

    public void removePreselectedTeam(Player p) {
        if (preTeamSelection.remove(p) != null) {
            p.setPlayerListName(TextBuilder.create(p.getName()).color(ChatColor.GREEN).getSingleLine());
        }
    }

    public void broadcastMessage(String msg) {
        for (GuardianPlayer p : players.values()) {
            p.getBukkitPlayer().sendMessage(msg);
        }
    }

    public void broadcastTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (GuardianPlayer p : players.values()) {
            TitleAPI.sendTitle(p.getBukkitPlayer(), fadeIn, stay, fadeOut, title, subtitle);
        }
    }

    public void setLevelOfAllPlayers(int level) {
        for (GuardianPlayer p : players.values()) {
            p.getBukkitPlayer().setLevel(level);
        }
    }

    public void broadcastSound(Sound sound) {
        for (GuardianPlayer p : players.values()) {
            p.getBukkitPlayer().playSound(p.getBukkitPlayer().getLocation(), sound, 3, 2);
        }
    }

    public void broadcastNote(Instrument instrument, Note note) {
        ArrayList<GuardianPlayer> players = new ArrayList<GuardianPlayer>(
                this.players.values());
        for (GuardianPlayer p : players) {
            p.getBukkitPlayer().playNote(p.getBukkitPlayer().getLocation(), instrument, note);
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

    public ArrayList<Player> getAllPlayersOfPreTeamSelection(GuardianTeam team) {
        ArrayList<Player> players = new ArrayList<Player>();

        for (Map.Entry<Player, GuardianTeam> preselection : preTeamSelection.entrySet()) {
            if (preselection.getValue().equals(team)) {
                players.add(preselection.getKey());
            }
        }

        return players;
    }

    public GuardianTeam getPreSelectionOfPlayer(Player p) {
        return preTeamSelection.get(p);
    }
}
