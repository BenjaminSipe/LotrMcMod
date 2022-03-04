package lotr.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class TranslucentMineralBlock extends MineralBlock implements IBeaconBeamColorProvider {
   private final DyeColor beaconBeamColor;

   public TranslucentMineralBlock(Properties properties, int harvestLvl, DyeColor color) {
      super(properties, harvestLvl);
      this.beaconBeamColor = color;
   }

   public DyeColor func_196457_d() {
      return this.beaconBeamColor;
   }

   public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
      return true;
   }
}
