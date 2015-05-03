package net.thelightmc.core.player;

import net.thelightmc.core.Game;
import net.thelightmc.core.statistics.StatValue;
import net.thelightmc.core.statistics.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GamePlayer {
    private final UUID player;
    private Game game;
    private final ConcurrentHashMap<String,ConcurrentHashMap<Stats,StatValue>> statMap = new ConcurrentHashMap<>();
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

    @SuppressWarnings(value = "unchecked")
    public void incrementStatistic(String mapName,Stats stats) {
        if (!statMap.containsKey(mapName)) {
            statMap.put(mapName,new ConcurrentHashMap<Stats, StatValue>());
        }
        ConcurrentHashMap<Stats,StatValue> map = statMap.get(mapName);
        if (!map.containsKey(stats)) {
            map.put(stats,new StatValue<>(1));
            return;
        }
        Object o = map.get(stats).getValue();
        if (!(o instanceof Integer))
            throw new NullPointerException("Statistic not an integer. Not able to increment.");
        Integer value = (Integer) o;
        map.get(stats).setValue(value);
    }
    @SuppressWarnings(value = "unchecked")
    public void setStatistic(String mapName,Stats stats,Integer value) {
        if (!statMap.containsKey(mapName)) {
            statMap.put(mapName,new ConcurrentHashMap<Stats, StatValue>());
        }
        ConcurrentHashMap<Stats,StatValue> map = statMap.get(mapName);
        if (!map.containsKey(stats)) {
            map.put(stats,new StatValue<>(1));
            return;
        }
        map.get(stats).setValue(value);
    }
    @SuppressWarnings(value = "unchecked")
    public void setStatistic(String mapName,Stats stats,String value) {
        if (!statMap.containsKey(mapName)) {
            statMap.put(mapName,new ConcurrentHashMap<Stats, StatValue>());
        }
        ConcurrentHashMap<Stats,StatValue> map = statMap.get(mapName);
        if (!map.containsKey(stats)) {
            map.put(stats,new StatValue<>(1));
            return;
        }
        map.get(stats).setValue(value);
    }
    public void leaveGame() {
        game.removePlayer(this);
    }

    public String getStat(Stats stats,String... maps) {
        int i = 0;
        if (maps == null || maps.length < 0) {
            maps = statMap.keySet().toArray(new String[statMap.keySet().size()]);
        }
        for (String mapName : maps) {
            ConcurrentHashMap<Stats,StatValue> map = statMap.get(mapName);
            Object o = map.get(stats).getValue();
            if (o instanceof Integer) {
                i+= (Integer)o;
            } else {
                return String.valueOf(o);
            }
        }
        return String.valueOf(i);
    }

    public void incrementStatistic(Stats stats) {
        incrementStatistic(getGame().getMap().getName(),stats);
    }
}
