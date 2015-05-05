package net.thelightmc;

import org.bukkit.ChatColor;

public enum Language {
    NoPermission("NoPermission","&4No permission."),
    PlayerNotFound("PlayerNotFound","&cThat player is not online."),
    PlayerNoGame("PlayerNotInGame","&cThat player is not currently in a game."),
    PlayerNowSpectating("PlayerSpectatingYou","&eThe player {Player} has started to spectate you."),
    SpectateModeEnabled("SpectateEnabled","&eYou have enabled spectate mode."),
    CommandsInArena("CommandsInArena","&cAll commands have been disabled while in the arena."),
    DeathmatchCountdown("DeathmatchCountdown","&5Deathmatch commencing in &6{Time}"),
    DeathmatchStarting("DeathmatchStarting","&6The deathmatch is now starting."),
    GameStart("GameStart","&9The game has started. Gain control over the others islands!"),
    PlayerDied("PlayerDied","&6{Player} &9has been removed from the game,"),
    PlayerJoinedGame("PlayerJoinedGame","&6{Player} &ehas joined the game &8(&6{Remaining}&8)"),
    PlayerKilled("PlayerKilled","&6{Player} &9has been killed by &6{Killer}"),
    PlayerQuit("PlayerQuit","6{Player} &ehas left the game &8(&6{Remaining}&8)"),
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
