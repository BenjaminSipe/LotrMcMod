package lotr.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.util.MathHelper;

public class LOTRMazeGenerator {
   public final int xSize;
   public final int zSize;
   private short[][] mazeFlags;
   private static final short FLAG_PATH = 1;
   private static final short FLAG_EXCLUDE = 2;
   private static final short FLAG_DEADEND = 4;
   private int startX = -1;
   private int startZ = -1;
   private int endX = -1;
   private int endZ = -1;
   private float windyness = 0.3F;
   private float branchingness = 0.2F;

   public LOTRMazeGenerator(int x, int z) {
      this.xSize = x;
      this.zSize = z;
      this.setupMaze();
   }

   private void setupMaze() {
      this.mazeFlags = new short[this.xSize][this.zSize];
   }

   public void setStart(int x, int z) {
      this.startX = x;
      this.startZ = z;
   }

   public int[] getEnd() {
      return new int[]{this.endX, this.endZ};
   }

   public void setWindyness(float f) {
      this.windyness = f;
   }

   public void clear(int x, int z) {
      this.setFlag(x, z, (short)1, true);
   }

   public void exclude(int x, int z) {
      this.setFlag(x, z, (short)2, true);
   }

   public boolean isPath(int x, int z) {
      return this.getFlag(x, z, (short)1);
   }

   public boolean isDeadEnd(int x, int z) {
      return this.getFlag(x, z, (short)4);
   }

   private void setFlag(int x, int z, short flag, boolean val) {
      short[] var10000;
      if (val) {
         var10000 = this.mazeFlags[x];
         var10000[z] |= flag;
      } else {
         var10000 = this.mazeFlags[x];
         var10000[z] = (short)(var10000[z] & ~flag);
      }

   }

   private boolean getFlag(int x, int z, short flag) {
      return (this.mazeFlags[x][z] & flag) == flag;
   }

   public void generate(Random random) {
      List positions = new ArrayList();
      LOTRMazeGenerator.Dir lastDir = null;
      this.clear(this.startX, this.startZ);
      positions.add(new LOTRMazeGenerator.MazePos(this.startX, this.startZ));

      while(true) {
         while(!positions.isEmpty()) {
            int maxIndex = positions.size() - 1;
            int randPosIndex = MathHelper.func_76136_a(random, (int)((float)maxIndex * (1.0F - this.branchingness)), maxIndex);
            LOTRMazeGenerator.MazePos pos = (LOTRMazeGenerator.MazePos)positions.get(randPosIndex);
            List validDirs = new ArrayList();
            LOTRMazeGenerator.Dir[] var8 = LOTRMazeGenerator.Dir.values();
            int x = var8.length;

            int z;
            label65:
            for(z = 0; z < x; ++z) {
               LOTRMazeGenerator.Dir dir = var8[z];

               for(int l = 1; l <= 2; ++l) {
                  int x = pos.xPos + dir.xDir * l;
                  int z = pos.zPos + dir.zDir * l;
                  if (x < 0 || x >= this.xSize || z < 0 || z >= this.zSize || this.isPath(x, z) || this.getFlag(x, z, (short)2)) {
                     continue label65;
                  }
               }

               validDirs.add(dir);
            }

            if (!validDirs.isEmpty()) {
               LOTRMazeGenerator.Dir dir;
               if (lastDir != null && validDirs.contains(lastDir) && random.nextFloat() >= this.windyness) {
                  dir = lastDir;
               } else {
                  dir = (LOTRMazeGenerator.Dir)validDirs.get(random.nextInt(validDirs.size()));
               }

               x = pos.xPos;
               z = pos.zPos;
               if (this.getFlag(x, z, (short)4)) {
                  this.setFlag(x, z, (short)4, false);
               }

               for(int l = 0; l < 2; ++l) {
                  x += dir.xDir;
                  z += dir.zDir;
                  this.clear(x, z);
               }

               if (!this.getFlag(x, z, (short)4)) {
                  this.setFlag(x, z, (short)4, true);
               }

               positions.add(new LOTRMazeGenerator.MazePos(x, z));
               lastDir = dir;
            } else {
               positions.remove(randPosIndex);
               lastDir = null;
            }
         }

         return;
      }
   }

   public void selectOuterEndpoint(Random random) {
      int startXHalf = this.startX / (this.xSize / 2);
      int startZHalf = this.startZ / (this.zSize / 2);
      int wx = 0;
      int wz = 0;

      do {
         List positions = new ArrayList();

         for(int x = 0; x < this.xSize; ++x) {
            for(int z = 0; z < this.zSize; ++z) {
               boolean outer = x == 0 + wx || x == this.xSize - 1 - wx || z == 0 + wz || z == this.zSize - 1 - wz;
               if (outer && this.isPath(x, z)) {
                  int xHalf = x / (this.xSize / 2);
                  int zHalf = z / (this.zSize / 2);
                  if (startXHalf != xHalf && startZHalf != zHalf) {
                     positions.add(new LOTRMazeGenerator.MazePos(x, z));
                  }
               }
            }
         }

         if (!positions.isEmpty()) {
            LOTRMazeGenerator.MazePos pos = (LOTRMazeGenerator.MazePos)positions.get(random.nextInt(positions.size()));
            this.endX = pos.xPos;
            this.endZ = pos.zPos;
            return;
         }

         ++wx;
         ++wz;
      } while(wx <= this.xSize / 2 + 1 && wz <= this.zSize / 2 + 1);

   }

   private static class MazePos {
      public int xPos;
      public int zPos;

      public MazePos(int x, int z) {
         this.xPos = x;
         this.zPos = z;
      }
   }

   private static enum Dir {
      XNEG(-1, 0),
      XPOS(1, 0),
      ZNEG(0, -1),
      ZPOS(0, 1);

      public final int xDir;
      public final int zDir;

      private Dir(int x, int z) {
         this.xDir = x;
         this.zDir = z;
      }
   }
}
