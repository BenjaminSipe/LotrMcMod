package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockClayMug extends LOTRBlockMug {
   public LOTRBlockClayMug() {
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150405_ch.func_149691_a(i, 0);
   }
}
