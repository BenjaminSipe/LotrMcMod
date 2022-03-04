package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRGuiSlider extends GuiButton {
   private String baseDisplayString;
   private String overrideStateString;
   private boolean isTime = false;
   private boolean isFloat = false;
   private boolean valueOnly = false;
   private int numberDigits = 0;
   private int minValue;
   private int maxValue;
   private float minValueF;
   private float maxValueF;
   private float sliderValue = 1.0F;
   public boolean dragging = false;

   public LOTRGuiSlider(int id, int x, int y, int width, int height, String s) {
      super(id, x, y, width, height, s);
      this.baseDisplayString = s;
   }

   public void setFloat() {
      this.isFloat = true;
   }

   public void setMinutesSecondsTime() {
      this.isTime = true;
   }

   public void setValueOnly() {
      this.valueOnly = true;
   }

   public void setNumberDigits(int i) {
      this.numberDigits = i;
   }

   public int func_146114_a(boolean flag) {
      return 0;
   }

   public void setMinMaxValues(int min, int max) {
      this.minValue = min;
      this.maxValue = max;
   }

   public int getSliderValue() {
      return this.minValue + Math.round(this.sliderValue * (float)(this.maxValue - this.minValue));
   }

   public void setSliderValue(int value) {
      value = MathHelper.func_76125_a(value, this.minValue, this.maxValue);
      this.sliderValue = (float)(value - this.minValue) / (float)(this.maxValue - this.minValue);
   }

   public void setMinMaxValues_F(float min, float max) {
      this.minValueF = min;
      this.maxValueF = max;
   }

   public float getSliderValue_F() {
      return this.minValueF + this.sliderValue * (this.maxValueF - this.minValueF);
   }

   public void setSliderValue_F(float value) {
      value = MathHelper.func_76131_a(value, this.minValueF, this.maxValueF);
      this.sliderValue = (value - this.minValueF) / (this.maxValueF - this.minValueF);
   }

   public void setOverrideStateString(String s) {
      this.overrideStateString = s;
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.overrideStateString != null) {
         this.field_146126_j = this.overrideStateString;
      } else {
         int value;
         if (this.isTime) {
            value = this.getSliderValue();
            int seconds = value % 60;
            int minutes = value / 60;
            String s = String.format("%d:%02d", minutes, seconds);
            this.field_146126_j = s;
         } else if (this.isFloat) {
            String s = String.format("%.2f", this.getSliderValue_F());
            this.field_146126_j = s;
         } else {
            value = this.getSliderValue();
            this.field_146126_j = String.valueOf(value);
            if (this.numberDigits > 0) {
               this.field_146126_j = String.format("%0" + this.numberDigits + "d", value);
            }
         }
      }

      if (!this.valueOnly) {
         this.field_146126_j = this.baseDisplayString + ": " + this.field_146126_j;
      }

      super.func_146112_a(mc, i, j);
   }

   protected void func_146119_b(Minecraft mc, int i, int j) {
      if (this.field_146125_m && this.field_146124_l) {
         if (this.dragging) {
            this.sliderValue = (float)(i - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
            if (this.sliderValue < 0.0F) {
               this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F) {
               this.sliderValue = 1.0F;
            }
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.field_146128_h + (int)(this.sliderValue * (float)(this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
         this.func_73729_b(this.field_146128_h + (int)(this.sliderValue * (float)(this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
      }

   }

   public boolean func_146116_c(Minecraft mc, int i, int j) {
      if (super.func_146116_c(mc, i, j)) {
         this.sliderValue = (float)(i - (this.field_146128_h + 4)) / (float)(this.field_146120_f - 8);
         if (this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
         }

         if (this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
         }

         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   public void func_146118_a(int i, int j) {
      this.dragging = false;
   }
}
