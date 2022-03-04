package lotr.common.world.biome.variant;

public class LOTRBiomeVariantDeadForest extends LOTRBiomeVariant {
   public LOTRBiomeVariantDeadForest(int i, String s) {
      super(i, s, LOTRBiomeVariant.VariantScale.ALL);
      this.setTemperatureRainfall(0.0F, -0.3F);
      this.setTrees(3.0F);
      this.setGrass(0.5F);
   }
}
