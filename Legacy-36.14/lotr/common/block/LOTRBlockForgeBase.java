package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityForgeBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class LOTRBlockForgeBase extends BlockContainer {
   @SideOnly(Side.CLIENT)
   private IIcon[] forgeIcons;

   public LOTRBlockForgeBase() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
      this.func_149711_c(4.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   public void func_149726_b(World world, int i, int j, int k) {
      super.func_149726_b(world, i, j, k);
      this.setDefaultDirection(world, i, j, k);
   }

   private void setDefaultDirection(World world, int i, int j, int k) {
      if (!world.field_72995_K) {
         Block i1 = world.func_147439_a(i, j, k - 1);
         Block j1 = world.func_147439_a(i, j, k + 1);
         Block k1 = world.func_147439_a(i - 1, j, k);
         Block l1 = world.func_147439_a(i + 1, j, k);
         byte meta = 3;
         if (i1.func_149662_c() && !j1.func_149662_c()) {
            meta = 3;
         }

         if (j1.func_149662_c() && !i1.func_149662_c()) {
            meta = 2;
         }

         if (k1.func_149662_c() && !l1.func_149662_c()) {
            meta = 5;
         }

         if (l1.func_149662_c() && !k1.func_149662_c()) {
            meta = 4;
         }

         world.func_72921_c(i, j, k, meta, 2);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      if (side != 1 && side != 0) {
         int meta = world.func_72805_g(i, j, k) & 7;
         return side != meta ? this.forgeIcons[0] : (isForgeActive(world, i, j, k) ? this.forgeIcons[3] : this.forgeIcons[2]);
      } else {
         return this.forgeIcons[1];
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i != 1 && i != 0 ? (i == 3 ? this.forgeIcons[2] : this.forgeIcons[0]) : this.forgeIcons[1];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.forgeIcons = new IIcon[4];
      this.forgeIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.forgeIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.forgeIcons[2] = iconregister.func_94245_a(this.func_149641_N() + "_front");
      this.forgeIcons[3] = iconregister.func_94245_a(this.func_149641_N() + "_active");
   }

   protected abstract boolean useLargeSmoke();

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (isForgeActive(world, i, j, k)) {
         int meta = world.func_72805_g(i, j, k) & 7;
         float f = (float)i + 0.5F;
         float f1 = (float)j + 0.0F + random.nextFloat() * 6.0F / 16.0F;
         float f2 = (float)k + 0.5F;
         float f3 = 0.52F;
         float f4 = random.nextFloat() * 0.6F - 0.3F;
         if (meta == 4) {
            world.func_72869_a("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            world.func_72869_a("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
         } else if (meta == 5) {
            world.func_72869_a("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            world.func_72869_a("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
         } else if (meta == 2) {
            world.func_72869_a("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            world.func_72869_a("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
         } else if (meta == 3) {
            world.func_72869_a("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            world.func_72869_a("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
         }

         if (this.useLargeSmoke()) {
            for(int l = 0; l < 6; ++l) {
               float f10 = random.nextBoolean() ? 0.0F : 1.0F;
               float f11 = random.nextBoolean() ? 0.0F : 1.0F;
               float f12 = 0.5F;
               f10 += -0.1F + random.nextFloat() * 0.2F;
               f11 += -0.1F + random.nextFloat() * 0.2F;
               if (random.nextInt(3) > 0) {
                  world.func_72869_a("largesmoke", (double)((float)i + f10), (double)((float)j + f12), (double)((float)k + f11), 0.0D, 0.0D, 0.0D);
               } else {
                  world.func_72869_a("smoke", (double)((float)i + f10), (double)((float)j + f12), (double)((float)k + f11), 0.0D, 0.0D, 0.0D);
               }
            }
         }
      }

   }

   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
      int rotation = MathHelper.func_76128_c((double)(entity.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
      if (rotation == 0) {
         world.func_72921_c(i, j, k, 2, 2);
      }

      if (rotation == 1) {
         world.func_72921_c(i, j, k, 5, 2);
      }

      if (rotation == 2) {
         world.func_72921_c(i, j, k, 3, 2);
      }

      if (rotation == 3) {
         world.func_72921_c(i, j, k, 4, 2);
      }

      if (itemstack.func_82837_s()) {
         ((LOTRTileEntityForgeBase)world.func_147438_o(i, j, k)).setSpecialForgeName(itemstack.func_82833_r());
      }

   }

   public int getLightValue(IBlockAccess world, int i, int j, int k) {
      return isForgeActive(world, i, j, k) ? 13 : 0;
   }

   public static boolean isForgeActive(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      return (meta & 8) != 0;
   }

   public static void toggleForgeActive(World world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      world.func_72921_c(i, j, k, meta ^ 8, 2);
      world.func_147463_c(EnumSkyBlock.Block, i, j, k);
   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      LOTRTileEntityForgeBase forge = (LOTRTileEntityForgeBase)world.func_147438_o(i, j, k);
      if (forge != null) {
         LOTRMod.dropContainerItems(forge, world, i, j, k);
         world.func_147453_f(i, j, k, block);
      }

      super.func_149749_a(world, i, j, k, block, meta);
   }

   public boolean func_149740_M() {
      return true;
   }

   public int func_149736_g(World world, int i, int j, int k, int direction) {
      return Container.func_94526_b((IInventory)world.func_147438_o(i, j, k));
   }
}
