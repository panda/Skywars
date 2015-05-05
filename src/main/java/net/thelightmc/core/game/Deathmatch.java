package net.thelightmc.core.game;

public interface Deathmatch {
    void startDeathmatch();
    void deathMatchTick();
    boolean isDeathmatchStarted();
    void announceTimer(int ctr);
}
