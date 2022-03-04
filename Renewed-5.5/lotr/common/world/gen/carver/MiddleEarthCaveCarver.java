package lotr.common.world.gen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class MiddleEarthCaveCarver extends CaveWorldCarver {
   private Set landOnlyCarvables;

   public MiddleEarthCaveCarver(Codec codec, int height) {
      super(codec, height);
      this.field_222718_j = LOTRWorldCarvers.listCarvableBlocks();
      this.landOnlyCarvables = LOTRWorldCarvers.listLandOnlyCarvableBlocks();
   }

   protected boolean func_222707_a(BlockState state, BlockState aboveState) {
      Block block = state.func_177230_c();
      return this.func_222706_a(state) || this.landOnlyCarvables.contains(block) && !aboveState.func_204520_s().func_206884_a(FluidTags.field_206959_a);
   }

   protected boolean func_230358_a_(IChunk chunk, Function biomeGetter, BitSet carvingMask, Random rand, Mutable movingPos, Mutable movingPosAbove, Mutable movingPosBelow, int seaLevel, int chunkX, int chunkZ, int x, int z, int xInChunk, int y, int zInChunk, MutableBoolean isSurface) {
      boolean flag = super.func_230358_a_(chunk, biomeGetter, carvingMask, rand, movingPos, movingPosAbove, movingPosBelow, seaLevel, chunkX, chunkZ, x, z, xInChunk, y, zInChunk, isSurface);
      if (flag) {
         changeOtherBlocksAboveAndBelow(chunk, movingPos, movingPosAbove, movingPosBelow);
      }

      return flag;
   }

   protected static void changeOtherBlocksAboveAndBelow(IChunk chunk, Mutable movingPos, Mutable movingPosAbove, Mutable movingPosBelow) {
      movingPosBelow.func_189533_g(movingPos).func_189536_c(Direction.DOWN);
      if (chunk.func_180495_p(movingPosBelow).func_177230_c() == LOTRBlocks.DIRTY_CHALK.get()) {
         chunk.func_177436_a(movingPosBelow, ((Block)LOTRBlocks.CHALK.get()).func_176223_P(), false);
      }

   }
}
