package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class LOTRBlockPipeweedCrop extends BlockCrops {
   @SideOnly(Side.CLIENT)
   private IIcon[] pipeweedIcons;

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j < 7) {
         if (j == 6) {
            j = 5;
         }

         return this.pipeweedIcons[j >> 1];
      } else {
         return this.pipeweedIcons[3];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.pipeweedIcons = new IIcon[4];

      for(int i = 0; i < this.pipeweedIcons.length; ++i) {
         this.pipeweedIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + i);
      }

   }

   public Item func_149866_i() {
      return LOTRMod.pipeweedSeeds;
   }

   public Item func_149865_P() {
      return LOTRMod.pipeweedLeaf;
   }

   public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
      return EnumPlantType.Crop;
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (world.func_72805_g(i, j, k) == 7) {
         LOTRMod.pipeweedPlant.func_149734_b(world, i, j, k, random);
      }

   }
}
