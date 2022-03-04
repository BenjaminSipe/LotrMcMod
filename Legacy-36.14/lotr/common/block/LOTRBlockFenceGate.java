package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.util.IIcon;

public class LOTRBlockFenceGate extends BlockFenceGate {
   private Block plankBlock;
   private int plankMeta;

   public LOTRBlockFenceGate(Block block, int meta) {
      this.plankBlock = block;
      this.plankMeta = meta;
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
      this.func_149711_c(2.0F);
      this.func_149752_b(5.0F);
      this.func_149672_a(field_149766_f);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return this.plankBlock.func_149691_a(i, this.plankMeta);
   }
}
