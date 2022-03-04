package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.block.LOTRBlockPortal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public abstract class LOTRTileEntityPortalBase extends TileEntity {
   public void func_145845_h() {
      if (!this.field_145850_b.field_72995_K) {
         LOTRBlockPortal portalBlock = (LOTRBlockPortal)this.func_145838_q();

         for(int i1 = this.field_145851_c - 1; i1 <= this.field_145851_c + 1; ++i1) {
            for(int k1 = this.field_145849_e - 1; k1 <= this.field_145849_e + 1; ++k1) {
               if (portalBlock.isValidPortalLocation(this.field_145850_b, i1, this.field_145848_d, k1, true)) {
                  return;
               }
            }
         }

         this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      }

   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.func_72330_a((double)(this.field_145851_c - 1), (double)this.field_145848_d, (double)(this.field_145849_e - 1), (double)(this.field_145851_c + 2), (double)(this.field_145848_d + 2), (double)(this.field_145849_e + 2));
   }
}
