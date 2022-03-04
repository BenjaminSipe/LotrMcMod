package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityEasterlingWarrior;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingGatehouse extends LOTRWorldGenEasterlingStructureTown {
   private String[] signText = LOTRNames.getRhunVillageName(new Random());
   private boolean enableSigns = true;

   public LOTRWorldGenEasterlingGatehouse(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenEasterlingGatehouse setSignText(String[] s) {
      this.signText = s;
      return this;
   }

   public LOTRWorldGenEasterlingGatehouse disableSigns() {
      this.enableSigns = false;
      return this;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int step;
      if (this.restrictions) {
         for(i1 = -7; i1 <= 7; ++i1) {
            for(k1 = -4; k1 <= 4; ++k1) {
               step = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, step, k1)) {
                  return false;
               }
            }
         }
      }

      int i1;
      int j1;
      for(i1 = -7; i1 <= 7; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            step = Math.abs(i1);
            i1 = Math.abs(k1);

            for(j1 = 1; j1 <= 12; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (step <= 3 && i1 <= 3 || step <= 6 && i1 <= 2 || step <= 7 && i1 <= 1) {
               for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            }

            if (step == 3 && i1 <= 2) {
               if (i1 == 0) {
                  for(j1 = 1; j1 <= 6; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
                  }
               } else {
                  for(j1 = 1; j1 <= 6; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  }
               }
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, 0, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, i1, 0, -2, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, i1, 0, 2, this.brickStairBlock, 2);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -2, 6, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 2, 6, i1, this.brickStairBlock, 5);
      }

      int[] var16 = new int[]{-3, 3};
      k1 = var16.length;

      int j2;
      int j1;
      for(step = 0; step < k1; ++step) {
         i1 = var16[step];
         int[] var18 = new int[]{-3, 3};
         j2 = var18.length;

         for(j1 = 0; j1 < j2; ++j1) {
            int i1 = var18[j1];

            for(int j1 = 1; j1 <= 6; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, i1, this.brickRedBlock, this.brickRedMeta);
            }
         }

         this.setBlockAndMetadata(world, -2, 6, i1, this.brickRedStairBlock, 4);
         this.setBlockAndMetadata(world, 2, 6, i1, this.brickRedStairBlock, 5);
         this.setBlockAndMetadata(world, -3, 7, i1, this.brickRedStairBlock, 1);
         this.setBlockAndMetadata(world, -2, 7, i1, this.brickRedBlock, this.brickRedMeta);
         this.setBlockAndMetadata(world, -1, 7, i1, this.brickRedBlock, this.brickRedMeta);
         this.setBlockAndMetadata(world, 0, 7, i1, this.brickRedStairBlock, i1 > 0 ? 7 : 6);
         this.setBlockAndMetadata(world, 1, 7, i1, this.brickRedBlock, this.brickRedMeta);
         this.setBlockAndMetadata(world, 2, 7, i1, this.brickRedBlock, this.brickRedMeta);
         this.setBlockAndMetadata(world, 3, 7, i1, this.brickRedStairBlock, 0);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            if (k1 == 0) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.brickRedBlock, this.brickRedMeta);
            } else {
               this.setBlockAndMetadata(world, i1, 7, k1, this.pillarBlock, this.pillarMeta);
            }
         }
      }

      var16 = new int[]{-2, 2};
      k1 = var16.length;

      for(step = 0; step < k1; ++step) {
         i1 = var16[step];

         for(j1 = -2; j1 <= 2; ++j1) {
            for(j2 = 1; j2 <= (Math.abs(j1) <= 1 ? 7 : 6); ++j2) {
               this.setBlockAndMetadata(world, j1, j2, i1, this.gateBlock, i1 > 0 ? 2 : 3);
            }
         }

         this.setBlockAndMetadata(world, -1, 8, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 0, 8, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 1, 8, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 0, 9, i1, Blocks.field_150442_at, 6);
      }

      for(i1 = 7; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, -3, i1, -2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 3, i1, -2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -3, i1, 2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 3, i1, 2, this.brickBlock, this.brickMeta);
      }

      var16 = new int[]{-3, 3};
      k1 = var16.length;

      for(step = 0; step < k1; ++step) {
         i1 = var16[step];
         this.setBlockAndMetadata(world, -3, 8, i1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, -2, 8, i1, this.brickStairBlock, i1 > 0 ? 3 : 2);
         this.setBlockAndMetadata(world, -1, 8, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 0, 8, i1, this.brickStairBlock, i1 > 0 ? 3 : 2);
         this.setBlockAndMetadata(world, 1, 8, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 2, 8, i1, this.brickStairBlock, i1 > 0 ? 3 : 2);
         this.setBlockAndMetadata(world, 3, 8, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, -3, 9, i1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, -1, 9, i1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, 1, 9, i1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, 3, 9, i1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, -3, 10, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -2, 10, i1, this.brickStairBlock, i1 > 0 ? 7 : 6);
         this.setBlockAndMetadata(world, -1, 10, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 0, 10, i1, this.brickStairBlock, i1 > 0 ? 7 : 6);
         this.setBlockAndMetadata(world, 1, 10, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 2, 10, i1, this.brickStairBlock, i1 > 0 ? 7 : 6);
         this.setBlockAndMetadata(world, 3, 10, i1, this.brickBlock, this.brickMeta);

         for(j1 = -3; j1 <= 3; ++j1) {
            this.setBlockAndMetadata(world, j1, 11, i1, this.brickBlock, this.brickMeta);
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -3, 11, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 3, 11, i1, this.brickBlock, this.brickMeta);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            step = Math.abs(i1);
            i1 = Math.abs(k1);
            if (step + i1 <= 1) {
               this.setBlockAndMetadata(world, i1, 12, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
            } else {
               this.setBlockAndMetadata(world, i1, 12, k1, this.brickBlock, this.brickMeta);
            }

            if (step == 2 && i1 == 2) {
               this.setBlockAndMetadata(world, i1, 11, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
            }
         }
      }

      this.setBlockAndMetadata(world, -2, 10, -2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 10, -2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -2, 10, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 10, 2, Blocks.field_150478_aa, 1);

      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -4; k1 <= 4; ++k1) {
            step = Math.abs(i1);
            i1 = Math.abs(k1);
            if (step == 4 || i1 == 4) {
               if ((step + i1) % 2 == 0) {
                  this.setBlockAndMetadata(world, i1, 13, k1, this.roofSlabBlock, this.roofSlabMeta);
                  if (step <= 2 || i1 <= 2) {
                     this.setBlockAndMetadata(world, i1, 12, k1, this.fenceBlock, this.fenceMeta);
                  }
               } else if (i1 == 4 && step == 3) {
                  this.setBlockAndMetadata(world, i1, 12, k1, this.roofStairBlock, k1 > 0 ? 7 : 6);
               } else if (step == 4 && i1 == 3) {
                  this.setBlockAndMetadata(world, i1, 12, k1, this.roofStairBlock, i1 > 0 ? 4 : 5);
               } else {
                  this.setBlockAndMetadata(world, i1, 12, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 13, -3, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 13, 3, this.roofStairBlock, 3);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -3, 13, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 3, 13, i1, this.roofStairBlock, 0);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            step = Math.abs(i1);
            i1 = Math.abs(k1);
            this.setBlockAndMetadata(world, i1, 13, k1, this.roofBlock, this.roofMeta);
            if (step == 2 && i1 == 2) {
               this.setBlockAndMetadata(world, i1, 14, k1, this.roofSlabBlock, this.roofSlabMeta);
            } else {
               this.setBlockAndMetadata(world, i1, 14, k1, this.roofBlock, this.roofMeta);
            }

            if (step == 2 && i1 == 0 || i1 == 2 && step == 0) {
               this.setBlockAndMetadata(world, i1, 15, k1, this.roofSlabBlock, this.roofSlabMeta);
            }

            if (step <= 1 && i1 <= 1) {
               this.setBlockAndMetadata(world, i1, 15, k1, this.roofBlock, this.roofMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 16, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, 17, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, 18, 0, this.roofWallBlock, this.roofWallMeta);
      this.setBlockAndMetadata(world, 0, 19, 0, this.roofWallBlock, this.roofWallMeta);
      this.setBlockAndMetadata(world, 0, 16, -1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 16, 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -1, 16, 0, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 16, 0, this.roofStairBlock, 0);

      for(i1 = -7; i1 <= 7; ++i1) {
         k1 = Math.abs(i1);
         if (k1 >= 4 && k1 <= 6) {
            int[] var17 = new int[]{-2, 2};
            i1 = var17.length;

            for(j1 = 0; j1 < i1; ++j1) {
               j2 = var17[j1];

               for(j1 = 1; j1 <= 8; ++j1) {
                  if (j1 == 1) {
                     this.setBlockAndMetadata(world, i1, j1, j2, this.brickRedBlock, this.brickRedMeta);
                  } else {
                     this.setBlockAndMetadata(world, i1, j1, j2, this.brickBlock, this.brickMeta);
                  }

                  if (j1 == 3 && k1 == 5) {
                     this.setBlockAndMetadata(world, i1, j1, j2, this.brickCarvedBlock, this.brickCarvedMeta);
                  }
               }

               this.setBlockAndMetadata(world, i1, 9, j2, this.brickWallBlock, this.brickWallMeta);
            }
         }

         if (k1 >= 4 && k1 <= 7) {
            for(step = -1; step <= 2; ++step) {
               for(i1 = 1; i1 <= 5; ++i1) {
                  if (step != 0 && i1 != 1) {
                     this.setBlockAndMetadata(world, i1, i1, step, this.brickBlock, this.brickMeta);
                  } else {
                     this.setBlockAndMetadata(world, i1, i1, step, this.brickRedBlock, this.brickRedMeta);
                  }
               }
            }
         }
      }

      for(i1 = 0; i1 <= 1; ++i1) {
         k1 = 6 + i1;

         for(step = -1; step <= 1; ++step) {
            this.setBlockAndMetadata(world, -4 + i1, k1, step, step == 0 ? this.brickRedStairBlock : this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 4 - i1, k1, step, step == 0 ? this.brickRedStairBlock : this.brickStairBlock, 0);
         }
      }

      this.setBlockAndMetadata(world, -7, 5, -2, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, -7, 6, -2, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -7, 7, -2, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 7, 5, -2, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 7, 6, -2, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 7, 7, -2, this.brickStairBlock, 3);
      this.placeWallBanner(world, -5, 6, -2, this.bannerType, 2);
      this.placeWallBanner(world, 5, 6, -2, this.bannerType, 2);
      this.placeWallBanner(world, -5, 6, 2, this.bannerType, 0);
      this.placeWallBanner(world, 5, 6, 2, this.bannerType, 0);
      if (this.enableSigns && this.signText != null) {
         this.placeSign(world, -3, 3, -4, Blocks.field_150444_as, 2, this.signText);
         this.placeSign(world, 3, 3, -4, Blocks.field_150444_as, 2, this.signText);
      }

      int maxStep = 12;

      for(k1 = 2; k1 <= 2; ++k1) {
         for(step = 0; step < maxStep; ++step) {
            i1 = -8 - step;
            j1 = 5 - step;
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
            i1 = 8 + step;
            j1 = 5 - step;
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

      int men = 2;

      for(step = 0; step < men; ++step) {
         int i1 = 0;
         int j1 = 8;
         int k1 = 0;
         LOTREntityNPC guard = new LOTREntityEasterlingWarrior(world);
         guard.spawnRidingHorse = false;
         this.spawnNPCAndSetHome(guard, world, i1, j1, k1, 8);
      }

      return true;
   }
}
