package lotr.common.util;

import net.minecraft.util.MathHelper;

public class LOTRFunctions {
   public static float triangleWave(float t, float min, float max, float period) {
      return min + (max - min) * Math.abs(t % period / period - 0.5F) * 2.0F;
   }

   public static float normalisedSin(float t) {
      return (MathHelper.func_76126_a(t) + 1.0F) / 2.0F;
   }

   public static float normalisedCos(float t) {
      return (MathHelper.func_76134_b(t) + 1.0F) / 2.0F;
   }

   public static int[] intRange(int min, int max) {
      int[] indices = new int[max - min + 1];

      for(int i = 0; i < indices.length; ++i) {
         indices[i] = min + i;
      }

      return indices;
   }
}
