package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRohanFarmhand;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillageFarm extends LOTRWorldGenRohanStructure {
   public LOTRWorldGenRohanVillageFarm(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -4; i2 <= 4; ++i2) {
            for(k2 = -5; k2 <= 5; ++k2) {
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

               if (k1 - i1 > 4) {
                  return false;
               }
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -5; k1 <= 5; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);

            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 4; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (i2 <= 3 && k2 <= 4) {
               this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
            }

            if (i2 == 0 && k2 == 0) {
               this.setBlockAndMetadata(world, i1, -1, k1, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, i1, -2, k1);
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150355_j, 0);
               this.setBlockAndMetadata(world, i1, 1, k1, this.logBlock, this.logMeta);
               this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150407_cf, 0);
               this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, i1, 4, k1, Blocks.field_150407_cf, 0);
               this.setBlockAndMetadata(world, i1, 5, k1, Blocks.field_150423_aK, 2);
            } else if (i2 == 3 && k2 == 4) {
               for(j1 = 1; j1 <= 2; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.logBlock, this.logMeta);
               }

               this.setBlockAndMetadata(world, i1, 3, k1, Blocks.field_150478_aa, 5);
            } else if (i2 == 3 && k2 <= 3) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
            } else if (i2 <= 2 && i2 % 2 == 0) {
               if (k2 <= 3) {
                  this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150458_ak, 7);
                  this.setBlockAndMetadata(world, i1, 1, k1, this.cropBlock, this.cropMeta);
               }

               if (k2 == 4) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
               }
            }
         }
      }

      i1 = 1 + random.nextInt(2);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityRohanFarmhand farmhand = new LOTREntityRohanFarmhand(world);
         this.spawnNPCAndSetHome(farmhand, world, random.nextBoolean() ? -1 : 1, 1, 0, 8);
         farmhand.seedsItem = this.seedItem;
      }

      return true;
   }
}
