package lotr.client.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.util.LOTRClientUtil;
import lotr.common.time.ShireReckoning;
import lotr.common.util.LOTRUtil;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;

public class DateDisplay {
   private int dateDisplayTime = 0;
   private static final int DATE_DISPLAY_DURATION = 200;

   public void displayNewDate() {
      this.dateDisplayTime = 200;
   }

   public void update() {
      if (this.dateDisplayTime > 0) {
         --this.dateDisplayTime;
      }

   }

   public void reset() {
      this.dateDisplayTime = 0;
   }

   public void render(MatrixStack matStack, Minecraft mc) {
      if (this.dateDisplayTime > 0) {
         ITextComponent date = ShireReckoning.INSTANCE.getCurrentDateAndYearLongform();
         float alpha = this.calculateDisplayAlpha();
         MainWindow window = mc.func_228018_at_();
         int width = window.func_198107_o();
         int height = window.func_198087_p();
         float scale = 1.5F;
         float invScale = 1.0F / scale;
         width = (int)((float)width * invScale);
         height = (int)((float)height * invScale);
         FontRenderer font = mc.field_71466_p;
         int dateX = (width - font.func_238414_a_(date)) / 2;
         font.getClass();
         int dateY = (height - 9) * 2 / 5;
         matStack.func_227860_a_();
         matStack.func_227862_a_(scale, scale, scale);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         font.func_243248_b(matStack, date, (float)dateX, (float)dateY, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
         RenderSystem.disableBlend();
         matStack.func_227865_b_();
      }

   }

   private float calculateDisplayAlpha() {
      return LOTRUtil.normalisedTriangleWave((float)(200 - this.dateDisplayTime), 200.0F, 0.0F, 1.0F);
   }
}
