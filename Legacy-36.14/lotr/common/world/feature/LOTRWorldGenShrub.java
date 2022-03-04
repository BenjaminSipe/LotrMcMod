package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenShrub extends WorldGenTrees {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenShrub(Block w1, int w2, Block l1, int l2) {
      super(false);
      this.woodBlock = w1;
      this.woodMeta = w2;
      this.leafBlock = l1;
      this.leafMeta = l2;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      do {
         Block block = world.func_147439_a(i, j, k);
         if (!block.isLeaves(world, i, j, k) && !block.isAir(world, i, j, k)) {
            break;
         }

         --j;
      } while(j > 0);

      Block below = world.func_147439_a(i, j, k);
      if (below.canSustainPlant(world, i, j, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
         ++j;
         this.func_150516_a(world, i, j, k, this.woodBlock, this.woodMeta);

         for(int j1 = j; j1 <= j + 2; ++j1) {
            int j2 = j1 - j;
            int range = 2 - j2;

            for(int i1 = i - range; i1 <= i + range; ++i1) {
               for(int k1 = k - range; k1 <= k + range; ++k1) {
                  int i2 = i1 - i;
                  int k2 = k1 - k;
                  if ((Math.abs(i2) != range || Math.abs(k2) != range || random.nextInt(2) != 0) && world.func_147439_a(i1, j1, k1).canBeReplacedByLeaves(world, i1, j1, k1)) {
                     this.func_150516_a(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                  }
               }
            }
         }
      }

      return true;
   }
}
