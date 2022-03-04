package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;

public class ClayTilingBlock extends Block {
   public ClayTilingBlock(Properties properties) {
      super(properties);
   }

   public ClayTilingBlock(MaterialColor materialColor) {
      this(Properties.func_200949_a(Material.field_151576_e, materialColor).func_235861_h_().func_200948_a(1.25F, 4.2F).func_200947_a(LOTRBlocks.SOUND_CERAMIC));
   }

   public ClayTilingBlock(DyeColor dyeColor) {
      this(dyeColor.func_196055_e());
   }
}
