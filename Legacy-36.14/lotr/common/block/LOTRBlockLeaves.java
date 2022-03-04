package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockLeaves extends LOTRBlockLeavesBase {
   public LOTRBlockLeaves() {
      this.setLeafNames(new String[]{"shirePine", "mallorn", "mirkOak", "mirkOakRed"});
      this.setSeasonal(new boolean[]{false, true, false, false});
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      super.func_149734_b(world, i, j, k, random);
      String s = null;
      int metadata = world.func_72805_g(i, j, k) & 3;
      if (metadata == 1 && random.nextInt(75) == 0) {
         s = "leafGold";
      } else if (metadata == 2 && random.nextInt(250) == 0) {
         s = "leafMirk";
      } else if (metadata == 3 && random.nextInt(40) == 0) {
         s = "leafRed";
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

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(LOTRMod.sapling);
   }

   protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
      if ((meta & 3) == 1) {
         int nutChance = this.calcFortuneModifiedDropChance(100, fortune);
         if (world.field_73012_v.nextInt(nutChance) == 0) {
            drops.add(new ItemStack(LOTRMod.mallornNut));
         }
      }

   }

   public int getLightOpacity(IBlockAccess world, int i, int j, int k) {
      int l = world.func_72805_g(i, j, k) & 3;
      return l != 2 && l != 3 ? super.getLightOpacity(world, i, j, k) : 255;
   }
}
