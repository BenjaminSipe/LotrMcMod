package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockRope extends LOTRBlockLadder {
   private boolean canRetract;

   public LOTRBlockRope(boolean flag) {
      this.func_149711_c(0.4F);
      this.func_149672_a(Block.field_149775_l);
      this.canRetract = flag;
   }

   @SideOnly(Side.CLIENT)
   public String func_149702_O() {
      return this.func_149641_N();
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getRopeRenderID();
   }

   @SideOnly(Side.CLIENT)
   public boolean func_149646_a(IBlockAccess world, int i, int j, int k, int side) {
      if (side != 0 && side != 1) {
         return true;
      } else {
         Block block = world.func_147439_a(i, j, k);
         return block != this && !block.func_149662_c();
      }
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return world.func_147439_a(i, j + 1, k) == this || super.func_149742_c(world, i, j, k);
   }

   public int func_149660_a(World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ, int meta) {
      int placeMeta = super.func_149660_a(world, i, j, k, side, hitX, hitY, hitZ, meta);
      if (placeMeta == 0 && world.func_147439_a(i, j + 1, k) == this) {
         placeMeta = world.func_72805_g(i, j + 1, k);
      }

      return placeMeta;
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return this.func_149742_c(world, i, j, k);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (world.func_147439_a(i, j + 1, k) != this) {
         super.func_149695_a(world, i, j, k, block);
      }

   }

   private boolean canExtendRopeWithMetadata(World world, int i, int j, int k, int meta) {
      if (world.func_147439_a(i, j + 1, k) == this) {
         return true;
      } else if (meta == 2) {
         return world.isSideSolid(i, j, k + 1, ForgeDirection.NORTH);
      } else if (meta == 3) {
         return world.isSideSolid(i, j, k - 1, ForgeDirection.SOUTH);
      } else if (meta == 4) {
         return world.isSideSolid(i + 1, j, k, ForgeDirection.WEST);
      } else {
         return meta == 5 ? world.isSideSolid(i - 1, j, k, ForgeDirection.EAST) : false;
      }
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      boolean lookingUpOrDown = entityplayer.field_70125_A <= 0.0F;
      int lookDir = lookingUpOrDown ? 1 : -1;
      ItemStack itemstack = entityplayer.func_70694_bm();
      if (itemstack != null && itemstack.func_77973_b() == Item.func_150898_a(this)) {
         int j1;
         Block block;
         for(j1 = j; j1 >= 0 && j1 < world.func_72800_K(); j1 += lookDir) {
            block = world.func_147439_a(i, j1, k);
            if (block != this) {
               break;
            }
         }

         if (j1 >= 0 && j1 < world.func_72800_K()) {
            block = world.func_147439_a(i, j1, k);
            if (this.func_149707_d(world, i, j1, k, side) && block.isReplaceable(world, i, j1, k) && !block.func_149688_o().func_76224_d()) {
               int thisMeta = world.func_72805_g(i, j, k);
               if (this.canExtendRopeWithMetadata(world, i, j1, k, thisMeta)) {
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
      } else if (!entityplayer.func_70617_f_() && this.canRetract) {
         if (!world.field_72995_K) {
            boolean invAdded = false;

            for(int j1 = j; j1 >= 0 && j1 < world.func_72800_K(); j1 += lookDir) {
               Block block = world.func_147439_a(i, j1, k);
               int meta = world.func_72805_g(i, j1, k);
               if (block != this) {
                  break;
               }

               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  List drops = block.getDrops(world, i, j1, k, meta, 0);
                  Iterator var18 = drops.iterator();

                  while(var18.hasNext()) {
                     ItemStack drop = (ItemStack)var18.next();
                     if (entityplayer.field_71071_by.func_70441_a(drop)) {
                        invAdded = true;
                     } else {
                        entityplayer.func_71019_a(drop, false);
                     }
                  }
               }

               world.func_147468_f(i, j1, k);
               world.func_72908_a((double)((float)i + 0.5F), (double)((float)j1 + 0.5F), (double)((float)k + 0.5F), this.field_149762_H.func_150495_a(), (this.field_149762_H.func_150497_c() + 1.0F) / 2.0F, this.field_149762_H.func_150494_d() * 0.8F);
            }

            if (invAdded) {
               ((EntityPlayerMP)entityplayer).field_71070_bA.func_75142_b();
            }
         }

         return true;
      }

      return false;
   }
}
