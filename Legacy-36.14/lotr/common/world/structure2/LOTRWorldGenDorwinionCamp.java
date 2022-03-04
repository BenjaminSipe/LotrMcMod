package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionCamp extends LOTRWorldGenDorwinionTent {
   public LOTRWorldGenDorwinionCamp(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      LOTRWorldGenDorwinionCaptainTent captainTent = new LOTRWorldGenDorwinionCaptainTent(this.notifyChanges);
      captainTent.restrictions = true;
      int i1 = 0;
      int k1 = -7;
      int j1 = this.getTopBlock(world, i1, k1);
      int r = 0;
      if (!captainTent.generateWithSetRotation(world, random, this.getX(i1, k1), this.getY(j1), this.getZ(i1, k1), (this.getRotationMode() + r) % 4)) {
         return false;
      } else {
         int xMin = 8;
         int xMax = 12;
         int zMin = -5;
         int zMax = 5;
         int[] var16 = new int[]{-9, 0, 9};
         int var17 = var16.length;

         for(int var18 = 0; var18 < var17; ++var18) {
            int k2 = var16[var18];
            this.tryGenerateTent(world, random, new int[]{-xMax, -xMin}, new int[]{k2 + zMin, k2 + zMax}, 3);
            this.tryGenerateTent(world, random, new int[]{xMin, xMax}, new int[]{k2 + zMin, k2 + zMax}, 1);
         }

         return true;
      }
   }

   private boolean tryGenerateTent(World world, Random random, int[] i, int[] k, int r) {
      LOTRWorldGenDorwinionTent tent = new LOTRWorldGenDorwinionTent(this.notifyChanges);
      tent.restrictions = true;
      int attempts = 1;

      for(int l = 0; l < attempts; ++l) {
         int i1 = MathHelper.func_76136_a(random, i[0], i[1]);
         int k1 = MathHelper.func_76136_a(random, k[0], k[1]);
         int j1 = this.getTopBlock(world, i1, k1);
         if (tent.generateWithSetRotation(world, random, this.getX(i1, k1), this.getY(j1), this.getZ(i1, k1), (this.getRotationMode() + r) % 4)) {
            return true;
         }
      }

      return false;
   }
}
