package lotr.common.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LOTRWallBlock extends WallBlock {
   private final Block modelBlock;

   public LOTRWallBlock(Block block) {
      super(Properties.func_200950_a(block));
      this.modelBlock = block;
   }

   public LOTRWallBlock(Supplier block) {
      this((Block)block.get());
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      this.modelBlock.func_180655_c(state, world, pos, rand);
   }
}
