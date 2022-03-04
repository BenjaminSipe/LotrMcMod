package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRBlockLeaves8 extends LOTRBlockLeavesBase {
   public LOTRBlockLeaves8() {
      this.setLeafNames(new String[]{"plum", "redwood", "pomegranate", "palm"});
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.sapling8);
   }

   protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
      int fruitChance;
      if ((meta & 3) == 0) {
         fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.plum));
         }
      }

      if ((meta & 3) == 2) {
         fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.pomegranate));
         }
      }

   }
}
