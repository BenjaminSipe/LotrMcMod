package lotr.client.render.tileentity;

import lotr.client.LOTRTextures;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class LOTRRenderCommandTable extends TileEntitySpecialRenderer {
   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityCommandTable table = (LOTRTileEntityCommandTable)tileentity;
      this.renderTableAt(d, d1, d2, (double)tileentity.field_145851_c + 0.5D, (double)tileentity.field_145849_e + 0.5D, table.getZoomExp());
   }

   public void renderInvTable() {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      EntityLivingBase viewer = Minecraft.func_71410_x().field_71451_h;
      this.renderTableAt(0.0D, 0.0D, 0.0D, viewer.field_70165_t, viewer.field_70161_v, 0);
      this.func_147499_a(TextureMap.field_110575_b);
   }

   private void renderTableAt(double d, double d1, double d2, double viewerX, double viewerZ, int zoomExp) {
      GL11.glEnable(32826);
      GL11.glDisable(2884);
      GL11.glDisable(2896);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.1F, (float)d2 + 0.5F);
      float posX = (float)(Math.round(viewerX / (double)LOTRGenLayerWorld.scale) + 810L);
      float posY = (float)(Math.round(viewerZ / (double)LOTRGenLayerWorld.scale) + 730L);
      int viewportWidth = 400;
      int viewportWidth = (int)Math.round((double)viewportWidth * Math.pow(2.0D, (double)zoomExp));
      double radius = 0.9D;
      float minX = posX - (float)(viewportWidth / 2);
      float maxX = posX + (float)(viewportWidth / 2);
      if (minX < 0.0F) {
         posX = (float)(0 + viewportWidth / 2);
      }

      if (maxX >= (float)LOTRGenLayerWorld.imageWidth) {
         posX = (float)(LOTRGenLayerWorld.imageWidth - viewportWidth / 2);
      }

      float minY = posY - (float)(viewportWidth / 2);
      float maxY = posY + (float)(viewportWidth / 2);
      if (minY < 0.0F) {
         posY = (float)(0 + viewportWidth / 2);
      }

      if (maxY >= (float)LOTRGenLayerWorld.imageHeight) {
         posY = (float)(LOTRGenLayerWorld.imageHeight - viewportWidth / 2);
      }

      double minU = (double)(posX - (float)(viewportWidth / 2)) / (double)LOTRGenLayerWorld.imageWidth;
      double maxU = (double)(posX + (float)(viewportWidth / 2)) / (double)LOTRGenLayerWorld.imageWidth;
      double minV = (double)(posY - (float)(viewportWidth / 2)) / (double)LOTRGenLayerWorld.imageHeight;
      double maxV = (double)(posY + (float)(viewportWidth / 2)) / (double)LOTRGenLayerWorld.imageHeight;
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      LOTRTextures.drawMap(Minecraft.func_71410_x().field_71439_g, true, -radius, radius, -radius, radius, 0.0D, minU, maxU, minV, maxV, 1.0F);
      LOTRTextures.drawMapOverlay(Minecraft.func_71410_x().field_71439_g, -radius, radius, -radius, radius, 0.0D, minU, maxU, minV, maxV);
      double compassInset = radius * 0.05D;
      LOTRTextures.drawMapCompassBottomLeft(-radius + compassInset, radius - compassInset, -0.01D, 0.15D * radius * 0.0625D);
      GL11.glDisable(3553);
      Tessellator tess = Tessellator.field_78398_a;
      double rRed = radius + 0.015D;
      double rBlack = rRed + 0.015D;
      GL11.glTranslatef(0.0F, 0.0F, 0.01F);
      tess.func_78382_b();
      tess.func_78378_d(-6156032);
      tess.func_78377_a(-rRed, rRed, 0.0D);
      tess.func_78377_a(rRed, rRed, 0.0D);
      tess.func_78377_a(rRed, -rRed, 0.0D);
      tess.func_78377_a(-rRed, -rRed, 0.0D);
      tess.func_78381_a();
      GL11.glTranslatef(0.0F, 0.0F, 0.01F);
      tess.func_78382_b();
      tess.func_78378_d(-16777216);
      tess.func_78377_a(-rBlack, rBlack, 0.0D);
      tess.func_78377_a(rBlack, rBlack, 0.0D);
      tess.func_78377_a(rBlack, -rBlack, 0.0D);
      tess.func_78377_a(-rBlack, -rBlack, 0.0D);
      tess.func_78381_a();
      GL11.glEnable(3553);
      GL11.glPopMatrix();
      GL11.glEnable(2896);
   }
}
