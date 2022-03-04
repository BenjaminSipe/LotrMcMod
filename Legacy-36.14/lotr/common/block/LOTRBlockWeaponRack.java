package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockWeaponRack extends BlockContainer {
   public LOTRBlockWeaponRack() {
      super(Material.field_151594_q);
      this.func_149711_c(0.5F);
      this.func_149752_b(1.0F);
      this.func_149672_a(Block.field_149766_f);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityWeaponRack();
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150344_f.func_149691_a(i, 0);
   }

   @SideOnly(Side.CLIENT)
   public String func_149702_O() {
      return this.func_149641_N();
   }

   public boolean func_149686_d() {
      return false;
   }

   public boolean func_149662_c() {
      return false;
   }

   public int func_149645_b() {
      return -1;
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      float f = 0.2F;
      float h = 0.9F;
      int meta = world.func_72805_g(i, j, k);
      if (meta != 0 && meta != 2) {
         if (meta != 1 && meta != 3) {
            if (meta == 4) {
               this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, h, 1.0F - f * 2.0F);
            } else if (meta == 6) {
               this.func_149676_a(0.0F, 0.0F, f * 2.0F, 1.0F, h, 1.0F);
            } else if (meta == 5) {
               this.func_149676_a(f * 2.0F, 0.0F, 0.0F, 1.0F, h, 1.0F);
            } else if (meta == 7) {
               this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F - f * 2.0F, h, 1.0F);
            }
         } else {
            this.func_149676_a(f, 0.0F, 0.0F, 1.0F - f, h, 1.0F);
         }
      } else {
         this.func_149676_a(0.0F, 0.0F, f, 1.0F, h, 1.0F - f);
      }

   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityWeaponRack) {
         LOTRTileEntityWeaponRack rack = (LOTRTileEntityWeaponRack)tileentity;
         ItemStack heldItem = entityplayer.func_70694_bm();
         ItemStack rackItem = rack.getWeaponItem();
         if (rackItem != null) {
            if (!world.field_72995_K) {
               if (entityplayer.func_70694_bm() == null) {
                  entityplayer.func_70062_b(0, rackItem);
                  world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.pop", 0.2F, ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7F + 1.0F) * 2.0F);
               } else {
                  this.func_149642_a(world, i, j, k, rackItem);
               }

               rack.setWeaponItem((ItemStack)null);
            }

            return true;
         }

         if (rack.canAcceptItem(heldItem)) {
            if (!world.field_72995_K) {
               rack.setWeaponItem(heldItem.func_77946_l());
            }

            if (!entityplayer.field_71075_bZ.field_75098_d) {
               --heldItem.field_77994_a;
            }

            return true;
         }
      }

      return false;
   }

   public boolean func_149707_d(World world, int i, int j, int k, int side) {
      if (side == 1) {
         return world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
      } else if (side != 0) {
         ForgeDirection dir = ForgeDirection.getOrientation(side);
         int i1 = i - dir.offsetX;
         int k1 = k - dir.offsetZ;
         return world.func_147439_a(i1, j, k1).isSideSolid(world, i1, j, k1, dir);
      } else {
         return false;
      }
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      if ((meta & 4) == 0) {
         return this.func_149707_d(world, i, j, k, 1);
      } else {
         int l = meta & 3;
         int dir = Direction.field_71582_c[l];
         return this.func_149707_d(world, i, j, k, dir);
      }
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         int meta = world.func_72805_g(i, j, k);
         this.func_149697_b(world, i, j, k, meta, 0);
         world.func_147468_f(i, j, k);
      }

   }

   public int func_149660_a(World world, int i, int j, int k, int side, float f, float f1, float f2, int meta) {
      if (side == 1) {
         return 0;
      } else {
         return side != 0 ? Direction.field_71579_d[side] | 4 : 0;
      }
   }

   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
      int meta = world.func_72805_g(i, j, k);
      if ((meta & 4) == 0) {
         int rotation = MathHelper.func_76128_c((double)(entity.field_70177_z * 4.0F / 360.0F) + 2.5D) & 3;
         meta |= rotation;
         world.func_72921_c(i, j, k, meta, 2);
      }

   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      LOTRTileEntityWeaponRack rack = (LOTRTileEntityWeaponRack)world.func_147438_o(i, j, k);
      if (rack != null) {
         ItemStack weaponItem = rack.getWeaponItem();
         if (weaponItem != null) {
            this.func_149642_a(world, i, j, k, weaponItem);
         }
      }

      super.func_149749_a(world, i, j, k, block, meta);
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k, EntityPlayer entityplayer) {
      LOTRTileEntityWeaponRack rack = (LOTRTileEntityWeaponRack)world.func_147438_o(i, j, k);
      if (rack != null) {
         ItemStack weaponItem = rack.getWeaponItem();
         if (weaponItem != null) {
            return weaponItem.func_77946_l();
         }
      }

      return super.getPickBlock(target, world, i, j, k, entityplayer);
   }
}
