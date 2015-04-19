package net.thelightmc.core.game;

import net.thelightmc.Language;
import net.thelightmc.core.build.Map;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.core.scoreboard.GameScoreboard;
import net.thelightmc.events.DeathmatchStartEvent;
import net.thelightmc.events.GameBroadcastEvent;
import net.thelightmc.events.GameJoinEvent;
import net.thelightmc.events.GameStateChangeEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class Game {
    public Game(Map map) {
        this.map = map;
        setGameState(GameState.Waiting);
    }

    private final ArrayList<GamePlayer> gamePlayers = new ArrayList<>();
    private final Map map;
    private GameState gameState;
    private GameScoreboard scoreboard;

    public void broadcast(Language language) {
        broadcast(language.toString());
    }

    public void broadcast(String msg) {
        GameBroadcastEvent event = new GameBroadcastEvent(msg,gamePlayers,this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) { return; }
        for (GamePlayer gamePlayer : event.getGamePlayers()) {
            gamePlayer.getPlayer().sendMessage(event.getMessage());
        }
    }
    public void startGame() {

    }
    public boolean checkStart() {
        return map.getMinimumPlayers() <= getPlayerSize() && map.getMaximumPlayers() == getPlayerSize();
    }

    public void removePlayer(GamePlayer gamePlayer) {
        gamePlayers.remove(gamePlayer);
    }

    public void addPlayer(GamePlayer gamePlayer) {
        GameJoinEvent event = new GameJoinEvent(this,gamePlayer.getPlayer());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {return;}
        gamePlayers.add(gamePlayer);
        Map.Spawn spawn = getMap().getAvailableSpawn();
        spawn.setUsed(true);
        gamePlayer.getPlayer().teleport(spawn.getLocation());
        gamePlayer.setGame(this);

    }

    public Map getMap() {
        return map;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        GameStateChangeEvent event = new GameStateChangeEvent(this.gameState,gameState,this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {return;}
        this.gameState = event.getTo();
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
}