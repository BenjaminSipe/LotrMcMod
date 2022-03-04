package lotr.client.gui;

import lotr.common.LOTRAchievement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonAchievements extends GuiButton {
   private boolean leftOrRight;
   public LOTRAchievement.Category buttonCategory;

   public LOTRGuiButtonAchievements(int i, boolean flag, int j, int k) {
      super(i, j, k, 15, 21, "");
      this.leftOrRight = flag;
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         mc.func_110434_K().func_110577_a(LOTRGuiAchievements.iconsTexture);
         int texU = this.leftOrRight ? 0 : this.field_146120_f * 3;
         int texV = 124;
         boolean highlighted = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         if (!this.field_146124_l) {
            texU += this.field_146120_f * 2;
         } else if (highlighted) {
            texU += this.field_146120_f;
         }

         float[] catColors = this.buttonCategory.getCategoryRGB();
         GL11.glColor4f(catColors[0], catColors[1], catColors[2], 1.0F);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, texU, texV, this.field_146120_f, this.field_146121_g);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, texU, texV + this.field_146121_g, this.field_146120_f, this.field_146121_g);
      }

   }
}
