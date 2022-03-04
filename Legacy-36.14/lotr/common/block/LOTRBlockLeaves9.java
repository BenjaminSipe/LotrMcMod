package lotr.common.block;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;

public class LOTRBlockLeaves9 extends LOTRBlockLeavesBase {
   public LOTRBlockLeaves9() {
      this.setLeafNames(new String[]{"dragon", "kanuka"});
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.sapling9);
   }
}
