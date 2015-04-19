package net.thelightmc.readers;

import net.thelightmc.util.WeightedList;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.ThreadLocalRandom;

public class ChestFileReader {
    private final FileConfiguration file;

    public ChestFileReader(FileConfiguration file) {
        this.file = file;
    }
    public WeightedList<Material> getList() {
        WeightedList<Material> list = new WeightedList<>(ThreadLocalRandom.current());
        for (String s : file.getStringList("")) {
            String[] split = s.split(" ");
            Integer weight;

        }
        return null;
    }
}
