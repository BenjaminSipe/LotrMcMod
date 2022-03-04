package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSandstone extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon iconTop;
   @SideOnly(Side.CLIENT)
   private IIcon iconBottom;

   public LOTRBlockSandstone() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149672_a(Block.field_149769_e);
      this.func_149711_c(0.8F);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      super.func_149651_a(iconregister);
      this.iconTop = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.iconBottom = iconregister.func_94245_a(this.func_149641_N() + "_bottom");
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 0) {
         return this.iconBottom;
      } else {
         return i == 1 ? this.iconTop : this.field_149761_L;
      }
   }
}
