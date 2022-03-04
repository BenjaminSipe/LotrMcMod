package lotr.common.world.biome.variant;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoads;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeVariantOrchard extends LOTRBiomeVariant {
   public LOTRBiomeVariantOrchard(int i, String s) {
      super(i, s, LOTRBiomeVariant.VariantScale.SMALL);
      this.setTemperatureRainfall(0.1F, 0.2F);
      this.setHeight(0.0F, 0.4F);
      this.setTrees(0.0F);
      this.setGrass(0.5F);
      this.disableStructuresVillages();
   }

   public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, LOTRBiome biome) {
      int chunkX = i & 15;
      int chunkZ = k & 15;
      int xzIndex = chunkX * 16 + chunkZ;
      int ySize = blocks.length / 256;
      if (!LOTRRoads.isRoadAt(i, k)) {
         for(int j = 128; j >= 0; --j) {
            int index = xzIndex * ySize + j;
            Block above = blocks[index + 1];
            Block block = blocks[index];
            if (block.func_149662_c() && above.func_149688_o() == Material.field_151579_a) {
               int i1 = IntMath.mod(i, 32);
               int k1 = IntMath.mod(k, 16);
               if (i1 != 6 && i1 != 7 && i1 != 8 && k1 == 0) {
                  blocks[index + 1] = Blocks.field_150422_aJ;
                  meta[index + 1] = 0;
               }
               break;
            }
         }
      }

   }

   public void decorateVariant(World world, Random random, int i, int k, LOTRBiome biome) {
      int[] var6 = new int[]{i + 3, i + 11};
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         int i1 = var6[var8];
         int k1 = k + 8;
         int j1 = world.func_72976_f(i1, k1);
         WorldGenerator treeGen = this.getRandomTree(random).create(false, random);
         treeGen.func_76484_a(world, random, i1, j1, k1);
      }

   }
}
