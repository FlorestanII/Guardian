package me.florestanii.guardian.commands;

import de.craften.plugins.mcguilib.text.TextBuilder;
import me.florestanii.guardian.Guardian;
import me.florestanii.guardian.util.commands.SubCommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class GuardianSubCommandHandler extends SubCommandHandler {
    public GuardianSubCommandHandler(String parentCommand) {
        super(parentCommand);
    }

    @Override
    protected final void onInvalidCommand(CommandSender sender) {
        Guardian.prefix()
                .append("Unknown command. Type ").red()
                .append("/guardian help").gold()
                .append(" for a full list of the commands.").red()
                .sendTo(sender);
    }

    @Override
    protected final void onPermissionDenied(CommandSender sender, Command command, String[] args) {
        Guardian.prefix().append("You have no permission to use this command.").red().sendTo(sender);
    }

    @Override
    protected final void sendHelpLine(CommandSender sender, me.florestanii.guardian.util.commands.Command command) {
        if (command.value().length > 0) {
            Guardian.prefix()
                    .append("/" + getParentCommand()).append(" ").append(command.value()[0]).red()
                    .append(" - ").append(command.description())
                    .sendTo(sender);
        } else {
            Guardian.prefix()
                    .append("/" + getParentCommand()).red()
                    .append(" - ").append(command.description())
                    .sendTo(sender);
        }
    }

    @Override
    protected final void sendUsageHelp(CommandSender sender, me.florestanii.guardian.util.commands.Command command) {
        TextBuilder usageText = TextBuilder.create();
        if (command.usage().length > 0) {
            usageText.append(command.description()).newLine();
            for (String usage : command.usage()) {
                usageText.append("/" + getParentCommand()).append(usage).newLine();
            }
        } else {
            usageText.append("/" + getParentCommand());
            if (command.value().length > 0) {
                usageText.append(" " + command.value()[0]);
            }
            usageText.append(" - " + command.description());
        }
        usageText.sendTo(sender);
    }
}
