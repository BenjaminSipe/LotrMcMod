package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import lotr.common.LOTRLog;
import lotr.common.block.ReedsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class ReedsFeature extends Feature {
   public ReedsFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, ReedsFeatureConfig config) {
      Mutable movingPos = new Mutable();

      for(int l = 0; l < config.tries; ++l) {
         int x = pos.func_177958_n() - rand.nextInt(config.xspread) + rand.nextInt(config.xspread);
         int y = pos.func_177956_o() - rand.nextInt(config.yspread) + rand.nextInt(config.yspread);
         int z = pos.func_177952_p() - rand.nextInt(config.zspread) + rand.nextInt(config.zspread);
         movingPos.func_181079_c(x, y, z);
         Block reedBlock = config.blockProvider.func_225574_a_(rand, movingPos).func_177230_c();
         if (!(reedBlock instanceof ReedsBlock)) {
            LOTRLog.warn("Attempted to generate non-reed block in a reeds feature (block: %s, position: [%d %d %d])", reedBlock.getRegistryName(), x, y, z);
         } else {
            BlockState baseState = reedBlock.func_176223_P();
            baseState = (BlockState)baseState.func_206870_a(ReedsBlock.WATERLOGGED, this.isWaterlogged(world, movingPos));
            if (this.canReedsReplaceAt(world, movingPos) && baseState.func_196955_c(world, movingPos)) {
               boolean threeHigh = rand.nextFloat() < config.fullyGrownChance;
               boolean placedThreeHigh = false;
               BlockPos abovePos;
               if (threeHigh) {
                  abovePos = movingPos.func_177984_a();
                  BlockPos twoAbovePos = abovePos.func_177984_a();
                  if ((world.func_175623_d(abovePos) || world.func_204610_c(abovePos).func_206886_c() == Fluids.field_204546_a) && world.func_175623_d(twoAbovePos)) {
                     this.placeAppropriateReedState(world, movingPos, baseState, ReedsBlock.Type.THREE_BOTTOM);
                     this.placeAppropriateReedState(world, abovePos, baseState, ReedsBlock.Type.THREE_MIDDLE);
                     this.placeAppropriateReedState(world, twoAbovePos, baseState, ReedsBlock.Type.THREE_TOP);
                     placedThreeHigh = true;
                  }
               }

               if (!placedThreeHigh) {
                  abovePos = movingPos.func_177984_a();
                  if (world.func_175623_d(abovePos) || world.func_204610_c(abovePos).func_206886_c() == Fluids.field_204546_a) {
                     this.placeAppropriateReedState(world, movingPos, baseState, ReedsBlock.Type.TWO_BOTTOM);
                     this.placeAppropriateReedState(world, abovePos, baseState, ReedsBlock.Type.TWO_TOP);
                  }
               }
            }
         }
      }

      return true;
   }

   private boolean canReedsReplaceAt(IWorld world, BlockPos pos) {
      BlockState existingState = world.func_180495_p(pos);
      return existingState.isAir(world, pos) || existingState.func_185904_a().func_76222_j() || existingState.func_177230_c() == Blocks.field_150355_j;
   }

   private void placeAppropriateReedState(IWorld world, BlockPos pos, BlockState baseState, ReedsBlock.Type type) {
      world.func_180501_a(pos, (BlockState)((BlockState)baseState.func_206870_a(ReedsBlock.REEDS_TYPE, type)).func_206870_a(ReedsBlock.WATERLOGGED, this.isWaterlogged(world, pos)), 2);
   }

   private boolean isWaterlogged(IWorld world, BlockPos pos) {
      return world.func_204610_c(pos).func_206886_c() == Fluids.field_204546_a;
   }
}
