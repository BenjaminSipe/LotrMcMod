package lotr.common.world.biome;

import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class FangornBiome extends LOTRBiomeBase {
   private static final int FANGORN_WATER_COLOR = 4687733;

   public FangornBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.2F).func_205420_b(0.4F).func_205414_c(0.7F).func_205417_d(0.8F), 4687733, major);
      this.biomeColors.setGrass(2652711).setFog(3308875).setFoggy(true).setWater(4687733);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 14, 0.1F, LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakBees(), 1, LOTRBiomeFeatures.oakTall(), 1000, LOTRBiomeFeatures.oakTallBees(), 1, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakFancyBees(), 1, LOTRBiomeFeatures.birch(), 200, LOTRBiomeFeatures.birchBees(), 1, LOTRBiomeFeatures.birchFancy(), 200, LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.beech(), 200, LOTRBiomeFeatures.beechBees(), 1, LOTRBiomeFeatures.beechFancy(), 200, LOTRBiomeFeatures.beechFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 100, LOTRBiomeFeatures.darkOak(), 4000, LOTRBiomeFeatures.oakFangorn(), 300, LOTRBiomeFeatures.beechFangorn(), 75, LOTRBiomeFeatures.oakShrub(), 600, LOTRBiomeFeatures.darkOakShrub(), 600);
      LOTRBiomeFeatures.addGrass(this, builder, 12, GrassBlends.WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_WITH_FERNS);
      LOTRBiomeFeatures.addForestFlowers(builder, 6);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
      LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 1);
      LOTRBiomeFeatures.addFallenLogs(builder, 3);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addDeer(builder, 3);
      this.addBears(builder, 1);
   }
}
