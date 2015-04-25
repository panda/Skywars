package net.thelightmc.listeners;

import net.thelightmc.Language;
import net.thelightmc.Skywars;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.manager.GameManager;
import net.thelightmc.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
            killed.getGame().broadcast(Language.PlayerDied.getMsg().replace("{Player}",event.getEntity().getName()));
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerInteractEvent event) {
        Material material = Material.STONE_PLATE;
        if (!event.getClickedBlock().getType().equals(material)) {
            return;
        }
        final GamePlayer player = PlayerManager.getPlayer(event.getPlayer());
        if (player.getGame() != null) {
            return;
        }
        player.getPlayer().sendMessage(ChatColor.RED + "Test");
        Bukkit.getScheduler().runTask(Skywars.getProvidingPlugin(Skywars.class), new Runnable() {
            @Override
            public void run() {
                GameManager.getInstance().getAvailable().addPlayer(player);
            }
        });
    }
}
