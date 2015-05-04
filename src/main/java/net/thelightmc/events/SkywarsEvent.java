package net.thelightmc.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class SkywarsEvent extends Event implements Cancellable {
    private boolean cancelled;
    private final Object game;
    private static final HandlerList handlers = new HandlerList();
    public SkywarsEvent(Object game) {
        this.game = game;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Object getGame() {
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
