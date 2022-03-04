package lotr.common.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lotr.common.entity.item.FallingTreasureBlockEntity;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.Tags.Blocks;

public class TreasurePileBlock extends FallingBlock implements IWaterLoggable {
   public static final IntegerProperty PILE_LEVEL;
   public static final int PILE_MAX_LEVEL = 8;
   public static final BooleanProperty WATERLOGGED;
   public static final BooleanProperty SUCH_WEALTH;
   private static final Map PILE_SHAPES;
   private static final VoxelShape COLLISION_FULL;
   private static final VoxelShape COLLISION_HALF;
   private static final VoxelShape COLLISION_MIN;

   public TreasurePileBlock(Properties properties) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)((BlockState)this.func_176223_P().func_206870_a(PILE_LEVEL, 1)).func_206870_a(WATERLOGGED, false)).func_206870_a(SUCH_WEALTH, false));
   }

   public TreasurePileBlock(MaterialColor color) {
      this(Properties.func_200949_a(Material.field_151594_q, color).func_200943_b(0.4F).harvestTool(ToolType.SHOVEL).func_200942_a().func_200947_a(LOTRBlocks.SOUND_TREASURE));
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{PILE_LEVEL, WATERLOGGED, SUCH_WEALTH});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      int level = (Integer)state.func_177229_b(PILE_LEVEL);
      VoxelShape shape = (VoxelShape)PILE_SHAPES.get(level);
      if (shape == null) {
         shape = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, (double)level / 8.0D * 16.0D, 16.0D);
         PILE_SHAPES.put(level, shape);
      }

      return shape;
   }

   public VoxelShape func_220071_b(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      int level = (Integer)state.func_177229_b(PILE_LEVEL);
      if (level == 8) {
         return COLLISION_FULL;
      } else {
         return level >= 4 ? COLLISION_HALF : COLLISION_MIN;
      }
   }

   public boolean func_196266_a(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
      return type == PathType.LAND && (Integer)state.func_177229_b(PILE_LEVEL) < 4 ? true : super.func_196266_a(state, world, pos, type);
   }

   public boolean func_196253_a(BlockState state, BlockItemUseContext context) {
      int level = (Integer)state.func_177229_b(PILE_LEVEL);
      if (context.func_195996_i().func_77973_b() == this.func_199767_j() && level < 8 && context.func_196012_c()) {
         return context.func_196000_l() == Direction.UP;
      } else {
         return super.func_196253_a(state, context);
      }
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      IWorld world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      boolean water = world.func_204610_c(pos).func_206886_c() == Fluids.field_204546_a;
      BlockState placeState = this.func_176223_P();
      BlockState currentState = world.func_180495_p(pos);
      if (currentState.func_177230_c() == this) {
         int level = (Integer)currentState.func_177229_b(PILE_LEVEL);
         placeState = (BlockState)currentState.func_206870_a(PILE_LEVEL, Math.min(level + 1, 8));
      }

      return (BlockState)((BlockState)placeState.func_206870_a(WATERLOGGED, water)).func_206870_a(SUCH_WEALTH, this.isSuchWealth(world, pos));
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      BlockPos belowPos = pos.func_177977_b();
      BlockState belowState = world.func_180495_p(belowPos);
      return Block.func_208061_a(belowState.func_196951_e(world, belowPos), Direction.UP);
   }

   private boolean isSuchWealth(IWorldReader world, BlockPos pos) {
      return world.func_180495_p(pos.func_177977_b()).func_235714_a_(Blocks.DIRT);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.func_177229_b(WATERLOGGED)) {
         world.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(world));
      }

      if (facing == Direction.DOWN) {
         state = (BlockState)state.func_206870_a(SUCH_WEALTH, this.isSuchWealth(world, currentPos));
      }

      return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public void func_225534_a_(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
      BlockPos belowPos = pos.func_177977_b();
      BlockState belowState = world.func_180495_p(belowPos);
      if (world.func_175623_d(pos.func_177977_b()) || (FallingBlock.func_185759_i(belowState) || !this.func_196260_a(state, world, pos)) && pos.func_177956_o() >= 0) {
         FallingTreasureBlockEntity fallingBlock = new FallingTreasureBlockEntity(world, (double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o(), (double)pos.func_177952_p() + 0.5D, world.func_180495_p(pos));
         world.func_217376_c(fallingBlock);
      }

   }

   public void func_176502_a_(World world, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity fallingBlock) {
      this.onEndFallingTreasure(world, pos, fallingState, hitState);
   }

   public void onEndFallingTreasure(World world, BlockPos pos, BlockState fallingState, BlockState hitState) {
      BlockState updatedState = (BlockState)fallingState.func_206870_a(SUCH_WEALTH, this.isSuchWealth(world, pos));
      world.func_180501_a(pos, updatedState, 3);
   }

   public void func_176199_a(World world, BlockPos pos, Entity entity) {
      double speedSq = entity.func_213322_ci().func_189985_c();
      if (speedSq > 0.01D && !entity.func_226271_bk_()) {
         this.spawnWalkingParticles(world, pos, entity, 1 + world.field_73012_v.nextInt(2));
      }

   }

   private void spawnWalkingParticles(World world, BlockPos pos, Entity entity, int num) {
      BlockState state = world.func_180495_p(pos);

      for(int l = 0; l < num; ++l) {
         double x = (double)((float)pos.func_177958_n() + world.field_73012_v.nextFloat());
         double y = (double)pos.func_177956_o() + state.func_196954_c(world, pos).func_197758_c(Axis.Y);
         double z = (double)((float)pos.func_177952_p() + world.field_73012_v.nextFloat());
         double velX = (double)MathHelper.func_151240_a(world.field_73012_v, -0.15F, 0.15F);
         double velY = (double)MathHelper.func_151240_a(world.field_73012_v, 0.1F, 0.4F);
         double velZ = (double)MathHelper.func_151240_a(world.field_73012_v, -0.15F, 0.15F);
         world.func_195594_a(new BlockParticleData(ParticleTypes.field_197611_d, state), x, y, z, velX, velY, velZ);
      }

   }

   public void func_180658_a(World world, BlockPos pos, Entity entity, float fallDistance) {
      super.func_180658_a(world, pos, entity, fallDistance);
      this.spawnWalkingParticles(world, pos, entity, 8);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if (world.field_73012_v.nextInt(3) == 0) {
         doTreasureParticles(state, world, pos, rand);
      }

   }

   public static void doTreasureParticles(BlockState state, World world, BlockPos pos, Random rand) {
      Direction face = Direction.func_239631_a_(rand);
      BlockPos facePos = pos.func_177972_a(face);
      if (!world.func_180495_p(facePos).func_224755_d(world, facePos, face.func_176734_d())) {
         double x = 0.5D;
         double z = 0.5D;
         double y = 0.0D;
         double outside = 0.02D;
         double width = 0.5D + outside;
         double minY = state.func_196954_c(world, pos).func_197762_b(Axis.Y) - outside;
         double maxY = state.func_196954_c(world, pos).func_197758_c(Axis.Y) + outside;
         if (face.func_176740_k() == Axis.X) {
            x += (double)face.func_82601_c() * width;
            y = MathHelper.func_82716_a(rand, minY, maxY);
            z += MathHelper.func_82716_a(rand, -width, width);
         } else if (face.func_176740_k() == Axis.Y) {
            x += MathHelper.func_82716_a(rand, -width, width);
            if (face == Direction.DOWN) {
               y = minY;
            } else if (face == Direction.UP) {
               y = maxY;
            }

            z += MathHelper.func_82716_a(rand, -width, width);
         } else if (face.func_176740_k() == Axis.Z) {
            x += MathHelper.func_82716_a(rand, -width, width);
            y = MathHelper.func_82716_a(rand, minY, maxY);
            z += (double)face.func_82599_e() * width;
         }

         x += (double)pos.func_177958_n();
         y += (double)pos.func_177956_o();
         z += (double)pos.func_177952_p();
         world.func_195594_a((IParticleData)LOTRParticles.GLITTER.get(), x, y, z, 0.0D, 0.0D, 0.0D);
      }

   }

   public FluidState func_204507_t(BlockState state) {
      return (Boolean)state.func_177229_b(WATERLOGGED) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
   }

   static {
      PILE_LEVEL = LOTRBlockStates.TREASURE_PILE_LEVEL;
      WATERLOGGED = BlockStateProperties.field_208198_y;
      SUCH_WEALTH = LOTRBlockStates.SUCH_WEALTH;
      PILE_SHAPES = new HashMap();
      COLLISION_FULL = VoxelShapes.func_197868_b();
      COLLISION_HALF = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
      COLLISION_MIN = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
   }
}
