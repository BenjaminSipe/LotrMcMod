package lotr.common.world.biome;

import lotr.common.block.DripstoneBlock;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.biome.surface.SurfaceNoiseMixer;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class ForochelBiome extends LOTRBiomeBase {
   public ForochelBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.SNOW).func_205419_a(Category.ICY).func_205421_a(0.1F).func_205420_b(0.2F).func_205414_c(0.1F).func_205417_d(0.3F), major);
      this.biomeColors.setSky(11783899);
   }

   protected void setupBiomeAmbience(net.minecraft.world.biome.BiomeAmbience.Builder builder) {
      super.setupBiomeAmbience(builder);
      builder.func_242537_a(LOTRGrassColorModifiers.FOROCHEL);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.setSurfaceNoiseMixer(SurfaceNoiseMixer.createNoiseMixer(SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(1).scales(0.3D, 0.06D).threshold(0.3D).state(Blocks.field_196660_k).topOnly(), SurfaceNoiseMixer.Condition.conditionBuilder().noiseIndex(2).scales(0.3D, 0.02D).threshold(0.35D).state(Blocks.field_196604_cC).topOnly()));
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
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 80, 1);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 80, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.04F, TreeCluster.of(8, 100), LOTRBiomeFeatures.spruce(), 600, LOTRBiomeFeatures.spruceThin(), 400, LOTRBiomeFeatures.spruceDead(), 3000, LOTRBiomeFeatures.pine(), 200, LOTRBiomeFeatures.pineDead(), 600, LOTRBiomeFeatures.fir(), 1000);
      LOTRBiomeFeatures.addGrass(this, builder, 1, GrassBlends.MUTED);
      LOTRBiomeFeatures.addBorealFlowers(builder, 2);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addWolves(builder, 1);
      this.addDeer(builder, 1);
      this.addElk(builder, 2);
      this.addBears(builder, 2);
      this.addFoxes(builder, 2);
      builder.func_242575_a(EntityClassification.CREATURE, new Spawners(EntityType.field_200786_Z, 1, 1, 2));
   }

   public boolean doesSnowGenerate(boolean defaultDoesSnowGenerate, IWorldReader world, BlockPos pos) {
      return defaultDoesSnowGenerate && (LOTRBiomeWrapper.isSnowBlockBelow(world, pos) || this.isForochelSnowy(pos));
   }

   private boolean isForochelSnowy(BlockPos pos) {
      int x = pos.func_177958_n();
      int z = pos.func_177952_p();
      double d1 = SNOW_VARIETY_NOISE.func_215464_a((double)x * 0.002D, (double)z * 0.002D, false);
      double d2 = SNOW_VARIETY_NOISE.func_215464_a((double)x * 0.05D, (double)z * 0.05D, false);
      double d3 = SNOW_VARIETY_NOISE.func_215464_a((double)x * 0.3D, (double)z * 0.3D, false);
      d2 *= 0.3D;
      d3 *= 0.3D;
      return d1 + d2 + d3 > 0.62D;
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.LOSSOTH_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.SNOW_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.LARCH_FENCE.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.BLUBBER_TORCH.get()).func_176223_P(), 1));
   }
}
