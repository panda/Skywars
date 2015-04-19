package net.thelightmc.core.scoreboard;

import com.google.common.base.Splitter;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import java.util.Iterator;

public class GameboardEntry {

    private String key;
    private GameScoreboard gameScoreboard;
    private String name;
    private Team team;
    private Score score;
    private int value;

    private String origName;
    private int count;

    public GameboardEntry(String key, GameScoreboard gameScoreboard, int value) {
        this.key = key;
        this.gameScoreboard = gameScoreboard;
        this.value = value;
        this.count = 0;
    }

    public GameboardEntry(String key, GameScoreboard gameScoreboard, int value, String origName, int count) {
        this.key = key;
        this.gameScoreboard = gameScoreboard;
        this.value = value;
        this.origName = origName;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public GameScoreboard getSpigboard() {
        return gameScoreboard;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }

    public int getValue() {
        return score != null ? (value = score.getScore()) : value;
    }

    public void setValue(int value) {
        if (!score.isScoreSet()) {
            score.setScore(-1);
        }

        score.setScore(value);
    }

    public void update(String newName) {
        int value = getValue();
        if (origName != null && newName.equals(origName)) {
            // String oldName = newName;
            for (int i = 0; i < count; i++) {
                newName = ChatColor.RESET + newName;
            }

            // Bukkit.getLogger().info("Changed '" + oldName + "' (" + oldName.length() + ") into '" + newName + "' (" + newName.length() + ")");
        } else if (newName.equals(name)) {
            // Bukkit.getLogger().info("Not updating '" + newName + "' because it matches previous name");
            return;
        }

        create(newName);
        setValue(value);
    }

    void remove() {
        if (score != null) {
            score.getScoreboard().resetScores(score.getEntry());
        }

        if (team != null) {
            team.unregister();
        }
    }

    private void create(String name) {
        this.name = name;
        remove();

        if (name.length() <= 16) {
            int value = getValue();
            score = gameScoreboard.getObjective().getScore(name);
            score.setScore(value);
            return;
        }

        // Credit to RainoBoy97 for this section here.
        team = gameScoreboard.getScoreboard().registerNewTeam("spigboard-" + gameScoreboard.getTeamId());
        Iterator<String> iterator = Splitter.fixedLength(16).split(name).iterator();
        if (name.length() > 16)
            team.setPrefix(iterator.next());
        String entry = iterator.next();
        score = gameScoreboard.getObjective().getScore(entry);
        if (name.length() > 32)
            team.setSuffix(iterator.next());

        team.addEntry(entry);
    }

}