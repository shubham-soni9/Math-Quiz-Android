package com.mathgame.util;

public class RandomUtils {

    public static double getRandomDouble(int max) {
        return (Math.random() * max);
    }

    public static double getRandomDouble(int maximum, int minimum) {
        return Math.random() * (maximum - minimum) + minimum;
    }

    public static int getRandomInt(int max) {
        return (int) (Math.random() * max);
    }

    public static int getRandomInt(int maximum, int minimum) {
        return (int) (Math.random() * (maximum - minimum) + minimum);
    }

}
