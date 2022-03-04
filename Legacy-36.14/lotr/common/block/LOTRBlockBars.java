package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockBars extends LOTRBlockPane {
   public LOTRBlockBars() {
      super("", "", Material.field_151573_f, true);
      this.func_149711_c(5.0F);
      this.func_149752_b(10.0F);
      this.func_149672_a(Block.field_149777_j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.field_149761_L = iconregister.func_94245_a(this.func_149641_N());
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_150097_e() {
      return this.field_149761_L;
   }
}
