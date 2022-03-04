package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class LOTRPlanksBlock extends Block {
   public final MaterialColor planksColor;

   public LOTRPlanksBlock(MaterialColor color) {
      super(Properties.func_200949_a(Material.field_151575_d, color).func_200948_a(2.0F, 3.0F).func_200947_a(SoundType.field_185848_a));
      this.planksColor = color;
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 5, 20);
   }
}
