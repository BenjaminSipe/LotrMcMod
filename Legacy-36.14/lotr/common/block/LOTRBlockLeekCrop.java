package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class LOTRBlockLeekCrop extends BlockCrops {
   @SideOnly(Side.CLIENT)
   private IIcon[] leekIcons;

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j < 7) {
         if (j == 6) {
            j = 5;
         }

         return this.leekIcons[j >> 1];
      } else {
         return this.leekIcons[3];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.leekIcons = new IIcon[4];

      for(int i = 0; i < this.leekIcons.length; ++i) {
         this.leekIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + i);
      }

   }

   public Item func_149866_i() {
      return LOTRMod.leek;
   }

   public Item func_149865_P() {
      return LOTRMod.leek;
   }

   public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
      return EnumPlantType.Crop;
   }
}
