package lotr.common.block;

import net.minecraft.block.PaneBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class LOTRBarsBlock extends PaneBlock {
   public LOTRBarsBlock(Properties properties) {
      super(properties);
   }

   public LOTRBarsBlock() {
      this(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151660_b).func_235861_h_().func_200948_a(5.0F, 6.0F).func_200947_a(SoundType.field_185852_e).func_226896_b_());
   }
}
