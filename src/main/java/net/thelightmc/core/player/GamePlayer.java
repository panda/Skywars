package net.thelightmc.core.player;

import net.thelightmc.core.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {
    private final UUID player;
    private Game game;
    //Wrapper for Players
    public GamePlayer(Player player) {
        this.player = player.getUniqueId();

    }

    public Player getPlayer() {
        return Bukkit.getPlayer(player);
    }
    public String getName() {
        return getPlayer().getName();
    }
    public UUID getUUID() {
        return player;
    }

    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }

    public void incrementStatistic(Statstics statistics) {

    }

    public void leaveGame() {
        game.removePlayer(this);
    }

    public enum Statstics {
        Games,Wins,Losses,Kills,Deaths,Points;
        private int value;
        Statstics() {
            this.value = 0;

        }
        protected void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
