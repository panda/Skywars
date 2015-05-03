package net.thelightmc.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FileUtil {
    private static String directory = "plugins/Skywars/";
    public static FileConfiguration getFile(String fileName) throws IOException {
        File file = getFile(fileName,true);
        return YamlConfiguration.loadConfiguration(file);
    }
    public static File getFile(String fileName,boolean createIfNotExist) throws IOException {
        File file = new File(directory,fileName);
        if (createIfNotExist && !file.exists()) {
            boolean success = file.createNewFile();
            if (!success) {
                throw new IOException("File couldn't be created");
            }
        }
        return file;
    }
    public static List<File> getAllFiletype(String ending) {
        List<File> files = new ArrayList<>();
        File dir = new File(directory);
        try {
            for (File file : dir.listFiles())
                if (file.getName().endsWith(ending)) {
                    files.add(file);
                }
        } catch (NullPointerException ex) {
            Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin(directory.split("/")[1]));
            Bukkit.getLogger().severe("No files found. Now disabing.");
            ex.printStackTrace();
        }
        return files;
    }
    public static File getRandomFile(List<File> fileList) {
        Random random = ThreadLocalRandom.current();
        return fileList.get(random.nextInt(fileList.size()));
    }
    public static String getDirectory() {
        return directory;
    }
}
