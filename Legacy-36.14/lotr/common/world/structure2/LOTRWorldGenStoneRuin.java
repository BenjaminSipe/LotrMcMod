package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTRWorldGenStoneRuin extends LOTRWorldGenStructureBase2 {
   private int minWidth;
   private int maxWidth;

   private LOTRWorldGenStoneRuin(int i, int j) {
      super(false);
      this.minWidth = i;
      this.maxWidth = j;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      int width = MathHelper.func_76136_a(random, this.minWidth, this.maxWidth);
      boolean generateColumn = random.nextInt(3) > 0;
      int minHeight;
      int maxHeight;
      int columnX;
      int i1;
      int k1;
      int k1;
      int j1;
      int j2;
      if (generateColumn) {
         minHeight = 0;
         maxHeight = 0;
         columnX = 0 - width / 2;
         i1 = 0 - width / 2;
         if (this.restrictions) {
            label122:
            for(k1 = columnX; k1 < columnX + width; ++k1) {
               for(k1 = i1; k1 < i1 + width; ++k1) {
                  j1 = this.getTopBlock(world, k1, k1);
                  if (j1 < minHeight) {
                     minHeight = j1;
                  }

                  if (j1 > maxHeight) {
                     maxHeight = j1;
                  }

                  if (maxHeight - minHeight > 8) {
                     generateColumn = false;
                     break label122;
                  }

                  if (!this.isSurface(world, k1, j1 - 1, k1)) {
                     generateColumn = false;
                     break label122;
                  }
               }
            }
         }

         if (generateColumn) {
            k1 = 4 + random.nextInt(4) + random.nextInt(width * 3);

            for(k1 = columnX; k1 < columnX + width; ++k1) {
               for(j1 = i1; j1 < i1 + width; ++j1) {
                  int height = (int)((float)k1 * (1.0F + random.nextFloat()));

                  for(j2 = height; j2 >= minHeight; --j2) {
                     this.placeRandomBrick(world, random, k1, j2, j1);
                     this.setGrassToDirt(world, k1, j2 - 1, j1);
                  }
               }
            }
         }
      }

      minHeight = width * 2;
      maxHeight = 2 + random.nextInt(4) + random.nextInt(width * 3);

      for(columnX = 0; columnX < maxHeight; ++columnX) {
         i1 = MathHelper.func_76136_a(random, -minHeight * 2, minHeight * 2);
         k1 = MathHelper.func_76136_a(random, -minHeight * 2, minHeight * 2);
         k1 = this.getTopBlock(world, i1, k1);
         if (!this.restrictions || this.isSurface(world, i1, k1 - 1, k1)) {
            j1 = random.nextInt(4);
            boolean flag = true;
            if (j1 == 0) {
               if (!this.isOpaque(world, i1, k1, k1)) {
                  this.placeRandomSlab(world, random, i1, k1, k1);
               }
            } else {
               for(j2 = k1; j2 < k1 + j1 && flag; ++j2) {
                  flag = !this.isOpaque(world, i1, j2, k1);
               }

               if (flag) {
                  for(j2 = k1; j2 < k1 + j1; ++j2) {
                     this.placeRandomBrick(world, random, i1, j2, k1);
                  }
               }
            }

            if (flag) {
               this.setGrassToDirt(world, i1, k1 - 1, k1);
            }
         }
      }

      return true;
   }

   protected abstract void placeRandomBrick(World var1, Random var2, int var3, int var4, int var5);

   protected abstract void placeRandomSlab(World var1, Random var2, int var3, int var4, int var5);

   // $FF: synthetic method
   LOTRWorldGenStoneRuin(int x0, int x1, Object x2) {
      this(x0, x1);
   }

   public static class TAUREDAIN extends LOTRWorldGenStoneRuin {
      public TAUREDAIN(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 0);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 1);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 2);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle8, 5);
         } else {
            int l = random.nextInt(3);
            switch(l) {
            case 0:
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle8, 0);
               break;
            case 1:
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle8, 1);
               break;
            case 2:
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle8, 2);
            }
         }

      }
   }

   public static class RHUN extends LOTRWorldGenStoneRuin {
      public RHUN(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick5, 11);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick5, 13);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick5, 14);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle12, 4);
         } else {
            int l = random.nextInt(3);
            switch(l) {
            case 0:
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle12, 0);
               break;
            case 1:
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle12, 1);
               break;
            case 2:
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle12, 2);
            }
         }

      }
   }

   public static class NUMENOR extends LOTRWorldGenStoneRuin {
      public NUMENOR(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 11);
      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 3);
      }
   }

   public static class UMBAR extends LOTRWorldGenStoneRuin {
      public UMBAR(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick6, 6);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick6, 7);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle13, 2);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle13, 3);
         }

      }
   }

   public static class NEAR_HARAD extends LOTRWorldGenStoneRuin {
      public NEAR_HARAD(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 15);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 11);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 0);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle7, 1);
         }

      }
   }

   public static class MORDOR extends LOTRWorldGenStoneRuin {
      public MORDOR(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 0);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 7);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle, 1);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle2, 2);
         }

      }
   }

   public static class HIGH_ELVEN extends LOTRWorldGenStoneRuin {
      public HIGH_ELVEN(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 2);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 3);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick3, 4);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle6, 0 + random.nextInt(2));
         } else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle5, 5 + random.nextInt(3));
         }

      }
   }

   public static class GALADHRIM extends LOTRWorldGenStoneRuin {
      public GALADHRIM(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 11);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 12);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 13);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle2, 6 + random.nextInt(2));
         } else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle2, 3 + random.nextInt(3));
         }

      }
   }

   public static class DWARVEN extends LOTRWorldGenStoneRuin {
      public DWARVEN(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 6);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 5);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle, 7);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle7, 6);
         }

      }
   }

   public static class DORWINION extends LOTRWorldGenStoneRuin {
      public DORWINION(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick5, 2);
      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle9, 7);
      }
   }

   public static class DOL_GULDUR extends LOTRWorldGenStoneRuin {
      public DOL_GULDUR(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 8);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 9);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick6, 11);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 6);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 5);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle14, 1);
         }

      }
   }

   public static class ARNOR extends LOTRWorldGenStoneRuin {
      public ARNOR(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 3);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 4);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 5);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 1);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 2);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle4, 3);
         }

      }
   }

   public static class ANGMAR extends LOTRWorldGenStoneRuin {
      public ANGMAR(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 0);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 1);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(2);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle3, 3);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle3, 4);
         }

      }
   }

   public static class STONE extends LOTRWorldGenStoneRuin {
      public STONE(int i, int j) {
         super(i, j, null);
      }

      protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 0);
            break;
         case 1:
            this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 1);
            break;
         case 2:
            this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 2);
         }

      }

      protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
         if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.field_150333_U, 0);
         } else {
            int l = random.nextInt(3);
            switch(l) {
            case 0:
               this.setBlockAndMetadata(world, i, j, k, Blocks.field_150333_U, 5);
               break;
            case 1:
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingleV, 0);
               break;
            case 2:
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingleV, 1);
            }
         }

      }
   }
}
