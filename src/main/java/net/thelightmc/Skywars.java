package net.thelightmc;

import net.thelightmc.commands.CommandHandler;
import net.thelightmc.commands.sub.*;
import net.thelightmc.core.build.MapCreator;
import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.listeners.*;
import net.thelightmc.manager.PlayerManager;
import org.apache.commons.lang.enums.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Skywars extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("skywars").setExecutor(new CommandHandler());
        initialize();
        for (Player player : Bukkit.getOnlinePlayers()) {
            GamePlayer gamePlayer = new GamePlayer(player);
            PlayerManager.addPlayer(gamePlayer);
        }
    }

    @Override
    public void onDisable() {
        MapCreator.get().deleteWorld();
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener,this);
        }
    }
    private void registerCommands() {
        CommandHandler.addCommand(new CmdHelp());
        CommandHandler.addCommand(new CmdJoin());
        CommandHandler.addCommand(new CmdScore());
        CommandHandler.addCommand(new CmdIncrement());
        CommandHandler.addCommand(new CmdSpectate());
    }
    private void initialize() {
        registerCommands();
        registerEvents(new PlayerListener(), new GameListener());
    }
}
