package net.thelightmc.manager;

import net.thelightmc.core.build.Map;
import net.thelightmc.core.build.MapCreator;
import net.thelightmc.core.Game;
import net.thelightmc.core.game.GameState;
import net.thelightmc.core.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {
    private static GameManager instance;
    private Game game;
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    private ArrayList<Game> games = new ArrayList<>();
    public Game getGame(GamePlayer gamePlayer) {
        for (Game game : games) {
            if (game.getPlayers().contains(gamePlayer)) {
                return game;
            }
        }
        return null;
    }
    public Object getGame(Player player) {
        GamePlayer gamePlayer = PlayerManager.getPlayer(player);
        return getGame(gamePlayer);
    }

    public Game getAvailable() {
        if (game != null && (game.getGameState() == GameState.InGame)) {
            game = null;
        } else {
            if (game != null)
                Bukkit.getLogger().info(game.getGameState().toString());
        }
        if (game == null) {
            createGame();
        }
        return game;
    }

    private void createGame() {
        assert game == null;
        game = new Game(getMap());
        games.add(game);
    }
    private Map getMap() {
        return MapCreator.get().create();
    }
}
