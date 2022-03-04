package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHalfTrollHouse extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenHalfTrollHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int radius = 5;
      int height = 6 + random.nextInt(4);
      this.setOriginAndRotation(world, i, j, k, rotation, radius + 1);
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         for(i1 = -radius; i1 <= radius; ++i1) {
            for(k1 = -radius; k1 <= radius; ++k1) {
               j1 = this.getTopBlock(world, i1, k1);
               Block block = this.getBlock(world, i1, j1 - 1, k1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      for(i1 = -radius; i1 <= radius; ++i1) {
         for(k1 = -radius; k1 <= radius; ++k1) {
            for(j1 = 0; j1 <= height; ++j1) {
               double f = (double)(i1 * i1 + k1 * k1) / 2.0D - (double)(8 - j1);
               if (f < 8.0D) {
                  if (j1 == 0) {
                     for(int j2 = 0; (j2 == 0 || !this.isOpaque(world, i1, j2, k1)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150405_ch, 0);
                        this.setGrassToDirt(world, i1, j2 - 1, k1);
                     }
                  }

                  if (f > 0.0D) {
                     if (j1 > 1 && j1 != height - 1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150405_ch, 0);
                     } else {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150406_ce, 12);
                     }
                  } else if (j1 == 0) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
                  } else {
                     this.setAir(world, i1, j1, k1);
                  }
               }
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -radius; k1 <= -radius + 1; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150347_e, 0);

            for(j1 = 1; j1 <= 3; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }

         this.setBlockAndMetadata(world, i1, 4, -radius, LOTRMod.woodSlabSingle, 3);
      }

      this.setBlockAndMetadata(world, -2, 2, -radius, LOTRMod.fence, 3);
      this.setBlockAndMetadata(world, -2, 3, -radius, LOTRMod.woodSlabSingle, 3);
      this.setBlockAndMetadata(world, 2, 2, -radius, LOTRMod.fence, 3);
      this.setBlockAndMetadata(world, 2, 3, -radius, LOTRMod.woodSlabSingle, 3);

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            j1 = Math.abs(i1);
            int k2 = Math.abs(k1);
            if (j1 != 2 && k2 != 2 && (j1 != 0 || k2 != 0)) {
               if (j1 == 1 || k2 == 1) {
                  this.setBlockAndMetadata(world, i1, -4, k1, LOTRMod.hearth, 0);
                  this.setBlockAndMetadata(world, i1, -3, k1, Blocks.field_150480_ab, 0);
                  this.setBlockAndMetadata(world, i1, -2, k1, Blocks.field_150350_a, 0);
                  this.setBlockAndMetadata(world, i1, -1, k1, Blocks.field_150350_a, 0);
                  this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150411_aY, 0);
               }
            } else {
               for(int j1 = -4; j1 <= 0; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150406_ce, 12);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 0, 0, Blocks.field_150347_e, 0);

      for(i1 = 0; i1 < 8; ++i1) {
         k1 = (2 + (i1 + 1) / 2 % 2) * IntMath.pow(-1, i1 / 4);
         j1 = (2 + (i1 + 3) / 2 % 2) * IntMath.pow(-1, (i1 + 2) / 4);
         this.setBlockAndMetadata(world, k1, 1, j1, Blocks.field_150347_e, 0);
         this.setBlockAndMetadata(world, k1, 2, j1, LOTRMod.fence, 3);
         this.setBlockAndMetadata(world, k1, 3, j1, LOTRMod.fence, 3);
      }

      this.setBlockAndMetadata(world, -4, 3, 0, LOTRMod.fence, 3);
      this.setAir(world, -5, 3, 0);
      this.setBlockAndMetadata(world, 4, 3, 0, LOTRMod.fence, 3);
      this.setAir(world, 5, 3, 0);
      this.setBlockAndMetadata(world, 0, 3, 4, LOTRMod.fence, 3);
      this.setAir(world, 0, 3, 5);

      for(i1 = -3; i1 <= 3; i1 += 6) {
         this.setBlockAndMetadata(world, i1, 1, -1, Blocks.field_150333_U, 11);
         this.setBlockAndMetadata(world, i1, 1, 1, Blocks.field_150333_U, 11);
         this.placeChest(world, random, i1, 1, 0, LOTRMod.chestBasket, 0, LOTRChestContents.HALF_TROLL_HOUSE);
      }

      this.setBlockAndMetadata(world, -1, 1, 3, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 0, 1, 3, Blocks.field_150333_U, 11);
      this.setBlockAndMetadata(world, 1, 1, 3, LOTRMod.halfTrollTable, 0);
      LOTREntityHalfTroll halfTroll = new LOTREntityHalfTroll(world);
      this.spawnNPCAndSetHome(halfTroll, world, 0, 1, 0, 16);
      return true;
   }
}
