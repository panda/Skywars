package net.thelightmc.core.statistics;

public class StatValue<T> {
    private T value;
    public StatValue() {}
    public StatValue(T defaultValue) {
        value = defaultValue;
    }
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
