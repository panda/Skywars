package net.thelightmc.listeners;

import net.thelightmc.core.Game;
import net.thelightmc.core.game.GameState;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        GamePlayer gamePlayer = PlayerManager.getPlayer(event.getPlayer());
        Game game = gamePlayer.getGame();
        if (game.getGameState() == GameState.Waiting) {
            event.setCancelled(true);
        }
    }
}
