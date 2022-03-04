package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRohanBaker;
import lotr.common.entity.npc.LOTREntityRohanBlacksmith;
import lotr.common.entity.npc.LOTREntityRohanBrewer;
import lotr.common.entity.npc.LOTREntityRohanBuilder;
import lotr.common.entity.npc.LOTREntityRohanButcher;
import lotr.common.entity.npc.LOTREntityRohanFarmer;
import lotr.common.entity.npc.LOTREntityRohanFishmonger;
import lotr.common.entity.npc.LOTREntityRohanLumberman;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityRohanOrcharder;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class LOTRWorldGenRohanMarketStall extends LOTRWorldGenRohanStructure {
   private static final Class[] allStallTypes = new Class[]{LOTRWorldGenRohanMarketStall.Blacksmith.class, LOTRWorldGenRohanMarketStall.Farmer.class, LOTRWorldGenRohanMarketStall.Lumber.class, LOTRWorldGenRohanMarketStall.Builder.class, LOTRWorldGenRohanMarketStall.Brewer.class, LOTRWorldGenRohanMarketStall.Butcher.class, LOTRWorldGenRohanMarketStall.Fish.class, LOTRWorldGenRohanMarketStall.Baker.class, LOTRWorldGenRohanMarketStall.Orcharder.class};

   public static LOTRWorldGenStructureBase2 getRandomStall(Random random, boolean flag) {
      try {
         Class cls = allStallTypes[random.nextInt(allStallTypes.length)];
         return (LOTRWorldGenStructureBase2)cls.getConstructor(Boolean.TYPE).newInstance(flag);
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public LOTRWorldGenRohanMarketStall(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 3);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      if (this.restrictions) {
         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               i2 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, i2, k1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            i2 = Math.abs(i1);
            int k2 = Math.abs(k1);

            int j1;
            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 4; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (i2 == 2 && k2 == 2) {
               if (k1 < 0) {
                  for(j1 = 1; j1 <= 4; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                  }
               } else {
                  for(j1 = 1; j1 <= 3; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                  }
               }
            } else {
               int j2 = 4;
               if (k1 == 2 || k1 == 1 && i2 == 2) {
                  j2 = 3;
               }

               this.generateRoof(world, random, i1, j2, k1);
            }
         }
      }

      this.setBlockAndMetadata(world, -1, 1, -2, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 1, -2, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, 1, 1, -2, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, -1, 1, 2, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 1, 2, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 1, 1, 2, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 2, 1, -1, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 2, 1, 0, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 2, 1, 1, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, -2, 1, -1, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -2, 1, 0, this.fenceGateBlock, 1);
      this.setBlockAndMetadata(world, -2, 1, 1, this.plankBlock, this.plankMeta);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 1, this.plank2StairBlock, 6);
         this.setBlockAndMetadata(world, i1, 3, 1, this.plankSlabBlock, this.plankSlabMeta | 8);
      }

      for(i1 = -1; i1 <= 0; ++i1) {
         this.setBlockAndMetadata(world, -2, 3, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.setBlockAndMetadata(world, 2, 3, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
      }

      this.setBlockAndMetadata(world, 1, 1, -1, Blocks.field_150486_ae, 3);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, -2, this.fenceBlock, this.fenceMeta);
      }

      LOTREntityRohanMan trader = this.createTrader(world);
      this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
      return true;
   }

   protected abstract void generateRoof(World var1, Random var2, int var3, int var4, int var5);

   protected abstract LOTREntityRohanMan createTrader(World var1);

   public static class Orcharder extends LOTRWorldGenRohanMarketStall {
      public Orcharder(boolean flag) {
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

      protected LOTREntityRohanMan createTrader(World world) {
         return new LOTREntityRohanOrcharder(world);
      }
   }

   public static class Baker extends LOTRWorldGenRohanMarketStall {
      public Baker(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 % 2 == 0) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 12);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 1);
         }

      }

      protected LOTREntityRohanMan createTrader(World world) {
         return new LOTREntityRohanBaker(world);
      }
   }

   public static class Fish extends LOTRWorldGenRohanMarketStall {
      public Fish(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (k2 % 2 == 1) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 11);
         } else if (i2 % 2 == k2 / 2 % 2) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 0);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 3);
         }

      }

      protected LOTREntityRohanMan createTrader(World world) {
         return new LOTREntityRohanFishmonger(world);
      }
   }

   public static class Butcher extends LOTRWorldGenRohanMarketStall {
      public Butcher(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         if (random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 14);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 6);
         }

      }

      protected LOTREntityRohanMan createTrader(World world) {
         return new LOTREntityRohanButcher(world);
      }
   }

   public static class Brewer extends LOTRWorldGenRohanMarketStall {
      public Brewer(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 % 2 == 1) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 12);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 4);
         }

      }

      protected LOTREntityRohanMan createTrader(World world) {
         return new LOTREntityRohanBrewer(world);
      }
   }

   public static class Builder extends LOTRWorldGenRohanMarketStall {
      public Builder(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (k2 % 2 == 0 && i2 % 2 == k2 / 2 % 2) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 12);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 7);
         }

      }

      protected LOTREntityRohanMan createTrader(World world) {
         return new LOTREntityRohanBuilder(world);
      }
   }

   public static class Lumber extends LOTRWorldGenRohanMarketStall {
      public Lumber(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 + k2 >= 3) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 13);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 12);
         }

      }

      protected LOTREntityRohanMan createTrader(World world) {
         return new LOTREntityRohanLumberman(world);
      }
   }

   public static class Farmer extends LOTRWorldGenRohanMarketStall {
      public Farmer(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         if (random.nextInt(3) == 0) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 0);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 8);
         }

      }

      protected LOTREntityRohanMan createTrader(World world) {
         return new LOTREntityRohanFarmer(world);
      }
   }

   public static class Blacksmith extends LOTRWorldGenRohanMarketStall {
      public Blacksmith(boolean flag) {
         super(flag);
      }

      protected void generateRoof(World world, Random random, int i1, int j1, int k1) {
         int i2 = Math.abs(i1);
         int k2 = Math.abs(k1);
         if (i2 + k2 >= 3) {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 7);
         } else {
            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150325_L, 15);
         }

      }

      protected LOTREntityRohanMan createTrader(World world) {
         return new LOTREntityRohanBlacksmith(world);
      }
   }
}
