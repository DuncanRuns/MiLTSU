package me.duncanruns.miltsu.randomsequence;

public class WitherSkeletonRandomSequence extends RandomSequence.EntityRandomSequence {
    public WitherSkeletonRandomSequence(long worldSeed) {
        super(worldSeed, "wither_skeleton");
    }

    public boolean nextHasSkull() {
        return nextHasSkull(0);
    }

    public boolean nextHasSkull(int lootingLevel) {
        // Throw out bones and coal random calls
        random.nextInt();
        random.nextInt();
        // Actual skull rng
        return random.nextFloat() < 0.025 + 0.01 * lootingLevel;
    }

    public boolean has3SkullsOutOf(int kills) {
        return has3SkullsOutOf(kills, 0);
    }

    public boolean has3SkullsOutOf(int kills, int lootingLevel) {
        int obtained = 0;
        for (int i = 0; i < kills; i++) {
            if (this.nextHasSkull(lootingLevel)) {
                if (++obtained == 3) {
                    break;
                }
            } else {
                // kills left minus plus skulls already obtained
                if (obtained + kills - i - 1 < 3) {
                    return false;
                }
            }
        }
        return obtained >= 3;
    }
}
