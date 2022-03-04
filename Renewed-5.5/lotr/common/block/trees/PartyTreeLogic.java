package lotr.common.block.trees;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

public class PartyTreeLogic {
   private static final int PARTY_TREE_EXTRA_WIDTH = 1;
   private final PartyTreeLogic.PartyTreeProvider partyTreeProvider;

   public PartyTreeLogic(PartyTreeLogic.PartyTreeProvider provider) {
      this.partyTreeProvider = provider;
   }

   protected boolean attemptGrowPartyTree(ServerWorld world, ChunkGenerator chunkGen, BlockPos pos, BlockState sapling, Random rand) {
      int searchRange = 2;

      for(int x = searchRange; x >= -searchRange; --x) {
         for(int z = searchRange; z >= -searchRange; --z) {
            if (this.canPartyTreeSpawnAt(sapling, world, pos, x, z)) {
               return this.growPartyTree(world, chunkGen, pos, sapling, rand, x, z);
            }
         }
      }

      return false;
   }

   private boolean growPartyTree(ServerWorld world, ChunkGenerator chunkGen, BlockPos pos, BlockState sapling, Random rand, int xOffset, int zOffset) {
      ConfiguredFeature partyTree = this.partyTreeProvider.getPartyTreeFeature(rand);
      if (partyTree == null) {
         return false;
      } else {
         ((BaseTreeFeatureConfig)partyTree.field_222738_b).func_227373_a_();
         BlockPos offsetPos = pos.func_177982_a(xOffset, 0, zOffset);
         BlockState air = Blocks.field_150350_a.func_176223_P();
         this.doForSaplingGrid(offsetPos, (saplingPos) -> {
            world.func_180501_a(saplingPos, air, 4);
         });
         if (partyTree.func_242765_a(world, chunkGen, rand, offsetPos)) {
            return true;
         } else {
            this.doForSaplingGrid(offsetPos, (saplingPos) -> {
               world.func_180501_a(saplingPos, sapling, 4);
            });
            return false;
         }
      }
   }

   private void doForSaplingGrid(BlockPos centralPos, Consumer action) {
      Mutable movingPos = new Mutable();

      for(int x = -1; x <= 1; ++x) {
         for(int z = -1; z <= 1; ++z) {
            movingPos.func_239621_a_(centralPos, x, 0, z);
            action.accept(movingPos);
         }
      }

   }

   private boolean canPartyTreeSpawnAt(BlockState sapling, IBlockReader world, BlockPos pos, int xOffset, int zOffset) {
      Block saplingBlock = sapling.func_177230_c();
      BlockPos offsetPos = pos.func_177982_a(xOffset, 0, zOffset);
      AtomicBoolean anyNotSaplings = new AtomicBoolean(false);
      this.doForSaplingGrid(offsetPos, (saplingPos) -> {
         if (!anyNotSaplings.get() && world.func_180495_p(saplingPos).func_177230_c() != saplingBlock) {
            anyNotSaplings.set(true);
         }

      });
      return !anyNotSaplings.get();
   }

   @FunctionalInterface
   public interface PartyTreeProvider {
      ConfiguredFeature getPartyTreeFeature(Random var1);
   }
}
