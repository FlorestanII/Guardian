package me.florestanii.guardian.commands;

import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.util.commands.SubCommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Command handler for /guardian commands.
 */
public class GuardianCommandHandler extends SubCommandHandler {
    public GuardianCommandHandler() {
        super("guardian");
    }

    @Override
    protected void onInvalidCommand(CommandSender sender) {
        Guardian.prefix().append("Unknown command. Type /guardian help for a full list of the commands.").sendTo(sender);
    }

    @Override
    protected void onPermissionDenied(CommandSender sender, Command command, String[] args) {
        Guardian.prefix().append("Du hast für diesen Befehl keine Rechte!").darkRed().sendTo(sender);
    }
}
