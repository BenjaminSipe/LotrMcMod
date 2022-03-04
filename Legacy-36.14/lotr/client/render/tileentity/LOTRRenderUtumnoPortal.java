package lotr.client.render.tileentity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class LOTRRenderUtumnoPortal extends TileEntitySpecialRenderer {
   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(false);
      int passes = 60;

      for(int i = 0; i < passes; ++i) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.0F + (float)i * 0.5F, (float)d2 + 0.5F);
         Tessellator tessellator = Tessellator.field_78398_a;
         tessellator.func_78382_b();
         tessellator.func_78369_a(0.0F, 0.0F, 0.0F, (float)(passes - i) / (float)passes);
         double width = 0.5D;
         tessellator.func_78374_a(width, 0.0D, width, 0.0D, 0.0D);
         tessellator.func_78374_a(width, 0.0D, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, 0.0D, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, 0.0D, width, 0.0D, 0.0D);
         tessellator.func_78381_a();
         GL11.glPopMatrix();
      }

      GL11.glDepthMask(true);
      GL11.glEnable(3553);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }
}
