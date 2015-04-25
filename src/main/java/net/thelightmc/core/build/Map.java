package net.thelightmc.core.build;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockType;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.schematic.SchematicFormat;
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

    protected Map(MapCreator creator,Location origin, String name,WeightedList<ItemStack> list) {
        this.origin = origin;
        this.middle = origin;
        this.name = name;
        maximumPlayers = 7;
        minimumPlayers = 0;
        itemStacks = list;
        creator.build(this);
    }

    public Spawn getAvailableSpawn() {
        for (Spawn spawn : spawns) {
            if (spawn.isUsed()) {
                continue;
            }
            return spawn;
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
        int amount = ThreadLocalRandom.current().nextInt(1,3);
        for (int i = 0; i<=amount; i++) {
            for (ItemStack itemStack : itemStacks.next()) {
                chest.getBlockInventory().addItem(itemStack);
            }
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

        public Spawn(Vector currentPoint) {
            location = new Location(getWorld(),currentPoint.getBlockX(),currentPoint.getBlockY(),currentPoint.getBlockZ());
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
    public void build(EditSession session, File schematic) {
        try {
            CuboidClipboard cc = SchematicFormat.MCEDIT.load(schematic);
            cc.paste(session, new Vector(middle.getX(), middle.getY(), middle.getZ()), true);
            for (int y = 0; y < cc.getSize().getBlockY(); y++) {
                for (int x = 0; x < cc.getSize().getBlockX(); x++) {
                    for (int z = 0; z < cc.getSize().getBlockZ(); z++) {
                        Vector currentPoint = new Vector(x, y, z);
                        BaseBlock currentBlock = cc.getBlock(currentPoint);
                        if (currentBlock.getId() != BlockType.BEACON.getID()) {
                            continue;
                        }
                        addSpawn(new Spawn(currentPoint));
                    }
                }
            }
        } catch (MaxChangedBlocksException
                | com.sk89q.worldedit.data.DataException | IOException e2) {
            e2.printStackTrace();
        }
        /*
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
        */
        addSpawn(new Spawn(getMiddle()));
        Bukkit.broadcastMessage("Test");
    }
    public void loopThrough(Location loc1, Location loc2, World w) {

        int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
                miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
                minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
                maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
                maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
                maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        for(int x = minx; x<=maxx;x++){
            for(int y = miny; y<=maxy;y++){
                for(int z = minz; z<=maxz;z++){
                    Block b = w.getBlockAt(x, y, z);
                    //do somthing productive with b...
                }
            }
        }
    }

    private World getWorld() {
        return origin.getWorld();
    }
}
