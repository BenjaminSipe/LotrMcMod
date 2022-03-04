package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGulfAltar extends LOTRWorldGenGulfStructure {
   public LOTRWorldGenGulfAltar(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 13);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int j1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(j1 = -12; j1 <= 12; ++j1) {
            for(int k1 = -12; k1 <= 8; ++k1) {
               j1 = this.getTopBlock(world, j1, k1) - 1;
               if (!this.isSurface(world, j1, j1, k1)) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 16) {
                  return false;
               }
            }
         }
      }

      for(minHeight = -3; minHeight <= 3; ++minHeight) {
         for(maxHeight = -3; maxHeight <= 3; ++maxHeight) {
            for(j1 = 5; j1 <= 10; ++j1) {
               this.setAir(world, minHeight, j1, maxHeight);
            }
         }
      }

      this.loadStrScan("gulf_altar");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
      this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
      this.generateStrScan(world, random, 0, 0, 0);
      this.placeSkull(world, 0, 7, 0, 0);
      int holeX = 0;
      int holeZ = 6;
      int holeR = 3;
      int holeD = true;
      int k1;
      int i2;
      int k2;
      int i1;
      if (this.getTopBlock(world, holeX, holeZ) >= -8) {
         for(j1 = -holeR; j1 <= holeR; ++j1) {
            for(k1 = -holeR; k1 <= holeR; ++k1) {
               i2 = holeX + j1;
               k2 = holeZ + k1;
               i1 = j1 * j1 + k1 * k1;
               if (i1 < holeR * holeR) {
                  int holeY = this.getTopBlock(world, i2, k2) - 1;
                  if (this.isSurface(world, i2, holeY, k2)) {
                     int holeDHere = (int)Math.round(Math.sqrt((double)Math.max(0, holeR * holeR - i1)));

                     for(int j1 = 3; j1 >= -holeDHere; --j1) {
                        int j2 = holeY + j1;
                        if (j1 > 0) {
                           this.setAir(world, i2, j2, k2);
                        } else if (j1 > -holeDHere) {
                           this.setAir(world, i2, j2, k2);
                        } else if (j1 == -holeDHere) {
                           if (random.nextBoolean()) {
                              this.setBlockAndMetadata(world, i2, j2, k2, Blocks.field_150346_d, 1);
                           } else {
                              this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.wasteBlock, 0);
                           }

                           if (random.nextInt(6) == 0) {
                              this.placeSkull(world, random, i2, j2 + 1, k2);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      int maxSteps = 12;

      for(k1 = -1; k1 <= 1; ++k1) {
         for(i2 = 0; i2 < maxSteps; ++i2) {
            k2 = 0 - i2 / 2;
            i1 = -13 - i2;
            if (this.isOpaque(world, k1, k2, i1)) {
               break;
            }

            if (i2 % 2 == 0) {
               this.setBlockAndMetadata(world, k1, k2, i1, this.plankBlock, this.plankMeta);
               this.setGrassToDirt(world, k1, k2 - 1, i1);
            } else {
               this.setBlockAndMetadata(world, k1, k2, i1, this.plankSlabBlock, this.plankSlabMeta);
               this.setBlockAndMetadata(world, k1, k2 - 1, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
            }
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         for(i2 = 0; i2 < maxSteps; ++i2) {
            k2 = 0 - i2 / 2;
            i1 = -13 - i2;
            if (this.isOpaque(world, i1, k2, k1)) {
               break;
            }

            if (i2 % 2 == 0) {
               this.setBlockAndMetadata(world, i1, k2, k1, this.plankBlock, this.plankMeta);
               this.setGrassToDirt(world, i1, k2 - 1, k1);
            } else {
               this.setBlockAndMetadata(world, i1, k2, k1, this.plankSlabBlock, this.plankSlabMeta);
               this.setBlockAndMetadata(world, i1, k2 - 1, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            }
         }

         for(i2 = 0; i2 < maxSteps; ++i2) {
            k2 = 0 - i2 / 2;
            i1 = 13 + i2;
            if (this.isOpaque(world, i1, k2, k1)) {
               break;
            }

            if (i2 % 2 == 0) {
               this.setBlockAndMetadata(world, i1, k2, k1, this.plankBlock, this.plankMeta);
               this.setGrassToDirt(world, i1, k2 - 1, k1);
            } else {
               this.setBlockAndMetadata(world, i1, k2, k1, this.plankSlabBlock, this.plankSlabMeta);
               this.setBlockAndMetadata(world, i1, k2 - 1, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            }
         }
      }

      LOTREntityGulfHaradrim gulfman = new LOTREntityGulfHaradrim(world);
      this.spawnNPCAndSetHome(gulfman, world, 0, 7, -1, 4);
      return true;
   }
}
