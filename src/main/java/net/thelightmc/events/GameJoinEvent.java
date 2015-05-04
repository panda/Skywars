package net.thelightmc.events;

import net.thelightmc.core.player.GamePlayer;


public class GameJoinEvent extends SkywarsPlayerEvent {
    public GameJoinEvent(Object game, GamePlayer who) {
        super(game, who);
    }
}

