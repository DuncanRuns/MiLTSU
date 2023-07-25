package me.duncanruns.miltsu.random;

public class X128PPRandom {
    private long seedLo;
    private long seedHi;

    public X128PPRandom(long lo, long hi) {
        this.seedLo = lo;
        this.seedHi = hi;
        if ((this.seedLo | this.seedHi) == 0L) {
            this.seedLo = -7046029254386353131L;
            this.seedHi = 7640891576956012809L;
        }
    }

    public static X128PPRandom fromLongSeed(long seed) {
        long unmixedLow = seed ^ 0x6A09E667F3BCC909L;
        long unmixedHigh = unmixedLow + -7046029254386353131L;
        return new X128PPRandom(RandomUtil.mixStafford13(unmixedLow), RandomUtil.mixStafford13(unmixedHigh));
    }

    public static long getPopulationSeed(long worldSeed, int blockX, int blockZ) {
        X128PPRandom random;
        random = fromLongSeed(worldSeed);
        long l = random.nextLong() | 1L;
        long m = random.nextLong() | 1L;
        return (long) blockX * l + (long) blockZ * m ^ worldSeed;
    }

    public static X128PPRandom fromDecorator(long popSeed, int index, int step) {
        return fromLongSeed(popSeed + (long) index + (10000L * step));
    }

    public long next() {
        long lo = this.seedLo;
        long hi = this.seedHi;
        long n = Long.rotateLeft(lo + hi, 17) + lo;
        this.seedLo = Long.rotateLeft(lo, 49) ^ (hi ^= lo) ^ hi << 21;
        this.seedHi = Long.rotateLeft(hi, 28);
        return n;
    }

    private long next(int bits) {
        return this.next() >>> 64 - bits;
    }

    public int nextInt() {
        return (int) this.next();
    }

    public int nextInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        }
        long l = Integer.toUnsignedLong(this.nextInt());
        long m = l * (long) bound;
        long n = m & 0xFFFFFFFFL;
        if (n < (long) bound) {
            int i = Integer.remainderUnsigned(~bound + 1, bound);
            while (n < (long) i) {
                l = Integer.toUnsignedLong(this.nextInt());
                m = l * (long) bound;
                n = m & 0xFFFFFFFFL;
            }
        }
        long o = m >> 32;
        return (int) o;
    }

    public boolean nextBoolean() {
        return (this.next() & 1L) != 0L;
    }

    public float nextFloat() {
        return (float) this.next(24) * 5.9604645E-8f;
    }

    public int nextInt(int min, int max) {
        if (min >= max) {
            return min;
        }
        return this.nextInt(max - min + 1) + min;
    }

    public long nextLong() {
        int high32 = (int) this.next(32);
        int low32 = (int) this.next(32);
        return (((long) high32) << 32) + (long) low32;
    }
}
