package net.thelightmc.listeners;

import net.thelightmc.Language;
import net.thelightmc.Skywars;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.core.statistics.Stats;
import net.thelightmc.manager.GameManager;
import net.thelightmc.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class GameListener implements Listener {
    private final List<String> allowedCommands = new ArrayList<>();
    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        GamePlayer killed = PlayerManager.getPlayer(event.getEntity());
        if (killed.getGame() == null) {
            return;
        }
        event.setDeathMessage("");
        killed.incrementStatistic(Stats.Deaths);
        killed.leaveGame();
        if (event.getEntity().getKiller() != null) {
            GamePlayer killer = PlayerManager.getPlayer(event.getEntity().getKiller());
            killer.incrementStatistic(Stats.Kills);
            killed.getGame().broadcast(Language.PlayerKilled.getMsg().replace("{Player}",event.getEntity().getName()).replace("{Killer}",killer.getName()));
        } else {
            killed.getGame().broadcast(Language.PlayerDied.getMsg().replace("{Player}",event.getEntity().getName()));
        }
    }
    @EventHandler
    public void onPlayerFall(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        GamePlayer gamePlayer = PlayerManager.getPlayer(event.getEntity());
        if (gamePlayer.getGame() != null && !gamePlayer.getGame().isFallDamage()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerInteractEvent event) {
        final Material material = Material.STONE_PLATE;
        if (event.getClickedBlock() == null || !event.getClickedBlock().getType().equals(material)) {
            return;
        }
        final GamePlayer player = PlayerManager.getPlayer(event.getPlayer());
        if (player.getGame() != null) {
            return;
        }
        Bukkit.getScheduler().runTask(Skywars.getProvidingPlugin(Skywars.class), new Runnable() {
            @Override
            public void run() {
                GameManager.getInstance().getAvailable().addPlayer(player);
            }
        });
    }
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().isOp()) {
            return;
        }
        GamePlayer gamePlayer = PlayerManager.getPlayer(event.getPlayer());
        if (gamePlayer.getGame() == null) {
            return;
        }

        if (!allowedCommands.contains(event.getMessage().split(" ")[0])) {
            event.getPlayer().sendMessage(Language.CommandsInArena.getMsg());
            event.setCancelled(true);
        }
    }
}
