package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityGondorBaker;
import lotr.common.entity.npc.LOTREntityGondorBlacksmith;
import lotr.common.entity.npc.LOTREntityGondorBrewer;
import lotr.common.entity.npc.LOTREntityGondorButcher;
import lotr.common.entity.npc.LOTREntityGondorFarmer;
import lotr.common.entity.npc.LOTREntityGondorFishmonger;
import lotr.common.entity.npc.LOTREntityGondorFlorist;
import lotr.common.entity.npc.LOTREntityGondorGreengrocer;
import lotr.common.entity.npc.LOTREntityGondorLumberman;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTREntityGondorMason;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class LOTRWorldGenGondorMarketStall extends LOTRWorldGenGondorStructure {
   private static final Class[] allStallTypes = new Class[]{LOTRWorldGenGondorMarketStall.Greengrocer.class, LOTRWorldGenGondorMarketStall.Lumber.class, LOTRWorldGenGondorMarketStall.Mason.class, LOTRWorldGenGondorMarketStall.Brewer.class, LOTRWorldGenGondorMarketStall.Flowers.class, LOTRWorldGenGondorMarketStall.Butcher.class, LOTRWorldGenGondorMarketStall.Fish.class, LOTRWorldGenGondorMarketStall.Farmer.class, LOTRWorldGenGondorMarketStall.Blacksmith.class, LOTRWorldGenGondorMarketStall.Baker.class};

   public static LOTRWorldGenStructureBase2 getRandomStall(Random random, boolean flag) {
      try {
         Class cls = allStallTypes[random.nextInt(allStallTypes.length)];
         return (LOTRWorldGenStructureBase2)cls.getConstructor(Boolean.TYPE).newInstance(flag);
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public LOTRWorldGenGondorMarketStall(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 3);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -2; i2 <= 2; ++i2) {
            for(k2 = -2; k2 <= 2; ++k2) {
               j1 = this.getTopBlock(world, i2, k2) - 1;
               if (!this.isSurface(world, i2, j1, k2)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 5) {
                  return false;
               }
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);

            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 4; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (i2 == 2 && k2 == 2) {
               for(j1 = 1; j1 <= 3; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
               }
            } else if (i2 == 2 || k2 == 2) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
               this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
            }

            this.generateRoof(world, random, i1, 4, k1);
         }
      }

      this.setBlockAndMetadata(world, -2, 1, 0, this.fenceGateBlock, 1);
      this.setBlockAndMetadata(world, -1, 1, -1, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 1, 1, -1, Blocks.field_150486_ae, 3);
      LOTREntityGondorMan trader = this.createTrader(world);
      this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
      return true;
   }

   protected abstract void generateRoof(World var1, Random var2, int var3, int var4, int var5);

   protected abstract LOTREntityGondorMan createTrader(World var1);

   public static class Baker extends LOTRWorldGenGondorMarketStall {
      public Baker(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (k2 % 2 == 0) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 1);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 12);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorBaker(world);
      }
   }

   public static class Blacksmith extends LOTRWorldGenGondorMarketStall {
      public Blacksmith(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 == k2) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 15);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 7);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorBlacksmith(world);
      }
   }

   public static class Farmer extends LOTRWorldGenGondorMarketStall {
      public Farmer(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (IntMath.mod(i2 + k2, 2) == 0) {
            if (Integer.signum(i1) != -Integer.signum(k1) && i2 + k2 == 2) {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 4);
            } else {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 13);
            }
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 12);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorFarmer(world);
      }
   }

   public static class Fish extends LOTRWorldGenGondorMarketStall {
      public Fish(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 % 2 == 0) {
            if (k2 == 2) {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 0);
            } else {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 3);
            }
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 11);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorFishmonger(world);
      }
   }

   public static class Butcher extends LOTRWorldGenGondorMarketStall {
      public Butcher(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 != 2 && k2 != 2) {
            if (i2 != 1 && k2 != 1) {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 0);
            } else {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 14);
            }
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 6);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorButcher(world);
      }
   }

   public static class Flowers extends LOTRWorldGenGondorMarketStall {
      public Flowers(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 == k2) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 4);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 13);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorFlorist(world);
      }
   }

   public static class Brewer extends LOTRWorldGenGondorMarketStall {
      public Brewer(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 % 2 == 0) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 12);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 4);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorBrewer(world);
      }
   }

   public static class Mason extends LOTRWorldGenGondorMarketStall {
      public Mason(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 != 2 && k2 != 2) {
            if (i2 != 1 && k2 != 1) {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 7);
            } else {
               this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 8);
            }
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 7);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorMason(world);
      }
   }

   public static class Lumber extends LOTRWorldGenGondorMarketStall {
      public Lumber(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if ((i2 == 2 || k2 == 2) && IntMath.mod(i2 + k2, 2) == 0) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 13);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 12);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorLumberman(world);
      }
   }

   public static class Greengrocer extends LOTRWorldGenGondorMarketStall {
      public Greengrocer(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (IntMath.mod(i2 + k2, 2) == 0) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 14);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 5);
         }

      }

      protected LOTREntityGondorMan createTrader(World world) {
         return new LOTREntityGondorGreengrocer(world);
      }
   }
}
