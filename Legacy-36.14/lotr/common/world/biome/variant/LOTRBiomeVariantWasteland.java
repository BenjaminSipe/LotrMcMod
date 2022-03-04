package lotr.common.world.biome.variant;

import java.util.Random;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRBiomeVariantWasteland extends LOTRBiomeVariant {
   private Block stoneBlock;

   public LOTRBiomeVariantWasteland(int i, String s, Block block) {
      super(i, s, LOTRBiomeVariant.VariantScale.LARGE);
      this.setTemperatureRainfall(0.0F, -0.3F);
      this.setTrees(0.1F);
      this.setGrass(0.3F);
      this.setFlowers(0.3F);
      this.stoneBlock = block;
      this.disableVillages();
   }

   public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, LOTRBiome biome) {
      int chunkX = i & 15;
      int chunkZ = k & 15;
      int xzIndex = chunkX * 16 + chunkZ;
      int ySize = blocks.length / 256;
      double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a((double)i * 0.04D, (double)k * 0.04D);
      double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      d2 *= 0.3D;
      double d3 = podzolNoise.func_151601_a((double)i * 0.04D, (double)k * 0.04D);
      double d4 = podzolNoise.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      d4 *= 0.3D;
      int index;
      if (d3 + d4 > 0.5D) {
         index = xzIndex * ySize + height;
         blocks[index] = Blocks.field_150346_d;
         meta[index] = 1;
      } else if (d1 + d2 > -0.3D) {
         index = xzIndex * ySize + height;
         if (random.nextInt(5) == 0) {
            blocks[index] = Blocks.field_150351_n;
            meta[index] = 0;
         } else {
            blocks[index] = this.stoneBlock;
            meta[index] = 0;
         }

         if (random.nextInt(50) == 0) {
            if (random.nextInt(3) == 0) {
               blocks[index + 1] = this.stoneBlock;
               meta[index + 1] = 0;
            } else {
               blocks[index + 1] = Blocks.field_150351_n;
               meta[index + 1] = 0;
            }
         }
      }

   }
}
