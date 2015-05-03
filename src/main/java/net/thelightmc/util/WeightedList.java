package net.thelightmc.util;

import java.util.*;

//Huge thanks to tkausl for this part
public class WeightedList<E> {
    private final Map<E, Integer> map = new HashMap<>();
    private final Random random;

    public WeightedList(Random random) {
        this.random = random;
    }

    public void add(int percentage, E result) {
        map.put(result, percentage);

    }

    public List<E> values(int max) {
        List<E> ret = new ArrayList<>();
        for(Map.Entry<E, Integer> e : map.entrySet()){
            if((random.nextInt(100) < e.getValue() && ret.size() < max) || e.getValue()==100)
                ret.add(e.getKey());
        }
        return ret;
    }
    private void shake() {
        Map<E,Integer> shakeMap = new HashMap<>();
        while (true) {

        }
    }
}