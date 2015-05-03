package net.thelightmc.core.scoreboard;

import net.thelightmc.core.Game;
import net.thelightmc.core.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class GameScoreboard {
    private final Game game;
    private final Scoreboard scoreboard;
    private final Objective sideBar;
    private int taken = 0;
    public GameScoreboard(Game game) {
        this.game = game;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sideBar = scoreboard.registerNewObjective("sideBar","dummy");
        setup();
    }
    public void update(ScoreboardUpdate update) {
        switch (update) {
            case AddPlayer:
                Integer integer = update.getScore();
                if (integer == null) {
                    integer = taken++;
                }
                getSideBar().getScore(update.getValue()).setScore(integer);
                break;
            case RemovePlayer:
                Score score = getSideBar().getScore(update.getValue());
                score.setScore(score.getScore()*-1);
                break;
            case StartGame:
                for (GamePlayer gamePlayer: getGame().getPlayers()) {
                    ScoreboardUpdate sUpdate = ScoreboardUpdate.AddPlayer;
                    sUpdate.setValue(gamePlayer.getName());
                    update(sUpdate);
                }
                break;
            case Countdown:
                break;
            default:
                break;
        }
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    private Game getGame() {
        return game;
    }

    private Objective getSideBar() {
        return sideBar;
    }
    private void setup() {
        getSideBar().setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Map: " + ChatColor.AQUA + game.getMap().getName());
    }
}