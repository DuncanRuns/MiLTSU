package me.duncanruns.miltsu.randomsequence;

import java.util.List;

public class BarteringRandomSequence extends RandomSequence {
    private static List<Entry> entries = List.of(
            new Entry("minecraft:book", 0, 0, 5, false, true),
            new Entry("minecraft:iron_boots", 0, 0, 8, false, true),
            new Entry("minecraft:potion", 0, 0, 8, false, false),
            new Entry("minecraft:splash_potion", 0, 0, 8, false, false),
            new Entry("minecraft:potion", 0, 0, 10, false, false),
            new Entry("minecraft:iron_nugget", 10, 36, 10, true, false),
            new Entry("minecraft:ender_pearl", 2, 4, 10, true, false),
            new Entry("minecraft:string", 3, 9, 20, true, false),
            new Entry("minecraft:quartz", 5, 12, 20, true, false),
            new Entry("minecraft:obsidian", 1, 1, 40, false, false),
            new Entry("minecraft:crying_obsidian", 1, 3, 40, true, false),
            new Entry("minecraft:fire_charge", 1, 1, 40, false, false),
            new Entry("minecraft:leather", 2, 4, 40, true, false),
            new Entry("minecraft:soul_sand", 2, 8, 40, true, false),
            new Entry("minecraft:nether_brick", 2, 8, 40, true, false),
            new Entry("minecraft:spectral_arrow", 6, 12, 40, true, false),
            new Entry("minecraft:gravel", 8, 16, 40, true, false),
            new Entry("minecraft:blackstone", 8, 16, 40, true, false)
    );
    private static final int TOTAL_WEIGHT = entries.stream().mapToInt(value -> value.weight).sum();

    public BarteringRandomSequence(long worldSeed) {
        super(worldSeed, "minecraft:gameplay/piglin_bartering");
    }

    public BarteringOut next() {
        int j = random.nextInt(TOTAL_WEIGHT);

        for (Entry entry : entries) {
            if ((j -= entry.weight) >= 0) continue;
            return this.rollEntry(entry);
        }
        return null;
    }

    private BarteringOut rollEntry(Entry entry) {
        int amount = 1;
        if (entry.rollAmount) {
            amount = random.nextInt(entry.min, entry.max);
        }
        if (entry.rollSS) {
            random.nextInt(1); // Randomly choose out of a list of a single enchantment
            amount = random.nextInt(1, 3); // Choose soul speed level
        }
        return new BarteringOut(entry.name, amount);
    }

    private record Entry(String name, int min, int max, int weight, boolean rollAmount, boolean rollSS) {
    }

    /**
     * @param name the identifier for the Minecraft item
     * @param amount if name is minecraft:iron_boots, this represents the level of soul speed on the boots, otherwise the amount of items in the stack
     */
    public record BarteringOut(String name, int amount) {
        @Override
        public String toString() {
            return name + ":" + amount;
        }
    }
}
