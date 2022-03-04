package lotr.client.render.tileentity;

import lotr.common.block.LOTRBlockSpawnerChest;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class LOTRRenderSpawnerChest extends TileEntitySpecialRenderer {
   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntitySpawnerChest chest = (LOTRTileEntitySpawnerChest)tileentity;
      Block block = chest.func_145838_q();
      if (block instanceof LOTRBlockSpawnerChest) {
         LOTRBlockSpawnerChest scBlock = (LOTRBlockSpawnerChest)block;
         Block model = scBlock.chestModel;
         if (model instanceof ITileEntityProvider) {
            ITileEntityProvider itep = (ITileEntityProvider)model;
            TileEntity modelTE = itep.func_149915_a(chest.func_145831_w(), 0);
            modelTE.func_145834_a(chest.func_145831_w());
            modelTE.field_145851_c = chest.field_145851_c;
            modelTE.field_145848_d = chest.field_145848_d;
            modelTE.field_145849_e = chest.field_145849_e;
            TileEntityRendererDispatcher.field_147556_a.func_147547_b(modelTE).func_147500_a(modelTE, d, d1, d2, f);
         }
      }

   }
}
