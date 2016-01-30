package me.florestanii.guardian;

import de.craften.plugins.mcguilib.text.TextBuilder;
import me.florestanii.guardian.arena.GuardianArena;
import me.florestanii.guardian.arena.config.GuardianArenaConfig;
import me.florestanii.guardian.arena.specialitems.teleportpowder.TeleportPowderHandler;
import me.florestanii.guardian.commands.ArenaCommands;
import me.florestanii.guardian.commands.ArenaSetupCommands;
import me.florestanii.guardian.commands.GuardianCommandHandler;
import me.florestanii.guardian.listerners.*;
import me.florestanii.guardian.util.commands.CommandHandler;
import me.florestanii.guardian.util.commands.SubCommandHandler;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Guardian extends JavaPlugin implements CommandHandler {
    private Map<String, GuardianArena> arenas;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        arenas = new HashMap<>();
        if (!getConfig().isConfigurationSection("arenas")) {
            getConfig().createSection("arenas");
        }
        ConfigurationSection arenasConfig = getConfig().getConfigurationSection("arenas");
        for (String key : arenasConfig.getKeys(false)) {
            arenas.put(key, new GuardianArena(this, new GuardianArenaConfig(arenasConfig.getConfigurationSection(key))));
        }

        //TODO Unregister all these handlers on disable. And register them here, not in their constructors
        getServer().getPluginManager().registerEvents(new ArenaProtectionListener(this), this);
        new PlayerDeathHandler(this);
        new PlayerRespawnHandler(this);
        new PlayerQuitHandler(this);
        new PlayerChatHandler(this);
        new PlayerInteractHandler(this);
        new PlayerAttackHandler(this);
        new PlayerMoveHandler(this);
        new EntityDeathHandler(this);
        getServer().getPluginManager().registerEvents(new PlayerShopHandler(this), this);
        new TeleportPowderHandler(this);
        getServer().getPluginManager().registerEvents(new TeamSelectionHandler(this), this);
        getServer().getPluginManager().registerEvents(new PlayerHideHandler(this), this);
        
        SubCommandHandler commandHandler = new GuardianCommandHandler();
        commandHandler.addHandlers(this, new ArenaCommands(this));
        getCommand("guardian").setExecutor(commandHandler);

        getCommand("guardiansetup").setExecutor(new ArenaSetupCommands(this));
    }

    @me.florestanii.guardian.util.commands.Command(
            value = "reload",
            permission = "guardian.admin")
    public void reload(CommandSender sender) {
        reloadConfig();
        prefix().append("Configuration reloaded.").sendTo(sender);
    }

    public GuardianArena getArena(String name) {
        return arenas.get(name);
    }

    public Collection<GuardianArena> getArenas() {
        return arenas.values();
    }

    public GuardianArena getArena(Player player) {
        for (GuardianArena arena : getArenas()) {
            if (arena.isPlayerInArena(player)) {
                return arena;
            }
        }
        return null;
    }

    public boolean isPlayerInArena(Player player) {
        for (GuardianArena arena : getArenas()) {
            if (arena.isPlayerInArena(player)) {
                return true;
            }
        }
        return false;
    }

    public static TextBuilder prefix() {
        return TextBuilder.create("[Guardian] ").gold();
    }

    public void addArena(GuardianArenaConfig arena, String shortName) {
        arenas.put(shortName, new GuardianArena(this, arena));
        getConfig().getConfigurationSection("arenas").set(shortName, arena.getConfig());
        saveConfig();
    }
}
