package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerWatchtower extends LOTRWorldGenStructureBase2 {
   private Block woodBlock;
   private int woodMeta;
   private Block plankBlock;
   private int plankMeta;
   private Block fenceBlock;
   private int fenceMeta;
   private Block stairBlock;
   private Block trapdoorBlock;

   public LOTRWorldGenRangerWatchtower(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      int randomWood;
      int step;
      int k1;
      if (this.restrictions) {
         for(randomWood = -4; randomWood <= 4; ++randomWood) {
            for(step = -4; step <= 4; ++step) {
               k1 = this.getTopBlock(world, randomWood, step);
               Block block = this.getBlock(world, randomWood, k1 - 1, step);
               if (block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      randomWood = random.nextInt(4);
      if (randomWood == 0) {
         this.woodBlock = Blocks.field_150364_r;
         this.woodMeta = 0;
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 0;
         this.fenceBlock = Blocks.field_150422_aJ;
         this.fenceMeta = 0;
         this.stairBlock = Blocks.field_150476_ad;
         this.trapdoorBlock = Blocks.field_150415_aT;
      } else if (randomWood == 1) {
         this.woodBlock = Blocks.field_150364_r;
         this.woodMeta = 1;
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 1;
         this.fenceBlock = Blocks.field_150422_aJ;
         this.fenceMeta = 0;
         this.stairBlock = Blocks.field_150485_bF;
         this.trapdoorBlock = LOTRMod.trapdoorSpruce;
      } else if (randomWood == 2) {
         this.woodBlock = LOTRMod.wood2;
         this.woodMeta = 1;
         this.plankBlock = LOTRMod.planks;
         this.plankMeta = 9;
         this.fenceBlock = LOTRMod.fence;
         this.fenceMeta = 9;
         this.stairBlock = LOTRMod.stairsBeech;
         this.trapdoorBlock = LOTRMod.trapdoorBeech;
      } else if (randomWood == 3) {
         this.woodBlock = LOTRMod.wood3;
         this.woodMeta = 0;
         this.plankBlock = LOTRMod.planks;
         this.plankMeta = 12;
         this.fenceBlock = LOTRMod.fence;
         this.fenceMeta = 12;
         this.stairBlock = LOTRMod.stairsMaple;
         this.trapdoorBlock = LOTRMod.trapdoorMaple;
      }

      this.generateSupportPillar(world, -3, 4, -3);
      this.generateSupportPillar(world, -3, 4, 3);
      this.generateSupportPillar(world, 3, 4, -3);
      this.generateSupportPillar(world, 3, 4, 3);

      int i2;
      for(step = -2; step <= 2; ++step) {
         for(k1 = -2; k1 <= 2; ++k1) {
            for(i2 = 5; i2 <= 19; ++i2) {
               this.setAir(world, step, i2, k1);
            }
         }
      }

      for(step = 6; step <= 19; ++step) {
         this.setBlockAndMetadata(world, -2, step, -2, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, -2, step, 2, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, 2, step, -2, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, 2, step, 2, this.woodBlock, this.woodMeta);
      }

      for(step = 5; step <= 10; step += 5) {
         for(k1 = -3; k1 <= 3; ++k1) {
            for(i2 = -3; i2 <= 3; ++i2) {
               this.setBlockAndMetadata(world, k1, step, i2, this.plankBlock, this.plankMeta);
            }
         }

         for(k1 = -4; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, k1, step, -4, this.stairBlock, 2);
            this.setBlockAndMetadata(world, k1, step, 4, this.stairBlock, 3);
         }

         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -4, step, k1, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 4, step, k1, this.stairBlock, 0);
         }

         for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, k1, step + 1, -3, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, k1, step + 1, 3, this.fenceBlock, this.fenceMeta);
         }

         for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, -3, step + 1, k1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 3, step + 1, k1, this.fenceBlock, this.fenceMeta);
         }

         this.setBlockAndMetadata(world, 0, step + 2, -3, Blocks.field_150478_aa, 5);
         this.setBlockAndMetadata(world, 0, step + 2, 3, Blocks.field_150478_aa, 5);
         this.setBlockAndMetadata(world, -3, step + 2, 0, Blocks.field_150478_aa, 5);
         this.setBlockAndMetadata(world, 3, step + 2, 0, Blocks.field_150478_aa, 5);
         this.spawnNPCAndSetHome(new LOTREntityRangerNorth(world), world, -1, step + 1, 0, 8);
      }

      for(step = -2; step <= 2; ++step) {
         for(k1 = -2; k1 <= 2; ++k1) {
            i2 = Math.abs(step);
            int k2 = Math.abs(k1);
            if (i2 < 2 || k2 < 2) {
               this.setBlockAndMetadata(world, step, 15, k1, this.plankBlock, this.plankMeta);
               if (i2 < 2 && k2 == 2 || i2 == 2 && k2 < 2) {
                  this.setBlockAndMetadata(world, step, 16, k1, this.fenceBlock, this.fenceMeta);
               }
            }
         }
      }

      this.setGrassToDirt(world, 0, 0, 0);

      for(step = 1; step <= 25; ++step) {
         this.setBlockAndMetadata(world, 0, step, 0, this.woodBlock, this.woodMeta);
         if (step <= 15) {
            this.setBlockAndMetadata(world, 0, step, -1, Blocks.field_150468_ap, 2);
         }
      }

      this.setBlockAndMetadata(world, 0, 6, -1, this.trapdoorBlock, 0);
      this.setBlockAndMetadata(world, 0, 11, -1, this.trapdoorBlock, 0);
      this.setBlockAndMetadata(world, 0, 17, -2, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 0, 17, 2, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, -2, 17, 0, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 2, 17, 0, Blocks.field_150478_aa, 5);
      this.placeChest(world, random, 0, 16, 1, 0, LOTRChestContents.RANGER_TENT);
      this.setBlockAndMetadata(world, 0, 11, 1, LOTRMod.rangerTable, 0);

      for(step = 17; step <= 18; ++step) {
         this.setBlockAndMetadata(world, -2, step, -2, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, -2, step, 2, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 2, step, -2, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 2, step, 2, this.fenceBlock, this.fenceMeta);
      }

      for(step = 0; step <= 1; ++step) {
         for(k1 = -2 + step; k1 <= 2 - step; ++k1) {
            this.setBlockAndMetadata(world, k1, 20 + step, -2 + step, this.stairBlock, 2);
            this.setBlockAndMetadata(world, k1, 20 + step, 2 - step, this.stairBlock, 3);
         }

         for(k1 = -1 + step; k1 <= 1 - step; ++k1) {
            this.setBlockAndMetadata(world, -2 + step, 20 + step, k1, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 2 - step, 20 + step, k1, this.stairBlock, 0);
         }
      }

      this.placeWallBanner(world, -2, 15, 0, LOTRItemBanner.BannerType.RANGER_NORTH, 3);
      this.placeWallBanner(world, 2, 15, 0, LOTRItemBanner.BannerType.RANGER_NORTH, 1);
      this.placeWallBanner(world, 0, 15, -2, LOTRItemBanner.BannerType.RANGER_NORTH, 2);
      this.placeWallBanner(world, 0, 15, 2, LOTRItemBanner.BannerType.RANGER_NORTH, 0);

      for(step = 24; step <= 25; ++step) {
         this.setBlockAndMetadata(world, 1, step, 0, Blocks.field_150325_L, 13);
         this.setBlockAndMetadata(world, 2, step, 1, Blocks.field_150325_L, 13);
         this.setBlockAndMetadata(world, 2, step, 2, Blocks.field_150325_L, 13);
         this.setBlockAndMetadata(world, 3, step, 3, Blocks.field_150325_L, 13);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClass(LOTREntityRangerNorth.class);
      respawner.setCheckRanges(24, -12, 20, 8);
      respawner.setSpawnRanges(4, -4, 4, 16);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      return true;
   }

   private void generateSupportPillar(World world, int i, int j, int k) {
      for(int j1 = j; !this.isOpaque(world, i, j1, k) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, i, j1, k, this.woodBlock, this.woodMeta);
         this.setGrassToDirt(world, i, j1 - 1, i);
      }

   }
}
