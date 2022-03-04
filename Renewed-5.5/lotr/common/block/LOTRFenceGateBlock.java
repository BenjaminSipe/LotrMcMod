package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;

public class LOTRFenceGateBlock extends FenceGateBlock {
   public LOTRFenceGateBlock(Supplier planks) {
      super(Properties.func_200949_a(Material.field_151575_d, ((LOTRPlanksBlock)planks.get()).planksColor).func_200948_a(2.0F, 3.0F).func_200947_a(SoundType.field_185848_a));
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 5, 20);
   }
}
