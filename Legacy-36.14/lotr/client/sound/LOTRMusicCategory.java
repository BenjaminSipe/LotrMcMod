package lotr.client.sound;

import net.minecraft.world.World;

public enum LOTRMusicCategory {
   DAY("day"),
   NIGHT("night"),
   CAVE("cave");

   public final String categoryName;

   private LOTRMusicCategory(String s) {
      this.categoryName = s;
   }

   public static LOTRMusicCategory forName(String s) {
      LOTRMusicCategory[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRMusicCategory cat = var1[var3];
         if (s.equalsIgnoreCase(cat.categoryName)) {
            return cat;
         }
      }

      return null;
   }

   public static boolean isDay(World world) {
      return world.func_72967_a(1.0F) < 5;
   }

   public static boolean isCave(World world, int i, int j, int k) {
      return j < 50 && !world.func_72937_j(i, j, k);
   }
}
