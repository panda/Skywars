package net.thelightmc.events;

import net.thelightmc.core.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
public abstract class SkywarsPlayerEvent extends SkywarsEvent {

    private final Player player;

    public SkywarsPlayerEvent(Game game,Player who) {
        super(game);
        this.player = who;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public Player getPlayer() {
        return player;
    }
}
