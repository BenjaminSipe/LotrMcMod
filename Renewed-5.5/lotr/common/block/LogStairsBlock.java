package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;

public class LogStairsBlock extends LOTRStairsBlock {
   public LogStairsBlock(Block block) {
      super(block);
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 5, 5);
   }

   public LogStairsBlock(Supplier block) {
      this((Block)block.get());
   }
}
