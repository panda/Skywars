package net.thelightmc.events;

import net.thelightmc.core.game.Game;
import net.thelightmc.core.player.GamePlayer;

import java.util.ArrayList;

public class GameBroadcastEvent extends SkywarsEvent {
    private String message;
    private final ArrayList<GamePlayer> gamePlayers;

    public GameBroadcastEvent(String message,ArrayList<GamePlayer> gamePlayers,Game game) {
        super(game);
        this.message = message;
        this.gamePlayers = gamePlayers;
    }


    public ArrayList<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
