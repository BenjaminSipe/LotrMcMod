package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenIthilienHills extends LOTRBiomeGenIthilien {
   public LOTRBiomeGenIthilienHills(int i, boolean major) {
      super(i, major);
      this.clearBiomeVariants();
      this.variantChance = 0.2F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.treesPerChunk = 0;
      this.decorator.logsPerChunk = 0;
      this.decorator.flowersPerChunk = 2;
      this.decorator.grassPerChunk = 8;
   }

   public float getTreeIncreaseChance() {
      return 0.25F;
   }
}
