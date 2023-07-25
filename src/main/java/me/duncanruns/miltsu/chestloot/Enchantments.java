package me.duncanruns.miltsu.chestloot;

import java.util.Objects;

public final class Enchantments {
    public static final Enchantment[] ALL_ENCHANTMENTS = new Enchantment[]{
            new Enchantment("protection", 4),
            new Enchantment("fire_protection", 4),
            new Enchantment("feather_falling", 4),
            new Enchantment("blast_protection", 4),
            new Enchantment("projectile_protection", 4),
            new Enchantment("respiration", 3),
            new Enchantment("aqua_affinity", 1),
            new Enchantment("thorns", 3),
            new Enchantment("depth_strider", 3),
            new Enchantment("frost_walker", 2),
            new Enchantment("binding_curse", 1),
            new Enchantment("soul_speed", 3),
            new Enchantment("swift_sneak", 3),
            new Enchantment("sharpness", 5),
            new Enchantment("smite", 5),
            new Enchantment("bane_of_arthropods", 5),
            new Enchantment("knockback", 2),
            new Enchantment("fire_aspect", 2),
            new Enchantment("looting", 3),
            new Enchantment("sweeping", 3),
            new Enchantment("efficiency", 5),
            new Enchantment("silk_touch", 1),
            new Enchantment("unbreaking", 3),
            new Enchantment("fortune", 3),
            new Enchantment("power", 5),
            new Enchantment("punch", 2),
            new Enchantment("flame", 1),
            new Enchantment("infinity", 1),
            new Enchantment("luck_of_the_sea", 3),
            new Enchantment("lure", 3),
            new Enchantment("loyalty", 3),
            new Enchantment("impaling", 5),
            new Enchantment("riptide", 3),
            new Enchantment("channeling", 1),
            new Enchantment("multishot", 1),
            new Enchantment("quick_charge", 3),
            new Enchantment("piercing", 4),
            new Enchantment("mending", 1),
            new Enchantment("vanishing_curse", 1)
    };

    // The order of the following lists affect the random selection, which can ultimately affect how many calls are done since max level 1 enchants do not use a call for level
    public static String[] BOOK_ENCHANTS = {"protection", "fire_protection", "feather_falling", "blast_protection", "projectile_protection", "respiration", "aqua_affinity", "thorns", "depth_strider", "frost_walker", "binding_curse", "sharpness", "smite", "bane_of_arthropods", "knockback", "fire_aspect", "looting", "sweeping", "efficiency", "silk_touch", "unbreaking", "fortune", "power", "punch", "flame", "infinity", "luck_of_the_sea", "lure", "loyalty", "impaling", "riptide", "channeling", "multishot", "quick_charge", "piercing", "mending", "vanishing_curse"};
    public static String[] TOOL_ENCHANTS = {"efficiency", "silk_touch", "unbreaking", "fortune", "mending", "vanishing_curse"};
    public static String[] AXE_ENCHANTS = {"sharpness", "smite", "bane_of_arthropods", "efficiency", "silk_touch", "unbreaking", "fortune", "mending", "vanishing_curse"};
    public static String[] SWORD_ENCHANTS = {"sharpness", "smite", "bane_of_arthropods", "knockback", "fire_aspect", "looting", "sweeping", "unbreaking", "mending", "vanishing_curse"};
    public static String[] BOOTS_ENCHANTS = {"protection", "fire_protection", "feather_falling", "blast_protection", "projectile_protection", "thorns", "depth_strider", "frost_walker", "binding_curse", "unbreaking", "mending", "vanishing_curse"};
    public static String[] LEGGINGS_ENCHANTS = {"protection", "fire_protection", "blast_protection", "projectile_protection", "thorns", "binding_curse", "unbreaking", "mending", "vanishing_curse"};
    public static String[] CHESTPLATE_ENCHANTS = {"protection", "fire_protection", "blast_protection", "projectile_protection", "thorns", "binding_curse", "unbreaking", "mending", "vanishing_curse"};
    public static String[] HELMET_ENCHANTS = {"protection", "fire_protection", "blast_protection", "projectile_protection", "respiration", "aqua_affinity", "thorns", "binding_curse", "unbreaking", "mending", "vanishing_curse"};

    private Enchantments() {
    }

    public static int getMaxLevel(String enchantmentName) {
        for (Enchantment enchantment : ALL_ENCHANTMENTS) {
            if (Objects.equals(enchantmentName, enchantment.name)) {
                return enchantment.maxLevel;
            }
        }
        return -1;
    }

    public static class Enchantment {
        public final String name;
        public final int maxLevel;

        public Enchantment(String name, int maxLevel) {
            this.name = name;
            this.maxLevel = maxLevel;
        }
    }
}
