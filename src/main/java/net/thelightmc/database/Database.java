package net.thelightmc.database;

import net.thelightmc.core.player.GamePlayer;
import net.thelightmc.util.FileUtil;

public interface Database {

    boolean loadPlayer(GamePlayer gamePlayer);

    boolean savePlayer(GamePlayer gamePlayer);

    DataType getDatatype();

    enum DataType {
        MySQL,Redis,MongoDB,JSON;
    }
}
