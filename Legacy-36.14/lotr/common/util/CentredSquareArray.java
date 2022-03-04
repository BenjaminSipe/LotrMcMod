package lotr.common.util;

import java.util.Arrays;

public class CentredSquareArray {
   private int radius;
   private int width;
   private Object[] array;

   public CentredSquareArray(int r) {
      this.radius = r;
      this.width = this.radius * 2 + 1;
      this.array = new Object[this.width * this.width];
   }

   private int getIndex(int x, int y) {
      return (y + this.radius) * this.width + x + this.radius;
   }

   public Object get(int x, int y) {
      int index = this.getIndex(x, y);
      return this.array[index];
   }

   public void set(int x, int y, Object val) {
      int index = this.getIndex(x, y);
      this.array[index] = val;
   }

   public void fill(Object val) {
      Arrays.fill(this.array, val);
   }
}
