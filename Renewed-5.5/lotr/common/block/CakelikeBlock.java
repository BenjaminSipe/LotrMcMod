package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.AbstractBlock.Properties;

public class CakelikeBlock extends CakeBlock {
   public CakelikeBlock(Properties properties) {
      super(properties);
      CompostingHelper.prepareCompostable(this, 1.0F);
   }

   public CakelikeBlock() {
      this(Properties.func_200950_a(Blocks.field_150414_aQ));
   }
}
