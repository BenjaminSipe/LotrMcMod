package lotr.common.world.biome;

import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class ChetwoodBiome extends BreelandBiome {
   public ChetwoodBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.2F).func_205420_b(0.4F).func_205414_c(0.8F).func_205417_d(0.9F), major);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 4, 0.1F, TreeCluster.of(8, 15), this.breelandTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_WITH_FERNS);
      LOTRBiomeFeatures.addForestFlowers(builder, 4);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 2);
      LOTRBiomeFeatures.addAthelasChance(builder);
      LOTRBiomeFeatures.addFoxBerryBushes(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addWolves(builder, 1);
      this.addDeer(builder, 2);
      this.addFoxes(builder, 2);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.PATH;
   }
}
