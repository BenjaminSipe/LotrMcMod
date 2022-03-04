package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class LOTRStainedGlassBlock extends AbstractGlassBlock implements IBeaconBeamColorProvider {
   private final DyeColor glassColor;

   public LOTRStainedGlassBlock(DyeColor color) {
      this(color, Properties.func_200952_a(Material.field_151592_s, color).func_200943_b(0.3F).func_200947_a(SoundType.field_185853_f).func_226896_b_().func_235827_a_(LOTRBlocks::notAllowSpawn).func_235828_a_(LOTRBlocks::posPredicateFalse).func_235842_b_(LOTRBlocks::posPredicateFalse).func_235847_c_(LOTRBlocks::posPredicateFalse));
   }

   public LOTRStainedGlassBlock(DyeColor color, Properties properties) {
      super(properties);
      this.glassColor = color;
   }

   public DyeColor func_196457_d() {
      return this.glassColor;
   }

   public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
      return true;
   }
}
