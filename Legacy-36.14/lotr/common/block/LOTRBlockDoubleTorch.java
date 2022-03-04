package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockDoubleTorch extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] torchIcons;
   public Item torchItem;

   public LOTRBlockDoubleTorch() {
      super(Material.field_151594_q);
      this.func_149711_c(0.0F);
      this.func_149672_a(Block.field_149766_f);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return j == 1 ? this.torchIcons[1] : this.torchIcons[0];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.torchIcons = new IIcon[2];
      this.torchIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_bottom");
      this.torchIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_top");
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getDoubleTorchRenderID();
   }

   public Item func_149650_a(int i, Random random, int j) {
      return i == 0 ? this.torchItem : null;
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         int meta = world.func_72805_g(i, j, k);
         if (meta == 0) {
            this.func_149697_b(world, i, j, k, meta, 0);
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
         return l == 1 ? world.func_147439_a(i, j - 1, k) == this : world.func_147439_a(i, j + 1, k) == this && canPlaceTorchOn(world, i, j - 1, k);
      }
   }

   public static boolean canPlaceTorchOn(World world, int i, int j, int k) {
      return world.func_147439_a(i, j, k).canPlaceTorchOnTop(world, i, j, k);
   }

   public void func_149681_a(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
      if (meta == 1) {
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
   public AxisAlignedBB func_149633_g(World world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      if (meta == 0) {
         this.func_149676_a(0.4F, 0.0F, 0.4F, 0.6F, 1.0F, 0.6F);
      } else if (meta == 1) {
         this.func_149676_a(0.4F, 0.0F, 0.4F, 0.6F, 0.5375F, 0.6F);
      }

      return super.func_149633_g(world, i, j, k);
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      if (meta == 0) {
         this.func_149676_a(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
      } else if (meta == 1) {
         this.func_149676_a(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.5F, 0.5625F);
      }

   }

   public int getLightValue(IBlockAccess world, int i, int j, int k) {
      return world.func_72805_g(i, j, k) == 1 ? 14 : 0;
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (world.func_72805_g(i, j, k) == 1) {
         double d = (double)i + 0.5D;
         double d1 = (double)j + 0.6D;
         double d2 = (double)k + 0.5D;
         world.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
         world.func_72869_a("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
      }

   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return this.torchItem;
   }
}
