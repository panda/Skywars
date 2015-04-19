package net.thelightmc.listeners;

import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerManager.addPlayer(new GamePlayer(event.getPlayer()));
    }
}
