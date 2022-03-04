package lotr.common.world.gen.carver;

import com.mojang.serialization.Codec;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.gen.carver.CanyonWorldCarver;

public class MiddleEarthCanyonCarver extends CanyonWorldCarver {
   private Set landOnlyCarvables;

   public MiddleEarthCanyonCarver(Codec codec) {
      super(codec);
      this.field_222718_j = LOTRWorldCarvers.listCarvableBlocks();
      this.landOnlyCarvables = LOTRWorldCarvers.listLandOnlyCarvableBlocks();
   }

   protected boolean func_222707_a(BlockState state, BlockState aboveState) {
      Block block = state.func_177230_c();
      return this.func_222706_a(state) || this.landOnlyCarvables.contains(block) && !aboveState.func_204520_s().func_206884_a(FluidTags.field_206959_a);
   }
}
