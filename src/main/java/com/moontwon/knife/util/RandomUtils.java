package com.moontwon.knife.util;

import java.util.Random;

public class RandomUtils {
    public static long nextLong(long startInclusive, long endInclusive, Random random) {
        return (long) (startInclusive + ((endInclusive - startInclusive) * random.nextDouble()));
    }

    public static int nextInet(int startInclusive, int endInclusive, Random random) {
        return (int) (startInclusive + ((endInclusive - startInclusive) * random.nextDouble()));
    }
}
