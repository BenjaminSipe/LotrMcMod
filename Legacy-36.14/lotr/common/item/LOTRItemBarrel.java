package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.entity.item.LOTREntityBarrel;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTRItemBarrel extends ItemBlock {
   public LOTRItemBarrel(Block block) {
      super(block);
   }

   public static void setBarrelData(ItemStack itemstack, NBTTagCompound nbt) {
      itemstack.func_77982_d(new NBTTagCompound());
      itemstack.func_77978_p().func_74782_a("LOTRBarrelData", nbt);
   }

   public static NBTTagCompound getBarrelData(ItemStack itemstack) {
      if (itemstack.func_77942_o() && itemstack.func_77978_p().func_74764_b("LOTRBarrelData")) {
         NBTTagCompound barrelData = itemstack.func_77978_p().func_74775_l("LOTRBarrelData");
         return barrelData;
      } else {
         return null;
      }
   }

   public static void setBarrelDataFromTE(ItemStack itemstack, LOTRTileEntityBarrel barrel) {
      NBTTagCompound nbt = new NBTTagCompound();
      barrel.writeBarrelToNBT(nbt);
      setBarrelData(itemstack, nbt);
   }

   public static void loadBarrelDataToTE(ItemStack itemstack, LOTRTileEntityBarrel barrel) {
      NBTTagCompound nbt = getBarrelData(itemstack);
      if (nbt != null) {
         barrel.readBarrelFromNBT(nbt);
      }

   }

   public int getItemStackLimit(ItemStack itemstack) {
      NBTTagCompound nbt = getBarrelData(itemstack);
      return nbt != null ? 1 : super.getItemStackLimit(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      NBTTagCompound barrelData = getBarrelData(itemstack);
      if (barrelData != null) {
         LOTRTileEntityBarrel tileEntity = new LOTRTileEntityBarrel();
         tileEntity.readBarrelFromNBT(barrelData);
         list.add(tileEntity.getInvSubtitle());
      }

   }

   public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int metadata) {
      if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
         TileEntity tileentity = world.func_147438_o(i, j, k);
         if (tileentity instanceof LOTRTileEntityBarrel) {
            LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)tileentity;
            loadBarrelDataToTE(itemstack, barrel);
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      MovingObjectPosition m = this.func_77621_a(world, entityplayer, true);
      if (m == null) {
         return itemstack;
      } else {
         if (m.field_72313_a == MovingObjectType.BLOCK) {
            int i = m.field_72311_b;
            int j = m.field_72312_c;
            int k = m.field_72309_d;
            if (world.func_147439_a(i, j, k).func_149688_o() != Material.field_151586_h || world.func_72805_g(i, j, k) != 0) {
               return itemstack;
            }

            LOTREntityBarrel barrel = new LOTREntityBarrel(world, (double)((float)i + 0.5F), (double)((float)j + 1.0F), (double)((float)k + 0.5F));
            barrel.field_70177_z = (float)((MathHelper.func_76128_c((double)(entityplayer.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3) - 1) * 90.0F;
            if (!world.func_72945_a(barrel, barrel.field_70121_D.func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty()) {
               return itemstack;
            }

            if (!world.field_72995_K) {
               NBTTagCompound barrelData = getBarrelData(itemstack);
               barrel.barrelItemData = barrelData;
               world.func_72838_d(barrel);
            }

            if (!entityplayer.field_71075_bZ.field_75098_d) {
               --itemstack.field_77994_a;
            }
         }

         return itemstack;
      }
   }
}
