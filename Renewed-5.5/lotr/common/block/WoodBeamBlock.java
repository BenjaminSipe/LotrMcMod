package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class WoodBeamBlock extends RotatedPillarBlock {
   public WoodBeamBlock(MaterialColor wood, MaterialColor bark) {
      super(Properties.func_235836_a_(Material.field_151575_d, LOTRLogBlock.logStateToMaterialColor(wood, bark)).func_200943_b(2.0F).func_200947_a(SoundType.field_185848_a));
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 5, 5);
   }

   public WoodBeamBlock(Supplier logBlock) {
      this(((LOTRLogBlock)logBlock.get()).woodColor, ((LOTRLogBlock)logBlock.get()).barkColor);
   }
}
