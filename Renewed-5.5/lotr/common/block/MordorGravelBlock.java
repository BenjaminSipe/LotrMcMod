package lotr.common.block;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MordorGravelBlock extends LOTRGravelBlock implements IGrowable {
   public MordorGravelBlock(MaterialColor materialColor, int dust) {
      super(materialColor, dust);
   }

   public boolean func_176473_a(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
      BlockPos abovePos = pos.func_177984_a();
      return world.func_180495_p(abovePos).isAir(world, abovePos);
   }

   public boolean func_180670_a(World world, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
      MordorRockBlock.growMordorPlants(world, rand, pos, state);
   }
}
