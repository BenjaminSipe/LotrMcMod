package lotr.common.block;

import java.util.function.Function;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction.Axis;

public class LOTRLogBlock extends RotatedPillarBlock {
   public final MaterialColor barkColor;
   public final MaterialColor woodColor;

   public LOTRLogBlock(Properties properties, MaterialColor bark, MaterialColor wood) {
      super(properties);
      this.barkColor = bark;
      this.woodColor = wood;
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 5, 5);
   }

   public LOTRLogBlock(MaterialColor wood, MaterialColor bark) {
      this(Properties.func_235836_a_(Material.field_151575_d, logStateToMaterialColor(wood, bark)).func_200943_b(2.0F).func_200947_a(SoundType.field_185848_a), wood, bark);
   }

   public static Function logStateToMaterialColor(MaterialColor wood, MaterialColor bark) {
      return (state) -> {
         return state.func_177229_b(RotatedPillarBlock.field_176298_M) == Axis.Y ? wood : bark;
      };
   }
}
