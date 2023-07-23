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
}
