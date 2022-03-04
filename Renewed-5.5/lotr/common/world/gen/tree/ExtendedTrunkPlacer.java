package lotr.common.world.gen.tree;

import com.mojang.datafixers.Products.P6;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import lotr.common.world.gen.feature.LOTRFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.AbstractBlock.AbstractBlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;

public abstract class ExtendedTrunkPlacer extends AbstractTrunkPlacer {
   protected final Optional woodProvider;
   protected final Optional strippedLogProvider;
   protected final Optional branchProvider;

   protected static P6 baseCodecWithWood(Instance instance) {
      return func_236915_a_(instance).and(instance.group(BlockStateProvider.field_236796_a_.optionalFieldOf("wood_provider").forGetter((trunk) -> {
         return trunk.woodProvider;
      }), BlockStateProvider.field_236796_a_.optionalFieldOf("stripped_log_provider").forGetter((trunk) -> {
         return trunk.strippedLogProvider;
      }), BlockStateProvider.field_236796_a_.optionalFieldOf("branch_provider").forGetter((trunk) -> {
         return trunk.branchProvider;
      })));
   }

   protected ExtendedTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, Optional woodProvider, Optional strippedLogProvider, Optional branchProvider) {
      super(baseHeight, heightRandA, heightRandB);
      this.woodProvider = woodProvider;
      this.strippedLogProvider = strippedLogProvider;
      this.branchProvider = branchProvider;
   }

   protected boolean placeLogWithAxis(IWorldGenerationReader world, Random rand, BlockPos pos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config, Axis axis) {
      if (TreeFeature.func_236404_a_(world, pos)) {
         BlockState logState = config.field_227368_m_.func_225574_a_(rand, pos);
         if (logState.func_235901_b_(RotatedPillarBlock.field_176298_M)) {
            logState = (BlockState)logState.func_206870_a(RotatedPillarBlock.field_176298_M, axis);
         }

         func_236913_a_(world, pos, logState, bb);
         trunk.add(pos.func_185334_h());
         return true;
      } else {
         return false;
      }
   }

   protected boolean placeWood(IWorldGenerationReader world, Random rand, BlockPos pos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config, Axis axis) {
      if (TreeFeature.func_236404_a_(world, pos)) {
         BlockState woodState = ((BlockStateProvider)this.woodProvider.orElseThrow(() -> {
            return new IllegalStateException("Wood blockstate provider is not set!");
         })).func_225574_a_(rand, pos);
         if (woodState.func_235901_b_(RotatedPillarBlock.field_176298_M)) {
            woodState = (BlockState)woodState.func_206870_a(RotatedPillarBlock.field_176298_M, axis);
         }

         func_236913_a_(world, pos, woodState, bb);
         trunk.add(pos.func_185334_h());
         return true;
      } else {
         return false;
      }
   }

   protected boolean placeStrippedLog(IWorldGenerationReader world, Random rand, BlockPos pos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config, Axis axis) {
      if (TreeFeature.func_236404_a_(world, pos)) {
         BlockState strippedLogState = ((BlockStateProvider)this.strippedLogProvider.orElseThrow(() -> {
            return new IllegalStateException("Stripped log blockstate provider is not set!");
         })).func_225574_a_(rand, pos);
         if (strippedLogState.func_235901_b_(RotatedPillarBlock.field_176298_M)) {
            strippedLogState = (BlockState)strippedLogState.func_206870_a(RotatedPillarBlock.field_176298_M, axis);
         }

         func_236913_a_(world, pos, strippedLogState, bb);
         trunk.add(pos.func_185334_h());
         return true;
      } else {
         return false;
      }
   }

   protected boolean placeBranch(IWorldGenerationReader world, Random rand, BlockPos pos, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      if (TreeFeature.func_236404_a_(world, pos)) {
         BlockState placeState = ((BlockStateProvider)this.branchProvider.orElseThrow(() -> {
            return new IllegalStateException("Branch blockstate provider is not set!");
         })).func_225574_a_(rand, pos);
         if (placeState.func_235901_b_(BlockStateProperties.field_208198_y)) {
            placeState = (BlockState)placeState.func_206870_a(BlockStateProperties.field_208198_y, world.func_217375_a(pos, (state) -> {
               return state.func_204520_s().func_206886_c() == Fluids.field_204546_a;
            }));
         }

         IWorld worldProper;
         if (world instanceof IWorld) {
            worldProper = (IWorld)world;
            placeState = LOTRFeatures.getBlockStateInContext(placeState, worldProper, pos);
         }

         func_236913_a_(world, pos, placeState, bb);
         trunk.add(pos.func_185334_h());
         if (world instanceof IWorld) {
            worldProper = (IWorld)world;
            this.updateNeighboursAfterGeneration(worldProper, pos);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void growRootsDown(IWorldGenerationReader world, Random rand, Mutable rootPos, int rootLength, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      this.growRootsDownBranchingOut(world, rand, rootPos, rootLength, (Direction)null, 0, trunk, bb, config);
   }

   protected void growRootsDownBranchingOut(IWorldGenerationReader world, Random rand, Mutable rootPos, int rootLength, Direction outwardsDir, int outwardsInterval, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      int roots = 0;
      int outwardsStartAt = outwardsInterval > 0 ? rand.nextInt(outwardsInterval) : 0;

      while(rootPos.func_177956_o() >= 0 && TreeFeature.func_236404_a_(world, rootPos) && this.placeBranch(world, rand, rootPos, trunk, bb, config)) {
         if (outwardsDir != null && outwardsInterval > 0 && roots % outwardsInterval == outwardsStartAt) {
            rootPos.func_189536_c(outwardsDir);
         } else {
            rootPos.func_189536_c(Direction.DOWN);
         }

         ++roots;
         if (roots > rootLength) {
            break;
         }
      }

   }

   protected void growRootsDownAndThenOut(IWorldGenerationReader world, Random rand, Mutable rootPos, int rootLength, Direction outwardsDir, int maxOut, Set trunk, MutableBoundingBox bb, BaseTreeFeatureConfig config) {
      int roots = 0;
      int numOut = 0;

      while(rootPos.func_177956_o() >= 0 && TreeFeature.func_236404_a_(world, rootPos) && this.placeBranch(world, rand, rootPos, trunk, bb, config)) {
         if (world.func_217375_a(rootPos.func_177977_b(), AbstractBlockState::func_200132_m)) {
            rootPos.func_189536_c(outwardsDir);
            ++numOut;
            if (numOut > maxOut) {
               break;
            }
         } else {
            rootPos.func_189536_c(Direction.DOWN);
         }

         ++roots;
         if (roots > rootLength) {
            break;
         }
      }

   }

   private void updateNeighboursAfterGeneration(IWorld world, BlockPos pos) {
      Direction[] var3 = Direction.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Direction dir = var3[var5];
         BlockPos adjacentPos = pos.func_177972_a(dir);
         BlockState state = world.func_180495_p(adjacentPos);
         BlockState updatedState = LOTRFeatures.getBlockStateInContext(state, world, adjacentPos);
         world.func_180501_a(adjacentPos, updatedState, 19);
      }

   }
}
