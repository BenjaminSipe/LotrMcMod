package lotr.common.world.biome;

import lotr.common.block.DripstoneBlock;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.MountainTerrainProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

public class ForodwaithBiome extends LOTRBiomeBase {
   public ForodwaithBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.SNOW).func_205419_a(Category.ICY).func_205421_a(0.1F).func_205420_b(0.1F).func_205414_c(0.0F).func_205417_d(0.2F), major);
   }

   protected ForodwaithBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setSky(10069160);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setTop(Blocks.field_196604_cC.func_176223_P());
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addStoneVariants(builder);
      LOTRBiomeFeatures.addPackedIceVeins(builder, 40);
   }

   protected void addDripstones(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addDripstones(builder);
      LOTRBiomeFeatures.addDripstones(builder, (DripstoneBlock)LOTRBlocks.ICE_DRIPSTONE.get(), 2);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 80, 2);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 80, 5);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addLavaSprings(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200786_Z, 1, 1, 2));
   }

   public static class Mountains extends ForodwaithBiome {
      public Mountains(boolean major) {
         super((new Builder()).func_205415_a(RainType.SNOW).func_205419_a(Category.ICY).func_205421_a(2.0F).func_205420_b(2.0F).func_205414_c(0.0F).func_205417_d(0.2F), major);
      }

      protected void setupSurface(MiddleEarthSurfaceConfig config) {
         super.setupSurface(config);
         config.setMountainTerrain(MountainTerrainProvider.createMountainTerrain(MountainTerrainProvider.MountainLayer.layerBuilder().above(100).state(Blocks.field_196604_cC).topOnly(), MountainTerrainProvider.MountainLayer.layerBuilder().above(70).useStone()));
      }

      public Biome getRiver(IWorld world) {
         return null;
      }
   }
}
