package lotr.common.world.biome;

import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class OldForestBiome extends LOTRBiomeBase {
   public OldForestBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.2F).func_205420_b(0.3F).func_205414_c(0.6F).func_205417_d(0.9F), major);
      this.biomeColors.setGrass(5477193).setFoliage(3172394).setFog(3627845).setFoggy(true);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 18, 0.0F, LOTRBiomeFeatures.oak(), 3000, LOTRBiomeFeatures.oakVines(), 1000, LOTRBiomeFeatures.oakTall(), 6000, LOTRBiomeFeatures.oakTallVines(), 2000, LOTRBiomeFeatures.oakFancy(), 4000, LOTRBiomeFeatures.oakBees(), 3, LOTRBiomeFeatures.oakBeesVines(), 1, LOTRBiomeFeatures.oakTallBees(), 6, LOTRBiomeFeatures.oakTallBeesVines(), 2, LOTRBiomeFeatures.oakFancyBees(), 4, LOTRBiomeFeatures.oakDead(), 1000, LOTRBiomeFeatures.oakParty(), 300, LOTRBiomeFeatures.oakShrub(), 2000, LOTRBiomeFeatures.darkOak(), 8000, LOTRBiomeFeatures.darkOakParty(), 500, LOTRBiomeFeatures.darkOakShrub(), 1000, LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.firShrub(), 200, LOTRBiomeFeatures.shirePine(), 1000, LOTRBiomeFeatures.pineShrub(), 100);
      LOTRBiomeFeatures.addGrass(this, builder, 12, GrassBlends.WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 5, GrassBlends.DOUBLE_WITH_FERNS);
      LOTRBiomeFeatures.addForestFlowers(builder, 1);
      LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 2);
      LOTRBiomeFeatures.addFallenLogs(builder, 1);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }
}
