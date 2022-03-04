package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.util.LOTRUtil;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class EttenmoorsBiome extends LOTRBiomeBase {
   public EttenmoorsBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(0.5F).func_205420_b(0.7F).func_205414_c(0.2F).func_205417_d(0.6F), major);
      this.biomeColors.setGrass(11910259);
      this.biomeColors.setSky(12965352);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCommonGranite(builder);
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 2, 3, 2);
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 2, 5, 12, 3);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.fir(), 400, LOTRBiomeFeatures.pine(), 800, LOTRBiomeFeatures.pineDead(), 100, LOTRBiomeFeatures.spruce(), 400, LOTRBiomeFeatures.spruceThin(), 400, LOTRBiomeFeatures.spruceDead(), 100};
      Object[] weightedTreesWithDead = LOTRUtil.combineVarargs(weightedTrees, LOTRBiomeFeatures.spruceDead(), 3000);
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.05F, weightedTreesWithDead);
      LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 2, 0.5F, 87, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 4, GrassBlends.MOORS);
      LOTRBiomeFeatures.addBorealFlowers(builder, 1);
      LOTRBiomeFeatures.addAthelasChance(builder);
      LOTRBiomeFeatures.addTundraBushesChance(builder, 3, (new WeightedBlockStateProvider()).func_227407_a_((BlockState)Blocks.field_196645_X.func_176223_P().func_206870_a(LeavesBlock.field_208495_b, true), 3).func_227407_a_((BlockState)((Block)LOTRBlocks.FIR_LEAVES.get()).func_176223_P().func_206870_a(LeavesBlock.field_208495_b, true), 3).func_227407_a_((BlockState)((Block)LOTRBlocks.PINE_LEAVES.get()).func_176223_P().func_206870_a(LeavesBlock.field_208495_b, true), 3), 40);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      this.addWolves(builder, 2);
      this.addElk(builder, 1);
      this.addBears(builder, 1);
   }
}
