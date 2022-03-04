package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import lotr.common.block.RottenLogBlock;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.map.RoadPointCache;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class FallenLogFeature extends Feature {
   public FallenLogFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, FallenLogFeatureConfig config) {
      if (!this.isSuitablePositionForLog(world, pos)) {
         return false;
      } else if (!RoadPointCache.checkNotGeneratingOnRoad(world, pos)) {
         return false;
      } else {
         Direction dir;
         int length;
         if (!config.horizontalOnly && rand.nextInt(3) == 0) {
            dir = Direction.UP;
            length = MathHelper.func_76136_a(rand, 1, 2);
         } else {
            dir = Plane.HORIZONTAL.func_179518_a(rand);
            length = MathHelper.func_76136_a(rand, 2, 7);
         }

         Block logBlock = config.isStripped ? (Block)LOTRBlocks.STRIPPED_ROTTEN_LOG.get() : (Block)LOTRBlocks.ROTTEN_LOG.get();
         BlockState logAxisState = (BlockState)logBlock.func_176223_P().func_206870_a(RotatedPillarBlock.field_176298_M, dir.func_176740_k());
         Mutable movingPos = (new Mutable()).func_189533_g(pos);

         for(int i = 0; i < length && this.isSuitablePositionForLog(world, movingPos); ++i) {
            boolean waterlogged = world.func_204610_c(movingPos).func_206886_c() == Fluids.field_204546_a;
            BlockState placeState = (BlockState)logAxisState.func_206870_a(RottenLogBlock.WATERLOGGED, waterlogged);
            world.func_180501_a(movingPos, placeState, 2);
            if (dir.func_176740_k() != Axis.Y) {
               LOTRFeatures.setGrassToDirtBelow(world, movingPos);
            }

            movingPos.func_189536_c(dir);
         }

         return true;
      }
   }

   private boolean isSuitablePositionForLog(IWorld world, BlockPos pos) {
      BlockPos belowPos = pos.func_177977_b();
      if (!world.func_180495_p(belowPos).func_224755_d(world, belowPos, Direction.UP)) {
         return false;
      } else {
         BlockState replacingState = world.func_180495_p(pos);
         return replacingState.isAir(world, pos) || replacingState.func_185904_a() == Material.field_151586_h;
      }
   }
}
