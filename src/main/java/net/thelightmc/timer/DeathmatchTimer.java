package net.thelightmc.timer;

import net.thelightmc.core.game.Deathmatch;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathmatchTimer extends BukkitRunnable {
    int ctr;
    private final Deathmatch deathmatch;

    public DeathmatchTimer(int ctr,Deathmatch deathmatch) {
        this.ctr = ctr;
        this.deathmatch = deathmatch;
    }

    @Override
    public void run() {
        if (ctr == 0) {
            if (!deathmatch.isDeathmatchStarted()) {
                deathmatch.startDeathmatch();
                return;
            }
            deathmatch.deathMatchTick();
            return;
        }
        if (ctr % 10 == 0) {
            deathmatch.announceTimer(ctr);
        }
        if (ctr<=5) {
            deathmatch.announceTimer(ctr);
        }
        ctr--;
    }
}
