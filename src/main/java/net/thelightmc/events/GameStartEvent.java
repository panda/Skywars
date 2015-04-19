package net.thelightmc.events;

import net.thelightmc.core.game.Game;

public class GameStartEvent extends SkywarsEvent{
    public GameStartEvent(Game game) {
        super(game);
    }
}
