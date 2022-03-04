package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityTauredainChieftain;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainChieftainPyramid extends LOTRWorldGenTauredainHouse {
   public LOTRWorldGenTauredainChieftainPyramid(boolean flag) {
      super(flag);
   }

   protected int getOffset() {
      return 11;
   }

   protected boolean useStoneBrick() {
      return true;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
         return false;
      } else {
         int i1;
         int k1;
         int i2;
         for(i1 = -10; i1 <= 10; ++i1) {
            for(k1 = -10; k1 <= 10; ++k1) {
               this.layFoundation(world, i1, k1);

               for(i2 = 1; i2 <= 14; ++i2) {
                  this.setAir(world, i1, i2, k1);
               }
            }
         }

         int k2;
         int j1;
         int k1;
         for(i1 = -10; i1 <= 10; ++i1) {
            for(k1 = -10; k1 <= 10; ++k1) {
               i2 = Math.abs(i1);
               k2 = Math.abs(k1);
               if (i2 < 8 && k2 < 8) {
                  for(j1 = 1; j1 <= 4; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  }

                  if (i2 <= 1 && k1 <= 0) {
                     j1 = 4 - (-3 - k1);
                     if (j1 >= 0 && j1 <= 4) {
                        for(k1 = j1 + 1; k1 <= 4; ++k1) {
                           this.setAir(world, i1, k1, k1);
                        }

                        if (j1 == 0) {
                           this.setBlockAndMetadata(world, -1, j1, k1, this.brickBlock, this.brickMeta);
                           this.setBlockAndMetadata(world, 0, j1, k1, LOTRMod.brick4, 4);
                           this.setBlockAndMetadata(world, 1, j1, k1, this.brickBlock, this.brickMeta);
                        } else if (j1 <= 4) {
                           this.setBlockAndMetadata(world, -1, j1, k1, this.brickStairBlock, 2);
                           this.setBlockAndMetadata(world, 0, j1, k1, LOTRMod.stairsTauredainBrickObsidian, 2);
                           this.setBlockAndMetadata(world, 1, j1, k1, this.brickStairBlock, 2);
                        }
                     }
                  } else if (i2 == 7 && k1 % 2 == 0 || k2 == 7 && i1 % 2 == 0) {
                     this.setBlockAndMetadata(world, i1, 1, k1, this.brickWallBlock, this.brickWallMeta);
                     this.placeTauredainTorch(world, i1, 2, k1);
                  }
               } else {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.brickBlock, this.brickMeta);
                  if (k1 < 0 && i1 == 0) {
                     this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.brick4, 4);
                  }

                  if (i2 <= 9 && k2 <= 9 && (i2 == 9 || k2 == 9)) {
                     this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.brick4, 4);
                  }
               }
            }
         }

         for(i1 = -2; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, 0, 4, i1, LOTRMod.brick4, 4);
         }

         for(i1 = -4; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, 0, LOTRMod.brick4, 4);
         }

         int[] var19 = new int[]{-5, 5};
         k1 = var19.length;

         for(i2 = 0; i2 < k1; ++i2) {
            k2 = var19[i2];
            int[] var20 = new int[]{-5, 5};
            k1 = var20.length;

            for(int var13 = 0; var13 < k1; ++var13) {
               int k1 = var20[var13];

               for(int i2 = k2 - 1; i2 <= k2 + 1; ++i2) {
                  for(int k2 = k1 - 1; k2 <= k1 + 1; ++k2) {
                     int i3 = Math.abs(i2 - k2);
                     int k3 = Math.abs(k2 - k1);
                     if (i3 == 1 && k3 == 1) {
                        this.setBlockAndMetadata(world, i2, 5, k2, this.brickSlabBlock, this.brickSlabMeta);
                     } else if (i3 != 1 && k3 != 1) {
                        this.setBlockAndMetadata(world, i2, 5, k2, LOTRMod.hearth, 0);
                        this.setBlockAndMetadata(world, i2, 6, k2, Blocks.field_150480_ab, 0);
                     } else {
                        this.setBlockAndMetadata(world, i2, 5, k2, this.brickBlock, this.brickMeta);
                     }
                  }
               }
            }
         }

         var19 = new int[]{-3, 3};
         k1 = var19.length;

         for(i2 = 0; i2 < k1; ++i2) {
            k2 = var19[i2];
            this.setBlockAndMetadata(world, k2, 5, -6, this.brickWallBlock, this.brickWallMeta);

            for(j1 = 5; j1 <= 7; ++j1) {
               for(k1 = -5; k1 <= -3; ++k1) {
                  this.setBlockAndMetadata(world, k2, j1, k1, this.brickBlock, this.brickMeta);
               }

               this.setBlockAndMetadata(world, k2, j1, -1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k2, j1, 1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k2, j1, 3, this.brickBlock, this.brickMeta);
            }

            this.setBlockAndMetadata(world, k2, 5, 4, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, k2, 7, 0, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, k2, 5, -2, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, k2, 7, -2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, k2, 5, 2, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, k2, 7, 2, this.brickStairBlock, 6);

            for(j1 = -4; j1 <= 4; ++j1) {
               if (j1 != 0 && Math.abs(j1) != 3) {
                  this.setBlockAndMetadata(world, k2, 8, j1, this.brickSlabBlock, this.brickSlabMeta);
               } else {
                  this.setBlockAndMetadata(world, k2, 8, j1, this.brickBlock, this.brickMeta);
               }
            }
         }

         var19 = new int[]{-4, 4};
         k1 = var19.length;

         for(i2 = 0; i2 < k1; ++i2) {
            k2 = var19[i2];

            for(j1 = 5; j1 <= 7; ++j1) {
               this.setBlockAndMetadata(world, k2, j1, -4, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k2, j1, -2, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k2, j1, 2, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k2, j1, 4, this.brickBlock, this.brickMeta);
            }

            this.setBlockAndMetadata(world, k2, 5, -3, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, k2, 5, 3, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, k2, 7, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, k2, 7, 1, this.brickSlabBlock, this.brickSlabMeta | 8);

            for(j1 = -4; j1 <= 4; ++j1) {
               if (Math.abs(j1) == 4) {
                  this.setBlockAndMetadata(world, k2, 8, j1, this.brickBlock, this.brickMeta);
               } else {
                  this.setBlockAndMetadata(world, k2, 8, j1, this.brickSlabBlock, this.brickSlabMeta);
               }
            }
         }

         var19 = new int[]{-2, 2};
         k1 = var19.length;

         for(i2 = 0; i2 < k1; ++i2) {
            k2 = var19[i2];
            this.setBlockAndMetadata(world, k2, 5, -6, this.brickWallBlock, this.brickWallMeta);
            this.placeTauredainTorch(world, k2, 6, -6);
            this.setBlockAndMetadata(world, k2, 5, -5, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, k2, 6, -5, this.brickSlabBlock, this.brickSlabMeta);
            this.setBlockAndMetadata(world, k2, 8, -4, this.brickSlabBlock, this.brickSlabMeta);
            this.setBlockAndMetadata(world, k2, 8, -3, this.brickSlabBlock, this.brickSlabMeta);
            this.placeArmorStand(world, k2, 5, 2, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetTauredain), new ItemStack(LOTRMod.bodyTauredain), new ItemStack(LOTRMod.legsTauredain), new ItemStack(LOTRMod.bootsTauredain)});
         }

         for(i1 = 5; i1 <= 7; ++i1) {
            this.setBlockAndMetadata(world, -2, i1, 4, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 2, i1, 4, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -1, i1, 3, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 1, i1, 3, this.brickBlock, this.brickMeta);
         }

         this.setBlockAndMetadata(world, 0, 7, 3, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, -2, 5, 3, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, -2, 7, 3, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 2, 5, 3, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, 2, 7, 3, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, -1, 7, 4, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, 1, 7, 4, this.brickSlabBlock, this.brickSlabMeta | 8);

         for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 8, 3, this.brickSlabBlock, this.brickSlabMeta);
            this.setBlockAndMetadata(world, i1, 8, 4, this.brickSlabBlock, this.brickSlabMeta);
         }

         this.setBlockAndMetadata(world, 0, 8, 3, this.brickBlock, this.brickMeta);

         for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, -7, 5, i1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 7, 5, i1, this.brickWallBlock, this.brickWallMeta);
         }

         for(i1 = -6; i1 <= -5; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, -3, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i1, 5, 3, this.brickWallBlock, this.brickWallMeta);
         }

         for(i1 = 5; i1 <= 6; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, -3, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i1, 5, 3, this.brickWallBlock, this.brickWallMeta);
         }

         for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, 7, this.brickWallBlock, this.brickWallMeta);
         }

         for(i1 = 5; i1 <= 6; ++i1) {
            this.setBlockAndMetadata(world, -3, 5, i1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 3, 5, i1, this.brickWallBlock, this.brickWallMeta);
         }

         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               i2 = Math.abs(i1);
               k2 = Math.abs(k1);

               for(j1 = 8; j1 <= 9; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
               }

               if (i2 == 2 && k2 == 2) {
                  this.setBlockAndMetadata(world, i1, 10, k1, this.brickSlabBlock, this.brickSlabMeta);
               } else {
                  this.setBlockAndMetadata(world, i1, 10, k1, this.brickBlock, this.brickMeta);
               }

               if (i2 == 1 && k2 == 1) {
                  for(j1 = 11; j1 <= 12; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.brickWallBlock, this.brickWallMeta);
                  }
               }

               if (i2 == 0 && k2 == 0) {
                  this.setBlockAndMetadata(world, i1, 10, k1, LOTRMod.hearth, 0);
                  this.setBlockAndMetadata(world, i1, 11, k1, Blocks.field_150480_ab, 0);
               }

               if (i2 <= 1 && k2 <= 1) {
                  this.setBlockAndMetadata(world, i1, 13, k1, LOTRMod.brick4, 3);
                  if (k1 == -1) {
                     this.setBlockAndMetadata(world, i1, 14, k1, LOTRMod.stairsTauredainBrickGold, 2);
                  } else if (k1 == 1) {
                     this.setBlockAndMetadata(world, i1, 14, k1, LOTRMod.stairsTauredainBrickGold, 3);
                  } else if (i1 == -1) {
                     this.setBlockAndMetadata(world, i1, 14, k1, LOTRMod.stairsTauredainBrickGold, 1);
                  } else if (i1 == 1) {
                     this.setBlockAndMetadata(world, i1, 14, k1, LOTRMod.stairsTauredainBrickGold, 0);
                  } else {
                     this.setBlockAndMetadata(world, i1, 14, k1, LOTRMod.brick4, 3);
                     this.setBlockAndMetadata(world, i1, 15, k1, LOTRMod.wall4, 3);
                     this.placeTauredainTorch(world, i1, 16, k1);
                  }
               }
            }
         }

         this.setBlockAndMetadata(world, -1, 8, -3, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 0, 8, -3, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, 0, 9, -3, this.brickSlabBlock, this.brickSlabMeta);
         this.setBlockAndMetadata(world, 1, 8, -3, this.brickBlock, this.brickMeta);
         this.placeWallBanner(world, -1, 8, -3, LOTRItemBanner.BannerType.TAUREDAIN, 2);
         this.placeWallBanner(world, 1, 8, -3, LOTRItemBanner.BannerType.TAUREDAIN, 2);
         this.placeWallBanner(world, -3, 7, -3, LOTRItemBanner.BannerType.TAUREDAIN, 1);
         this.placeWallBanner(world, 3, 7, -3, LOTRItemBanner.BannerType.TAUREDAIN, 3);
         this.setBlockAndMetadata(world, -2, 6, -1, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, -2, 6, 1, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, 2, 6, -1, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, 2, 6, 1, Blocks.field_150478_aa, 1);
         this.spawnItemFrame(world, -1, 6, 3, 2, new ItemStack(LOTRMod.tauredainDart));
         this.spawnItemFrame(world, 1, 6, 3, 2, new ItemStack(LOTRMod.daggerTauredain));
         LOTREntityTauredainChieftain chieftain = new LOTREntityTauredainChieftain(world);
         this.spawnNPCAndSetHome(chieftain, world, 0, 5, 0, 8);
         return true;
      }
   }
}
