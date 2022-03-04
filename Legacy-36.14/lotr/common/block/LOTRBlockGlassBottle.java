package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockGlassBottle extends LOTRBlockMug {
   public LOTRBlockGlassBottle() {
      super(3.0F, 10.0F);
      this.func_149672_a(Block.field_149778_k);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150359_w.func_149691_a(i, 0);
   }
}
