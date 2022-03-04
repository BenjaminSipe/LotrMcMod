package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityRohirrimArcher;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanWatchtower extends LOTRWorldGenRohanStructure {
   public LOTRWorldGenRohanWatchtower(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int height = 7 + random.nextInt(3);
      this.originY += height;
      int k1;
      int k2;
      int l;
      if (this.restrictions) {
         for(k1 = -4; k1 <= 4; ++k1) {
            for(k2 = -4; k2 <= 4; ++k2) {
               l = this.getTopBlock(world, k1, k2) - 1;
               if (!this.isSurface(world, k1, l, k2)) {
                  return false;
               }
            }
         }
      }

      int[] var17 = new int[]{-3, 3};
      k2 = var17.length;

      int i1;
      int i1;
      int j1;
      for(l = 0; l < k2; ++l) {
         i1 = var17[l];
         int[] var12 = new int[]{-3, 3};
         i1 = var12.length;

         for(j1 = 0; j1 < i1; ++j1) {
            int k1 = var12[j1];

            for(int j1 = 3; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.plank2Block, this.plank2Meta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }
         }
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         for(k2 = -2; k2 <= 2; ++k2) {
            this.setBlockAndMetadata(world, k1, 0, k2, this.plankBlock, this.plankMeta);
         }
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         for(k2 = -3; k2 <= 3; ++k2) {
            this.setBlockAndMetadata(world, k1, 4, k2, this.plankBlock, this.plankMeta);
         }
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, k1, 0, -3, this.logBlock, this.logMeta | 4);
         this.setBlockAndMetadata(world, k1, 0, 3, this.logBlock, this.logMeta | 4);
         this.setBlockAndMetadata(world, k1, 4, -3, this.logBlock, this.logMeta | 4);
         this.setBlockAndMetadata(world, k1, 4, 3, this.logBlock, this.logMeta | 4);
         this.setBlockAndMetadata(world, k1, 0, -4, this.plankStairBlock, 6);
         this.setBlockAndMetadata(world, k1, 0, 4, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 1, -4, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, k1, 1, 4, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, k1, 3, -3, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, k1, 3, 3, this.fenceBlock, this.fenceMeta);
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, -3, 0, k1, this.logBlock, this.logMeta | 8);
         this.setBlockAndMetadata(world, 3, 0, k1, this.logBlock, this.logMeta | 8);
         this.setBlockAndMetadata(world, -3, 4, k1, this.logBlock, this.logMeta | 8);
         this.setBlockAndMetadata(world, 3, 4, k1, this.logBlock, this.logMeta | 8);
         this.setBlockAndMetadata(world, -4, 0, k1, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, 4, 0, k1, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, -4, 1, k1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 4, 1, k1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, -3, 3, k1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 3, 3, k1, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -3, 1, -2, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -3, 1, 2, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 3, 1, -2, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 3, 1, 2, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 1, -3, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 1, -3, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 1, 3, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 1, 3, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -3, 2, -2, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -3, 2, 2, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 3, 2, -2, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 3, 2, 2, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -2, 2, -3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 2, -3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -2, 2, 3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 2, 3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -1, 4, 0, this.logBlock, this.logMeta | 8);
      this.setBlockAndMetadata(world, 1, 4, 0, this.logBlock, this.logMeta | 8);
      this.setBlockAndMetadata(world, 0, 4, -1, this.logBlock, this.logMeta | 4);
      this.setBlockAndMetadata(world, 0, 4, 1, this.logBlock, this.logMeta | 4);

      for(k1 = -4; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, k1, 4, -4, this.plankStairBlock, 2);
         this.setBlockAndMetadata(world, k1, 4, 4, this.plankStairBlock, 3);
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, -4, 4, k1, this.plankStairBlock, 1);
         this.setBlockAndMetadata(world, 4, 4, k1, this.plankStairBlock, 0);
      }

      for(k1 = 0; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, 0, k1, 3, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 0, k1, 2, Blocks.field_150468_ap, 2);
      }

      for(k1 = -1; !this.isOpaque(world, 0, k1, 3) && this.getY(k1) >= 0; --k1) {
         this.setBlockAndMetadata(world, 0, k1, 3, this.plank2Block, this.plank2Meta);
         this.setGrassToDirt(world, 0, k1 - 1, 3);
         if (!this.isOpaque(world, 0, k1, 2)) {
            this.setBlockAndMetadata(world, 0, k1, 2, Blocks.field_150468_ap, 2);
         }
      }

      this.placeChest(world, random, -2, 1, 2, 2, LOTRChestContents.ROHAN_WATCHTOWER);
      this.setBlockAndMetadata(world, 2, 1, 2, this.tableBlock, 0);

      for(k1 = -2; k1 <= 2; ++k1) {
         k2 = Math.abs(k1);
         int[] var18 = new int[]{-3, 3};
         i1 = var18.length;

         for(int var20 = 0; var20 < i1; ++var20) {
            i1 = var18[var20];

            for(j1 = -1; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
               if (k2 == 2 && IntMath.mod(j1, 4) == 1 || k2 == 1 && IntMath.mod(j1, 2) == 0 || k2 == 0 && IntMath.mod(j1, 4) == 3) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.logBlock, this.logMeta);
                  if (k2 == 0) {
                     this.setBlockAndMetadata(world, i1 - 1 * Integer.signum(i1), j1, k1, Blocks.field_150478_aa, i1 > 0 ? 1 : 2);
                  }
               }
            }
         }
      }

      k1 = this.getBelowTop(world, 2, -1, 2);
      this.placeChest(world, random, 2, k1, 2, 5, LOTRChestContents.ROHAN_WATCHTOWER);
      k1 = this.getBelowTop(world, 2, -1, 0);
      this.setBlockAndMetadata(world, 2, k1, 0, this.plankBlock, this.plankMeta);
      this.setGrassToDirt(world, 2, k1 - 1, 0);
      this.placeBarrel(world, random, 2, k1 + 1, 0, 5, LOTRFoods.ROHAN_DRINK);
      k1 = this.getBelowTop(world, -2, -1, 1);
      this.setBlockAndMetadata(world, -2, k1, 1, Blocks.field_150407_cf, 0);
      this.setGrassToDirt(world, -2, k1 - 1, 1);
      k1 = this.getBelowTop(world, -2, -1, 2);
      this.setBlockAndMetadata(world, -2, k1, 2, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -2, k1 + 1, 2, Blocks.field_150407_cf, 0);
      this.setGrassToDirt(world, -2, k1 - 1, 2);
      k1 = this.getBelowTop(world, -1, -1, 2);
      this.setBlockAndMetadata(world, -1, k1, 2, Blocks.field_150407_cf, 0);
      this.setGrassToDirt(world, -1, k1 - 1, 2);
      k2 = 1 + random.nextInt(3);

      for(l = 0; l < k2; ++l) {
         LOTREntityRohirrimWarrior rohirrim = random.nextInt(3) == 0 ? new LOTREntityRohirrimArcher(world) : new LOTREntityRohirrimWarrior(world);
         ((LOTREntityRohirrimWarrior)rohirrim).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)rohirrim, world, 0, 1, 0, 4);
      }

      return true;
   }

   private int getBelowTop(World world, int i, int j, int k) {
      while(!this.isOpaque(world, i, j, k) && this.getY(j) >= 0) {
         --j;
      }

      return j + 1;
   }
}
