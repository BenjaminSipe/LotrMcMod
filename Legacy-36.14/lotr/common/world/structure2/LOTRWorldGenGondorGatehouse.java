package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorGatehouse extends LOTRWorldGenGondorStructure {
   private String[] signText = LOTRNames.getGondorVillageName(new Random());

   public LOTRWorldGenGondorGatehouse(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenGondorGatehouse setSignText(String[] s) {
      this.signText = s;
      return this;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int k1;
      int i2;
      int step;
      int i1;
      int j1;
      if (this.restrictions) {
         k1 = 0;
         i2 = 0;

         for(step = -6; step <= 6; ++step) {
            for(i1 = -5; i1 <= 5; ++i1) {
               j1 = this.getTopBlock(world, step, i1) - 1;
               if (!this.isSurface(world, step, j1, i1)) {
                  return false;
               }

               if (j1 < k1) {
                  k1 = j1;
               }

               if (j1 > i2) {
                  i2 = j1;
               }

               if (i2 - k1 > 8) {
                  return false;
               }
            }
         }
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         for(i2 = -3; i2 <= 3; ++i2) {
            step = Math.abs(k1);
            i1 = Math.abs(i2);

            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, k1, j1, i2)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, k1, j1, i2, this.cobbleBlock, this.cobbleMeta);
               this.setGrassToDirt(world, k1, j1 - 1, i2);
            }

            for(j1 = 1; j1 <= 14; ++j1) {
               this.setAir(world, k1, j1, i2);
            }

            if (step == 3 && i1 == 3) {
               for(j1 = 1; j1 <= 10; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, i2, this.pillarBlock, this.pillarMeta);
               }
            } else {
               if (step == 3) {
                  for(j1 = 1; j1 <= 6; ++j1) {
                     this.setBlockAndMetadata(world, k1, j1, i2, this.brickBlock, this.brickMeta);
                  }
               }

               this.setBlockAndMetadata(world, k1, 7, i2, this.brickBlock, this.brickMeta);
            }

            if (step <= 3 && i1 <= 3) {
               if (step != 3 && i1 != 3) {
                  this.setBlockAndMetadata(world, k1, 11, i2, this.rockSlabBlock, this.rockSlabMeta | 8);
               } else {
                  this.setBlockAndMetadata(world, k1, 11, i2, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
               }
            }
         }
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, k1, 0, -1, this.cobbleStairBlock, 3);
         this.setBlockAndMetadata(world, k1, 0, 1, this.cobbleStairBlock, 2);
         this.setBlockAndMetadata(world, k1, -1, 0, this.cobbleBlock, this.cobbleMeta);
         this.setGrassToDirt(world, k1, -2, 0);
         this.setAir(world, k1, 0, 0);
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         for(i2 = 1; i2 <= 7; ++i2) {
            if (i2 <= 6 || k1 == 0) {
               this.setBlockAndMetadata(world, k1, i2, -1, this.gateBlock, 2);
               this.setBlockAndMetadata(world, k1, i2, 1, LOTRMod.gateIronBars, 2);
            }
         }
      }

      int[] var15 = new int[]{-3, 3};
      i2 = var15.length;

      int j2;
      for(step = 0; step < i2; ++step) {
         i1 = var15[step];
         this.setBlockAndMetadata(world, -2, 6, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 2, 6, i1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, -2, 5, i1, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, 2, 5, i1, Blocks.field_150478_aa, 1);

         for(j1 = -2; j1 <= 2; ++j1) {
            j2 = Math.abs(j1);
            this.setBlockAndMetadata(world, j1, 8, i1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
            if (j2 % 2 == 0) {
               this.setBlockAndMetadata(world, j1, 9, i1, LOTRMod.gateIronBars, 2);
            } else {
               this.setBlockAndMetadata(world, j1, 9, i1, this.brickBlock, this.brickMeta);
            }

            if (j2 == 0) {
               this.setBlockAndMetadata(world, j1, 10, i1, LOTRMod.brick, 5);
            } else {
               this.setBlockAndMetadata(world, j1, 10, i1, this.brickBlock, this.brickMeta);
            }
         }
      }

      var15 = new int[]{-3, 3};
      i2 = var15.length;

      for(step = 0; step < i2; ++step) {
         i1 = var15[step];
         int[] var20 = new int[]{-2, 2};
         j2 = var20.length;

         for(int var13 = 0; var13 < j2; ++var13) {
            int k1 = var20[var13];
            this.setBlockAndMetadata(world, i1, 8, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
            this.setBlockAndMetadata(world, i1, 9, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 10, k1, this.brickBlock, this.brickMeta);
         }

         this.setBlockAndMetadata(world, i1, 10, -1, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 10, 1, this.brickStairBlock, 6);
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, k1, 11, -4, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, k1, 11, 4, this.brickStairBlock, 7);
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, -4, 11, k1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 4, 11, k1, this.brickStairBlock, 4);
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         for(i2 = -4; i2 <= 4; ++i2) {
            step = Math.abs(k1);
            i1 = Math.abs(i2);
            if ((step <= 3 || i1 <= 3) && (step == 4 || i1 == 4)) {
               if ((step + i1) % 2 == 1) {
                  this.setBlockAndMetadata(world, k1, 12, i2, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, k1, 13, i2, this.brickSlabBlock, this.brickSlabMeta);
               } else {
                  this.setBlockAndMetadata(world, k1, 12, i2, this.brickWallBlock, this.brickWallMeta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 8, -1, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 8, 0, this.fenceBlock, this.fenceMeta);
      this.setAir(world, 0, 7, 0);
      this.setBlockAndMetadata(world, 0, 8, 1, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 9, -1, Blocks.field_150442_at, 6);
      this.setBlockAndMetadata(world, 0, 9, 1, Blocks.field_150442_at, 6);
      var15 = new int[]{-1, 1};
      i2 = var15.length;

      for(step = 0; step < i2; ++step) {
         i1 = var15[step];

         for(j1 = 8; j1 <= 11; ++j1) {
            this.setBlockAndMetadata(world, i1, j1, 2, Blocks.field_150468_ap, 2);
         }
      }

      this.setBlockAndMetadata(world, -2, 10, -2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 10, -2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -2, 10, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 10, 2, Blocks.field_150478_aa, 1);
      this.placeWallBanner(world, 1, 10, -3, this.bannerType2, 0);
      this.placeWallBanner(world, -1, 10, -3, this.bannerType, 0);
      this.setBlockAndMetadata(world, -3, 12, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 3, 12, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -3, 12, 3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 3, 12, 3, Blocks.field_150478_aa, 4);
      this.placeWallBanner(world, -3, 7, -3, this.bannerType2, 2);
      this.placeWallBanner(world, 3, 7, -3, this.bannerType, 2);
      this.placeWallBanner(world, 3, 7, 3, this.bannerType2, 0);
      this.placeWallBanner(world, -3, 7, 3, this.bannerType, 0);
      if (this.signText != null) {
         this.placeSign(world, -3, 3, -4, Blocks.field_150444_as, 2, this.signText);
         this.placeSign(world, 3, 3, -4, Blocks.field_150444_as, 2, this.signText);
      }

      for(k1 = -5; k1 <= 5; ++k1) {
         i2 = Math.abs(k1);
         byte k1;
         if (i2 >= 4) {
            step = -1;

            while(true) {
               if (step > 1) {
                  k1 = -2;

                  for(i1 = 7; (i1 >= 0 || !this.isOpaque(world, k1, i1, k1)) && this.getY(i1) >= 0; --i1) {
                     this.setBlockAndMetadata(world, k1, i1, k1, this.brickBlock, this.brickMeta);
                     this.setGrassToDirt(world, k1, i1 - 1, k1);
                  }

                  this.setBlockAndMetadata(world, k1, 4, k1, this.brick2Block, this.brick2Meta);
                  this.setBlockAndMetadata(world, k1, 8, k1, this.rockWallBlock, this.rockWallMeta);
                  break;
               }

               for(i1 = 4; (i1 >= 0 || !this.isOpaque(world, k1, i1, step)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, k1, i1, step, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, k1, i1 - 1, step);
               }

               ++step;
            }
         }

         if (i2 == 5) {
            k1 = -3;

            for(i1 = 7; (i1 >= 0 || !this.isOpaque(world, k1, i1, k1)) && this.getY(i1) >= 0; --i1) {
               this.setBlockAndMetadata(world, k1, i1, k1, this.pillarBlock, this.pillarMeta);
               this.setGrassToDirt(world, k1, i1 - 1, k1);
            }

            this.setBlockAndMetadata(world, k1, 8, k1, this.rockWallBlock, this.rockWallMeta);
         }

         if (i2 == 4) {
            k1 = -3;
            this.setBlockAndMetadata(world, k1, 5, k1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, k1, 6, k1, this.rockWallBlock, this.rockWallMeta);
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         this.setBlockAndMetadata(world, -3, 7, k1, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, -4, 6, k1, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, -5, 5, k1, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, -4, 5, k1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 3, 7, k1, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, 4, 6, k1, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, 5, 5, k1, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, 4, 5, k1, this.brickBlock, this.brickMeta);
      }

      for(k1 = -8; k1 <= 8; ++k1) {
         i2 = Math.abs(k1);
         if (i2 >= 6) {
            for(step = 0; step <= 1; ++step) {
               for(i1 = 4; (i1 >= 0 || !this.isOpaque(world, k1, i1, step)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, k1, i1, step, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, k1, i1 - 1, step);
               }
            }
         }
      }

      for(k1 = 0; k1 <= 1; ++k1) {
         int maxStep = 12;

         for(step = 0; step < maxStep; ++step) {
            i1 = -9 - step;
            j1 = 4 - step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 1);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }

         for(step = 0; step < maxStep; ++step) {
            i1 = 9 + step;
            j1 = 4 - step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 0);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      for(k1 = -9; k1 <= 9; ++k1) {
         i2 = Math.abs(k1);
         if (i2 != 5 && i2 != 8) {
            if (i2 >= 4) {
               this.setBlockAndMetadata(world, k1, 3, 1, this.brickStairBlock, 7);
            }
         } else {
            this.setBlockAndMetadata(world, k1, 3, 1, LOTRMod.brick, 5);
         }
      }

      var15 = new int[]{-1, 1};
      i2 = var15.length;

      for(step = 0; step < i2; ++step) {
         i1 = var15[step];
         int j1 = 8;
         int k1 = 0;
         LOTREntityNPC levyman = (LOTREntityNPC)LOTREntities.createEntityByClass(this.strFief.getLevyClasses()[0], world);
         this.spawnNPCAndSetHome(levyman, world, i1, j1, k1, 8);
      }

      return true;
   }
}
