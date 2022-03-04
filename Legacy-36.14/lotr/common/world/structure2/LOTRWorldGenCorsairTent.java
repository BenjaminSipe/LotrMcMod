package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenCorsairTent extends LOTRWorldGenCorsairStructure {
   public LOTRWorldGenCorsairTent(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      int randomGround;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(j1 = -2; j1 <= 2; ++j1) {
            for(randomGround = -3; randomGround <= 3; ++randomGround) {
               j1 = this.getTopBlock(world, j1, randomGround) - 1;
               Block block = this.getBlock(world, j1, j1, randomGround);
               if (!this.isSurface(world, j1, j1, randomGround) && block != Blocks.field_150348_b && block != Blocks.field_150322_A) {
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

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               randomGround = random.nextInt(3);
               if (randomGround == 0) {
                  if (j1 == 0) {
                     this.setBiomeTop(world, i1, j1, k1);
                  } else {
                     this.setBiomeFiller(world, i1, j1, k1);
                  }
               } else if (randomGround == 1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 1);
               } else if (randomGround == 2) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150354_m, 0);
               }

               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 3; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         int[] var13 = new int[]{-2, 2};
         j1 = var13.length;

         for(randomGround = 0; randomGround < j1; ++randomGround) {
            j1 = var13[randomGround];

            for(int j1 = 1; j1 <= 2; ++j1) {
               this.setBlockAndMetadata(world, j1, j1, i1, Blocks.field_150325_L, random.nextBoolean() ? 15 : 7);
            }

            this.setGrassToDirt(world, j1, 0, i1);
         }

         this.setBlockAndMetadata(world, -1, 3, i1, Blocks.field_150325_L, random.nextBoolean() ? 15 : 7);
         this.setBlockAndMetadata(world, 1, 3, i1, Blocks.field_150325_L, random.nextBoolean() ? 15 : 7);
         this.setBlockAndMetadata(world, 0, 4, i1, Blocks.field_150325_L, random.nextBoolean() ? 15 : 7);
         if (Math.abs(i1) == 3) {
            this.setBlockAndMetadata(world, 0, 5, i1, Blocks.field_150325_L, random.nextBoolean() ? 15 : 7);
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, -3, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 0, i1, 3, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -1, 2, -3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 2, -3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -1, 2, 3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 2, 3, Blocks.field_150478_aa, 1);
      if (random.nextBoolean()) {
         this.placeChest(world, random, -1, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.CORSAIR, 1 + random.nextInt(2));
      } else {
         this.placeChest(world, random, 1, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.CORSAIR, 1 + random.nextInt(2));
      }

      return true;
   }
}
