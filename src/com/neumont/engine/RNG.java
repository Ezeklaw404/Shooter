package com.neumont.engine;

import java.util.Random;

public class RNG {
    static Random random = new Random();

    public static void seed(long seed){
        random.setSeed(seed);
    }

    public static boolean bool(){
        return random.nextBoolean();
    }
    public static int range(int max){
        return random.nextInt(max);
    }

    public static int range(int min, int max){
        return random.nextInt(min,max);
    }

    public static double range(double max){
        return random.nextDouble(max);
    }

    public static double range(double min, double max){
        return random.nextDouble(min,max);
    }
}
