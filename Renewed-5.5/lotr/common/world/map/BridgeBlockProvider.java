package lotr.common.world.map;

import java.util.Random;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraftforge.common.util.LazyOptional;

public class BridgeBlockProvider {
   public static final BridgeBlockProvider OAK = new BridgeBlockProvider(LazyOptional.of(() -> {
      return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_196662_n.func_176223_P(), 1);
   }), LazyOptional.of(() -> {
      return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_196622_bq.func_176223_P(), 1);
   }), LazyOptional.of(() -> {
      return (new WeightedBlockStateProvider()).func_227407_a_(Blocks.field_180407_aO.func_176223_P(), 1);
   }), LazyOptional.of(() -> {
      return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.OAK_BEAM.get()).func_176223_P(), 1);
   }), LazyOptional.of(() -> {
      return (new WeightedBlockStateProvider()).func_227407_a_(((Block)LOTRBlocks.OAK_BEAM_SLAB.get()).func_176223_P(), 1);
   }));
   private final LazyOptional mainProvider;
   private final LazyOptional mainSlabProvider;
   private final LazyOptional fenceProvider;
   private final LazyOptional beamProvider;
   private final LazyOptional beamSlabProvider;

   public BridgeBlockProvider(LazyOptional main, LazyOptional mainSlab, LazyOptional fence, LazyOptional beam, LazyOptional beamSlab) {
      this.mainProvider = main;
      this.mainSlabProvider = mainSlab;
      this.fenceProvider = fence;
      this.beamProvider = beam;
      this.beamSlabProvider = beamSlab;
   }

   public BlockState getMainBlock(Random rand, BlockPos pos) {
      return ((BlockStateProvider)this.mainProvider.orElse(RoadBlockProvider.FAILSAFE_PROVIDER)).func_225574_a_(rand, pos);
   }

   public BlockState getMainSlabBlock(Random rand, BlockPos pos) {
      return ((BlockStateProvider)this.mainSlabProvider.orElse(RoadBlockProvider.FAILSAFE_PROVIDER)).func_225574_a_(rand, pos);
   }

   public BlockState getMainSlabBlockInverted(Random rand, BlockPos pos) {
      return this.safeGetInvertedSlab(this.getMainSlabBlock(rand, pos));
   }

   public BlockState getFenceBlock(Random rand, BlockPos pos) {
      return ((BlockStateProvider)this.fenceProvider.orElse(RoadBlockProvider.FAILSAFE_PROVIDER)).func_225574_a_(rand, pos);
   }

   public BlockState getBeamBlock(Random rand, BlockPos pos) {
      return ((BlockStateProvider)this.beamProvider.orElse(RoadBlockProvider.FAILSAFE_PROVIDER)).func_225574_a_(rand, pos);
   }

   public BlockState getBeamSlabBlock(Random rand, BlockPos pos) {
      return ((BlockStateProvider)this.beamSlabProvider.orElse(RoadBlockProvider.FAILSAFE_PROVIDER)).func_225574_a_(rand, pos);
   }

   public BlockState getBeamSlabBlockInverted(Random rand, BlockPos pos) {
      return this.safeGetInvertedSlab(this.getBeamSlabBlock(rand, pos));
   }

   private BlockState safeGetInvertedSlab(BlockState slab) {
      if (slab.func_235901_b_(BlockStateProperties.field_208145_at)) {
         slab = (BlockState)slab.func_206870_a(BlockStateProperties.field_208145_at, SlabType.TOP);
      }

      return slab;
   }
}
