package lotr.common.block.trees;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

public abstract class CrossBigTree extends Tree {
   protected abstract ConfiguredFeature getCrossTreeFeature(Random var1);

   public boolean func_230339_a_(ServerWorld world, ChunkGenerator chunkGen, BlockPos pos, BlockState sapling, Random rand) {
      for(int x = -2; x <= 2; ++x) {
         for(int z = -2; z <= 2; ++z) {
            if ((x == 0 || z == 0) && canCrossTreeSpawnAt(sapling, world, pos.func_177982_a(x, 0, z))) {
               return this.growCrossTree(world, chunkGen, pos, sapling, rand, x, z);
            }
         }
      }

      return super.func_230339_a_(world, chunkGen, pos, sapling, rand);
   }

   private boolean growCrossTree(ServerWorld world, ChunkGenerator chunkGen, BlockPos pos, BlockState sapling, Random rand, int xOffset, int zOffset) {
      ConfiguredFeature crossTree = this.getCrossTreeFeature(rand);
      if (crossTree == null) {
         return false;
      } else {
         BlockState air = Blocks.field_150350_a.func_176223_P();
         world.func_180501_a(pos.func_177982_a(xOffset, 0, zOffset), air, 4);
         world.func_180501_a(pos.func_177982_a(xOffset - 1, 0, zOffset), air, 4);
         world.func_180501_a(pos.func_177982_a(xOffset + 1, 0, zOffset), air, 4);
         world.func_180501_a(pos.func_177982_a(xOffset, 0, zOffset - 1), air, 4);
         world.func_180501_a(pos.func_177982_a(xOffset, 0, zOffset + 1), air, 4);
         if (crossTree.func_242765_a(world, chunkGen, rand, pos.func_177982_a(xOffset, 0, zOffset))) {
            return true;
         } else {
            world.func_180501_a(pos.func_177982_a(xOffset, 0, zOffset), sapling, 4);
            world.func_180501_a(pos.func_177982_a(xOffset - 1, 0, zOffset), sapling, 4);
            world.func_180501_a(pos.func_177982_a(xOffset + 1, 0, zOffset), sapling, 4);
            world.func_180501_a(pos.func_177982_a(xOffset, 0, zOffset - 1), sapling, 4);
            world.func_180501_a(pos.func_177982_a(xOffset, 0, zOffset + 1), sapling, 4);
            return false;
         }
      }
   }

   public static boolean canCrossTreeSpawnAt(BlockState sapling, IBlockReader world, BlockPos pos) {
      Block block = sapling.func_177230_c();

      for(int x = -1; x <= 1; ++x) {
         for(int z = -1; z <= 1; ++z) {
            if ((x == 0 || z == 0) && block != world.func_180495_p(pos.func_177982_a(x, 0, z)).func_177230_c()) {
               return false;
            }
         }
      }

      return true;
   }
}
