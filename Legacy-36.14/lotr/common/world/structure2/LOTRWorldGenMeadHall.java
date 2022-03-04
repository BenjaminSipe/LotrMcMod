package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityRohanMeadhost;
import lotr.common.entity.npc.LOTREntityRohanShieldmaiden;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenMeadHall extends LOTRWorldGenRohanStructure {
   private String[] meadHallName;
   private String[] meadNameSign;
   private String meadNameNPC;

   public LOTRWorldGenMeadHall(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.meadHallName = LOTRNames.getRohanMeadHallName(random);
      this.meadNameSign = new String[]{"", this.meadHallName[0], this.meadHallName[1], ""};
      this.meadNameNPC = this.meadHallName[0] + " " + this.meadHallName[1];
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int i1;
      int men;
      int l;
      int i1;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         men = 0;

         for(l = -8; l <= 8; ++l) {
            for(i1 = 0; i1 <= 28; ++i1) {
               j1 = this.getTopBlock(world, l, i1) - 1;
               if (!this.isSurface(world, l, j1, i1)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > men) {
                  men = j1;
               }

               if (men - i1 > 8) {
                  return false;
               }
            }
         }
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         for(men = 0; men <= 28; ++men) {
            for(l = 1; l <= 11; ++l) {
               this.setAir(world, i1, l, men);
            }

            boolean corner = Math.abs(i1) == 8 && (men == 0 || men == 28);
            boolean stairSide = Math.abs(i1) == 3 && men == 0;
            if (!corner && !stairSide) {
               for(j1 = 1; (j1 >= 1 || !this.isOpaque(world, i1, j1, men)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, men, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, men);
               }

               if (Math.abs(i1) <= 4 && men >= 4 && men <= 21 && random.nextInt(4) == 0) {
                  this.setBlockAndMetadata(world, i1, 2, men, LOTRMod.thatchFloor, 0);
               }
            } else {
               for(j1 = 1; (j1 >= 1 || !this.isOpaque(world, i1, j1, men)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, men, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, men);
               }

               if (corner) {
                  this.setBlockAndMetadata(world, i1, 2, men, this.rockSlabBlock, this.rockSlabMeta);
               }
            }
         }
      }

      for(i1 = -7; i1 <= 7; ++i1) {
         men = Math.abs(i1);
         if (men <= 2) {
            this.setBlockAndMetadata(world, i1, 1, 0, this.brickStairBlock, 2);
         }

         if (men == 3) {
            this.setBlockAndMetadata(world, i1, 2, 0, this.rockWallBlock, this.rockWallMeta);
         }

         if (men >= 4 && men <= 7) {
            this.setBlockAndMetadata(world, i1, 2, 0, this.fenceBlock, this.fenceMeta);
         }
      }

      for(i1 = -7; i1 <= 7; ++i1) {
         for(men = 2; men <= 26; ++men) {
            l = Math.abs(i1);
            if (l == 5 && (men == 2 || men == 26)) {
               for(i1 = 2; i1 <= 4; ++i1) {
                  this.setBlockAndMetadata(world, i1, i1, men, this.woodBeamRohanBlock, this.woodBeamRohanMeta);
               }
            } else {
               if (l == 5 && men >= 3 && men <= 25) {
                  this.setBlockAndMetadata(world, i1, 2, men, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, i1, 3, men, this.plank2Block, this.plank2Meta);
                  this.setBlockAndMetadata(world, i1, 4, men, this.plankBlock, this.plankMeta);
               }

               if (l <= 4 && men == 3) {
                  for(i1 = 2; i1 <= 4; ++i1) {
                     this.setBlockAndMetadata(world, i1, i1, men, this.plankBlock, this.plankMeta);
                  }

                  this.setBlockAndMetadata(world, i1, 5, men, this.woodBeamBlock, this.woodBeamMeta | 4);
               }

               if (l <= 4 && men == 25) {
                  this.setBlockAndMetadata(world, i1, 2, men, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, i1, 3, men, this.plank2Block, this.plank2Meta);
                  this.setBlockAndMetadata(world, i1, 4, men, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, i1, 5, men, this.woodBeamBlock, this.woodBeamMeta | 4);
               }
            }

            if (men >= 3 && men <= 25) {
               if (l == 6) {
                  if (men % 6 == 2) {
                     for(i1 = 2; i1 <= 4; ++i1) {
                        this.setBlockAndMetadata(world, i1, i1, men, this.woodBeamBlock, this.woodBeamMeta);
                     }
                  } else {
                     if (men != 3 && men != 25) {
                        this.setBlockAndMetadata(world, i1, 2, men, this.rockSlabBlock, this.rockSlabMeta | 8);
                     } else {
                        this.setBlockAndMetadata(world, i1, 2, men, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                     }

                     if (men % 6 == 3 || men % 6 == 1) {
                        this.setBlockAndMetadata(world, i1, 4, men, this.plank2SlabBlock, this.plank2SlabMeta | 8);
                     }

                     if (random.nextInt(5) == 0) {
                        this.placeFlowerPot(world, i1, 3, men, this.getRandomFlower(world, random));
                     }
                  }
               }

               if (l == 5 && men % 6 == 5) {
                  this.setBlockAndMetadata(world, i1, 3, men, this.plank2StairBlock, i1 > 0 ? 0 : 1);
                  this.setBlockAndMetadata(world, i1, 4, men, this.fenceBlock, this.fenceMeta);
               }
            }
         }
      }

      int k1;
      for(i1 = 3; i1 <= 25; ++i1) {
         for(men = 0; men <= 5; ++men) {
            l = 1 + men;
            i1 = 7 - men / 2;
            Block block = this.roofSlabBlock;
            k1 = this.roofSlabMeta;
            if (i1 == 3 || i1 == 25) {
               block = this.plankSlabBlock;
               k1 = this.plankSlabMeta;
            }

            if (men % 2 == 0) {
               k1 |= 8;
            }

            this.setBlockAndMetadata(world, -l, i1, i1, block, k1);
            this.setBlockAndMetadata(world, l, i1, i1, block, k1);
         }
      }

      for(i1 = 2; i1 <= 26; ++i1) {
         this.setBlockAndMetadata(world, 0, 7, i1, this.logBlock, this.logMeta | 8);
         this.setBlockAndMetadata(world, 0, 8, i1, this.plank2SlabBlock, this.plank2SlabMeta);
         if (i1 % 12 == 2) {
            for(men = 0; men <= 6; ++men) {
               l = 1 + men;
               i1 = 8 - (men + 1) / 2;
               int[] var19 = new int[]{-l, l};
               k1 = var19.length;

               for(int var13 = 0; var13 < k1; ++var13) {
                  int i2 = var19[var13];
                  if (men % 2 == 0) {
                     this.setBlockAndMetadata(world, i2, i1, i1, this.plank2SlabBlock, this.plank2SlabMeta);
                     this.setBlockAndMetadata(world, i2, i1 - 1, i1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
                  } else {
                     this.setBlockAndMetadata(world, i2, i1, i1, this.plank2Block, this.plank2Meta);
                  }
               }
            }

            this.setBlockAndMetadata(world, 0, 8, i1, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 0, 9, i1, this.plank2SlabBlock, this.plank2SlabMeta);
            this.setBlockAndMetadata(world, -1, 9, i1, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 1, 9, i1, this.plank2StairBlock, 4);

            for(men = 2; men <= 4; ++men) {
               this.setBlockAndMetadata(world, -7, men, i1, this.fence2Block, this.fence2Meta);
               this.setBlockAndMetadata(world, 7, men, i1, this.fence2Block, this.fence2Meta);
            }

            if (i1 == 2 || i1 == 26) {
               this.setBlockAndMetadata(world, -6, 4, i1, this.fence2Block, this.fence2Meta);
               this.setBlockAndMetadata(world, 6, 4, i1, this.fence2Block, this.fence2Meta);
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         men = Math.abs(i1);
         if (men == 5) {
            this.setBlockAndMetadata(world, i1, 5, 3, this.plankBlock, this.plankMeta);
         }

         if (men >= 2 && men <= 3) {
            this.setBlockAndMetadata(world, i1, 6, 3, this.plankBlock, this.plankMeta);
         }

         if (men == 1) {
            this.setBlockAndMetadata(world, i1, 6, 3, this.plankStairBlock, i1 > 0 ? 5 : 4);
         }

         if (men == 1) {
            this.setBlockAndMetadata(world, i1, 7, 3, this.plankBlock, this.plankMeta);
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         men = Math.abs(i1);
         if (men == 5) {
            this.setBlockAndMetadata(world, i1, 5, 25, this.plankBlock, this.plankMeta);
         }

         if (men == 3) {
            this.setBlockAndMetadata(world, i1, 6, 25, this.plankBlock, this.plankMeta);
         }

         if (men == 2) {
            for(l = 2; l <= 6; ++l) {
               this.setBlockAndMetadata(world, i1, l, 25, this.woodBeamRohanBlock, this.woodBeamRohanMeta);
            }
         }

         if (men <= 1) {
            for(l = 2; l <= 8; ++l) {
               if (l != 5) {
                  this.setBlockAndMetadata(world, i1, l, 25, this.brickBlock, this.brickMeta);
               }
            }
         }

         if (men == 0) {
            for(l = 9; l <= 11; ++l) {
               this.setBlockAndMetadata(world, i1, l, 25, this.brickBlock, this.brickMeta);
            }

            this.setBlockAndMetadata(world, i1, 12, 25, this.rockSlabBlock, this.rockSlabMeta);
         }

         if (men == 1) {
            this.setBlockAndMetadata(world, i1, 9, 25, this.brickStairBlock, i1 > 0 ? 0 : 1);
         }

         if (men <= 2) {
            for(l = 23; l <= 24; ++l) {
               for(i1 = 2; i1 <= 6; ++i1) {
                  this.setBlockAndMetadata(world, i1, i1, l, this.brickBlock, this.brickMeta);
               }
            }
         }

         if (men <= 1) {
            this.setBlockAndMetadata(world, i1, 7, 24, this.brickBlock, this.brickMeta);
         }

         if (men == 1) {
            this.setBlockAndMetadata(world, i1, 8, 24, this.brickStairBlock, i1 > 0 ? 0 : 1);
         }

         if (men == 0) {
            this.setBlockAndMetadata(world, i1, 8, 24, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 9, 24, this.brickStairBlock, 2);
         }

         if (men >= 3 && men <= 4) {
            for(l = 2; l <= 5; ++l) {
               this.setBlockAndMetadata(world, i1, l, 24, this.brickBlock, this.brickMeta);
            }
         }

         if (men <= 1) {
            this.setBlockAndMetadata(world, i1, 2, 26, this.brickBlock, this.brickMeta);
         }

         if (men == 1) {
            this.setBlockAndMetadata(world, i1, 3, 26, this.brickStairBlock, i1 > 0 ? 0 : 1);
         }

         if (men == 0) {
            this.setBlockAndMetadata(world, i1, 3, 26, this.brickBlock, this.brickMeta);
         }
      }

      int[] var18 = new int[]{2, 26};
      men = var18.length;

      for(l = 0; l < men; ++l) {
         i1 = var18[l];

         for(j1 = -5; j1 <= 5; ++j1) {
            k1 = Math.abs(j1);
            if (k1 == 2 || k1 == 5) {
               this.setBlockAndMetadata(world, j1, 5, i1, this.woodBeamRohanBlock, this.woodBeamRohanMeta | 8);
            }

            if (k1 <= 1) {
               this.setBlockAndMetadata(world, j1, 5, i1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            }
         }
      }

      var18 = new int[]{-4, 3};
      men = var18.length;

      for(l = 0; l < men; ++l) {
         i1 = var18[l];
         this.setBlockAndMetadata(world, i1, 2, 2, this.plank2StairBlock, 4);
         this.setBlockAndMetadata(world, i1 + 1, 2, 2, this.plank2StairBlock, 5);

         for(j1 = i1; j1 <= i1 + 1; ++j1) {
            if (random.nextBoolean()) {
               this.placeFlowerPot(world, j1, 3, 2, this.getRandomFlower(world, random));
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 2, 3, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 3, 3, this.doorBlock, 8);
      this.setBlockAndMetadata(world, -1, 3, 2, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 3, 2, Blocks.field_150478_aa, 4);
      this.placeSign(world, 0, 4, 2, Blocks.field_150444_as, 2, this.meadNameSign);
      var18 = new int[]{-2, 2};
      men = var18.length;

      for(l = 0; l < men; ++l) {
         i1 = var18[l];

         for(j1 = 2; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, i1, j1, 3, this.woodBeamRohanGoldBlock, this.woodBeamRohanGoldMeta);
         }
      }

      var18 = new int[]{-3, 3};
      men = var18.length;

      for(l = 0; l < men; ++l) {
         i1 = var18[l];
         this.setBlockAndMetadata(world, i1, 3, 3, this.plankStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 4, 3, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, 0, 4, 4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 5, 4, this.woodBeamBlock, this.woodBeamMeta | 8);
      this.setBlockAndMetadata(world, 2, 5, 4, this.woodBeamBlock, this.woodBeamMeta | 8);

      for(i1 = 4; i1 <= 24; ++i1) {
         this.setBlockAndMetadata(world, -5, 5, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 5, 5, i1, this.roofBlock, this.roofMeta);
         if (i1 <= 23) {
            if (i1 % 6 == 2) {
               for(men = 2; men <= 5; ++men) {
                  this.setBlockAndMetadata(world, -4, men, i1, this.woodBeamRohanBlock, this.woodBeamRohanMeta);
                  this.setBlockAndMetadata(world, 4, men, i1, this.woodBeamRohanBlock, this.woodBeamRohanMeta);
               }

               this.setBlockAndMetadata(world, -3, 5, i1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
               this.setBlockAndMetadata(world, 3, 5, i1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
               this.setBlockAndMetadata(world, -2, 6, i1, this.plank2SlabBlock, this.plank2SlabMeta);
               this.setBlockAndMetadata(world, 2, 6, i1, this.plank2SlabBlock, this.plank2SlabMeta);
               this.setBlockAndMetadata(world, -1, 6, i1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
               this.setBlockAndMetadata(world, 1, 6, i1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
               this.setBlockAndMetadata(world, 0, 6, i1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, 0, 5, i1, LOTRMod.chandelier, 1);
            } else {
               this.setBlockAndMetadata(world, -4, 5, i1, this.plankSlabBlock, this.plankSlabMeta);
            }

            if (i1 % 6 == 4 || i1 % 6 == 0) {
               this.setBlockAndMetadata(world, -4, 4, i1, Blocks.field_150478_aa, 2);
               this.setBlockAndMetadata(world, 4, 4, i1, Blocks.field_150478_aa, 1);
            }

            if (i1 % 6 == 1 || i1 % 6 == 3) {
               this.spawnItemFrame(world, -5, 3, i1, 1, this.getRohanFramedItem(random));
               this.spawnItemFrame(world, 5, 3, i1, 3, this.getRohanFramedItem(random));
            }
         }
      }

      for(i1 = 9; i1 <= 19; ++i1) {
         for(men = -1; men <= 1; ++men) {
            if (i1 % 5 == 4 && Math.abs(men) == 1) {
               this.setBlockAndMetadata(world, men, 2, i1, this.plank2Block, this.plank2Meta);
            } else {
               this.setBlockAndMetadata(world, men, 2, i1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            }

            if (men == 0 && random.nextBoolean()) {
               if (random.nextBoolean()) {
                  this.placeBarrel(world, random, men, 3, i1, random.nextBoolean() ? 4 : 5, LOTRFoods.ROHAN_DRINK);
               } else {
                  this.setBlockAndMetadata(world, men, 3, i1, this.getRandomCakeBlock(random), 0);
               }
            } else if (random.nextInt(3) == 0) {
               this.placeMug(world, random, men, 3, i1, random.nextInt(4), LOTRFoods.ROHAN_DRINK);
            } else {
               this.placePlate(world, random, men, 3, i1, this.plateBlock, LOTRFoods.ROHAN);
            }
         }
      }

      for(i1 = 8; i1 <= 20; ++i1) {
         if (i1 % 2 == 0) {
            this.setBlockAndMetadata(world, -3, 2, i1, this.plankStairBlock, 0);
            this.setBlockAndMetadata(world, 3, 2, i1, this.plankStairBlock, 1);
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(men = 6; men <= 7; ++men) {
            this.setBlockAndMetadata(world, i1, 2, men, this.carpetBlock, this.carpetMeta);
         }
      }

      this.setBlockAndMetadata(world, -2, 2, 4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -3, 2, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, -4, 2, 4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -4, 2, 5, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, -4, 2, 6, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, -2, 3, 4, 2, LOTRFoods.ROHAN_DRINK);
      this.placeBarrel(world, random, -3, 3, 4, 3, LOTRFoods.ROHAN_DRINK);
      this.placeBarrel(world, random, -4, 3, 5, 4, LOTRFoods.ROHAN_DRINK);
      this.placeMug(world, random, -4, 3, 6, 3, LOTRFoods.ROHAN_DRINK);
      this.setBlockAndMetadata(world, 2, 2, 4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 3, 2, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 4, 2, 4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 4, 2, 5, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 4, 2, 6, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 2, 3, 4, 2, LOTRFoods.ROHAN_DRINK);
      this.placeBarrel(world, random, 3, 3, 4, 3, LOTRFoods.ROHAN_DRINK);
      this.placeBarrel(world, random, 4, 3, 5, 5, LOTRFoods.ROHAN_DRINK);
      this.placeMug(world, random, 4, 3, 6, 1, LOTRFoods.ROHAN_DRINK);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 24, LOTRMod.hearth, 0);
         this.setBlockAndMetadata(world, i1, 2, 24, Blocks.field_150480_ab, 0);

         for(men = 3; men <= 4; ++men) {
            this.setAir(world, i1, men, 24);
         }

         for(men = 2; men <= 3; ++men) {
            this.setBlockAndMetadata(world, i1, men, 23, this.barsBlock, 0);
         }
      }

      this.setBlockAndMetadata(world, -3, 6, 24, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 3, 6, 24, this.roofBlock, this.roofMeta);

      for(i1 = -3; i1 <= 3; ++i1) {
         for(men = 22; men <= 23; ++men) {
            this.setBlockAndMetadata(world, i1, 1, men, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
         }
      }

      this.setBlockAndMetadata(world, -3, 4, 23, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 3, 4, 23, Blocks.field_150478_aa, 4);
      this.placeWallBanner(world, -2, 5, 23, this.bannerType, 2);
      this.placeWallBanner(world, 2, 5, 23, this.bannerType, 2);
      this.setBlockAndMetadata(world, -1, 5, 23, this.brickCarvedBlock, this.brickCarvedMeta);
      this.setBlockAndMetadata(world, 1, 5, 23, this.brickCarvedBlock, this.brickCarvedMeta);
      this.placeWeaponRack(world, 0, 5, 22, 6, this.getRandomRohanWeapon(random));
      LOTREntityRohanMan meadhost = new LOTREntityRohanMeadhost(world);
      this.spawnNPCAndSetHome(meadhost, world, 0, 2, 21, 8);
      men = 5 + random.nextInt(5);

      for(l = 0; l < men; ++l) {
         i1 = random.nextBoolean() ? -2 : 2;
         int j1 = 2;
         k1 = MathHelper.func_76136_a(random, 8, 20);
         LOTREntityRohanMan rohirrim = random.nextBoolean() ? new LOTREntityRohanMan(world) : new LOTREntityRohirrimWarrior(world);
         this.spawnNPCAndSetHome((EntityCreature)rohirrim, world, i1, j1, k1, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClass(LOTREntityRohanShieldmaiden.class);
      respawner.setCheckRanges(32, -12, 12, 2);
      respawner.setSpawnRanges(4, -2, 4, 16);
      respawner.setSpawnIntervalMinutes(5);
      respawner.setNoPlayerRange(8);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      return true;
   }

   protected ItemStack getRohanFramedItem(Random random) {
      return random.nextBoolean() ? LOTRFoods.ROHAN_DRINK.getRandomVessel(random).getEmptyVessel() : super.getRohanFramedItem(random);
   }
}
