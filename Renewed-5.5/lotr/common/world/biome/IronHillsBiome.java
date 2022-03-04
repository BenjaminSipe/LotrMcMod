package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class IronHillsBiome extends LOTRBiomeBase {
   public IronHillsBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.EXTREME_HILLS).func_205421_a(0.3F).func_205420_b(1.4F).func_205414_c(0.27F).func_205417_d(0.4F), major);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      super.addOres(builder);
      LOTRBiomeFeatures.addExtraIron(builder, 4, 20, 96);
      LOTRBiomeFeatures.addExtraGold(builder, 4, 2, 48);
      LOTRBiomeFeatures.addExtraSilver(builder, 4, 2, 48);
      LOTRBiomeFeatures.addGlowstoneOre(builder);
   }

   protected void addCobwebs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.spruceMega(), 2000, LOTRBiomeFeatures.spruceThinMega(), 500, LOTRBiomeFeatures.spruceDead(), 400, LOTRBiomeFeatures.pine(), 4000, LOTRBiomeFeatures.pineDead(), 200, LOTRBiomeFeatures.fir(), 4000};
      LOTRBiomeFeatures.addTrees(this, builder, 0, 0.1F, weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 8, 0.1F, 80, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 8, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addMountainsFlowers(builder, 1, LOTRBlocks.DWARFWORT.get(), 1);
   }

   protected void addLiquidSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addWaterLavaSpringsReducedAboveground(builder, 80, 0.15F);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addWolves(builder, 1);
      this.addBears(builder, 2);
      this.addBoars(builder, 3);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.DWARVEN_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DWARVEN_BRICK.get()).func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_180408_aP.func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_222432_lU.func_176223_P(), 1));
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.DWARVEN;
   }
}
