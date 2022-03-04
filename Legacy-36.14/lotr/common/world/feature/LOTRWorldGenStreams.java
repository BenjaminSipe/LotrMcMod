package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenStreams extends WorldGenerator {
   private Block liquidBlock;

   public LOTRWorldGenStreams(Block block) {
      this.liquidBlock = block;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (!this.isRock(world, i, j + 1, k)) {
         return false;
      } else if (!this.isRock(world, i, j - 1, k)) {
         return false;
      } else if (!world.func_147437_c(i, j, k) && !this.isRock(world, i, j, k)) {
         return false;
      } else {
         int sides = 0;
         if (this.isRock(world, i - 1, j, k)) {
            ++sides;
         }

         if (this.isRock(world, i + 1, j, k)) {
            ++sides;
         }

         if (this.isRock(world, i, j, k - 1)) {
            ++sides;
         }

         if (this.isRock(world, i, j, k + 1)) {
            ++sides;
         }

         int openAir = 0;
         if (world.func_147437_c(i - 1, j, k)) {
            ++openAir;
         }

         if (world.func_147437_c(i + 1, j, k)) {
            ++openAir;
         }

         if (world.func_147437_c(i, j, k - 1)) {
            ++openAir;
         }

         if (world.func_147437_c(i, j, k + 1)) {
            ++openAir;
         }

         if (sides == 3 && openAir == 1) {
            world.func_147465_d(i, j, k, this.liquidBlock, 0, 2);
            world.field_72999_e = true;
            this.liquidBlock.func_149674_a(world, i, j, k, random);
            world.field_72999_e = false;
         }

         return true;
      }
   }

   private boolean isRock(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      return block == Blocks.field_150348_b || block == Blocks.field_150322_A || block == LOTRMod.rock;
   }
}
