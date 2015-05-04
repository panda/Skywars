package net.thelightmc.core.build;

import net.thelightmc.readers.ChestFileReader;
import net.thelightmc.readers.FileReader;
import net.thelightmc.util.FileUtil;
import net.thelightmc.util.WeightedList;
import org.apache.commons.io.FilenameUtils;
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
    private int buffer = -200000;
    private int y = -2000;
    private final WeightedList<ItemStack> itemStackWeightedList;
    @SuppressWarnings(value = "unchecked")
    private MapCreator(String worldName) {WeightedList<ItemStack> itemStackWeightedList1;
        this.worldName = worldName;
        createWorld();
        try {
            FileReader<WeightedList> reader = new ChestFileReader(FileUtil.getFile("materials.yml"));
            itemStackWeightedList1 = reader.read();
        } catch (IOException e) {
            itemStackWeightedList1 = new WeightedList<>(ThreadLocalRandom.current());
            e.getSuppressed();
        }
        itemStackWeightedList = itemStackWeightedList1;
    }
    public static MapCreator get() {
        if (instance == null) {
            instance = new MapCreator("skyWorld");
        }
        return instance;
    }

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
                        final boolean delete = file.delete();
                        if (!delete) {
                            Bukkit.getLogger().severe(
                                    "Error deleting world file."
                            );
                        }
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
        if (buffer > 2000000) {
            buffer = -200000;
            y += 200;
        }
        File file = FileUtil.getRandomFile(FileUtil.getAllFiletype(".schematic"));
        buffer += map.build(file,buffer)*5;
        String name = file.getName();
        map.setName(name);
    }
    public Map create() {
        return new Map(this,new Location(skyWorld,0,100,y),"default",getItemStackWeightedList());
    }

    public WeightedList<ItemStack> getItemStackWeightedList() {
        return itemStackWeightedList;
    }
}
