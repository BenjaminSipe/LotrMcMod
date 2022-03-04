package lotr.common.block;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;

public class LOTRBlockLeaves3 extends LOTRBlockLeavesBase {
   public LOTRBlockLeaves3() {
      this.setLeafNames(new String[]{"maple", "larch", "datePalm", "mangrove"});
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.sapling3);
   }

   protected int getSaplingChance(int meta) {
      return meta == 2 ? 15 : super.getSaplingChance(meta);
   }
}
