package lotr.common.world.map;

import java.util.Random;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.RegistryObject;

public class RoadBlockProvider {
   public static final WeightedBlockStateProvider FAILSAFE_PROVIDER;
   public static final RoadBlockProvider PATH;
   public static final RoadBlockProvider PAVED_PATH;
   public static final RoadBlockProvider CHALK_PATH;
   public static final RoadBlockProvider DIRT;
   public static final RoadBlockProvider COBBLESTONE;
   public static final RoadBlockProvider SMOOTH_STONE;
   public static final RoadBlockProvider STONE_BRICK;
   public static final RoadBlockProvider DRYSTONE;
   public static final RoadBlockProvider GONDOR;
   public static final RoadBlockProvider DOL_AMROTH;
   public static final RoadBlockProvider ROHAN;
   public static final RoadBlockProvider ARNOR;
   public static final RoadBlockProvider HIGH_ELVEN;
   public static final RoadBlockProvider WOOD_ELVEN;
   public static final RoadBlockProvider MIRKWOOD_PATH;
   public static final RoadBlockProvider MORDOR_BRICK;
   public static final RoadBlockProvider MORDOR_PATH;
   public static final RoadBlockProvider NURN_PATH;
   public static final RoadBlockProvider DWARVEN;
   public static final RoadBlockProvider DALE;
   public static final RoadBlockProvider DORWINION;
   public static final RoadBlockProvider DORWINION_PATH;
   public static final RoadBlockProvider HARAD;
   public static final RoadBlockProvider HARAD_PATH;
   public static final RoadBlockProvider UMBAR;
   public static final LazyOptional STANDARD_HEDGE;
   private final LazyOptional topProvider;
   private final LazyOptional topSlabProvider;
   private final LazyOptional fillerProvider;
   private final float repair;
   private final boolean requiresPostProcessing;
   private final RoadBlockProvider edgeRoadBlockProvider;
   private final LazyOptional hedgeProvider;
   private final float hedgeDensity;

   public RoadBlockProvider(LazyOptional top, LazyOptional topSlab, LazyOptional filler) {
      this(top, topSlab, filler, 1.0F);
   }

   public RoadBlockProvider(LazyOptional top, LazyOptional topSlab, LazyOptional filler, float rep) {
      this(top, topSlab, filler, rep, false, (RoadBlockProvider)null, (LazyOptional)null, 0.0F);
   }

   public RoadBlockProvider(LazyOptional top, LazyOptional topSlab, LazyOptional filler, float rep, boolean postProcess, RoadBlockProvider edgeRoad, LazyOptional hedge, float hedgeDens) {
      this.topProvider = top;
      this.topSlabProvider = topSlab;
      this.fillerProvider = filler;
      this.repair = rep;
      this.requiresPostProcessing = postProcess;
      this.edgeRoadBlockProvider = edgeRoad;
      this.hedgeProvider = hedge;
      this.hedgeDensity = hedgeDens;
   }

   public static RoadBlockProvider makeFromProviders(NonNullSupplier top, NonNullSupplier topSlab, NonNullSupplier filler) {
      return new RoadBlockProvider(LazyOptional.of(top), LazyOptional.of(topSlab), LazyOptional.of(filler));
   }

   public static RoadBlockProvider makeFromBlockAndSlabProviders(NonNullSupplier full, NonNullSupplier slab) {
      return makeFromProviders(full, slab, full);
   }

   public static RoadBlockProvider makeFromSingleBlocks(NonNullSupplier top, NonNullSupplier topSlab, NonNullSupplier filler) {
      return makeFromProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)top.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)topSlab.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)filler.get()).func_176223_P(), 1);
      });
   }

   public static RoadBlockProvider makeFromBlockAndSlab(NonNullSupplier full, NonNullSupplier slab) {
      return makeFromSingleBlocks(full, slab, full);
   }

   public static RoadBlockProvider makeFromBlockAndSlab(Block full, Block slab) {
      return makeFromBlockAndSlab(() -> {
         return full;
      }, () -> {
         return slab;
      });
   }

   public static RoadBlockProvider makeFromBlockAndSlab(RegistryObject full, RegistryObject slab) {
      return makeFromBlockAndSlab(() -> {
         return (Block)full.get();
      }, () -> {
         return (Block)slab.get();
      });
   }

   public RoadBlockProvider withRepair(float newRepair) {
      return new RoadBlockProvider(this.topProvider, this.topSlabProvider, this.fillerProvider, newRepair, this.requiresPostProcessing, this.edgeRoadBlockProvider, this.hedgeProvider, this.hedgeDensity);
   }

   public RoadBlockProvider withPostProcessing() {
      return new RoadBlockProvider(this.topProvider, this.topSlabProvider, this.fillerProvider, this.repair, true, this.edgeRoadBlockProvider, this.hedgeProvider, this.hedgeDensity);
   }

   public RoadBlockProvider withEdgeProvider(RoadBlockProvider newEdge) {
      return new RoadBlockProvider(this.topProvider, this.topSlabProvider, this.fillerProvider, this.repair, this.requiresPostProcessing, newEdge, this.hedgeProvider, this.hedgeDensity);
   }

   public RoadBlockProvider withHedge(LazyOptional hedge, float hedgeDens) {
      return new RoadBlockProvider(this.topProvider, this.topSlabProvider, this.fillerProvider, this.repair, this.requiresPostProcessing, this.edgeRoadBlockProvider, hedge, hedgeDens);
   }

   public RoadBlockProvider withStandardHedge() {
      return this.withHedge(STANDARD_HEDGE, 0.82F);
   }

   public BlockState getTopBlock(Random rand, BlockPos pos) {
      return ((BlockStateProvider)this.topProvider.orElse(FAILSAFE_PROVIDER)).func_225574_a_(rand, pos);
   }

   public BlockState getTopSlabBlock(Random rand, BlockPos pos) {
      return ((BlockStateProvider)this.topSlabProvider.orElse(FAILSAFE_PROVIDER)).func_225574_a_(rand, pos);
   }

   public BlockState getFillerBlock(Random rand, BlockPos pos) {
      return ((BlockStateProvider)this.fillerProvider.orElse(FAILSAFE_PROVIDER)).func_225574_a_(rand, pos);
   }

   public float getRepair() {
      return this.repair;
   }

   public boolean requiresPostProcessing() {
      return this.requiresPostProcessing;
   }

   public RoadBlockProvider getEdgeProvider() {
      return this.edgeRoadBlockProvider != null ? this.edgeRoadBlockProvider : this;
   }

   public boolean hasDistinctEdge() {
      return this.edgeRoadBlockProvider != null;
   }

   public boolean hasHedge() {
      return this.hedgeProvider != null;
   }

   public BlockState getHedgeBlock(Random rand, BlockPos pos) {
      return ((BlockStateProvider)this.hedgeProvider.orElse(FAILSAFE_PROVIDER)).func_225574_a_(rand, pos);
   }

   public float getHedgeDensity() {
      return this.hedgeDensity;
   }

   static {
      FAILSAFE_PROVIDER = (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150347_e.func_176223_P(), 1);
      PATH = makeFromProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 12).func_227407_a_(Blocks.field_196660_k.func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 12).func_227407_a_(Blocks.field_196660_k.func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150346_d.func_176223_P(), 1);
      }).withRepair(0.95F);
      PAVED_PATH = makeFromProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 6).func_227407_a_(Blocks.field_150347_e.func_176223_P(), 3).func_227407_a_(Blocks.field_150341_Y.func_176223_P(), 1).func_227407_a_(Blocks.field_150351_n.func_176223_P(), 2);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 6).func_227407_a_(Blocks.field_196646_bz.func_176223_P(), 3).func_227407_a_(Blocks.field_222450_ln.func_176223_P(), 1).func_227407_a_(Blocks.field_150351_n.func_176223_P(), 2);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150346_d.func_176223_P(), 1);
      });
      CHALK_PATH = makeFromProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.CHALK_PATH.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.DIRTY_CHALK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.CHALK_PATH.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.DIRTY_CHALK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.CHALK.get()).func_176223_P(), 1);
      }).withRepair(0.95F);
      DIRT = makeFromBlockAndSlab(Blocks.field_196660_k, Blocks.field_196660_k);
      COBBLESTONE = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150347_e.func_176223_P(), 6).func_227407_a_(Blocks.field_150341_Y.func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_196646_bz.func_176223_P(), 6).func_227407_a_(Blocks.field_222450_ln.func_176223_P(), 1);
      });
      SMOOTH_STONE = makeFromBlockAndSlab(Blocks.field_196579_bG, Blocks.field_222401_hJ);
      STONE_BRICK = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_196696_di.func_176223_P(), 4).func_227407_a_(Blocks.field_196698_dj.func_176223_P(), 1).func_227407_a_(Blocks.field_196700_dk.func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_196573_bB.func_176223_P(), 4).func_227407_a_(Blocks.field_222448_ll.func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_STONE_BRICK_SLAB.get()).func_176223_P(), 1);
      });
      DRYSTONE = makeFromBlockAndSlab(LOTRBlocks.DRYSTONE, LOTRBlocks.DRYSTONE);
      GONDOR = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.GONDOR_BRICK.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.MOSSY_GONDOR_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_GONDOR_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.SMOOTH_GONDOR_ROCK.get()).func_176223_P(), 2);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.GONDOR_BRICK_SLAB.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.MOSSY_GONDOR_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_GONDOR_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.SMOOTH_GONDOR_ROCK_SLAB.get()).func_176223_P(), 2);
      }).withEdgeProvider(SMOOTH_STONE);
      DOL_AMROTH = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DOL_AMROTH_BRICK.get()).func_176223_P(), 5).func_227407_a_(((Block)LOTRBlocks.CRACKED_DOL_AMROTH_BRICK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DOL_AMROTH_BRICK_SLAB.get()).func_176223_P(), 5).func_227407_a_(((Block)LOTRBlocks.CRACKED_DOL_AMROTH_BRICK_SLAB.get()).func_176223_P(), 1);
      }).withPostProcessing().withEdgeProvider(makeFromBlockAndSlab(LOTRBlocks.SMOOTH_GONDOR_ROCK, LOTRBlocks.SMOOTH_GONDOR_ROCK_SLAB));
      ROHAN = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ROHAN_BRICK.get()).func_176223_P(), 2).func_227407_a_(((Block)LOTRBlocks.CRACKED_ROHAN_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.ROHAN_ROCK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.SMOOTH_ROHAN_ROCK.get()).func_176223_P(), 1).func_227407_a_(Blocks.field_196660_k.func_176223_P(), 2);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ROHAN_BRICK_SLAB.get()).func_176223_P(), 2).func_227407_a_(((Block)LOTRBlocks.CRACKED_ROHAN_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.ROHAN_ROCK_SLAB.get()).func_176223_P(), 2).func_227407_a_(((Block)LOTRBlocks.ROHAN_ROCK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.SMOOTH_ROHAN_ROCK_SLAB.get()).func_176223_P(), 1).func_227407_a_(Blocks.field_196660_k.func_176223_P(), 2);
      }).withRepair(0.94F).withEdgeProvider(COBBLESTONE);
      ARNOR = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ARNOR_BRICK.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MOSSY_ARNOR_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_ARNOR_BRICK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.ARNOR_BRICK_SLAB.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MOSSY_ARNOR_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_ARNOR_BRICK_SLAB.get()).func_176223_P(), 1);
      }).withEdgeProvider(STONE_BRICK);
      HIGH_ELVEN = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HIGH_ELVEN_BRICK.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.MOSSY_HIGH_ELVEN_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_HIGH_ELVEN_BRICK.get()).func_176223_P(), 3);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HIGH_ELVEN_BRICK_SLAB.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.MOSSY_HIGH_ELVEN_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_HIGH_ELVEN_BRICK_SLAB.get()).func_176223_P(), 3);
      }).withEdgeProvider(SMOOTH_STONE);
      WOOD_ELVEN = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.WOOD_ELVEN_BRICK.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.WOOD_ELVEN_BRICK_SLAB.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK_SLAB.get()).func_176223_P(), 1);
      }).withEdgeProvider(STONE_BRICK);
      MIRKWOOD_PATH = makeFromProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.WOOD_ELVEN_BRICK.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK.get()).func_176223_P(), 1).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 8);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.WOOD_ELVEN_BRICK_SLAB.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 8);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.WOOD_ELVEN_BRICK.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MOSSY_WOOD_ELVEN_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_WOOD_ELVEN_BRICK.get()).func_176223_P(), 1).func_227407_a_(Blocks.field_150346_d.func_176223_P(), 8);
      }).withEdgeProvider(STONE_BRICK);
      MORDOR_BRICK = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MORDOR_BRICK.get()).func_176223_P(), 20).func_227407_a_(((Block)LOTRBlocks.CRACKED_MORDOR_BRICK.get()).func_176223_P(), 8).func_227407_a_(((Block)LOTRBlocks.MOSSY_MORDOR_BRICK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MORDOR_BRICK_SLAB.get()).func_176223_P(), 20).func_227407_a_(((Block)LOTRBlocks.CRACKED_MORDOR_BRICK_SLAB.get()).func_176223_P(), 8).func_227407_a_(((Block)LOTRBlocks.MOSSY_MORDOR_BRICK_SLAB.get()).func_176223_P(), 1);
      });
      MORDOR_PATH = makeFromProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MORDOR_DIRT_PATH.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MORDOR_DIRT.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.MORDOR_GRAVEL.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MORDOR_DIRT_PATH.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.MORDOR_DIRT.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.MORDOR_GRAVEL.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.MORDOR_DIRT.get()).func_176223_P(), 1);
      }).withEdgeProvider(MORDOR_BRICK);
      NURN_PATH = PATH.withEdgeProvider(MORDOR_BRICK);
      DWARVEN = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DWARVEN_BRICK.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.CRACKED_DWARVEN_BRICK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DWARVEN_BRICK_SLAB.get()).func_176223_P(), 4).func_227407_a_(((Block)LOTRBlocks.CRACKED_DWARVEN_BRICK_SLAB.get()).func_176223_P(), 1);
      }).withEdgeProvider(STONE_BRICK);
      DALE = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DALE_BRICK.get()).func_176223_P(), 8).func_227407_a_(((Block)LOTRBlocks.MOSSY_DALE_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_DALE_BRICK.get()).func_176223_P(), 2).func_227407_a_(((Block)LOTRBlocks.DALE_PAVING.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.MOSSY_DALE_PAVING.get()).func_176223_P(), 4);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DALE_BRICK_SLAB.get()).func_176223_P(), 8).func_227407_a_(((Block)LOTRBlocks.MOSSY_DALE_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_DALE_BRICK_SLAB.get()).func_176223_P(), 2).func_227407_a_(((Block)LOTRBlocks.DALE_PAVING_SLAB.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.MOSSY_DALE_PAVING_SLAB.get()).func_176223_P(), 4);
      }).withEdgeProvider(STONE_BRICK);
      DORWINION = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DORWINION_BRICK.get()).func_176223_P(), 6).func_227407_a_(((Block)LOTRBlocks.MOSSY_DORWINION_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_DORWINION_BRICK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.DORWINION_BRICK_SLAB.get()).func_176223_P(), 6).func_227407_a_(((Block)LOTRBlocks.MOSSY_DORWINION_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_DORWINION_BRICK_SLAB.get()).func_176223_P(), 1);
      }).withEdgeProvider(makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.RED_DORWINION_BRICK.get()).func_176223_P(), 6).func_227407_a_(((Block)LOTRBlocks.MOSSY_RED_DORWINION_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_RED_DORWINION_BRICK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.RED_DORWINION_BRICK_SLAB.get()).func_176223_P(), 6).func_227407_a_(((Block)LOTRBlocks.MOSSY_RED_DORWINION_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_RED_DORWINION_BRICK_SLAB.get()).func_176223_P(), 1);
      }));
      DORWINION_PATH = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 12).func_227407_a_(Blocks.field_196660_k.func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 12).func_227407_a_(Blocks.field_196660_k.func_176223_P(), 1);
      }).withEdgeProvider(makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.RED_DORWINION_BRICK.get()).func_176223_P(), 6).func_227407_a_(((Block)LOTRBlocks.MOSSY_RED_DORWINION_BRICK.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_RED_DORWINION_BRICK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.RED_DORWINION_BRICK_SLAB.get()).func_176223_P(), 6).func_227407_a_(((Block)LOTRBlocks.MOSSY_RED_DORWINION_BRICK_SLAB.get()).func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.CRACKED_RED_DORWINION_BRICK_SLAB.get()).func_176223_P(), 1);
      }));
      HARAD = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HARAD_BRICK.get()).func_176223_P(), 6);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HARAD_BRICK_SLAB.get()).func_176223_P(), 6);
      }).withEdgeProvider(makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HARAD_PILLAR.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HARAD_PILLAR_SLAB.get()).func_176223_P(), 1);
      }));
      HARAD_PATH = makeFromProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 12).func_227407_a_(Blocks.field_196660_k.func_176223_P(), 4).func_227407_a_(Blocks.field_150354_m.func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.HARAD_BRICK.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_185774_da.func_176223_P(), 12).func_227407_a_(Blocks.field_196660_k.func_176223_P(), 4).func_227407_a_(Blocks.field_150354_m.func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.HARAD_BRICK_SLAB.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150346_d.func_176223_P(), 16).func_227407_a_(Blocks.field_150322_A.func_176223_P(), 1).func_227407_a_(((Block)LOTRBlocks.HARAD_BRICK.get()).func_176223_P(), 1);
      }).withEdgeProvider(makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HARAD_PILLAR.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.HARAD_PILLAR_SLAB.get()).func_176223_P(), 1);
      }));
      UMBAR = makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.UMBAR_BRICK.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.HARAD_BRICK.get()).func_176223_P(), 4);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.UMBAR_BRICK_SLAB.get()).func_176223_P(), 12).func_227407_a_(((Block)LOTRBlocks.HARAD_BRICK_SLAB.get()).func_176223_P(), 4);
      }).withEdgeProvider(makeFromBlockAndSlabProviders(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.NUMENOREAN_BRICK.get()).func_176223_P(), 2).func_227407_a_(((Block)LOTRBlocks.NUMENOREAN_PILLAR.get()).func_176223_P(), 1);
      }, () -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.NUMENOREAN_BRICK_SLAB.get()).func_176223_P(), 2).func_227407_a_(((Block)LOTRBlocks.NUMENOREAN_PILLAR_SLAB.get()).func_176223_P(), 1);
      }));
      STANDARD_HEDGE = LazyOptional.of(() -> {
         return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_150347_e.func_176223_P(), 3).func_227407_a_(Blocks.field_196646_bz.func_176223_P(), 2).func_227407_a_(Blocks.field_150341_Y.func_176223_P(), 3).func_227407_a_(Blocks.field_222450_ln.func_176223_P(), 2).func_227407_a_((BlockState)Blocks.field_196642_W.func_176223_P().func_206870_a(LeavesBlock.field_208495_b, true), 24);
      });
   }
}
