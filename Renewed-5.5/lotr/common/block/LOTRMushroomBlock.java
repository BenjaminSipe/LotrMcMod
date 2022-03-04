package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class LOTRMushroomBlock extends MushroomBlock {
   public LOTRMushroomBlock() {
      super(Properties.func_200945_a(Material.field_151585_k).func_200942_a().func_200944_c().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c));
      CompostingHelper.prepareCompostable(this, 0.65F);
   }

   public boolean func_176473_a(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
      return false;
   }
}
