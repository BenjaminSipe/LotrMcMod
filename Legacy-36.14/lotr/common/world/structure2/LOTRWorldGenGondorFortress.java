package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGondorFortress extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorFortress(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.bedBlock = Blocks.field_150324_C;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 12);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int j1;
      int d;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -14; i2 <= 14; ++i2) {
            for(j1 = -14; j1 <= 14; ++j1) {
               d = this.getTopBlock(world, i2, j1) - 1;
               if (!this.isSurface(world, i2, d, j1)) {
                  return false;
               }

               if (d < i1) {
                  i1 = d;
               }

               if (d > k1) {
                  k1 = d;
               }

               if (k1 - i1 > 8) {
                  return false;
               }
            }
         }
      }

      int i2;
      for(i1 = -11; i1 <= 11; ++i1) {
         for(k1 = -11; k1 <= 11; ++k1) {
            i2 = Math.abs(i1);
            j1 = Math.abs(k1);
            if ((i2 < 9 || i2 > 11 || j1 > 5) && (j1 < 9 || j1 > 11 || i2 > 5)) {
               for(d = 0; (d == 0 || !this.isOpaque(world, i1, d, k1)) && this.getY(d) >= 0; --d) {
                  this.setBlockAndMetadata(world, i1, d, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, d - 1, k1);
               }

               for(d = 1; d <= 9; ++d) {
                  this.setAir(world, i1, d, k1);
               }
            } else {
               boolean pillar = false;
               if (i2 == 11) {
                  pillar = j1 == 2 || j1 == 5;
               } else if (j1 == 11) {
                  pillar = i2 == 2 || i2 == 5;
               }

               for(i2 = 5; (i2 >= 0 || !this.isOpaque(world, i1, i2, k1)) && this.getY(i2) >= 0; --i2) {
                  if (pillar && i2 >= 1) {
                     this.setBlockAndMetadata(world, i1, i2, k1, this.pillarBlock, this.pillarMeta);
                  } else {
                     this.setBlockAndMetadata(world, i1, i2, k1, this.brickBlock, this.brickMeta);
                  }

                  this.setGrassToDirt(world, i1, i2 - 1, k1);
               }

               this.setBlockAndMetadata(world, i1, 6, k1, this.brickBlock, this.brickMeta);

               for(i2 = 7; i2 <= 9; ++i2) {
                  this.setAir(world, i1, i2, k1);
               }

               if (i2 == 9 || i2 == 11 || j1 == 9 || j1 == 11) {
                  this.setBlockAndMetadata(world, i1, 7, k1, this.brick2WallBlock, this.brick2WallMeta);
                  if (i2 == 5 || j1 == 5) {
                     this.setBlockAndMetadata(world, i1, 8, k1, Blocks.field_150478_aa, 5);
                  }
               }
            }
         }
      }

      int[] var23 = new int[]{-10, 10};
      k1 = var23.length;

      int var13;
      int k2;
      int i2;
      int k2;
      int[] var27;
      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var23[i2];
         var27 = new int[]{-10, 10};
         i2 = var27.length;

         for(var13 = 0; var13 < i2; ++var13) {
            k2 = var27[var13];

            for(i2 = j1 - 4; i2 <= j1 + 4; ++i2) {
               for(k2 = k2 - 4; k2 <= k2 + 4; ++k2) {
                  int i3 = Math.abs(i2 - j1);
                  int k3 = Math.abs(k2 - k2);
                  int i4 = Math.abs(i2);
                  int k4 = Math.abs(k2);
                  if ((i3 != 4 || k3 < 3) && (k3 != 4 || i3 < 3)) {
                     if ((i3 != 4 || k3 > 2) && (k3 != 4 || i3 > 2) && (i3 != 3 || k3 != 3)) {
                        int j1;
                        for(j1 = 0; (j1 == 0 || !this.isOpaque(world, i2, j1, k2)) && this.getY(j1) >= 0; --j1) {
                           this.setBlockAndMetadata(world, i2, j1, k2, this.brickBlock, this.brickMeta);
                           this.setGrassToDirt(world, i2, j1 - 1, k2);
                        }

                        for(j1 = 1; j1 <= 9; ++j1) {
                           this.setAir(world, i2, j1, k2);
                        }

                        this.setBlockAndMetadata(world, i2, 6, k2, this.plankBlock, this.plankMeta);
                        if (i4 == 9 && k4 == 9 || i4 == 11 && k4 == 11) {
                           this.setBlockAndMetadata(world, i2, 5, k2, LOTRMod.chandelier, 2);
                        }
                     } else {
                        boolean pillar = false;
                        if (i3 == 4) {
                           pillar = k3 == 2;
                        } else if (k3 == 4) {
                           pillar = i3 == 2;
                        }

                        int j1;
                        for(j1 = 5; (j1 >= 0 || !this.isOpaque(world, i2, j1, k2)) && this.getY(j1) >= 0; --j1) {
                           if (pillar && j1 >= 1) {
                              this.setBlockAndMetadata(world, i2, j1, k2, this.pillarBlock, this.pillarMeta);
                           } else {
                              this.setBlockAndMetadata(world, i2, j1, k2, this.brickBlock, this.brickMeta);
                           }

                           this.setGrassToDirt(world, i2, j1 - 1, k2);
                        }

                        this.setBlockAndMetadata(world, i2, 6, k2, this.brickBlock, this.brickMeta);

                        for(j1 = 7; j1 <= 9; ++j1) {
                           this.setAir(world, i2, j1, k2);
                        }

                        if (i3 > 1 && k3 > 1) {
                           this.setBlockAndMetadata(world, i2, 7, k2, this.brick2Block, this.brick2Meta);
                           this.setBlockAndMetadata(world, i2, 8, k2, this.brick2SlabBlock, this.brick2SlabMeta);
                        } else {
                           this.setBlockAndMetadata(world, i2, 7, k2, this.brick2WallBlock, this.brick2WallMeta);
                           if (i4 == 10 || k4 == 10) {
                              if (i4 <= 10 && k4 <= 10) {
                                 this.setAir(world, i2, 7, k2);
                              } else {
                                 this.setBlockAndMetadata(world, i2, 8, k2, Blocks.field_150478_aa, 5);
                              }
                           }
                        }
                     }
                  }
               }
            }

            for(i2 = 1; i2 <= 8; ++i2) {
               this.setBlockAndMetadata(world, j1, i2, k2, this.woodBeamBlock, this.woodBeamMeta);
            }

            this.setBlockAndMetadata(world, j1, 9, k2, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, j1, 8, k2 - 1, Blocks.field_150478_aa, 4);
            this.setBlockAndMetadata(world, j1, 8, k2 + 1, Blocks.field_150478_aa, 3);
            this.setBlockAndMetadata(world, j1 - 1, 8, k2, Blocks.field_150478_aa, 1);
            this.setBlockAndMetadata(world, j1 + 1, 8, k2, Blocks.field_150478_aa, 2);
            if (j1 < 0) {
               for(i2 = 1; i2 <= 5; ++i2) {
                  this.setBlockAndMetadata(world, j1 + 1, i2, k2, Blocks.field_150468_ap, 4);
               }

               this.setBlockAndMetadata(world, j1 + 1, 6, k2, this.trapdoorBlock, 11);
            }

            if (j1 > 0) {
               for(i2 = 1; i2 <= 5; ++i2) {
                  this.setBlockAndMetadata(world, j1 - 1, i2, k2, Blocks.field_150468_ap, 5);
               }

               this.setBlockAndMetadata(world, j1 - 1, 6, k2, this.trapdoorBlock, 10);
            }

            if (k2 < 0) {
               for(i2 = 1; i2 <= 5; ++i2) {
                  this.setBlockAndMetadata(world, j1, i2, k2 + 1, Blocks.field_150468_ap, 3);
               }

               this.setBlockAndMetadata(world, j1, 6, k2 + 1, this.trapdoorBlock, 8);
            }

            if (k2 > 0) {
               for(i2 = 1; i2 <= 5; ++i2) {
                  this.setBlockAndMetadata(world, j1, i2, k2 - 1, Blocks.field_150468_ap, 2);
               }

               this.setBlockAndMetadata(world, j1, 6, k2 - 1, this.trapdoorBlock, 9);
            }
         }
      }

      var23 = new int[]{-11, 11};
      k1 = var23.length;

      int[] var26;
      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var23[i2];
         d = j1 + Integer.signum(j1) * -1;
         var26 = new int[]{-4, 3};
         var13 = var26.length;

         for(k2 = 0; k2 < var13; ++k2) {
            i2 = var26[k2];
            this.setBlockAndMetadata(world, j1, 2, i2, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, j1, 2, i2 + 1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, j1, 4, i2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, j1, 4, i2 + 1, this.brickStairBlock, 6);

            for(k2 = i2; k2 <= i2 + 1; ++k2) {
               this.setAir(world, j1, 3, k2);
               this.setBlockAndMetadata(world, d, 3, k2, LOTRMod.brick, 5);
            }
         }

         this.setBlockAndMetadata(world, j1, 2, -1, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, j1, 2, 0, this.brickSlabBlock, this.brickSlabMeta);
         this.setBlockAndMetadata(world, j1, 2, 1, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, j1, 4, -1, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, j1, 4, 0, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, j1, 4, 1, this.brickStairBlock, 6);

         for(i2 = -1; i2 <= 1; ++i2) {
            this.setAir(world, j1, 3, i2);
            this.setBlockAndMetadata(world, d, 3, i2, LOTRMod.brick, 5);
         }
      }

      var23 = new int[]{-11, 11};
      k1 = var23.length;

      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var23[i2];
         d = j1 + Integer.signum(j1) * -1;
         var26 = new int[]{-4, 3};
         var13 = var26.length;

         for(k2 = 0; k2 < var13; ++k2) {
            i2 = var26[k2];
            this.setBlockAndMetadata(world, i2, 2, j1, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, i2 + 1, 2, j1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, i2, 4, j1, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, i2 + 1, 4, j1, this.brickStairBlock, 5);

            for(k2 = i2; k2 <= i2 + 1; ++k2) {
               this.setAir(world, k2, 3, j1);
               this.setBlockAndMetadata(world, k2, 3, d, LOTRMod.brick, 5);
            }
         }

         if (j1 > 0) {
            this.setBlockAndMetadata(world, -1, 2, j1, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 0, 2, j1, this.brickSlabBlock, this.brickSlabMeta);
            this.setBlockAndMetadata(world, 1, 2, j1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -1, 4, j1, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 4, j1, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, 1, 4, j1, this.brickStairBlock, 5);

            for(i2 = -1; i2 <= 1; ++i2) {
               this.setAir(world, i2, 3, j1);
               this.setBlockAndMetadata(world, i2, 3, d, LOTRMod.brick, 5);
            }
         }
      }

      var23 = new int[]{-14, 14};
      k1 = var23.length;

      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var23[i2];
         d = j1 + Integer.signum(j1) * -1;
         var26 = new int[]{-10, 10};
         var13 = var26.length;

         for(k2 = 0; k2 < var13; ++k2) {
            i2 = var26[k2];
            this.setBlockAndMetadata(world, i2 - 1, 3, j1, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, i2, 3, j1, this.brick2WallBlock, this.brick2WallMeta);
            this.setBlockAndMetadata(world, i2 + 1, 3, j1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, i2 - 1, 4, j1, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, i2, 4, j1, this.brick2WallBlock, this.brick2WallMeta);
            this.setBlockAndMetadata(world, i2 + 1, 4, j1, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, i2 - 1, 1, d, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, i2, 1, d, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i2 + 1, 1, d, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, i2, 2, d, this.brickSlabBlock, this.brickSlabMeta);
         }
      }

      var23 = new int[]{-14, 14};
      k1 = var23.length;

      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var23[i2];
         d = j1 + Integer.signum(j1) * -1;
         var26 = new int[]{-10, 10};
         var13 = var26.length;

         for(k2 = 0; k2 < var13; ++k2) {
            i2 = var26[k2];
            this.setBlockAndMetadata(world, j1, 3, i2 - 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, j1, 3, i2, this.brick2WallBlock, this.brick2WallMeta);
            this.setBlockAndMetadata(world, j1, 3, i2 + 1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, j1, 4, i2 - 1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, j1, 4, i2, this.brick2WallBlock, this.brick2WallMeta);
            this.setBlockAndMetadata(world, j1, 4, i2 + 1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, d, 1, i2 - 1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, d, 1, i2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, d, 1, i2 + 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, d, 2, i2, this.brickSlabBlock, this.brickSlabMeta);
         }
      }

      var23 = new int[]{-10, 10};
      k1 = var23.length;

      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var23[i2];
         var27 = new int[]{j1 - 2, j1 + 2};
         i2 = var27.length;

         for(var13 = 0; var13 < i2; ++var13) {
            k2 = var27[var13];
            this.placeArmorStand(world, k2, 1, -7, 0, (ItemStack[])null);
            this.placeArmorStand(world, k2, 1, 7, 2, (ItemStack[])null);
         }

         this.placeChest(world, random, j1, 1, -6, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
         this.setAir(world, j1, 2, -6);
         this.spawnItemFrame(world, j1, 3, -6, 2, this.getGondorFramedItem(random));
         this.placeChest(world, random, j1, 1, 6, LOTRMod.chestLebethron, 3, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
         this.setAir(world, j1, 2, 6);
         this.spawnItemFrame(world, j1, 3, 6, 0, this.getGondorFramedItem(random));
      }

      var23 = new int[]{-10, 10};
      k1 = var23.length;

      for(i2 = 0; i2 < k1; ++i2) {
         j1 = var23[i2];
         var27 = new int[]{j1 - 2, j1 + 2};
         i2 = var27.length;

         for(var13 = 0; var13 < i2; ++var13) {
            k2 = var27[var13];
            this.placeArmorStand(world, -7, 1, k2, 1, (ItemStack[])null);
            this.placeArmorStand(world, 7, 1, k2, 3, (ItemStack[])null);
         }

         this.placeChest(world, random, -6, 1, j1, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
         this.setAir(world, -6, 2, j1);
         this.spawnItemFrame(world, -6, 3, j1, 3, this.getGondorFramedItem(random));
         this.placeChest(world, random, 6, 1, j1, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES, 1);
         this.setAir(world, 6, 2, j1);
         this.spawnItemFrame(world, 6, 3, j1, 1, this.getGondorFramedItem(random));
      }

      for(i1 = 1; i1 <= 4; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, k1, i1, -10, this.gateBlock, 2);
            this.setAir(world, k1, i1, -9);
            this.setAir(world, k1, i1, -11);
         }

         this.setBlockAndMetadata(world, -2, i1, -9, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, 2, i1, -9, this.pillarBlock, this.pillarMeta);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         int k1 = -12;

         for(i2 = 0; (i2 <= 0 || !this.isOpaque(world, i1, i2, k1)) && this.getY(i2) >= 0; --i2) {
            this.setBlockAndMetadata(world, i1, i2, k1, this.brickBlock, this.brickMeta);
            this.setGrassToDirt(world, i1, i2 - 1, k1);
         }
      }

      this.placeWallBanner(world, -2, 4, -11, this.bannerType2, 2);
      this.placeWallBanner(world, 2, 4, -11, this.bannerType, 2);

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, this.brick2Block, this.brick2Meta);
         }
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, 0, this.brick2Block, this.brick2Meta);
      }

      for(i1 = -12; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, 0, 0, i1, this.brick2Block, this.brick2Meta);
      }

      this.setBlockAndMetadata(world, 0, 0, 0, LOTRMod.brick4, 6);

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -1, i1, -1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, 1, i1, -1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, -1, i1, 1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, 1, i1, 1, this.brickWallBlock, this.brickWallMeta);
      }

      this.setBlockAndMetadata(world, -1, 5, -1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 5, -1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 1, 5, -1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -1, 5, 0, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 0, 5, 0, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, 5, 0, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, -1, 5, 1, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 5, 1, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 1, 5, 1, this.brickStairBlock, 3);

      for(i1 = 6; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 0, this.pillarBlock, this.pillarMeta);
      }

      this.setBlockAndMetadata(world, 0, 10, 0, LOTRMod.brick, 5);
      this.setBlockAndMetadata(world, 0, 11, 0, LOTRMod.beacon, 0);
      this.placeWallBanner(world, 0, 9, 0, this.bannerType, 0);
      this.placeWallBanner(world, 0, 9, 0, this.bannerType2, 1);
      this.placeWallBanner(world, 0, 9, 0, this.bannerType, 2);
      this.placeWallBanner(world, 0, 9, 0, this.bannerType2, 3);
      this.setBlockAndMetadata(world, 0, 4, 0, LOTRMod.chandelier, 2);
      this.setBlockAndMetadata(world, -3, 3, -8, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -3, 4, -8, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 3, 3, -8, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 3, 4, -8, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, -8, 3, -3, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -8, 4, -3, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 8, 3, -3, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 8, 4, -3, Blocks.field_150478_aa, 5);

      for(i1 = -7; i1 <= 7; ++i1) {
         k1 = Math.abs(i1);
         if (k1 >= 2) {
            for(i2 = -7; i2 <= -2; ++i2) {
               j1 = i2 - -9;
               d = Math.abs(k1 - j1);
               if (d == 0 && (k1 == 2 || k1 == 7)) {
                  d = 2;
               }

               if (d <= 2) {
                  this.setBlockAndMetadata(world, i1, 0, i2, Blocks.field_150349_c, 0);
                  if (d == 0) {
                     this.setBlockAndMetadata(world, i1, 1, i2, Blocks.field_150398_cm, 4);
                     this.setBlockAndMetadata(world, i1, 2, i2, Blocks.field_150398_cm, 8);
                  } else if (d == 1) {
                     this.setBlockAndMetadata(world, i1, 1, i2, Blocks.field_150328_O, 6);
                  } else if (d == 2) {
                     this.setBlockAndMetadata(world, i1, 1, i2, Blocks.field_150328_O, 4);
                  }
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -7, 0, 1, this.brick2Block, this.brick2Meta);

      for(i1 = 0; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -7, 1 + i1, 2 + i1, this.brickStairBlock, 2);

         for(k1 = 1; k1 < 1 + i1; ++k1) {
            this.setBlockAndMetadata(world, -7, k1, 2 + i1, this.brickBlock, this.brickMeta);
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -7, i1, 5, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -7, i1, 6, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -8, i1, 4, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -8, i1, 5, this.brickBlock, this.brickMeta);
      }

      for(i1 = 0; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -8, 4 + i1, 3 - i1, this.brickStairBlock, 3);

         for(k1 = 1; k1 < 4 + i1; ++k1) {
            this.setBlockAndMetadata(world, -8, k1, 3 - i1, this.brickBlock, this.brickMeta);
         }
      }

      for(i1 = -1; i1 <= 0; ++i1) {
         this.setBlockAndMetadata(world, -8, 5, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, -8, 6, i1, this.brickBlock, this.brickMeta);
      }

      this.setAir(world, -9, 7, 0);
      this.setAir(world, -9, 7, 1);
      this.setBlockAndMetadata(world, -8, 7, -1, this.brick2WallBlock, this.brick2WallMeta);

      for(i1 = 6; i1 <= 8; ++i1) {
         for(k1 = -1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, this.brick2Block, this.brick2Meta);
            if (i1 >= 7 && k1 >= 0 && k1 <= 2) {
               this.setBlockAndMetadata(world, i1, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            } else {
               this.setBlockAndMetadata(world, i1, 4, k1, this.plankSlabBlock, this.plankSlabMeta);
            }
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, 6, i1, -1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 6, i1, 3, this.fenceBlock, this.fenceMeta);
      }

      for(i1 = 7; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, -1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 3, 3, this.fenceBlock, this.fenceMeta);
      }

      for(i1 = 0; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, 6, 3, i1, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, 8, 1, -1, LOTRMod.alloyForge, 5);
      this.setBlockAndMetadata(world, 8, 2, -1, Blocks.field_150460_al, 5);
      this.placeArmorStand(world, 8, 1, 0, 1, this.strFief.getFiefdomArmor());
      this.setBlockAndMetadata(world, 8, 1, 1, this.tableBlock, 0);
      this.placeChest(world, random, 8, 1, 2, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
      this.setBlockAndMetadata(world, 8, 1, 3, Blocks.field_150462_ai, 0);
      this.spawnItemFrame(world, 9, 2, 1, 3, this.getGondorFramedItem(random));
      this.spawnItemFrame(world, 9, 2, 2, 3, this.getGondorFramedItem(random));
      this.setBlockAndMetadata(world, 6, 1, 1, Blocks.field_150467_bQ, 0);
      this.setBlockAndMetadata(world, 8, 3, 1, Blocks.field_150478_aa, 1);

      for(i1 = -5; i1 <= 5; ++i1) {
         for(k1 = 4; k1 <= 8; ++k1) {
            i2 = Math.abs(i1);
            if (i2 != 5 || k1 != 4) {
               if (i2 <= 4 && k1 >= 5) {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, i1, 4, k1, this.brickBlock, this.brickMeta);
               } else {
                  if (i2 != 1 && i2 != 4 && k1 != 5) {
                     this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);

                     for(j1 = 2; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.plankBlock, this.plankMeta);
                     }
                  } else {
                     for(j1 = 1; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                     }
                  }

                  this.setBlockAndMetadata(world, i1, 4, k1, this.brickSlabBlock, this.brickSlabMeta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 0, 4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 1, 4, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, 4, this.doorBlock, 8);

      for(i1 = -4; i1 <= 4; ++i1) {
         if (IntMath.mod(i1, 2) == 0) {
            this.setBlockAndMetadata(world, i1, 1, 7, this.bedBlock, 0);
            this.setBlockAndMetadata(world, i1, 1, 8, this.bedBlock, 8);
         } else {
            this.placeChest(world, random, i1, 1, 8, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
         }
      }

      this.placeWallBanner(world, -2, 3, 9, this.bannerType, 2);
      this.placeWallBanner(world, 2, 3, 9, this.bannerType, 2);
      this.setBlockAndMetadata(world, -4, 1, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -3, 1, 5, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, -4, 2, 5, 3, LOTRFoods.GONDOR_DRINK);
      this.placeMug(world, random, -3, 2, 5, 2, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, 3, 1, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 4, 1, 5, this.plankBlock, this.plankMeta);
      this.placePlateWithCertainty(world, random, 3, 2, 5, this.plateBlock, LOTRFoods.GONDOR);
      this.placePlateWithCertainty(world, random, 4, 2, 5, this.plateBlock, LOTRFoods.GONDOR);
      this.setBlockAndMetadata(world, -3, 3, 6, LOTRMod.chandelier, 2);
      this.setBlockAndMetadata(world, 3, 3, 6, LOTRMod.chandelier, 2);
      this.setBlockAndMetadata(world, -5, 1, 2, LOTRMod.commandTable, 0);
      LOTREntityGondorMan captain = this.strFief.createCaptain(world);
      captain.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 12);
      Class[] soldierClasses = this.strFief.getSoldierClasses();
      i2 = 4 + random.nextInt(4);

      for(j1 = 0; j1 < i2; ++j1) {
         Class entityClass = soldierClasses[0];
         if (random.nextInt(3) == 0) {
            entityClass = soldierClasses[1];
         }

         LOTREntityGondorMan soldier = (LOTREntityGondorMan)LOTREntities.createEntityByClass(entityClass, world);
         soldier.spawnRidingHorse = false;
         this.spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(soldierClasses[0], soldierClasses[1]);
      respawner.setCheckRanges(20, -8, 12, 12);
      respawner.setSpawnRanges(10, 0, 8, 16);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      return true;
   }
}
