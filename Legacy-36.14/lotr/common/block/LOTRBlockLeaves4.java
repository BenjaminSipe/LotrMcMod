package lotr.common.block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRBlockLeaves4 extends LOTRBlockLeavesBase {
   public LOTRBlockLeaves4() {
      this.setLeafNames(new String[]{"chestnut", "baobab", "cedar", "fir"});
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      super.func_149674_a(world, i, j, k, random);
      if (!world.field_72995_K && world.func_147439_a(i, j, k) == this) {
         int meta = world.func_72805_g(i, j, k);
         int leafType = meta & 3;
         boolean playerPlaced = (meta & 4) != 0;
         if (leafType == 0 && !playerPlaced && world.func_147437_c(i, j - 1, k) && random.nextInt(300) == 0) {
            double d = (double)i + random.nextDouble();
            double d1 = (double)j - 0.2D;
            double d2 = (double)k + random.nextDouble();
            EntityItem conker = new EntityItem(world, d, d1, d2, new ItemStack(LOTRMod.chestnut));
            conker.field_145804_b = 10;
            conker.field_70159_w = conker.field_70181_x = conker.field_70179_y = 0.0D;
            world.func_72838_d(conker);
         }
      }

   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.sapling4);
   }

   protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
      if ((meta & 3) == 0) {
         int fruitChance = this.calcFortuneModifiedDropChance(20, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.chestnut));
         }
      }

   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = super.getDrops(world, i, j, k, meta, fortune);
      if ((meta & 3) == 3 && LOTRMod.isChristmas()) {
         Iterator var8 = drops.iterator();

         while(var8.hasNext()) {
            ItemStack itemstack = (ItemStack)var8.next();
            if (world.field_73012_v.nextInt(3) == 0 && itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.sapling4)) {
               itemstack.func_151001_c("Christmas Tree");
            }
         }
      }

      return drops;
   }
}
