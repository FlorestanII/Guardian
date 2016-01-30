package me.florestanii.guardian.commands;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.util.commands.SubCommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Command handler for /guardian commands.
 */
public class GuardianCommandHandler extends GuardianSubCommandHandler {
    public GuardianCommandHandler() {
        super("guardian");
    }
}
