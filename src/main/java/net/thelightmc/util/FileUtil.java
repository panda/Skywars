package net.thelightmc.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static FileConfiguration getFile(String fileName) throws IOException {
        File file = getFile(fileName,true);
        return YamlConfiguration.loadConfiguration(file);
    }
    public static File getFile(String fileName,boolean createIfNotExist) throws IOException {
        String directory = "plugins/Skywars/";
        File file = new File(directory,fileName);
        if (createIfNotExist && !file.exists()) {
            boolean success = file.createNewFile();
            if (!success) {
                throw new IOException("File couldn't be created");
            }
        }
        return file;
    }
}
