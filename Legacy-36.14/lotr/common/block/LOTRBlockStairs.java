package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class LOTRBlockStairs extends BlockStairs {
   private Block baseBlock;
   private int baseMeta;

   public LOTRBlockStairs(Block block, int meta) {
      super(block, meta);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.field_149783_u = true;
      this.baseBlock = block;
      this.baseMeta = meta;
   }
}
