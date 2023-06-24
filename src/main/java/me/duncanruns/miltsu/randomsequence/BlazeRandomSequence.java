package me.duncanruns.miltsu.randomsequence;

public class BlazeRandomSequence extends RandomSequence.EntityRandomSequence {
    public BlazeRandomSequence(long worldSeed) {
        super(worldSeed, "blaze");
    }

    public boolean nextDropsRod() {
        return random.nextInt(0, 1) == 1;
    }


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
