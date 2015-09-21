package me.florestanii.guardian.commands;

import de.craften.plugins.mcguilib.text.TextBuilder;
import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.config.GuardianArenaConfig;
import me.florestanii.guardian.arena.config.GuardianTeamConfig;
import me.florestanii.guardian.arena.config.ItemSpawnerConfig;
import me.florestanii.guardian.util.commands.CommandHandler;
import me.florestanii.guardian.util.commands.SubCommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ArenaSetupCommands extends GuardianSubCommandHandler implements CommandHandler {
    private final Guardian plugin;
    private GuardianArenaConfig arena;

    public ArenaSetupCommands(Guardian plugin) {
        super("guardiansetup");
        this.plugin = plugin;
        addHandlers(this);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "start",
            permission = "guardian.admin.setup",
            description = "Start creating a new arena in this world.")
    public void start(Player player) {
        arena = new GuardianArenaConfig();
        arena.setWorld(player.getWorld());

        TextBuilder.create("Arena setup started.").sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "cancel",
            permission = "guardian.admin.setup",
            description = "Cancel creating a new arena.")
    public void cancel(Player player) {
        arena = null;
        TextBuilder.create("Arena setup canceled.").sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "done",
            permission = "guardian.admin.setup",
            description = "Finish creating a new arena.",
            usage = "done <short name>",
            min = 1, max = 1)
    public void done(Player player, String[] args) {
        plugin.addArena(arena, args[0]);
        arena = null;
        TextBuilder.create("Arena created and saved.").sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "lobby",
            permission = "guardian.admin.setup",
            description = "Set the lobby location.")
    public void setLobby(Player player) {
        arena.setLobbyLocation(player.getLocation().clone());

        TextBuilder.create("Lobby location set to your location.").sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "leave",
            permission = "guardian.admin.setup",
            description = "Set the leave location.")
    public void setLeave(Player player) {
        arena.setLeaveLocation(player.getLocation().clone());

        TextBuilder.create("Leave location set to your location.").sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "middle",
            permission = "guardian.admin.setup",
            description = "Set the arena's middle location.")
    public void setMiddle(Player player) {
        arena.setArenaMiddle(player.getLocation().clone());

        TextBuilder.create("Middle location set to your location.").sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "playerlimits",
            permission = "guardian.admin.setup",
            description = "Set the arena's player limits.",
            usage = "playerlimits <min players> <max players>",
            min = 2, max = 2)
    public void setPlayerLimits(Player player, String[] args) {
        arena.setMinPlayers(Integer.parseInt(args[0]));
        arena.setMaxPlayers(Integer.parseInt(args[1]));

        TextBuilder
                .create("Arena player limit set to ")
                .append(args[0]).append("-").append(args[1])
                .append(" players.").sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "itemspawner",
            permission = "guardian.admin.setup",
            description = "Add item spawners.",
            usage = "itemspawner <diamond | emerald> <delay in seconds>",
            min = 2, max = 2)
    public void addSpawner(Player player, String[] args) {
        ItemSpawnerConfig itemSpawnerConfig = new ItemSpawnerConfig();
        itemSpawnerConfig.setLocation(player.getLocation());
        itemSpawnerConfig.setDelay(Integer.parseInt(args[1]));

        if (args[0].equalsIgnoreCase("diamond")) {
            itemSpawnerConfig.setType(Material.DIAMOND);
        } else if (args[0].equalsIgnoreCase("emerald")) {
            itemSpawnerConfig.setType(Material.EMERALD);
        }

        arena.addItemSpawner(itemSpawnerConfig);

        TextBuilder.create("Item spawner added at your location.").sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "addteam",
            permission = "guardian.admin.setup",
            description = "Add a team.",
            usage = "addteam <short name> <display name> <color>",
            min = 3, max = 3)
    public void addTeam(Player player, String[] args) {
        GuardianTeamConfig teamConfig = new GuardianTeamConfig();
        teamConfig.setName(args[1]);
        teamConfig.setColor(ChatColor.valueOf(args[2].toUpperCase()));
        arena.addTeam(args[0], teamConfig);

        TextBuilder.create("Team ")
                .append(teamConfig.getName()).color(teamConfig.getColor())
                .append(" created. Use its short name ")
                .append(args[0]).color(teamConfig.getColor())
                .append(" to configure it.")
                .sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "removeteam",
            permission = "guardian.admin.setup",
            description = "Remove a team.",
            usage = "removeteam <short name>",
            min = 1, max = 1)
    public void removeTeam(Player player, String[] args) {
        GuardianTeamConfig team = arena.getTeams().get(args[0]);
        arena.removeTeam(args[0]);

        TextBuilder.create("Team ")
                .append(team.getName()).color(team.getColor())
                .append(" removed.")
                .sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "teamspawn",
            permission = "guardian.admin.setup",
            description = "Set the spawn location of a team.",
            usage = "teamspawn <short name>",
            min = 1, max = 1)
    public void setTeamSpawn(Player player, String[] args) {
        GuardianTeamConfig team = arena.getTeams().get(args[0]);
        team.setSpawn(player.getLocation().clone());

        TextBuilder.create("Spawn of team ")
                .append(team.getName()).color(team.getColor())
                .append(" set to your location.").sendTo(player);
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "respawnblock",
            permission = "guardian.admin.setup",
            description = "Add or remove a respawn block of a team.",
            usage = "respawnblock <short team name>",
            min = 1, max = 1)
    public void addOrRemoveRespawnBlock(final Player player, String[] args) {
        final GuardianTeamConfig team = arena.getTeams().get(args[0]);
        team.setSpawn(player.getLocation().clone());

        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.HIGHEST)
            public void onBlockClick(PlayerInteractEvent event) {
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    PlayerInteractEvent.getHandlerList().unregister(this);
                    Location location = event.getClickedBlock().getLocation();
                    if (team.isRespawnBlock(location)) {
                        team.removeRespawnBlock(location);
                        TextBuilder.create("Respawn block for team ")
                                .append(team.getName()).color(team.getColor())
                                .append(" removed.")
                                .sendTo(player);
                    } else {
                        team.addRespawnBlock(location);
                        TextBuilder.create("Respawn block for team ")
                                .append(team.getName()).color(team.getColor())
                                .append(" added.")
                                .sendTo(player);
                    }
                    event.setCancelled(true);
                }
            }
        }, plugin);

        TextBuilder.create("Now click on the respawn block.").sendTo(player);
    }
}
