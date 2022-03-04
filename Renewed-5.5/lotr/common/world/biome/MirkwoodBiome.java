package lotr.common.world.biome;

import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Blocks;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class MirkwoodBiome extends LOTRBiomeBase {
   private static final int MIRKWOOD_WATER_COLOR = 1708838;

   public MirkwoodBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.2F).func_205420_b(0.4F).func_205414_c(0.6F).func_205417_d(0.8F), 1708838, major);
   }

   protected MirkwoodBiome(Builder builder, int waterFogColor, boolean major) {
      super(builder, waterFogColor, major);
      this.biomeColors.setGrass(2841381).setFoliage(3096365).setClouds(11123133).setFog(2774107).setFoggy(true).setWater(waterFogColor);
   }

   protected MirkwoodBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 14, 0.0F, LOTRBiomeFeatures.mirkOak(), 8000, LOTRBiomeFeatures.mirkOakParty(), 2000, LOTRBiomeFeatures.mirkOakShrub(), 6000, LOTRBiomeFeatures.oakFancy(), 3000, LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.fir(), 1000, LOTRBiomeFeatures.pine(), 2000, LOTRBiomeFeatures.pineDead(), 200);
      LOTRBiomeFeatures.addGrass(this, builder, 12, GrassBlends.MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
      LOTRBiomeFeatures.addForestFlowers(builder, 1);
      LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 2);
      LOTRBiomeFeatures.addMirkShroomsFreq(builder, 1);
      LOTRBiomeFeatures.addFallenLogs(builder, 2);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addDeer(builder, 3);
      this.addElk(builder, 8);
      this.addBears(builder);
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.MIRKWOOD_PATH.withRepair(0.9F);
   }

   public static class Northern extends MirkwoodBiome {
      public Northern(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.2F).func_205420_b(0.4F).func_205414_c(0.5F).func_205417_d(0.8F), major);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTrees(this, builder, 14, 0.1F, LOTRBiomeFeatures.greenOak(), 500, LOTRBiomeFeatures.greenOakBees(), 5, LOTRBiomeFeatures.greenOakParty(), 100, LOTRBiomeFeatures.greenOakShrub(), 500, LOTRBiomeFeatures.mirkOak(), 50, LOTRBiomeFeatures.mirkOakShrub(), 50, LOTRBiomeFeatures.oak(), 500, LOTRBiomeFeatures.oakBees(), 5, LOTRBiomeFeatures.oakFancy(), 500, LOTRBiomeFeatures.oakFancyBees(), 5, LOTRBiomeFeatures.oakParty(), 100, LOTRBiomeFeatures.oakShrub(), 1000, LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.spruceThin(), 500, LOTRBiomeFeatures.spruceMega(), 200, LOTRBiomeFeatures.spruceThinMega(), 200, LOTRBiomeFeatures.larch(), 500, LOTRBiomeFeatures.fir(), 2000, LOTRBiomeFeatures.pine(), 2000, LOTRBiomeFeatures.pineDead(), 200, LOTRBiomeFeatures.aspen(), 500, LOTRBiomeFeatures.aspenLarge(), 100);
         LOTRBiomeFeatures.addGrass(this, builder, 12, GrassBlends.WITH_FERNS);
         LOTRBiomeFeatures.addDoubleGrass(builder, 6, GrassBlends.DOUBLE_WITH_FERNS);
         LOTRBiomeFeatures.addForestFlowers(builder, 2);
         LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 1);
         LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 2);
         LOTRBiomeFeatures.addFallenLogs(builder, 1);
         LOTRBiomeFeatures.addFoxBerryBushes(builder);
      }

      protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
         super.addAnimals(builder);
         this.addFoxes(builder);
      }
   }

   public static class Mountains extends MirkwoodBiome {
      public Mountains(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(1.5F).func_205420_b(1.5F).func_205414_c(0.28F).func_205417_d(0.9F), 1708838, major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(150).state(Blocks.field_196604_cC).topOnly(), MountainTerrainProvider.MountainLayer.layerBuilder().above(110).useStone()));
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         int treeline = 100;
         LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 3, 0.25F, treeline, LOTRBiomeFeatures.mirkOak(), 200, LOTRBiomeFeatures.spruce(), 300, LOTRBiomeFeatures.fir(), 1000, LOTRBiomeFeatures.pine(), 300, LOTRBiomeFeatures.pineDead(), 50);
         LOTRBiomeFeatures.addTreesBelowTreeline(this, builder, 1, 0.25F, treeline + 10, LOTRBiomeFeatures.mirkOakShrub(), 200, LOTRBiomeFeatures.spruceShrub(), 300, LOTRBiomeFeatures.firShrub(), 1000, LOTRBiomeFeatures.pineShrub(), 300);
         LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.MUTED_WITH_FERNS);
         LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_MUTED_WITH_FERNS);
         LOTRBiomeFeatures.addForestFlowers(builder, 1);
         LOTRBiomeFeatures.addMoreMushroomsFreq(builder, 2);
         LOTRBiomeFeatures.addMirkShroomsFreq(builder, 1);
         LOTRBiomeFeatures.addFallenLogsBelowTreeline(builder, 1, treeline);
      }

      public Biome getRiver(IWorld world) {
         return null;
      }
   }
}
