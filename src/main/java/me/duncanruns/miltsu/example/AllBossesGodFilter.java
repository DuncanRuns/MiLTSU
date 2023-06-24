package me.duncanruns.miltsu.example;

import me.duncanruns.miltsu.randomsequence.BarteringRandomSequence;
import me.duncanruns.miltsu.randomsequence.BlazeRandomSequence;
import me.duncanruns.miltsu.randomsequence.WitherSkeletonRandomSequence;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AllBossesGodFilter {

    public static void main(String[] args) throws IOException {
        filterSeeds(new String[]{"7692429641256684269"});
    }

    public static void filterSeeds(String[] seeds) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Long> out = Collections.synchronizedList(new ArrayList<>());
        for (String arg : seeds) {
            final String toProcess = arg;
            executor.execute(() -> {
                long worldSeed = Long.parseLong(toProcess.strip());
                if (isGodSeed(worldSeed)) {
                    out.add(worldSeed);
                }
            });
        }
        executor.shutdown();
        try{
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (out.isEmpty()) {
            System.out.println("Found no god seeds out of the given seeds");
            return;
        }

        String fileName = "AB Seeds " + out.get(0) + ".txt";
        FileWriter writer = new FileWriter(fileName);
        for (Long seed : out) {
            writer.write(seed + "\n");
        }
        writer.close();
        System.out.println("Wrote god seeds to " + fileName);
    }

    public static boolean isGodSeed(long worldSeed) {
        BarteringRandomSequence bartering = new BarteringRandomSequence(worldSeed);
        int totalPearls = 0;
        for (int i = 0; i < 72; i++) {
            BarteringRandomSequence.BarteringOut out = bartering.next();
            if (out.name().equals("minecraft:ender_pearl")) {
                totalPearls += out.amount();
            }
        }
        if (totalPearls < 13) {
            return false;
        }

        if (!new BlazeRandomSequence(worldSeed).has6OutOf(9)) {
            return false;
        }

        return new WitherSkeletonRandomSequence(worldSeed).has3SkullsOutOf(9);
    }
}
