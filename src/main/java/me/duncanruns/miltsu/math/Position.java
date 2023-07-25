package me.duncanruns.miltsu.math;

public class Position {
    public static final Position ZERO = new Position(0, 0);
    public final int x;
    public final int z;

    public Position(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public Position toBlockPosition() {
        return this;
    }

    public ChunkPosition toChunkPosition() {
        return new Position.ChunkPosition(x >> 4, z >> 4);
    }

    public Position add(Position other) {
        return new Position(x + other.x, z + other.z);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }

    public static class ChunkPosition extends Position {
        public ChunkPosition(int x, int z) {
            super(x, z);
        }

        @Override
        public Position toBlockPosition() {
            return new Position(x << 4, z << 4);
        }

        @Override
        public ChunkPosition toChunkPosition() {
            return this;
        }
    }
}
