package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDorwinionCaptain;
import lotr.common.entity.npc.LOTREntityDorwinionCrossbower;
import lotr.common.entity.npc.LOTREntityDorwinionElfCaptain;
import lotr.common.entity.npc.LOTREntityDorwinionGuard;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionCaptainTent extends LOTRWorldGenDorwinionTent {
   public LOTRWorldGenDorwinionCaptainTent(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 7);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      if (this.restrictions) {
         int minHeight = 0;
         int maxHeight = 0;
         i1 = 0;
         k1 = -15;

         while(true) {
            if (k1 > 15) {
               this.originY = this.getY(i1);
               break;
            }

            for(i2 = -15; i2 <= 15; ++i2) {
               k2 = this.getTopBlock(world, k1, i2);
               Block block = this.getBlock(world, k1, k2 - 1, i2);
               if (block != Blocks.field_150349_c) {
                  return false;
               }

               if (k2 < minHeight) {
                  minHeight = k2;
               }

               if (k2 > maxHeight) {
                  maxHeight = k2;
               }

               if (maxHeight - minHeight > 6) {
                  return false;
               }

               if ((Math.abs(k1) == 8 || Math.abs(i2) == 8) && k2 > i1) {
                  i1 = k2;
               }
            }

            ++k1;
         }
      }

      int r = 35;
      int h = 7;

      int j1;
      int j1;
      boolean inside;
      for(i1 = -r; i1 <= r; ++i1) {
         for(k1 = -r; k1 <= r; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            inside = false;

            for(j1 = 0; j1 >= -h; --j1) {
               j1 = j1 + r - 3;
               int d = i2 * i2 + j1 * j1 + k2 * k2;
               if (d < r * r) {
                  boolean grass = !this.isOpaque(world, i1, j1 + 1, k1);
                  this.setBlockAndMetadata(world, i1, j1, k1, (Block)(grass ? Blocks.field_150349_c : Blocks.field_150346_d), 0);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
                  inside = true;
               }
            }

            if (inside) {
               for(j1 = -h - 1; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            }
         }
      }

      for(i1 = -6; i1 <= 6; ++i1) {
         for(k1 = -6; k1 <= 6; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            inside = false;
            if (i2 <= 3 && k2 <= 3) {
               inside = true;
            }

            if (i2 == 4 && k2 <= 3 || k2 == 4 && i2 <= 3) {
               inside = true;
            }

            if (i2 == 5 && k2 <= 2 || k2 == 5 && i2 <= 2) {
               inside = true;
            }

            if (inside) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.floorBlock, this.floorMeta);

               for(j1 = 1; j1 <= 4; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }

            if (i2 == 6 && k2 == 2 || k2 == 6 && i2 == 2 || i2 == 4 && k2 == 4) {
               this.setGrassToDirt(world, i1, 0, k1);

               for(j1 = 1; j1 <= 3; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
               }

               this.setBlockAndMetadata(world, i1, 4, k1, this.clay2SlabBlock, this.clay2SlabMeta);
            }

            if (i2 == 5 && k2 == 3 || k2 == 5 && i2 == 3) {
               this.setGrassToDirt(world, i1, 0, k1);
               this.setBlockAndMetadata(world, i1, 1, k1, this.wool1Block, this.wool1Meta);
               this.setBlockAndMetadata(world, i1, 2, k1, this.wool2Block, this.wool2Meta);
               this.setBlockAndMetadata(world, i1, 3, k1, this.wool1Block, this.wool1Meta);
               this.setBlockAndMetadata(world, i1, 4, k1, this.clay2SlabBlock, this.clay2SlabMeta);
            }

            if (i2 == 5 && k2 == 4 || k2 == 5 && i2 == 4) {
               for(j1 = 1; j1 <= 2; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
               }

               this.setBlockAndMetadata(world, i1, 3, k1, this.clay1Block, this.clay1Meta);
            }

            if (i2 == 6 && k2 == 3 || k2 == 6 && i2 == 3) {
               this.setBlockAndMetadata(world, i1, 3, k1, this.clay1SlabBlock, this.clay1SlabMeta | 8);
            }

            if (i2 == 6 && k2 <= 1 || k2 == 6 && i2 <= 1) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.floorBlock, this.floorMeta);
               j1 = Direction.field_71582_c[Direction.func_82372_a((double)i1, (double)k1)];

               for(j1 = 1; j1 <= 3; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.gateWooden, j1);
               }

               this.setBlockAndMetadata(world, i1, 4, k1, this.clay2SlabBlock, this.clay2SlabMeta);
            }

            if (i2 == 5 && k2 == 2 || k2 == 5 && i2 == 2) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
               this.setBlockAndMetadata(world, i1, 2, k1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, i1, 4, k1, this.clay1Block, this.clay1Meta);
            }

            if (i2 == 5 && k2 <= 1 || k2 == 5 && i2 <= 1) {
               this.setBlockAndMetadata(world, i1, 4, k1, this.clay1SlabBlock, this.clay1SlabMeta | 8);
            }

            if (i2 == 4 && k2 == 3 || k2 == 4 && i2 == 3) {
               this.setBlockAndMetadata(world, i1, 4, k1, this.clay1SlabBlock, this.clay1SlabMeta | 8);
            }

            if (i2 == 4 && k2 <= 2 || k2 == 4 && i2 <= 2 || i2 == 3 && k2 == 3) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.clay2SlabBlock, this.clay2SlabMeta);
            }

            if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2 || i2 == 2 && k2 == 2) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.clay1SlabBlock, this.clay1SlabMeta);
            }

            if (i2 == 2 && k2 == 1 || k2 == 2 && i2 == 1) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.clay2SlabBlock, this.clay2SlabMeta);
            }

            if (i2 == 0 && k2 == 2 || k2 == 0 && i2 == 2 || i2 == 1 && k2 == 1) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.clay2Block, this.clay2Meta);
            }

            if (i2 == 0 && k2 == 1 || k2 == 0 && i2 == 1 || i2 == 0 && k2 == 0) {
               this.setBlockAndMetadata(world, i1, 5, k1, LOTRMod.silverBars, 0);
            }

            if (i2 == 2 && k2 <= 1 || k2 == 2 && i2 <= 1 || i2 <= 1 && k2 <= 1) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.plankBlock, this.plankMeta);
            }

            if (i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
               for(j1 = 1; j1 <= 4; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 1, 0, LOTRMod.commandTable, 0);
      this.setBlockAndMetadata(world, 0, 3, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 3, 3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -3, 3, 0, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 3, 3, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -3, 1, -4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -3, 1, -3, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -4, 1, -3, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -3, 1, -2, this.plankSlabBlock, this.plankSlabMeta);
      this.setBlockAndMetadata(world, -4, 1, -2, this.plankSlabBlock, this.plankSlabMeta);
      this.setBlockAndMetadata(world, -3, 2, -3, Blocks.field_150324_C, 2);
      this.setBlockAndMetadata(world, -3, 2, -4, Blocks.field_150324_C, 10);
      this.setBlockAndMetadata(world, 3, 1, -4, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 4, 1, -3, LOTRMod.dorwinionTable, 0);
      this.placeChest(world, random, -4, 1, 3, 2, LOTRChestContents.DORWINION_CAMP);
      this.placeChest(world, random, -3, 1, 4, 4, LOTRChestContents.DORWINION_CAMP);
      this.setBlockAndMetadata(world, 2, 1, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.placeMug(world, random, 2, 2, 4, 1, LOTRFoods.DORWINION_DRINK);
      this.setBlockAndMetadata(world, 3, 1, 3, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.placeMug(world, random, 3, 2, 3, 0, LOTRFoods.DORWINION_DRINK);
      this.setBlockAndMetadata(world, 4, 1, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.placeMug(world, random, 4, 2, 2, 1, LOTRFoods.DORWINION_DRINK);
      this.setBlockAndMetadata(world, 3, 1, 4, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, 3, 2, 4, 5, LOTRFoods.DORWINION_DRINK);
      this.setBlockAndMetadata(world, 4, 1, 3, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, 4, 2, 3, 2, LOTRFoods.DORWINION_DRINK);
      int[] var20 = new int[]{-2, 2};
      k1 = var20.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var20[i2];
         this.placeWallBanner(world, k2, 3, -6, LOTRItemBanner.BannerType.DORWINION, 2);
         this.placeWallBanner(world, k2, 3, 6, LOTRItemBanner.BannerType.DORWINION, 0);
      }

      var20 = new int[]{-2, 2};
      k1 = var20.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var20[i2];
         this.placeWallBanner(world, -6, 3, k2, LOTRItemBanner.BannerType.DORWINION, 3);
         this.placeWallBanner(world, 6, 3, k2, LOTRItemBanner.BannerType.DORWINION, 1);
      }

      LOTREntityDorwinionCaptain captain = new LOTREntityDorwinionCaptain(world);
      this.spawnNPCAndSetHome(captain, world, 0, 1, -1, 16);
      if (random.nextInt(3) == 0) {
         LOTREntityDorwinionElfCaptain elfCaptain = new LOTREntityDorwinionElfCaptain(world);
         this.spawnNPCAndSetHome(elfCaptain, world, 0, 1, -1, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityDorwinionGuard.class, LOTREntityDorwinionCrossbower.class);
      respawner.setCheckRanges(24, -12, 12, 12);
      respawner.setSpawnRanges(12, -2, 2, 16);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      return true;
   }
}
