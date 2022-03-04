package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LOTRTileEntitySignCarvedIthildin extends LOTRTileEntitySignCarved {
   private LOTRDwarvenGlowLogic glowLogic = (new LOTRDwarvenGlowLogic()).setPlayerRange(8);

   public void func_145845_h() {
      super.func_145845_h();
      this.glowLogic.update(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
   }

   public float getGlowBrightness(float f) {
      return this.isFakeGuiSign ? 1.0F : this.glowLogic.getGlowBrightness(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, f);
   }

   @SideOnly(Side.CLIENT)
   public double func_145833_n() {
      return 1024.0D;
   }
}
