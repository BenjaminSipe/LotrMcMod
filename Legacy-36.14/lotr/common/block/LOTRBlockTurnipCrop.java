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

public class LOTRBlockTurnipCrop extends BlockCrops {
   @SideOnly(Side.CLIENT)
   private IIcon[] turnipIcons;

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j < 7) {
         if (j == 6) {
            j = 5;
         }

         return this.turnipIcons[j >> 1];
      } else {
         return this.turnipIcons[3];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.turnipIcons = new IIcon[4];

      for(int i = 0; i < this.turnipIcons.length; ++i) {
         this.turnipIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + i);
      }

   }

   public Item func_149866_i() {
      return LOTRMod.turnip;
   }

   public Item func_149865_P() {
      return LOTRMod.turnip;
   }

   public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
      return EnumPlantType.Crop;
   }
}
