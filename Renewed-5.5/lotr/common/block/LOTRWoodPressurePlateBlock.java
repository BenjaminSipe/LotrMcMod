package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.PressurePlateBlock.Sensitivity;
import net.minecraft.block.material.Material;

public class LOTRWoodPressurePlateBlock extends PressurePlateBlock {
   public LOTRWoodPressurePlateBlock(Supplier planks) {
      super(Sensitivity.EVERYTHING, Properties.func_200949_a(Material.field_151575_d, ((LOTRPlanksBlock)planks.get()).planksColor).func_200942_a().func_200943_b(0.5F).func_200947_a(SoundType.field_185848_a));
   }
}
