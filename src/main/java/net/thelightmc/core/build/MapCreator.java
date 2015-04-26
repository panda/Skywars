package net.thelightmc.core.build;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.thelightmc.util.FileUtil;
import net.thelightmc.util.WeightedList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class MapCreator {
    private static MapCreator instance;
    private final String worldName;
    private World skyWorld;
    private MapCreator(String worldName) {
        this.worldName = worldName;
        plugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        createWorld();
    }
    public static MapCreator get() {
        if (instance == null) {
            instance = new MapCreator("skyWorld");
        }
        return instance;
    }
    private final WorldEditPlugin plugin;
    private void createWorld() {
        skyWorld = Bukkit.createWorld(new WorldCreator(worldName).environment(World.Environment.THE_END));
    }
    private void deleteFile(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFile(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }
    public void deleteWorld() {
        File path = skyWorld.getWorldFolder();
        Bukkit.unloadWorld(skyWorld,false);
        deleteFile(path);
    }
    public void build(Map map) {
        EditSession session = plugin.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(skyWorld), 1000000);
        try {
            map.build(session, FileUtil.getFile("default.schematic",false));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Map create() {
        return new Map(this,new Location(skyWorld,0,10,0),"default",new WeightedList<ItemStack>(ThreadLocalRandom.current()));
    }
}
