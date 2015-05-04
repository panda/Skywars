package net.thelightmc.core.build;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.schematic.SchematicFormat;
import net.thelightmc.Skywars;
import net.thelightmc.util.WeightedList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    private final ArrayList<Spawn> spawns = new ArrayList<>();
    private final WeightedList<ItemStack> itemStacks;
    private final Location middle;
    private int minimumPlayers;
    private int maximumPlayers;
    private String name;

    protected Map(MapCreator creator,Location origin, String name,WeightedList<ItemStack> list) {
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

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Spawn> getSpawns() {
        return spawns;
    }

    public class Spawn {
        private final Location location;
        private boolean used;
        public Spawn(Location location) {
            this.location = location;
            this.used = false;
            setBox();
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

        public void setBox() {
            Block block = getLocation().getBlock();
            block.setType(Material.GLASS);
            int boxHeight = 3;
            for (int i = 0; i < boxHeight; i++) {
                Location location = getLocation().clone().add(0,i,0);
                Block tempBlock = location.getBlock();
                tempBlock.getRelative(BlockFace.NORTH).setType(Material.GLASS);
                tempBlock.getRelative(BlockFace.EAST).setType(Material.GLASS);
                tempBlock.getRelative(BlockFace.WEST).setType(Material.GLASS);
                tempBlock.getRelative(BlockFace.SOUTH).setType(Material.GLASS);
            }
            getLocation().clone().add(0,boxHeight,0).getBlock().setType(Material.GLASS);
        }
        public void removeBox() {
            getLocation().getBlock().setType(Material.AIR);
        }
    }
    @SuppressWarnings(value = "deprecation")
    public int build(File schematic,int buffer) {
        double bufferX = middle.getX()+buffer;
        double bufferY = middle.getY();
        double bufferZ = middle.getZ();
        try {
            HashMap<Block,Integer> blocks = new HashMap<>();
            CuboidClipboard cuboidClipboard = SchematicFormat.MCEDIT.load(schematic);
            Vector origin = new Vector(bufferX, bufferY, bufferZ);
            for (int y = 0; y < cuboidClipboard.getHeight(); y++)
                for (int x = 0; x < cuboidClipboard.getWidth(); x++)
                    for (int z = 0; z < cuboidClipboard.getLength(); z++) {
                        BaseBlock baseBlock = cuboidClipboard.getPoint(new Vector(x, y, z));
                        Vector relativeVector = new Vector(x, y, z).add(origin);
                        Block buildBlock = middle.getWorld().getBlockAt(relativeVector.getBlockX(), relativeVector.getBlockY(), relativeVector.getBlockZ());
                        if (buildBlock.getTypeId() != baseBlock.getId()) {
                            blocks.put(buildBlock,baseBlock.getId());
                        }
                    }
            buildAsync(blocks,1000, JavaPlugin.getProvidingPlugin(Skywars.class));
            return cuboidClipboard.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    private void buildAsync(final HashMap<Block, Integer> blocks, final int max,final Plugin plugin) {
        ArrayList<Block> temp = new ArrayList<>();
        int ctr = 0;
        for (Block block : blocks.keySet()) {
            if (ctr >= max) {
                break;
            }
            block.setTypeId(blocks.get(block));
            if (block.getType() == Material.CHEST) {
                Chest chest = (Chest) block.getState();
                fillChest(chest);
            }
            if (block.getType() == Material.BEACON)
                addSpawn(new Spawn(block.getLocation()));
            temp.add(block);
            ctr++;
        }
        for (Block block : temp) {
            blocks.remove(block);
        }
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                buildAsync(blocks,max, plugin);
            }
        },2);
    }
}
