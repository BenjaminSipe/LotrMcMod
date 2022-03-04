package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.AbstractBlock.Properties;

public class WattleAndDaubPillarBlock extends LOTRPillarBlock {
   public WattleAndDaubPillarBlock(Supplier blockSup) {
      super(Properties.func_200950_a((AbstractBlock)blockSup.get()));
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 40, 40);
   }
}
