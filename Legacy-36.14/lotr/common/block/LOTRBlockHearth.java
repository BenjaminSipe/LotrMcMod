package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockHearth extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] blockIcons;

   public LOTRBlockHearth() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 0) {
         return this.blockIcons[0];
      } else {
         return i == 1 ? this.blockIcons[1] : this.blockIcons[2];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.blockIcons = new IIcon[3];
      this.blockIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_bottom");
      this.blockIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.blockIcons[2] = iconregister.func_94245_a(this.func_149641_N() + "_side");
   }

   public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
      return side == ForgeDirection.UP;
   }

   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (world.func_147439_a(i, j + 1, k) == Blocks.field_150480_ab) {
         int smokeHeight = 5;

         for(int j1 = j + 1; j1 <= j + smokeHeight && !world.func_147439_a(i, j1, k).func_149688_o().func_76220_a(); ++j1) {
            for(int l = 0; l < 3; ++l) {
               float f = (float)i + random.nextFloat();
               float f1 = (float)j1 + random.nextFloat();
               float f2 = (float)k + random.nextFloat();
               world.func_72869_a("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
            }
         }
      }

   }
}
