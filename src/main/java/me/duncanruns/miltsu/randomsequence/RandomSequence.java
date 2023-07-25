package me.duncanruns.miltsu.randomsequence;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import me.duncanruns.miltsu.random.RandomUtil;
import me.duncanruns.miltsu.random.X128PPRandom;

public abstract class RandomSequence {
    private static final HashFunction MD5_HASH = Hashing.md5();
    protected final X128PPRandom random;

    public RandomSequence(long worldSeed, String identifier) {
        // Create unmixed Xoroshiro seed
        long unmixedLow = worldSeed ^ 0x6A09E667F3BCC909L;
        long unmixedHi = unmixedLow - 7046029254386353131L;

        // Create identifier Xoroshiro seed
        byte[] bs = MD5_HASH.hashString(identifier, Charsets.UTF_8).asBytes();
        long identifierLow = Longs.fromBytes(bs[0], bs[1], bs[2], bs[3], bs[4], bs[5], bs[6], bs[7]);
        long identifierHi = Longs.fromBytes(bs[8], bs[9], bs[10], bs[11], bs[12], bs[13], bs[14], bs[15]);

        // Create split Xoroshiro seed
        long splitLow = unmixedLow ^ identifierLow;
        long splitHi = unmixedHi ^ identifierHi;

        // Create mixed Xoroshiro seed
        long mixedLow = RandomUtil.mixStafford13(splitLow);
        long mixedHi = RandomUtil.mixStafford13(splitHi);

        this.random = new X128PPRandom(mixedLow, mixedHi);
    }

    public X128PPRandom getRandom() {
        return random;
    }

    public abstract static class EntityRandomSequence extends RandomSequence {
        public EntityRandomSequence(long worldSeed, String entityName) {
            super(worldSeed, "minecraft:entities/" + entityName);
        }
    }
}
