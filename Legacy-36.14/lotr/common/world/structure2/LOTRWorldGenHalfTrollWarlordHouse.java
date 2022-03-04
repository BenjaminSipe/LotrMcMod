package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import lotr.common.entity.npc.LOTREntityHalfTrollWarlord;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHalfTrollWarlordHouse extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenHalfTrollWarlordHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int radius = 7;
      int height = 10 + random.nextInt(4);
      this.setOriginAndRotation(world, i, j, k, rotation, radius + 1);
      int b;
      int i1;
      int k1;
      if (this.restrictions) {
         for(b = -radius; b <= radius; ++b) {
            for(i1 = -radius; i1 <= radius; ++i1) {
               k1 = this.getTopBlock(world, b, i1);
               Block block = this.getBlock(world, b, k1 - 1, i1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      for(b = -radius; b <= radius; ++b) {
         for(i1 = -radius; i1 <= radius; ++i1) {
            for(k1 = 0; k1 <= height; ++k1) {
               double f = (double)(b * b + i1 * i1) / 4.0D - (double)(8 - k1);
               if (f < 8.0D) {
                  if (k1 == 0) {
                     for(int j2 = 0; (j2 == 0 || !this.isOpaque(world, b, j2, i1)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, b, j2, i1, Blocks.field_150405_ch, 0);
                        this.setGrassToDirt(world, b, j2 - 1, i1);
                     }
                  }

                  if (f > 0.0D) {
                     if (k1 > 1 && k1 != height - 1) {
                        this.setBlockAndMetadata(world, b, k1, i1, Blocks.field_150405_ch, 0);
                     } else {
                        this.setBlockAndMetadata(world, b, k1, i1, Blocks.field_150406_ce, 12);
                     }
                  } else if (k1 == 0) {
                     this.setBlockAndMetadata(world, b, k1, i1, Blocks.field_150347_e, 0);
                  } else {
                     this.setAir(world, b, k1, i1);
                  }
               }
            }
         }
      }

      for(b = -1; b <= 1; ++b) {
         for(i1 = -radius; i1 <= -radius + 2; ++i1) {
            this.setBlockAndMetadata(world, b, 0, i1, Blocks.field_150347_e, 0);

            for(k1 = 1; k1 <= 3; ++k1) {
               this.setAir(world, b, k1, i1);
            }
         }

         this.setBlockAndMetadata(world, b, 4, -radius, LOTRMod.woodSlabSingle, 3);
      }

      this.setBlockAndMetadata(world, -2, 2, -radius, LOTRMod.fence, 3);
      this.setBlockAndMetadata(world, -2, 3, -radius, LOTRMod.woodSlabSingle, 3);
      this.setBlockAndMetadata(world, 2, 2, -radius, LOTRMod.fence, 3);
      this.setBlockAndMetadata(world, 2, 3, -radius, LOTRMod.woodSlabSingle, 3);

      int j1;
      for(b = -2; b <= 2; ++b) {
         for(i1 = -2; i1 <= 2; ++i1) {
            k1 = Math.abs(b);
            j1 = Math.abs(i1);
            if (k1 == 2 || j1 == 2 || k1 == 0 && j1 == 0) {
               for(int j1 = -4; j1 <= 0; ++j1) {
                  this.setBlockAndMetadata(world, b, j1, i1, Blocks.field_150406_ce, 12);
               }
            } else if (k1 == 1 || j1 == 1) {
               this.setBlockAndMetadata(world, b, -4, i1, LOTRMod.hearth, 0);
               this.setBlockAndMetadata(world, b, -3, i1, Blocks.field_150480_ab, 0);
               this.setBlockAndMetadata(world, b, -2, i1, Blocks.field_150350_a, 0);
               this.setBlockAndMetadata(world, b, -1, i1, Blocks.field_150350_a, 0);
               this.setBlockAndMetadata(world, b, 0, i1, Blocks.field_150411_aY, 0);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 0, 0, Blocks.field_150347_e, 0);

      for(b = 0; b < 8; ++b) {
         i1 = (3 + (b + 1) / 2 % 2) * IntMath.pow(-1, b / 4);
         k1 = (3 + (b + 3) / 2 % 2) * IntMath.pow(-1, (b + 2) / 4);
         this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150347_e, 0);
         this.setBlockAndMetadata(world, i1, 2, k1, LOTRMod.fence, 3);
         this.setBlockAndMetadata(world, i1, 3, k1, LOTRMod.fence, 3);
      }

      this.setBlockAndMetadata(world, -5, 3, 0, LOTRMod.fence, 3);
      this.setAir(world, -6, 3, 0);
      this.setAir(world, -7, 3, 0);
      this.setBlockAndMetadata(world, 5, 3, 0, LOTRMod.fence, 3);
      this.setAir(world, 6, 3, 0);
      this.setAir(world, 7, 3, 0);
      this.setBlockAndMetadata(world, 0, 3, 5, LOTRMod.fence, 3);
      this.setAir(world, 0, 3, 6);
      this.setAir(world, 0, 3, 7);

      for(b = -4; b <= 4; b += 8) {
         this.setBlockAndMetadata(world, b, 1, -2, Blocks.field_150333_U, 11);
         this.setBlockAndMetadata(world, b, 1, -1, Blocks.field_150333_U, 11);
         this.setBlockAndMetadata(world, b, 1, 1, Blocks.field_150333_U, 11);
         this.setBlockAndMetadata(world, b, 1, 2, Blocks.field_150333_U, 11);
         this.setBlockAndMetadata(world, b + Integer.signum(b), 1, 0, Blocks.field_150406_ce, 12);
         this.placeChest(world, random, b, 1, 0, LOTRMod.chestBasket, 0, LOTRChestContents.HALF_TROLL_HOUSE);
      }

      this.setBlockAndMetadata(world, -2, 1, 4, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -1, 1, 4, Blocks.field_150333_U, 11);
      this.setBlockAndMetadata(world, 0, 1, 4, Blocks.field_150347_e, 0);
      this.setBlockAndMetadata(world, 1, 1, 4, Blocks.field_150333_U, 11);
      this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.halfTrollTable, 0);
      this.setBlockAndMetadata(world, 0, 1, 3, LOTRMod.commandTable, 0);
      this.placeWallBanner(world, 0, 6, -radius + 1, LOTRItemBanner.BannerType.HALF_TROLL, 2);
      b = 8;

      for(i1 = 0; i1 < 2; ++i1) {
         this.setBlockAndMetadata(world, 0, b, 0, LOTRMod.fence, 3);
         --b;
      }

      this.setBlockAndMetadata(world, 0, b, 0, LOTRMod.woodSlabSingle, 11);
      this.placeWallBanner(world, 0, b, 0, LOTRItemBanner.BannerType.HALF_TROLL, 0);
      this.placeWallBanner(world, 0, b, 0, LOTRItemBanner.BannerType.HALF_TROLL, 1);
      this.placeWallBanner(world, 0, b, 0, LOTRItemBanner.BannerType.HALF_TROLL, 2);
      this.placeWallBanner(world, 0, b, 0, LOTRItemBanner.BannerType.HALF_TROLL, 3);

      for(i1 = -radius; i1 <= radius; ++i1) {
         for(k1 = -radius; k1 <= radius; ++k1) {
            if (random.nextInt(10) == 0) {
               for(j1 = height; j1 > 0; --j1) {
                  if (this.isAir(world, i1, j1, k1) && this.isOpaque(world, i1, j1 - 1, k1)) {
                     this.placeSkull(world, random, i1, j1, k1);
                     break;
                  }
               }
            }
         }
      }

      LOTREntityHalfTroll halfTroll = new LOTREntityHalfTrollWarlord(world);
      halfTroll.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(halfTroll, world, 0, 1, 0, 16);
      return true;
   }
}
