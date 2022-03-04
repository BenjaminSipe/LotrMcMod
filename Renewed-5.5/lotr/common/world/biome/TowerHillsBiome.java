package lotr.common.world.biome;

import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class TowerHillsBiome extends LOTRBiomeBase {
   public TowerHillsBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(0.7F).func_205420_b(0.9F).func_205414_c(0.7F).func_205417_d(0.9F), major);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addStoneVariants(builder);
      LOTRBiomeFeatures.addAndesite(builder);
      LOTRBiomeFeatures.addDiorite(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addBoulders(builder);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 12, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.1F, LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakFancy(), 100, LOTRBiomeFeatures.oakBees(), 1, LOTRBiomeFeatures.oakFancyBees(), 1, LOTRBiomeFeatures.birch(), 500, LOTRBiomeFeatures.birchFancy(), 100, LOTRBiomeFeatures.birchBees(), 1, LOTRBiomeFeatures.birchFancyBees(), 1, LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 50);
      LOTRBiomeFeatures.addGrass(this, builder, 5, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
      LOTRBiomeFeatures.addAthelasChance(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.HIGH_ELVEN.withRepair(0.9F);
   }
}
