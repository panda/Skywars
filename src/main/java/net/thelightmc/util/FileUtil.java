package net.thelightmc.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    //ToDo a lot of work on here
    private static String directory = "plugins/Skywars/";
    public static FileConfiguration getFile(String fileName) throws IOException {
        File file = getFile(fileName,true);
        return YamlConfiguration.loadConfiguration(file);
    }
    public static File getFile(String fileName,boolean createIfNotExist) throws IOException {
        File file = new File(directory,fileName);
        if (createIfNotExist && !file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
