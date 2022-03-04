package lotr.common.util;

import java.awt.Color;
import java.util.Random;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class LOTRColorUtil {
   public static float[] lerpColors(int color0, int color1, float f) {
      float[] rgb0 = (new Color(color0)).getColorComponents((float[])null);
      return lerpColors(rgb0, color1, f);
   }

   public static float[] lerpColors(float[] rgb0, int color1, float f) {
      float[] rgb1 = (new Color(color1)).getColorComponents((float[])null);
      float r0 = rgb0[0];
      float g0 = rgb0[1];
      float b0 = rgb0[2];
      float r1 = rgb1[0];
      float g1 = rgb1[1];
      float b1 = rgb1[2];
      float r = r0 + (r1 - r0) * f;
      float g = g0 + (g1 - g0) * f;
      float b = b0 + (b1 - b0) * f;
      r = MathHelper.func_76131_a(r, 0.0F, 1.0F);
      g = MathHelper.func_76131_a(g, 0.0F, 1.0F);
      b = MathHelper.func_76131_a(b, 0.0F, 1.0F);
      return new float[]{r, g, b};
   }

   public static int lerpColors_I(int color0, int color1, float f) {
      float[] rgb0 = (new Color(color0)).getColorComponents((float[])null);
      return lerpColors_I(rgb0, color1, f);
   }

   public static int lerpColors_I(float[] rgb0, int color1, float f) {
      float[] rgb = lerpColors(rgb0, color1, f);
      return (new Color(rgb[0], rgb[1], rgb[2])).getRGB();
   }

   public static ItemStack dyeLeather(ItemStack itemstack, int[] colors, Random rand) {
      int color = colors[rand.nextInt(colors.length)];
      return dyeLeather(itemstack, color);
   }

   public static ItemStack dyeLeather(ItemStack itemstack, int color) {
      ((ItemArmor)itemstack.func_77973_b()).func_82813_b(itemstack, color);
      return itemstack;
   }
}
