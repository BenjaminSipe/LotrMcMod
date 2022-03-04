package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class LOTRSandstoneBlock extends Block {
   public LOTRSandstoneBlock(MaterialColor color) {
      super(Properties.func_200949_a(Material.field_151576_e, color).func_235861_h_().func_200943_b(0.8F));
   }
}
