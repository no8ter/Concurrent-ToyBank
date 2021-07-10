package ru.rsf.Concurrent.ToyBank;

import java.util.function.Supplier;

public class WarmingService implements Supplier<Integer> {
    @Override
    public Integer get() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        return (int) (Math.random() * 10000);
    }
}
