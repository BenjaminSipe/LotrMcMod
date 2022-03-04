package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockDoubleFlower extends BlockDoublePlant {
   public static final String[] flowerNames = new String[]{"blackIris", "yellowIris", "pink", "red"};
   @SideOnly(Side.CLIENT)
   private IIcon[] doublePlantBottomIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] doublePlantTopIcons;

   public LOTRBlockDoubleFlower() {
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getDoublePlantRenderID();
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      return 16777215;
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public int func_149885_e(IBlockAccess world, int i, int j, int k) {
      int l = world.func_72805_g(i, j, k);
      return !isTop(l) ? l & 7 : world.func_72805_g(i, j - 1, k) & 7;
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return super.func_149742_c(world, i, j, k) && world.func_147437_c(i, j + 1, k);
   }

   protected void func_149855_e(World world, int i, int j, int k) {
      if (!this.func_149718_j(world, i, j, k)) {
         int l = world.func_72805_g(i, j, k);
         if (!isTop(l)) {
            this.func_149697_b(world, i, j, k, l, 0);
            if (world.func_147439_a(i, j + 1, k) == this) {
               world.func_147465_d(i, j + 1, k, Blocks.field_150350_a, 0, 2);
            }
         }

         world.func_147465_d(i, j, k, Blocks.field_150350_a, 0, 2);
      }

   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      if (world.func_147439_a(i, j, k) != this) {
         return super.func_149718_j(world, i, j, k);
      } else {
         int l = world.func_72805_g(i, j, k);
         return isTop(l) ? world.func_147439_a(i, j - 1, k) == this : world.func_147439_a(i, j + 1, k) == this && super.func_149718_j(world, i, j, k);
      }
   }

   public Item func_149650_a(int i, Random random, int j) {
      return isTop(i) ? null : Item.func_150898_a(this);
   }

   public int func_149692_a(int i) {
      return isTop(i) ? 0 : i & 7;
   }

   public static boolean isTop(int i) {
      return (i & 8) != 0;
   }

   public static int getFlowerMeta(int i) {
      return i & 7;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (isTop(j)) {
         return this.doublePlantBottomIcons[1];
      } else {
         int k = j & 7;
         if (k >= this.doublePlantBottomIcons.length) {
            k = 0;
         }

         return this.doublePlantBottomIcons[k];
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149888_a(boolean isTop, int i) {
      if (isTop) {
         if (i >= this.doublePlantTopIcons.length) {
            i = 0;
         }

         return this.doublePlantTopIcons[i];
      } else {
         if (i >= this.doublePlantBottomIcons.length) {
            i = 0;
         }

         return this.doublePlantBottomIcons[i];
      }
   }

   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
      int l = ((MathHelper.func_76128_c((double)(entity.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
      world.func_147465_d(i, j + 1, k, this, 8 | l, 2);
   }

   public void func_149681_a(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
      if (isTop(meta)) {
         if (world.func_147439_a(i, j - 1, k) == this) {
            if (!entityplayer.field_71075_bZ.field_75098_d) {
               world.func_147480_a(i, j - 1, k, true);
            } else {
               world.func_147468_f(i, j - 1, k);
            }
         }
      } else if (entityplayer.field_71075_bZ.field_75098_d && world.func_147439_a(i, j + 1, k) == this) {
         world.func_147465_d(i, j + 1, k, Blocks.field_150350_a, 0, 2);
      }

      super.func_149681_a(world, i, j, k, meta, entityplayer);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.doublePlantBottomIcons = new IIcon[flowerNames.length];
      this.doublePlantTopIcons = new IIcon[flowerNames.length];

      for(int i = 0; i < this.doublePlantBottomIcons.length; ++i) {
         this.doublePlantBottomIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + flowerNames[i] + "_bottom");
         this.doublePlantTopIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + flowerNames[i] + "_top");
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.doublePlantBottomIcons.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   public int func_149643_k(World world, int i, int j, int k) {
      int l = world.func_72805_g(i, j, k);
      return isTop(l) ? getFlowerMeta(world.func_72805_g(i, j - 1, k)) : getFlowerMeta(l);
   }

   public boolean func_149851_a(World world, int i, int j, int k, boolean flag) {
      return true;
   }

   public boolean func_149852_a(World world, Random random, int i, int j, int k) {
      return true;
   }

   public void func_149853_b(World world, Random random, int i, int j, int k) {
      int meta = this.func_149885_e(world, i, j, k);
      this.func_149642_a(world, i, j, k, new ItemStack(this, 1, meta));
   }
}
