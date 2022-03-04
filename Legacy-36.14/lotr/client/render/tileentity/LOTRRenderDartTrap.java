package lotr.client.render.tileentity;

import lotr.common.tileentity.LOTRTileEntityDartTrap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class LOTRRenderDartTrap extends TileEntitySpecialRenderer {
   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      Minecraft mc = Minecraft.func_71410_x();
      if (mc.field_71439_g.field_71075_bZ.field_75098_d && mc.field_71474_y.field_74330_P && Minecraft.func_71382_s()) {
         GL11.glPushMatrix();
         GL11.glTranslated(-TileEntityRendererDispatcher.field_147554_b, -TileEntityRendererDispatcher.field_147555_c, -TileEntityRendererDispatcher.field_147552_d);
         GL11.glDepthMask(false);
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glDisable(2884);
         GL11.glDisable(3042);
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         AxisAlignedBB range = ((LOTRTileEntityDartTrap)tileentity).getTriggerRange();
         RenderGlobal.func_147590_a(range, 16777215);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(3553);
         GL11.glEnable(2896);
         GL11.glEnable(2884);
         GL11.glDisable(3042);
         GL11.glDepthMask(true);
         GL11.glPopMatrix();
      }

   }
}
