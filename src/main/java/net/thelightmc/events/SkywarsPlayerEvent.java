package net.thelightmc.events;

import net.thelightmc.core.player.GamePlayer;
import org.bukkit.event.HandlerList;

public abstract class SkywarsPlayerEvent extends SkywarsEvent {

    private final GamePlayer player;

    public SkywarsPlayerEvent(Object game,GamePlayer who) {
        super(game);
        this.player = who;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public GamePlayer getPlayer() {
        return player;
    }
}
