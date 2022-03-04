package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRBlockLeaves5 extends LOTRBlockLeavesBase {
   public LOTRBlockLeaves5() {
      this.setLeafNames(new String[]{"pine", "lemon", "orange", "lime"});
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.sapling5);
   }

   protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
      int fruitChance;
      if ((meta & 3) == 1) {
         fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.lemon));
         }
      }

      if ((meta & 3) == 2) {
         fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.orange));
         }
      }

      if ((meta & 3) == 3) {
         fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.lime));
         }
      }

   }
}
