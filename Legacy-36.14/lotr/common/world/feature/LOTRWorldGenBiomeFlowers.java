package lotr.common.world.feature;

import java.util.Random;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.FlowerEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenBiomeFlowers extends WorldGenerator {
   private Block specifiedBlock;
   private int specifiedMeta;

   public LOTRWorldGenBiomeFlowers() {
      this((Block)null, -1);
   }

   public LOTRWorldGenBiomeFlowers(Block block, int meta) {
      this.specifiedBlock = block;
      this.specifiedMeta = meta;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      Block block;
      int meta;
      if (this.specifiedBlock != null) {
         block = this.specifiedBlock;
         meta = this.specifiedMeta;
      } else {
         FlowerEntry flower = ((LOTRBiome)world.func_72807_a(i, k)).getRandomFlower(world, random, i, j, k);
         block = flower.block;
         meta = flower.metadata;
      }

      for(int l = 0; l < 64; ++l) {
         int i1 = i + random.nextInt(8) - random.nextInt(8);
         int j1 = j + random.nextInt(4) - random.nextInt(4);
         int k1 = k + random.nextInt(8) - random.nextInt(8);
         if (world.func_147437_c(i1, j1, k1) && (!world.field_73011_w.field_76576_e || j1 < 255) && block.func_149718_j(world, i1, j1, k1)) {
            world.func_147465_d(i1, j1, k1, block, meta, 2);
         }
      }

      return true;
   }
}
