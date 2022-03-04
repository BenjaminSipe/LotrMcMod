package lotr.common.world.genlayer;

import com.google.common.math.IntMath;
import net.minecraft.world.World;

public class LOTRGenLayerBiomeVariantsLake extends LOTRGenLayer {
   public static final int FLAG_LAKE = 1;
   public static final int FLAG_JUNGLE = 2;
   public static final int FLAG_MANGROVE = 4;
   private int zoomScale;
   private int lakeFlags = 0;

   public LOTRGenLayerBiomeVariantsLake(long l, LOTRGenLayer layer, int i) {
      super(l);
      this.lotrParent = layer;
      this.zoomScale = IntMath.pow(2, i);
   }

   public LOTRGenLayerBiomeVariantsLake setLakeFlags(int... flags) {
      int[] var2 = flags;
      int var3 = flags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int f = var2[var4];
         this.lakeFlags = setFlag(this.lakeFlags, f);
      }

      return this;
   }

   public static int setFlag(int param, int flag) {
      param |= flag;
      return param;
   }

   public static boolean getFlag(int param, int flag) {
      return (param & flag) == flag;
   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int[] baseInts = this.lotrParent == null ? null : this.lotrParent.getInts(world, i, k, xSize, zSize);
      int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k1 = 0; k1 < zSize; ++k1) {
         for(int i1 = 0; i1 < xSize; ++i1) {
            this.func_75903_a((long)(i + i1), (long)(k + k1));
            int baseInt = baseInts == null ? 0 : baseInts[i1 + k1 * xSize];
            if (getFlag(this.lakeFlags, 1) && this.func_75902_a(30 * this.zoomScale * this.zoomScale * this.zoomScale) == 2) {
               baseInt = setFlag(baseInt, 1);
            }

            if (getFlag(this.lakeFlags, 2) && this.func_75902_a(12) == 3) {
               baseInt = setFlag(baseInt, 2);
            }

            if (getFlag(this.lakeFlags, 4) && this.func_75902_a(10) == 1) {
               baseInt = setFlag(baseInt, 4);
            }

            ints[i1 + k1 * xSize] = baseInt;
         }
      }

      return ints;
   }
}
