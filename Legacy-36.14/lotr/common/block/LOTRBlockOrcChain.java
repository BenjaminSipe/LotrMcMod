package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockOrcChain extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon iconMiddle;
   @SideOnly(Side.CLIENT)
   private IIcon iconTop;
   @SideOnly(Side.CLIENT)
   private IIcon iconBottom;
   @SideOnly(Side.CLIENT)
   private IIcon iconSingle;

   public LOTRBlockOrcChain() {
      super(Material.field_151594_q);
      this.func_149711_c(1.0F);
      this.func_149672_a(Block.field_149777_j);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
      float f = 0.2F;
      this.func_149676_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.iconMiddle = iconregister.func_94245_a(this.func_149641_N() + "_mid");
      this.iconTop = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.iconBottom = iconregister.func_94245_a(this.func_149641_N() + "_bottom");
      this.iconSingle = iconregister.func_94245_a(this.func_149641_N() + "_single");
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      Block above = world.func_147439_a(i, j + 1, k);
      Block below = world.func_147439_a(i, j - 1, k);
      boolean chainAbove = above instanceof LOTRBlockOrcChain;
      boolean chainBelow = below instanceof LOTRBlockOrcChain || below instanceof LOTRBlockChandelier;
      if (chainAbove && chainBelow) {
         return this.iconMiddle;
      } else if (chainAbove) {
         return this.iconBottom;
      } else {
         return chainBelow ? this.iconTop : this.iconSingle;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return this.iconMiddle;
   }

   @SideOnly(Side.CLIENT)
   public String func_149702_O() {
      return this.func_149641_N();
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getOrcChainRenderID();
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j + 1, k);
      int meta = world.func_72805_g(i, j + 1, k);
      if (block instanceof LOTRBlockOrcChain) {
         return true;
      } else if (!(block instanceof BlockFence) && !(block instanceof BlockWall)) {
         if (block instanceof BlockSlab && !block.func_149662_c() && (meta & 8) == 0) {
            return true;
         } else {
            return block instanceof BlockStairs && (meta & 4) == 0 ? true : world.func_147439_a(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
         }
      } else {
         return true;
      }
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return this.func_149742_c(world, i, j, k);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         int meta = world.func_72805_g(i, j, k);
         this.func_149697_b(world, i, j, k, meta, 0);
         world.func_147468_f(i, j, k);
      }

      super.func_149695_a(world, i, j, k, block);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      ItemStack itemstack = entityplayer.func_70694_bm();
      if (itemstack != null && itemstack.func_77973_b() == Item.func_150898_a(this)) {
         int j1;
         Block block;
         for(j1 = j; j1 >= 0 && j1 < world.func_72800_K(); --j1) {
            block = world.func_147439_a(i, j1, k);
            if (block != this) {
               break;
            }
         }

         if (j1 >= 0 && j1 < world.func_72800_K()) {
            block = world.func_147439_a(i, j1, k);
            if (this.func_149707_d(world, i, j1, k, side) && block.isReplaceable(world, i, j1, k) && !block.func_149688_o().func_76224_d()) {
               int thisMeta = world.func_72805_g(i, j, k);
               world.func_147465_d(i, j1, k, this, thisMeta, 3);
               world.func_72908_a((double)((float)i + 0.5F), (double)((float)j1 + 0.5F), (double)((float)k + 0.5F), this.field_149762_H.func_150496_b(), (this.field_149762_H.func_150497_c() + 1.0F) / 2.0F, this.field_149762_H.func_150494_d() * 0.8F);
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  --itemstack.field_77994_a;
               }

               if (itemstack.field_77994_a <= 0) {
                  entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
               }

               return true;
            }
         }
      }

      return false;
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      float f = 0.01F;
      return AxisAlignedBB.func_72330_a((double)((float)i + 0.5F - f), (double)j, (double)((float)k + 0.5F - f), (double)((float)i + 0.5F + f), (double)(j + 1), (double)((float)k + 0.5F + f));
   }

   public boolean isLadder(IBlockAccess world, int i, int j, int k, EntityLivingBase entity) {
      return true;
   }
}
