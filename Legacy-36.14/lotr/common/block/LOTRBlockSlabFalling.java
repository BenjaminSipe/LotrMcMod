package lotr.common.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

public abstract class LOTRBlockSlabFalling extends LOTRBlockSlabBase {
   public LOTRBlockSlabFalling(boolean flag, Material material, int n) {
      super(flag, material, n);
   }

   public void func_149726_b(World world, int i, int j, int k) {
      world.func_147464_a(i, j, k, this, this.func_149738_a(world));
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      world.func_147464_a(i, j, k, this, this.func_149738_a(world));
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (!world.field_72995_K) {
         this.tryBlockFall(world, i, j, k);
      }

   }

   private void tryBlockFall(World world, int i, int j, int k) {
      if (BlockFalling.func_149831_e(world, i, j - 1, k) && j >= 0) {
         int range = 32;
         if (!BlockFalling.field_149832_M && world.func_72904_c(i - range, j - range, k - range, i + range, j + range, k + range)) {
            if (!world.field_72995_K) {
               EntityFallingBlock fallingBlock = new EntityFallingBlock(world, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), this, world.func_72805_g(i, j, k) & 7);
               world.func_72838_d(fallingBlock);
            }
         } else {
            world.func_147468_f(i, j, k);

            while(BlockFalling.func_149831_e(world, i, j - 1, k) && j > 0) {
               --j;
            }

            if (j > 0) {
               world.func_147449_b(i, j, k, this);
            }
         }
      }

   }

   public int func_149738_a(World world) {
      return 2;
   }
}
