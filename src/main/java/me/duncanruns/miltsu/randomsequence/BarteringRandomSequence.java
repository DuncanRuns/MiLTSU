package me.duncanruns.miltsu.randomsequence;

import java.util.Arrays;
import java.util.List;

public class BarteringRandomSequence extends RandomSequence {
    private static final List<LootEntry> ENTRIES = Arrays.asList(
            new LootEntry("minecraft:book", 0, 0, 5, false, true),
            new LootEntry("minecraft:iron_boots", 0, 0, 8, false, true),
            new LootEntry("minecraft:potion", 0, 0, 8, false, false),
            new LootEntry("minecraft:splash_potion", 0, 0, 8, false, false),
            new LootEntry("minecraft:potion", 0, 0, 10, false, false),
            new LootEntry("minecraft:iron_nugget", 10, 36, 10, true, false),
            new LootEntry("minecraft:ender_pearl", 2, 4, 10, true, false),
            new LootEntry("minecraft:string", 3, 9, 20, true, false),
            new LootEntry("minecraft:quartz", 5, 12, 20, true, false),
            new LootEntry("minecraft:obsidian", 1, 1, 40, false, false),
            new LootEntry("minecraft:crying_obsidian", 1, 3, 40, true, false),
            new LootEntry("minecraft:fire_charge", 1, 1, 40, false, false),
            new LootEntry("minecraft:leather", 2, 4, 40, true, false),
            new LootEntry("minecraft:soul_sand", 2, 8, 40, true, false),
            new LootEntry("minecraft:nether_brick", 2, 8, 40, true, false),
            new LootEntry("minecraft:spectral_arrow", 6, 12, 40, true, false),
            new LootEntry("minecraft:gravel", 8, 16, 40, true, false),
            new LootEntry("minecraft:blackstone", 8, 16, 40, true, false)
    );
    private static final int TOTAL_WEIGHT = ENTRIES.stream().mapToInt(value -> value.weight).sum();

    public BarteringRandomSequence(long worldSeed) {
        super(worldSeed, "minecraft:gameplay/piglin_bartering");
    }

    public BarteringOut next() {
        int j = random.nextInt(TOTAL_WEIGHT);

        for (LootEntry lootEntry : ENTRIES) {
            if ((j -= lootEntry.weight) >= 0) continue;
            return this.rollEntry(lootEntry);
        }
        return null;
    }

    private BarteringOut rollEntry(LootEntry lootEntry) {
        int amount = 1;
        if (lootEntry.rollAmount) {
            amount = random.nextInt(lootEntry.min, lootEntry.max);
        }
        if (lootEntry.rollSS) {
            random.nextInt(1); // Randomly choose out of a list of a single enchantment
            amount = random.nextInt(1, 3); // Choose soul speed level
        }
        return new BarteringOut(lootEntry.name, amount);
    }

    private static class LootEntry {
        public final String name;
        public final int min;
        public final int max;
        public final int weight;
        public final boolean rollAmount;
        public final boolean rollSS;

        private LootEntry(String name, int min, int max, int weight, boolean rollAmount, boolean rollSS) {
            this.name = name;
            this.min = min;
            this.max = max;
            this.weight = weight;
            this.rollAmount = rollAmount;
            this.rollSS = rollSS;
        }
    }

    public static class BarteringOut {
        public final String name;
        public final int amount;

        /**
         * @param name   the identifier for the Minecraft item
         * @param amount if name is minecraft:iron_boots, this represents the level of soul speed on the boots, otherwise the amount of items in the stack
         */
        public BarteringOut(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return name + ":" + amount;
        }
    }
}
