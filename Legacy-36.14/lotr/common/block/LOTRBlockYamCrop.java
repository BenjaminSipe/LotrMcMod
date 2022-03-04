package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockYamCrop extends BlockCrops {
   @SideOnly(Side.CLIENT)
   private IIcon[] yamIcons;

   public boolean func_149718_j(World world, int i, int j, int k) {
      return world.func_72805_g(i, j, k) == 8 ? world.func_147439_a(i, j - 1, k).canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, Blocks.field_150329_H) : super.func_149718_j(world, i, j, k);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j < 7) {
         if (j == 6) {
            j = 5;
         }

         return this.yamIcons[j >> 1];
      } else {
         return this.yamIcons[3];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.yamIcons = new IIcon[4];

      for(int i = 0; i < this.yamIcons.length; ++i) {
         this.yamIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + i);
      }

   }

   public Item func_149866_i() {
      return LOTRMod.yam;
   }

   public Item func_149865_P() {
      return LOTRMod.yam;
   }

   public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
      return EnumPlantType.Crop;
   }
}
