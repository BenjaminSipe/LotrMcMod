package lotr.common.world.biome.variant;

public class LOTRBiomeVariantDenseForest extends LOTRBiomeVariant {
   public LOTRBiomeVariantDenseForest(int i, String s) {
      super(i, s, LOTRBiomeVariant.VariantScale.LARGE);
      this.setTemperatureRainfall(0.1F, 0.3F);
      this.setHeight(0.5F, 2.0F);
      this.setTrees(8.0F);
      this.setGrass(2.0F);
   }
}
