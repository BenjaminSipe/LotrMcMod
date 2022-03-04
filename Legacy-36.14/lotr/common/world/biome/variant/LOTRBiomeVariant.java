package lotr.common.world.biome.variant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.commons.lang3.ArrayUtils;

public class LOTRBiomeVariant {
   private static LOTRBiomeVariant[] allVariants = new LOTRBiomeVariant[256];
   public static LOTRBiomeVariant STANDARD;
   public static LOTRBiomeVariant FLOWERS;
   public static LOTRBiomeVariant FOREST;
   public static LOTRBiomeVariant FOREST_LIGHT;
   public static LOTRBiomeVariant STEPPE;
   public static LOTRBiomeVariant STEPPE_BARREN;
   public static LOTRBiomeVariant HILLS;
   public static LOTRBiomeVariant HILLS_FOREST;
   public static LOTRBiomeVariant MOUNTAIN;
   public static LOTRBiomeVariant CLEARING;
   public static LOTRBiomeVariant DENSEFOREST_OAK;
   public static LOTRBiomeVariant DENSEFOREST_SPRUCE;
   public static LOTRBiomeVariant DENSEFOREST_OAK_SPRUCE;
   public static LOTRBiomeVariant DEADFOREST_OAK;
   public static LOTRBiomeVariant DEADFOREST_SPRUCE;
   public static LOTRBiomeVariant DEADFOREST_OAK_SPRUCE;
   public static LOTRBiomeVariant SHRUBLAND_OAK;
   public static LOTRBiomeVariant DENSEFOREST_BIRCH;
   public static LOTRBiomeVariant SWAMP_LOWLAND;
   public static LOTRBiomeVariant SWAMP_UPLAND;
   public static LOTRBiomeVariant SAVANNAH_BAOBAB;
   public static LOTRBiomeVariant LAKE;
   public static LOTRBiomeVariant DENSEFOREST_LEBETHRON;
   public static LOTRBiomeVariant BOULDERS_RED;
   public static LOTRBiomeVariant BOULDERS_ROHAN;
   public static LOTRBiomeVariant JUNGLE_DENSE;
   public static LOTRBiomeVariant VINEYARD;
   public static LOTRBiomeVariant FOREST_ASPEN;
   public static LOTRBiomeVariant FOREST_BIRCH;
   public static LOTRBiomeVariant FOREST_BEECH;
   public static LOTRBiomeVariant FOREST_MAPLE;
   public static LOTRBiomeVariant FOREST_LARCH;
   public static LOTRBiomeVariant FOREST_PINE;
   public static LOTRBiomeVariant ORCHARD_SHIRE;
   public static LOTRBiomeVariant ORCHARD_APPLE_PEAR;
   public static LOTRBiomeVariant ORCHARD_ORANGE;
   public static LOTRBiomeVariant ORCHARD_LEMON;
   public static LOTRBiomeVariant ORCHARD_LIME;
   public static LOTRBiomeVariant ORCHARD_ALMOND;
   public static LOTRBiomeVariant ORCHARD_OLIVE;
   public static LOTRBiomeVariant ORCHARD_PLUM;
   public static LOTRBiomeVariant RIVER;
   public static LOTRBiomeVariant SCRUBLAND;
   public static LOTRBiomeVariant HILLS_SCRUBLAND;
   public static LOTRBiomeVariant WASTELAND;
   public static LOTRBiomeVariant ORCHARD_DATE;
   public static LOTRBiomeVariant DENSEFOREST_DARK_OAK;
   public static LOTRBiomeVariant ORCHARD_POMEGRANATE;
   public static LOTRBiomeVariant DUNES;
   public static LOTRBiomeVariant SCRUBLAND_SAND;
   public static LOTRBiomeVariant HILLS_SCRUBLAND_SAND;
   public static LOTRBiomeVariant WASTELAND_SAND;
   public static LOTRBiomeVariant[] SET_NORMAL;
   public static LOTRBiomeVariant[] SET_NORMAL_OAK;
   public static LOTRBiomeVariant[] SET_NORMAL_SPRUCE;
   public static LOTRBiomeVariant[] SET_NORMAL_OAK_SPRUCE;
   public static LOTRBiomeVariant[] SET_NORMAL_NOSTEPPE;
   public static LOTRBiomeVariant[] SET_NORMAL_OAK_NOSTEPPE;
   public static LOTRBiomeVariant[] SET_FOREST;
   public static LOTRBiomeVariant[] SET_MOUNTAINS;
   public static LOTRBiomeVariant[] SET_SWAMP;
   public static NoiseGeneratorPerlin marshNoise;
   public static NoiseGeneratorPerlin podzolNoise;
   public final int variantID;
   public final String variantName;
   public final LOTRBiomeVariant.VariantScale variantScale;
   public float tempBoost = 0.0F;
   public float rainBoost = 0.0F;
   public boolean absoluteHeight = false;
   public float absoluteHeightLevel = 0.0F;
   private float heightBoost = 0.0F;
   public float hillFactor = 1.0F;
   public float treeFactor = 1.0F;
   public float grassFactor = 1.0F;
   public float flowerFactor = 1.0F;
   public boolean hasMarsh = false;
   public boolean disableStructures = false;
   public boolean disableVillages = false;
   public List treeTypes = new ArrayList();
   public float variantTreeChance = 0.0F;
   public WorldGenerator boulderGen;
   public int boulderChance = 0;
   public int boulderMax = 1;

   public LOTRBiomeVariant(int i, String s, LOTRBiomeVariant.VariantScale scale) {
      if (allVariants[i] != null) {
         throw new IllegalArgumentException("LOTR Biome variant already exists at index " + i);
      } else {
         this.variantID = i;
         allVariants[i] = this;
         this.variantName = s;
         this.variantScale = scale;
      }
   }

   public static LOTRBiomeVariant getVariantForID(int i) {
      LOTRBiomeVariant variant = allVariants[i];
      return variant == null ? STANDARD : variant;
   }

   protected LOTRBiomeVariant setTemperatureRainfall(float temp, float rain) {
      this.tempBoost = temp;
      this.rainBoost = rain;
      return this;
   }

   protected LOTRBiomeVariant setHeight(float height, float hills) {
      this.heightBoost = height;
      this.hillFactor = hills;
      return this;
   }

   protected LOTRBiomeVariant setAbsoluteHeight(float height, float hills) {
      this.absoluteHeight = true;
      this.absoluteHeightLevel = height;
      float f = height - 2.0F;
      f += 0.2F;
      this.heightBoost = f;
      this.hillFactor = hills;
      return this;
   }

   public float getHeightBoostAt(int i, int k) {
      return this.heightBoost;
   }

   protected LOTRBiomeVariant setTrees(float f) {
      this.treeFactor = f;
      return this;
   }

   protected LOTRBiomeVariant setGrass(float f) {
      this.grassFactor = f;
      return this;
   }

   protected LOTRBiomeVariant setFlowers(float f) {
      this.flowerFactor = f;
      return this;
   }

   protected LOTRBiomeVariant addTreeTypes(float f, Object... trees) {
      this.variantTreeChance = f;

      for(int i = 0; i < trees.length / 2; ++i) {
         Object obj1 = trees[i * 2];
         Object obj2 = trees[i * 2 + 1];
         this.treeTypes.add(new LOTRTreeType.WeightedTreeType((LOTRTreeType)obj1, (Integer)obj2));
      }

      return this;
   }

   public LOTRTreeType getRandomTree(Random random) {
      Item item = WeightedRandom.func_76271_a(random, this.treeTypes);
      return ((LOTRTreeType.WeightedTreeType)item).treeType;
   }

   protected LOTRBiomeVariant setMarsh() {
      this.hasMarsh = true;
      return this;
   }

   protected LOTRBiomeVariant disableVillages() {
      this.disableVillages = true;
      return this;
   }

   protected LOTRBiomeVariant disableStructuresVillages() {
      this.disableStructures = true;
      this.disableVillages = true;
      return this;
   }

   protected LOTRBiomeVariant setBoulders(WorldGenerator boulder, int chance, int num) {
      if (num < 1) {
         throw new IllegalArgumentException("n must be > 1");
      } else {
         this.boulderGen = boulder;
         this.boulderChance = chance;
         this.boulderMax = num;
         return this;
      }
   }

   public void generateVariantTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int height, LOTRBiome biome) {
   }

   public void decorateVariant(World world, Random random, int i, int k, LOTRBiome biome) {
   }

   static {
      STANDARD = new LOTRBiomeVariant(0, "standard", LOTRBiomeVariant.VariantScale.ALL);
      FLOWERS = (new LOTRBiomeVariant(1, "flowers", LOTRBiomeVariant.VariantScale.SMALL)).setFlowers(10.0F);
      FOREST = new LOTRBiomeVariantForest(2, "forest");
      FOREST_LIGHT = (new LOTRBiomeVariant(3, "forest_light", LOTRBiomeVariant.VariantScale.ALL)).setTemperatureRainfall(0.0F, 0.2F).setTrees(3.0F).setGrass(2.0F);
      STEPPE = (new LOTRBiomeVariant(4, "steppe", LOTRBiomeVariant.VariantScale.LARGE)).setTemperatureRainfall(0.0F, -0.1F).setHeight(0.0F, 0.1F).setTrees(0.01F).setGrass(3.0F).setFlowers(0.8F);
      STEPPE_BARREN = (new LOTRBiomeVariant(5, "steppe_barren", LOTRBiomeVariant.VariantScale.LARGE)).setTemperatureRainfall(0.1F, -0.2F).setHeight(0.0F, 0.1F).setTrees(0.01F).setGrass(0.2F).setFlowers(0.4F);
      HILLS = (new LOTRBiomeVariant(6, "hills", LOTRBiomeVariant.VariantScale.ALL)).setTemperatureRainfall(-0.1F, -0.1F).setHeight(0.5F, 1.5F).setGrass(0.5F);
      HILLS_FOREST = (new LOTRBiomeVariant(7, "hills_forest", LOTRBiomeVariant.VariantScale.ALL)).setTemperatureRainfall(-0.1F, 0.0F).setHeight(0.5F, 1.5F).setTrees(3.0F);
      MOUNTAIN = (new LOTRBiomeVariant(8, "mountain", LOTRBiomeVariant.VariantScale.ALL)).setTemperatureRainfall(-0.1F, -0.2F).setHeight(1.2F, 3.0F).setFlowers(0.8F);
      CLEARING = (new LOTRBiomeVariant(9, "clearing", LOTRBiomeVariant.VariantScale.SMALL)).setHeight(0.0F, 0.5F).setTrees(0.0F).setGrass(2.0F).setFlowers(3.0F);
      DENSEFOREST_OAK = (new LOTRBiomeVariantDenseForest(10, "denseForest_oak")).addTreeTypes(0.5F, new Object[]{LOTRTreeType.OAK_LARGE, 600, LOTRTreeType.OAK_PARTY, 100});
      DENSEFOREST_SPRUCE = (new LOTRBiomeVariantDenseForest(11, "denseForest_spruce")).addTreeTypes(0.5F, new Object[]{LOTRTreeType.SPRUCE_MEGA, 100});
      DENSEFOREST_OAK_SPRUCE = (new LOTRBiomeVariantDenseForest(12, "denseForest_oak_spruce")).addTreeTypes(0.5F, new Object[]{LOTRTreeType.OAK_LARGE, 600, LOTRTreeType.OAK_PARTY, 200, LOTRTreeType.SPRUCE_MEGA, 200});
      DEADFOREST_OAK = (new LOTRBiomeVariantDeadForest(13, "deadForest_oak")).addTreeTypes(0.5F, new Object[]{LOTRTreeType.OAK_DEAD, 100});
      DEADFOREST_SPRUCE = (new LOTRBiomeVariantDeadForest(14, "deadForest_spruce")).addTreeTypes(0.5F, new Object[]{LOTRTreeType.SPRUCE_DEAD, 100});
      DEADFOREST_OAK_SPRUCE = (new LOTRBiomeVariantDeadForest(15, "deadForest_oak_spruce")).addTreeTypes(0.5F, new Object[]{LOTRTreeType.OAK_DEAD, 100, LOTRTreeType.SPRUCE_DEAD, 100});
      SHRUBLAND_OAK = (new LOTRBiomeVariant(16, "shrubland_oak", LOTRBiomeVariant.VariantScale.ALL)).setTemperatureRainfall(0.0F, 0.3F).setTrees(6.0F).addTreeTypes(0.7F, LOTRTreeType.OAK_SHRUB, 100);
      DENSEFOREST_BIRCH = (new LOTRBiomeVariantDenseForest(17, "denseForest_birch")).addTreeTypes(0.5F, new Object[]{LOTRTreeType.BIRCH_LARGE, 600, LOTRTreeType.BIRCH_PARTY, 100});
      SWAMP_LOWLAND = (new LOTRBiomeVariant(18, "swampLowland", LOTRBiomeVariant.VariantScale.SMALL)).setHeight(-0.12F, 0.2F).setTrees(0.5F).setGrass(5.0F).setMarsh();
      SWAMP_UPLAND = (new LOTRBiomeVariant(19, "swampUpland", LOTRBiomeVariant.VariantScale.SMALL)).setHeight(0.12F, 1.0F).setTrees(6.0F).setGrass(5.0F);
      SAVANNAH_BAOBAB = (new LOTRBiomeVariant(20, "savannahBaobab", LOTRBiomeVariant.VariantScale.LARGE)).setHeight(0.0F, 0.5F).setTemperatureRainfall(0.0F, 0.2F).setTrees(1.5F).setGrass(0.5F).addTreeTypes(0.6F, LOTRTreeType.BAOBAB, 100);
      LAKE = (new LOTRBiomeVariant(21, "lake", LOTRBiomeVariant.VariantScale.NONE)).setAbsoluteHeight(-0.5F, 0.05F);
      DENSEFOREST_LEBETHRON = (new LOTRBiomeVariantDenseForest(22, "denseForest_lebethron")).addTreeTypes(0.5F, new Object[]{LOTRTreeType.LEBETHRON_LARGE, 600, LOTRTreeType.LEBETHRON_PARTY, 100});
      BOULDERS_RED = (new LOTRBiomeVariant(23, "boulders_red", LOTRBiomeVariant.VariantScale.LARGE)).setBoulders(new LOTRWorldGenBoulder(LOTRMod.redSandstone, 1, 1, 3), 2, 4);
      BOULDERS_ROHAN = (new LOTRBiomeVariant(24, "boulders_rohan", LOTRBiomeVariant.VariantScale.LARGE)).setBoulders(new LOTRWorldGenBoulder(LOTRMod.rock, 2, 1, 3), 2, 4);
      JUNGLE_DENSE = (new LOTRBiomeVariant(25, "jungle_dense", LOTRBiomeVariant.VariantScale.LARGE)).setTemperatureRainfall(0.1F, 0.1F).setTrees(2.0F).addTreeTypes(0.6F, LOTRTreeType.JUNGLE_FANGORN, 1000, LOTRTreeType.MAHOGANY_FANGORN, 500);
      VINEYARD = (new LOTRBiomeVariant(26, "vineyard", LOTRBiomeVariant.VariantScale.SMALL)).setHeight(0.0F, 0.5F).setTrees(0.0F).setGrass(0.5F).setFlowers(0.0F).disableStructuresVillages();
      FOREST_ASPEN = (new LOTRBiomeVariantForest(27, "forest_aspen")).addTreeTypes(0.8F, new Object[]{LOTRTreeType.ASPEN, 1000, LOTRTreeType.ASPEN_LARGE, 50});
      FOREST_BIRCH = (new LOTRBiomeVariantForest(28, "forest_birch")).addTreeTypes(0.8F, new Object[]{LOTRTreeType.BIRCH, 1000, LOTRTreeType.BIRCH_LARGE, 150});
      FOREST_BEECH = (new LOTRBiomeVariantForest(29, "forest_beech")).addTreeTypes(0.8F, new Object[]{LOTRTreeType.BEECH, 1000, LOTRTreeType.BEECH_LARGE, 150});
      FOREST_MAPLE = (new LOTRBiomeVariantForest(30, "forest_maple")).addTreeTypes(0.8F, new Object[]{LOTRTreeType.MAPLE, 1000, LOTRTreeType.MAPLE_LARGE, 150});
      FOREST_LARCH = (new LOTRBiomeVariantForest(31, "forest_larch")).addTreeTypes(0.8F, new Object[]{LOTRTreeType.LARCH, 1000});
      FOREST_PINE = (new LOTRBiomeVariantForest(32, "forest_pine")).addTreeTypes(0.8F, new Object[]{LOTRTreeType.PINE, 1000});
      ORCHARD_SHIRE = (new LOTRBiomeVariantOrchard(33, "orchard_shire")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.APPLE, 100, LOTRTreeType.PEAR, 100, LOTRTreeType.CHERRY, 10});
      ORCHARD_APPLE_PEAR = (new LOTRBiomeVariantOrchard(34, "orchard_apple_pear")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.APPLE, 100, LOTRTreeType.PEAR, 100});
      ORCHARD_ORANGE = (new LOTRBiomeVariantOrchard(35, "orchard_orange")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.ORANGE, 100});
      ORCHARD_LEMON = (new LOTRBiomeVariantOrchard(36, "orchard_lemon")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.LEMON, 100});
      ORCHARD_LIME = (new LOTRBiomeVariantOrchard(37, "orchard_lime")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.LIME, 100});
      ORCHARD_ALMOND = (new LOTRBiomeVariantOrchard(38, "orchard_almond")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.ALMOND, 100});
      ORCHARD_OLIVE = (new LOTRBiomeVariantOrchard(39, "orchard_olive")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.OLIVE, 100});
      ORCHARD_PLUM = (new LOTRBiomeVariantOrchard(40, "orchard_plum")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.PLUM, 100});
      RIVER = (new LOTRBiomeVariant(41, "river", LOTRBiomeVariant.VariantScale.NONE)).setAbsoluteHeight(-0.5F, 0.05F).setTemperatureRainfall(0.0F, 0.3F);
      SCRUBLAND = (new LOTRBiomeVariantScrubland(42, "scrubland", Blocks.field_150348_b)).setHeight(0.0F, 0.8F);
      HILLS_SCRUBLAND = (new LOTRBiomeVariantScrubland(43, "hills_scrubland", Blocks.field_150348_b)).setHeight(0.5F, 2.0F);
      WASTELAND = (new LOTRBiomeVariantWasteland(44, "wasteland", Blocks.field_150348_b)).setHeight(0.0F, 0.5F);
      ORCHARD_DATE = (new LOTRBiomeVariantOrchard(45, "orchard_date")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.DATE_PALM, 100});
      DENSEFOREST_DARK_OAK = (new LOTRBiomeVariantDenseForest(46, "denseForest_darkOak")).addTreeTypes(0.5F, new Object[]{LOTRTreeType.DARK_OAK, 600, LOTRTreeType.DARK_OAK_PARTY, 100});
      ORCHARD_POMEGRANATE = (new LOTRBiomeVariantOrchard(47, "orchard_pomegranate")).addTreeTypes(1.0F, new Object[]{LOTRTreeType.POMEGRANATE, 100});
      DUNES = new LOTRBiomeVariantDunes(48, "dunes");
      SCRUBLAND_SAND = (new LOTRBiomeVariantScrubland(49, "scrubland_sand", Blocks.field_150322_A)).setHeight(0.0F, 0.8F);
      HILLS_SCRUBLAND_SAND = (new LOTRBiomeVariantScrubland(50, "hills_scrubland_sand", Blocks.field_150322_A)).setHeight(0.5F, 2.0F);
      WASTELAND_SAND = (new LOTRBiomeVariantWasteland(51, "wasteland_sand", Blocks.field_150322_A)).setHeight(0.0F, 0.5F);
      SET_NORMAL = new LOTRBiomeVariant[]{FLOWERS, FOREST, FOREST_LIGHT, STEPPE, STEPPE_BARREN, HILLS, HILLS_FOREST};
      SET_NORMAL_OAK = (LOTRBiomeVariant[])ArrayUtils.addAll(SET_NORMAL, new LOTRBiomeVariant[]{DENSEFOREST_OAK, DEADFOREST_OAK, SHRUBLAND_OAK});
      SET_NORMAL_SPRUCE = (LOTRBiomeVariant[])ArrayUtils.addAll(SET_NORMAL, new LOTRBiomeVariant[]{DENSEFOREST_SPRUCE, DEADFOREST_SPRUCE});
      SET_NORMAL_OAK_SPRUCE = (LOTRBiomeVariant[])ArrayUtils.addAll(SET_NORMAL, new LOTRBiomeVariant[]{DENSEFOREST_OAK, DEADFOREST_OAK, SHRUBLAND_OAK, DENSEFOREST_SPRUCE, DEADFOREST_SPRUCE, DENSEFOREST_OAK_SPRUCE, DEADFOREST_OAK_SPRUCE});
      SET_NORMAL_NOSTEPPE = (LOTRBiomeVariant[])ArrayUtils.removeElements(SET_NORMAL, new LOTRBiomeVariant[]{STEPPE, STEPPE_BARREN});
      SET_NORMAL_OAK_NOSTEPPE = (LOTRBiomeVariant[])ArrayUtils.removeElements(SET_NORMAL_OAK, new LOTRBiomeVariant[]{STEPPE, STEPPE_BARREN});
      SET_FOREST = new LOTRBiomeVariant[]{FLOWERS, HILLS, CLEARING};
      SET_MOUNTAINS = new LOTRBiomeVariant[]{FOREST, FOREST_LIGHT};
      SET_SWAMP = new LOTRBiomeVariant[]{SWAMP_LOWLAND, SWAMP_LOWLAND, SWAMP_LOWLAND, SWAMP_UPLAND};
      marshNoise = new NoiseGeneratorPerlin(new Random(444L), 1);
      podzolNoise = new NoiseGeneratorPerlin(new Random(58052L), 1);
   }

   public static enum VariantScale {
      LARGE,
      SMALL,
      ALL,
      NONE;
   }
}
