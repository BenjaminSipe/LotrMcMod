package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRBlockFruitLeaves extends LOTRBlockLeavesBase {
   public LOTRBlockFruitLeaves() {
      this.setLeafNames(new String[]{"apple", "pear", "cherry", "mango"});
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.fruitSapling);
   }

   protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
      int fruitChance;
      if ((meta & 3) == 0) {
         fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            if (world.field_73012_v.nextBoolean()) {
               drops.add(new ItemStack(Items.field_151034_e));
            } else {
               drops.add(new ItemStack(LOTRMod.appleGreen));
            }
         }
      }

      if ((meta & 3) == 1) {
         fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.pear));
         }
      }

      if ((meta & 3) == 2) {
         fruitChance = this.calcFortuneModifiedDropChance(8, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.cherry));
         }
      }

      if ((meta & 3) == 3) {
         fruitChance = this.calcFortuneModifiedDropChance(16, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.mango));
         }
      }

   }
}
