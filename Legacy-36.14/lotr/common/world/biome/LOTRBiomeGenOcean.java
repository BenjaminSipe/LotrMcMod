package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntitySeagull;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenSeaBlock;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure.LOTRWorldGenUnderwaterElvenRuin;
import lotr.common.world.structure2.LOTRWorldGenNumenorRuin;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenOcean extends LOTRBiome {
   private static Random iceRand = new Random();
   private static final int iceLimitSouth = -30000;
   private static final int iceLimitNorth = -60000;
   private static final int palmStartZ = 64000;
   private static final int palmFullZ = 130000;
   private WorldGenerator spongeGen;
   private WorldGenerator coralGen;

   public LOTRBiomeGenOcean(int i, boolean major) {
      super(i, major);
      this.spongeGen = new LOTRWorldGenSeaBlock(Blocks.field_150360_v, 0, 24);
      this.coralGen = new LOTRWorldGenSeaBlock(LOTRMod.coralReef, 0, 64);
      this.field_76755_L.add(new SpawnListEntry(EntitySquid.class, 4, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntitySeagull.class, 20, 4, 4));
      this.npcSpawnList.clear();
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreSalt, 8), 4.0F, 0, 64);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreSalt, 8, Blocks.field_150354_m), 0.5F, 56, 80);
      this.decorator.addOre(new WorldGenMinable(LOTRMod.oreSalt, 8, LOTRMod.whiteSand), 0.5F, 56, 80);
      this.decorator.treesPerChunk = 1;
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 2;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.addTree(LOTRTreeType.OAK, 1000);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.BEECH, 50);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 5);
      this.decorator.addTree(LOTRTreeType.APPLE, 3);
      this.decorator.addTree(LOTRTreeType.PEAR, 3);
      this.decorator.addRandomStructure(new LOTRWorldGenNumenorRuin(false), 500);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.OCEAN;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.SEA.getSubregion("sea");
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterOcean;
   }

   public boolean getEnableRiver() {
      return false;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int i1;
      int palms;
      int l;
      if (i < LOTRWaypoint.MITHLOND_SOUTH.getXCoord() && k > LOTRWaypoint.SOUTH_FOROCHEL.getZCoord() && k < LOTRWaypoint.ERYN_VORN.getZCoord() && random.nextInt(200) == 0) {
         i1 = i + random.nextInt(16) + 8;
         palms = k + random.nextInt(16) + 8;
         l = world.func_72825_h(i1, palms);
         (new LOTRWorldGenUnderwaterElvenRuin(false)).func_76484_a(world, random, i1, l, palms);
      }

      if (k > -30000) {
         if (random.nextInt(12) == 0) {
            i1 = i + random.nextInt(16) + 8;
            palms = k + random.nextInt(16) + 8;
            l = world.func_72825_h(i1, palms);
            if (l < 60 || random.nextBoolean()) {
               this.spongeGen.func_76484_a(world, random, i1, l, palms);
            }
         }

         if (random.nextInt(4) == 0) {
            i1 = i + random.nextInt(16) + 8;
            palms = k + random.nextInt(16) + 8;
            l = world.func_72825_h(i1, palms);
            if (l < 60 || random.nextBoolean()) {
               this.coralGen.func_76484_a(world, random, i1, l, palms);
            }
         }
      }

      if (k >= 64000) {
         float chance = 0.0F;
         if (k >= 130000) {
            chance = 1.0F;
         } else {
            chance = (float)(k - '切') / 66000.0F;
         }

         if (random.nextFloat() < chance && random.nextInt(6) == 0) {
            palms = 1 + random.nextInt(2);
            if (random.nextInt(3) == 0) {
               ++palms;
            }

            for(l = 0; l < palms; ++l) {
               int i1 = i + random.nextInt(16) + 8;
               int k1 = k + random.nextInt(16) + 8;
               int j1 = world.func_72825_h(i1, k1) - 1;
               if (world.func_147439_a(i1, j1, k1).func_149721_r() && LOTRWorldGenStructureBase2.isSurfaceStatic(world, i1, j1, k1)) {
                  Block prevBlock = world.func_147439_a(i1, j1, k1);
                  int prevMeta = world.func_72805_g(i1, j1, k1);
                  world.func_147465_d(i1, j1, k1, Blocks.field_150346_d, 0, 2);
                  WorldGenerator palmGen = LOTRTreeType.PALM.create(false, random);
                  if (!palmGen.func_76484_a(world, random, i1, j1 + 1, k1)) {
                     world.func_147465_d(i1, j1, k1, prevBlock, prevMeta, 2);
                  }
               }
            }
         }
      }

   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public static boolean isFrozen(int i, int k) {
      if (k > -30000) {
         return false;
      } else {
         int l = -60000 - k;
         l *= -1;
         if (l < 1) {
            return true;
         } else {
            iceRand.setSeed((long)i * 341873128712L + (long)k * 132897987541L);
            l -= Math.abs(-30000) / 2;
            if (l < 0) {
               l *= -1;
               l = (int)Math.sqrt((double)l);
               if (l < 2) {
                  l = 2;
               }

               return iceRand.nextInt(l) != 0;
            } else {
               l = (int)Math.sqrt((double)l);
               if (l < 2) {
                  l = 2;
               }

               return iceRand.nextInt(l) == 0;
            }
         }
      }
   }
}
