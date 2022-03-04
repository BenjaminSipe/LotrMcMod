package lotr.common.block;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MithrilOreBlock extends LOTROreBlock {
   public MithrilOreBlock(Properties properties) {
      super(properties);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if (world.field_73012_v.nextInt(3) == 0) {
         TreasurePileBlock.doTreasureParticles(state, world, pos, rand);
      }

   }
}
