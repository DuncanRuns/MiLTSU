# MiLTSU
Just some basic Minecraft Loot Table (and) Sequence Utilities.

```java
// Loop through world seeds
for (long worldSeed = 0; worldSeed != -1; worldSeed++) {
    // Create a random sequence object
    WitherSkeletonRandomSequence wsrs = new WitherSkeletonRandomSequence(worldSeed);
    // Check for 3 skulls in the first 3 kills with a looting 3 sword
    if (wsrs.has3SkullsOutOf(3, 3)) {
        // Print successful seed and return
        System.out.println(worldSeed);
        return;
    }
}
```

## MiLTSU but in C

Random sequence stuff implemented in C:
[random_sequence_c_stuff.zip](https://github.com/DuncanRuns/MiLTSU/files/11916009/random_sequence_c_stuff.zip)

## Include in your own project

We can use JitPack to include the projects directly from GitHub:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```
And include MiLTSU in your dependencies:
```groovy
dependencies {
    implementation 'com.github.DuncanRuns:MiLTSU:-SNAPSHOT'
}
```