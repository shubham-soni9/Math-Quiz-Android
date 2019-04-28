package com.mathgame.util;

public class RandomUtils {
    public static int getRandomInteger(int max) {
        return (int) (Math.random() * max);
    }

    public static int getRandomInteger(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }
}
