package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class LOTRWoodBlock extends RotatedPillarBlock {
   public final MaterialColor barkColor;

   public LOTRWoodBlock(MaterialColor bark) {
      super(Properties.func_200949_a(Material.field_151575_d, bark).func_200943_b(2.0F).func_200947_a(SoundType.field_185848_a));
      this.barkColor = bark;
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 5, 5);
   }

   public LOTRWoodBlock(Supplier logBlock) {
      this(((LOTRLogBlock)logBlock.get()).barkColor);
   }
}
