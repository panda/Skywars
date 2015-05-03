package net.thelightmc.core.scoreboard;

public enum ScoreboardUpdate {
    //Testing this out 50% chance I'll change this later
    AddPlayer,RemovePlayer,StartGame,Countdown;
    private String value;

    private Integer score;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
