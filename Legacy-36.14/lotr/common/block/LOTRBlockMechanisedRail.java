package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.Block.SoundType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTRBlockMechanisedRail extends BlockRailBase {
   @SideOnly(Side.CLIENT)
   private IIcon iconOn;
   @SideOnly(Side.CLIENT)
   private IIcon iconOff;
   private boolean defaultPower;

   public LOTRBlockMechanisedRail(boolean power) {
      super(true);
      this.func_149711_c(0.7F);
      this.func_149672_a(field_149777_j);
      this.defaultPower = power;
      this.func_149647_a((CreativeTabs)null);
   }

   public boolean isPowerOn(int meta) {
      return (meta & 8) == 0 == this.defaultPower;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int side, int meta) {
      return this.isPowerOn(meta) ? this.iconOn : this.iconOff;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister register) {
      this.iconOn = register.func_94245_a(this.func_149641_N() + "_on");
      this.iconOff = register.func_94245_a(this.func_149641_N() + "_off");
      this.field_149761_L = this.iconOff;
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      drops.add(new ItemStack(Blocks.field_150448_aq));
      drops.add(new ItemStack(LOTRMod.mechanism));
      return drops;
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      ItemStack curItem = entityplayer.func_70694_bm();
      if (curItem != null && curItem.func_77973_b() instanceof ItemMinecart) {
         return false;
      } else {
         Block setBlock = this == LOTRMod.mechanisedRailOff ? LOTRMod.mechanisedRailOn : LOTRMod.mechanisedRailOff;
         int setMeta = world.func_72805_g(i, j, k);
         world.func_147465_d(i, j, k, setBlock, setMeta, 3);
         boolean isNowPowered = ((LOTRBlockMechanisedRail)setBlock).isPowerOn(setMeta);
         world.func_72908_a((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), "random.click", 0.3F, isNowPowered ? 0.6F : 0.5F);
         return true;
      }
   }

   public boolean onShiftClickActivateFirst(World world, int i, int j, int k, EntityPlayer entityplayer, int side) {
      ItemStack curItem = entityplayer.func_70694_bm();
      if (curItem != null && curItem.func_77973_b() instanceof ItemMinecart) {
         return false;
      } else {
         Block setBlock = Blocks.field_150448_aq;
         world.func_147465_d(i, j, k, setBlock, this.getBasicRailMetadata(world, (EntityMinecart)null, i, j, k), 3);
         SoundType sound = setBlock.field_149762_H;
         world.func_72908_a((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), sound.func_150496_b(), (sound.func_150497_c() + 1.0F) / 2.0F, sound.func_150494_d() * 0.8F);
         if (!world.field_72995_K) {
            this.func_149642_a(world, i, j, k, new ItemStack(LOTRMod.mechanism));
         }

         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random rand) {
      int meta = world.func_72805_g(i, j, k);
      boolean power = this.isPowerOn(meta);
      if (power) {
         int dir = meta & 7;
         Vec3 corner1 = Vec3.func_72443_a(0.0D, 0.0D, 0.0D);
         Vec3 corner2 = Vec3.func_72443_a(0.0D, 0.0D, 0.0D);
         Vec3 corner3 = Vec3.func_72443_a(0.0D, 0.0D, 0.0D);
         Vec3 corner4 = Vec3.func_72443_a(0.0D, 0.0D, 0.0D);
         if (dir == 0) {
            corner1 = Vec3.func_72443_a(-0.4D, 0.0D, -0.5D);
            corner2 = Vec3.func_72443_a(-0.4D, 0.0D, 0.5D);
            corner3 = Vec3.func_72443_a(0.4D, 0.0D, -0.5D);
            corner4 = Vec3.func_72443_a(0.4D, 0.0D, 0.5D);
         } else if (dir == 1) {
            corner1 = Vec3.func_72443_a(-0.5D, 0.0D, -0.4D);
            corner2 = Vec3.func_72443_a(0.5D, 0.0D, -0.4D);
            corner3 = Vec3.func_72443_a(-0.5D, 0.0D, 0.4D);
            corner4 = Vec3.func_72443_a(0.5D, 0.0D, 0.4D);
         } else if (dir == 2) {
            corner1 = Vec3.func_72443_a(-0.5D, 0.0D, -0.4D);
            corner2 = Vec3.func_72443_a(0.5D, 1.0D, -0.4D);
            corner3 = Vec3.func_72443_a(-0.5D, 0.0D, 0.4D);
            corner4 = Vec3.func_72443_a(0.5D, 1.0D, 0.4D);
         } else if (dir == 3) {
            corner1 = Vec3.func_72443_a(-0.5D, 1.0D, -0.4D);
            corner2 = Vec3.func_72443_a(0.5D, 0.0D, -0.4D);
            corner3 = Vec3.func_72443_a(-0.5D, 1.0D, 0.4D);
            corner4 = Vec3.func_72443_a(0.5D, 0.0D, 0.4D);
         } else if (dir == 4) {
            corner1 = Vec3.func_72443_a(-0.4D, 1.0D, -0.5D);
            corner2 = Vec3.func_72443_a(-0.4D, 0.0D, 0.5D);
            corner3 = Vec3.func_72443_a(0.4D, 1.0D, -0.5D);
            corner4 = Vec3.func_72443_a(0.4D, 0.0D, 0.5D);
         } else if (dir == 5) {
            corner1 = Vec3.func_72443_a(-0.4D, 0.0D, -0.5D);
            corner2 = Vec3.func_72443_a(-0.4D, 1.0D, 0.5D);
            corner3 = Vec3.func_72443_a(0.4D, 0.0D, -0.5D);
            corner4 = Vec3.func_72443_a(0.4D, 1.0D, 0.5D);
         }

         for(int l = 0; l < 1; ++l) {
            float t1 = rand.nextFloat();
            float t2 = rand.nextFloat();
            Vec3 edge1 = this.lerp(corner1, corner2, t1).func_72441_c((double)i + 0.5D, (double)j, (double)k + 0.5D);
            Vec3 edge2 = this.lerp(corner3, corner4, t2).func_72441_c((double)i + 0.5D, (double)j, (double)k + 0.5D);
            world.func_72869_a("smoke", edge1.field_72450_a, edge1.field_72448_b, edge1.field_72449_c, 0.0D, 0.1D, 0.0D);
            world.func_72869_a("smoke", edge2.field_72450_a, edge2.field_72448_b, edge2.field_72449_c, 0.0D, 0.1D, 0.0D);
         }
      }

   }

   private Vec3 lerp(Vec3 vec1, Vec3 vec2, float t) {
      return Vec3.func_72443_a(vec1.field_72450_a + (vec2.field_72450_a - vec1.field_72450_a) * (double)t, vec1.field_72448_b + (vec2.field_72448_b - vec1.field_72448_b) * (double)t, vec1.field_72449_c + (vec2.field_72449_c - vec1.field_72449_c) * (double)t);
   }

   protected boolean isPoweredRailAdjacent(World world, int i, int j, int k, int meta, boolean forwardOrBack, int recursion) {
      if (recursion >= 8) {
         return false;
      } else {
         int dir = meta & 7;
         boolean flat = true;
         int axis = 0;
         switch(dir) {
         case 0:
            if (forwardOrBack) {
               ++k;
            } else {
               --k;
            }

            axis = 0;
            break;
         case 1:
            if (forwardOrBack) {
               --i;
            } else {
               ++i;
            }

            axis = 0;
            break;
         case 2:
            if (forwardOrBack) {
               --i;
            } else {
               ++i;
               ++j;
               flat = false;
            }

            axis = 1;
            break;
         case 3:
            if (forwardOrBack) {
               --i;
               ++j;
               flat = false;
            } else {
               ++i;
            }

            axis = 1;
            break;
         case 4:
            if (forwardOrBack) {
               ++k;
            } else {
               --k;
               ++j;
               flat = false;
            }

            axis = 0;
            break;
         case 5:
            if (forwardOrBack) {
               ++k;
               ++j;
               flat = false;
            } else {
               --k;
            }

            axis = 0;
         }

         return this.isPoweredRailAt(world, i, j, k, forwardOrBack, recursion, axis) ? true : flat && this.isPoweredRailAt(world, i, j - 1, k, forwardOrBack, recursion, axis);
      }
   }

   protected boolean isPoweredRailAt(World world, int i, int j, int k, boolean forwardOrBack, int recursion, int axis) {
      Block block = world.func_147439_a(i, j, k);
      if (block == this) {
         int j1 = world.func_72805_g(i, j, k);
         int k1 = j1 & 7;
         if (axis == 1 && (k1 == 0 || k1 == 4 || k1 == 5)) {
            return false;
         }

         if (axis == 0 && (k1 == 1 || k1 == 2 || k1 == 3)) {
            return false;
         }

         if ((j1 & 8) != 0) {
            if (world.func_72864_z(i, j, k)) {
               return true;
            }

            return this.isPoweredRailAdjacent(world, i, j, k, j1, forwardOrBack, recursion + 1);
         }
      }

      return false;
   }

   protected void func_150048_a(World world, int i, int j, int k, int meta, int dir, Block block) {
      boolean powered = world.func_72864_z(i, j, k);
      powered = powered || this.isPoweredRailAdjacent(world, i, j, k, meta, true, 0) || this.isPoweredRailAdjacent(world, i, j, k, meta, false, 0);
      boolean powerToggled = false;
      if (powered && (meta & 8) == 0) {
         world.func_72921_c(i, j, k, dir | 8, 3);
         powerToggled = true;
      } else if (!powered && (meta & 8) != 0) {
         world.func_72921_c(i, j, k, dir, 3);
         powerToggled = true;
      }

      if (powerToggled) {
         world.func_147459_d(i, j - 1, k, this);
         if (dir == 2 || dir == 3 || dir == 4 || dir == 5) {
            world.func_147459_d(i, j + 1, k, this);
         }
      }

   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
      return new ItemStack(LOTRMod.mechanism);
   }
}
