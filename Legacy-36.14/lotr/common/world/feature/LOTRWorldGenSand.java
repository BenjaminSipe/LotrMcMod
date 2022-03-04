package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenSand extends WorldGenerator {
   private Block sandBlock;
   private int radius;
   private int heightRadius;

   public LOTRWorldGenSand(Block b, int r, int hr) {
      this.sandBlock = b;
      this.radius = r;
      this.heightRadius = hr;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      boolean mid = false;
      boolean adj = false;
      int waterCheck = 1;

      int r;
      int hr;
      int i1;
      for(int i1 = -waterCheck; i1 <= waterCheck; ++i1) {
         for(r = -waterCheck; r <= waterCheck; ++r) {
            hr = i + i1;
            i1 = k + r;
            if (world.func_147439_a(hr, j, i1).func_149688_o() == Material.field_151586_h) {
               if (i1 == 0 && r == 0) {
                  mid = true;
               } else {
                  adj = true;
               }
            }
         }
      }

      if (mid && adj) {
         BiomeGenBase biome = world.func_72807_a(i, k);
         r = random.nextInt(this.radius - 2) + 2;
         hr = this.heightRadius;

         for(i1 = i - r; i1 <= i + r; ++i1) {
            for(int k1 = k - r; k1 <= k + r; ++k1) {
               int i2 = i1 - i;
               int k2 = k1 - k;
               if (i2 * i2 + k2 * k2 <= r * r) {
                  for(int j1 = j - hr; j1 <= j + hr; ++j1) {
                     Block block = world.func_147439_a(i1, j1, k1);
                     if ((block == biome.field_76752_A || block == biome.field_76753_B) && random.nextInt(3) != 0) {
                        world.func_147465_d(i1, j1, k1, this.sandBlock, 0, 2);
                     }
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
