package net.thelightmc.core;

import net.thelightmc.Language;
import net.thelightmc.core.build.Map;
import net.thelightmc.core.game.GameState;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.core.scoreboard.GameScoreboard;
import net.thelightmc.core.scoreboard.ScoreboardUpdate;
import net.thelightmc.events.DeathmatchStartEvent;
//import net.thelightmc.events.GameJoinEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class Game {
    public Game(Map map) {
        this.map = map;
        setGameState(GameState.Waiting);
        scoreboard = new GameScoreboard(this);
    }

    private final ArrayList<GamePlayer> gamePlayers = new ArrayList<>();
    private final Map map;
    private GameState gameState;
    private final GameScoreboard scoreboard;

    public void broadcast(Language language) {
        broadcast(language.toString());
    }

    public void broadcast(String msg) {
        for (GamePlayer gamePlayer : gamePlayers) {
            gamePlayer.getPlayer().sendMessage(msg);
        }
    }
    public void startGame() {
        //ToDo remove boxes
        broadcast(Language.GameStart);
        setGameState(GameState.InGame);
        scoreboard.update(ScoreboardUpdate.StartGame);
    }
    public void endGame() {
        broadcast(Language.GameEnd);
    }
    private boolean checkEnd() {
        return gamePlayers.isEmpty() || gamePlayers.size() == 1;
    }
    public boolean checkStart(boolean startIfReady) {
        if (map.getMinimumPlayers() <= getPlayerSize() && map.getMaximumPlayers() == getPlayerSize() && startIfReady) {
            startGame();
            return true;
        }
        return false;
    }

    public void removePlayer(GamePlayer gamePlayer) {
        gamePlayers.remove(gamePlayer);
        broadcast(Language.PlayerQuit.getMsg().replace("{Player}",gamePlayer.getName()).replace("{Remaining}",getRemaining()));
        if (checkEnd()) {
            endGame();
        }
    }

    public void addPlayer(GamePlayer gamePlayer) {
        //GameJoinEvent event = new GameJoinEvent(this,gamePlayer.getPlayer());
        //Bukkit.getPluginManager().callEvent(event);
        //if (event.isCancelled()) {return;}
        gamePlayers.add(gamePlayer);
        Map.Spawn spawn = getMap().getAvailableSpawn();
        if (spawn == null) {
            Bukkit.broadcastMessage("Null");
            return;
        }
        spawn.setUsed(true);
        gamePlayer.getPlayer().teleport(spawn.getLocation());
        gamePlayer.setGame(this);
        broadcast(Language.PlayerJoinedGame.getMsg().replace("{Player}",gamePlayer.getName()).replace("{Remaining}",getRemaining()));
        checkStart(true);
        gamePlayer.getPlayer().setScoreboard(scoreboard.getScoreboard());
    }

    public Map getMap() {
        return map;
    }

    private String getRemaining() {
        return getPlayerSize() + "/" + map.getMaximumPlayers();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        //GameStateChangeEvent event = new GameStateChangeEvent(this.gameState,gameState,this);
        //Bukkit.getPluginManager().callEvent(event);
        //if (event.isCancelled()) {return;}
        this.gameState = gameState;
    }

    public int getPlayerSize() {
        return gamePlayers.size();
    }

    public ArrayList<GamePlayer> getPlayers() {
        return gamePlayers;
    }

    public void startDeathmatch() {
        DeathmatchStartEvent event = new DeathmatchStartEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        setGameState(GameState.Ending);
        for (GamePlayer gamePlayer : gamePlayers) {
            gamePlayer.getPlayer().teleport(getMap().getMiddle());
        }
    }
    public void updateScoreboard(ScoreboardUpdate scoreboardUpdate) {
        scoreboard.update(scoreboardUpdate);
    }
}