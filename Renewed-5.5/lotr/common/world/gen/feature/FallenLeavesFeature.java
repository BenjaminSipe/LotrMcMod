package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import lotr.common.block.FallenLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FallenLeavesFeature extends Feature {
   public FallenLeavesFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      BlockState fallenLeavesState = null;
      int searchTries = 40;
      int searchXRange = 6;
      int searchYRangeUp = 12;
      int searchZRange = 6;
      Mutable movingPos = new Mutable();

      for(int i = 0; i < searchTries; ++i) {
         int x = MathHelper.func_76136_a(rand, -searchXRange, searchXRange);
         int y = rand.nextInt(searchYRangeUp + 1);
         int z = MathHelper.func_76136_a(rand, -searchZRange, searchZRange);
         movingPos.func_189533_g(pos).func_196234_d(x, y, z);
         BlockState state = world.func_180495_p(movingPos);
         Block fallenLeavesBlock = FallenLeavesBlock.getFallenLeavesFor(state.func_177230_c());
         if (fallenLeavesBlock != null) {
            fallenLeavesState = fallenLeavesBlock.func_176223_P();
            break;
         }
      }

      if (fallenLeavesState == null) {
         return false;
      } else {
         int placeTries = 64;
         int placeXRange = 5;
         int placeYRange = 3;
         int placeZRange = 5;

         for(int i = 0; i < placeTries; ++i) {
            int x = MathHelper.func_76136_a(rand, -placeXRange, placeXRange);
            int y = MathHelper.func_76136_a(rand, -placeYRange, placeYRange);
            int z = MathHelper.func_76136_a(rand, -placeZRange, placeZRange);
            movingPos.func_189533_g(pos).func_196234_d(x, y, z);
            if (fallenLeavesState.func_196955_c(world, movingPos) && this.canFallenLeavesReplaceBlockAt(world, movingPos)) {
               world.func_180501_a(movingPos, fallenLeavesState, 2);
               this.checkForFloatingTopHalfBlocksAbove(world, movingPos);
            }
         }

         return true;
      }
   }

   private boolean canFallenLeavesReplaceBlockAt(ISeedReader world, Mutable movingPos) {
      BlockState currentState = world.func_180495_p(movingPos);
      return currentState.func_185904_a().func_76222_j() && currentState.func_204520_s().func_206888_e();
   }

   private void checkForFloatingTopHalfBlocksAbove(ISeedReader world, Mutable movingPos) {
      BlockPos abovePos = movingPos.func_177984_a();
      BlockState aboveState = world.func_180495_p(abovePos);
      if (!aboveState.func_196955_c(world, abovePos)) {
         world.func_180501_a(abovePos, Blocks.field_150350_a.func_176223_P(), 3);
      }

   }
}
