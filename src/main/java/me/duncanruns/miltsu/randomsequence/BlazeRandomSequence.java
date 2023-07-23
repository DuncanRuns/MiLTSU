package me.duncanruns.miltsu.randomsequence;

public class BlazeRandomSequence extends RandomSequence.EntityRandomSequence {
    public BlazeRandomSequence(long worldSeed) {
        super(worldSeed, "blaze");
    }

    /**
     * @return true if the next blaze drops a rod without using a looting sword
     */
    public boolean nextDropsRod() {
        return nextRods(0) == 1;
    }

    /**
     * Will use up 1 call to the random object without looting, and 2 with any level of looting
     */
    public int nextRods(int looting) {
        int setCount = random.nextInt(0, 1);
        if (looting == 0) {
            return setCount;
        }
        return setCount + Math.round(random.nextFloat() * looting);
    }


    /**
     * Helpful for finding speedrun seeds, assumes no looting sword
     */
    public boolean has6OutOf(int kills) {
        int obtained = 0;
        for (int i = 0; i < kills; i++) {
            if (this.nextDropsRod()) {
                if (++obtained == 6) {
                    break;
                }
            } else {
                // kills left minus plus skulls already obtained
                if (obtained + kills - i - 1 < 3) {
                    return false;
                }
            }
        }
        return obtained >= 6;
    }
}
