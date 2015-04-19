package net.thelightmc.timer;

import net.thelightmc.Language;
import net.thelightmc.core.game.Game;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathmatchTimer extends BukkitRunnable {
    int ctr;
    private final Game game;

    public DeathmatchTimer(int ctr,Game game) {
        this.ctr = ctr;
        this.game = game;
    }

    @Override
    public void run() {
        if (ctr == 0) {
            game.broadcast(Language.DeathmatchStarting);
            game.startDeathmatch();
            this.cancel();
            return;
        }
        if (ctr % 10 == 0) {
            game.broadcast(Language.DeathmatchCountdown.getFormat(ctr));
        }
        if (ctr<=5) {
            game.broadcast(Language.DeathmatchCountdown.getFormat(ctr));
        }
        ctr--;
    }
}
