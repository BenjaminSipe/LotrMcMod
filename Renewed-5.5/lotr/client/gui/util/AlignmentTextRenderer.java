package lotr.client.gui.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.util.LOTRClientUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;

public class AlignmentTextRenderer {
   private final boolean isInWorldRendering;
   private boolean defaultSeethrough = false;

   private AlignmentTextRenderer(boolean inWorld) {
      this.isInWorldRendering = inWorld;
   }

   public static AlignmentTextRenderer newGUIRenderer() {
      return new AlignmentTextRenderer(false);
   }

   public static AlignmentTextRenderer newInWorldRenderer() {
      return new AlignmentTextRenderer(true);
   }

   public AlignmentTextRenderer setDefaultSeethrough(boolean flag) {
      this.defaultSeethrough = flag;
      return this;
   }

   public void drawAlignmentText(MatrixStack matStack, FontRenderer fr, int x, int y, ITextComponent text, float alphaF) {
      this.drawAlignmentText(matStack, fr, x, y, text, alphaF, this.defaultSeethrough);
   }

   public void drawAlignmentText(MatrixStack matStack, FontRenderer fr, int x, int y, ITextComponent text, float alphaF, boolean seethrough) {
      this.drawBorderedText(matStack, fr, x, y, text, 16772620, alphaF, seethrough);
   }

   public void drawConquestText(MatrixStack matStack, FontRenderer fr, int x, int y, ITextComponent text, boolean isConquestCleanse, float alphaF) {
      this.drawConquestText(matStack, fr, x, y, text, isConquestCleanse, alphaF, this.defaultSeethrough);
   }

   public void drawConquestText(MatrixStack matStack, FontRenderer fr, int x, int y, ITextComponent text, boolean isConquestCleanse, float alphaF, boolean seethrough) {
      this.drawBorderedText(matStack, fr, x, y, text, isConquestCleanse ? 16773846 : 14833677, alphaF, seethrough);
   }

   public void drawBorderedText(MatrixStack matStack, FontRenderer fr, int x, int y, ITextComponent text, int color, float alphaF) {
      this.drawBorderedText(matStack, fr, x, y, text, color, alphaF, this.defaultSeethrough);
   }

   public void drawBorderedText(MatrixStack matStack, FontRenderer fr, int x, int y, ITextComponent text, int color, float alphaF, boolean seethrough) {
      int colorWithAlpha = LOTRClientUtil.getRGBAForFontRendering(color, alphaF);
      int blackWithAlpha = LOTRClientUtil.getRGBAForFontRendering(0, alphaF);
      if (this.isInWorldRendering) {
         matStack.func_227860_a_();
         matStack.func_227861_a_(0.0D, 0.0D, 0.001D);
      }

      LOTRClientUtil.drawSeethroughText(fr, text, x - 1, y - 1, blackWithAlpha, matStack);
      LOTRClientUtil.drawSeethroughText(fr, text, x, y - 1, blackWithAlpha, matStack);
      LOTRClientUtil.drawSeethroughText(fr, text, x + 1, y - 1, blackWithAlpha, matStack);
      LOTRClientUtil.drawSeethroughText(fr, text, x + 1, y, blackWithAlpha, matStack);
      LOTRClientUtil.drawSeethroughText(fr, text, x + 1, y + 1, blackWithAlpha, matStack);
      LOTRClientUtil.drawSeethroughText(fr, text, x, y + 1, blackWithAlpha, matStack);
      LOTRClientUtil.drawSeethroughText(fr, text, x - 1, y + 1, blackWithAlpha, matStack);
      LOTRClientUtil.drawSeethroughText(fr, text, x - 1, y, blackWithAlpha, matStack);
      if (this.isInWorldRendering) {
         matStack.func_227865_b_();
      }

      LOTRClientUtil.drawSeethroughText(fr, text, x, y, colorWithAlpha, matStack);
   }
}
