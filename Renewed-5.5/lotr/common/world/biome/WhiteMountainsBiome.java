package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class WhiteMountainsBiome extends LOTRBiomeBase {
   public WhiteMountainsBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(1.5F).func_205420_b(2.0F).func_205414_c(0.6F).func_205417_d(0.8F), major);
   }

   protected WhiteMountainsBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected boolean isFoothills() {
      return false;
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      if (!this.isFoothills()) {
         config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(100).state(LOTRBlocks.GONDOR_ROCK).excludeStone()));
      }

   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 1, 0.1F, LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakFancy(), 500, LOTRBiomeFeatures.birch(), 200, LOTRBiomeFeatures.birchFancy(), 50, LOTRBiomeFeatures.beech(), 200, LOTRBiomeFeatures.beechFancy(), 50, LOTRBiomeFeatures.spruce(), 3000, LOTRBiomeFeatures.larch(), 3000, LOTRBiomeFeatures.fir(), 5000, LOTRBiomeFeatures.pine(), 5000, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50);
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.MUTED);
      LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_MUTED);
      LOTRBiomeFeatures.addMountainsFlowers(builder, 2);
   }

   protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      if (!this.isFoothills()) {
         LOTRBiomeFeatures.addWaterLavaSpringsReducedAboveground(builder, 80, 0.15F);
      } else {
         super.addLiquidSprings(builder);
      }

   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }

   public Biome getRiver(IWorld world) {
      return this.isFoothills() ? super.getRiver(world) : null;
   }

   public static class Foothills extends WhiteMountainsBiome {
      public Foothills(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(0.5F).func_205420_b(0.9F).func_205414_c(0.6F).func_205417_d(0.7F), major);
      }

      protected boolean isFoothills() {
         return true;
      }
   }
}
