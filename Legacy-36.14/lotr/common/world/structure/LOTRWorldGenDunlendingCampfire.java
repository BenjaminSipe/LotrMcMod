package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDunlendingCampfire extends LOTRWorldGenStructureBase {
   public LOTRWorldGenDunlendingCampfire(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions && world.func_147439_a(i, j - 1, k) != Blocks.field_150349_c) {
         return false;
      } else {
         --j;
         int rotation = random.nextInt(4);
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
         }

         switch(rotation) {
         case 0:
            k += 5;
            break;
         case 1:
            i -= 5;
            break;
         case 2:
            k -= 5;
            break;
         case 3:
            i += 5;
         }

         int chestX;
         int chestZ;
         int j1;
         if (this.restrictions) {
            for(chestX = i - 5; chestX <= i + 5; ++chestX) {
               for(chestZ = k - 5; chestZ <= k + 5; ++chestZ) {
                  for(j1 = j + 1; j1 <= j + 2; ++j1) {
                     if (LOTRMod.isOpaque(world, chestX, j1, chestZ)) {
                        return false;
                     }
                  }

                  j1 = world.func_72825_h(chestX, chestZ) - 1;
                  if (Math.abs(j1 - j) > 2) {
                     return false;
                  }

                  Block l = world.func_147439_a(chestX, j1, chestZ);
                  if (l != Blocks.field_150349_c) {
                     return false;
                  }
               }
            }
         }

         for(chestX = i - 5; chestX <= i + 5; ++chestX) {
            for(chestZ = k - 5; chestZ <= k + 5; ++chestZ) {
               if (!this.restrictions) {
                  for(j1 = j + 1; j1 <= j + 2; ++j1) {
                     this.func_150516_a(world, chestX, j1, chestZ, Blocks.field_150350_a, 0);
                  }
               }

               for(j1 = j; j1 >= j - 2; --j1) {
                  if (LOTRMod.isOpaque(world, chestX, j1 + 1, chestZ)) {
                     this.func_150516_a(world, chestX, j1, chestZ, Blocks.field_150346_d, 0);
                  } else {
                     this.func_150516_a(world, chestX, j1, chestZ, Blocks.field_150349_c, 0);
                  }

                  this.setGrassToDirt(world, chestX, j1 - 1, chestZ);
               }
            }
         }

         for(chestX = i - 1; chestX <= i + 1; ++chestX) {
            for(chestZ = k - 1; chestZ <= k + 1; ++chestZ) {
               this.func_150516_a(world, chestX, j, chestZ, Blocks.field_150351_n, 0);
            }
         }

         this.func_150516_a(world, i, j, k, LOTRMod.hearth, 0);
         this.func_150516_a(world, i, j + 1, k, Blocks.field_150480_ab, 0);
         this.placeSkullPillar(world, random, i - 2, j + 1, k - 2);
         this.placeSkullPillar(world, random, i + 2, j + 1, k - 2);
         this.placeSkullPillar(world, random, i - 2, j + 1, k + 2);
         this.placeSkullPillar(world, random, i + 2, j + 1, k + 2);
         if (random.nextBoolean()) {
            for(chestX = i - 2; chestX <= i + 2; ++chestX) {
               this.func_150516_a(world, chestX, j + 1, k + 4, Blocks.field_150364_r, 4);
               this.setGrassToDirt(world, chestX, j, k - 4);
            }
         }

         if (random.nextBoolean()) {
            for(chestX = k - 2; chestX <= k + 2; ++chestX) {
               this.func_150516_a(world, i - 4, j + 1, chestX, Blocks.field_150364_r, 8);
               this.setGrassToDirt(world, i - 4, j, chestX);
            }
         }

         if (random.nextBoolean()) {
            for(chestX = i - 2; chestX <= i + 2; ++chestX) {
               this.func_150516_a(world, chestX, j + 1, k - 4, Blocks.field_150364_r, 4);
               this.setGrassToDirt(world, chestX, j, k - 4);
            }
         }

         if (random.nextBoolean()) {
            for(chestX = k - 2; chestX <= k + 2; ++chestX) {
               this.func_150516_a(world, i + 4, j + 1, chestX, Blocks.field_150364_r, 8);
               this.setGrassToDirt(world, i + 4, j, chestX);
            }
         }

         if (random.nextBoolean()) {
            chestX = i;
            chestZ = k;
            int chestMeta = 0;
            int l = random.nextInt(4);
            switch(l) {
            case 0:
               chestX = i - 3 + random.nextInt(6);
               chestZ = k + 3;
               chestMeta = 3;
               break;
            case 1:
               chestX = i - 3;
               chestZ = k - 3 + random.nextInt(6);
               chestMeta = 4;
               break;
            case 2:
               chestX = i - 3 + random.nextInt(6);
               chestZ = k - 3;
               chestMeta = 2;
               break;
            case 3:
               chestX = i + 3;
               chestZ = k - 3 + random.nextInt(6);
               chestMeta = 5;
            }

            this.func_150516_a(world, chestX, j + 1, chestZ, LOTRMod.chestBasket, chestMeta);
            LOTRChestContents.fillChest(world, random, chestX, j + 1, chestZ, LOTRChestContents.DUNLENDING_CAMPFIRE);
         }

         return true;
      }
   }

   private void placeSkullPillar(World world, Random random, int i, int j, int k) {
      this.func_150516_a(world, i, j, k, Blocks.field_150463_bK, 0);
      this.placeSkull(world, random, i, j + 1, k);
   }
}
