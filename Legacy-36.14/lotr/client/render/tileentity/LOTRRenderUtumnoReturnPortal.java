package lotr.client.render.tileentity;

import lotr.common.tileentity.LOTRTileEntityUtumnoReturnPortal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRRenderUtumnoReturnPortal extends TileEntitySpecialRenderer {
   private static ResourceLocation lightCircle = new ResourceLocation("lotr:misc/utumnoPortal_lightCircle.png");

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityUtumnoReturnPortal portal = (LOTRTileEntityUtumnoReturnPortal)tileentity;
      World world = portal.func_145831_w();
      world.field_72984_F.func_76320_a("utumnoReturnPortal");
      float renderTime = (float)portal.ticksExisted + f;
      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glAlphaFunc(516, 0.01F);
      GL11.glBlendFunc(770, 771);
      GL11.glTranslatef((float)d + 0.5F, (float)d1, (float)d2 + 0.5F);
      float alpha = 0.2F + 0.15F * MathHelper.func_76126_a(renderTime * 0.1F);
      int passes = 12;

      double width;
      double height;
      for(int i = 0; i < passes; ++i) {
         GL11.glPushMatrix();
         tessellator.func_78382_b();
         tessellator.func_78369_a(1.0F, 1.0F, 1.0F, alpha);
         double width = (double)((float)(i + 1) / (float)passes * 0.99F);
         width /= 2.0D;
         width = 0.0D;
         height = width + (double)LOTRTileEntityUtumnoReturnPortal.PORTAL_TOP;
         tessellator.func_78374_a(width, width, width, 0.0D, 0.0D);
         tessellator.func_78374_a(width, height, width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, height, width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, width, width, 0.0D, 0.0D);
         tessellator.func_78374_a(width, width, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(width, height, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, height, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, width, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(width, width, width, 0.0D, 0.0D);
         tessellator.func_78374_a(width, height, width, 0.0D, 0.0D);
         tessellator.func_78374_a(width, height, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(width, width, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, width, width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, height, width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, height, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, width, -width, 0.0D, 0.0D);
         tessellator.func_78381_a();
         GL11.glPopMatrix();
      }

      GL11.glEnable(3553);
      this.func_147499_a(lightCircle);
      int circles = 12;

      for(int i = 0; i < circles; ++i) {
         GL11.glPushMatrix();
         float rotation = renderTime * (float)i * 0.2F;
         GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
         alpha = 0.15F + 0.1F * MathHelper.func_76126_a(renderTime * 0.1F + (float)i);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
         width = (double)(1.5F + 1.5F * MathHelper.func_76126_a(renderTime * 0.05F + (float)i));
         height = 0.01D + (double)i * 0.01D;
         tessellator.func_78382_b();
         tessellator.func_78374_a(width, height, width, 1.0D, 1.0D);
         tessellator.func_78374_a(width, height, -width, 1.0D, 0.0D);
         tessellator.func_78374_a(-width, height, -width, 0.0D, 0.0D);
         tessellator.func_78374_a(-width, height, width, 0.0D, 1.0D);
         tessellator.func_78381_a();
         GL11.glPopMatrix();
      }

      GL11.glAlphaFunc(516, 0.1F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glEnable(2896);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
      world.field_72984_F.func_76319_b();
   }
}
