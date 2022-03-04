package lotr.common.world.biome;

import java.util.Random;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenStreams;
import lotr.common.world.feature.LOTRWorldGenVolcanoCrater;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradJungleMountains extends LOTRBiomeGenFarHaradJungle {
   public LOTRBiomeGenFarHaradJungleMountains(int i, boolean major) {
      super(i, major);
      this.obsidianGravelRarity = 5;
      this.npcSpawnList.clear();
      this.clearBiomeVariants();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.biomeGemFactor = 1.0F;
      this.decorator.treesPerChunk = 0;
      this.biomeColors.setSky(10659994);
      this.biomeColors.setFog(9805451);
      this.invasionSpawns.clearInvasions();
   }

   public boolean hasJungleLakes() {
      return false;
   }

   public boolean getEnableRiver() {
      return false;
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
      int stoneHeight = 120 - rockDepth;
      boolean generateMud = false;
      int muds = 0;
      double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.09D, (double)k * 0.09D);
      double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      if (d1 + d2 > 0.4D) {
         generateMud = true;
      }

      for(int j = ySize - 1; j >= stoneHeight; --j) {
         int index = xzIndex * ySize + j;
         Block block = blocks[index];
         if ((block == this.field_76752_A || block == this.field_76753_B) && (!generateMud || muds >= 4 + random.nextInt(2))) {
            blocks[index] = Blocks.field_150348_b;
            meta[index] = 0;
         }
      }

   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int l;
      int l;
      int i1;
      int j1;
      int k1;
      for(l = 0; l < 3; ++l) {
         l = 1 + random.nextInt(3);

         for(i1 = 0; i1 < l; ++i1) {
            j1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            int j1 = world.func_72976_f(j1, k1);
            if (j1 > 110) {
               (new LOTRWorldGenVolcanoCrater()).func_76484_a(world, random, j1, j1, k1);
            }
         }
      }

      for(l = 0; l < 12; ++l) {
         l = i + random.nextInt(16) + 8;
         i1 = k + random.nextInt(16) + 8;
         j1 = world.func_72976_f(l, i1);
         if (j1 < 120 || random.nextInt(20) == 0) {
            this.decorator.genTree(world, random, l, j1, i1);
         }
      }

      WorldGenerator lavaGen = new LOTRWorldGenStreams(Blocks.field_150356_k);

      for(l = 0; l < 5; ++l) {
         i1 = i + random.nextInt(16) + 8;
         j1 = 140 + random.nextInt(50);
         k1 = k + random.nextInt(16) + 8;
         lavaGen.func_76484_a(world, random, i1, j1, k1);
      }

   }
}
