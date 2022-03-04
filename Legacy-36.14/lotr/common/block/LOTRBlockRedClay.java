package lotr.common.block;

import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class LOTRBlockRedClay extends Block {
   public LOTRBlockRedClay() {
      super(Material.field_151571_B);
      this.func_149711_c(0.6F);
      this.func_149672_a(Block.field_149767_g);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   public Item func_149650_a(int i, Random random, int j) {
      return LOTRMod.redClayBall;
   }

   public int func_149745_a(Random random) {
      return 4;
   }
}
