package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import lotr.common.block.DripstoneBlock;
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

public class DripstoneFeature extends Feature {
   public DripstoneFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, DripstoneFeatureConfig config) {
      boolean placedAny = false;
      Mutable movingPos = new Mutable();

      for(int l = 0; l < config.tries; ++l) {
         int x = pos.func_177958_n() - rand.nextInt(config.xspread) + rand.nextInt(config.xspread);
         int y = pos.func_177956_o() - rand.nextInt(config.yspread) + rand.nextInt(config.yspread);
         int z = pos.func_177952_p() - rand.nextInt(config.zspread) + rand.nextInt(config.zspread);
         movingPos.func_181079_c(x, y, z);
         Block above = world.func_180495_p(movingPos.func_177984_a()).func_177230_c();
         Block below = world.func_180495_p(movingPos.func_177977_b()).func_177230_c();
         boolean waterlogged = world.func_204610_c(movingPos).func_206886_c() == Fluids.field_204546_a;
         boolean isForcedBlock = config.hasForcedDripstoneState();
         boolean canPlaceStalac = isForcedBlock ? ((BlockState)config.forcedBlockState.func_206870_a(DripstoneBlock.DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE)).func_196955_c(world, movingPos) : DripstoneBlock.BLOCK_TO_DRIPSTONE.containsKey(above);
         boolean canPlaceStalag = isForcedBlock ? ((BlockState)config.forcedBlockState.func_206870_a(DripstoneBlock.DRIPSTONE_TYPE, DripstoneBlock.Type.STALAGMITE)).func_196955_c(world, movingPos) : DripstoneBlock.BLOCK_TO_DRIPSTONE.containsKey(below);
         boolean placed = false;
         BlockState stalag;
         BlockState placeState;
         BlockState placeState;
         BlockState placeStateUp;
         boolean waterloggedUp;
         if (canPlaceStalac) {
            stalag = isForcedBlock ? config.forcedBlockState : ((DripstoneBlock)DripstoneBlock.BLOCK_TO_DRIPSTONE.get(above)).func_176223_P();
            if (this.canDripstoneReplace(stalag, world, movingPos)) {
               if (rand.nextFloat() < config.doubleChance && this.canDripstoneReplace(stalag, world, movingPos.func_177977_b())) {
                  waterloggedUp = world.func_204610_c(movingPos.func_177977_b()).func_206886_c() == Fluids.field_204546_a;
                  placeState = this.waterlogIfApplicable((BlockState)stalag.func_206870_a(DripstoneBlock.DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE_DOUBLE_BASE), waterlogged);
                  placeStateUp = this.waterlogIfApplicable((BlockState)stalag.func_206870_a(DripstoneBlock.DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE_DOUBLE_POINT), waterloggedUp);
                  world.func_180501_a(movingPos, placeState, 2);
                  world.func_180501_a(movingPos.func_177977_b(), placeStateUp, 2);
                  placed = true;
               } else {
                  placeState = this.waterlogIfApplicable((BlockState)stalag.func_206870_a(DripstoneBlock.DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE), waterlogged);
                  world.func_180501_a(movingPos, placeState, 2);
                  placed = true;
               }
            }
         }

         if (!placed && canPlaceStalag) {
            stalag = isForcedBlock ? config.forcedBlockState : ((DripstoneBlock)DripstoneBlock.BLOCK_TO_DRIPSTONE.get(below)).func_176223_P();
            if (this.canDripstoneReplace(stalag, world, movingPos)) {
               if (rand.nextFloat() < config.doubleChance && this.canDripstoneReplace(stalag, world, movingPos.func_177984_a())) {
                  waterloggedUp = world.func_204610_c(movingPos.func_177984_a()).func_206886_c() == Fluids.field_204546_a;
                  placeState = this.waterlogIfApplicable((BlockState)stalag.func_206870_a(DripstoneBlock.DRIPSTONE_TYPE, DripstoneBlock.Type.STALAGMITE_DOUBLE_BASE), waterlogged);
                  placeStateUp = this.waterlogIfApplicable((BlockState)stalag.func_206870_a(DripstoneBlock.DRIPSTONE_TYPE, DripstoneBlock.Type.STALAGMITE_DOUBLE_POINT), waterloggedUp);
                  world.func_180501_a(movingPos, placeState, 2);
                  world.func_180501_a(movingPos.func_177984_a(), placeStateUp, 2);
                  placed = true;
               } else {
                  placeState = this.waterlogIfApplicable((BlockState)stalag.func_206870_a(DripstoneBlock.DRIPSTONE_TYPE, DripstoneBlock.Type.STALAGMITE), waterlogged);
                  world.func_180501_a(movingPos, placeState, 2);
                  placed = true;
               }
            }
         }

         placedAny |= placed;
      }

      return placedAny;
   }

   private boolean canDripstoneReplace(BlockState state, IWorld world, BlockPos pos) {
      if (world.func_175623_d(pos)) {
         return true;
      } else if (world.func_180495_p(pos).func_177230_c() != Blocks.field_150355_j) {
         return false;
      } else {
         Block block = state.func_177230_c();
         return block instanceof DripstoneBlock && ((DripstoneBlock)block).isWaterloggable;
      }
   }

   private BlockState waterlogIfApplicable(BlockState state, boolean waterlogged) {
      Block block = state.func_177230_c();
      if (block instanceof DripstoneBlock && ((DripstoneBlock)block).isWaterloggable) {
         state = (BlockState)state.func_206870_a(DripstoneBlock.WATERLOGGED, waterlogged);
      }

      return state;
   }
}
