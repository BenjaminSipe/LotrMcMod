package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.block.LOTRBlockSignCarved;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRTileEntitySignCarved extends LOTRTileEntitySign {
   public int getNumLines() {
      return 8;
   }

   public IIcon getOnBlockIcon() {
      World world = this.func_145831_w();
      Block block = this.func_145838_q();
      if (block instanceof LOTRBlockSignCarved) {
         LOTRBlockSignCarved signBlock = (LOTRBlockSignCarved)block;
         int meta = this.func_145832_p();
         int i = this.field_145851_c;
         int j = this.field_145848_d;
         int k = this.field_145849_e;
         return signBlock.getOnBlockIcon(world, i, j, k, meta);
      } else {
         return Blocks.field_150348_b.func_149691_a(0, 0);
      }
   }

   @SideOnly(Side.CLIENT)
   public double func_145833_n() {
      return 1600.0D;
   }
}
