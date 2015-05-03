package net.thelightmc.core.build;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.schematic.SchematicFormat;
import net.thelightmc.util.WeightedList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;

public class Map {
    private final ArrayList<Spawn> spawns = new ArrayList<>();
    private final WeightedList<ItemStack> itemStacks;
    private final Location origin;
    private final Location middle;
    private int minimumPlayers;
    private int maximumPlayers;
    private String name;

    protected Map(MapCreator creator,Location origin, String name,WeightedList<ItemStack> list) {
        this.origin = origin;
        this.middle = origin;
        this.name = name;
        maximumPlayers = 2;
        minimumPlayers = 2;
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

    public void addSpawn(Spawn spawn) {
        spawns.add(spawn);
        maximumPlayers = spawns.size();
        minimumPlayers = 4;
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
            for (ItemStack itemStack : itemStacks.values(10)) {
                chest.getBlockInventory().addItem(itemStack);
            }
    }

    public WeightedList<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public void setName(String name) {
        this.name = name;
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
    @SuppressWarnings(value = "deprecation")
    public int build(File schematic,int buffer) {
        double bufferX = middle.getX()+buffer;
        double bufferY = middle.getY();
        double bufferZ = middle.getZ();
        try {
            CuboidClipboard cuboidClipboard = SchematicFormat.MCEDIT.load(schematic);
            Vector origin = new Vector(bufferX, bufferY, bufferZ);
            for (int y = 0; y < cuboidClipboard.getHeight(); y++)
                for (int x = 0; x < cuboidClipboard.getWidth(); x++)
                    for (int z = 0; z < cuboidClipboard.getLength(); z++) {
                        BaseBlock baseBlock = cuboidClipboard.getPoint(new Vector(x, y, z));
                        Vector relativeVector = new Vector(x, y, z).add(origin);
                        Block buildBlock = middle.getWorld().getBlockAt(relativeVector.getBlockX(), relativeVector.getBlockY(), relativeVector.getBlockZ());
                        if (buildBlock.getTypeId() != baseBlock.getId()) {
                            buildBlock.setTypeId(baseBlock.getId());
                        }
                        if (buildBlock.getType() == Material.CHEST) {
                            Chest chest = (Chest) buildBlock.getState();
                            fillChest(chest);
                        }
                        if (buildBlock.getType() == Material.BEACON)
                            addSpawn(new Spawn(buildBlock.getLocation().add(0,1,0)));
                    }
            return cuboidClipboard.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private World getWorld() {
        return origin.getWorld();
    }
}
