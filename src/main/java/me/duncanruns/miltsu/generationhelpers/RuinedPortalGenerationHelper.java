package me.duncanruns.miltsu.generationhelpers;

import me.duncanruns.miltsu.math.Position;
import me.duncanruns.miltsu.random.RandomUtil;
import me.duncanruns.miltsu.random.X128PPRandom;

import java.util.Random;

/**
 * Important:
 * This is only really tested with non-underground, non-giant, structureIndex 10 ruined portals.
 * I am confident that anything involving generateChestPos would break with ruined portals that do not match this description.
 */
public class RuinedPortalGenerationHelper {
    private static final int CLOCKWISE_90 = 1;
    private static final int CLOCKWISE_180 = 2;
    private static final int COUNTERCLOCKWISE_90 = 3;

    private static final Position[] SIZES = new Position[]{
            new Position(6, 6), //1
            new Position(9, 9),
            new Position(8, 9),
            new Position(8, 9),
            new Position(10, 7),
            new Position(5, 7),
            new Position(9, 9),
            new Position(14, 9),
            new Position(10, 9),
            new Position(12, 10) // 10
    };
    private static final Position[] CHEST_OFFSETS = new Position[]{
            new Position(2, 0),//1
            new Position(8, 7),
            new Position(3, 6),
            new Position(3, 2),
            new Position(4, 2),
            new Position(1, 4),
            new Position(0, 2),
            new Position(4, 2),
            new Position(4, 0),
            new Position(2, 7) //10
    };
    private static final Position[] GIANT_SIZES = new Position[]{
            new Position(11, 16),
            new Position(11, 16),
            new Position(16, 16)
    };
    private static final Position[] GIANT_CHEST_OFFSETS = new Position[]{
            new Position(4, 3),
            new Position(9, 9),
            new Position(9, 3)
    };

    /**
     * All ruined portal's generationStep should be 4 (surface structures step)
     * <p>
     * The following are structure indices for different ruined portal biome locations:
     * <li>ruined_portal_standard = 10</li>
     * <li>ruined_portal_desert = 11</li>
     * <li>ruined_portal_jungle = 12</li>
     * <li>ruined_portal_mountain = 13</li>
     * <li>ruined_portal_nether = 14</li>
     * <li>ruined_portal_ocean = 15</li>
     * <li>ruined_portal_swamp = 16</li>
     */
    public static long getChestSeed(long worldSeed, Position.ChunkPosition chunkWithChest, int structureIndex, int generationStep, int throwAway) {
        Position minPos = chunkWithChest.toBlockPosition();
        long popSeed = X128PPRandom.getPopulationSeed(worldSeed, minPos.x, minPos.z);
        X128PPRandom chunkRandom = X128PPRandom.fromDecorator(popSeed, structureIndex, generationStep);
        for (int i = 0; i < throwAway; i++) {
            chunkRandom.nextInt();
        }
        return chunkRandom.nextLong();
    }

    public static Position generateChestPos(long worldSeed, Position.ChunkPosition startChunk) {
        Random chunkRandom = RandomUtil.fromCarverSeed(worldSeed, startChunk.x, startChunk.z);

        boolean underground = chunkRandom.nextFloat() < 0.5f;
        boolean airpocket = underground || chunkRandom.nextFloat() < 0.5f;
        boolean giant = chunkRandom.nextFloat() < 0.05f;
        int variant = chunkRandom.nextInt(giant ? 3 : 10) + 1;

        int rotation = chunkRandom.nextInt(4);
        boolean mirror = !(chunkRandom.nextFloat() < 0.5f);

        return getChestPos(startChunk, variant, giant, mirror, rotation);
    }

    public static int generateVariant(long worldSeed, Position.ChunkPosition startChunk) {
        Random chunkRandom = RandomUtil.fromCarverSeed(worldSeed, startChunk.x, startChunk.z);

        boolean underground = chunkRandom.nextFloat() < 0.5f;
        boolean airpocket = underground || chunkRandom.nextFloat() < 0.5f;
        boolean giant = chunkRandom.nextFloat() < 0.05f;

        return (giant ? 100 : 0) + chunkRandom.nextInt(giant ? 3 : 10) + 1;
    }

    public static Position getChestPos(Position.ChunkPosition startChunk, int variant, boolean giant, boolean mirror, int rotation) {
        Position size = giant ? GIANT_SIZES[variant - 1] : SIZES[variant - 1];
        Position pivot = new Position(size.getX() / 2, size.getZ() / 2);
        Position chunkPosStart = startChunk.toBlockPosition();

        Position newStructureStart = Position.ZERO;
        int px = pivot.getX();
        int pz = pivot.getZ();
        switch (rotation) {
            case CLOCKWISE_90:
                newStructureStart = new Position(px + pz, pz - px);
                break;
            case CLOCKWISE_180:
                newStructureStart = new Position(px + px, pz + pz);
                break;
            case COUNTERCLOCKWISE_90:
                newStructureStart = new Position(px - pz, px + pz);
                break;
        }
        Position startPos = newStructureStart.add(chunkPosStart);

        Position realChestOff = null;
        Position normalChestOff = giant ? GIANT_CHEST_OFFSETS[variant - 1] : CHEST_OFFSETS[variant - 1];
        int coX = normalChestOff.getX();
        int coZ = normalChestOff.getZ();
        if (mirror) {
            coX = -coX;
        }
        switch (rotation) {
            case CLOCKWISE_180:
                realChestOff = new Position(-coX, -coZ);
                break;
            case COUNTERCLOCKWISE_90:
                realChestOff = new Position(coZ, -coX);
                break;
            case CLOCKWISE_90:
                realChestOff = new Position(-coZ, coX);
                break;
        }
        if (realChestOff == null) {
            realChestOff = mirror ? new Position(coX, coZ) : normalChestOff;
        }
        return startPos.add(realChestOff);
    }
}
