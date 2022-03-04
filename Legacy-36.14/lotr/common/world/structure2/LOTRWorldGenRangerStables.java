package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerStables extends LOTRWorldGenRangerStructure {
   public LOTRWorldGenRangerStables(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1, -2);
      this.setupRandomBlocks(random);
      int i1;
      int l;
      int h1;
      int i1;
      int k1;
      if (this.restrictions) {
         i1 = 0;
         l = 0;

         for(h1 = -5; h1 <= 5; ++h1) {
            for(i1 = -1; i1 <= 10; ++i1) {
               k1 = this.getTopBlock(world, h1, i1) - 1;
               if (!this.isSurface(world, h1, k1, i1)) {
                  return false;
               }

               if (k1 < i1) {
                  i1 = k1;
               }

               if (k1 > l) {
                  l = k1;
               }

               if (l - i1 > 5) {
                  return false;
               }
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(l = 0; l <= 9; ++l) {
            h1 = Math.abs(i1);

            for(i1 = 0; (i1 >= 0 || !this.isOpaque(world, i1, i1, l)) && this.getY(i1) >= 0; --i1) {
               this.setBlockAndMetadata(world, i1, i1, l, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, i1 - 1, l);
            }

            for(i1 = 1; i1 <= 8; ++i1) {
               this.setAir(world, i1, i1, l);
            }

            if (l <= 4 && (l == 0 || l == 4 || h1 == 4)) {
               boolean beam = false;
               if ((l == 0 || l == 4) && (h1 == 0 || h1 == 4)) {
                  beam = true;
               }

               if (beam) {
                  for(k1 = 1; k1 <= 3; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, l, this.woodBeamBlock, this.woodBeamMeta);
                  }
               } else if (l == 4) {
                  for(k1 = 1; k1 <= 3; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, l, this.plankBlock, this.plankMeta);
                  }
               } else {
                  for(k1 = 1; k1 <= 3; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, l, this.wallBlock, this.wallMeta);
                  }
               }
            }
         }
      }

      int[] var15 = new int[]{0, 4};
      l = var15.length;

      for(h1 = 0; h1 < l; ++h1) {
         i1 = var15[h1];

         for(k1 = -3; k1 <= 3; ++k1) {
            if (k1 != 0) {
               this.setBlockAndMetadata(world, k1, 3, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
            }
         }
      }

      var15 = new int[]{-4, 0, 4};
      l = var15.length;

      for(h1 = 0; h1 < l; ++h1) {
         i1 = var15[h1];

         for(k1 = 0; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }

         for(k1 = 4; k1 <= 6; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 4, this.woodBeamBlock, this.woodBeamMeta);
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, 7, 4, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      var15 = new int[]{-4, 4};
      l = var15.length;

      for(h1 = 0; h1 < l; ++h1) {
         i1 = var15[h1];
         this.setBlockAndMetadata(world, i1, 5, 2, this.wallBlock, this.wallMeta);
         this.setBlockAndMetadata(world, i1, 5, 3, this.wallBlock, this.wallMeta);
         this.setBlockAndMetadata(world, i1, 6, 3, this.wallBlock, this.wallMeta);
      }

      this.setBlockAndMetadata(world, -2, 1, 0, this.doorBlock, 1);
      this.setBlockAndMetadata(world, -2, 2, 0, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 2, 2, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -4, 2, 2, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 4, 2, 2, this.fenceBlock, this.fenceMeta);

      for(i1 = -3; i1 <= 3; ++i1) {
         for(l = 1; l <= 3; ++l) {
            this.setBlockAndMetadata(world, i1, 0, l, this.plankBlock, this.plankMeta);
         }

         if (i1 != 0) {
            for(l = 1; l <= 3; ++l) {
               this.setBlockAndMetadata(world, i1, 4, l, this.plankSlabBlock, this.plankSlabMeta | 8);
            }

            this.setBlockAndMetadata(world, i1, 4, 4, this.plankBlock, this.plankMeta);
         }
      }

      var15 = new int[]{-4, 0, 4};
      l = var15.length;

      for(h1 = 0; h1 < l; ++h1) {
         i1 = var15[h1];

         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 9, this.woodBeamBlock, this.woodBeamMeta);
         }

         for(k1 = 5; k1 <= 9; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }

         for(k1 = 5; k1 <= 8; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
         }

         this.setBlockAndMetadata(world, i1, 2, 5, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 2, 8, this.fenceBlock, this.fenceMeta);
      }

      var15 = new int[]{-4, 4};
      l = var15.length;

      for(h1 = 0; h1 < l; ++h1) {
         i1 = var15[h1];
         this.setBlockAndMetadata(world, i1, 5, 5, this.wallBlock, this.wallMeta);
         this.setBlockAndMetadata(world, i1, 5, 6, this.wallBlock, this.wallMeta);
         this.setBlockAndMetadata(world, i1, 5, 7, this.wallBlock, this.wallMeta);
         this.setBlockAndMetadata(world, i1, 6, 5, this.wallBlock, this.wallMeta);
         this.setBlockAndMetadata(world, i1, 6, 6, this.wallBlock, this.wallMeta);
         this.setBlockAndMetadata(world, i1, 7, 5, this.wallBlock, this.wallMeta);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         if (i1 != 0) {
            this.setBlockAndMetadata(world, i1, 1, 9, this.fenceGateBlock, 2);

            for(l = 5; l <= 8; ++l) {
               h1 = random.nextInt(3);
               if (h1 == 0) {
                  this.setBlockAndMetadata(world, i1, 0, l, Blocks.field_150349_c, 0);
               } else if (h1 == 1) {
                  this.setBlockAndMetadata(world, i1, 0, l, Blocks.field_150346_d, 1);
               } else if (h1 == 2) {
                  this.setBlockAndMetadata(world, i1, 0, l, LOTRMod.dirtPath, 0);
               }

               if (random.nextBoolean()) {
                  this.setBlockAndMetadata(world, i1, 1, l, LOTRMod.thatchFloor, 0);
               }
            }

            this.setBlockAndMetadata(world, i1, 4, 5, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 4, 6, this.plankSlabBlock, this.plankSlabMeta | 8);
         }
      }

      this.setBlockAndMetadata(world, -3, 3, 9, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, -1, 3, 9, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 1, 3, 9, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 3, 3, 9, this.plankStairBlock, 5);

      for(i1 = -5; i1 <= 5; ++i1) {
         boolean avoidBeam = IntMath.mod(i1, 4) == 0;
         if (!avoidBeam) {
            this.setBlockAndMetadata(world, i1, 4, 0, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 4, 9, this.roofStairBlock, 3);
         }

         for(h1 = 0; h1 <= 2; ++h1) {
            this.setBlockAndMetadata(world, i1, 5 + h1, 1 + h1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 5 + h1, 8 - h1, this.roofStairBlock, 3);
         }

         for(h1 = 4; h1 <= 5; ++h1) {
            this.setBlockAndMetadata(world, i1, 8, h1, this.roofSlabBlock, this.roofSlabMeta);
         }

         if (Math.abs(i1) == 5) {
            for(h1 = 0; h1 <= 3; ++h1) {
               this.setBlockAndMetadata(world, i1, 4 + h1, 1 + h1, this.roofStairBlock, 7);
               this.setBlockAndMetadata(world, i1, 4 + h1, 8 - h1, this.roofStairBlock, 6);
            }
         }
      }

      var15 = new int[]{-4, 0, 4};
      l = var15.length;

      for(h1 = 0; h1 < l; ++h1) {
         i1 = var15[h1];
         this.setBlockAndMetadata(world, i1, 3, -1, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, i1, 3, 10, Blocks.field_150478_aa, 3);
      }

      this.setBlockAndMetadata(world, -5, 3, 4, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 5, 3, 4, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -3, 1, 3, Blocks.field_150462_ai, 0);
      this.placeChest(world, random, -2, 1, 3, 2, this.chestContentsHouse);
      this.setBlockAndMetadata(world, -1, 1, 3, this.plankBlock, this.plankMeta);
      this.placePlateWithCertainty(world, random, -1, 2, 3, this.plateBlock, LOTRFoods.RANGER);
      this.setBlockAndMetadata(world, 0, 1, 3, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, 0, 2, 3, 2, LOTRFoods.RANGER_DRINK);
      this.setBlockAndMetadata(world, 1, 1, 3, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 1, 2, 3, 0, LOTRFoods.RANGER_DRINK);
      this.setBlockAndMetadata(world, 3, 1, 3, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 3, 2, 3, 1, LOTRFoods.RANGER_DRINK);
      this.setBlockAndMetadata(world, 2, 1, 1, this.bedBlock, 1);
      this.setBlockAndMetadata(world, 3, 1, 1, this.bedBlock, 9);
      this.setBlockAndMetadata(world, -3, 3, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 3, 2, Blocks.field_150478_aa, 1);

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 2, i1, 3, Blocks.field_150468_ap, 2);
      }

      this.setBlockAndMetadata(world, 0, 7, 5, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, -3, 7, 5, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 3, 7, 5, this.plankStairBlock, 5);

      for(i1 = -3; i1 <= 3; ++i1) {
         if (random.nextInt(3) != 0) {
            this.setBlockAndMetadata(world, i1, 5, 2, Blocks.field_150407_cf, 0);
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         if (random.nextInt(3) != 0) {
            int h = 5;
            h1 = h + random.nextInt(2);

            for(i1 = h; i1 <= h1; ++i1) {
               this.setBlockAndMetadata(world, i1, i1, 6, Blocks.field_150407_cf, 0);
            }
         }
      }

      var15 = new int[]{-3, 3};
      l = var15.length;

      for(h1 = 0; h1 < l; ++h1) {
         i1 = var15[h1];

         for(k1 = 3; k1 <= 5; ++k1) {
            if (random.nextInt(3) != 0) {
               int h = 5;
               int h1 = h + random.nextInt(2);

               for(int j1 = h; j1 <= h1; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150407_cf, 0);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -2, 5, 3, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -3, 5, 3, this.bedBlock, 11);
      this.placeChest(world, random, -3, 5, 5, 4, this.chestContentsHouse);
      this.setBlockAndMetadata(world, -3, 6, 4, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 6, 4, Blocks.field_150478_aa, 1);
      int men = 1;

      for(l = 0; l < men; ++l) {
         LOTREntityDunedain dunedain = new LOTREntityDunedain(world);
         this.spawnNPCAndSetHome(dunedain, world, 0, 1, 2, 8);
      }

      int[] var20 = new int[]{-2, 2};
      h1 = var20.length;

      for(i1 = 0; i1 < h1; ++i1) {
         k1 = var20[i1];
         LOTREntityHorse horse = new LOTREntityHorse(world);
         this.spawnNPCAndSetHome(horse, world, k1, 1, 7, 0);
         horse.func_110214_p(0);
         horse.saddleMountForWorldGen();
         horse.func_110177_bN();
      }

      return true;
   }
}
