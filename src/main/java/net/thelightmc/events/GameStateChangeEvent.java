package net.thelightmc.events;

import net.thelightmc.core.game.GameState;

public class GameStateChangeEvent extends SkywarsEvent {
    private final GameState from;
    private GameState to;

    public GameStateChangeEvent(GameState from,GameState to,Object game) {
        super(game);
        this.from = from;
        this.to = to;
    }

    public GameState getTo() {
        return to;
    }

    public void setTo(GameState to) {
        this.to = to;
    }

    public GameState getFrom() {
        return from;
    }
}
