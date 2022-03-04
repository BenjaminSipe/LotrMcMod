package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradBushland extends LOTRBiomeGenFarHarad {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenFarHaradBushland(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
      this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
      this.decorator.treesPerChunk = 0;
      this.decorator.setTreeCluster(3, 3);
      this.decorator.logsPerChunk = 1;
      this.decorator.grassPerChunk = 16;
      this.decorator.doubleGrassPerChunk = 10;
      this.decorator.cornPerChunk = 4;
      this.decorator.addTree(LOTRTreeType.BIRCH, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 25);
      this.biomeColors.setGrass(13414999);
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FAR_HARAD.getSubregion("bushland");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      int termites;
      int l;
      int i1;
      int k1;
      if (random.nextInt(32) == 0) {
         termites = 1 + random.nextInt(4);

         for(l = 0; l < termites; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

      if (random.nextInt(16) == 0) {
         termites = 1 + random.nextInt(4);

         for(l = 0; l < termites; ++l) {
            i1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            int j1 = world.func_72976_f(i1, k1);
            (new LOTRWorldGenBoulder(LOTRMod.termiteMound, 0, 1, 4)).func_76484_a(world, random, i1, j1, k1);

            for(int x = 0; x < 5; ++x) {
               int i2 = i1 - random.nextInt(5) + random.nextInt(5);
               int k2 = k1 - random.nextInt(5) + random.nextInt(5);
               int j2 = world.func_72976_f(i2, k2);
               Block surfaceBlock = world.func_147439_a(i2, j2 - 1, k1);
               if (surfaceBlock.func_149662_c() && (surfaceBlock == LOTRMod.termiteMound || LOTRWorldGenStructureBase2.isSurfaceStatic(world, i2, j2 - 1, k1))) {
                  int j3 = j2 + random.nextInt(4);

                  for(int j4 = j2; j4 <= j3; ++j4) {
                     world.func_147449_b(i2, j4, k2, LOTRMod.termiteMound);
                     world.func_147439_a(i2, j4 - 1, k2).onPlantGrow(world, i2, j4 - 1, k2, i2, j4 - 1, k2);
                  }
               }
            }
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.05F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.75F;
   }
}
