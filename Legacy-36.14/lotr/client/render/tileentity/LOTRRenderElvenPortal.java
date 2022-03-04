package lotr.client.render.tileentity;

import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderElvenPortal extends TileEntitySpecialRenderer {
   private FloatBuffer floatBuffer = GLAllocation.func_74529_h(16);
   private ResourceLocation portalTexture0 = new ResourceLocation("lotr:misc/elvenportal_0.png");
   private ResourceLocation portalTexture1 = new ResourceLocation("lotr:misc/elvenportal_1.png");

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      float f0 = (float)(tileentity.func_145831_w().func_82737_E() % 16L) + f;
      TileEntityRendererDispatcher var10000 = this.field_147501_a;
      float f1 = (float)TileEntityRendererDispatcher.field_147554_b + f0 * 0.25F;
      var10000 = this.field_147501_a;
      float f2 = (float)TileEntityRendererDispatcher.field_147555_c;
      var10000 = this.field_147501_a;
      float f3 = (float)TileEntityRendererDispatcher.field_147552_d + f0 * 0.25F;
      GL11.glDisable(2896);
      GL11.glColor3f(0.2F, 0.6F, 1.0F);
      Random random = new Random(31100L);
      float f4 = 0.75F;

      for(int i = 0; i < 16; ++i) {
         GL11.glPushMatrix();
         float f5 = (float)(16 - i);
         float f6 = 0.0625F;
         float f7 = 1.0F / (f5 + 1.0F);
         if (i == 0) {
            this.func_147499_a(this.portalTexture0);
            f7 = 0.1F;
            f5 = 65.0F;
            f6 = 0.125F;
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
         }

         if (i == 1) {
            this.func_147499_a(this.portalTexture1);
            GL11.glEnable(3042);
            GL11.glBlendFunc(1, 1);
            f6 = 0.5F;
         }

         float f8 = (float)(-(d1 + (double)f4));
         float f9 = f8 + ActiveRenderInfo.field_74590_b;
         float f10 = f8 + f5 + ActiveRenderInfo.field_74590_b;
         float f11 = f9 / f10;
         f11 += (float)(d1 + (double)f4);
         GL11.glTranslatef(f1, f11, f3);
         GL11.glTexGeni(8192, 9472, 9217);
         GL11.glTexGeni(8193, 9472, 9217);
         GL11.glTexGeni(8194, 9472, 9217);
         GL11.glTexGeni(8195, 9472, 9216);
         GL11.glTexGen(8192, 9473, this.getFloatBuffer(1.0F, 0.0F, 0.0F, 0.0F));
         GL11.glTexGen(8193, 9473, this.getFloatBuffer(0.0F, 0.0F, 1.0F, 0.0F));
         GL11.glTexGen(8194, 9473, this.getFloatBuffer(0.0F, 0.0F, 0.0F, 1.0F));
         GL11.glTexGen(8195, 9474, this.getFloatBuffer(0.0F, 1.0F, 0.0F, 0.0F));
         GL11.glEnable(3168);
         GL11.glEnable(3169);
         GL11.glEnable(3170);
         GL11.glEnable(3171);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5890);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glTranslatef(0.0F, (float)(Minecraft.func_71386_F() % 700000L) / 700000.0F, 0.0F);
         GL11.glScalef(f6, f6, f6);
         GL11.glTranslatef(0.5F, 0.5F, 0.0F);
         GL11.glRotatef((float)(i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
         GL11.glTranslatef(-f1, -f3, -f2);
         f9 = f8 + ActiveRenderInfo.field_74590_b;
         GL11.glTranslatef(ActiveRenderInfo.field_74592_a * f5 / f9, ActiveRenderInfo.field_74591_c * f5 / f9, -f2);
         Tessellator tessellator = Tessellator.field_78398_a;
         tessellator.func_78382_b();
         f11 = random.nextFloat() * 0.5F + 0.1F;
         float f12 = random.nextFloat() * 0.5F + 0.4F;
         float f13 = random.nextFloat() * 0.5F + 0.5F;
         if (i == 0) {
            f13 = 1.0F;
            f12 = 1.0F;
            f11 = 1.0F;
         }

         tessellator.func_78369_a(f11 * f7, f12 * f7, f13 * f7, 1.0F);
         tessellator.func_78377_a(d, d1 + (double)f4, d2);
         tessellator.func_78377_a(d, d1 + (double)f4, d2 + 1.0D);
         tessellator.func_78377_a(d + 1.0D, d1 + (double)f4, d2 + 1.0D);
         tessellator.func_78377_a(d + 1.0D, d1 + (double)f4, d2);
         tessellator.func_78381_a();
         GL11.glPopMatrix();
         GL11.glMatrixMode(5888);
      }

      GL11.glDisable(3042);
      GL11.glDisable(3168);
      GL11.glDisable(3169);
      GL11.glDisable(3170);
      GL11.glDisable(3171);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glEnable(2896);
   }

   private FloatBuffer getFloatBuffer(float f, float f1, float f2, float f3) {
      this.floatBuffer.clear();
      this.floatBuffer.put(f).put(f1).put(f2).put(f3);
      this.floatBuffer.flip();
      return this.floatBuffer;
   }
}
