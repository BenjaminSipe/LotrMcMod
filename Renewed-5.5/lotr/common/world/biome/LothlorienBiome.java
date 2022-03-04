package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class LothlorienBiome extends LOTRBiomeBase {
   public LothlorienBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.1F).func_205420_b(0.3F).func_205414_c(0.8F).func_205417_d(0.8F), major);
   }

   protected LothlorienBiome(Builder builder, boolean major) {
      super(builder, major);
      this.biomeColors.setGrass(12837416);
      this.biomeColors.setFog(16770660);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setPodzol(false);
      config.setSurfaceNoisePaths(true);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addEdhelvirOre(builder);
   }

   protected void addCobwebs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addBiomeSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addWhiteSandSediments(builder);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 3, 0.1F, LOTRBiomeFeatures.mallorn(), 3000, LOTRBiomeFeatures.mallornBees(), 30, LOTRBiomeFeatures.mallornBoughs(), 6000, LOTRBiomeFeatures.mallornParty(), 1000, LOTRBiomeFeatures.oak(), 3000, LOTRBiomeFeatures.oakFancy(), 500, LOTRBiomeFeatures.oakBees(), 30, LOTRBiomeFeatures.oakFancyBees(), 5, LOTRBiomeFeatures.oakParty(), 100, LOTRBiomeFeatures.larch(), 2000, LOTRBiomeFeatures.beech(), 1000, LOTRBiomeFeatures.beechFancy(), 200, LOTRBiomeFeatures.beechBees(), 10, LOTRBiomeFeatures.beechFancyBees(), 2, LOTRBiomeFeatures.beechParty(), 100, LOTRBiomeFeatures.aspen(), 1000, LOTRBiomeFeatures.aspenLarge(), 200, LOTRBiomeFeatures.lairelosse(), 500);
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 2, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addForestFlowers(builder, 6, LOTRBlocks.ELANOR.get(), 20, LOTRBlocks.NIPHREDIL.get(), 20);
      LOTRBiomeFeatures.addTreeTorches(builder, 120, 60, 110, ((Block)LOTRBlocks.GOLD_MALLORN_WALL_TORCH.get()).func_176223_P(), ((Block)LOTRBlocks.BLUE_MALLORN_WALL_TORCH.get()).func_176223_P(), ((Block)LOTRBlocks.GREEN_MALLORN_WALL_TORCH.get()).func_176223_P(), ((Block)LOTRBlocks.SILVER_MALLORN_WALL_TORCH.get()).func_176223_P());
   }

   protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addWaterSprings(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder, 4);
      this.addDeer(builder, 2);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.GALADHRIM_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MALLORN_PLANKS.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MALLORN_FENCE.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.GOLD_MALLORN_TORCH.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.BLUE_MALLORN_TORCH.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.GREEN_MALLORN_TORCH.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.SILVER_MALLORN_TORCH.get()).func_176223_P(), 1));
   }

   public boolean hasBreakMallornResponse() {
      return true;
   }

   public static class Eaves extends LothlorienBiome {
      public Eaves(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.1F).func_205420_b(0.2F).func_205414_c(0.8F).func_205417_d(0.8F), major);
      }

      protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addTrees(this, builder, 2, 0.5F, LOTRBiomeFeatures.mallorn(), 500, LOTRBiomeFeatures.mallornBees(), 5, LOTRBiomeFeatures.mallornBoughs(), 100, LOTRBiomeFeatures.oak(), 3000, LOTRBiomeFeatures.oakFancy(), 500, LOTRBiomeFeatures.oakBees(), 30, LOTRBiomeFeatures.oakFancyBees(), 5, LOTRBiomeFeatures.larch(), 2000, LOTRBiomeFeatures.beech(), 1000, LOTRBiomeFeatures.beechFancy(), 200, LOTRBiomeFeatures.beechBees(), 10, LOTRBiomeFeatures.beechFancyBees(), 2, LOTRBiomeFeatures.aspen(), 1000, LOTRBiomeFeatures.aspenLarge(), 200);
         LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.STANDARD);
         LOTRBiomeFeatures.addDoubleGrass(builder, 3, GrassBlends.DOUBLE_STANDARD);
         LOTRBiomeFeatures.addForestFlowers(builder, 2, LOTRBlocks.ELANOR.get(), 10, LOTRBlocks.NIPHREDIL.get(), 10);
      }
   }
}
