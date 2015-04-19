package net.thelightmc.core.build;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import net.thelightmc.util.WeightedList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Map {
    private final ArrayList<Spawn> spawns = new ArrayList<>();
    private final ArrayList<Chest> chests = new ArrayList<>();
    private final WeightedList<ItemStack> itemStacks;
    private final Location origin;
    private final Location middle;
    private final int minimumPlayers;
    private final int maximumPlayers;
    private final String name;

    protected Map(Location origin, String name,WeightedList<ItemStack> list) {
        this.origin = origin;
        this.middle = origin;
        this.name = name;
        maximumPlayers = 0;
        minimumPlayers = 0;
        itemStacks = list;
    }

    public Spawn getAvailableSpawn() {
        for (Spawn spawn : spawns) {
            if (!spawn.isUsed()) {
                return spawn;
            }
        }
        return null;
    }

    public ArrayList<Chest> getChests() {
        return chests;
    }

    public void addSpawn(Spawn spawn) {
        spawns.add(spawn);
    }

    public Vector getOrigin() {
        Vector vector = new Vector();
        vector.setX(origin.getX());
        vector.setY(origin.getY());
        vector.setZ(origin.getZ());
        return vector;
    }

    public Location getMiddle() {
        return middle;
    }

    public String getName() {
        return name;
    }

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public void fillChest(Chest chest) {
        chest.getBlockInventory().clear();
        int amount = ThreadLocalRandom.current().nextInt(5,10);
        for (int i = 0; i<=amount; i++) {

        }
    }

    public WeightedList<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public class Spawn {
        private final Location location;
        private boolean used;
        public Spawn(Location location) {
            this.location = location;
            this.used = false;
        }
        public Location getLocation() {
            return location;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }
    }
    public void build(Vector origin,EditSession session,File schematic) {
        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        try {
            MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, origin, false);
        } catch (MaxChangedBlocksException
                | com.sk89q.worldedit.data.DataException | IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        for (int x = 0; x <= session.getMaximumPoint().getBlockX(); x++) {
            for (int y = 0; y <= session.getMaximumPoint().getBlockY(); y++) {
                for (int z = 0; z <= session.getMaximumPoint().getBlockZ(); z++) {
                    Block block = getWorld().getBlockAt(x,y,z);
                    if (block.getType() != Material.BEACON) {
                        continue;
                    }
                    addSpawn(new Spawn(block.getLocation()));
                }
            }
        }
    }

    private World getWorld() {
        return origin.getWorld();
    }
}
