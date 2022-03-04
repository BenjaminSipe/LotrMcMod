package lotr.client.render.tileentity;

import lotr.client.LOTRClientProxy;
import lotr.common.tileentity.LOTRTileEntitySignCarved;
import lotr.common.tileentity.LOTRTileEntitySignCarvedIthildin;
import net.minecraft.tileentity.TileEntity;

public class LOTRRenderSignCarvedIthildin extends LOTRRenderSignCarved {
   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntitySignCarvedIthildin sign = (LOTRTileEntitySignCarvedIthildin)tileentity;
      float alphaFunc = LOTRRenderDwarvenGlow.setupGlow(sign.getGlowBrightness(f));
      super.func_147500_a(tileentity, d, d1, d2, f);
      LOTRRenderDwarvenGlow.endGlow(alphaFunc);
   }

   protected int getTextColor(LOTRTileEntitySignCarved sign, float f) {
      float glow = ((LOTRTileEntitySignCarvedIthildin)sign).getGlowBrightness(f);
      int alpha = LOTRClientProxy.getAlphaInt(glow);
      return 16777215 | alpha << 24;
   }
}
