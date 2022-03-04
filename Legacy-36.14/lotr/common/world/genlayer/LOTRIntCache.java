package lotr.common.world.genlayer;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.World;

public class LOTRIntCache {
   private static LOTRIntCache SERVER = new LOTRIntCache();
   private static LOTRIntCache CLIENT = new LOTRIntCache();
   private int intCacheSize = 256;
   private List freeSmallArrays = new ArrayList();
   private List inUseSmallArrays = new ArrayList();
   private List freeLargeArrays = new ArrayList();
   private List inUseLargeArrays = new ArrayList();

   public static LOTRIntCache get(World world) {
      return !world.field_72995_K ? SERVER : CLIENT;
   }

   public int[] getIntArray(int size) {
      int[] ints;
      if (size <= 256) {
         if (this.freeSmallArrays.isEmpty()) {
            ints = new int[256];
            this.inUseSmallArrays.add(ints);
            return ints;
         } else {
            ints = (int[])this.freeSmallArrays.remove(this.freeSmallArrays.size() - 1);
            this.inUseSmallArrays.add(ints);
            return ints;
         }
      } else if (size > this.intCacheSize) {
         this.intCacheSize = size;
         this.freeLargeArrays.clear();
         this.inUseLargeArrays.clear();
         ints = new int[this.intCacheSize];
         this.inUseLargeArrays.add(ints);
         return ints;
      } else if (this.freeLargeArrays.isEmpty()) {
         ints = new int[this.intCacheSize];
         this.inUseLargeArrays.add(ints);
         return ints;
      } else {
         ints = (int[])this.freeLargeArrays.remove(this.freeLargeArrays.size() - 1);
         this.inUseLargeArrays.add(ints);
         return ints;
      }
   }

   public void resetIntCache() {
      if (!this.freeLargeArrays.isEmpty()) {
         this.freeLargeArrays.remove(this.freeLargeArrays.size() - 1);
      }

      if (!this.freeSmallArrays.isEmpty()) {
         this.freeSmallArrays.remove(this.freeSmallArrays.size() - 1);
      }

      this.freeLargeArrays.addAll(this.inUseLargeArrays);
      this.freeSmallArrays.addAll(this.inUseSmallArrays);
      this.inUseLargeArrays.clear();
      this.inUseSmallArrays.clear();
   }

   public String getCacheSizes() {
      return "cache: " + this.freeLargeArrays.size() + ", tcache: " + this.freeSmallArrays.size() + ", allocated: " + this.inUseLargeArrays.size() + ", tallocated: " + this.inUseSmallArrays.size();
   }
}
