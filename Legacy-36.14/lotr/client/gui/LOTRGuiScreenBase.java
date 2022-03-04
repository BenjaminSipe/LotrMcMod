package lotr.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public abstract class LOTRGuiScreenBase extends GuiScreen {
   public void func_73876_c() {
      super.func_73876_c();
      if (!this.field_146297_k.field_71439_g.func_70089_S() || this.field_146297_k.field_71439_g.field_70128_L) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   protected void func_73869_a(char c, int i) {
      if (i == 1 || i == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   public boolean func_73868_f() {
      return false;
   }

   protected void drawCenteredString(String s, int x, int y, int c) {
      this.field_146289_q.func_78276_b(s, x - this.field_146289_q.func_78256_a(s) / 2, y, c);
   }

   public void func_73729_b(int x, int y, int u, int v, int width, int height) {
      this.drawTexturedModalRect(x, y, u, v, width, height, 256);
   }

   public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, int imageWidth) {
      drawTexturedModalRectFloat((double)x, (double)y, (double)u, (double)v, (double)width, (double)height, imageWidth, this.field_73735_i);
   }

   public void drawTexturedModalRectFloat(float x, float y, int u, int v, float width, float height) {
      this.drawTexturedModalRectFloat(x, y, u, v, width, height, 256);
   }

   public void drawTexturedModalRectFloat(float x, float y, int u, int v, float width, float height, int imageWidth) {
      drawTexturedModalRectFloat((double)x, (double)y, (double)u, (double)v, (double)width, (double)height, imageWidth, this.field_73735_i);
   }

   public static void drawTexturedModalRectFloat(double x, double y, double u, double v, double width, double height, int imageWidth, float z) {
      float f = 1.0F / (float)imageWidth;
      float f1 = 1.0F / (float)imageWidth;
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a(x + 0.0D, y + height, (double)z, (u + 0.0D) * (double)f, (v + height) * (double)f1);
      tessellator.func_78374_a(x + width, y + height, (double)z, (u + width) * (double)f, (v + height) * (double)f1);
      tessellator.func_78374_a(x + width, y + 0.0D, (double)z, (u + width) * (double)f, (v + 0.0D) * (double)f1);
      tessellator.func_78374_a(x + 0.0D, y + 0.0D, (double)z, (u + 0.0D) * (double)f, (v + 0.0D) * (double)f1);
      tessellator.func_78381_a();
   }

   public static void drawFloatRect(float x0, float y0, float x1, float y1, int color) {
      float alpha;
      if (x0 < x1) {
         alpha = x0;
         x0 = x1;
         x1 = alpha;
      }

      if (y0 < y1) {
         alpha = y0;
         y0 = y1;
         y1 = alpha;
      }

      alpha = (float)(color >> 24 & 255) / 255.0F;
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(r, g, b, alpha);
      tessellator.func_78382_b();
      tessellator.func_78377_a((double)x0, (double)y1, 0.0D);
      tessellator.func_78377_a((double)x1, (double)y1, 0.0D);
      tessellator.func_78377_a((double)x1, (double)y0, 0.0D);
      tessellator.func_78377_a((double)x0, (double)y0, 0.0D);
      tessellator.func_78381_a();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }
}
