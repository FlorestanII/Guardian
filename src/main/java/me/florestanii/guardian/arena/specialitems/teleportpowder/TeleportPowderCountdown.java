package me.florestanii.guardian.arena.specialitems.teleportpowder;

import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

class TeleportPowderCountdown extends BukkitRunnable {
    private int countdown;
    private Runnable finishAction;
    private Player player;

    public TeleportPowderCountdown(int length, Player p, Runnable finishAction) {
        this.countdown = length;
        this.finishAction = finishAction;
        this.player = p;
    }

    @Override
    public void cancel() {
        super.cancel();
        player.setLevel(0);
    }

    @Override
    public void run() {
        if (countdown != 0) {
            player.setLevel(countdown);
            player.playNote(player.getLocation(), Instrument.BASS_GUITAR, new Note(10));
            player.sendMessage(ChatColor.YELLOW + "Du wirst in " + ChatColor.RED + countdown + " Sekunden" + ChatColor.YELLOW + " teleportiert!");
            countdown--;
        } else {
            cancel();
            player.playNote(player.getLocation(), Instrument.BASS_GUITAR, new Note(20));
            finishAction.run();
        }
    }
}
