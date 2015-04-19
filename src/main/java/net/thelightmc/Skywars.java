package net.thelightmc;

import net.thelightmc.commands.CommandHandler;
import net.thelightmc.commands.sub.CmdHelp;
import net.thelightmc.listeners.GameListener;
import net.thelightmc.listeners.PlayerListener;
import net.thelightmc.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Skywars extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("skywars").setExecutor(new CommandHandler());
    }
    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener,this);
        }
    }
    private void registerCommands() {
        CommandHandler.addCommand(new CmdHelp());
    }
    private void initialize() {
        registerCommands();
        registerEvents(new GameListener(),new PlayerListener());
    }
}
