package net.thelightmc.database;

import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.util.FileUtil;

public abstract class Database {
    private final DataType dataType;
    private final String flatDirectory;

    protected Database(DataType dataType) {
        this.dataType = dataType;
        this.flatDirectory = FileUtil.getDirectory();
    }

    protected String getFlatDirectory() {
        return flatDirectory;
    }

    public DataType getDataType() {
        return dataType;
    }

    public abstract boolean loadPlayer(GamePlayer gamePlayer);

    public abstract boolean savePlayer(GamePlayer gamePlayer);

    public enum DataType {
        MySQL,Redis,MongoDB,JSON;
    }
}
