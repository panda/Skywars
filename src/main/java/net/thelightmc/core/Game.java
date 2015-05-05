package net.thelightmc.core;

import net.thelightmc.Language;
import net.thelightmc.Skywars;
import net.thelightmc.core.build.Map;
import net.thelightmc.core.game.Deathmatch;
import net.thelightmc.core.game.GameState;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.core.scoreboard.GameScoreboard;
import net.thelightmc.core.scoreboard.ScoreboardUpdate;
import net.thelightmc.events.DeathmatchStartEvent;
//import net.thelightmc.events.GameJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Game implements Deathmatch {
    public Game(Map map) {
        this.map = map;
        setGameState(GameState.Waiting);
        scoreboard = new GameScoreboard(this);
    }

    private final ArrayList<GamePlayer> gamePlayers = new ArrayList<>();
    private final Map map;
    private GameState gameState;
    private final GameScoreboard scoreboard;
    private boolean fallDamage = true;
    private boolean deathMatchStarted = false;

    public void broadcast(Language language) {
        broadcast(language.toString());
    }

    public void broadcast(String msg) {
        for (GamePlayer gamePlayer : gamePlayers) {
            gamePlayer.getPlayer().sendMessage(msg);
        }
    }
    public void startGame() {
        for (Map.Spawn spawn : getMap().getSpawns()) {
            spawn.removeBox();
        }
        setFallDamage(false);
        broadcast(Language.GameStart);
        setGameState(GameState.InGame);
        scoreboard.update(ScoreboardUpdate.StartGame);
        Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(Skywars.class), new Runnable() {
            @Override
            public void run() {
                setFallDamage(true);
            }
        }, 60);
    }
    public void endGame() {
        broadcast(Language.GameEnd);
    }
    private boolean checkEnd() {
        return (gameState == GameState.InGame || gameState == GameState.DeathMatch) && (gamePlayers.isEmpty() || gamePlayers.size() == 1);
    }
    public boolean checkStart(boolean startIfReady) {
        if (map.getMinimumPlayers() < getPlayerSize() && startIfReady) {
            startGame();
        }
        return map.getMinimumPlayers() < getPlayerSize();
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
            Bukkit.getLogger().severe("Spawn is null. Please report this error.");
            return;
        }
        spawn.setUsed(true);
        gamePlayer.getPlayer().teleport(spawn.getLocation());
        gamePlayer.setGame(this);
        broadcast(Language.PlayerJoinedGame.getMsg().replace("{Player}", gamePlayer.getName()).replace("{Remaining}", getRemaining()));
        checkStart(true);
        ScoreboardUpdate scoreboardUpdate = ScoreboardUpdate.AddPlayer;
        scoreboardUpdate.setValue(gamePlayer.getName());
        updateScoreboard(scoreboardUpdate);
        
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

    public void updateScoreboard(ScoreboardUpdate scoreboardUpdate) {
        scoreboard.update(scoreboardUpdate);
    }

    public boolean isFallDamage() {
        return fallDamage;
    }

    public void setFallDamage(boolean fallDamage) {
        this.fallDamage = fallDamage;
    }

    @Override
    public void startDeathmatch() {
        DeathmatchStartEvent event = new DeathmatchStartEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        setGameState(GameState.Ending);
        for (GamePlayer gamePlayer : gamePlayers) {
            //This is temporarily here till I get a block to use for middle
            gamePlayer.getPlayer().teleport(getMap().getSpawns().get(0).getLocation());
        }
    }

    @Override
    public void deathMatchTick() {}

    @Override
    public boolean isDeathmatchStarted() {
        return deathMatchStarted;
    }

    @Override
    public void announceTimer(int ctr) {
        broadcast(Language.DeathmatchCountdown.getMsg().replace("{Time}",String.valueOf(ctr)));
    }
}