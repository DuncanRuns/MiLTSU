package me.duncanruns.miltsu.random;

import java.util.Random;

public class RandomUtil {
    public static long mixStafford13(long seed) {
        seed = (seed ^ seed >>> 30) * -4658895280553007687L;
        seed = (seed ^ seed >>> 27) * -7723592293110705685L;
        return seed ^ seed >>> 31;
    }

    public static Random fromCarverSeed(long worldSeed, int chunkX, int chunkZ) {
        Random random = new Random(worldSeed);
        long rf1 = random.nextLong();
        long rf2 = random.nextLong();
        random.setSeed((long) chunkX * rf1 ^ (long) chunkZ * rf2 ^ worldSeed);
        return random;
    }


    public static float nextFloat(Random random, float min, float max) {
        if (min >= max) {
            return min;
        }
        return random.nextFloat() * (max - min) + min;
    }


    public static int nextInt(Random random, int min, int max) {
        if (min >= max) {
            return min;
        }
        return random.nextInt(max - min + 1) + min;
    }
}
