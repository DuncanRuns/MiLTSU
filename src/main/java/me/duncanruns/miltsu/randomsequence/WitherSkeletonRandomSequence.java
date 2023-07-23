package me.duncanruns.miltsu.randomsequence;

public class WitherSkeletonRandomSequence extends RandomSequence.EntityRandomSequence {
    public WitherSkeletonRandomSequence(long worldSeed) {
        super(worldSeed, "wither_skeleton");
    }

    public boolean nextHasSkull() {
        return nextHasSkull(0);
    }

    /**
     * Uses 3 calls to the random object without a looting sword, and 5 with a looting sword.
     * If a wither skeleton is not killed by the player it will use up 2 calls, so this sequence can get messy.
     */
    public boolean nextHasSkull(int lootingLevel) {
        // Throw out bones and coal random calls (looting, no matter the level, is just 1 extra call for both coal and bones)
        for (int i = 0; i < 2 + (lootingLevel > 0 ? 2 : 0); i++) {
            random.nextInt();
        }
        // Actual skull rng
        return random.nextFloat() < 0.025 + (0.01 * lootingLevel);
    }

    /**
     * Simulates killing some wither skeletons without a looting sword
     *
     * @return true if the amount of wither skeletons killed would drop at least 3 skulls
     */
    public boolean has3SkullsOutOf(int kills) {
        return has3SkullsOutOf(kills, 0);
    }

    /**
     * Simulates killing some wither skeletons with a looting sword of the specified level
     *
     * @return true if the amount of wither skeletons killed would drop at least 3 skulls
     */
    public boolean has3SkullsOutOf(int kills, int lootingLevel) {
        int obtained = 0;
        for (int i = 0; i < kills; i++) {
            if (this.nextHasSkull(lootingLevel)) {
                if (++obtained == 3) {
                    return true;
                }
            }
        }
        return false;
    }
}
