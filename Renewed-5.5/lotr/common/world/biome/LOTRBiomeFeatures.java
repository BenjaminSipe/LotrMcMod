package lotr.common.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import lotr.common.block.DripstoneBlock;
import lotr.common.compatibility.SnowRealMagicCompatibility;
import lotr.common.init.LOTRBlocks;
import lotr.common.util.LOTRUtil;
import lotr.common.world.gen.carver.LOTRWorldCarvers;
import lotr.common.world.gen.feature.BoulderFeatureConfig;
import lotr.common.world.gen.feature.CobwebFeatureConfig;
import lotr.common.world.gen.feature.CraftingMonumentFeatureConfig;
import lotr.common.world.gen.feature.CrystalFeatureConfig;
import lotr.common.world.gen.feature.DripstoneFeatureConfig;
import lotr.common.world.gen.feature.FallenLogFeatureConfig;
import lotr.common.world.gen.feature.GrassPatchFeatureConfig;
import lotr.common.world.gen.feature.LOTRFeatures;
import lotr.common.world.gen.feature.LatitudeBasedFeatureConfig;
import lotr.common.world.gen.feature.MordorBasaltFeatureConfig;
import lotr.common.world.gen.feature.MordorMossFeatureConfig;
import lotr.common.world.gen.feature.ReedsFeatureConfig;
import lotr.common.world.gen.feature.TerrainSharpenFeatureConfig;
import lotr.common.world.gen.feature.TreeCluster;
import lotr.common.world.gen.feature.WeightedFeature;
import lotr.common.world.gen.feature.WeightedRandomFeatureConfig;
import lotr.common.world.gen.feature.WrappedTreeFeatureConfig;
import lotr.common.world.gen.feature.grassblend.DoubleGrassBlend;
import lotr.common.world.gen.feature.grassblend.SingleGrassBlend;
import lotr.common.world.gen.placement.ByWaterConfig;
import lotr.common.world.gen.placement.TreeClustersConfig;
import lotr.common.world.gen.tree.AspenFoliagePlacer;
import lotr.common.world.gen.tree.BoughsFoliagePlacer;
import lotr.common.world.gen.tree.BoughsTrunkPlacer;
import lotr.common.world.gen.tree.CedarFoliagePlacer;
import lotr.common.world.gen.tree.CedarTrunkPlacer;
import lotr.common.world.gen.tree.ClusterFoliagePlacer;
import lotr.common.world.gen.tree.CulumaldaFoliagePlacer;
import lotr.common.world.gen.tree.CypressFoliagePlacer;
import lotr.common.world.gen.tree.DeadTrunkPlacer;
import lotr.common.world.gen.tree.DesertFoliagePlacer;
import lotr.common.world.gen.tree.DesertTrunkPlacer;
import lotr.common.world.gen.tree.EmptyFoliagePlacer;
import lotr.common.world.gen.tree.FangornTrunkPlacer;
import lotr.common.world.gen.tree.FirFoliagePlacer;
import lotr.common.world.gen.tree.HollyFoliagePlacer;
import lotr.common.world.gen.tree.LOTRFoliagePlacers;
import lotr.common.world.gen.tree.LOTRPineFoliagePlacer;
import lotr.common.world.gen.tree.LOTRTreeDecorators;
import lotr.common.world.gen.tree.LOTRTrunkPlacers;
import lotr.common.world.gen.tree.LairelosseFoliagePlacer;
import lotr.common.world.gen.tree.MirkOakFoliagePlacer;
import lotr.common.world.gen.tree.MirkOakLeavesGrowthDecorator;
import lotr.common.world.gen.tree.MirkOakTrunkPlacer;
import lotr.common.world.gen.tree.MirkOakWebsDecorator;
import lotr.common.world.gen.tree.PartyTrunkPlacer;
import lotr.common.world.gen.tree.PineBranchDecorator;
import lotr.common.world.gen.tree.PineStripDecorator;
import lotr.common.world.gen.tree.ShirePineFoliagePlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.gen.GenerationStage.Carving;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SingleRandomFeature;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig.Builder;
import net.minecraft.world.gen.feature.Features.Placements;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.ForgeRegistries;

public class LOTRBiomeFeatures {
   public static RuleTest MORDOR_ROCK_FILLER;
   public static RuleTest SAND_FILLER;
   private static BlockState STONE;
   private static BlockState DIRT;
   private static BlockState COARSE_DIRT;
   private static BlockState GRAVEL;
   private static BlockState GRANITE;
   private static BlockState DIORITE;
   private static BlockState ANDESITE;
   private static BlockState PACKED_ICE;
   private static BlockState BLUE_ICE;
   private static BlockState GONDOR_ROCK;
   private static BlockState MORDOR_ROCK;
   private static BlockState ROHAN_ROCK;
   private static BlockState BLUE_ROCK;
   private static BlockState RED_ROCK;
   private static BlockState MORDOR_DIRT;
   private static BlockState MORDOR_GRAVEL;
   private static BlockState COAL_ORE;
   private static BlockState IRON_ORE;
   private static BlockState COPPER_ORE;
   private static BlockState TIN_ORE;
   private static BlockState GOLD_ORE;
   private static BlockState SILVER_ORE;
   private static BlockState SULFUR_ORE;
   private static BlockState NITER_ORE;
   private static BlockState SALT_ORE;
   private static BlockState LAPIS_ORE;
   private static BlockState MITHRIL_ORE;
   private static BlockState EDHELVIR_ORE;
   private static BlockState GLOWSTONE_ORE;
   private static BlockState DURNOR_ORE;
   private static BlockState MORGUL_IRON_ORE_MORDOR;
   private static BlockState MORGUL_IRON_ORE_STONE;
   private static BlockState GULDURIL_ORE_MORDOR;
   private static BlockState GULDURIL_ORE_STONE;
   private static BlockState EDHELVIR_CRYSTAL;
   private static BlockState GULDURIL_CRYSTAL;
   private static BlockState GLOWSTONE_CRYSTAL;
   private static BlockState COBWEB;
   private static BlockState WATER;
   private static BlockState LAVA;
   private static BlockState OAK_LOG;
   private static BlockState OAK_WOOD;
   private static BlockState OAK_STRIPPED_LOG;
   private static BlockState OAK_BRANCH;
   private static BlockState OAK_LEAVES;
   private static IPlantable OAK_SAPLING;
   public static BaseTreeFeatureConfig OAK_TREE_VINES;
   public static BaseTreeFeatureConfig OAK_TREE_BEES_VINES;
   public static BaseTreeFeatureConfig OAK_TREE_TALL;
   public static BaseTreeFeatureConfig OAK_TREE_TALL_BEES;
   public static BaseTreeFeatureConfig OAK_TREE_TALL_VINES;
   public static BaseTreeFeatureConfig OAK_TREE_TALL_BEES_VINES;
   public static WrappedTreeFeatureConfig OAK_DESERT;
   public static WrappedTreeFeatureConfig OAK_DESERT_BEES;
   public static WrappedTreeFeatureConfig OAK_DEAD;
   public static BaseTreeFeatureConfig OAK_PARTY;
   public static BaseTreeFeatureConfig OAK_FANGORN;
   public static BaseTreeFeatureConfig OAK_SHRUB;
   private static BlockState SPRUCE_LOG;
   private static BlockState SPRUCE_WOOD;
   private static BlockState SPRUCE_BRANCH;
   private static BlockState SPRUCE_LEAVES;
   private static IPlantable SPRUCE_SAPLING;
   public static WrappedTreeFeatureConfig SPRUCE_DEAD;
   public static BaseTreeFeatureConfig SPRUCE_SHRUB;
   private static BlockState BIRCH_LOG;
   private static BlockState BIRCH_WOOD;
   private static BlockState BIRCH_BRANCH;
   private static BlockState BIRCH_LEAVES;
   private static IPlantable BIRCH_SAPLING;
   public static BaseTreeFeatureConfig BIRCH_TREE_FANCY;
   public static BaseTreeFeatureConfig BIRCH_TREE_FANCY_BEES;
   public static BaseTreeFeatureConfig BIRCH_TREE_ALT;
   public static BaseTreeFeatureConfig BIRCH_TREE_ALT_BEES;
   public static WrappedTreeFeatureConfig BIRCH_DEAD;
   public static BaseTreeFeatureConfig BIRCH_PARTY;
   private static BlockState DARK_OAK_LOG;
   private static BlockState DARK_OAK_WOOD;
   private static BlockState DARK_OAK_BRANCH;
   private static BlockState DARK_OAK_LEAVES;
   private static IPlantable DARK_OAK_SAPLING;
   public static BaseTreeFeatureConfig DARK_OAK_PARTY;
   public static BaseTreeFeatureConfig DARK_OAK_SHRUB;
   private static BlockState PINE_LOG;
   private static BlockState PINE_LOG_SLAB;
   private static BlockState PINE_LEAVES;
   private static IPlantable PINE_SAPLING;
   public static BaseTreeFeatureConfig PINE_TREE;
   public static BaseTreeFeatureConfig SHIRE_PINE_TREE;
   public static BaseTreeFeatureConfig PINE_DEAD;
   public static BaseTreeFeatureConfig PINE_SHRUB;
   private static BlockState MALLORN_LOG;
   private static BlockState MALLORN_WOOD;
   private static BlockState MALLORN_BRANCH;
   private static BlockState MALLORN_LEAVES;
   private static IPlantable MALLORN_SAPLING;
   public static BaseTreeFeatureConfig MALLORN_TREE;
   public static BaseTreeFeatureConfig MALLORN_TREE_BEES;
   public static BaseTreeFeatureConfig MALLORN_TREE_BOUGHS;
   public static BaseTreeFeatureConfig MALLORN_PARTY;
   private static BlockState MIRK_OAK_LOG;
   private static BlockState MIRK_OAK_WOOD;
   private static BlockState MIRK_OAK_BRANCH;
   private static BlockState MIRK_OAK_LEAVES;
   private static IPlantable MIRK_OAK_SAPLING;
   public static BaseTreeFeatureConfig MIRK_OAK_TREE;
   public static BaseTreeFeatureConfig MIRK_OAK_PARTY;
   public static BaseTreeFeatureConfig MIRK_OAK_SHRUB;
   private static BlockState CHARRED_LOG;
   private static BlockState CHARRED_WOOD;
   private static BlockState CHARRED_BRANCH;
   public static WrappedTreeFeatureConfig CHARRED_TREE;
   private static BlockState APPLE_LOG;
   private static BlockState APPLE_LEAVES;
   private static BlockState APPLE_LEAVES_RED;
   private static BlockState APPLE_LEAVES_GREEN;
   private static BlockStateProvider APPLE_LEAVES_RED_POOL;
   private static BlockStateProvider APPLE_LEAVES_GREEN_POOL;
   private static BlockStateProvider APPLE_LEAVES_MIX_POOL;
   private static IPlantable APPLE_SAPLING;
   public static BaseTreeFeatureConfig APPLE_TREE_RED;
   public static BaseTreeFeatureConfig APPLE_TREE_RED_BEES;
   public static BaseTreeFeatureConfig APPLE_TREE_GREEN;
   public static BaseTreeFeatureConfig APPLE_TREE_GREEN_BEES;
   public static BaseTreeFeatureConfig APPLE_TREE_MIX;
   public static BaseTreeFeatureConfig APPLE_TREE_MIX_BEES;
   private static BlockState PEAR_LOG;
   private static BlockState PEAR_LEAVES;
   private static BlockState PEAR_LEAVES_FRUIT;
   private static BlockStateProvider PEAR_LEAVES_POOL;
   private static IPlantable PEAR_SAPLING;
   public static BaseTreeFeatureConfig PEAR_TREE;
   public static BaseTreeFeatureConfig PEAR_TREE_BEES;
   private static BlockState CHERRY_LOG;
   private static BlockState CHERRY_LEAVES;
   private static BlockState CHERRY_LEAVES_FRUIT;
   private static BlockStateProvider CHERRY_LEAVES_POOL;
   private static IPlantable CHERRY_SAPLING;
   public static BaseTreeFeatureConfig CHERRY_TREE;
   public static BaseTreeFeatureConfig CHERRY_TREE_BEES;
   private static BlockState LEBETHRON_LOG;
   private static BlockState LEBETHRON_WOOD;
   private static BlockState LEBETHRON_BRANCH;
   private static BlockState LEBETHRON_LEAVES;
   private static IPlantable LEBETHRON_SAPLING;
   public static BaseTreeFeatureConfig LEBETHRON_TREE;
   public static BaseTreeFeatureConfig LEBETHRON_TREE_BEES;
   public static BaseTreeFeatureConfig LEBETHRON_TREE_FANCY;
   public static BaseTreeFeatureConfig LEBETHRON_TREE_FANCY_BEES;
   public static BaseTreeFeatureConfig LEBETHRON_PARTY;
   private static BlockState BEECH_LOG;
   private static BlockState BEECH_WOOD;
   private static BlockState BEECH_STRIPPED_LOG;
   private static BlockState BEECH_BRANCH;
   private static BlockState BEECH_LEAVES;
   private static IPlantable BEECH_SAPLING;
   public static BaseTreeFeatureConfig BEECH_TREE;
   public static BaseTreeFeatureConfig BEECH_TREE_BEES;
   public static BaseTreeFeatureConfig BEECH_TREE_FANCY;
   public static BaseTreeFeatureConfig BEECH_TREE_FANCY_BEES;
   public static BaseTreeFeatureConfig BEECH_PARTY;
   public static BaseTreeFeatureConfig BEECH_FANGORN;
   public static WrappedTreeFeatureConfig BEECH_DEAD;
   private static BlockState MAPLE_LOG;
   private static BlockState MAPLE_WOOD;
   private static BlockState MAPLE_BRANCH;
   private static BlockState MAPLE_LEAVES;
   private static IPlantable MAPLE_SAPLING;
   public static BaseTreeFeatureConfig MAPLE_TREE;
   public static BaseTreeFeatureConfig MAPLE_TREE_BEES;
   public static BaseTreeFeatureConfig MAPLE_TREE_FANCY;
   public static BaseTreeFeatureConfig MAPLE_TREE_FANCY_BEES;
   public static BaseTreeFeatureConfig MAPLE_PARTY;
   private static BlockState ASPEN_LOG;
   private static BlockState ASPEN_LEAVES;
   private static IPlantable ASPEN_SAPLING;
   public static BaseTreeFeatureConfig ASPEN_TREE;
   private static BlockState LAIRELOSSE_LOG;
   private static BlockState LAIRELOSSE_LEAVES;
   private static IPlantable LAIRELOSSE_SAPLING;
   public static BaseTreeFeatureConfig LAIRELOSSE_TREE;
   private static BlockState CEDAR_LOG;
   private static BlockState CEDAR_WOOD;
   private static BlockState CEDAR_BRANCH;
   private static BlockState CEDAR_LEAVES;
   private static IPlantable CEDAR_SAPLING;
   public static BaseTreeFeatureConfig CEDAR_TREE;
   public static BaseTreeFeatureConfig CEDAR_TREE_LARGE;
   private static BlockState FIR_LOG;
   private static BlockState FIR_LEAVES;
   private static IPlantable FIR_SAPLING;
   public static BaseTreeFeatureConfig FIR_TREE;
   public static BaseTreeFeatureConfig FIR_SHRUB;
   private static BlockState LARCH_LOG;
   private static BlockState LARCH_LEAVES;
   private static IPlantable LARCH_SAPLING;
   public static BaseTreeFeatureConfig LARCH_TREE;
   private static BlockState HOLLY_LOG;
   private static BlockState HOLLY_LEAVES;
   private static IPlantable HOLLY_SAPLING;
   public static BaseTreeFeatureConfig HOLLY_TREE;
   public static BaseTreeFeatureConfig HOLLY_TREE_BEES;
   private static BlockState GREEN_OAK_LOG;
   private static BlockState GREEN_OAK_WOOD;
   private static BlockState GREEN_OAK_BRANCH;
   private static BlockState GREEN_OAK_LEAVES;
   private static IPlantable GREEN_OAK_SAPLING;
   private static BlockState RED_OAK_LEAVES;
   private static IPlantable RED_OAK_SAPLING;
   public static BaseTreeFeatureConfig GREEN_OAK_TREE;
   public static BaseTreeFeatureConfig GREEN_OAK_TREE_BEES;
   public static BaseTreeFeatureConfig RED_OAK_TREE;
   public static BaseTreeFeatureConfig RED_OAK_TREE_BEES;
   public static BaseTreeFeatureConfig GREEN_OAK_PARTY;
   public static BaseTreeFeatureConfig RED_OAK_PARTY;
   public static BaseTreeFeatureConfig GREEN_OAK_SHRUB;
   private static BlockState CYPRESS_LOG;
   private static BlockState CYPRESS_LEAVES;
   private static IPlantable CYPRESS_SAPLING;
   public static BaseTreeFeatureConfig CYPRESS_TREE;
   private static BlockState CULUMALDA_LOG;
   private static BlockState CULUMALDA_LEAVES;
   private static IPlantable CULUMALDA_SAPLING;
   public static BaseTreeFeatureConfig CULUMALDA_TREE;
   public static BaseTreeFeatureConfig CULUMALDA_TREE_BEES;
   private static BlockState SIMBELMYNE;
   private static BlockState ATHELAS;
   private static BlockState WILD_PIPEWEED;
   public static BlockClusterFeatureConfig SIMBELMYNE_CONFIG;
   public static BlockClusterFeatureConfig ATHELAS_CONFIG;
   public static BlockClusterFeatureConfig WILD_PIPEWEED_CONFIG;
   private static BlockState LILAC;
   private static BlockState ROSE_BUSH;
   private static BlockState PEONY;
   private static BlockState SUNFLOWER;
   private static BlockState HIBISCUS;
   private static BlockState FLAME_OF_HARAD;
   public static BlockClusterFeatureConfig LILAC_CONFIG;
   public static BlockClusterFeatureConfig ROSE_BUSH_CONFIG;
   public static BlockClusterFeatureConfig PEONY_CONFIG;
   public static BlockClusterFeatureConfig SUNFLOWER_CONFIG;
   public static BlockClusterFeatureConfig HIBISCUS_CONFIG;
   public static BlockClusterFeatureConfig FLAME_OF_HARAD_CONFIG;
   private static BlockState DEAD_BUSH;
   private static BlockClusterFeatureConfig DEAD_BUSH_CONFIG;
   private static BlockState CACTUS;
   private static BlockClusterFeatureConfig CACTUS_CONFIG;
   private static BlockState SAND;
   private static BlockState RED_SAND;
   private static BlockState WHITE_SAND;
   private static BlockState CLAY;
   private static BlockState QUAGMIRE;
   private static BlockState GRASS_BLOCK;
   private static BlockState BROWN_MUSHROOM;
   private static BlockState RED_MUSHROOM;
   private static BlockState MIRK_SHROOM;
   private static BlockClusterFeatureConfig BROWN_MUSHROOM_CONFIG;
   private static BlockClusterFeatureConfig RED_MUSHROOM_CONFIG;
   private static BlockClusterFeatureConfig MIRK_SHROOM_CONFIG;
   private static BlockState SUGAR_CANE;
   private static BlockClusterFeatureConfig SUGAR_CANE_CONFIG;
   private static BlockState REEDS;
   private static BlockState DRIED_REEDS;
   private static Function REEDS_CONFIG_FOR_DRIED_CHANCE;
   private static final float DEFAULT_DRIED_REEDS_CHANCE = 0.1F;
   private static BlockState PAPYRUS;
   private static ReedsFeatureConfig PAPYRUS_CONFIG;
   private static BlockState RUSHES;
   public static BlockClusterFeatureConfig RUSHES_CONFIG;
   private static BlockState PUMPKIN;
   public static BlockClusterFeatureConfig PUMPKIN_PATCH_CONFIG;
   private static BlockState LILY_PAD;
   private static BlockClusterFeatureConfig LILY_PAD_CONFIG;
   private static BlockState WHITE_WATER_LILY;
   private static BlockState YELLOW_WATER_LILY;
   private static BlockState PURPLE_WATER_LILY;
   private static BlockState PINK_WATER_LILY;
   private static BlockClusterFeatureConfig LILY_PAD_WITH_FLOWERS_CONFIG;
   private static BlockClusterFeatureConfig LILY_PAD_WITH_RARE_FLOWERS_CONFIG;
   private static BlockState SPONGE;
   private static BlockClusterFeatureConfig SPONGE_CONFIG;
   public static BlockState SWEET_BERRY_BUSH;
   public static BlockClusterFeatureConfig SWEET_BERRY_BUSH_CONFIG;
   private static BlockState MORDOR_MOSS;
   public static MordorMossFeatureConfig MORDOR_MOSS_CONFIG;
   private static BlockState MORDOR_GRASS;
   public static BlockClusterFeatureConfig MORDOR_GRASS_CONFIG;
   private static BlockState MORDOR_THORN;
   public static BlockClusterFeatureConfig MORDOR_THORN_CONFIG;
   private static BlockState MORGUL_SHROOM;
   public static BlockClusterFeatureConfig MORGUL_SHROOM_CONFIG;
   private static BlockState MORGUL_FLOWER;
   public static BlockClusterFeatureConfig MORGUL_FLOWER_CONFIG;
   public static LiquidsConfig WATER_SPRING_CONFIG;
   public static LiquidsConfig LAVA_SPRING_CONFIG;
   private static final int PARTY_TREE_NORMAL_BASE_HEIGHT = 10;
   private static final int PARTY_TREE_NORMAL_HEIGHT_RAND_A = 14;

   private static BaseTreeFeatureConfig buildClassicTree(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA, boolean bees, boolean vines) {
      return buildClassicTreeWithSpecifiedFoliage(log, leavesPool, baseHeight, heightRandA, new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), bees, vines);
   }

   private static BaseTreeFeatureConfig buildClassicTreeWithSpecifiedFoliage(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA, FoliagePlacer foliage, boolean bees, boolean vines) {
      List decorators = new ArrayList();
      if (bees) {
         decorators.add(Placements.field_243992_c);
      }

      if (vines) {
         decorators.add(TrunkVineTreeDecorator.field_236879_b_);
         decorators.add(LeaveVineTreeDecorator.field_236871_b_);
      }

      return (new Builder(new SimpleBlockStateProvider(log), leavesPool, foliage, new StraightTrunkPlacer(baseHeight, heightRandA, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.copyOf(decorators)).func_225568_b_();
   }

   private static BaseTreeFeatureConfig buildClassicTree(BlockState log, BlockState leaves, int baseHeight, int heightRandA) {
      return buildClassicTree(log, new SimpleBlockStateProvider(leaves), baseHeight, heightRandA, false, false);
   }

   private static BaseTreeFeatureConfig buildClassicTree(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA) {
      return buildClassicTree(log, leavesPool, baseHeight, heightRandA, false, false);
   }

   private static BaseTreeFeatureConfig buildClassicTreeWithBees(BlockState log, BlockState leaves, int baseHeight, int heightRandA) {
      return buildClassicTree(log, new SimpleBlockStateProvider(leaves), baseHeight, heightRandA, true, false);
   }

   private static BaseTreeFeatureConfig buildClassicTreeWithBees(BlockState log, BlockStateProvider leavesPool, int baseHeight, int heightRandA) {
      return buildClassicTree(log, leavesPool, baseHeight, heightRandA, true, false);
   }

   private static BaseTreeFeatureConfig buildClassicTreeWithVines(BlockState log, BlockState leaves, int baseHeight, int heightRandA) {
      return buildClassicTree(log, new SimpleBlockStateProvider(leaves), baseHeight, heightRandA, false, true);
   }

   private static BaseTreeFeatureConfig buildClassicTreeWithBeesAndVines(BlockState log, BlockState leaves, int baseHeight, int heightRandA) {
      return buildClassicTree(log, new SimpleBlockStateProvider(leaves), baseHeight, heightRandA, true, true);
   }

   private static BaseTreeFeatureConfig buildFancyTree(BlockState log, BlockState leaves, boolean bees) {
      List decorators = new ArrayList();
      if (bees) {
         decorators.add(Placements.field_243992_c);
      }

      return (new Builder(new SimpleBlockStateProvider(log), new SimpleBlockStateProvider(leaves), new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).func_236700_a_().func_236702_a_(Type.MOTION_BLOCKING).func_236703_a_(ImmutableList.copyOf(decorators)).func_225568_b_();
   }

   private static BaseTreeFeatureConfig buildFancyTree(BlockState log, BlockState leaves) {
      return buildFancyTree(log, leaves, false);
   }

   private static BaseTreeFeatureConfig buildFancyTreeWithBees(BlockState log, BlockState leaves) {
      return buildFancyTree(log, leaves, true);
   }

   private static BaseTreeFeatureConfig buildShrub(BlockState log, BlockState leaves) {
      return (new Builder(new SimpleBlockStateProvider(log), new SimpleBlockStateProvider(leaves), new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236702_a_(Type.MOTION_BLOCKING_NO_LEAVES).func_225568_b_();
   }

   private static BaseTreeFeatureConfig buildPartyTree(BlockState log, BlockState wood, BlockState branch, BlockState leaves, int baseHeight, int heightRandA, FoliagePlacer foliage, List decorators) {
      return (new Builder(new SimpleBlockStateProvider(log), new SimpleBlockStateProvider(leaves), foliage, new PartyTrunkPlacer(baseHeight, heightRandA, 0, wood, branch), new TwoLayerFeature(1, 1, 2))).func_236700_a_().func_236703_a_(ImmutableList.copyOf(decorators)).func_225568_b_();
   }

   private static BaseTreeFeatureConfig buildPartyTree(BlockState log, BlockState wood, BlockState branch, BlockState leaves, int baseHeight, int heightRandA, FoliagePlacer foliage) {
      return buildPartyTree(log, wood, branch, leaves, baseHeight, heightRandA, foliage, ImmutableList.of());
   }

   private static BaseTreeFeatureConfig buildNormalPartyTree(BlockState log, BlockState wood, BlockState branch, BlockState leaves) {
      return buildNormalFoliagePartyTree(log, wood, branch, leaves, 10, 14);
   }

   private static BaseTreeFeatureConfig buildNormalFoliagePartyTree(BlockState log, BlockState wood, BlockState branch, BlockState leaves, int baseHeight, int heightRandA) {
      return buildPartyTree(log, wood, branch, leaves, baseHeight, heightRandA, new ClusterFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0)), ImmutableList.of());
   }

   private static BaseTreeFeatureConfig buildNormalHeightPartyTree(BlockState log, BlockState wood, BlockState branch, BlockState leaves, FoliagePlacer foliage) {
      return buildPartyTree(log, wood, branch, leaves, 10, 14, foliage, ImmutableList.of());
   }

   private static BaseTreeFeatureConfig buildMirkPartyTree(BlockState log, BlockState wood, BlockState branch, BlockState leaves) {
      return buildMirkPartyTree(log, wood, branch, leaves, ImmutableList.of());
   }

   private static BaseTreeFeatureConfig buildMirkPartyTree(BlockState log, BlockState wood, BlockState branch, BlockState leaves, List decorators) {
      return buildPartyTree(log, wood, branch, leaves, 10, 14, new MirkOakFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 1), decorators);
   }

   private static BaseTreeFeatureConfig buildFangornTree(BlockState log, BlockState wood, BlockState strippedLog, BlockState leaves) {
      return (new Builder(new SimpleBlockStateProvider(log), new SimpleBlockStateProvider(leaves), new ClusterFoliagePlacer(FeatureSpread.func_242252_a(3), FeatureSpread.func_242252_a(0)), new FangornTrunkPlacer(20, 20, 0, wood, strippedLog), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(TrunkVineTreeDecorator.field_236879_b_, LeaveVineTreeDecorator.field_236871_b_)).func_225568_b_();
   }

   public static void setup(Register event) {
      if (event.getRegistry() == ForgeRegistries.BLOCKS) {
         LOTRWorldCarvers.register();
         MORDOR_ROCK_FILLER = new BlockMatchRuleTest((Block)LOTRBlocks.MORDOR_ROCK.get());
         SAND_FILLER = new TagMatchRuleTest(BlockTags.field_203436_u);
         STONE = Blocks.field_150348_b.func_176223_P();
         DIRT = Blocks.field_150346_d.func_176223_P();
         COARSE_DIRT = Blocks.field_196660_k.func_176223_P();
         GRAVEL = Blocks.field_150351_n.func_176223_P();
         GRANITE = Blocks.field_196650_c.func_176223_P();
         DIORITE = Blocks.field_196654_e.func_176223_P();
         ANDESITE = Blocks.field_196656_g.func_176223_P();
         PACKED_ICE = Blocks.field_150403_cj.func_176223_P();
         BLUE_ICE = Blocks.field_205164_gk.func_176223_P();
         GONDOR_ROCK = ((Block)LOTRBlocks.GONDOR_ROCK.get()).func_176223_P();
         MORDOR_ROCK = ((Block)LOTRBlocks.MORDOR_ROCK.get()).func_176223_P();
         ROHAN_ROCK = ((Block)LOTRBlocks.ROHAN_ROCK.get()).func_176223_P();
         BLUE_ROCK = ((Block)LOTRBlocks.BLUE_ROCK.get()).func_176223_P();
         RED_ROCK = ((Block)LOTRBlocks.RED_ROCK.get()).func_176223_P();
         MORDOR_DIRT = ((Block)LOTRBlocks.MORDOR_DIRT.get()).func_176223_P();
         MORDOR_GRAVEL = ((Block)LOTRBlocks.MORDOR_GRAVEL.get()).func_176223_P();
         COAL_ORE = Blocks.field_150365_q.func_176223_P();
         IRON_ORE = Blocks.field_150366_p.func_176223_P();
         COPPER_ORE = ((Block)LOTRBlocks.COPPER_ORE.get()).func_176223_P();
         TIN_ORE = ((Block)LOTRBlocks.TIN_ORE.get()).func_176223_P();
         GOLD_ORE = Blocks.field_150352_o.func_176223_P();
         SILVER_ORE = ((Block)LOTRBlocks.SILVER_ORE.get()).func_176223_P();
         SULFUR_ORE = ((Block)LOTRBlocks.SULFUR_ORE.get()).func_176223_P();
         NITER_ORE = ((Block)LOTRBlocks.NITER_ORE.get()).func_176223_P();
         SALT_ORE = ((Block)LOTRBlocks.SALT_ORE.get()).func_176223_P();
         LAPIS_ORE = Blocks.field_150369_x.func_176223_P();
         MITHRIL_ORE = ((Block)LOTRBlocks.MITHRIL_ORE.get()).func_176223_P();
         EDHELVIR_ORE = ((Block)LOTRBlocks.EDHELVIR_ORE.get()).func_176223_P();
         GLOWSTONE_ORE = ((Block)LOTRBlocks.GLOWSTONE_ORE.get()).func_176223_P();
         DURNOR_ORE = ((Block)LOTRBlocks.DURNOR_ORE.get()).func_176223_P();
         MORGUL_IRON_ORE_MORDOR = ((Block)LOTRBlocks.MORGUL_IRON_ORE_MORDOR.get()).func_176223_P();
         MORGUL_IRON_ORE_STONE = ((Block)LOTRBlocks.MORGUL_IRON_ORE_STONE.get()).func_176223_P();
         GULDURIL_ORE_MORDOR = ((Block)LOTRBlocks.GULDURIL_ORE_MORDOR.get()).func_176223_P();
         GULDURIL_ORE_STONE = ((Block)LOTRBlocks.GULDURIL_ORE_STONE.get()).func_176223_P();
         EDHELVIR_CRYSTAL = ((Block)LOTRBlocks.EDHELVIR_CRYSTAL.get()).func_176223_P();
         GULDURIL_CRYSTAL = ((Block)LOTRBlocks.GULDURIL_CRYSTAL.get()).func_176223_P();
         GLOWSTONE_CRYSTAL = ((Block)LOTRBlocks.GLOWSTONE_CRYSTAL.get()).func_176223_P();
         COBWEB = Blocks.field_196553_aF.func_176223_P();
         WATER = Blocks.field_150355_j.func_176223_P();
         LAVA = Blocks.field_150353_l.func_176223_P();
         LOTRTrunkPlacers.register();
         LOTRFoliagePlacers.register();
         LOTRTreeDecorators.register();
         OAK_LOG = Blocks.field_196617_K.func_176223_P();
         OAK_WOOD = Blocks.field_196626_Q.func_176223_P();
         OAK_STRIPPED_LOG = Blocks.field_203204_R.func_176223_P();
         OAK_BRANCH = ((Block)LOTRBlocks.OAK_BRANCH.get()).func_176223_P();
         OAK_LEAVES = Blocks.field_196642_W.func_176223_P();
         OAK_SAPLING = (IPlantable)Blocks.field_196674_t;
         OAK_TREE_VINES = buildClassicTreeWithVines(OAK_LOG, OAK_LEAVES, 4, 2);
         OAK_TREE_BEES_VINES = buildClassicTreeWithBeesAndVines(OAK_LOG, OAK_LEAVES, 4, 2);
         OAK_TREE_TALL = buildClassicTree(OAK_LOG, (BlockState)OAK_LEAVES, 6, 3);
         OAK_TREE_TALL_BEES = buildClassicTree(OAK_LOG, (BlockState)OAK_LEAVES, 6, 3);
         OAK_TREE_TALL_VINES = buildClassicTreeWithVines(OAK_LOG, OAK_LEAVES, 6, 3);
         OAK_TREE_TALL_BEES_VINES = buildClassicTreeWithBeesAndVines(OAK_LOG, OAK_LEAVES, 6, 3);
         OAK_DESERT = new WrappedTreeFeatureConfig((new Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES), new DesertFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 2), new DesertTrunkPlacer(3, 2, 0, OAK_WOOD), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_(), WrappedTreeFeatureConfig.AlternativeTreeSoil.DESERT);
         OAK_DESERT_BEES = new WrappedTreeFeatureConfig((new Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES), new DesertFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 2), new DesertTrunkPlacer(3, 2, 0, OAK_WOOD), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(Placements.field_243992_c)).func_225568_b_(), WrappedTreeFeatureConfig.AlternativeTreeSoil.DESERT);
         OAK_DEAD = new WrappedTreeFeatureConfig((new Builder(new SimpleBlockStateProvider(OAK_LOG), new SimpleBlockStateProvider(OAK_LEAVES), new EmptyFoliagePlacer(), new DeadTrunkPlacer(2, 3, 0, OAK_WOOD, OAK_BRANCH), new TwoLayerFeature(1, 0, 1))).func_225568_b_(), WrappedTreeFeatureConfig.AlternativeTreeSoil.DESERT);
         OAK_PARTY = buildNormalPartyTree(OAK_LOG, OAK_WOOD, OAK_BRANCH, OAK_LEAVES);
         OAK_FANGORN = buildFangornTree(OAK_LOG, OAK_WOOD, OAK_STRIPPED_LOG, OAK_LEAVES);
         OAK_SHRUB = buildShrub(OAK_LOG, OAK_LEAVES);
         SPRUCE_LOG = Blocks.field_196618_L.func_176223_P();
         SPRUCE_WOOD = Blocks.field_196629_R.func_176223_P();
         SPRUCE_BRANCH = ((Block)LOTRBlocks.SPRUCE_BRANCH.get()).func_176223_P();
         SPRUCE_LEAVES = Blocks.field_196645_X.func_176223_P();
         SPRUCE_SAPLING = (IPlantable)Blocks.field_196675_u;
         SPRUCE_DEAD = new WrappedTreeFeatureConfig((new Builder(new SimpleBlockStateProvider(SPRUCE_LOG), new SimpleBlockStateProvider(SPRUCE_LEAVES), new EmptyFoliagePlacer(), new DeadTrunkPlacer(2, 3, 0, SPRUCE_WOOD, SPRUCE_BRANCH), new TwoLayerFeature(1, 0, 1))).func_225568_b_(), WrappedTreeFeatureConfig.AlternativeTreeSoil.DESERT);
         SPRUCE_SHRUB = buildShrub(SPRUCE_LOG, SPRUCE_LEAVES);
         BIRCH_LOG = Blocks.field_196619_M.func_176223_P();
         BIRCH_WOOD = Blocks.field_196631_S.func_176223_P();
         BIRCH_BRANCH = ((Block)LOTRBlocks.BIRCH_BRANCH.get()).func_176223_P();
         BIRCH_LEAVES = Blocks.field_196647_Y.func_176223_P();
         BIRCH_SAPLING = (IPlantable)Blocks.field_196676_v;
         BIRCH_TREE_FANCY = buildFancyTree(BIRCH_LOG, BIRCH_LEAVES);
         BIRCH_TREE_FANCY_BEES = buildFancyTreeWithBees(BIRCH_LOG, BIRCH_LEAVES);
         BIRCH_TREE_ALT = (new Builder(new SimpleBlockStateProvider(BIRCH_LOG), new SimpleBlockStateProvider(BIRCH_LEAVES), new AspenFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(2), FeatureSpread.func_242253_a(2, 2)), new StraightTrunkPlacer(8, 8, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         BIRCH_TREE_ALT_BEES = (new Builder(new SimpleBlockStateProvider(BIRCH_LOG), new SimpleBlockStateProvider(BIRCH_LEAVES), new AspenFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(2), FeatureSpread.func_242253_a(2, 2)), new StraightTrunkPlacer(8, 8, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(Placements.field_243992_c)).func_225568_b_();
         BIRCH_DEAD = new WrappedTreeFeatureConfig((new Builder(new SimpleBlockStateProvider(BIRCH_LOG), new SimpleBlockStateProvider(BIRCH_LEAVES), new EmptyFoliagePlacer(), new DeadTrunkPlacer(2, 3, 0, BIRCH_WOOD, BIRCH_BRANCH), new TwoLayerFeature(1, 0, 1))).func_225568_b_(), WrappedTreeFeatureConfig.AlternativeTreeSoil.DESERT);
         BIRCH_PARTY = buildNormalPartyTree(BIRCH_LOG, BIRCH_WOOD, BIRCH_BRANCH, BIRCH_LEAVES);
         DARK_OAK_LOG = Blocks.field_196623_P.func_176223_P();
         DARK_OAK_WOOD = Blocks.field_196639_V.func_176223_P();
         DARK_OAK_BRANCH = ((Block)LOTRBlocks.DARK_OAK_BRANCH.get()).func_176223_P();
         DARK_OAK_LEAVES = Blocks.field_196574_ab.func_176223_P();
         DARK_OAK_SAPLING = (IPlantable)Blocks.field_196680_y;
         DARK_OAK_PARTY = buildNormalHeightPartyTree(DARK_OAK_LOG, DARK_OAK_WOOD, DARK_OAK_BRANCH, DARK_OAK_LEAVES, new DarkOakFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0)));
         DARK_OAK_SHRUB = buildShrub(DARK_OAK_LOG, DARK_OAK_LEAVES);
         PINE_LOG = ((Block)LOTRBlocks.PINE_LOG.get()).func_176223_P();
         PINE_LOG_SLAB = ((Block)LOTRBlocks.PINE_LOG_SLAB.get()).func_176223_P();
         PINE_LEAVES = ((Block)LOTRBlocks.PINE_LEAVES.get()).func_176223_P();
         PINE_SAPLING = (IPlantable)LOTRBlocks.PINE_SAPLING.get();
         BlockStateProvider pineBranchProvider = (new WeightedBlockStateProvider()).func_227407_a_(PINE_LOG, 1).func_227407_a_(PINE_LOG_SLAB, 2);
         PINE_TREE = (new Builder(new SimpleBlockStateProvider(PINE_LOG), new SimpleBlockStateProvider(PINE_LEAVES), new LOTRPineFoliagePlacer(FeatureSpread.func_242253_a(3, 0), FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(6, 6)), new StraightTrunkPlacer(12, 12, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(new PineBranchDecorator(pineBranchProvider, 0.33F), new PineStripDecorator(0.1F, 0.3F, 0.7F))).func_225568_b_();
         SHIRE_PINE_TREE = (new Builder(new SimpleBlockStateProvider(PINE_LOG), new SimpleBlockStateProvider(PINE_LEAVES), new ShirePineFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(6, 3)), new StraightTrunkPlacer(10, 10, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(new PineBranchDecorator(pineBranchProvider, 0.33F), new PineStripDecorator(0.1F, 0.3F, 0.7F))).func_225568_b_();
         PINE_DEAD = (new Builder(new SimpleBlockStateProvider(PINE_LOG), new SimpleBlockStateProvider(PINE_LEAVES), new EmptyFoliagePlacer(), new StraightTrunkPlacer(12, 12, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(new PineBranchDecorator(pineBranchProvider, 0.33F), new PineStripDecorator(0.1F, 0.3F, 0.7F))).func_225568_b_();
         PINE_SHRUB = buildShrub(PINE_LOG, PINE_LEAVES);
         MALLORN_LOG = ((Block)LOTRBlocks.MALLORN_LOG.get()).func_176223_P();
         MALLORN_WOOD = ((Block)LOTRBlocks.MALLORN_WOOD.get()).func_176223_P();
         MALLORN_BRANCH = ((Block)LOTRBlocks.MALLORN_BRANCH.get()).func_176223_P();
         MALLORN_LEAVES = ((Block)LOTRBlocks.MALLORN_LEAVES.get()).func_176223_P();
         MALLORN_SAPLING = (IPlantable)LOTRBlocks.MALLORN_SAPLING.get();
         MALLORN_TREE = buildClassicTree(MALLORN_LOG, (BlockState)MALLORN_LEAVES, 6, 3);
         MALLORN_TREE_BEES = buildClassicTreeWithBees(MALLORN_LOG, (BlockState)MALLORN_LEAVES, 6, 3);
         MALLORN_TREE_BOUGHS = (new Builder(new SimpleBlockStateProvider(MALLORN_LOG), new SimpleBlockStateProvider(MALLORN_LEAVES), new BoughsFoliagePlacer(FeatureSpread.func_242252_a(4), FeatureSpread.func_242252_a(0), 3), new BoughsTrunkPlacer(10, 4, 0, MALLORN_WOOD, MALLORN_BRANCH), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         MALLORN_PARTY = buildPartyTree(MALLORN_LOG, MALLORN_WOOD, MALLORN_BRANCH, MALLORN_LEAVES, 15, 15, new AcaciaFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0)));
         MIRK_OAK_LOG = ((Block)LOTRBlocks.MIRK_OAK_LOG.get()).func_176223_P();
         MIRK_OAK_WOOD = ((Block)LOTRBlocks.MIRK_OAK_WOOD.get()).func_176223_P();
         MIRK_OAK_BRANCH = ((Block)LOTRBlocks.MIRK_OAK_BRANCH.get()).func_176223_P();
         MIRK_OAK_LEAVES = ((Block)LOTRBlocks.MIRK_OAK_LEAVES.get()).func_176223_P();
         MIRK_OAK_SAPLING = (IPlantable)LOTRBlocks.MIRK_OAK_SAPLING.get();
         List mirkOakDecorators = ImmutableList.of(new MirkOakLeavesGrowthDecorator(), new MirkOakWebsDecorator(true, 0.25F, 0.1F, 0.15F), new MirkOakWebsDecorator(true, 0.05F, 0.35F, 0.2F), new LeaveVineTreeDecorator());
         MIRK_OAK_TREE = (new Builder(new SimpleBlockStateProvider(MIRK_OAK_LOG), new SimpleBlockStateProvider(MIRK_OAK_LEAVES), new MirkOakFoliagePlacer(FeatureSpread.func_242253_a(3, 1), FeatureSpread.func_242252_a(0), 3), new MirkOakTrunkPlacer(4, 5, 0, MIRK_OAK_WOOD, MIRK_OAK_BRANCH), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(mirkOakDecorators).func_225568_b_();
         MIRK_OAK_PARTY = buildMirkPartyTree(MIRK_OAK_LOG, MIRK_OAK_WOOD, MIRK_OAK_BRANCH, MIRK_OAK_LEAVES, mirkOakDecorators);
         MIRK_OAK_SHRUB = buildShrub(MIRK_OAK_LOG, MIRK_OAK_LEAVES);
         CHARRED_LOG = ((Block)LOTRBlocks.CHARRED_LOG.get()).func_176223_P();
         CHARRED_WOOD = ((Block)LOTRBlocks.CHARRED_WOOD.get()).func_176223_P();
         CHARRED_BRANCH = ((Block)LOTRBlocks.CHARRED_BRANCH.get()).func_176223_P();
         CHARRED_TREE = new WrappedTreeFeatureConfig((new Builder(new SimpleBlockStateProvider(CHARRED_LOG), new SimpleBlockStateProvider(OAK_LEAVES), new EmptyFoliagePlacer(), new DeadTrunkPlacer(2, 3, 0, CHARRED_WOOD, CHARRED_BRANCH), new TwoLayerFeature(1, 0, 1))).func_225568_b_(), WrappedTreeFeatureConfig.AlternativeTreeSoil.CHARRED);
         APPLE_LOG = ((Block)LOTRBlocks.APPLE_LOG.get()).func_176223_P();
         APPLE_LEAVES = ((Block)LOTRBlocks.APPLE_LEAVES.get()).func_176223_P();
         APPLE_LEAVES_RED = ((Block)LOTRBlocks.APPLE_LEAVES_RED.get()).func_176223_P();
         APPLE_LEAVES_GREEN = ((Block)LOTRBlocks.APPLE_LEAVES_GREEN.get()).func_176223_P();
         APPLE_LEAVES_RED_POOL = (new WeightedBlockStateProvider()).func_227407_a_(APPLE_LEAVES, 15).func_227407_a_(APPLE_LEAVES_RED, 1);
         APPLE_LEAVES_GREEN_POOL = (new WeightedBlockStateProvider()).func_227407_a_(APPLE_LEAVES, 15).func_227407_a_(APPLE_LEAVES_GREEN, 1);
         APPLE_LEAVES_MIX_POOL = (new WeightedBlockStateProvider()).func_227407_a_(APPLE_LEAVES, 30).func_227407_a_(APPLE_LEAVES_RED, 1).func_227407_a_(APPLE_LEAVES_GREEN, 1);
         APPLE_SAPLING = (IPlantable)LOTRBlocks.APPLE_SAPLING.get();
         APPLE_TREE_RED = buildClassicTree(APPLE_LOG, (BlockStateProvider)APPLE_LEAVES_RED_POOL, 4, 3);
         APPLE_TREE_RED_BEES = buildClassicTreeWithBees(APPLE_LOG, (BlockStateProvider)APPLE_LEAVES_RED_POOL, 4, 3);
         APPLE_TREE_GREEN = buildClassicTree(APPLE_LOG, (BlockStateProvider)APPLE_LEAVES_GREEN_POOL, 4, 3);
         APPLE_TREE_GREEN_BEES = buildClassicTreeWithBees(APPLE_LOG, (BlockStateProvider)APPLE_LEAVES_GREEN_POOL, 4, 3);
         APPLE_TREE_MIX = buildClassicTree(APPLE_LOG, (BlockStateProvider)APPLE_LEAVES_MIX_POOL, 4, 3);
         APPLE_TREE_MIX_BEES = buildClassicTreeWithBees(APPLE_LOG, (BlockStateProvider)APPLE_LEAVES_MIX_POOL, 4, 3);
         PEAR_LOG = ((Block)LOTRBlocks.PEAR_LOG.get()).func_176223_P();
         PEAR_LEAVES = ((Block)LOTRBlocks.PEAR_LEAVES.get()).func_176223_P();
         PEAR_LEAVES_FRUIT = ((Block)LOTRBlocks.PEAR_LEAVES_FRUIT.get()).func_176223_P();
         PEAR_LEAVES_POOL = (new WeightedBlockStateProvider()).func_227407_a_(PEAR_LEAVES, 15).func_227407_a_(PEAR_LEAVES_FRUIT, 1);
         PEAR_SAPLING = (IPlantable)LOTRBlocks.PEAR_SAPLING.get();
         PEAR_TREE = buildClassicTree(PEAR_LOG, (BlockStateProvider)PEAR_LEAVES_POOL, 4, 1);
         PEAR_TREE_BEES = buildClassicTreeWithBees(PEAR_LOG, (BlockStateProvider)PEAR_LEAVES_POOL, 4, 1);
         CHERRY_LOG = ((Block)LOTRBlocks.CHERRY_LOG.get()).func_176223_P();
         CHERRY_LEAVES = ((Block)LOTRBlocks.CHERRY_LEAVES.get()).func_176223_P();
         CHERRY_LEAVES_FRUIT = ((Block)LOTRBlocks.CHERRY_LEAVES_FRUIT.get()).func_176223_P();
         CHERRY_LEAVES_POOL = (new WeightedBlockStateProvider()).func_227407_a_(CHERRY_LEAVES, 7).func_227407_a_(CHERRY_LEAVES_FRUIT, 1);
         CHERRY_SAPLING = (IPlantable)LOTRBlocks.CHERRY_SAPLING.get();
         CHERRY_TREE = buildClassicTree(CHERRY_LOG, (BlockStateProvider)CHERRY_LEAVES_POOL, 4, 4);
         CHERRY_TREE_BEES = buildClassicTreeWithBees(CHERRY_LOG, (BlockStateProvider)CHERRY_LEAVES_POOL, 4, 4);
         LEBETHRON_LOG = ((Block)LOTRBlocks.LEBETHRON_LOG.get()).func_176223_P();
         LEBETHRON_WOOD = ((Block)LOTRBlocks.LEBETHRON_WOOD.get()).func_176223_P();
         LEBETHRON_BRANCH = ((Block)LOTRBlocks.LEBETHRON_BRANCH.get()).func_176223_P();
         LEBETHRON_LEAVES = ((Block)LOTRBlocks.LEBETHRON_LEAVES.get()).func_176223_P();
         LEBETHRON_SAPLING = (IPlantable)LOTRBlocks.LEBETHRON_SAPLING.get();
         LEBETHRON_TREE = buildClassicTree(LEBETHRON_LOG, (BlockState)LEBETHRON_LEAVES, 5, 4);
         LEBETHRON_TREE_BEES = buildClassicTreeWithBees(LEBETHRON_LOG, (BlockState)LEBETHRON_LEAVES, 5, 4);
         LEBETHRON_TREE_FANCY = buildFancyTree(LEBETHRON_LOG, LEBETHRON_LEAVES);
         LEBETHRON_TREE_FANCY_BEES = buildFancyTreeWithBees(LEBETHRON_LOG, LEBETHRON_LEAVES);
         LEBETHRON_PARTY = buildNormalPartyTree(LEBETHRON_LOG, LEBETHRON_WOOD, LEBETHRON_BRANCH, LEBETHRON_LEAVES);
         BEECH_LOG = ((Block)LOTRBlocks.BEECH_LOG.get()).func_176223_P();
         BEECH_WOOD = ((Block)LOTRBlocks.BEECH_WOOD.get()).func_176223_P();
         BEECH_STRIPPED_LOG = ((Block)LOTRBlocks.STRIPPED_BEECH_LOG.get()).func_176223_P();
         BEECH_BRANCH = ((Block)LOTRBlocks.BEECH_BRANCH.get()).func_176223_P();
         BEECH_LEAVES = ((Block)LOTRBlocks.BEECH_LEAVES.get()).func_176223_P();
         BEECH_SAPLING = (IPlantable)LOTRBlocks.BEECH_SAPLING.get();
         BEECH_TREE = buildClassicTree(BEECH_LOG, (BlockState)BEECH_LEAVES, 5, 4);
         BEECH_TREE_BEES = buildClassicTreeWithBees(BEECH_LOG, (BlockState)BEECH_LEAVES, 5, 4);
         BEECH_TREE_FANCY = buildFancyTree(BEECH_LOG, BEECH_LEAVES);
         BEECH_TREE_FANCY_BEES = buildFancyTreeWithBees(BEECH_LOG, BEECH_LEAVES);
         BEECH_PARTY = buildNormalPartyTree(BEECH_LOG, BEECH_WOOD, BEECH_BRANCH, BEECH_LEAVES);
         BEECH_FANGORN = buildFangornTree(BEECH_LOG, BEECH_WOOD, BEECH_STRIPPED_LOG, BEECH_LEAVES);
         BEECH_DEAD = new WrappedTreeFeatureConfig((new Builder(new SimpleBlockStateProvider(BEECH_LOG), new SimpleBlockStateProvider(BEECH_LEAVES), new EmptyFoliagePlacer(), new DeadTrunkPlacer(2, 3, 0, BEECH_WOOD, BEECH_BRANCH), new TwoLayerFeature(1, 0, 1))).func_225568_b_(), WrappedTreeFeatureConfig.AlternativeTreeSoil.DESERT);
         MAPLE_LOG = ((Block)LOTRBlocks.MAPLE_LOG.get()).func_176223_P();
         MAPLE_WOOD = ((Block)LOTRBlocks.MAPLE_WOOD.get()).func_176223_P();
         MAPLE_BRANCH = ((Block)LOTRBlocks.MAPLE_BRANCH.get()).func_176223_P();
         MAPLE_LEAVES = ((Block)LOTRBlocks.MAPLE_LEAVES.get()).func_176223_P();
         MAPLE_SAPLING = (IPlantable)LOTRBlocks.MAPLE_SAPLING.get();
         MAPLE_TREE = buildClassicTree(MAPLE_LOG, (BlockState)MAPLE_LEAVES, 4, 4);
         MAPLE_TREE_BEES = buildClassicTreeWithBees(MAPLE_LOG, (BlockState)MAPLE_LEAVES, 4, 4);
         MAPLE_TREE_FANCY = buildFancyTree(MAPLE_LOG, MAPLE_LEAVES);
         MAPLE_TREE_FANCY_BEES = buildFancyTreeWithBees(MAPLE_LOG, MAPLE_LEAVES);
         MAPLE_PARTY = buildNormalPartyTree(MAPLE_LOG, MAPLE_WOOD, MAPLE_BRANCH, MAPLE_LEAVES);
         ASPEN_LOG = ((Block)LOTRBlocks.ASPEN_LOG.get()).func_176223_P();
         ASPEN_LEAVES = ((Block)LOTRBlocks.ASPEN_LEAVES.get()).func_176223_P();
         ASPEN_SAPLING = (IPlantable)LOTRBlocks.ASPEN_SAPLING.get();
         ASPEN_TREE = (new Builder(new SimpleBlockStateProvider(ASPEN_LOG), new SimpleBlockStateProvider(ASPEN_LEAVES), new AspenFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(2), FeatureSpread.func_242253_a(2, 2)), new StraightTrunkPlacer(8, 7, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         LAIRELOSSE_LOG = ((Block)LOTRBlocks.LAIRELOSSE_LOG.get()).func_176223_P();
         LAIRELOSSE_LEAVES = ((Block)LOTRBlocks.LAIRELOSSE_LEAVES.get()).func_176223_P();
         LAIRELOSSE_SAPLING = (IPlantable)LOTRBlocks.LAIRELOSSE_SAPLING.get();
         LAIRELOSSE_TREE = (new Builder(new SimpleBlockStateProvider(LAIRELOSSE_LOG), new SimpleBlockStateProvider(LAIRELOSSE_LEAVES), new LairelosseFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(1, 2)), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         CEDAR_LOG = ((Block)LOTRBlocks.CEDAR_LOG.get()).func_176223_P();
         CEDAR_WOOD = ((Block)LOTRBlocks.CEDAR_WOOD.get()).func_176223_P();
         CEDAR_BRANCH = ((Block)LOTRBlocks.CEDAR_BRANCH.get()).func_176223_P();
         CEDAR_LEAVES = ((Block)LOTRBlocks.CEDAR_LEAVES.get()).func_176223_P();
         CEDAR_SAPLING = (IPlantable)LOTRBlocks.CEDAR_SAPLING.get();
         CEDAR_TREE = (new Builder(new SimpleBlockStateProvider(CEDAR_LOG), new SimpleBlockStateProvider(CEDAR_LEAVES), new CedarFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new CedarTrunkPlacer(10, 6, 0, CEDAR_BRANCH), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         CEDAR_TREE_LARGE = (new Builder(new SimpleBlockStateProvider(CEDAR_LOG), new SimpleBlockStateProvider(CEDAR_LEAVES), new CedarFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new CedarTrunkPlacer(15, 15, 0, CEDAR_BRANCH), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         FIR_LOG = ((Block)LOTRBlocks.FIR_LOG.get()).func_176223_P();
         FIR_LEAVES = ((Block)LOTRBlocks.FIR_LEAVES.get()).func_176223_P();
         FIR_SAPLING = (IPlantable)LOTRBlocks.FIR_SAPLING.get();
         FIR_TREE = (new Builder(new SimpleBlockStateProvider(FIR_LOG), new SimpleBlockStateProvider(FIR_LEAVES), new FirFoliagePlacer(FeatureSpread.func_242253_a(3, 0), FeatureSpread.func_242252_a(2), FeatureSpread.func_242253_a(7, 4)), new StraightTrunkPlacer(6, 7, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         FIR_SHRUB = buildShrub(FIR_LOG, FIR_LEAVES);
         LARCH_LOG = ((Block)LOTRBlocks.LARCH_LOG.get()).func_176223_P();
         LARCH_LEAVES = ((Block)LOTRBlocks.LARCH_LEAVES.get()).func_176223_P();
         LARCH_SAPLING = (IPlantable)LOTRBlocks.LARCH_SAPLING.get();
         LARCH_TREE = (new Builder(new SimpleBlockStateProvider(LARCH_LOG), new SimpleBlockStateProvider(LARCH_LEAVES), new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(2, 1)), new StraightTrunkPlacer(8, 8, 0), new TwoLayerFeature(2, 0, 2))).func_236700_a_().func_225568_b_();
         HOLLY_LOG = ((Block)LOTRBlocks.HOLLY_LOG.get()).func_176223_P();
         HOLLY_LEAVES = ((Block)LOTRBlocks.HOLLY_LEAVES.get()).func_176223_P();
         HOLLY_SAPLING = (IPlantable)LOTRBlocks.HOLLY_SAPLING.get();
         HOLLY_TREE = (new Builder(new SimpleBlockStateProvider(HOLLY_LOG), new SimpleBlockStateProvider(HOLLY_LEAVES), new HollyFoliagePlacer(FeatureSpread.func_242253_a(2, 0), FeatureSpread.func_242252_a(0), FeatureSpread.func_242253_a(1, 2)), new StraightTrunkPlacer(9, 5, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         HOLLY_TREE_BEES = (new Builder(new SimpleBlockStateProvider(HOLLY_LOG), new SimpleBlockStateProvider(HOLLY_LEAVES), new HollyFoliagePlacer(FeatureSpread.func_242253_a(2, 0), FeatureSpread.func_242252_a(0), FeatureSpread.func_242253_a(1, 2)), new StraightTrunkPlacer(9, 5, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(Placements.field_243992_c)).func_225568_b_();
         GREEN_OAK_LOG = ((Block)LOTRBlocks.GREEN_OAK_LOG.get()).func_176223_P();
         GREEN_OAK_WOOD = ((Block)LOTRBlocks.GREEN_OAK_WOOD.get()).func_176223_P();
         GREEN_OAK_BRANCH = ((Block)LOTRBlocks.GREEN_OAK_BRANCH.get()).func_176223_P();
         GREEN_OAK_LEAVES = ((Block)LOTRBlocks.GREEN_OAK_LEAVES.get()).func_176223_P();
         GREEN_OAK_SAPLING = (IPlantable)LOTRBlocks.GREEN_OAK_SAPLING.get();
         RED_OAK_LEAVES = ((Block)LOTRBlocks.RED_OAK_LEAVES.get()).func_176223_P();
         RED_OAK_SAPLING = (IPlantable)LOTRBlocks.RED_OAK_SAPLING.get();
         GREEN_OAK_TREE = (new Builder(new SimpleBlockStateProvider(GREEN_OAK_LOG), new SimpleBlockStateProvider(GREEN_OAK_LEAVES), new MirkOakFoliagePlacer(FeatureSpread.func_242252_a(3), FeatureSpread.func_242252_a(0), 3), new MirkOakTrunkPlacer(4, 4, 0, GREEN_OAK_WOOD, GREEN_OAK_BRANCH), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         GREEN_OAK_TREE_BEES = (new Builder(new SimpleBlockStateProvider(GREEN_OAK_LOG), new SimpleBlockStateProvider(GREEN_OAK_LEAVES), new MirkOakFoliagePlacer(FeatureSpread.func_242252_a(3), FeatureSpread.func_242252_a(0), 3), new MirkOakTrunkPlacer(4, 4, 0, GREEN_OAK_WOOD, GREEN_OAK_BRANCH), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(Placements.field_243992_c)).func_225568_b_();
         RED_OAK_TREE = (new Builder(new SimpleBlockStateProvider(GREEN_OAK_LOG), new SimpleBlockStateProvider(RED_OAK_LEAVES), new MirkOakFoliagePlacer(FeatureSpread.func_242253_a(3, 1), FeatureSpread.func_242252_a(0), 3), new MirkOakTrunkPlacer(6, 3, 0, GREEN_OAK_WOOD, GREEN_OAK_BRANCH), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         RED_OAK_TREE_BEES = (new Builder(new SimpleBlockStateProvider(GREEN_OAK_LOG), new SimpleBlockStateProvider(RED_OAK_LEAVES), new MirkOakFoliagePlacer(FeatureSpread.func_242253_a(3, 1), FeatureSpread.func_242252_a(0), 3), new MirkOakTrunkPlacer(6, 3, 0, GREEN_OAK_WOOD, GREEN_OAK_BRANCH), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_236703_a_(ImmutableList.of(Placements.field_243992_c)).func_225568_b_();
         GREEN_OAK_PARTY = buildMirkPartyTree(GREEN_OAK_LOG, GREEN_OAK_WOOD, GREEN_OAK_BRANCH, GREEN_OAK_LEAVES);
         RED_OAK_PARTY = buildMirkPartyTree(GREEN_OAK_LOG, GREEN_OAK_WOOD, GREEN_OAK_BRANCH, RED_OAK_LEAVES);
         GREEN_OAK_SHRUB = buildShrub(GREEN_OAK_LOG, GREEN_OAK_LEAVES);
         CYPRESS_LOG = ((Block)LOTRBlocks.CYPRESS_LOG.get()).func_176223_P();
         CYPRESS_LEAVES = ((Block)LOTRBlocks.CYPRESS_LEAVES.get()).func_176223_P();
         CYPRESS_SAPLING = (IPlantable)LOTRBlocks.CYPRESS_SAPLING.get();
         CYPRESS_TREE = (new Builder(new SimpleBlockStateProvider(CYPRESS_LOG), new SimpleBlockStateProvider(CYPRESS_LEAVES), new CypressFoliagePlacer(FeatureSpread.func_242252_a(1), FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(3, 1)), new StraightTrunkPlacer(8, 5, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_().func_225568_b_();
         CULUMALDA_LOG = ((Block)LOTRBlocks.CULUMALDA_LOG.get()).func_176223_P();
         CULUMALDA_LEAVES = ((Block)LOTRBlocks.CULUMALDA_LEAVES.get()).func_176223_P();
         CULUMALDA_SAPLING = (IPlantable)LOTRBlocks.CULUMALDA_SAPLING.get();
         CULUMALDA_TREE = buildClassicTreeWithSpecifiedFoliage(CULUMALDA_LOG, new SimpleBlockStateProvider(CULUMALDA_LEAVES), 5, 4, new CulumaldaFoliagePlacer(FeatureSpread.func_242253_a(3, 0), FeatureSpread.func_242252_a(0), 4), false, false);
         CULUMALDA_TREE_BEES = buildClassicTreeWithSpecifiedFoliage(CULUMALDA_LOG, new SimpleBlockStateProvider(CULUMALDA_LEAVES), 5, 4, new CulumaldaFoliagePlacer(FeatureSpread.func_242253_a(3, 0), FeatureSpread.func_242252_a(0), 4), true, false);
         SIMBELMYNE = ((Block)LOTRBlocks.SIMBELMYNE.get()).func_176223_P();
         ATHELAS = ((Block)LOTRBlocks.ATHELAS.get()).func_176223_P();
         WILD_PIPEWEED = ((Block)LOTRBlocks.WILD_PIPEWEED.get()).func_176223_P();
         SIMBELMYNE_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SIMBELMYNE), new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
         ATHELAS_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ATHELAS), new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
         WILD_PIPEWEED_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(WILD_PIPEWEED), new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
         LILAC = Blocks.field_196801_ge.func_176223_P();
         ROSE_BUSH = Blocks.field_196802_gf.func_176223_P();
         PEONY = Blocks.field_196803_gg.func_176223_P();
         SUNFLOWER = Blocks.field_196800_gd.func_176223_P();
         HIBISCUS = ((Block)LOTRBlocks.HIBISCUS.get()).func_176223_P();
         FLAME_OF_HARAD = ((Block)LOTRBlocks.FLAME_OF_HARAD.get()).func_176223_P();
         LILAC_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(LILAC), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         ROSE_BUSH_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ROSE_BUSH), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         PEONY_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(PEONY), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         SUNFLOWER_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SUNFLOWER), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         HIBISCUS_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(HIBISCUS), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         FLAME_OF_HARAD_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(FLAME_OF_HARAD), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         DEAD_BUSH = Blocks.field_196555_aI.func_176223_P();
         DEAD_BUSH_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DEAD_BUSH), new SimpleBlockPlacer())).func_227315_a_(4).func_227322_d_();
         CACTUS = Blocks.field_150434_aF.func_176223_P();
         CACTUS_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(CACTUS), new ColumnBlockPlacer(1, 2))).func_227315_a_(10).func_227317_b_().func_227322_d_();
         SAND = Blocks.field_150354_m.func_176223_P();
         RED_SAND = Blocks.field_196611_F.func_176223_P();
         WHITE_SAND = ((Block)LOTRBlocks.WHITE_SAND.get()).func_176223_P();
         CLAY = Blocks.field_150435_aG.func_176223_P();
         QUAGMIRE = ((Block)LOTRBlocks.QUAGMIRE.get()).func_176223_P();
         GRASS_BLOCK = Blocks.field_196658_i.func_176223_P();
         BROWN_MUSHROOM = Blocks.field_150338_P.func_176223_P();
         RED_MUSHROOM = Blocks.field_150337_Q.func_176223_P();
         MIRK_SHROOM = ((Block)LOTRBlocks.MIRK_SHROOM.get()).func_176223_P();
         BROWN_MUSHROOM_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BROWN_MUSHROOM), new SimpleBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         RED_MUSHROOM_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RED_MUSHROOM), new SimpleBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         MIRK_SHROOM_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MIRK_SHROOM), new SimpleBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         SUGAR_CANE = Blocks.field_196608_cF.func_176223_P();
         SUGAR_CANE_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SUGAR_CANE), new ColumnBlockPlacer(2, 2))).func_227315_a_(20).func_227318_b_(4).func_227321_c_(0).func_227323_d_(4).func_227317_b_().func_227320_c_().func_227322_d_();
         REEDS = ((Block)LOTRBlocks.REEDS.get()).func_176223_P();
         DRIED_REEDS = ((Block)LOTRBlocks.DRIED_REEDS.get()).func_176223_P();
         REEDS_CONFIG_FOR_DRIED_CHANCE = (driedChance) -> {
            float freshChance = 1.0F - driedChance;
            int weight = 1000;
            WeightedBlockStateProvider blockProv = (new WeightedBlockStateProvider()).func_227407_a_(REEDS, (int)(freshChance * (float)weight)).func_227407_a_(DRIED_REEDS, (int)(driedChance * (float)weight));
            return new ReedsFeatureConfig(blockProv, 32, 5, 2, 5, 0.75F);
         };
         PAPYRUS = ((Block)LOTRBlocks.PAPYRUS.get()).func_176223_P();
         PAPYRUS_CONFIG = new ReedsFeatureConfig(new SimpleBlockStateProvider(PAPYRUS), 32, 5, 2, 5, 0.75F);
         RUSHES = ((Block)LOTRBlocks.RUSHES.get()).func_176223_P();
         RUSHES_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(RUSHES), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227314_a_().func_227322_d_();
         PUMPKIN = Blocks.field_150423_aK.func_176223_P();
         PUMPKIN_PATCH_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(PUMPKIN), new SimpleBlockPlacer())).func_227315_a_(64).func_227316_a_(ImmutableSet.of(GRASS_BLOCK.func_177230_c())).func_227317_b_().func_227322_d_();
         LILY_PAD = Blocks.field_196651_dG.func_176223_P();
         LILY_PAD_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(LILY_PAD), new SimpleBlockPlacer())).func_227315_a_(10).func_227322_d_();
         WHITE_WATER_LILY = ((Block)LOTRBlocks.WHITE_WATER_LILY.get()).func_176223_P();
         YELLOW_WATER_LILY = ((Block)LOTRBlocks.YELLOW_WATER_LILY.get()).func_176223_P();
         PURPLE_WATER_LILY = ((Block)LOTRBlocks.PURPLE_WATER_LILY.get()).func_176223_P();
         PINK_WATER_LILY = ((Block)LOTRBlocks.PINK_WATER_LILY.get()).func_176223_P();
         LILY_PAD_WITH_FLOWERS_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).func_227407_a_(LILY_PAD, 5).func_227407_a_(WHITE_WATER_LILY, 1).func_227407_a_(YELLOW_WATER_LILY, 1).func_227407_a_(PURPLE_WATER_LILY, 1).func_227407_a_(PINK_WATER_LILY, 1), new SimpleBlockPlacer())).func_227315_a_(10).func_227322_d_();
         LILY_PAD_WITH_RARE_FLOWERS_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).func_227407_a_(LILY_PAD, 80).func_227407_a_(WHITE_WATER_LILY, 1).func_227407_a_(YELLOW_WATER_LILY, 1).func_227407_a_(PURPLE_WATER_LILY, 1).func_227407_a_(PINK_WATER_LILY, 1), new SimpleBlockPlacer())).func_227315_a_(10).func_227322_d_();
         SPONGE = Blocks.field_196577_ad.func_176223_P();
         SPONGE_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SPONGE), new SimpleBlockPlacer())).func_227315_a_(64).func_227316_a_(ImmutableSet.of(SAND.func_177230_c(), GRAVEL.func_177230_c())).func_227314_a_().func_227317_b_().func_227322_d_();
         SWEET_BERRY_BUSH = (BlockState)Blocks.field_222434_lW.func_176223_P().func_206870_a(SweetBerryBushBlock.field_220125_a, 3);
         SWEET_BERRY_BUSH_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SWEET_BERRY_BUSH), new SimpleBlockPlacer())).func_227315_a_(64).func_227316_a_(ImmutableSet.of(GRASS_BLOCK.func_177230_c())).func_227317_b_().func_227322_d_();
         MORDOR_MOSS = ((Block)LOTRBlocks.MORDOR_MOSS.get()).func_176223_P();
         MORDOR_MOSS_CONFIG = new MordorMossFeatureConfig(MORDOR_MOSS, 32, 80);
         MORDOR_GRASS = ((Block)LOTRBlocks.MORDOR_GRASS.get()).func_176223_P();
         MORDOR_GRASS_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MORDOR_GRASS), new SimpleBlockPlacer())).func_227315_a_(32).func_227322_d_();
         MORDOR_THORN = ((Block)LOTRBlocks.MORDOR_THORN.get()).func_176223_P();
         MORDOR_THORN_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MORDOR_THORN), new SimpleBlockPlacer())).func_227318_b_(6).func_227321_c_(2).func_227323_d_(6).func_227315_a_(160).func_227322_d_();
         MORGUL_SHROOM = ((Block)LOTRBlocks.MORGUL_SHROOM.get()).func_176223_P();
         MORGUL_SHROOM_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MORGUL_SHROOM), new SimpleBlockPlacer())).func_227315_a_(64).func_227317_b_().func_227322_d_();
         MORGUL_FLOWER = ((Block)LOTRBlocks.MORGUL_FLOWER.get()).func_176223_P();
         MORGUL_FLOWER_CONFIG = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(MORGUL_FLOWER), new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
         Set springStoneSet = ImmutableSet.of(Blocks.field_150348_b, Blocks.field_196650_c, Blocks.field_196654_e, Blocks.field_196656_g, LOTRBlocks.GONDOR_ROCK.get(), LOTRBlocks.MORDOR_ROCK.get(), new Block[]{(Block)LOTRBlocks.ROHAN_ROCK.get(), (Block)LOTRBlocks.BLUE_ROCK.get(), (Block)LOTRBlocks.RED_ROCK.get(), (Block)LOTRBlocks.CHALK.get(), (Block)LOTRBlocks.DIRTY_CHALK.get()});
         WATER_SPRING_CONFIG = new LiquidsConfig(Fluids.field_204546_a.func_207188_f(), true, 4, 1, springStoneSet);
         LAVA_SPRING_CONFIG = new LiquidsConfig(Fluids.field_204547_b.func_207188_f(), true, 4, 1, springStoneSet);
      }
   }

   public static void addCarvers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      float caveChance = 0.14285715F;
      float canyonChance = 0.02F;
      addCarvers(builder, caveChance, canyonChance);
   }

   public static void addCarversExtraCanyons(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      float caveChance = 0.14285715F;
      float canyonChance = 0.2F;
      addCarvers(builder, caveChance, canyonChance);
   }

   public static void addCarvers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, float caveChance, float canyonChance) {
      builder.func_242512_a(Carving.AIR, LOTRWorldCarvers.CAVE.func_242761_a(new ProbabilityConfig(caveChance)));
      builder.func_242512_a(Carving.AIR, LOTRWorldCarvers.CANYON.func_242761_a(new ProbabilityConfig(canyonChance)));
   }

   public static void addSeaCarvers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242512_a(Carving.AIR, LOTRWorldCarvers.CAVE.func_242761_a(new ProbabilityConfig(0.06666667F)));
      builder.func_242512_a(Carving.AIR, LOTRWorldCarvers.CANYON.func_242761_a(new ProbabilityConfig(0.02F)));
      builder.func_242512_a(Carving.LIQUID, LOTRWorldCarvers.UNDERWATER_CAVE.func_242761_a(new ProbabilityConfig(0.06666667F)));
      builder.func_242512_a(Carving.LIQUID, LOTRWorldCarvers.UNDERWATER_CANYON.func_242761_a(new ProbabilityConfig(0.02F)));
   }

   public static void addDirtGravel(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, DIRT, 33)).func_242733_d(256)).func_242728_a()).func_242731_b(10));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, GRAVEL, 33)).func_242733_d(256)).func_242728_a()).func_242731_b(8));
   }

   public static void addMordorDirtGravel(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(MORDOR_ROCK_FILLER, MORDOR_DIRT, 61)).func_242733_d(256)).func_242728_a()).func_242731_b(10));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(MORDOR_ROCK_FILLER, MORDOR_GRAVEL, 33)).func_242733_d(256)).func_242728_a()).func_242731_b(10));
   }

   public static void addAndesite(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addStoneVariety(builder, ANDESITE, 10, 80);
   }

   public static void addDiorite(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addStoneVariety(builder, DIORITE, 5, 80);
   }

   public static void addDeepDiorite(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addStoneVariety(builder, DIORITE, 5, 32);
   }

   public static void addGranite(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addStoneVariety(builder, GRANITE, 5, 80);
   }

   public static void addCommonGranite(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addStoneVariety(builder, GRANITE, 12, 96);
   }

   private static void addStoneVariety(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, BlockState stone, int freq, int height) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, stone, 61)).func_242733_d(height)).func_242728_a()).func_242731_b(freq));
   }

   public static void addGondorRockPatches(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, GONDOR_ROCK, 61)).func_242733_d(80)).func_242728_a()).func_242731_b(4));
   }

   public static void addRohanRockPatches(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, ROHAN_ROCK, 61)).func_242733_d(80)).func_242728_a()).func_242731_b(4));
   }

   public static void addBlueRockPatches(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, BLUE_ROCK, 61)).func_242733_d(96)).func_242728_a()).func_242731_b(6));
   }

   public static void addOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, COAL_ORE, 17)).func_242733_d(128)).func_242728_a()).func_242731_b(20));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, IRON_ORE, 9)).func_242733_d(64)).func_242728_a()).func_242731_b(20));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, COPPER_ORE, 9)).func_242733_d(128)).func_242728_a()).func_242731_b(16));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, TIN_ORE, 9)).func_242733_d(128)).func_242728_a()).func_242731_b(16));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, GOLD_ORE, 9)).func_242733_d(32)).func_242728_a()).func_242731_b(2));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, SILVER_ORE, 9)).func_242733_d(32)).func_242728_a()).func_242731_b(3));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, SULFUR_ORE, 9)).func_242733_d(64)).func_242728_a()).func_242731_b(2));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, NITER_ORE, 9)).func_242733_d(64)).func_242728_a()).func_242731_b(2));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, SALT_ORE, 13)).func_242733_d(64)).func_242728_a()).func_242731_b(2));
   }

   public static void addExtraCoal(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int size, int freq, int height) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, COAL_ORE, size + 1)).func_242733_d(height)).func_242728_a()).func_242731_b(freq));
   }

   public static void addExtraIron(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int size, int freq, int height) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, COAL_ORE, size + 1)).func_242733_d(height)).func_242728_a()).func_242731_b(freq));
   }

   public static void addExtraGold(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int size, int freq, int height) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, GOLD_ORE, size + 1)).func_242733_d(height)).func_242728_a()).func_242731_b(freq));
   }

   public static void addExtraSilver(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int size, int freq, int height) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, SILVER_ORE, size + 1)).func_242733_d(height)).func_242728_a()).func_242731_b(freq));
   }

   public static void addExtraSalt(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int size, int freq, int height) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, SALT_ORE, size + 1)).func_242733_d(height)).func_242728_a()).func_242731_b(freq));
   }

   public static void addSaltInSand(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int size, int freq, int minHeight, int maxHeight) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(SAND_FILLER, SALT_ORE, size + 1)).func_227228_a_(Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(minHeight, minHeight, maxHeight))).func_242728_a()).func_242731_b(freq));
   }

   public static void addMordorOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(MORDOR_ROCK_FILLER, DURNOR_ORE, 13)).func_242733_d(64)).func_242728_a()).func_242731_b(20));
      addMorgulIronOre(builder, true);
      addGuldurilOre(builder, true);
   }

   public static void addStoneOrcishOres(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addMorgulIronOre(builder, false);
      addGuldurilOre(builder, false);
   }

   public static void addMorgulIronOre(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, boolean mordor) {
      int oreFreq = 20;
      ConfiguredPlacement orePlacement = Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, 64));
      OreFeatureConfig oreConfig = mordor ? new OreFeatureConfig(MORDOR_ROCK_FILLER, MORGUL_IRON_ORE_MORDOR, 9) : new OreFeatureConfig(FillerBlockType.field_241882_a, MORGUL_IRON_ORE_STONE, 9);
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(oreConfig).func_227228_a_(orePlacement).func_242728_a()).func_242731_b(oreFreq));
   }

   public static void addGuldurilOre(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, boolean mordor) {
      int oreFreq = 4;
      int crystalFreq = 2;
      addGuldurilOre(builder, mordor, oreFreq, crystalFreq, 32);
   }

   private static void addGuldurilOre(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, boolean mordor, int oreFreq, int crystalFreq, int topY) {
      ConfiguredPlacement orePlacement = Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, topY));
      ConfiguredPlacement crystalPlacement = Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, topY));
      OreFeatureConfig oreConfig = mordor ? new OreFeatureConfig(MORDOR_ROCK_FILLER, GULDURIL_ORE_MORDOR, 9) : new OreFeatureConfig(FillerBlockType.field_241882_a, GULDURIL_ORE_STONE, 9);
      CrystalFeatureConfig crystalConfig = new CrystalFeatureConfig(new SimpleBlockStateProvider(GULDURIL_CRYSTAL), 64, 6, 4, 6);
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(oreConfig).func_227228_a_(orePlacement).func_242728_a()).func_242731_b(oreFreq));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)LOTRFeatures.CRYSTALS.func_225566_b_(crystalConfig).func_227228_a_(crystalPlacement).func_242728_a()).func_242731_b(crystalFreq));
   }

   public static void addExtraMordorGulduril(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      int extraOreFreq = 10;
      int extraCrystalFreq = 1;
      addGuldurilOre(builder, true, extraOreFreq, extraCrystalFreq, 60);
   }

   public static void addLapisOre(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, LAPIS_ORE, 7)).func_227228_a_(Placement.field_242910_o.func_227446_a_(new DepthAverageConfig(16, 16))).func_242728_a());
   }

   public static void addMithrilOre(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int invChance) {
      ConfiguredPlacement orePlacement = Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, 16));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, MITHRIL_ORE, 7)).func_227228_a_(orePlacement).func_242729_a(invChance));
   }

   public static void addEdhelvirOre(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      int oreFreq = 4;
      int crystalFreq = 2;
      ConfiguredPlacement orePlacement = Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, 48));
      ConfiguredPlacement crystalPlacement = Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, 48));
      OreFeatureConfig oreConfig = new OreFeatureConfig(FillerBlockType.field_241882_a, EDHELVIR_ORE, 7);
      CrystalFeatureConfig crystalConfig = new CrystalFeatureConfig(new SimpleBlockStateProvider(EDHELVIR_CRYSTAL), 64, 6, 4, 6);
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(oreConfig).func_227228_a_(orePlacement).func_242728_a()).func_242731_b(oreFreq));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)LOTRFeatures.CRYSTALS.func_225566_b_(crystalConfig).func_227228_a_(crystalPlacement).func_242728_a()).func_242731_b(crystalFreq));
   }

   public static void addGlowstoneOre(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      int oreFreq = 6;
      int crystalFreq = 2;
      ConfiguredPlacement orePlacement = Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, 48));
      ConfiguredPlacement crystalPlacement = Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, 48));
      OreFeatureConfig oreConfig = new OreFeatureConfig(FillerBlockType.field_241882_a, GLOWSTONE_ORE, 5);
      CrystalFeatureConfig crystalConfig = new CrystalFeatureConfig(new SimpleBlockStateProvider(GLOWSTONE_CRYSTAL), 64, 6, 4, 6);
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(oreConfig).func_227228_a_(orePlacement).func_242728_a()).func_242731_b(oreFreq));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)LOTRFeatures.CRYSTALS.func_225566_b_(crystalConfig).func_227228_a_(crystalPlacement).func_242728_a()).func_242731_b(crystalFreq));
   }

   public static void addPackedIceVeins(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      int size = 16;
      int yMin = 32;
      int yMax = 256;
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202290_aj.func_225566_b_(new OreFeatureConfig(FillerBlockType.field_241882_a, PACKED_ICE, size)).func_227228_a_(Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(yMin, yMin, yMax))).func_242728_a()).func_242731_b(freq));
   }

   public static void addDripstones(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addDripstones(builder, (DripstoneBlock)null);
   }

   public static void addDripstones(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, DripstoneBlock block) {
      addDripstones(builder, (DripstoneBlock)null, 3);
   }

   public static void addDripstones(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, DripstoneBlock block, int freq) {
      DripstoneFeatureConfig config = new DripstoneFeatureConfig(block == null ? null : block.func_176223_P(), 64, 8, 4, 8, 0.33F);
      builder.func_242513_a(Decoration.UNDERGROUND_DECORATION, (ConfiguredFeature)((ConfiguredFeature)LOTRFeatures.DRIPSTONE.func_225566_b_(config).func_227228_a_(Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, 60))).func_242728_a()).func_242731_b(freq));
   }

   public static void addCobwebs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addCobwebs(builder, 2, COBWEB);
   }

   public static void addCobwebs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int invChance, BlockState webBlock) {
      CobwebFeatureConfig config = new CobwebFeatureConfig(new SimpleBlockStateProvider(webBlock), 64, 6, 4, 6);
      builder.func_242513_a(Decoration.UNDERGROUND_DECORATION, (ConfiguredFeature)((ConfiguredFeature)LOTRFeatures.COBWEBS.func_225566_b_(config).func_227228_a_(Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, 60))).func_242728_a()).func_242729_a(invChance));
   }

   public static void addLakes(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      int waterChance = 4;
      int waterChance = waterChance * 2;
      int lavaChance = 8;
      int lavaChance = lavaChance * 2;
      builder.func_242513_a(Decoration.LOCAL_MODIFICATIONS, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202289_ai.func_225566_b_(new BlockStateFeatureConfig(WATER)).func_227228_a_(Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(0, 0, 62))).func_242728_a()).func_242729_a(waterChance));
      builder.func_242513_a(Decoration.LOCAL_MODIFICATIONS, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202289_ai.func_225566_b_(new BlockStateFeatureConfig(LAVA)).func_227228_a_(Placement.field_242908_m.func_227446_a_(new TopSolidRangeConfig(8, 8, 62))).func_242728_a()).func_242729_a(lavaChance));
   }

   public static void addSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addSandSediments(builder, SAND);
   }

   public static void addWhiteSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addSandSediments(builder, WHITE_SAND);
   }

   public static void addSandSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, BlockState sandBlock) {
      int freq = 3;
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, Feature.field_202285_ae.func_225566_b_(new SphereReplaceConfig(sandBlock, FeatureSpread.func_242253_a(2, 4), 2, Lists.newArrayList(new BlockState[]{DIRT, COARSE_DIRT, GRASS_BLOCK}))).func_227228_a_(placeTopSolidFreq(freq)));
   }

   public static void addClayGravelSediments(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, Feature.field_202285_ae.func_225566_b_(new SphereReplaceConfig(CLAY, FeatureSpread.func_242253_a(2, 1), 1, Lists.newArrayList(new BlockState[]{DIRT, COARSE_DIRT, CLAY}))).func_227228_a_(placeTopSolidFreq(1)));
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, Feature.field_202285_ae.func_225566_b_(new SphereReplaceConfig(GRAVEL, FeatureSpread.func_242253_a(2, 3), 2, Lists.newArrayList(new BlockState[]{DIRT, COARSE_DIRT, GRASS_BLOCK}))).func_227228_a_(placeTopSolidFreq(1)));
   }

   public static void addQuagmire(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.UNDERGROUND_ORES, Feature.field_202285_ae.func_225566_b_(new SphereReplaceConfig(QUAGMIRE, FeatureSpread.func_242253_a(2, 2), 2, Lists.newArrayList(new BlockState[]{DIRT, COARSE_DIRT, GRASS_BLOCK}))).func_227228_a_(placeTopSolidFreq(freq)));
   }

   public static void addTrees(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int count, float extraChance, Object... weightedTrees) {
      addTreesFromGeneratingConfig(biome, builder, GeneratingTreesConfig.builder().weightedTrees(weightedTrees).clusterConfig(TreeClustersConfig.builder().count(count).extraChance(extraChance).build()).build());
   }

   public static void addTreesWithClusters(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int count, float extraChance, TreeCluster cluster, Object... weightedTrees) {
      addTreesFromGeneratingConfig(biome, builder, GeneratingTreesConfig.builder().weightedTrees(weightedTrees).clusterConfig(TreeClustersConfig.builder().count(count).extraChance(extraChance).cluster(cluster).build()).build());
   }

   public static void addTreesWithLatitudeConfig(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, LatitudeBasedFeatureConfig.LatitudeConfiguration latitudeConfig, int count, float extraChance, Object... weightedTrees) {
      addTreesFromGeneratingConfig(biome, builder, GeneratingTreesConfig.builder().weightedTrees(weightedTrees).clusterConfig(TreeClustersConfig.builder().count(count).extraChance(extraChance).build()).latitudeConfig(latitudeConfig).build());
   }

   public static void addTreesIncrease(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int count, float extraChance, int extraCount, Object... weightedTrees) {
      addTreesFromGeneratingConfig(biome, builder, GeneratingTreesConfig.builder().weightedTrees(weightedTrees).clusterConfig(TreeClustersConfig.builder().count(count).extraChance(extraChance).extraCount(extraCount).build()).build());
   }

   public static void addTreesBelowTreeline(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int count, float extraChance, int treeline, Object... weightedTrees) {
      addTreesFromGeneratingConfig(biome, builder, GeneratingTreesConfig.builder().weightedTrees(weightedTrees).clusterConfig(TreeClustersConfig.builder().count(count).extraChance(extraChance).layerLimit(treeline, true).build()).build());
   }

   public static void addTreesBelowTreelineIncrease(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int count, float extraChance, int extraCount, int treeline, Object... weightedTrees) {
      addTreesFromGeneratingConfig(biome, builder, GeneratingTreesConfig.builder().weightedTrees(weightedTrees).clusterConfig(TreeClustersConfig.builder().count(count).extraChance(extraChance).extraCount(extraCount).layerLimit(treeline, true).build()).build());
   }

   public static void addTreesAboveTreeline(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int count, float extraChance, int treeline, Object... weightedTrees) {
      addTreesFromGeneratingConfig(biome, builder, GeneratingTreesConfig.builder().weightedTrees(weightedTrees).clusterConfig(TreeClustersConfig.builder().count(count).extraChance(extraChance).layerLimit(treeline, false).build()).build());
   }

   public static void addTreesAboveTreelineIncrease(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int count, float extraChance, int extraCount, int treeline, Object... weightedTrees) {
      addTreesFromGeneratingConfig(biome, builder, GeneratingTreesConfig.builder().weightedTrees(weightedTrees).clusterConfig(TreeClustersConfig.builder().count(count).extraChance(extraChance).extraCount(extraCount).layerLimit(treeline, false).build()).build());
   }

   public static void addTreesFromGeneratingConfig(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, GeneratingTreesConfig config) {
      ConfiguredPlacement treePlacement = LOTRFeatures.TREE_CLUSTERS.func_227446_a_(config.clusterConfig);
      addTreesWithPlacement(builder, treePlacement, config.latitudeConfig, config.weightedTrees);
      if (config.shouldUpdateBiomeTreeAmount()) {
         biome.updateBiomePodzolVariables(config.getTreeCountApproximation(), config.getTreeLayerUpperLimit());
      }

      addLeafBushesWithPlacement(builder, LOTRFeatures.TREE_CLUSTERS.func_227446_a_(config.makePlacementForLeafBushes()), config.latitudeConfig);
      addFallenLeavesWithPlacement(builder, LOTRFeatures.TREE_CLUSTERS.func_227446_a_(config.makePlacementForFallenLeaves()), config.latitudeConfig);
   }

   private static void addTreesWithPlacement(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, ConfiguredPlacement placement, LatitudeBasedFeatureConfig.LatitudeConfiguration latConfig, Object... weightedTrees) {
      WeightedRandomFeatureConfig wrConfig = WeightedRandomFeatureConfig.fromEntries(weightedTrees);
      ConfiguredFeature randomTreeFeature = LOTRFeatures.WEIGHTED_RANDOM.func_225566_b_(wrConfig);
      if (latConfig == null) {
         builder.func_242513_a(Decoration.VEGETAL_DECORATION, randomTreeFeature.func_227228_a_(placement));
      } else {
         addLatitudeBased(builder, Decoration.VEGETAL_DECORATION, randomTreeFeature, placement, latConfig);
      }

   }

   private static void addLeafBushesWithPlacement(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, ConfiguredPlacement placement, LatitudeBasedFeatureConfig.LatitudeConfiguration latConfig) {
      ConfiguredFeature bushesFeature = LOTRFeatures.LEAF_BUSHES.func_225566_b_(IFeatureConfig.field_202429_e);
      if (latConfig == null) {
         builder.func_242513_a(Decoration.VEGETAL_DECORATION, bushesFeature.func_227228_a_(placement));
      } else {
         addLatitudeBased(builder, Decoration.VEGETAL_DECORATION, bushesFeature, placement, latConfig);
      }

   }

   private static void addFallenLeavesWithPlacement(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, ConfiguredPlacement placement, LatitudeBasedFeatureConfig.LatitudeConfiguration latConfig) {
      ConfiguredFeature fallenLeavesFeature = LOTRFeatures.FALLEN_LEAVES.func_225566_b_(IFeatureConfig.field_202429_e);
      if (latConfig == null) {
         builder.func_242513_a(Decoration.VEGETAL_DECORATION, fallenLeavesFeature.func_227228_a_(placement));
      } else {
         addLatitudeBased(builder, Decoration.VEGETAL_DECORATION, fallenLeavesFeature, placement, latConfig);
      }

   }

   public static ConfiguredFeature snowWrapTree(ConfiguredFeature tree) {
      IFeatureConfig config = tree.func_242767_c();
      BaseTreeFeatureConfig treeConfig;
      if (config instanceof BaseTreeFeatureConfig) {
         treeConfig = (BaseTreeFeatureConfig)config;
      } else {
         if (!(config instanceof WrappedTreeFeatureConfig)) {
            throw new IllegalArgumentException("Cannot wrap the supplied ConfiguredFeature type (" + tree + ") in a snowy wrapped tree config");
         }

         treeConfig = ((WrappedTreeFeatureConfig)config).treeConfig;
      }

      return LOTRFeatures.WRAPPED_TREE.func_225566_b_(new WrappedTreeFeatureConfig(treeConfig, WrappedTreeFeatureConfig.AlternativeTreeSoil.SNOWY));
   }

   public static ConfiguredFeature oak() {
      return Features.field_243862_bH;
   }

   public static ConfiguredFeature oakBees() {
      return Features.field_243879_bY;
   }

   public static ConfiguredFeature oakVines() {
      return Feature.field_236291_c_.func_225566_b_(OAK_TREE_VINES);
   }

   public static ConfiguredFeature oakBeesVines() {
      return Feature.field_236291_c_.func_225566_b_(OAK_TREE_BEES_VINES);
   }

   public static ConfiguredFeature oakTall() {
      return Feature.field_236291_c_.func_225566_b_(OAK_TREE_TALL);
   }

   public static ConfiguredFeature oakTallBees() {
      return Feature.field_236291_c_.func_225566_b_(OAK_TREE_TALL_BEES);
   }

   public static ConfiguredFeature oakTallVines() {
      return Feature.field_236291_c_.func_225566_b_(OAK_TREE_TALL_VINES);
   }

   public static ConfiguredFeature oakTallBeesVines() {
      return Feature.field_236291_c_.func_225566_b_(OAK_TREE_TALL_BEES_VINES);
   }

   public static ConfiguredFeature oakFancy() {
      return Features.field_243869_bO;
   }

   public static ConfiguredFeature oakFancyBees() {
      return Features.field_243922_ce;
   }

   public static ConfiguredFeature oakSwamp() {
      return Features.field_243875_bU;
   }

   public static ConfiguredFeature oakDesert() {
      return LOTRFeatures.WRAPPED_TREE.func_225566_b_(OAK_DESERT);
   }

   public static ConfiguredFeature oakDesertBees() {
      return LOTRFeatures.WRAPPED_TREE.func_225566_b_(OAK_DESERT_BEES);
   }

   public static ConfiguredFeature oakDead() {
      return LOTRFeatures.WRAPPED_TREE.func_225566_b_(OAK_DEAD);
   }

   public static ConfiguredFeature oakParty() {
      return Feature.field_236291_c_.func_225566_b_(OAK_PARTY);
   }

   public static ConfiguredFeature oakFangorn() {
      return Feature.field_236291_c_.func_225566_b_(OAK_FANGORN);
   }

   public static ConfiguredFeature oakShrub() {
      return Feature.field_236291_c_.func_225566_b_(OAK_SHRUB);
   }

   public static ConfiguredFeature spruce() {
      return Features.field_243866_bL;
   }

   public static ConfiguredFeature spruceThin() {
      return Features.field_243867_bM;
   }

   public static ConfiguredFeature spruceMega() {
      return Features.field_243872_bR;
   }

   public static ConfiguredFeature spruceThinMega() {
      return Features.field_243873_bS;
   }

   public static ConfiguredFeature spruceDead() {
      return LOTRFeatures.WRAPPED_TREE.func_225566_b_(SPRUCE_DEAD);
   }

   public static ConfiguredFeature spruceShrub() {
      return Feature.field_236291_c_.func_225566_b_(SPRUCE_SHRUB);
   }

   public static ConfiguredFeature birch() {
      return LOTRFeatures.getWeightedRandom().func_225566_b_(WeightedRandomFeatureConfig.fromEntries(Features.field_243864_bJ, 1, Feature.field_236291_c_.func_225566_b_(BIRCH_TREE_ALT), 2));
   }

   public static ConfiguredFeature birchBees() {
      return LOTRFeatures.getWeightedRandom().func_225566_b_(WeightedRandomFeatureConfig.fromEntries(Features.field_243919_cb, 1, Feature.field_236291_c_.func_225566_b_(BIRCH_TREE_ALT_BEES), 2));
   }

   public static ConfiguredFeature birchFancy() {
      return Feature.field_236291_c_.func_225566_b_(BIRCH_TREE_FANCY);
   }

   public static ConfiguredFeature birchFancyBees() {
      return Feature.field_236291_c_.func_225566_b_(BIRCH_TREE_FANCY_BEES);
   }

   public static ConfiguredFeature birchDead() {
      return LOTRFeatures.WRAPPED_TREE.func_225566_b_(BIRCH_DEAD);
   }

   public static ConfiguredFeature birchParty() {
      return Feature.field_236291_c_.func_225566_b_(BIRCH_PARTY);
   }

   public static ConfiguredFeature jungle() {
      return Features.field_243868_bN;
   }

   public static ConfiguredFeature jungleMega() {
      return Features.field_243871_bQ;
   }

   public static ConfiguredFeature jungleShrub() {
      return Features.field_243876_bV;
   }

   public static ConfiguredFeature acacia() {
      return Features.field_243865_bK;
   }

   public static ConfiguredFeature darkOak() {
      return Features.field_243863_bI;
   }

   public static ConfiguredFeature darkOakParty() {
      return Feature.field_236291_c_.func_225566_b_(DARK_OAK_PARTY);
   }

   public static ConfiguredFeature darkOakShrub() {
      return Feature.field_236291_c_.func_225566_b_(DARK_OAK_SHRUB);
   }

   public static ConfiguredFeature pine() {
      return Feature.field_236291_c_.func_225566_b_(PINE_TREE);
   }

   public static ConfiguredFeature shirePine() {
      return Feature.field_236291_c_.func_225566_b_(SHIRE_PINE_TREE);
   }

   public static ConfiguredFeature pineDead() {
      return Feature.field_236291_c_.func_225566_b_(PINE_DEAD);
   }

   public static ConfiguredFeature pineShrub() {
      return Feature.field_236291_c_.func_225566_b_(PINE_SHRUB);
   }

   public static ConfiguredFeature mallorn() {
      return Feature.field_236291_c_.func_225566_b_(MALLORN_TREE);
   }

   public static ConfiguredFeature mallornBees() {
      return Feature.field_236291_c_.func_225566_b_(MALLORN_TREE_BEES);
   }

   public static ConfiguredFeature mallornBoughs() {
      return Feature.field_236291_c_.func_225566_b_(MALLORN_TREE_BOUGHS);
   }

   public static ConfiguredFeature mallornParty() {
      return Feature.field_236291_c_.func_225566_b_(MALLORN_PARTY);
   }

   public static ConfiguredFeature mirkOak() {
      return Feature.field_236291_c_.func_225566_b_(MIRK_OAK_TREE);
   }

   public static ConfiguredFeature mirkOakParty() {
      return Feature.field_236291_c_.func_225566_b_(MIRK_OAK_PARTY);
   }

   public static ConfiguredFeature mirkOakShrub() {
      return Feature.field_236291_c_.func_225566_b_(MIRK_OAK_SHRUB);
   }

   public static ConfiguredFeature charred() {
      return LOTRFeatures.WRAPPED_TREE.func_225566_b_(CHARRED_TREE);
   }

   public static ConfiguredFeature apple() {
      return LOTRFeatures.getWeightedRandom().func_225566_b_(WeightedRandomFeatureConfig.fromEntries(Feature.field_236291_c_.func_225566_b_(APPLE_TREE_RED), 19, Feature.field_236291_c_.func_225566_b_(APPLE_TREE_GREEN), 19, Feature.field_236291_c_.func_225566_b_(APPLE_TREE_MIX), 2));
   }

   public static ConfiguredFeature appleBees() {
      return LOTRFeatures.getWeightedRandom().func_225566_b_(WeightedRandomFeatureConfig.fromEntries(Feature.field_236291_c_.func_225566_b_(APPLE_TREE_RED_BEES), 19, Feature.field_236291_c_.func_225566_b_(APPLE_TREE_GREEN_BEES), 19, Feature.field_236291_c_.func_225566_b_(APPLE_TREE_MIX_BEES), 2));
   }

   public static ConfiguredFeature pear() {
      return Feature.field_236291_c_.func_225566_b_(PEAR_TREE);
   }

   public static ConfiguredFeature pearBees() {
      return Feature.field_236291_c_.func_225566_b_(PEAR_TREE_BEES);
   }

   public static ConfiguredFeature cherry() {
      return Feature.field_236291_c_.func_225566_b_(CHERRY_TREE);
   }

   public static ConfiguredFeature cherryBees() {
      return Feature.field_236291_c_.func_225566_b_(CHERRY_TREE_BEES);
   }

   public static ConfiguredFeature lebethron() {
      return Feature.field_236291_c_.func_225566_b_(LEBETHRON_TREE);
   }

   public static ConfiguredFeature lebethronBees() {
      return Feature.field_236291_c_.func_225566_b_(LEBETHRON_TREE_BEES);
   }

   public static ConfiguredFeature lebethronFancy() {
      return Feature.field_236291_c_.func_225566_b_(LEBETHRON_TREE_FANCY);
   }

   public static ConfiguredFeature lebethronFancyBees() {
      return Feature.field_236291_c_.func_225566_b_(LEBETHRON_TREE_FANCY_BEES);
   }

   public static ConfiguredFeature lebethronParty() {
      return Feature.field_236291_c_.func_225566_b_(LEBETHRON_PARTY);
   }

   public static ConfiguredFeature beech() {
      return Feature.field_236291_c_.func_225566_b_(BEECH_TREE);
   }

   public static ConfiguredFeature beechBees() {
      return Feature.field_236291_c_.func_225566_b_(BEECH_TREE_BEES);
   }

   public static ConfiguredFeature beechFancy() {
      return Feature.field_236291_c_.func_225566_b_(BEECH_TREE_FANCY);
   }

   public static ConfiguredFeature beechFancyBees() {
      return Feature.field_236291_c_.func_225566_b_(BEECH_TREE_FANCY_BEES);
   }

   public static ConfiguredFeature beechParty() {
      return Feature.field_236291_c_.func_225566_b_(BEECH_PARTY);
   }

   public static ConfiguredFeature beechFangorn() {
      return Feature.field_236291_c_.func_225566_b_(BEECH_FANGORN);
   }

   public static ConfiguredFeature beechDead() {
      return LOTRFeatures.WRAPPED_TREE.func_225566_b_(BEECH_DEAD);
   }

   public static ConfiguredFeature maple() {
      return Feature.field_236291_c_.func_225566_b_(MAPLE_TREE);
   }

   public static ConfiguredFeature mapleBees() {
      return Feature.field_236291_c_.func_225566_b_(MAPLE_TREE_BEES);
   }

   public static ConfiguredFeature mapleFancy() {
      return Feature.field_236291_c_.func_225566_b_(MAPLE_TREE_FANCY);
   }

   public static ConfiguredFeature mapleFancyBees() {
      return Feature.field_236291_c_.func_225566_b_(MAPLE_TREE_FANCY_BEES);
   }

   public static ConfiguredFeature mapleParty() {
      return Feature.field_236291_c_.func_225566_b_(MAPLE_PARTY);
   }

   public static ConfiguredFeature aspen() {
      return Feature.field_236291_c_.func_225566_b_(ASPEN_TREE);
   }

   public static ConfiguredFeature aspenLarge() {
      return aspen();
   }

   public static ConfiguredFeature lairelosse() {
      return Feature.field_236291_c_.func_225566_b_(LAIRELOSSE_TREE);
   }

   public static ConfiguredFeature cedar() {
      return Feature.field_236291_c_.func_225566_b_(CEDAR_TREE);
   }

   public static ConfiguredFeature cedarLarge() {
      return Feature.field_236291_c_.func_225566_b_(CEDAR_TREE_LARGE);
   }

   public static ConfiguredFeature fir() {
      return Feature.field_236291_c_.func_225566_b_(FIR_TREE);
   }

   public static ConfiguredFeature firShrub() {
      return Feature.field_236291_c_.func_225566_b_(FIR_SHRUB);
   }

   public static ConfiguredFeature larch() {
      return Feature.field_236291_c_.func_225566_b_(LARCH_TREE);
   }

   public static ConfiguredFeature holly() {
      return Feature.field_236291_c_.func_225566_b_(HOLLY_TREE);
   }

   public static ConfiguredFeature hollyBees() {
      return Feature.field_236291_c_.func_225566_b_(HOLLY_TREE_BEES);
   }

   public static ConfiguredFeature greenOak() {
      return Feature.field_236291_c_.func_225566_b_(GREEN_OAK_TREE);
   }

   public static ConfiguredFeature greenOakBees() {
      return Feature.field_236291_c_.func_225566_b_(GREEN_OAK_TREE_BEES);
   }

   public static ConfiguredFeature redOak() {
      return Feature.field_236291_c_.func_225566_b_(RED_OAK_TREE);
   }

   public static ConfiguredFeature redOakBees() {
      return Feature.field_236291_c_.func_225566_b_(RED_OAK_TREE_BEES);
   }

   public static ConfiguredFeature greenOakParty() {
      return Feature.field_236291_c_.func_225566_b_(GREEN_OAK_PARTY);
   }

   public static ConfiguredFeature redOakParty() {
      return Feature.field_236291_c_.func_225566_b_(RED_OAK_PARTY);
   }

   public static ConfiguredFeature greenOakShrub() {
      return Feature.field_236291_c_.func_225566_b_(GREEN_OAK_SHRUB);
   }

   public static ConfiguredFeature cypress() {
      return Feature.field_236291_c_.func_225566_b_(CYPRESS_TREE);
   }

   public static ConfiguredFeature culumalda() {
      return Feature.field_236291_c_.func_225566_b_(CULUMALDA_TREE);
   }

   public static ConfiguredFeature culumaldaBees() {
      return Feature.field_236291_c_.func_225566_b_(CULUMALDA_TREE_BEES);
   }

   public static void addGrass(LOTRBiomeBase biome, net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, SingleGrassBlend blend) {
      WeightedRandomFeatureConfig wrGrassConfig = blend.getFeatureConfig();
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, LOTRFeatures.getWeightedRandom().func_225566_b_(wrGrassConfig).func_227228_a_(placeHeightmapDoubleFreq(freq)));
      biome.setGrassBonemealGenerator(wrGrassConfig);
   }

   public static void addDoubleGrass(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, DoubleGrassBlend blend) {
      WeightedRandomFeatureConfig wrGrassConfig = blend.getFeatureConfig();
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)LOTRFeatures.getWeightedRandom().func_225566_b_(wrGrassConfig).func_227228_a_(placeFlowers()).func_242731_b(freq));
   }

   public static void addFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... weightedFlowers) {
      try {
         WeightedBlockStateProvider stateProvider = new WeightedBlockStateProvider();

         for(int i = 0; i < weightedFlowers.length; i += 2) {
            Object obj1 = weightedFlowers[i];
            BlockState state;
            if (obj1 instanceof BlockState) {
               state = (BlockState)obj1;
            } else {
               state = ((Block)obj1).func_176223_P();
            }

            int weight = (Integer)weightedFlowers[i + 1];
            stateProvider.func_227407_a_(state, weight);
         }

         BlockClusterFeatureConfig flowerConfig = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(stateProvider, new SimpleBlockPlacer())).func_227315_a_(64).func_227322_d_();
         builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)Feature.field_227247_y_.func_225566_b_(flowerConfig).func_227228_a_(placeFlowers()).func_242731_b(freq));
      } catch (ArrayIndexOutOfBoundsException | ClassCastException var8) {
         throw new IllegalArgumentException("Error adding biome flowers! A list of (blockstate1, weight1), (blockstate2, weight2)... is required", var8);
      }
   }

   public static void addDefaultFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] defaultFlowers = new Object[]{Blocks.field_196606_bd, 1, Blocks.field_196605_bc, 2};
      addFlowers(builder, freq, LOTRUtil.combineVarargs(defaultFlowers, extraFlowers));
   }

   public static void addPlainsFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] plainsFlowers = new Object[]{Blocks.field_196606_bd, 20, Blocks.field_196605_bc, 30, Blocks.field_196610_bg, 20, Blocks.field_196616_bl, 20, Blocks.field_222387_by, 5, Blocks.field_196613_bi, 3, Blocks.field_196612_bh, 3, Blocks.field_196615_bk, 3, Blocks.field_196614_bj, 3, LOTRBlocks.BLUEBELL.get(), 5, LOTRBlocks.MARIGOLD.get(), 10, LOTRBlocks.LAVENDER.get(), 5};
      addFlowers(builder, freq, LOTRUtil.combineVarargs(plainsFlowers, extraFlowers));
   }

   public static void addForestFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] forestFlowers = new Object[]{Blocks.field_196606_bd, 10, Blocks.field_196605_bc, 20, Blocks.field_222383_bA, 2, LOTRBlocks.BLUEBELL.get(), 5, LOTRBlocks.MARIGOLD.get(), 10};
      addFlowers(builder, freq, LOTRUtil.combineVarargs(forestFlowers, extraFlowers));
   }

   public static void addMountainsFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] mountainsFlowers = new Object[]{Blocks.field_196606_bd, 10, Blocks.field_196605_bc, 20, Blocks.field_196607_be, 10, LOTRBlocks.BLUEBELL.get(), 5};
      addFlowers(builder, freq, LOTRUtil.combineVarargs(mountainsFlowers, extraFlowers));
   }

   public static void addBorealFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] borealFlowers = new Object[]{Blocks.field_196606_bd, 10, Blocks.field_196605_bc, 20, Blocks.field_196607_be, 10, LOTRBlocks.BLUEBELL.get(), 5};
      addFlowers(builder, freq, LOTRUtil.combineVarargs(borealFlowers, extraFlowers));
   }

   public static void addSwampFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      addDefaultFlowers(builder, freq, extraFlowers);
   }

   public static void addHaradFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] haradFlowers = new Object[]{LOTRBlocks.RED_SAND_GEM.get(), 5, LOTRBlocks.YELLOW_SAND_GEM.get(), 10, LOTRBlocks.HARAD_DAISY.get(), 5, LOTRBlocks.SOUTHBELL.get(), 5};
      addFlowers(builder, freq, LOTRUtil.combineVarargs(haradFlowers, extraFlowers));
   }

   public static void addRhunPlainsFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] rhunFlowers = new Object[]{LOTRBlocks.MARIGOLD.get(), 10, LOTRBlocks.WHITE_CHRYSANTHEMUM.get(), 10, LOTRBlocks.YELLOW_CHRYSANTHEMUM.get(), 10, LOTRBlocks.PINK_CHRYSANTHEMUM.get(), 10, LOTRBlocks.RED_CHRYSANTHEMUM.get(), 10, LOTRBlocks.ORANGE_CHRYSANTHEMUM.get(), 10};
      addPlainsFlowers(builder, freq, LOTRUtil.combineVarargs(rhunFlowers, extraFlowers));
   }

   public static void addRhunForestFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] rhunFlowers = new Object[]{LOTRBlocks.MARIGOLD.get(), 10, LOTRBlocks.WHITE_CHRYSANTHEMUM.get(), 10, LOTRBlocks.YELLOW_CHRYSANTHEMUM.get(), 10, LOTRBlocks.PINK_CHRYSANTHEMUM.get(), 10, LOTRBlocks.RED_CHRYSANTHEMUM.get(), 10, LOTRBlocks.ORANGE_CHRYSANTHEMUM.get(), 10};
      addForestFlowers(builder, freq, LOTRUtil.combineVarargs(rhunFlowers, extraFlowers));
   }

   public static void addSimbelmyneChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)Feature.field_227247_y_.func_225566_b_(SIMBELMYNE_CONFIG).func_227228_a_(placeFlowers()).func_242729_a(chance));
   }

   public static void addDoubleFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... weightedFlowers) {
      try {
         List weightedDoubleFlowerFeatures = new ArrayList();

         for(int i = 0; i < weightedFlowers.length; i += 2) {
            Object obj1 = weightedFlowers[i];
            BlockState state;
            if (obj1 instanceof BlockState) {
               state = (BlockState)obj1;
            } else {
               state = ((Block)obj1).func_176223_P();
            }

            int weight = (Integer)weightedFlowers[i + 1];
            net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder flowerConfigBuilder = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(state), new DoublePlantBlockPlacer())).func_227315_a_(64).func_227317_b_();
            if (state.func_177230_c() instanceof IWaterLoggable) {
               flowerConfigBuilder.func_227314_a_();
            }

            weightedDoubleFlowerFeatures.add(WeightedFeature.make(Feature.field_227248_z_.func_225566_b_(flowerConfigBuilder.func_227322_d_()), weight));
         }

         ConfiguredFeature doubleFlowerFeature = LOTRFeatures.WEIGHTED_RANDOM.func_225566_b_(new WeightedRandomFeatureConfig(weightedDoubleFlowerFeatures));
         builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)doubleFlowerFeature.func_227228_a_(placeFlowers()).func_242731_b(freq));
      } catch (ArrayIndexOutOfBoundsException | ClassCastException var9) {
         throw new IllegalArgumentException("Error adding biome double flowers! A list of (blockstate1, weight1), (blockstate2, weight2)... is required", var9);
      }
   }

   public static void addDefaultDoubleFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] defaultFlowers = new Object[]{Blocks.field_196801_ge, 10, Blocks.field_196802_gf, 10, Blocks.field_196803_gg, 10};
      addDoubleFlowers(builder, freq, LOTRUtil.combineVarargs(defaultFlowers, extraFlowers));
   }

   public static void addHaradDoubleFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, Object... extraFlowers) {
      Object[] haradFlowers = new Object[]{LOTRBlocks.HIBISCUS.get(), 10, LOTRBlocks.FLAME_OF_HARAD.get(), 2};
      addDoubleFlowers(builder, freq, LOTRUtil.combineVarargs(haradFlowers, extraFlowers));
   }

   public static void addSunflowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(SUNFLOWER_CONFIG).func_227228_a_(placeHeightmapChance(chance)));
   }

   public static void addAthelasChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addAthelasChance(builder, 30);
   }

   public static void addAthelasChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(ATHELAS_CONFIG).func_227228_a_(placeHeightmapDoubleChance(chance)));
   }

   public static void addWildPipeweedChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(WILD_PIPEWEED_CONFIG).func_227228_a_(placeHeightmapDoubleChance(chance)));
   }

   public static void addDeadBushes(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(DEAD_BUSH_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addDeadBushAtSurfaceChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(DEAD_BUSH_CONFIG).func_227228_a_(placeHeightmapChance(chance)));
   }

   public static void addCactiFreq(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(CACTUS_CONFIG).func_227228_a_(placeHeightmapFreq(freq)));
   }

   public static void addCactiAtSurfaceChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(CACTUS_CONFIG).func_227228_a_(placeHeightmapChance(chance)));
   }

   public static void addFallenLogs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      ConfiguredPlacement logPlacement = placeTopSolidFreq(freq);
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, LOTRFeatures.FALLEN_LOG.func_225566_b_(new FallenLogFeatureConfig(false, false)).func_227228_a_(logPlacement));
   }

   public static void addFallenLogsBelowTreeline(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, int treeline) {
      ConfiguredPlacement logPlacement = LOTRFeatures.TREE_CLUSTERS.func_227446_a_(TreeClustersConfig.builder().count(freq).layerLimit(treeline, true).build());
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, LOTRFeatures.FALLEN_LOG.func_225566_b_(new FallenLogFeatureConfig(false, false)).func_227228_a_(logPlacement));
   }

   public static void addDriftwood(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      ConfiguredPlacement logPlacement = placeTopSolidChance(chance);
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, LOTRFeatures.FALLEN_LOG.func_225566_b_(new FallenLogFeatureConfig(true, true)).func_227228_a_(logPlacement));
   }

   public static void addSeagrass(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, float tallProb) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, ((ConfiguredFeature)Feature.field_203234_at.func_225566_b_(new ProbabilityConfig(tallProb)).func_242731_b(freq)).func_227228_a_(placeTopSolidFreq(1)));
   }

   public static void addSwampSeagrass(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addSeagrass(builder, 100, 0.4F);
   }

   public static void addExtraUnderwaterSeagrass(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_206922_aF.func_225566_b_(new BlockWithContextConfig(Blocks.field_203198_aQ.func_176223_P(), ImmutableList.of(STONE), ImmutableList.of(WATER), ImmutableList.of(WATER))).func_227228_a_(Placement.field_215039_y.func_227446_a_(new CaveEdgeConfig(Carving.LIQUID, 0.1F))));
   }

   public static void addKelp(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, ((ConfiguredFeature)Feature.field_203235_au.func_225566_b_(IFeatureConfig.field_202429_e).func_227228_a_(Placement.field_215036_v.func_227446_a_(IPlacementConfig.field_202468_e)).func_242728_a()).func_227228_a_(Placement.field_242901_e.func_227446_a_(new TopSolidWithNoiseConfig(80, 80.0D, 0.0D))));
   }

   public static void addLatitudeBased(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, Decoration genStage, ConfiguredFeature feature, ConfiguredPlacement placement, LatitudeBasedFeatureConfig.LatitudeConfiguration latConfig) {
      LatitudeBasedFeatureConfig latFeatureConfig = new LatitudeBasedFeatureConfig(feature, latConfig);
      builder.func_242513_a(genStage, LOTRFeatures.LATITUDE_BASED.func_225566_b_(latFeatureConfig).func_227228_a_(placement));
   }

   public static void addCoral(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      ConfiguredFeature coralTree = Feature.field_204621_ay.func_225566_b_(IFeatureConfig.field_202429_e);
      ConfiguredFeature coralClaw = Feature.field_204619_aA.func_225566_b_(IFeatureConfig.field_202429_e);
      ConfiguredFeature coralShroom = Feature.field_204622_az.func_225566_b_(IFeatureConfig.field_202429_e);
      ConfiguredFeature coralRandomiser = Feature.field_204620_ao.func_225566_b_(new SingleRandomFeature(ImmutableList.of(() -> {
         return coralTree;
      }, () -> {
         return coralClaw;
      }, () -> {
         return coralShroom;
      })));
      int noiseToCount = 10;
      double noiseFactor = 400.0D;
      ConfiguredPlacement placement = ((ConfiguredPlacement)Placement.field_215036_v.func_227446_a_(IPlacementConfig.field_202468_e).func_242728_a()).func_227228_a_(Placement.field_242901_e.func_227446_a_(new TopSolidWithNoiseConfig(noiseToCount, noiseFactor, 0.0D)));
      addLatitudeBased(builder, Decoration.VEGETAL_DECORATION, coralRandomiser, placement, LatitudeBasedFeatureConfig.LatitudeConfiguration.of(LatitudeBasedFeatureConfig.LatitudeValuesType.CORAL));
   }

   public static void addSeaPickles(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      ConfiguredFeature seaPickle = Feature.field_204914_aC.func_225566_b_(new FeatureSpreadConfig(20));
      ConfiguredPlacement placement = placeTopSolidChance(16);
      addLatitudeBased(builder, Decoration.VEGETAL_DECORATION, seaPickle, placement, LatitudeBasedFeatureConfig.LatitudeConfiguration.of(LatitudeBasedFeatureConfig.LatitudeValuesType.CORAL));
   }

   public static void addSponges(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      ConfiguredFeature sponge = LOTRFeatures.UNDERWATER_SPONGE.func_225566_b_(SPONGE_CONFIG);
      ConfiguredPlacement placement = placeTopSolidChance(20);
      addLatitudeBased(builder, Decoration.VEGETAL_DECORATION, sponge, placement, LatitudeBasedFeatureConfig.LatitudeConfiguration.of(LatitudeBasedFeatureConfig.LatitudeValuesType.CORAL));
   }

   public static void addFreezeTopLayer(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      Feature freezeTopLayer = SnowRealMagicCompatibility.getFreezeTopLayerFeature();
      builder.func_242513_a(Decoration.TOP_LAYER_MODIFICATION, freezeTopLayer.func_225566_b_(IFeatureConfig.field_202429_e).func_227228_a_(Placement.field_215022_h.func_227446_a_(IPlacementConfig.field_202468_e)));
   }

   public static void addIcebergs(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      ConfiguredFeature iceberg = Feature.field_205172_ag.func_225566_b_(new BlockStateFeatureConfig(PACKED_ICE));
      ConfiguredFeature blueIceberg = Feature.field_205172_ag.func_225566_b_(new BlockStateFeatureConfig(BLUE_ICE));
      int icebergChance = 16;
      int blueIcebergChance = 200;
      ConfiguredPlacement icebergPlacement = (ConfiguredPlacement)Placement.field_215009_H.func_227446_a_(NoPlacementConfig.field_236556_b_).func_242729_a(icebergChance);
      ConfiguredPlacement blueIcebergPlacement = (ConfiguredPlacement)Placement.field_215009_H.func_227446_a_(NoPlacementConfig.field_236556_b_).func_242729_a(blueIcebergChance);
      addLatitudeBased(builder, Decoration.LOCAL_MODIFICATIONS, iceberg, icebergPlacement, LatitudeBasedFeatureConfig.LatitudeConfiguration.of(LatitudeBasedFeatureConfig.LatitudeValuesType.ICE));
      addLatitudeBased(builder, Decoration.LOCAL_MODIFICATIONS, blueIceberg, blueIcebergPlacement, LatitudeBasedFeatureConfig.LatitudeConfiguration.of(LatitudeBasedFeatureConfig.LatitudeValuesType.ICE));
   }

   public static void addBlueIcePatches(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      ConfiguredFeature blueIce = Feature.field_205171_af.func_225566_b_(IFeatureConfig.field_202429_e);
      int blueIceFreq = 19;
      ConfiguredPlacement blueIcePlacement = (ConfiguredPlacement)((ConfiguredPlacement)Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(30, 32, 64)).func_242728_a()).func_242732_c(blueIceFreq);
      addLatitudeBased(builder, Decoration.SURFACE_STRUCTURES, blueIce, blueIcePlacement, LatitudeBasedFeatureConfig.LatitudeConfiguration.of(LatitudeBasedFeatureConfig.LatitudeValuesType.ICE));
   }

   public static void addMushrooms(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(BROWN_MUSHROOM_CONFIG).func_227228_a_(placeHeightmapDoubleChance(4)));
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(RED_MUSHROOM_CONFIG).func_227228_a_(placeHeightmapDoubleChance(8)));
   }

   public static void addMoreMushroomsFreq(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(BROWN_MUSHROOM_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq * 2)));
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(RED_MUSHROOM_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addMirkShroomsFreq(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(MIRK_SHROOM_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addReedsWithDriedChance(builder, 0.1F);
   }

   public static void addReedsWithDriedChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, float driedChance) {
      addReedsWithFreqAndDriedChance(builder, 10, driedChance);
   }

   public static void addLessCommonReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addReedsWithFreqAndDriedChance(builder, 4, 0.1F);
   }

   public static void addReedsWithFreqAndDriedChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, float driedChance) {
      ReedsFeatureConfig config = (ReedsFeatureConfig)REEDS_CONFIG_FOR_DRIED_CHANCE.apply(driedChance);
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, LOTRFeatures.REEDS.func_225566_b_(config).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addMoreSwampReeds(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      int freq = 1;
      ReedsFeatureConfig noDriedConfig = (ReedsFeatureConfig)REEDS_CONFIG_FOR_DRIED_CHANCE.apply(0.0F);
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, LOTRFeatures.REEDS.func_225566_b_(noDriedConfig).func_227228_a_(placeHeightmapFreq(freq)));
   }

   public static void addPapyrus(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addPapyrus(builder, 10);
   }

   public static void addMoreCommonPapyrus(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addPapyrus(builder, 20);
   }

   public static void addPapyrus(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, LOTRFeatures.REEDS.func_225566_b_(PAPYRUS_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addSwampRushes(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addRushes(builder, 2);
   }

   public static void addRiverRushes(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addRushes(builder, 7);
   }

   public static void addRushes(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(RUSHES_CONFIG).func_227228_a_(placeHeightmapFreq(freq)));
   }

   public static void addSugarCane(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      int freq = 10;
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(SUGAR_CANE_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addPumpkins(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(PUMPKIN_PATCH_CONFIG).func_227228_a_(placeHeightmapDoubleChance(32)));
   }

   public static void addWaterLilies(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(LILY_PAD_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addWaterLiliesWithFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(LILY_PAD_WITH_FLOWERS_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addWaterLiliesWithRareFlowers(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(LILY_PAD_WITH_RARE_FLOWERS_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addFoxBerryBushes(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addFoxBerryBushesChance(builder, 4);
   }

   public static void addSparseFoxBerryBushes(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addFoxBerryBushesChance(builder, 12);
   }

   public static void addFoxBerryBushesChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(SWEET_BERRY_BUSH_CONFIG).func_227228_a_(placeHeightmapDoubleChance(chance)));
   }

   public static void addTundraBushesChance(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance, BlockStateProvider blockProvider, int triesPerPatch) {
      addTundraBushes(builder, blockProvider, triesPerPatch, placeHeightmapChance(chance));
   }

   public static void addTundraBushesFreq(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, BlockStateProvider blockProvider, int triesPerPatch) {
      addTundraBushes(builder, blockProvider, triesPerPatch, placeHeightmapFreq(freq));
   }

   public static void addTundraBushes(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, BlockStateProvider blockProvider, int triesPerPatch, ConfiguredPlacement placement) {
      BlockClusterFeatureConfig config = (new net.minecraft.world.gen.feature.BlockClusterFeatureConfig.Builder(blockProvider, new SimpleBlockPlacer())).func_227315_a_(triesPerPatch).func_227316_a_(ImmutableSet.of(Blocks.field_196658_i, Blocks.field_196660_k, Blocks.field_150348_b, Blocks.field_196604_cC)).func_227317_b_().func_227322_d_();
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(config).func_227228_a_(placement));
   }

   public static void addTreeTorches(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq, int yMin, int yMax, BlockState... torches) {
      WeightedBlockStateProvider stateProvider = new WeightedBlockStateProvider();
      Arrays.asList(torches).stream().forEach((state) -> {
         int weight = true;
         stateProvider.func_227407_a_(state, 1);
      });
      ConfiguredPlacement placement = (ConfiguredPlacement)((ConfiguredPlacement)Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(yMin, yMin, yMax)).func_242728_a()).func_242731_b(freq);
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, LOTRFeatures.TREE_TORCHES.func_225566_b_(new BlockStateProvidingFeatureConfig(stateProvider)).func_227228_a_(placement));
   }

   public static void addMordorBasalt(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int flatChance, int spikyChance) {
      List surfaceBlocks = ImmutableList.of(MORDOR_ROCK, MORDOR_DIRT, MORDOR_GRAVEL, STONE, DIRT, GRAVEL, Blocks.field_235337_cO_.func_176223_P());
      MordorBasaltFeatureConfig spikyConfig = new MordorBasaltFeatureConfig(surfaceBlocks, FeatureSpread.func_242253_a(3, 9), FeatureSpread.func_242253_a(1, 2), 0.6F, 0.95F, 0.15F, 0.35F, 0.2F);
      MordorBasaltFeatureConfig flatConfig = new MordorBasaltFeatureConfig(surfaceBlocks, FeatureSpread.func_242253_a(3, 9), FeatureSpread.func_242253_a(1, 2), 0.3F, 0.95F, 0.0F, 0.1F, 0.0F);
      builder.func_242513_a(Decoration.LOCAL_MODIFICATIONS, LOTRFeatures.MORDOR_BASALT.func_225566_b_(spikyConfig).func_227228_a_(placeHeightmapChance(spikyChance)));
      builder.func_242513_a(Decoration.LOCAL_MODIFICATIONS, LOTRFeatures.MORDOR_BASALT.func_225566_b_(flatConfig).func_227228_a_(placeHeightmapChance(flatChance)));
   }

   public static void addMordorMoss(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, LOTRFeatures.MORDOR_MOSS.func_225566_b_(MORDOR_MOSS_CONFIG).func_227228_a_(placeHeightmapChance(chance)));
   }

   public static void addMordorGrass(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(MORDOR_GRASS_CONFIG).func_227228_a_(placeHeightmapDoubleFreq(freq)));
   }

   public static void addMordorThorns(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(MORDOR_THORN_CONFIG).func_227228_a_(placeHeightmapDoubleChance(chance)));
   }

   public static void addMorgulShrooms(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int chance) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(MORGUL_SHROOM_CONFIG).func_227228_a_(placeHeightmapDoubleChance(chance)));
   }

   public static void addExtraMorgulFlowersByWater(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(MORGUL_FLOWER_CONFIG).func_227228_a_(LOTRFeatures.BY_WATER.func_227446_a_(new ByWaterConfig(8, 20)).func_227228_a_(placeHeightmapFreq(freq))));
   }

   public static void addWaterSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addWaterSprings(builder, 50);
   }

   public static void addWaterSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202295_ao.func_225566_b_(WATER_SPRING_CONFIG).func_227228_a_(Placement.field_242908_m.func_227446_a_(new TopSolidRangeConfig(8, 8, 256))).func_242728_a()).func_242731_b(freq));
   }

   public static void addLavaSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addLavaSprings(builder, 20);
   }

   public static void addLavaSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int freq) {
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202295_ao.func_225566_b_(LAVA_SPRING_CONFIG).func_227228_a_(Placement.field_242909_n.func_227446_a_(new TopSolidRangeConfig(8, 16, 256))).func_242728_a()).func_242731_b(freq));
   }

   public static void addWaterLavaSprings(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder) {
      addWaterSprings(builder);
      addLavaSprings(builder);
   }

   public static void addWaterLavaSpringsReducedAboveground(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int reducedAboveY, float aboveReductionFactor) {
      int defaultWater = 50;
      int defaultLava = 20;
      int height = 256;
      float belowFactor = (float)reducedAboveY / (float)height;
      float aboveFactor = (float)(height - reducedAboveY) / (float)height;
      aboveFactor *= aboveReductionFactor;
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202295_ao.func_225566_b_(WATER_SPRING_CONFIG).func_227228_a_(Placement.field_242908_m.func_227446_a_(new TopSolidRangeConfig(8, 8, reducedAboveY))).func_242728_a()).func_242731_b((int)((float)defaultWater * belowFactor)));
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202295_ao.func_225566_b_(LAVA_SPRING_CONFIG).func_227228_a_(Placement.field_242909_n.func_227446_a_(new TopSolidRangeConfig(8, 16, reducedAboveY))).func_242728_a()).func_242731_b((int)((float)defaultLava * belowFactor)));
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202295_ao.func_225566_b_(WATER_SPRING_CONFIG).func_227228_a_(Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(reducedAboveY, reducedAboveY, height))).func_242728_a()).func_242731_b((int)((float)defaultWater * aboveFactor)));
      builder.func_242513_a(Decoration.VEGETAL_DECORATION, (ConfiguredFeature)((ConfiguredFeature)Feature.field_202295_ao.func_225566_b_(LAVA_SPRING_CONFIG).func_227228_a_(Placement.field_242907_l.func_227446_a_(new TopSolidRangeConfig(reducedAboveY, reducedAboveY, height))).func_242728_a()).func_242731_b((int)((float)defaultLava * aboveFactor)));
   }

   public static void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, BlockState block, int minWidth, int maxWidth, int chanceInChunk, int genAmount) {
      int heightCheck = 3;
      addBoulders(builder, block, minWidth, maxWidth, chanceInChunk, genAmount, heightCheck);
   }

   public static void addBoulders(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, BlockState block, int minWidth, int maxWidth, int chanceInChunk, int genAmount, int heightCheck) {
      BlockStateProvider blockProv = new SimpleBlockStateProvider(block);
      BoulderFeatureConfig config = new BoulderFeatureConfig(blockProv, minWidth, maxWidth, heightCheck);
      int baseCount = 0;
      float increaseChance = 1.0F / (float)chanceInChunk;
      ConfiguredPlacement placement = ((ConfiguredPlacement)Placement.field_242904_h.func_227446_a_(NoPlacementConfig.field_236556_b_).func_242728_a()).func_227228_a_(Placement.field_242902_f.func_227446_a_(new AtSurfaceWithExtraConfig(baseCount, increaseChance, genAmount)));
      builder.func_242513_a(Decoration.LOCAL_MODIFICATIONS, LOTRFeatures.BOULDER.func_225566_b_(config).func_227228_a_(placement));
   }

   public static void addTerrainSharpener(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, List targets, int minHeight, int maxHeight, int freq) {
      TerrainSharpenFeatureConfig config = new TerrainSharpenFeatureConfig(targets, minHeight, maxHeight);
      ConfiguredPlacement placement = placeHeightmapFreq(freq);
      builder.func_242513_a(Decoration.LOCAL_MODIFICATIONS, LOTRFeatures.TERRAIN_SHARPEN.func_225566_b_(config).func_227228_a_(placement));
   }

   public static void addGrassPatches(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, List targets, int rMin, int rMax, int depthMin, int depthMax, int freq) {
      GrassPatchFeatureConfig config = new GrassPatchFeatureConfig(targets, rMin, rMax, depthMin, depthMax);
      ConfiguredPlacement placement = placeHeightmapFreq(freq);
      builder.func_242513_a(Decoration.LOCAL_MODIFICATIONS, LOTRFeatures.GRASS_PATCH.func_225566_b_(config).func_227228_a_(placement));
   }

   public static void addCraftingMonument(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, BlockState table, BlockStateProvider baseProvider, BlockStateProvider postProvider, BlockStateProvider torchProvider) {
      addCraftingMonument(builder, 1, table, baseProvider, postProvider, torchProvider);
   }

   public static void addCraftingMonument(net.minecraft.world.biome.BiomeGenerationSettings.Builder builder, int relativeChance, BlockState table, BlockStateProvider baseProvider, BlockStateProvider postProvider, BlockStateProvider torchProvider) {
      CraftingMonumentFeatureConfig config = new CraftingMonumentFeatureConfig(table, baseProvider, postProvider, torchProvider);
      int chance = 512 * relativeChance;
      ConfiguredPlacement placement = placeHeightmapChance(chance);
      builder.func_242513_a(Decoration.SURFACE_STRUCTURES, LOTRFeatures.CRAFTING_MONUMENT.func_225566_b_(config).func_227228_a_(placement));
   }

   private static ConfiguredPlacement placeTopSolidFreq(int freq) {
      return (ConfiguredPlacement)((ConfiguredPlacement)Placement.field_215036_v.func_227446_a_(IPlacementConfig.field_202468_e).func_242728_a()).func_242731_b(freq);
   }

   private static ConfiguredPlacement placeTopSolidChance(int chance) {
      return (ConfiguredPlacement)((ConfiguredPlacement)Placement.field_215036_v.func_227446_a_(IPlacementConfig.field_202468_e).func_242728_a()).func_242729_a(chance);
   }

   private static ConfiguredPlacement placeHeightmapFreq(int freq) {
      return (ConfiguredPlacement)((ConfiguredPlacement)Placement.field_242904_h.func_227446_a_(IPlacementConfig.field_202468_e).func_242728_a()).func_242731_b(freq);
   }

   private static ConfiguredPlacement placeHeightmapChance(int chance) {
      return (ConfiguredPlacement)((ConfiguredPlacement)Placement.field_242904_h.func_227446_a_(IPlacementConfig.field_202468_e).func_242728_a()).func_242729_a(chance);
   }

   private static ConfiguredPlacement placeFlowers() {
      return (ConfiguredPlacement)Placement.field_242911_p.func_227446_a_(NoPlacementConfig.field_236556_b_).func_227228_a_(Placement.field_242904_h.func_227446_a_(IPlacementConfig.field_202468_e)).func_242728_a();
   }

   private static ConfiguredPlacement placeHeightmapDoubleFreq(int freq) {
      return (ConfiguredPlacement)((ConfiguredPlacement)Placement.field_242905_i.func_227446_a_(IPlacementConfig.field_202468_e).func_242728_a()).func_242731_b(freq);
   }

   private static ConfiguredPlacement placeHeightmapDoubleChance(int chance) {
      return (ConfiguredPlacement)((ConfiguredPlacement)Placement.field_242905_i.func_227446_a_(IPlacementConfig.field_202468_e).func_242728_a()).func_242729_a(chance);
   }
}
