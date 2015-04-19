package net.thelightmc.events;

import net.thelightmc.core.game.Game;
import org.bukkit.entity.Player;

public class GameJoinEvent extends SkywarsPlayerEvent {
    public GameJoinEvent(Game game, Player who) {
        super(game, who);
    }
}
