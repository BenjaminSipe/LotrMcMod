package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class LOTRGlassBlock extends GlassBlock {
   public LOTRGlassBlock() {
      this(Properties.func_200945_a(Material.field_151592_s).func_200943_b(0.3F).func_200947_a(SoundType.field_185853_f).func_226896_b_().func_235827_a_(LOTRBlocks::notAllowSpawn).func_235828_a_(LOTRBlocks::posPredicateFalse).func_235842_b_(LOTRBlocks::posPredicateFalse).func_235847_c_(LOTRBlocks::posPredicateFalse));
   }

   public LOTRGlassBlock(Properties properties) {
      super(properties);
   }

   public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
      return true;
   }
}
