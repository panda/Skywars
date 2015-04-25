package net.thelightmc;

import org.bukkit.ChatColor;

public enum Language {
    NoPermission("NoPermission","&4No permission."),
    DeathmatchCountdown("DeathmatchCountdown","&5Deathmatch commencing in &6%s"),
    DeathmatchStarting("DeathmatchStarting","&6The deathmatch is now starting."),
    GameStart("GameStart","&9The game has started. Gain control over the others islands!"),
    PlayerDied("PlayerDied","&6{Player} &9has been removed from the game,"),
    PlayerJoinedGame("PlayerJoinedGame","&6{Player} &9has entered the game. &8(&6{REMAINING}&8)"),
    PlayerKilled("PlayerKilled","&6{Player} &9has been killed by &6{Killer}"),
    GameEnd("GameEnd", "&6The game has been ended");

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
