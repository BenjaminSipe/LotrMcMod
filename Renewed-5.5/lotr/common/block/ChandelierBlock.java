package lotr.common.block;

import java.util.Random;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChandelierBlock extends Block {
   private static final VoxelShape CHANDELIER_SHAPE = Block.func_208617_a(1.0D, 3.0D, 1.0D, 15.0D, 16.0D, 15.0D);

   public ChandelierBlock(Properties properties) {
      super(properties);
   }

   public ChandelierBlock() {
      this(Properties.func_200945_a(Material.field_151594_q).func_200942_a().func_200948_a(0.0F, 2.0F).func_235838_a_(LOTRBlocks.constantLight(14)).func_200947_a(SoundType.field_235597_S_));
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return CHANDELIER_SHAPE;
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return facing == Direction.UP && !this.func_196260_a(state, world, currentPos) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      return func_220055_a(world, pos.func_177984_a(), Direction.DOWN);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState stateIn, World world, BlockPos pos, Random rand) {
      double minW = 0.21875D;
      double maxW = 1.0D - minW;
      double h = 0.6875D;
      int x = pos.func_177958_n();
      int y = pos.func_177956_o();
      int z = pos.func_177952_p();
      this.doChandelierParticles(world, rand, (double)x + minW, (double)y + h, (double)z + minW);
      this.doChandelierParticles(world, rand, (double)x + maxW, (double)y + h, (double)z + minW);
      this.doChandelierParticles(world, rand, (double)x + minW, (double)y + h, (double)z + maxW);
      this.doChandelierParticles(world, rand, (double)x + maxW, (double)y + h, (double)z + maxW);
   }

   protected void doChandelierParticles(World world, Random rand, double x, double y, double z) {
      world.func_195594_a(ParticleTypes.field_197601_L, x, y, z, 0.0D, 0.0D, 0.0D);
      world.func_195594_a(ParticleTypes.field_197631_x, x, y, z, 0.0D, 0.0D, 0.0D);
   }
}
