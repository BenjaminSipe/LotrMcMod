package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.AbstractBlock.Properties;

public class LOTRStairsBlock extends StairsBlock {
   public LOTRStairsBlock(Block block) {
      super(() -> {
         return block.func_176223_P();
      }, Properties.func_200950_a(block));
      if (block instanceof LOTRPlanksBlock) {
         ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 5, 20);
      } else if (block instanceof ThatchBlock) {
         ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 20);
      }

   }

   public LOTRStairsBlock(Supplier block) {
      this((Block)block.get());
   }
}
