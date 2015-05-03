package net.thelightmc.readers;

import net.thelightmc.util.WeightedList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ChestFileReader {
    private final FileConfiguration file;

    public ChestFileReader(FileConfiguration file) {
        this.file = file;
    }
    public WeightedList<ItemStack> getList() {
        WeightedList<ItemStack> list = new WeightedList<>(ThreadLocalRandom.current());
        for (String s : file.getStringList("items")) {
            String[] split = s.split(" ");
            Integer weight = Integer.parseInt(split[0]);
            Integer data = null;
            String mat = split[1];
            Material material;
            if (mat.contains(":")) {
                String[] temp = mat.split(":");
                data = Integer.parseInt(temp[1]);
                mat = temp[0];
            }
            try {
                if (StringUtils.isNumeric(mat)) {
                    material = Material.getMaterial(Integer.parseInt(mat));
                } else {
                    material = Material.valueOf(split[1].toUpperCase().replace(" ", "_"));
                }
            } catch (IllegalArgumentException ex) {
                ex.getSuppressed();
                continue;
            }
            Integer quantity = Integer.parseInt(split[2]);
            ItemStack itemStack = new ItemStack(material,quantity);
            list.add(weight,itemStack);
        }
        return list;
    }
}
