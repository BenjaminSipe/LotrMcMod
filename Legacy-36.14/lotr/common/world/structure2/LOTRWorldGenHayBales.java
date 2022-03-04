package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenHayBales extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenHayBales(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int width = 2 + random.nextInt(3);
      int size = 4 + width * (1 + random.nextInt(2));

      for(int l = 0; l < size; ++l) {
         int r = MathHelper.func_76136_a(random, 0, width * width);
         int dist = (int)Math.round(Math.sqrt((double)r));
         float angle = 6.2831855F * random.nextFloat();
         int i1 = Math.round(MathHelper.func_76134_b(angle) * (float)dist);
         int k1 = Math.round(MathHelper.func_76126_a(angle) * (float)dist);

         for(int j1 = 12; j1 >= -12; --j1) {
            if (this.isSurface(world, i1, j1 - 1, k1) || this.getBlock(world, i1, j1 - 1, k1) == Blocks.field_150407_cf) {
               Block block = this.getBlock(world, i1, j1, k1);
               if (this.isAir(world, i1, j1, k1) || this.isReplaceable(world, i1, j1, k1) || block.func_149688_o() == Material.field_151585_k) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150407_cf, 0);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
                  break;
               }
            }
         }
      }

      return true;
   }
}
