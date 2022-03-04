package lotr.common.world.gen.feature;

import lotr.common.init.LOTRTags;
import lotr.common.init.RegistryOrderHelper;
import lotr.common.world.gen.placement.AtSurfaceLayerLimitedWithExtra;
import lotr.common.world.gen.placement.AtSurfaceLayerLimitedWithExtraConfig;
import lotr.common.world.gen.placement.ByWater;
import lotr.common.world.gen.placement.ByWaterConfig;
import lotr.common.world.gen.placement.TreeClusters;
import lotr.common.world.gen.placement.TreeClustersConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LOTRFeatures {
   public static final DeferredRegister FEATURES;
   public static final DeferredRegister PLACEMENTS;
   public static final WeightedRandomFeature WEIGHTED_RANDOM;
   public static final MordorMossFeature MORDOR_MOSS;
   public static final BoulderFeature BOULDER;
   public static final DripstoneFeature DRIPSTONE;
   public static final CobwebFeature COBWEBS;
   public static final TerrainSharpenFeature TERRAIN_SHARPEN;
   public static final GrassPatchFeature GRASS_PATCH;
   public static final TreeTorchesFeature TREE_TORCHES;
   public static final CraftingMonumentFeature CRAFTING_MONUMENT;
   public static final CrystalFeature CRYSTALS;
   public static final LatitudeBasedFeature LATITUDE_BASED;
   public static final LeafBushesFeature LEAF_BUSHES;
   public static final FallenLogFeature FALLEN_LOG;
   public static final ReedsFeature REEDS;
   public static final UnderwaterSpongeFeature UNDERWATER_SPONGE;
   public static final FallenLeavesFeature FALLEN_LEAVES;
   public static final WrappedTreeFeature WRAPPED_TREE;
   public static final MordorBasaltFeature MORDOR_BASALT;
   public static final AtSurfaceLayerLimitedWithExtra COUNT_EXTRA_HEIGHTMAP_LIMITED;
   public static final TreeClusters TREE_CLUSTERS;
   public static final ByWater BY_WATER;

   public static void register() {
      IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
      FEATURES.register(bus);
      PLACEMENTS.register(bus);
   }

   private static Feature preRegFeature(String name, Feature feature) {
      return (Feature)RegistryOrderHelper.preRegObject(FEATURES, name, feature);
   }

   private static Placement preRegPlacement(String name, Placement placement) {
      return (Placement)RegistryOrderHelper.preRegObject(PLACEMENTS, name, placement);
   }

   public static final WeightedRandomFeature getWeightedRandom() {
      return WEIGHTED_RANDOM;
   }

   public static boolean isSurfaceBlock(IWorld world, BlockPos pos) {
      return isSurfaceBlock(world, pos, 0);
   }

   private static boolean isSurfaceBlock(IWorld world, BlockPos pos, int recursion) {
      if (world.func_180495_p(pos.func_177984_a()).func_185904_a().func_76224_d()) {
         return false;
      } else {
         BlockState state = world.func_180495_p(pos);
         Block block = state.func_177230_c();
         Biome biome = world.func_226691_t_(pos);
         ConfiguredSurfaceBuilder surface = (ConfiguredSurfaceBuilder)biome.func_242440_e().func_242500_d().get();
         if (block != surface.field_215454_b.func_204108_a().func_177230_c() && block != surface.field_215454_b.func_204109_b().func_177230_c()) {
            if (!block.func_203417_a(BlockTags.field_203436_u) && !block.func_203417_a(BlockTags.field_205599_H) && !block.func_203417_a(Blocks.GRAVEL) && !block.func_203417_a(Blocks.DIRT)) {
               if (block.func_203417_a(LOTRTags.Blocks.MORDOR_PLANT_SURFACES)) {
                  return true;
               } else {
                  return block == net.minecraft.block.Blocks.field_150348_b && recursion <= 1 && isSurfaceBlock(world, pos.func_177977_b(), recursion + 1);
               }
            } else {
               return true;
            }
         } else {
            return true;
         }
      }
   }

   public static void setGrassToDirtBelow(IWorld world, BlockPos pos) {
      BlockPos belowPos = pos.func_177977_b();
      BlockState belowState = world.func_180495_p(belowPos);
      belowState.func_177230_c().onPlantGrow(belowState, world, belowPos, pos);
   }

   public static void setGrassToDirtBelowDuringChunkGen(IChunk chunk, BlockPos pos) {
      BlockPos belowPos = pos.func_177977_b();
      if (chunk.func_180495_p(belowPos).func_235714_a_(Blocks.DIRT)) {
         chunk.func_177436_a(belowPos, net.minecraft.block.Blocks.field_150346_d.func_176223_P(), false);
      }

   }

   public static BlockState getBlockStateInContext(BlockState state, IWorld world, BlockPos pos) {
      return Block.func_199770_b(state, world, pos);
   }

   static {
      FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, "lotr");
      PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, "lotr");
      WEIGHTED_RANDOM = (WeightedRandomFeature)preRegFeature("weighted_random", new WeightedRandomFeature(WeightedRandomFeatureConfig.CODEC));
      MORDOR_MOSS = (MordorMossFeature)preRegFeature("mordor_moss", new MordorMossFeature(MordorMossFeatureConfig.CODEC));
      BOULDER = (BoulderFeature)preRegFeature("boulder", new BoulderFeature(BoulderFeatureConfig.CODEC));
      DRIPSTONE = (DripstoneFeature)preRegFeature("dripstone", new DripstoneFeature(DripstoneFeatureConfig.CODEC));
      COBWEBS = (CobwebFeature)preRegFeature("cobwebs", new CobwebFeature(CobwebFeatureConfig.CODEC));
      TERRAIN_SHARPEN = (TerrainSharpenFeature)preRegFeature("terrain_sharpen", new TerrainSharpenFeature(TerrainSharpenFeatureConfig.CODEC));
      GRASS_PATCH = (GrassPatchFeature)preRegFeature("grass_patch", new GrassPatchFeature(GrassPatchFeatureConfig.CODEC));
      TREE_TORCHES = (TreeTorchesFeature)preRegFeature("tree_torches", new TreeTorchesFeature(BlockStateProvidingFeatureConfig.field_236453_a_));
      CRAFTING_MONUMENT = (CraftingMonumentFeature)preRegFeature("crafting_monument", new CraftingMonumentFeature(CraftingMonumentFeatureConfig.CODEC));
      CRYSTALS = (CrystalFeature)preRegFeature("crystals", new CrystalFeature(CrystalFeatureConfig.CODEC));
      LATITUDE_BASED = (LatitudeBasedFeature)preRegFeature("latitude_based", new LatitudeBasedFeature(LatitudeBasedFeatureConfig.CODEC));
      LEAF_BUSHES = (LeafBushesFeature)preRegFeature("leaf_bushes", new LeafBushesFeature(NoFeatureConfig.field_236558_a_));
      FALLEN_LOG = (FallenLogFeature)preRegFeature("fallen_log", new FallenLogFeature(FallenLogFeatureConfig.CODEC));
      REEDS = (ReedsFeature)preRegFeature("reeds", new ReedsFeature(ReedsFeatureConfig.CODEC));
      UNDERWATER_SPONGE = (UnderwaterSpongeFeature)preRegFeature("underwater_sponge", new UnderwaterSpongeFeature(BlockClusterFeatureConfig.field_236587_a_));
      FALLEN_LEAVES = (FallenLeavesFeature)preRegFeature("fallen_leaves", new FallenLeavesFeature(NoFeatureConfig.field_236558_a_));
      WRAPPED_TREE = (WrappedTreeFeature)preRegFeature("wrapped_tree", new WrappedTreeFeature(WrappedTreeFeatureConfig.CODEC));
      MORDOR_BASALT = (MordorBasaltFeature)preRegFeature("mordor_basalt", new MordorBasaltFeature(MordorBasaltFeatureConfig.CODEC));
      COUNT_EXTRA_HEIGHTMAP_LIMITED = (AtSurfaceLayerLimitedWithExtra)preRegPlacement("count_extra_heightmap_limited", new AtSurfaceLayerLimitedWithExtra(AtSurfaceLayerLimitedWithExtraConfig.CODEC));
      TREE_CLUSTERS = (TreeClusters)preRegPlacement("tree_clusters", new TreeClusters(TreeClustersConfig.CODEC));
      BY_WATER = (ByWater)preRegPlacement("by_water", new ByWater(ByWaterConfig.CODEC));
   }
}
