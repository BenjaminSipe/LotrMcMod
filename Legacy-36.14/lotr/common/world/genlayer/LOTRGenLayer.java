package lotr.common.world.genlayer;

import net.minecraft.world.World;
import net.minecraft.world.gen.layer.GenLayer;

public abstract class LOTRGenLayer extends GenLayer {
   protected LOTRGenLayer lotrParent;

   public LOTRGenLayer(long l) {
      super(l);
   }

   public void func_75905_a(long l) {
      super.func_75905_a(l);
      if (this.lotrParent != null) {
         this.lotrParent.func_75905_a(l);
      }

   }

   public final int[] func_75904_a(int i, int k, int xSize, int zSize) {
      throw new RuntimeException("Do not use this method!");
   }

   public abstract int[] getInts(World var1, int var2, int var3, int var4, int var5);
}
