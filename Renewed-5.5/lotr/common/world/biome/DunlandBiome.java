package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;

public class DunlandBiome extends LOTRBiomeBase {
   public DunlandBiome(boolean major) {
      super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.3F).func_205420_b(0.5F).func_205414_c(0.4F).func_205417_d(0.7F), major);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 8, 4);
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Object[] weightedTrees = new Object[]{LOTRBiomeFeatures.spruce(), 5000, LOTRBiomeFeatures.oak(), 1000, LOTRBiomeFeatures.oakTall(), 1000, LOTRBiomeFeatures.oakFancy(), 200, LOTRBiomeFeatures.oakBees(), 10, LOTRBiomeFeatures.oakTallBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 2, LOTRBiomeFeatures.pine(), 5000, LOTRBiomeFeatures.pineDead(), 50, LOTRBiomeFeatures.fir(), 5000};
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, 0.4F, TreeCluster.of(12, 10), weightedTrees);
      LOTRBiomeFeatures.addTreesAboveTreeline(this, builder, 6, 0.1F, 85, weightedTrees);
      LOTRBiomeFeatures.addGrass(this, builder, 6, GrassBlends.STANDARD);
      LOTRBiomeFeatures.addDoubleGrass(builder, 1, GrassBlends.DOUBLE_STANDARD);
      LOTRBiomeFeatures.addForestFlowers(builder, 2);
      LOTRBiomeFeatures.addFoxBerryBushes(builder);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addWolves(builder, 2);
      this.addBears(builder, 1);
      this.addFoxes(builder, 2);
   }

   protected void addStructures(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addCraftingMonument(builder, ((Block)LOTRBlocks.DUNLENDING_CRAFTING_TABLE.get()).func_176223_P(), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150347_e.func_176223_P(), 2).func_227407_a_(Blocks.field_196700_dk.func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_180408_aP.func_176223_P(), 1), (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150478_aa.func_176223_P(), 1));
   }
}
