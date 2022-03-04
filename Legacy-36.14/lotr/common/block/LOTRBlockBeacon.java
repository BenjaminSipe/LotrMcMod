package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMatch;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockBeacon extends BlockContainer {
   public LOTRBlockBeacon() {
      super(Material.field_151575_d);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
      this.func_149711_c(0.0F);
      this.func_149752_b(5.0F);
      this.func_149672_a(Block.field_149766_f);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150344_f.func_149691_a(i, 0);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityBeacon();
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getBeaconRenderID();
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return this.func_149718_j(world, i, j, k);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
         world.func_147468_f(i, j, k);
      } else if (isLit(world, i, j, k) && world.func_147439_a(i, j + 1, k).func_149688_o() == Material.field_151586_h) {
         world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.fizz", 0.5F, 2.6F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.8F);
         if (!world.field_72995_K) {
            setLit(world, i, j, k, false);
         }
      }

   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      ItemStack itemstack = entityplayer.func_71045_bC();
      if (this.canItemLightBeacon(itemstack) && !isLit(world, i, j, k) && world.func_147439_a(i, j + 1, k).func_149688_o() != Material.field_151586_h) {
         world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "fire.ignite", 1.0F, world.field_73012_v.nextFloat() * 0.4F + 0.8F);
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            if (itemstack.func_77973_b().func_77645_m()) {
               itemstack.func_77972_a(1, entityplayer);
            } else if (itemstack.func_77976_d() > 1) {
               --itemstack.field_77994_a;
               if (itemstack.field_77994_a <= 0) {
                  entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
               }
            }
         }

         if (!world.field_72995_K) {
            setLit(world, i, j, k, true);
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.lightGondorBeacon);
         }

         return true;
      } else if (itemstack != null && itemstack.func_77973_b() == Items.field_151131_as && isLit(world, i, j, k)) {
         world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.fizz", 0.5F, 2.6F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.8F);
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, new ItemStack(Items.field_151133_ar));
         }

         if (!world.field_72995_K) {
            setLit(world, i, j, k, false);
         }

         return true;
      } else {
         entityplayer.openGui(LOTRMod.instance, 50, world, i, j, k);
         return true;
      }
   }

   private boolean canItemLightBeacon(ItemStack itemstack) {
      if (itemstack == null) {
         return false;
      } else {
         Item item = itemstack.func_77973_b();
         return item == Items.field_151033_d || item instanceof LOTRItemMatch || item instanceof ItemBlock && ((ItemBlock)item).field_150939_a instanceof BlockTorch;
      }
   }

   public int getLightValue(IBlockAccess world, int i, int j, int k) {
      return isFullyLit(world, i, j, k) ? 15 : 0;
   }

   public static boolean isLit(IBlockAccess world, int i, int j, int k) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityBeacon) {
         LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)tileentity;
         return beacon.isLit();
      } else {
         return false;
      }
   }

   public static boolean isFullyLit(IBlockAccess world, int i, int j, int k) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityBeacon) {
         LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)tileentity;
         return beacon.isFullyLit();
      } else {
         return false;
      }
   }

   public static void setLit(World world, int i, int j, int k, boolean lit) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityBeacon) {
         LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)tileentity;
         beacon.setLit(lit);
      }

   }

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      if (entity.func_70027_ad() && !isLit(world, i, j, k) && world.func_147439_a(i, j + 1, k).func_149688_o() != Material.field_151586_h) {
         world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "fire.ignite", 1.0F, world.field_73012_v.nextFloat() * 0.4F + 0.8F);
         if (!world.field_72995_K) {
            setLit(world, i, j, k, true);
            entity.func_70106_y();
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (isLit(world, i, j, k)) {
         if (random.nextInt(24) == 0) {
            world.func_72980_b((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
         }

         for(int l = 0; l < 3; ++l) {
            double d = (double)((float)i + random.nextFloat());
            double d1 = (double)j + (double)random.nextFloat() * 0.5D + 0.5D;
            double d2 = (double)((float)k + random.nextFloat());
            world.func_72869_a("largesmoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
         }

      }
   }
}
