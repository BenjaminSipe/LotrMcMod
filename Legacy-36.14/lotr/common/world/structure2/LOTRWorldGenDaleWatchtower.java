package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDaleLevyman;
import lotr.common.entity.npc.LOTREntityDaleMan;
import lotr.common.entity.npc.LOTREntityDaleSoldier;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDaleWatchtower extends LOTRWorldGenDaleStructure {
   public LOTRWorldGenDaleWatchtower(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 3);
      this.setupRandomBlocks(random);
      int soldiers;
      int l;
      int i2;
      if (this.restrictions) {
         for(soldiers = -3; soldiers <= 3; ++soldiers) {
            for(l = -3; l <= 3; ++l) {
               i2 = this.getTopBlock(world, soldiers, l);
               Block block = this.getBlock(world, soldiers, i2 - 1, l);
               if (block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      for(soldiers = -2; soldiers <= 2; ++soldiers) {
         for(l = -2; l <= 2; ++l) {
            for(i2 = 9; i2 <= 13; ++i2) {
               this.setAir(world, soldiers, i2, l);
            }
         }
      }

      int j1;
      int k2;
      for(soldiers = -2; soldiers <= 2; ++soldiers) {
         for(l = -2; l <= 2; ++l) {
            i2 = Math.abs(soldiers);
            k2 = Math.abs(l);

            for(j1 = 8; (j1 >= 0 || !this.isOpaque(world, soldiers, j1, l)) && this.getY(j1) >= 0; --j1) {
               if (i2 == 2 && k2 == 2) {
                  this.setBlockAndMetadata(world, soldiers, j1, l, this.pillarBlock, this.pillarMeta);
               } else {
                  this.setBlockAndMetadata(world, soldiers, j1, l, this.brickBlock, this.brickMeta);
               }

               this.setGrassToDirt(world, soldiers, j1 - 1, l);
            }

            if (i2 == 2 && k2 == 2) {
               for(j1 = 9; j1 <= 11; ++j1) {
                  this.setBlockAndMetadata(world, soldiers, j1, l, this.pillarBlock, this.pillarMeta);
               }

               this.setBlockAndMetadata(world, soldiers, 12, l, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, soldiers, 12, l - 1, this.roofStairBlock, 6);
               this.setBlockAndMetadata(world, soldiers, 12, l + 1, this.roofStairBlock, 7);
               this.setBlockAndMetadata(world, soldiers - 1, 12, l, this.roofStairBlock, 5);
               this.setBlockAndMetadata(world, soldiers + 1, 12, l, this.roofStairBlock, 4);
            }
         }
      }

      for(soldiers = -3; soldiers <= 3; ++soldiers) {
         for(l = -3; l <= 3; ++l) {
            i2 = Math.abs(soldiers);
            k2 = Math.abs(l);
            if (i2 == 1 && k2 == 3 || i2 == 3 && k2 == 1) {
               for(j1 = 3; (j1 >= 0 || !this.isOpaque(world, soldiers, j1, l)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, soldiers, j1, l, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, soldiers, j1 - 1, l);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -1, 4, -3, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 1, 4, -3, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -1, 4, 3, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 1, 4, 3, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, -3, 4, -1, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -3, 4, 1, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 3, 4, -1, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 3, 4, 1, this.brickStairBlock, 0);

      for(soldiers = -1; soldiers <= 1; ++soldiers) {
         for(l = -1; l <= 1; ++l) {
            for(i2 = 1; i2 <= 3; ++i2) {
               this.setAir(world, soldiers, i2, l);
            }

            for(i2 = 5; i2 <= 7; ++i2) {
               this.setAir(world, soldiers, i2, l);
            }

            this.setBlockAndMetadata(world, soldiers, 4, l, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, soldiers, 8, l, this.plankBlock, this.plankMeta);
         }
      }

      for(soldiers = -1; soldiers <= 1; ++soldiers) {
         this.setBlockAndMetadata(world, soldiers, 9, -2, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, soldiers, 9, 2, this.fenceBlock, this.fenceMeta);
      }

      for(soldiers = -1; soldiers <= 1; ++soldiers) {
         this.setBlockAndMetadata(world, -2, 9, soldiers, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 2, 9, soldiers, this.fenceBlock, this.fenceMeta);
      }

      for(soldiers = 1; soldiers <= 7; ++soldiers) {
         this.setBlockAndMetadata(world, 0, soldiers, 1, Blocks.field_150468_ap, 2);
      }

      this.setBlockAndMetadata(world, 0, 8, 1, this.trapdoorBlock, 9);
      this.setBlockAndMetadata(world, 0, 1, -2, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -2, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 3, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 0, 0, 0, this.pillarBlock, this.pillarMeta);
      this.setBlockAndMetadata(world, -2, 1, -1, Blocks.field_150462_ai, 0);
      this.setAir(world, -2, 2, -1);
      this.setBlockAndMetadata(world, -2, 1, 1, LOTRMod.daleTable, 0);
      this.setAir(world, -2, 2, 1);
      this.setBlockAndMetadata(world, 2, 1, -1, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, 2, 2, -1, 5, LOTRFoods.DALE_DRINK);
      this.placeChest(world, random, 2, 1, 1, 5, LOTRChestContents.DALE_WATCHTOWER);
      this.setAir(world, 2, 2, 1);
      int[] var13 = new int[]{-1, 1};
      l = var13.length;

      for(i2 = 0; i2 < l; ++i2) {
         k2 = var13[i2];
         this.setBlockAndMetadata(world, k2, 5, 0, LOTRMod.strawBed, 2);
         this.setBlockAndMetadata(world, k2, 5, -1, LOTRMod.strawBed, 10);
      }

      this.setBlockAndMetadata(world, 0, 7, -1, Blocks.field_150478_aa, 3);
      this.placeWallBanner(world, 0, 7, -2, LOTRItemBanner.BannerType.DALE, 2);
      this.placeWallBanner(world, 0, 7, 2, LOTRItemBanner.BannerType.DALE, 0);
      this.placeWallBanner(world, -2, 7, 0, LOTRItemBanner.BannerType.DALE, 3);
      this.placeWallBanner(world, 2, 7, 0, LOTRItemBanner.BannerType.DALE, 1);

      for(soldiers = -3; soldiers <= 3; ++soldiers) {
         for(l = -3; l <= 3; ++l) {
            i2 = Math.abs(soldiers);
            k2 = Math.abs(l);
            if (i2 != 3 && k2 != 3) {
               if (i2 != 2 && k2 != 2) {
                  if (i2 == 1 || k2 == 1) {
                     this.setBlockAndMetadata(world, soldiers, 14, l, this.roofBlock, this.roofMeta);
                     this.setBlockAndMetadata(world, soldiers, 15, l, this.roofSlabBlock, this.roofSlabMeta);
                  }
               } else {
                  this.setBlockAndMetadata(world, soldiers, 13, l, this.roofBlock, this.roofMeta);
               }
            } else {
               this.setBlockAndMetadata(world, soldiers, 13, l, this.roofSlabBlock, this.roofSlabMeta);
            }
         }
      }

      for(soldiers = -2; soldiers <= 2; ++soldiers) {
         this.setBlockAndMetadata(world, soldiers, 14, -2, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, soldiers, 14, 2, this.roofStairBlock, 3);
      }

      for(soldiers = -2; soldiers <= 2; ++soldiers) {
         this.setBlockAndMetadata(world, -2, 14, soldiers, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 2, 14, soldiers, this.roofStairBlock, 0);
      }

      for(soldiers = -1; soldiers <= 1; ++soldiers) {
         this.setBlockAndMetadata(world, soldiers, 14, -1, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, soldiers, 14, 1, this.roofStairBlock, 6);
      }

      this.setBlockAndMetadata(world, -1, 14, 0, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 14, 0, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 0, 13, 1, Blocks.field_150478_aa, 4);
      soldiers = 1 + random.nextInt(2);

      for(l = 0; l < soldiers; ++l) {
         LOTREntityDaleMan soldier = random.nextBoolean() ? new LOTREntityDaleLevyman(world) : new LOTREntityDaleSoldier(world);
         ((LOTREntityDaleMan)soldier).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)soldier, world, 0, 9, 0, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityDaleLevyman.class, LOTREntityDaleSoldier.class);
      respawner.setCheckRanges(16, -12, 12, 4);
      respawner.setSpawnRanges(2, -2, 2, 16);
      this.placeNPCRespawner(respawner, world, 0, 9, 0);
      return true;
   }
}
