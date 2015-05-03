package net.thelightmc.core.player;

import net.thelightmc.core.Game;
import net.thelightmc.core.statistics.StatValue;
import net.thelightmc.core.statistics.Stats;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GamePlayer {
    private final UUID player;
    private Game game;
    private final ConcurrentHashMap<String,ConcurrentHashMap<Stats,StatValue>> statMap = new ConcurrentHashMap<>();
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
    public void leaveGame() {
        game.removePlayer(this);
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }

    public void incrementStatistic(Stats stats) {
        String mapName;
        if (getGame() != null) {
            mapName = getGame().getMap().getName();
        } else {
            mapName = "map";
        }
        incrementStatistic(mapName, stats);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //               >>>>>>>           ||  ||  ||||  ||    ||            <<<<<<<                 //
    //               >>>>>>>           ||||||  |||   ||    ||            <<<<<<<                 //
    //               >>>>>>>           ||  ||  ||||| ||||| ||||          <<<<<<<                 //
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings(value = "unchecked")
    public void incrementStatistic(String mapName,Stats stats) {
        ConcurrentHashMap<Stats,StatValue> map = verifyMap(mapName, stats);
        Object o = map.get(stats).getValue();
        if (!(o instanceof Integer))
            throw new NullPointerException("Statistic not an integer. Not able to increment.");
        Integer value = (Integer) o;
        map.get(stats).setValue(value + 1);
    }
    @SuppressWarnings(value = "unchecked")
    public void setStatistic(String mapName,Stats stats,Integer value) {
        ConcurrentHashMap<Stats,StatValue> map = verifyMap(mapName, stats);
        map.get(stats).setValue(value);
    }
    @SuppressWarnings(value = "unchecked")
    public void setStatistic(String mapName,Stats stats,Object value) {
        ConcurrentHashMap<Stats,StatValue> map = verifyMap(mapName,stats);
        map.get(stats).setValue(value);
    }

    public String getStat(Stats stats,String... maps) {
        int i = 0;
        List<String> mapList = new ArrayList<>();
        for (String s : statMap.keySet()) {
            mapList.add(s);
        }
        if (mapList.isEmpty()) {
            Collections.addAll(mapList, maps);
        }
        for (String mapName : mapList) {
            ConcurrentHashMap<Stats,StatValue> map = verifyMap(mapName, stats);
            Object o = map.get(stats).getValue();
            if (o instanceof Integer) {
                i+= (Integer)o;
            } else {
                return String.valueOf(o);
            }
        }
        return String.valueOf(i);
    }
    private ConcurrentHashMap<Stats,StatValue> verifyMap(String mapName,Stats stats) {
        if (!statMap.containsKey(mapName)) {
            statMap.put(mapName,new ConcurrentHashMap<Stats, StatValue>());
        }
        ConcurrentHashMap<Stats,StatValue> map = statMap.get(mapName);
        if (!map.containsKey(stats)) {
            map.put(stats,new StatValue<>(stats.getValue()));
        }
        return map;
    }
}
