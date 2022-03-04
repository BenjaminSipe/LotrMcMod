package lotr.common.block;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;

public class LOTRBlockLeaves2 extends LOTRBlockLeavesBase {
   public LOTRBlockLeaves2() {
      this.setLeafNames(new String[]{"lebethron", "beech", "holly", "banana"});
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.sapling2);
   }

   protected int getSaplingChance(int meta) {
      return meta == 3 ? 9 : super.getSaplingChance(meta);
   }
}
