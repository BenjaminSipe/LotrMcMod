package lotr.common.block;

import java.util.Random;
import java.util.function.Supplier;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PipeweedCropBlock extends LOTRCropBlock {
   public PipeweedCropBlock(Properties properties, Supplier sup) {
      super(properties, sup);
   }

   public PipeweedCropBlock(Supplier sup) {
      super(sup);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if (this.func_185525_y(state)) {
         ((Block)LOTRBlocks.WILD_PIPEWEED.get()).func_180655_c(state, world, pos, rand);
      }

   }
}
