package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRBlockLeaves6 extends LOTRBlockLeavesBase {
   public LOTRBlockLeaves6() {
      this.setLeafNames(new String[]{"mahogany", "willow", "cypress", "olive"});
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.sapling6);
   }

   protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
      if ((meta & 3) == 3) {
         int fruitChance = this.calcFortuneModifiedDropChance(10, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.olive));
         }
      }

   }
}
