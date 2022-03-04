package lotr.common.block;

import java.util.Random;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRTags;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MordorRockBlock extends LOTRStoneBlock implements IGrowable {
   public static final BooleanProperty MOSSY;

   public MordorRockBlock(MaterialColor materialColor) {
      super(materialColor);
      this.func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(MOSSY, false));
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{MOSSY});
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if (facing != Direction.UP) {
         return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
      } else {
         Block block = facingState.func_177230_c();
         return (BlockState)state.func_206870_a(MOSSY, block == LOTRBlocks.MORDOR_MOSS.get());
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if (rand.nextInt(32) == 0 && !LOTRUtil.hasSolidSide(world, pos.func_177984_a(), Direction.DOWN)) {
         VoxelShape shape = state.func_196954_c(world, pos);
         double x = rand.nextDouble();
         double z = rand.nextDouble();
         if (x >= shape.func_197762_b(Axis.X) && x <= shape.func_197758_c(Axis.X) && z >= shape.func_197762_b(Axis.Z) && z <= shape.func_197758_c(Axis.Z)) {
            double topY = state.func_196954_c(world, pos).func_197760_b(Axis.Y, x, z);
            world.func_195594_a(ParticleTypes.field_197601_L, (double)pos.func_177958_n() + x, (double)pos.func_177956_o() + topY, (double)pos.func_177952_p() + z, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public boolean func_176473_a(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
      BlockPos abovePos = pos.func_177984_a();
      return world.func_180495_p(abovePos).isAir(world, abovePos);
   }

   public boolean func_180670_a(World world, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
      growMordorPlants(world, rand, pos, state);
   }

   public static void growMordorPlants(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
      BlockPos above = pos.func_177984_a();
      int tries = 128;

      label32:
      for(int i = 0; i < tries; ++i) {
         BlockPos plantPos = above;

         for(int triesHere = 0; triesHere < i / 16; ++triesHere) {
            plantPos = plantPos.func_177982_a(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
            if (!world.func_180495_p(plantPos.func_177977_b()).func_235714_a_(LOTRTags.Blocks.MORDOR_PLANT_SURFACES) || world.func_180495_p(plantPos).func_235785_r_(world, plantPos)) {
               continue label32;
            }
         }

         BlockState curBlock = world.func_180495_p(plantPos);
         if (curBlock.isAir(world, plantPos)) {
            BlockState plant = createMordorGrowBlock(world, rand, plantPos);
            if (plant.func_196955_c(world, plantPos)) {
               world.func_180501_a(plantPos, plant, 3);
            }
         }
      }

   }

   private static BlockState createMordorGrowBlock(ServerWorld world, Random rand, BlockPos pos) {
      boolean isRock = world.func_180495_p(pos.func_177977_b()).func_177230_c() == LOTRBlocks.MORDOR_ROCK.get();
      if (rand.nextInt(60) == 0) {
         return ((Block)LOTRBlocks.MORGUL_SHROOM.get()).func_176223_P();
      } else if (rand.nextInt(16) == 0) {
         return ((Block)LOTRBlocks.MORDOR_THORN.get()).func_176223_P();
      } else {
         return rand.nextInt(isRock ? 4 : 8) == 0 ? ((Block)LOTRBlocks.MORDOR_MOSS.get()).func_176223_P() : ((Block)LOTRBlocks.MORDOR_GRASS.get()).func_176223_P();
      }
   }

   static {
      MOSSY = LOTRBlockStates.MOSSY;
   }
}
