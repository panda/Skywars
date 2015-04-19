package net.thelightmc.core.build;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.extent.clipboard.io.SchematicReader;
import org.bukkit.Location;

import java.util.ArrayList;

public class MapCreator {
    protected MapCreator() {}
    private Location origin;
    private ArrayList<Map.Spawn> locations = new ArrayList<>();
    public static Map fromSchem(CuboidClipboard cc) {
        //Todo: This method to get from schem
        return null;
    }
    public void addSpawn(Map.Spawn spawn) {
        locations.add(spawn);
    }
    public void setOrigin(Location location) {
        origin = location;
    }
    public Map create() {
        Map map = new Map(origin);
        for (Map.Spawn spawn : locations) {
            map.addSpawn(spawn);
        }
        return map;
    }
}
