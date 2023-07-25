package me.duncanruns.miltsu.chestloot;

import me.duncanruns.miltsu.random.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RuinedPortalChest implements Chest {
    private static final List<LootEntry> ENTRIES = Arrays.asList(
            new LootEntry("minecraft:obsidian", 1, 2, 40, true, false),
            new LootEntry("minecraft:flint", 1, 4, 40, true, false),
            new LootEntry("minecraft:iron_nugget", 9, 18, 40, true, false),
            new LootEntry("minecraft:flint_and_steel", 1, 1, 40, false, false),
            new LootEntry("minecraft:fire_charge", 1, 1, 40, false, false),
            new LootEntry("minecraft:golden_apple", 1, 1, 15, false, false),
            new LootEntry("minecraft:gold_nugget", 4, 24, 15, true, false),
            new LootEntry("minecraft:golden_sword", 1, 1, 15, false, true),
            new LootEntry("minecraft:golden_axe", 1, 1, 15, false, true),
            new LootEntry("minecraft:golden_hoe", 1, 1, 15, false, true),
            new LootEntry("minecraft:golden_shovel", 1, 1, 15, false, true),
            new LootEntry("minecraft:golden_pickaxe", 1, 1, 15, false, true),
            new LootEntry("minecraft:golden_boots", 1, 1, 15, false, true),
            new LootEntry("minecraft:golden_chestplate", 1, 1, 15, false, true),
            new LootEntry("minecraft:golden_helmet", 1, 1, 15, false, true),
            new LootEntry("minecraft:golden_leggings", 1, 1, 15, false, true),
            new LootEntry("minecraft:glistering_melon_slice", 4, 12, 5, true, false),
            new LootEntry("minecraft:golden_horse_armor", 1, 1, 5, false, false),
            new LootEntry("minecraft:light_weighted_pressure_plate", 1, 1, 5, false, false),
            new LootEntry("minecraft:golden_carrot", 4, 12, 5, true, false),
            new LootEntry("minecraft:clock", 1, 1, 5, false, false),
            new LootEntry("minecraft:gold_ingot", 2, 8, 5, true, false),
            new LootEntry("minecraft:bell", 1, 1, 1, false, false),
            new LootEntry("minecraft:enchanted_golden_apple", 1, 1, 1, false, false),
            new LootEntry("minecraft:gold_block", 1, 2, 1, true, false)
    );
    private static final int TOTAL_WEIGHT = ENTRIES.stream().mapToInt(value -> value.weight).sum();


    private final long seed;

    public RuinedPortalChest(long seed) {
        this.seed = seed;
    }

    private static LootStack next(Random random) {
        int j = random.nextInt(TOTAL_WEIGHT);

        for (LootEntry lootEntry : ENTRIES) {
            if ((j -= lootEntry.weight) >= 0) continue;
            return rollEntry(lootEntry, random);
        }
        return null;
    }

    private static LootStack rollEntry(LootEntry lootEntry, Random random) {
        LootStack out = new LootStack(
                lootEntry.name,
                lootEntry.rollAmount ? RandomUtil.nextInt(random, lootEntry.min, lootEntry.max) : 1
        );

        if (lootEntry.enchantRandomly) {
            String enchant = null;
            switch (lootEntry.name) {
                case "minecraft:golden_sword":
                    enchant = Enchantments.SWORD_ENCHANTS[random.nextInt(Enchantments.SWORD_ENCHANTS.length)];
                    break;
                case "minecraft:golden_axe":
                    enchant = Enchantments.AXE_ENCHANTS[random.nextInt(Enchantments.AXE_ENCHANTS.length)];
                    break;
                case "minecraft:golden_hoe":
                case "minecraft:golden_shovel":
                case "minecraft:golden_pickaxe":
                    enchant = Enchantments.TOOL_ENCHANTS[random.nextInt(Enchantments.TOOL_ENCHANTS.length)];
                    break;
                case "minecraft:golden_boots":
                    enchant = Enchantments.BOOTS_ENCHANTS[random.nextInt(Enchantments.BOOTS_ENCHANTS.length)];
                    break;
                case "minecraft:golden_leggings":
                    enchant = Enchantments.LEGGINGS_ENCHANTS[random.nextInt(Enchantments.LEGGINGS_ENCHANTS.length)];
                    break;
                case "minecraft:golden_chestplate":
                    enchant = Enchantments.CHESTPLATE_ENCHANTS[random.nextInt(Enchantments.CHESTPLATE_ENCHANTS.length)];
                    break;
                case "minecraft:golden_helmet":
                    enchant = Enchantments.HELMET_ENCHANTS[random.nextInt(Enchantments.HELMET_ENCHANTS.length)];
                    break;
            }
            if (enchant == null) {
                return out;
            }
            out.setEnchant(enchant, RandomUtil.nextInt(random, 1, Enchantments.getMaxLevel(enchant)));
        }

        return out;
    }

    public List<LootStack> getLoot() {
        Random random = new Random(seed);
        int rolls = RandomUtil.nextInt(random, 4, 8);
        List<LootStack> items = new ArrayList<>();
        for (int i = 0; i < rolls; i++) {
            items.add(next(random));
        }
        return items;
    }

    private static class LootEntry {
        public final String name;
        public final int min;
        public final int max;
        public final int weight;
        public final boolean rollAmount;
        public final boolean enchantRandomly;

        private LootEntry(String name, int min, int max, int weight, boolean rollAmount, boolean enchantRandomly) {
            this.name = name;
            this.min = min;
            this.max = max;
            this.weight = weight;
            this.rollAmount = rollAmount;
            this.enchantRandomly = enchantRandomly;
        }
    }
}
