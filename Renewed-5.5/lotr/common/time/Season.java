package lotr.common.time;

import java.awt.Color;

public enum Season {
   SPRING("spring", 0, new float[]{1.0F, 1.0F, 1.0F}),
   SUMMER("summer", 1, new float[]{1.15F, 1.15F, 0.9F}),
   AUTUMN("autumn", 2, new float[]{1.2F, 1.0F, 0.7F}),
   WINTER("winter", 3, new float[]{1.0F, 0.8F, 0.8F});

   public static Season[] allSeasons = new Season[]{SPRING, SUMMER, AUTUMN, WINTER};
   private final String name;
   public final int seasonID;
   private final float[] grassRGB;

   private Season(String s, int i, float[] f) {
      this.name = s;
      this.seasonID = i;
      this.grassRGB = f;
   }

   public String codeName() {
      return this.name;
   }

   public int transformColor(int color) {
      float[] rgb = (new Color(color)).getRGBColorComponents((float[])null);
      float r = rgb[0];
      float g = rgb[1];
      float b = rgb[2];
      r = Math.min(r * this.grassRGB[0], 1.0F);
      g = Math.min(g * this.grassRGB[1], 1.0F);
      b = Math.min(b * this.grassRGB[2], 1.0F);
      return (new Color(r, g, b)).getRGB();
   }
}
