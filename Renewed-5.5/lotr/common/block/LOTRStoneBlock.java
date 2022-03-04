package lotr.common.block;

import java.util.function.Supplier;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class LOTRStoneBlock extends Block {
   public LOTRStoneBlock(Properties properties) {
      super(properties);
   }

   public LOTRStoneBlock(MaterialColor materialColor) {
      this(materialColor, 1.5F, 6.0F);
   }

   public LOTRStoneBlock(MaterialColor materialColor, float hard, float res) {
      this(Properties.func_200949_a(Material.field_151576_e, materialColor).func_235861_h_().func_200948_a(hard, res));
   }

   public LOTRStoneBlock(MaterialColor materialColor, int light) {
      this(Properties.func_200949_a(Material.field_151576_e, materialColor).func_235861_h_().func_200948_a(1.5F, 6.0F).func_235838_a_(LOTRBlocks.constantLight(light)));
   }

   public LOTRStoneBlock(Supplier blockSup) {
      this(Properties.func_200950_a((AbstractBlock)blockSup.get()));
   }
}
