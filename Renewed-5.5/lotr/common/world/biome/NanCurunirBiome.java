package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class NanCurunirBiome extends LOTRBiomeBase {
   public NanCurunirBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.PLAINS).func_205421_a(0.2F).func_205420_b(0.1F).func_205414_c(0.6F).func_205417_d(0.4F), major);
      this.biomeColors.setSky(8683640);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.4D, 0.09D).threshold(0.15D).state(Blocks.field_196660_k).topOnly()));
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 40, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.05F, LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakTall(), 1000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakDead(), 2000, LOTRBiomeFeatures.spruce(), 1000, LOTRBiomeFeatures.spruceDead(), 1000, LOTRBiomeFeatures.charred(), 3000);
      LOTRBiomeFeatures.addGrass(this, builder, 4, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addPlainsFlowers(builder, 1);
      LOTRBiomeFeatures.addFallenLogs(builder, 1);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.URUK_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.URUK_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150463_bK.func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ORC_TORCH.get()).func_176223_P(), 1));
   }
}
