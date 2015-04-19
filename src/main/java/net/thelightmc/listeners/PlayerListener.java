package net.thelightmc.listeners;

import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerManager.addPlayer(new GamePlayer(event.getPlayer()));
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        GamePlayer removed = PlayerManager.getPlayer(event.getPlayer());
        PlayerManager.removePlayer(removed);
    }
}
