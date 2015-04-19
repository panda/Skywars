package net.thelightmc.listeners;

import net.thelightmc.Language;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.manager.PlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        GamePlayer killed = PlayerManager.getPlayer(event.getEntity());
        if (killed.getGame() == null) {
            return;
        }
        event.setDeathMessage("");
        killed.incrementStatistic(GamePlayer.Statstics.Deaths);
        killed.leaveGame();
        if (event.getEntity().getKiller() != null) {
            GamePlayer killer = PlayerManager.getPlayer(event.getEntity().getKiller());
            killer.incrementStatistic(GamePlayer.Statstics.Kills);
            killed.getGame().broadcast(Language.PlayerKilled.getMsg().replace("{Player}",event.getEntity().getName()).replace("{Killer}",killer.getName()));
        } else {
            killed.getGame().broadcast(Language.PlayerDied.getMsg().replace("{PLayer}",event.getEntity().getName()));
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Block block = event.getTo().getBlock().getRelative(BlockFace.DOWN);
        Material material = Material.valueOf("STONE_PLATE");
        if (!block.getType().equals(material)) {
            return;
        }

    }
}
