package lotr.client.render.tileentity;

import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderDwarvenGlow {
   public static float setupGlow(float brightness) {
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      float alphaFunc = GL11.glGetFloat(3010);
      GL11.glAlphaFunc(516, 0.02F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, brightness);
      return alphaFunc;
   }

   public static void endGlow(float alphaFunc) {
      GL11.glAlphaFunc(516, alphaFunc);
      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glEnable(2896);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
