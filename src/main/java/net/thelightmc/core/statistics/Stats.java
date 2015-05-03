package net.thelightmc.core.statistics;

public enum Stats {
    Matches,Wins,Losses,Deaths,Kills,Score;

    @Override
    public String toString() {
        return name();
    }
}