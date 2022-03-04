package lotr.common.world.biome;

import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

public class MERiverBiome extends LOTRBiomeBase {
   public MERiverBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.RIVER).func_205421_a(-0.75F).func_205420_b(0.0F).func_205414_c(0.5F).func_205417_d(0.5F), major);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.5F, LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakFancy(), 100);
      LOTRBiomeFeatures.addGrass(this, builder, 4, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDefaultFlowers(builder, 2);
      LOTRBiomeFeatures.addSeagrass(builder, 48, 0.4F);
   }

   protected void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addReeds(builder);
      LOTRBiomeFeatures.addRiverRushes(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      builder.func_242575_a(EntityClassification.WATER_AMBIENT, new Spawners(EntityType.field_203778_ae, 5, 1, 5));
   }

   public boolean isRiver() {
      return true;
   }
}
