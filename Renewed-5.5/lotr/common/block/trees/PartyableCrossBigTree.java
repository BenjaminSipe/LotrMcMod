package lotr.common.block.trees;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

public abstract class PartyableCrossBigTree extends CrossBigTree {
   private final PartyTreeLogic partyTree = new PartyTreeLogic(this::getPartyTreeFeature);

   protected abstract ConfiguredFeature getPartyTreeFeature(Random var1);

   public boolean func_230339_a_(ServerWorld world, ChunkGenerator chunkGen, BlockPos pos, BlockState sapling, Random rand) {
      return this.partyTree.attemptGrowPartyTree(world, chunkGen, pos, sapling, rand) ? true : super.func_230339_a_(world, chunkGen, pos, sapling, rand);
   }
}
