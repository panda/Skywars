package net.thelightmc;

import org.bukkit.ChatColor;

public enum  Language {
    NoPermission("NoPermission","&4No permission."),
    DeathmatchCountdown("DeathmatchCountdown","&5Deathmatch commencing in &6%s"),
    DeathmatchStarting("DeathmatchStarting","&6The deathmatch is now starting."),
    PlayerDied("PlayerDied","&6{Player} &9has been removed from the game,"),
    PlayerKilled("PlayerKilled","&6{Player} &9has been killed by &6{Killer}");
    private final String path;
    private final String msg;
    Language(String path,String msg) {
        this.path = path;
        this.msg = msg;
    }

    private void load() {

    }

    private String getPath() {
        return path;
    }

    public String getMsg() {
        return ChatColor.translateAlternateColorCodes('&',msg);
    }
    public String getFormat(Object... objects) {
        return String.format(getMsg(),objects);
    }
    @Override
    public String toString() {
        return getMsg();
    }
}
