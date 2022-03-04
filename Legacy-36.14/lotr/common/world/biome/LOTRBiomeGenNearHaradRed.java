package lotr.common.world.biome;

import net.minecraft.init.Blocks;

public class LOTRBiomeGenNearHaradRed extends LOTRBiomeGenNearHarad {
   public LOTRBiomeGenNearHaradRed(int i, boolean major) {
      super(i, major);
      this.func_76745_m();
      this.field_76752_A = Blocks.field_150354_m;
      this.topBlockMeta = 1;
      this.field_76753_B = Blocks.field_150354_m;
      this.fillerBlockMeta = 1;
      this.decorator.clearRandomStructures();
      this.decorator.clearVillages();
   }
}
