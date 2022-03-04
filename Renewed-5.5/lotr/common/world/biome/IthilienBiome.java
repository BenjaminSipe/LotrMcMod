package lotr.common.world.biome;

import lotr.common.init.LOTRBlocks;
import lotr.common.util.LOTRUtil;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.grassblend.GrassBlends;
import lotr.common.world.map.RoadBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome.Builder;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.RainType;

public class IthilienBiome extends LOTRBiomeBase {
   public IthilienBiome(boolean major) {
      this((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.15F).func_205420_b(0.5F).func_205414_c(0.9F).func_205417_d(0.9F), major);
   }

   protected IthilienBiome(Builder builder, boolean major) {
      super(builder, major);
   }

   protected void setupSurface(MiddleEarthSurfaceConfig config) {
      config.addSubSoilLayer(((Block)LOTRBlocks.GONDOR_ROCK.get()).func_176223_P(), 8, 10);
   }

   protected void addStoneVariants(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addGranite(builder);
      LOTRBiomeFeatures.addDeepDiorite(builder);
      LOTRBiomeFeatures.addGondorRockPatches(builder);
   }

   protected void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addBoulders(builder, Blocks.field_150348_b.func_176223_P(), 1, 3, 40, 3);
   }

   protected Object[] getIthilienTrees() {
      return new Object[]{LOTRBiomeFeatures.oak(), 4000, LOTRBiomeFeatures.oakTall(), 1000, LOTRBiomeFeatures.oakFancy(), 1000, LOTRBiomeFeatures.oakBees(), 50, LOTRBiomeFeatures.oakTallBees(), 10, LOTRBiomeFeatures.oakFancyBees(), 20, LOTRBiomeFeatures.oakParty(), 50, LOTRBiomeFeatures.lebethron(), 1000, LOTRBiomeFeatures.lebethronFancy(), 500, LOTRBiomeFeatures.lebethronBees(), 10, LOTRBiomeFeatures.lebethronFancy(), 5, LOTRBiomeFeatures.lebethronParty(), 20, LOTRBiomeFeatures.birch(), 1500, LOTRBiomeFeatures.birchFancy(), 500, LOTRBiomeFeatures.birchBees(), 15, LOTRBiomeFeatures.birchFancyBees(), 5, LOTRBiomeFeatures.cedar(), 1500, LOTRBiomeFeatures.cypress(), 1800, LOTRBiomeFeatures.pine(), 500, LOTRBiomeFeatures.apple(), 50, LOTRBiomeFeatures.pear(), 50, LOTRBiomeFeatures.appleBees(), 5, LOTRBiomeFeatures.pearBees(), 5, LOTRBiomeFeatures.culumalda(), 40, LOTRBiomeFeatures.culumaldaBees(), 1};
   }

   protected float getIthilienTreesIncreaseChance() {
      return 0.7F;
   }

   protected void addVegetation(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addTreesWithClusters(this, builder, 0, this.getIthilienTreesIncreaseChance(), TreeCluster.of(50, 2), this.getIthilienTrees());
      LOTRBiomeFeatures.addGrass(this, builder, 10, GrassBlends.WITH_FERNS);
      LOTRBiomeFeatures.addDoubleGrass(builder, 4, GrassBlends.DOUBLE_WITH_FERNS);
      this.addIthilienFlowers(builder);
      LOTRBiomeFeatures.addDefaultDoubleFlowers(builder, 3);
      LOTRBiomeFeatures.addSunflowers(builder, 16);
      LOTRBiomeFeatures.addWaterLiliesWithFlowers(builder, 2);
      LOTRBiomeFeatures.addAthelasChance(builder);
      LOTRBiomeFeatures.addWildPipeweedChance(builder, 24);
      LOTRBiomeFeatures.addFoxBerryBushes(builder);
   }

   protected void addIthilienFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      LOTRBiomeFeatures.addForestFlowers(builder, 4, Blocks.field_196609_bf, 5, LOTRBlocks.ASPHODEL.get(), 10);
   }

   protected void addAnimals(net.minecraft.world.biome.MobSpawnInfo.Builder builder) {
      super.addAnimals(builder);
      this.addHorsesDonkeys(builder);
      this.addDeer(builder);
      this.addBears(builder);
      this.addFoxes(builder);
   }

   public RoadBlockProvider getRoadBlockProvider() {
      return RoadBlockProvider.GONDOR.withRepair(0.7F);
   }

   public static class Cormallen extends IthilienBiome {
      public Cormallen(boolean major) {
         super((new Builder()).func_205415_a(RainType.RAIN).func_205419_a(Category.FOREST).func_205421_a(0.12F).func_205420_b(0.2F).func_205414_c(0.9F).func_205417_d(0.9F), major);
         this.biomeColors.setFog(16777159);
      }

      protected Object[] getIthilienTrees() {
         return LOTRUtil.combineVarargs(super.getIthilienTrees(), LOTRBiomeFeatures.culumalda(), 12000, LOTRBiomeFeatures.culumaldaBees(), 240);
      }

      protected float getIthilienTreesIncreaseChance() {
         return 0.5F;
      }

      protected void addIthilienFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
         LOTRBiomeFeatures.addForestFlowers(builder, 4, Blocks.field_196609_bf, 5, LOTRBlocks.ASPHODEL.get(), 10, LOTRBlocks.MALLOS.get(), 20);
      }
   }
}
