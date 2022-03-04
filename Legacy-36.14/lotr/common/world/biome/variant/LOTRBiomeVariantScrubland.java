package lotr.common.world.biome.variant;

import java.util.Random;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRBiomeVariantScrubland extends LOTRBiomeVariant {
   private Block stoneBlock;

   public LOTRBiomeVariantScrubland(int i, String s, Block block) {
      super(i, s, LOTRBiomeVariant.VariantScale.LARGE);
      this.setTemperatureRainfall(0.0F, -0.2F);
      this.setTrees(0.8F);
      this.setGrass(0.5F);
      this.setFlowers(0.5F);
      this.addTreeTypes(0.6F, new Object[]{LOTRTreeType.OAK_SHRUB, 100});
      this.stoneBlock = block;
      this.disableVillages();
   }

   public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, LOTRBiome biome) {
      int chunkX = i & 15;
      int chunkZ = k & 15;
      int xzIndex = chunkX * 16 + chunkZ;
      int ySize = blocks.length / 256;
      double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a((double)i * 0.005D, (double)k * 0.005D);
      double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d3 = LOTRBiome.biomeTerrainNoise.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
      int index;
      if (d1 + d2 + d3 > 0.6D) {
         index = xzIndex * ySize + height;
         if (d1 + d2 > 0.7D && random.nextInt(3) != 0) {
            blocks[index] = Blocks.field_150354_m;
            meta[index] = 0;
         } else if (random.nextInt(5) == 0) {
            blocks[index] = Blocks.field_150351_n;
            meta[index] = 0;
         } else {
            blocks[index] = this.stoneBlock;
            meta[index] = 0;
         }

         if (random.nextInt(30) == 0) {
            if (random.nextInt(3) == 0) {
               blocks[index + 1] = this.stoneBlock;
               meta[index + 1] = 0;
            } else {
               blocks[index + 1] = Blocks.field_150351_n;
               meta[index + 1] = 0;
            }
         }
      }

      d1 = LOTRBiome.biomeTerrainNoise.func_151601_a((double)i * 0.008D, (double)k * 0.008D);
      d2 = LOTRBiome.biomeTerrainNoise.func_151601_a((double)i * 0.05D, (double)k * 0.05D);
      d3 = LOTRBiome.biomeTerrainNoise.func_151601_a((double)i * 0.6D, (double)k * 0.6D);
      if (d1 + d2 + d3 > 0.8D && random.nextInt(3) == 0) {
         index = xzIndex * ySize + height;
         Block above = blocks[index + 1];
         Block block = blocks[index];
         if (block.func_149662_c() && above.func_149688_o() == Material.field_151579_a) {
            blocks[index + 1] = Blocks.field_150362_t;
            meta[index + 1] = 4;
            if (random.nextInt(5) == 0) {
               blocks[index + 2] = Blocks.field_150362_t;
               meta[index + 2] = 4;
            }
         }
      }

      if (random.nextInt(3000) == 0) {
         index = xzIndex * ySize + height;
         blocks[index] = biome.field_76753_B;
         meta[index] = (byte)biome.fillerBlockMeta;
         int logHeight = 1 + random.nextInt(4);

         for(int j1 = 1; j1 <= logHeight; ++j1) {
            blocks[index + j1] = Blocks.field_150364_r;
            meta[index + j1] = 0;
         }
      }

   }
}
