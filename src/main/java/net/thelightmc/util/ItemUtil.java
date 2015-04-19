package net.thelightmc.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {
    public static ItemStack readLine(String s) {
        //ToDo support lore,name,enchants,ect.
        return new ItemStack(Material.valueOf(s));
    }
}
