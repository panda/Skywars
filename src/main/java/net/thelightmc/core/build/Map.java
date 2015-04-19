package net.thelightmc.core.build;

import org.bukkit.Location;
import org.bukkit.block.Chest;

import java.util.ArrayList;

public class Map {
    private final ArrayList<Spawn> spawns = new ArrayList<>();
    private final ArrayList<Chest> chests = new ArrayList<>();
    private final Location origin;
    private final Location middle;

    protected Map(Location origin) {
        this.origin = origin;
        this.middle = origin;
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

    public Location getOrigin() {
        return origin;
    }

    public Location getMiddle() {
        return middle;
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
}
