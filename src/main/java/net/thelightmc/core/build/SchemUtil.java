package net.thelightmc.core.build;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;

public class SchemUtil {
    private Map loadArea(World world, File file,Vector origin) throws DataException, IOException, MaxChangedBlocksException {
        EditSession es = new EditSession(new BukkitWorld(world), 999999999);
        CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
        cc.paste(es, origin, false);
        return new MapCreator().fromSchem(cc);
    }
}
