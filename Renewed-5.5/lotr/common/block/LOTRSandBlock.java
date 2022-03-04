package lotr.common.block;

import net.minecraft.block.SandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class LOTRSandBlock extends SandBlock {
   public LOTRSandBlock(int particleRGB, MaterialColor mapColor) {
      super(particleRGB, Properties.func_200949_a(Material.field_151595_p, mapColor).func_200943_b(0.5F).func_200947_a(SoundType.field_185855_h));
   }
}
