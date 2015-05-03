package net.thelightmc.events;

import net.thelightmc.core.Game;
import net.thelightmc.core.player.GamePlayer;


public class GameJoinEvent extends SkywarsPlayerEvent {
    public GameJoinEvent(Game game, GamePlayer who) {
        super(game, who);
    }
}

