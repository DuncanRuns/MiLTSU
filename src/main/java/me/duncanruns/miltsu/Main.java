package me.duncanruns.miltsu;

import me.duncanruns.miltsu.example.AllBossesGodFilter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        runABExample(args);
    }

    private static void runABExample(String[] args) {
        if (args.length == 0) {
            System.out.println("MiLTSU (Minecraft Loot Table Sequence Utilities)\nTo run the All Bosses Filter example, use the follwing command:\njava mitsu.jar 1 2 3 4\nwhere 1 2 3 and 4 are seeds.");
            return;
        }
        try {
            AllBossesGodFilter.filterSeeds(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
