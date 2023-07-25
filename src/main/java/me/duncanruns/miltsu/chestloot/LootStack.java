package me.duncanruns.miltsu.chestloot;

public class LootStack {
    private final String name;
    private final int count;
    private String enchant;
    private int enchantLevel;

    public LootStack(String name, int count) {
        this.name = name;
        this.count = count;
        this.enchant = "";
        this.enchantLevel = 0;
    }

    public LootStack setEnchant(String enchant, int level) {
        this.enchant = enchant;
        this.enchantLevel = level;
        return this;
    }


    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getEnchant() {
        return enchant;
    }

    public int getEnchantLevel() {
        return enchantLevel;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Item:(");
        out.append(getName()).append(":").append(getCount());
        if (!getEnchant().isEmpty()) {
            out.append("{").append(getEnchant()).append(":").append(getEnchantLevel()).append("}");
        }
        return out.append(")").toString();
    }
}
