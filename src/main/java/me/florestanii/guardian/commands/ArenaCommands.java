package me.florestanii.guardian.commands;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.GuardianArenaState;
import me.florestanii.guardian.util.commands.CommandHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommands implements CommandHandler {
    private final Guardian guardian;

    public ArenaCommands(Guardian guardian) {
        this.guardian = guardian;
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "join",
            description = "Join an arena.",
            usage = "join <arena name>",
            min = 1, max = 1)
    public void join(Player player, String[] args) {
        GuardianArena arena = guardian.getArena(args[0]);
        if (arena != null) {
            if (arena.getArenaState() == GuardianArenaState.LOBBY) {
                arena.joinPlayer(player);
            } else {
                Guardian.prefix().append("Die Guardian-Runde läuft bereits, du musst warten, bis sie vorbei ist.").darkRed().sendTo(player);
            }
        }
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "leave",
            description = "Leave the arena.")
    public void leave(Player player) {
        GuardianArena arena = guardian.getArena(player);
        if (arena != null) {
            arena.kickPlayer(player, player.getName() + " hat das Spiel verlassen.");
        } else {
            Guardian.prefix().append("Du befindest dich in keiner Guardian-Runde!").darkRed().sendTo(player);
        }
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "start",
            permission = "guardian.start",
            description = "Start the game in an arena.",
            usage = "start <arena name>",
            min = 1, max = 1)
    public void startArena(CommandSender sender, String[] args) {
        GuardianArena arena = guardian.getArena(args[0]);
        if (arena != null) {
            if (arena.getArenaState() == GuardianArenaState.LOBBY) {
                arena.getLobby().setCountdown(3);
            } else {
                Guardian.prefix().append("Das Spiel befindet sich nicht in der Lobby Phase!").darkRed().sendTo(sender);
            }
        } else {
            Guardian.prefix().append("Die angegebene Arena existiert nicht.").darkRed().sendTo(sender);
        }
    }
}
