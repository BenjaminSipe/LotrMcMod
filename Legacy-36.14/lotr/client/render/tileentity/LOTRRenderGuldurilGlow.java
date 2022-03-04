package lotr.client.render.tileentity;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRRenderGuldurilGlow extends TileEntitySpecialRenderer {
   private static ResourceLocation texture = new ResourceLocation("lotr:misc/gulduril_glow.png");
   private static final float texSize = 64.0F;

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityGulduril glow = (LOTRTileEntityGulduril)tileentity;
      this.renderGlowAt(d, d1, d2, glow.ticksExisted, f, glow);
   }

   private void renderGlowAt(double d, double d1, double d2, int tick, float f, TileEntity te) {
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
      GL11.glPushMatrix();
      float glowTick = (float)tick + f;
      this.func_147499_a(texture);
      GL11.glMatrixMode(5890);
      GL11.glLoadIdentity();
      float glowX = glowTick * 0.008F;
      float glowY = glowTick * 0.008F;
      GL11.glTranslatef(glowX, glowY, 0.0F);
      GL11.glMatrixMode(5888);
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.5F, (float)d2 + 0.5F);
      float alpha = 0.6F;
      GL11.glColor4f(alpha, alpha, alpha, 1.0F);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glBlendFunc(768, 1);
      boolean glowWest = this.guldurilBlockAt(te, -1, 0, 0);
      boolean glowEast = this.guldurilBlockAt(te, 1, 0, 0);
      boolean glowBelow = this.guldurilBlockAt(te, 0, -1, 0);
      boolean glowAbove = this.guldurilBlockAt(te, 0, 1, 0);
      boolean glowNorth = this.guldurilBlockAt(te, 0, 0, -1);
      boolean glowSouth = this.guldurilBlockAt(te, 0, 0, 1);
      float edge = 8.0F;
      float edgeNoGlow = 8.5F;
      float xMin = -(glowWest ? 8.0F : 8.5F) / 16.0F;
      float xMax = (glowEast ? 8.0F : 8.5F) / 16.0F;
      float yMin = -(glowBelow ? 8.0F : 8.5F) / 16.0F;
      float yMax = (glowAbove ? 8.0F : 8.5F) / 16.0F;
      float zMin = -(glowNorth ? 8.0F : 8.5F) / 16.0F;
      float zMax = (glowSouth ? 8.0F : 8.5F) / 16.0F;
      PositionTextureVertex xyz = new PositionTextureVertex(xMin, yMin, zMin, 0.0F, 0.0F);
      PositionTextureVertex Xyz = new PositionTextureVertex(xMax, yMin, zMin, 0.0F, 8.0F);
      PositionTextureVertex XYz = new PositionTextureVertex(xMax, yMax, zMin, 8.0F, 8.0F);
      PositionTextureVertex xYz = new PositionTextureVertex(xMin, yMax, zMin, 8.0F, 0.0F);
      PositionTextureVertex xyZ = new PositionTextureVertex(xMin, yMin, zMax, 0.0F, 0.0F);
      PositionTextureVertex XyZ = new PositionTextureVertex(xMax, yMin, zMax, 0.0F, 8.0F);
      PositionTextureVertex XYZ = new PositionTextureVertex(xMax, yMax, zMax, 8.0F, 8.0F);
      PositionTextureVertex xYZ = new PositionTextureVertex(xMin, yMax, zMax, 8.0F, 0.0F);
      if (!glowEast) {
         this.renderFace(XyZ, Xyz, XYz, XYZ);
      }

      if (!glowWest) {
         this.renderFace(xyz, xyZ, xYZ, xYz);
      }

      if (!glowBelow) {
         this.renderFace(XyZ, xyZ, xyz, Xyz);
      }

      if (!glowAbove) {
         this.renderFace(XYz, xYz, xYZ, XYZ);
      }

      if (!glowNorth) {
         this.renderFace(Xyz, xyz, xYz, XYz);
      }

      if (!glowSouth) {
         this.renderFace(xyZ, XyZ, XYZ, xYZ);
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glMatrixMode(5890);
      GL11.glLoadIdentity();
      GL11.glMatrixMode(5888);
      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glEnable(2896);
      GL11.glPopMatrix();
   }

   private boolean guldurilBlockAt(TileEntity te, int dx, int dy, int dz) {
      if (te == null) {
         return false;
      } else {
         World world = te.func_145831_w();
         TileEntity otherTE = world.func_147438_o(te.field_145851_c + dx, te.field_145848_d + dy, te.field_145849_e + dz);
         return otherTE instanceof LOTRTileEntityGulduril;
      }
   }

   private void renderFace(PositionTextureVertex v0, PositionTextureVertex v1, PositionTextureVertex v2, PositionTextureVertex v3) {
      float uMin = 0.0F;
      float uMax = 0.25F;
      float vMin = 0.0F;
      float vMax = 0.25F;
      v0.field_78241_b = uMin;
      v0.field_78242_c = vMax;
      v1.field_78241_b = uMax;
      v1.field_78242_c = vMax;
      v2.field_78241_b = uMax;
      v2.field_78242_c = vMin;
      v3.field_78241_b = uMin;
      v3.field_78242_c = vMin;
      Tessellator tess = Tessellator.field_78398_a;
      tess.func_78382_b();
      PositionTextureVertex[] vertices = new PositionTextureVertex[]{v0, v1, v2, v3};
      PositionTextureVertex[] var11 = vertices;
      int var12 = vertices.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         PositionTextureVertex v = var11[var13];
         tess.func_78374_a(v.field_78243_a.field_72450_a, v.field_78243_a.field_72448_b, v.field_78243_a.field_72449_c, (double)v.field_78241_b, (double)v.field_78242_c);
      }

      tess.func_78381_a();
   }

   public void renderInvGlow() {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      EntityLivingBase viewer = Minecraft.func_71410_x().field_71451_h;
      this.renderGlowAt(0.0D, 0.0D, 0.0D, LOTRTickHandlerClient.clientTick, LOTRTickHandlerClient.renderTick, (TileEntity)null);
      GL11.glEnable(32826);
   }
}
