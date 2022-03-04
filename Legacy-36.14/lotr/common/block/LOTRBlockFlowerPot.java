package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityFlowerPot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockFlowerPot extends BlockFlowerPot implements ITileEntityProvider {
   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityFlowerPot();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150457_bL.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getFlowerPotRenderID();
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
      return getPlant(world, i, j, k);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      return false;
   }

   public void func_149681_a(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
      if (entityplayer.field_71075_bZ.field_75098_d) {
         meta |= 8;
         world.func_72921_c(i, j, k, meta, 4);
      }

      super.func_149681_a(world, i, j, k, meta, entityplayer);
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      drops.add(new ItemStack(Items.field_151162_bE));
      if ((meta & 8) == 0) {
         ItemStack itemstack = getPlant(world, i, j, k);
         if (itemstack != null) {
            LOTRTileEntityFlowerPot pot = (LOTRTileEntityFlowerPot)world.func_147438_o(i, j, k);
            if (pot != null) {
               drops.add(itemstack);
            }
         }
      }

      return drops;
   }

   public static boolean canAcceptPlant(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      if (item instanceof ItemBlock) {
         Block block = ((ItemBlock)item).field_150939_a;
         return block instanceof LOTRBlockFlower;
      } else {
         return false;
      }
   }

   public static void setPlant(World world, int i, int j, int k, ItemStack itemstack) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityFlowerPot) {
         LOTRTileEntityFlowerPot flowerPot = (LOTRTileEntityFlowerPot)tileentity;
         flowerPot.item = itemstack.func_77973_b();
         flowerPot.meta = itemstack.func_77960_j();
         world.func_147471_g(i, j, k);
      }

   }

   public static ItemStack getPlant(IBlockAccess world, int i, int j, int k) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity != null && tileentity instanceof LOTRTileEntityFlowerPot) {
         LOTRTileEntityFlowerPot flowerPot = (LOTRTileEntityFlowerPot)tileentity;
         return flowerPot.item == null ? null : new ItemStack(flowerPot.item, 1, flowerPot.meta);
      } else {
         return null;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (getPlant(world, i, j, k) != null && getPlant(world, i, j, k).func_77973_b() == Item.func_150898_a(LOTRMod.pipeweedPlant)) {
         double d = (double)i + 0.2D + (double)(random.nextFloat() * 0.6F);
         double d1 = (double)j + 0.625D + (double)(random.nextFloat() * 0.1875F);
         double d2 = (double)k + 0.2D + (double)(random.nextFloat() * 0.6F);
         world.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
      }

      if (getPlant(world, i, j, k) != null && getPlant(world, i, j, k).func_77973_b() == Item.func_150898_a(LOTRMod.corruptMallorn)) {
         LOTRMod.corruptMallorn.func_149734_b(world, i, j, k, random);
      }

   }
}
