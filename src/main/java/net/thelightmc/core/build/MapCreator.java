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

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class MapCreator {
    private final String worldName;
    private World skyWorld;
    public MapCreator(String worldName) {
        this.worldName = worldName;
        plugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        createWorld();
    }
    private final WorldEditPlugin plugin;
    private void createWorld() {
        skyWorld = Bukkit.createWorld(new WorldCreator(worldName).environment(World.Environment.THE_END));
    }
    private void deleteWorld() {

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
