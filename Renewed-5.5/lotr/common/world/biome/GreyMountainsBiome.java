package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class GreyMountainsBiome extends LOTRBiomeBase {
   public GreyMountainsBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(1.8F).func_205420_b(2.0F).func_205414_c(0.28F).func_205417_d(0.3F), major);
   }

   protected GreyMountainsBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setSky(10862798);
   }

   protected boolean isFoothills() {
      return false;
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      if (!this.isFoothills()) {
         config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(150).state(Blocks.field_196604_cC).topOnly(), MountainTerrainProvider.MountainLayer.layerBuilder().above(110).useStone()));
      }

   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addStoneVariants(builder);
      LOTRBiomeFeatures.addDeepDiorite(builder);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 3, 0.1F, 100, LOTRBiomeFeatures.spruce(), 4000, LOTRBiomeFeatures.spruceThin(), 4000, LOTRBiomeFeatures.spruceMega(), 500, LOTRBiomeFeatures.spruceThinMega(), 100, LOTRBiomeFeatures.spruceDead(), 500, LOTRBiomeFeatures.larch(), 5000, LOTRBiomeFeatures.fir(), 5000, LOTRBiomeFeatures.pine(), 5000, LOTRBiomeFeatures.pineDead(), 250);
      LOTRBiomeFeatures.addGrass(this, builder, 2, GrassBlends.MUTED);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MUTED);
      LOTRBiomeFeatures.addMountainsFlowers(builder, 1);
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

   public static class Foothills extends GreyMountainsBiome {
      public Foothills(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(0.5F).func_205420_b(0.9F).func_205414_c(0.5F).func_205417_d(0.7F), major);
      }

      protected boolean isFoothills() {
         return true;
      }
   }
}
