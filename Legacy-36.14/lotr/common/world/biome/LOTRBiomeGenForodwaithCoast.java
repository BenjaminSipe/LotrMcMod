package lotr.common.world.biome;

import net.minecraft.init.Blocks;

public class LOTRBiomeGenForodwaithCoast extends LOTRBiomeGenForodwaith {
   public LOTRBiomeGenForodwaithCoast(int i, boolean major) {
      super(i, major);
      this.field_76752_A = Blocks.field_150348_b;
      this.topBlockMeta = 0;
      this.field_76753_B = this.field_76752_A;
      this.fillerBlockMeta = this.topBlockMeta;
      this.biomeTerrain.setXZScale(30.0D);
      this.clearBiomeVariants();
      this.decorator.clearRandomStructures();
   }
}
