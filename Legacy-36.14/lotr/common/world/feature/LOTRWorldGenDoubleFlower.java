package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenDoubleFlower extends WorldGenerator {
   private int flowerType;

   public void setFlowerType(int i) {
      this.flowerType = i;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      boolean flag = false;

      for(int l = 0; l < 64; ++l) {
         int i1 = i + random.nextInt(8) - random.nextInt(8);
         int j1 = j + random.nextInt(4) - random.nextInt(4);
         int k1 = k + random.nextInt(8) - random.nextInt(8);
         if (world.func_147437_c(i1, j1, k1) && (!world.field_73011_w.field_76576_e || j1 < 254) && LOTRMod.doubleFlower.func_149742_c(world, i1, j1, k1)) {
            ((BlockDoublePlant)LOTRMod.doubleFlower).func_149889_c(world, i1, j1, k1, this.flowerType, 2);
            flag = true;
         }
      }

      return flag;
   }
}
