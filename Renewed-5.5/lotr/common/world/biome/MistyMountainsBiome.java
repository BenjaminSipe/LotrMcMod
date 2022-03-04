package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Blocks;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class MistyMountainsBiome extends LOTRBiomeBase {
   public MistyMountainsBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(2.0F).func_205420_b(2.0F).func_205414_c(0.2F).func_205417_d(0.5F), major);
   }

   protected MistyMountainsBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setSky(12241873);
   }

   protected boolean isFoothills() {
      return false;
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      if (!this.isFoothills()) {
         config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(120).state(Blocks.field_196604_cC).topOnly(), MountainTerrainProvider.MountainLayer.layerBuilder().above(90).useStone()));
      }

   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addGranite(builder);
      LOTRBiomeFeatures.addDeepDiorite(builder);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addMithrilOre(builder, 4);
      LOTRBiomeFeatures.addGlowstoneOre(builder);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] treeParams = new Object[]{LOTRBiomeFeatures.spruce(), 400, LOTRBiomeFeatures.spruceThin(), 400, LOTRBiomeFeatures.spruceMega(), 100, LOTRBiomeFeatures.spruceThinMega(), 20, LOTRBiomeFeatures.spruceDead(), 50, LOTRBiomeFeatures.fir(), 500, LOTRBiomeFeatures.pine(), 500, LOTRBiomeFeatures.pineDead(), 50, LOTRBiomeFeatures.larch(), 300};
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.05F, treeParams);
      LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 2, 0.1F, 100, treeParams);
      LOTRBiomeFeatures.addGrass(this, builder, 3, GrassBlends.MUTED);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_MUTED);
      LOTRBiomeFeatures.addMountainsFlowers(builder, 1, LOTRBlocks.DWARFWORT.get(), 1);
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

   public boolean hasMountainsMist() {
      return true;
   }

   public static class Foothills extends MistyMountainsBiome {
      public Foothills(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(0.7F).func_205420_b(0.9F).func_205414_c(0.25F).func_205417_d(0.6F), major);
      }

      protected boolean isFoothills() {
         return true;
      }

      public boolean hasMountainsMist() {
         return false;
      }
   }
}
