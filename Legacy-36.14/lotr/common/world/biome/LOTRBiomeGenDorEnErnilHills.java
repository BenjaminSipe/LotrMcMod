package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenDorEnErnilHills extends LOTRBiomeGenDorEnErnil {
   public LOTRBiomeGenDorEnErnilHills(int i, boolean major) {
      super(i, major);
      this.clearBiomeVariants();
      this.variantChance = 0.2F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.treesPerChunk = 1;
   }

   public boolean getEnableRiver() {
      return false;
   }

   public int spawnCountMultiplier() {
      return super.spawnCountMultiplier() * 3;
   }
}
