package net.thelightmc.core.build;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class MapCreator {
    private final String worldName;
    private World skyWorld;
    public MapCreator(String worldName) {
        this.worldName = worldName;
        plugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
    }
    private final WorldEditPlugin plugin;
    private void createWorld() {
        skyWorld = Bukkit.createWorld(new WorldCreator(worldName).environment(World.Environment.THE_END));
    }
    private void deleteWorld() {

    }
    public void create() {
        EditSession session = plugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(skyWorld), 1000000);

    }
}
