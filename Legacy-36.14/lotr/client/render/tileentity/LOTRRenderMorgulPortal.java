package lotr.client.render.tileentity;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.tileentity.LOTRTileEntityMorgulPortal;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMorgulPortal extends TileEntitySpecialRenderer {
   private static ResourceLocation portalTexture = new ResourceLocation("lotr:misc/gulduril_glow.png");

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityMorgulPortal portal = (LOTRTileEntityMorgulPortal)tileentity;
      float f1 = (float)LOTRTickHandlerClient.clientTick + f;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
      GL11.glPushMatrix();
      this.func_147499_a(portalTexture);
      GL11.glMatrixMode(5890);
      GL11.glLoadIdentity();
      float f2 = f1 * 0.01F;
      GL11.glTranslatef(f2, 0.0F, 0.0F);
      GL11.glMatrixMode(5888);
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.5F + 0.25F * MathHelper.func_76126_a(f1 / 40.0F), (float)d2 + 0.5F);
      float f4 = 0.9F;
      GL11.glColor4f(f4, f4, f4, 1.0F);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(768, 1);
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a(0.5D, 0.0D, 0.5D, 0.0D, 0.0D);
      tessellator.func_78374_a(0.5D, 0.0D, -0.5D, 0.0D, 0.0D);
      tessellator.func_78374_a(-0.5D, 0.0D, -0.5D, 0.0D, 0.0D);
      tessellator.func_78374_a(-0.5D, 0.0D, 0.5D, 0.0D, 0.0D);
      tessellator.func_78381_a();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glMatrixMode(5890);
      GL11.glLoadIdentity();
      GL11.glMatrixMode(5888);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glPopMatrix();
   }
}
