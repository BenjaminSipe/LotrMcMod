package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class OrcPlatingBlock extends Block {
   public OrcPlatingBlock(Properties properties) {
      super(properties);
   }

   public OrcPlatingBlock() {
      this(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151670_w).func_235861_h_().func_200948_a(3.0F, 6.0F).func_200947_a(SoundType.field_235590_L_));
   }
}
