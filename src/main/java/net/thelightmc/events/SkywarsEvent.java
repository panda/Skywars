package net.thelightmc.events;

import net.thelightmc.core.game.Game;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class SkywarsEvent extends Event implements Cancellable {
    private boolean cancelled;
    private final Game game;
    public SkywarsEvent(Game game) {
        this.game = game;
    }
    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
