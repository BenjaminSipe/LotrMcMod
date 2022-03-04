package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.SoundType;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;

public class LOTRStandingSignBlock extends StandingSignBlock {
   public final WoodType signType;

   public LOTRStandingSignBlock(Supplier planks, WoodType type) {
      super(Properties.func_200949_a(Material.field_151575_d, ((LOTRPlanksBlock)planks.get()).planksColor).func_200942_a().func_200943_b(1.0F).func_200947_a(SoundType.field_185848_a), type);
      this.signType = type;
      SignSetupHelper.add(this);
   }
}
