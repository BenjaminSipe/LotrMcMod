package lotr.common.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LOTRSlabBlock extends SlabBlock {
   private final Block modelBlock;

   public LOTRSlabBlock(Block block) {
      super(Properties.func_200950_a(block));
      this.modelBlock = block;
      if (!(this.modelBlock instanceof LOTRPlanksBlock) && !isVanillaFlammablePlanksBlock_mightAsWellHardcodeIt(this.modelBlock)) {
         if (this.modelBlock instanceof ThatchBlock) {
            ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 20);
         }
      } else {
         ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 5, 20);
      }

   }

   public LOTRSlabBlock(Supplier block) {
      this((Block)block.get());
   }

   private static boolean isVanillaFlammablePlanksBlock_mightAsWellHardcodeIt(Block block) {
      String expectedVersion = "1.16.5";
      String gameVersion = SharedConstants.func_215069_a().getName();
      if (!gameVersion.equals(expectedVersion)) {
         throw new IllegalStateException("Game version is " + gameVersion + ", this method (vanilla planks check) was built on version " + expectedVersion + ". Modder, check if any new flammable vanilla planks were added, and update this check!");
      } else {
         return block == Blocks.field_196662_n || block == Blocks.field_196664_o || block == Blocks.field_196666_p || block == Blocks.field_196668_q || block == Blocks.field_196670_r || block == Blocks.field_196672_s;
      }
   }

   public Block getModelBlock() {
      return this.modelBlock;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      this.modelBlock.func_180655_c(state, world, pos, rand);
   }
}
