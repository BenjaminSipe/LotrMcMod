package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRBlockLeaves7 extends LOTRBlockLeavesBase {
   public LOTRBlockLeaves7() {
      this.setLeafNames(new String[]{"aspen", "greenOak", "lairelosse", "almond"});
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.sapling7);
   }

   protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
      if ((meta & 3) == 3) {
         int fruitChance = this.calcFortuneModifiedDropChance(12, fortune);
         if (world.field_73012_v.nextInt(fruitChance) == 0) {
            drops.add(new ItemStack(LOTRMod.almond));
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      super.func_149734_b(world, i, j, k, random);
      String s = null;
      int metadata = world.func_72805_g(i, j, k) & 3;
      if (metadata == 1 && random.nextInt(150) == 0) {
         s = "leafGreen";
      }

      if (s != null) {
         double d = (double)((float)i + random.nextFloat());
         double d1 = (double)j - 0.05D;
         double d2 = (double)((float)k + random.nextFloat());
         double d3 = -0.1D + (double)(random.nextFloat() * 0.2F);
         double d4 = -0.03D - (double)(random.nextFloat() * 0.02F);
         double d5 = -0.1D + (double)(random.nextFloat() * 0.2F);
         LOTRMod.proxy.spawnParticle(s, d, d1, d2, d3, d4, d5);
      }

   }
}
