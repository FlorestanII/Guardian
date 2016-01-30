package me.florestanii.guardian.util.commands;


public interface CommandHandler {
    enum Result {
        Done,
        CommandNotFound,
        NoPermission
    }
}
