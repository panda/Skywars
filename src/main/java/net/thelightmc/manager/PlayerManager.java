package net.thelightmc.manager;

import net.thelightmc.core.player.GamePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager {
    private static final ArrayList<GamePlayer> gamePlayers = new ArrayList<>();
    static {}
    public static void addPlayer(GamePlayer player) {
        gamePlayers.add(player);
    }
    public static void removePlayer(GamePlayer player) {
        gamePlayers.remove(player);
    }
    @Deprecated
    public static GamePlayer getPlayer(String name) {
        for (GamePlayer player : gamePlayers) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }
    public static GamePlayer getPlayer(UUID uuid) {
        for (GamePlayer player : gamePlayers) {
            if (player.getUUID().equals(uuid)) {
                return player;
            }
        }
        return null;
    }
    public static GamePlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }
}