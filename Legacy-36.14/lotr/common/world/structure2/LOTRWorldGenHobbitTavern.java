package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTREntityHobbitBartender;
import lotr.common.entity.npc.LOTREntityHobbitShirriff;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenHobbitTavern extends LOTRWorldGenHobbitStructure {
   private String[] tavernName;
   private String[] tavernNameSign;
   private String tavernNameNPC;

   public LOTRWorldGenHobbitTavern(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.tavernName = LOTRNames.getHobbitTavernName(random);
      this.tavernNameSign = new String[]{"", this.tavernName[0], this.tavernName[1], ""};
      this.tavernNameNPC = this.tavernName[0] + " " + this.tavernName[1];
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 12);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      int i1;
      int i1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(j1 = -18; j1 <= 18; ++j1) {
            for(i1 = -12; i1 <= 19; ++i1) {
               i1 = this.getTopBlock(world, j1, i1) - 1;
               if (!this.isSurface(world, j1, i1, i1)) {
                  return false;
               }

               if (i1 < i1) {
                  i1 = i1;
               }

               if (i1 > k1) {
                  k1 = i1;
               }

               if (k1 - i1 > 8) {
                  return false;
               }
            }
         }
      }

      int j1;
      for(i1 = -16; i1 <= 16; ++i1) {
         for(k1 = -12; k1 <= 18; ++k1) {
            j1 = Math.abs(i1);
            int grassHeight = -1;
            if (j1 <= 14) {
               if (k1 <= -6) {
                  if ((k1 != -7 || j1 > 1) && (k1 != -6 || j1 > 3)) {
                     grassHeight = 0;
                  } else {
                     grassHeight = 1;
                  }
               } else if (k1 <= -5 && (j1 == 11 || j1 <= 5)) {
                  grassHeight = 1;
               } else if (k1 <= -4 && j1 <= 11) {
                  grassHeight = 1;
               } else if (k1 <= -3 && j1 <= 3) {
                  grassHeight = 1;
               }
            }

            if (grassHeight >= 0) {
               for(i1 = grassHeight; (i1 >= -1 || !this.isOpaque(world, i1, i1, k1)) && this.getY(i1) >= 0; --i1) {
                  if (i1 == grassHeight) {
                     this.setBlockAndMetadata(world, i1, i1, k1, Blocks.field_150349_c, 0);
                  } else {
                     this.setBlockAndMetadata(world, i1, i1, k1, Blocks.field_150346_d, 0);
                  }

                  this.setGrassToDirt(world, i1, i1 - 1, k1);
               }

               for(i1 = grassHeight + 1; i1 <= 12; ++i1) {
                  this.setAir(world, i1, i1, k1);
               }
            } else {
               boolean wood = false;
               boolean beam = false;
               if (k1 >= -5 && k1 <= 17) {
                  if (j1 < 15 || k1 > -4 && k1 < 16) {
                     wood = true;
                  } else {
                     wood = false;
                  }
               }

               if (j1 == 15 && (k1 == -4 || k1 == 16)) {
                  beam = true;
               }

               if (k1 == 18 && j1 <= 14 && IntMath.mod(i1, 5) == 0) {
                  beam = true;
               }

               if (beam || wood) {
                  for(j1 = 5; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                     if (beam) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.beamBlock, this.beamMeta);
                     } else {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.plankBlock, this.plankMeta);
                     }

                     this.setGrassToDirt(world, i1, j1 - 1, k1);
                  }

                  this.setBlockAndMetadata(world, i1, 6, k1, this.plankBlock, this.plankMeta);

                  for(j1 = 8; j1 <= 12; ++j1) {
                     this.setAir(world, i1, j1, k1);
                  }
               }
            }
         }
      }

      for(i1 = -16; i1 <= 16; ++i1) {
         k1 = Math.abs(i1);
         if (k1 <= 4) {
            this.setBlockAndMetadata(world, i1, 1, -10, this.outFenceBlock, this.outFenceMeta);
         }

         if (k1 >= 4 && k1 <= 11) {
            this.setBlockAndMetadata(world, i1, 1, -9, this.outFenceBlock, this.outFenceMeta);
         }

         if (k1 >= 11 && k1 <= 13) {
            this.setBlockAndMetadata(world, i1, 1, -8, this.outFenceBlock, this.outFenceMeta);
         }

         if (k1 == 13) {
            this.setBlockAndMetadata(world, i1, 1, -7, this.outFenceBlock, this.outFenceMeta);
            this.setBlockAndMetadata(world, i1, 1, -6, this.outFenceBlock, this.outFenceMeta);
         }

         if (k1 == 0) {
            this.setBlockAndMetadata(world, i1, 1, -10, this.outFenceGateBlock, 0);
         }

         if (k1 == 4) {
            this.setBlockAndMetadata(world, i1, 2, -10, Blocks.field_150478_aa, 5);
         }

         if (k1 <= 1) {
            if (i1 == 0) {
               this.setBlockAndMetadata(world, i1, 0, -12, this.pathBlock, this.pathMeta);
               this.setBlockAndMetadata(world, i1, 0, -11, this.pathBlock, this.pathMeta);
               this.setBlockAndMetadata(world, i1, 0, -10, this.pathBlock, this.pathMeta);
            }

            this.setBlockAndMetadata(world, i1, 0, -9, this.pathBlock, this.pathMeta);
            this.setBlockAndMetadata(world, i1, 0, -8, this.pathBlock, this.pathMeta);
            this.setBlockAndMetadata(world, i1, 1, -7, this.pathSlabBlock, this.pathSlabMeta);
            this.setBlockAndMetadata(world, i1, 1, -6, this.pathSlabBlock, this.pathSlabMeta);

            for(j1 = -5; j1 <= -2; ++j1) {
               this.setBlockAndMetadata(world, i1, 1, j1, this.pathBlock, this.pathMeta);
            }
         }

         if (k1 == 5 || k1 == 11) {
            this.setGrassToDirt(world, i1, 0, -4);

            for(j1 = 1; j1 <= 5; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, -4, this.beamBlock, this.beamMeta);
            }
         }

         if (k1 == 6 || k1 == 10) {
            this.setBlockAndMetadata(world, i1, 3, -4, this.hedgeBlock, this.hedgeMeta);
            this.setBlockAndMetadata(world, i1, 2, -4, this.hedgeBlock, this.hedgeMeta);
            this.setBlockAndMetadata(world, i1, 1, -5, this.hedgeBlock, this.hedgeMeta);
            this.setBlockAndMetadata(world, i1, 0, -5, Blocks.field_150349_c, 0);
         }

         if (k1 >= 7 && k1 <= 9) {
            this.setBlockAndMetadata(world, i1, 2, -5, this.hedgeBlock, this.hedgeMeta);
            this.setBlockAndMetadata(world, i1, 1, -5, this.hedgeBlock, this.hedgeMeta);
            this.setBlockAndMetadata(world, i1, 0, -5, Blocks.field_150349_c, 0);
            this.setBlockAndMetadata(world, i1, 1, -6, this.hedgeBlock, this.hedgeMeta);
            this.setBlockAndMetadata(world, i1, 2, -4, this.brickBlock, this.brickMeta);
            this.setGrassToDirt(world, i1, 1, -4);
            this.setBlockAndMetadata(world, i1, 3, -3, LOTRMod.glassPane, 0);
            this.setBlockAndMetadata(world, i1, 4, -3, LOTRMod.glassPane, 0);
            if (k1 == 7 || k1 == 9) {
               this.placeFlowerPot(world, i1, 3, -4, this.getRandomFlower(world, random));
            }
         }

         if (k1 >= 6 && k1 <= 10) {
            this.setBlockAndMetadata(world, i1, 5, -4, this.plankStairBlock, 6);
         }

         if (k1 >= 5 && k1 <= 11) {
            this.setBlockAndMetadata(world, i1, 6, -4, this.plankBlock, this.plankMeta);
         }

         if (k1 == 13) {
            this.setBlockAndMetadata(world, i1, 3, -6, this.fence2Block, this.fence2Meta);
            this.setBlockAndMetadata(world, i1, 4, -6, Blocks.field_150478_aa, 5);
         }

         if (k1 == 4) {
            this.setBlockAndMetadata(world, i1, 2, -4, this.hedgeBlock, this.hedgeMeta);
         }

         if (k1 == 3) {
            this.setBlockAndMetadata(world, i1, 2, -4, this.hedgeBlock, this.hedgeMeta);
            this.setBlockAndMetadata(world, i1, 2, -3, this.hedgeBlock, this.hedgeMeta);
         }
      }

      for(i1 = -12; i1 <= 12; ++i1) {
         for(k1 = -8; k1 <= -2; ++k1) {
            for(j1 = 0; j1 <= 1; ++j1) {
               if (this.getBlock(world, i1, j1, k1) == Blocks.field_150349_c && this.isAir(world, i1, j1 + 1, k1) && random.nextInt(12) == 0) {
                  this.plantFlower(world, random, i1, j1 + 1, k1);
               }
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = 2; k1 <= 4; ++k1) {
            this.setAir(world, i1, k1, -2);
         }
      }

      this.setBlockAndMetadata(world, -2, 2, -2, this.plankStairBlock, 0);
      this.setBlockAndMetadata(world, -2, 4, -2, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 2, 2, -2, this.plankStairBlock, 1);
      this.setBlockAndMetadata(world, 2, 4, -2, this.plankStairBlock, 5);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = 2; k1 <= 4; ++k1) {
            this.setAir(world, i1, k1, -1);
            this.setBlockAndMetadata(world, i1, k1, 0, this.brickBlock, this.brickMeta);
         }
      }

      this.setBlockAndMetadata(world, -1, 2, -1, this.plankStairBlock, 0);
      this.setBlockAndMetadata(world, -1, 4, -1, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 2, -1, this.plankStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 4, -1, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 0, 1, -1, this.pathBlock, this.pathMeta);
      this.setBlockAndMetadata(world, 0, 1, 0, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 2, 0, this.doorBlock, 3);
      this.setBlockAndMetadata(world, 0, 3, 0, this.doorBlock, 8);
      this.placeSign(world, 0, 4, -1, Blocks.field_150444_as, 2, this.tavernNameSign);
      this.setBlockAndMetadata(world, -2, 3, -2, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 3, -2, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -3, 4, -3, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.setBlockAndMetadata(world, -2, 5, -3, this.tileSlabBlock, this.tileSlabMeta);
      this.setBlockAndMetadata(world, -1, 5, -3, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.setBlockAndMetadata(world, 0, 5, -3, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.setBlockAndMetadata(world, 1, 5, -3, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.setBlockAndMetadata(world, 2, 5, -3, this.tileSlabBlock, this.tileSlabMeta);
      this.setBlockAndMetadata(world, 3, 4, -3, this.tileSlabBlock, this.tileSlabMeta | 8);
      if (random.nextBoolean()) {
         this.placeSign(world, -2, 2, -10, Blocks.field_150472_an, MathHelper.func_76136_a(random, 7, 9), this.tavernNameSign);
      } else {
         this.placeSign(world, 2, 2, -10, Blocks.field_150472_an, MathHelper.func_76136_a(random, 7, 9), this.tavernNameSign);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, -3, this.roofStairBlock, 2);
      }

      this.setBlockAndMetadata(world, -3, 6, -4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -4, 6, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -4, 6, -5, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 3, 6, -4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 4, 6, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 4, 6, -5, this.roofStairBlock, 1);

      for(i1 = -11; i1 <= -5; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, -5, this.roofStairBlock, 2);
      }

      for(i1 = 5; i1 <= 11; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, -5, this.roofStairBlock, 2);
      }

      this.setBlockAndMetadata(world, -11, 6, -6, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 11, 6, -6, this.roofStairBlock, 1);

      for(i1 = -14; i1 <= -12; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, -6, this.roofStairBlock, 2);
      }

      for(i1 = 12; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, -6, this.roofStairBlock, 2);
      }

      this.setBlockAndMetadata(world, -15, 6, -6, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -15, 6, -5, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -16, 6, -5, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -16, 6, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 15, 6, -6, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 15, 6, -5, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 16, 6, -5, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 16, 6, -4, this.roofStairBlock, 2);

      for(i1 = -4; i1 <= 16; ++i1) {
         this.setBlockAndMetadata(world, -17, 6, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 17, 6, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -16, 6, 16, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -16, 6, 17, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -15, 6, 17, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -15, 6, 18, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 16, 6, 16, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 16, 6, 17, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 15, 6, 17, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 15, 6, 18, this.roofStairBlock, 0);

      for(i1 = -14; i1 <= -11; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, 18, this.roofStairBlock, 3);
      }

      for(i1 = 11; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, 18, this.roofStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -11, 6, 19, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 11, 6, 19, this.roofStairBlock, 0);

      for(i1 = -10; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, 18, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, i1, 6, 19, this.roofStairBlock, 3);
         k1 = IntMath.mod(i1, 5);
         if (IntMath.mod(i1, 5) != 0) {
            this.setBlockAndMetadata(world, i1, 5, 18, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 1, 18, this.brickBlock, this.brickMeta);
            this.setGrassToDirt(world, i1, 0, 18);
            if (k1 != 1 && k1 != 4) {
               this.placeFlowerPot(world, i1, 2, 18, this.getRandomFlower(world, random));
            } else {
               this.setBlockAndMetadata(world, i1, 2, 18, this.hedgeBlock, this.hedgeMeta);
            }

            if (!this.isOpaque(world, i1, 0, 18)) {
               this.setBlockAndMetadata(world, i1, 0, 18, this.plankStairBlock, 7);
            }
         }
      }

      int[] var26 = new int[]{-15, 12};
      k1 = var26.length;

      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var26[j1];

         for(i1 = i1; i1 <= i1 + 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 11, 6, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 11, 8, this.brickStairBlock, 3);
         }

         this.setBlockAndMetadata(world, i1, 11, 7, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, i1 + 3, 11, 7, this.brickStairBlock, 0);

         for(i1 = i1 + 1; i1 <= i1 + 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 11, 7, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 12, 7, Blocks.field_150457_bL, 0);
         }
      }

      var26 = new int[]{-16, 16};
      k1 = var26.length;

      int j1;
      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var26[j1];

         for(i1 = 3; i1 <= 4; ++i1) {
            for(j1 = 2; j1 <= 3; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, i1, LOTRMod.glassPane, 0);
            }
         }
      }

      var26 = new int[]{-17, 17};
      k1 = var26.length;

      int hobbits;
      int l;
      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var26[j1];

         for(i1 = 2; i1 <= 10; ++i1) {
            if (i1 != 6) {
               this.setBlockAndMetadata(world, i1, 1, i1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, 0, i1);
               if (i1 != 2 && i1 != 5 && i1 != 7 && i1 != 10) {
                  this.placeFlowerPot(world, i1, 2, i1, this.getRandomFlower(world, random));
               } else {
                  this.setBlockAndMetadata(world, i1, 2, i1, this.hedgeBlock, this.hedgeMeta);
               }
            }
         }

         int[] var24 = new int[]{1, 6, 11};
         j1 = var24.length;

         for(j1 = 0; j1 < j1; ++j1) {
            hobbits = var24[j1];

            for(l = 5; (l >= 0 || !this.isOpaque(world, i1, l, hobbits)) && this.getY(l) >= 0; --l) {
               this.setBlockAndMetadata(world, i1, l, hobbits, this.beamBlock, this.beamMeta);
               this.setGrassToDirt(world, i1, l, hobbits);
            }
         }

         for(i1 = 1; i1 <= 11; ++i1) {
            this.setBlockAndMetadata(world, i1, 6, i1, this.roofBlock, this.roofMeta);
         }
      }

      for(i1 = 2; i1 <= 10; ++i1) {
         if (i1 != 6) {
            if (!this.isOpaque(world, -17, 0, i1)) {
               this.setBlockAndMetadata(world, -17, 0, i1, this.plankStairBlock, 5);
            }

            this.setBlockAndMetadata(world, -17, 5, i1, this.plankStairBlock, 5);
            if (!this.isOpaque(world, 17, 0, i1)) {
               this.setBlockAndMetadata(world, 17, 0, i1, this.plankStairBlock, 4);
            }

            this.setBlockAndMetadata(world, 17, 5, i1, this.plankStairBlock, 4);
         }
      }

      for(i1 = 7; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, -17, 5, i1, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, 17, 5, i1, this.plankStairBlock, 4);
      }

      for(i1 = 1; i1 <= 11; ++i1) {
         this.setBlockAndMetadata(world, -18, 6, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 18, 6, i1, this.roofStairBlock, 0);
      }

      var26 = new int[]{-18, 18};
      k1 = var26.length;

      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var26[j1];
         this.setBlockAndMetadata(world, i1, 6, 0, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 6, 12, this.roofStairBlock, 3);
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, 7, -2, this.roofStairBlock, 2);
      }

      this.setBlockAndMetadata(world, -4, 7, -3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -5, 7, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -5, 7, -4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, 7, -3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 5, 7, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 5, 7, -4, this.roofStairBlock, 1);

      for(i1 = -12; i1 <= -6; ++i1) {
         this.setBlockAndMetadata(world, i1, 7, -4, this.roofStairBlock, 2);
      }

      for(i1 = 6; i1 <= 12; ++i1) {
         this.setBlockAndMetadata(world, i1, 7, -4, this.roofStairBlock, 2);
      }

      this.setBlockAndMetadata(world, -12, 7, -5, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -13, 7, -5, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -14, 7, -5, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -14, 7, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -15, 7, -4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -15, 7, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 12, 7, -5, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 13, 7, -5, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 14, 7, -5, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 14, 7, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 15, 7, -4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 15, 7, -3, this.roofStairBlock, 2);

      for(i1 = -3; i1 <= 0; ++i1) {
         this.setBlockAndMetadata(world, -16, 7, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 16, 7, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -16, 7, 1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 16, 7, 1, this.roofStairBlock, 2);

      for(i1 = 1; i1 <= 11; ++i1) {
         this.setBlockAndMetadata(world, -17, 7, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 17, 7, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -16, 7, 11, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 16, 7, 11, this.roofStairBlock, 3);

      for(i1 = 12; i1 <= 15; ++i1) {
         this.setBlockAndMetadata(world, -16, 7, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 16, 7, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -15, 7, 15, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -15, 7, 16, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -14, 7, 16, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -14, 7, 17, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 15, 7, 15, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 15, 7, 16, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 14, 7, 16, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 14, 7, 17, this.roofStairBlock, 0);

      for(i1 = -13; i1 <= -11; ++i1) {
         this.setBlockAndMetadata(world, i1, 7, 17, this.roofStairBlock, 3);
      }

      for(i1 = 11; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, i1, 7, 17, this.roofStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -10, 7, 17, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 10, 7, 17, this.roofStairBlock, 0);

      for(i1 = -10; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, i1, 7, 18, this.roofStairBlock, 3);
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 8, -1, this.roofStairBlock, 2);
      }

      this.setBlockAndMetadata(world, -5, 8, -2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -6, 8, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -6, 8, -3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 5, 8, -2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 6, 8, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 6, 8, -3, this.roofStairBlock, 1);

      for(i1 = -13; i1 <= -7; ++i1) {
         this.setBlockAndMetadata(world, i1, 8, -3, this.roofStairBlock, 2);
      }

      for(i1 = 7; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, i1, 8, -3, this.roofStairBlock, 2);
      }

      this.setBlockAndMetadata(world, -13, 8, -4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 13, 8, -4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -14, 8, -3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -14, 8, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 14, 8, -3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 14, 8, -2, this.roofStairBlock, 2);

      for(i1 = -2; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -15, 8, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 15, 8, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -15, 8, 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 15, 8, 2, this.roofStairBlock, 2);

      for(i1 = 2; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, -16, 8, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 16, 8, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -15, 8, 10, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 15, 8, 10, this.roofStairBlock, 3);

      for(i1 = 11; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, -15, 8, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 15, 8, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -14, 8, 14, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -14, 8, 15, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -13, 8, 15, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -13, 8, 16, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 14, 8, 14, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 14, 8, 15, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 13, 8, 15, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 13, 8, 16, this.roofStairBlock, 0);

      for(i1 = -12; i1 <= -10; ++i1) {
         this.setBlockAndMetadata(world, i1, 8, 16, this.roofStairBlock, 3);
      }

      for(i1 = 10; i1 <= 12; ++i1) {
         this.setBlockAndMetadata(world, i1, 8, 16, this.roofStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -9, 8, 16, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 9, 8, 16, this.roofStairBlock, 0);

      for(i1 = -9; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, i1, 8, 17, this.roofStairBlock, 3);
      }

      boolean room;
      for(i1 = -16; i1 <= 16; ++i1) {
         k1 = Math.abs(i1);

         for(j1 = -4; j1 <= 17; ++j1) {
            room = false;
            if (j1 == -4) {
               room = k1 == 13;
            }

            if (j1 == -3) {
               room = k1 >= 6 && k1 <= 14;
            }

            if (j1 == -2) {
               room = k1 >= 5 && k1 <= 15;
            }

            if (j1 >= -1 && j1 <= 1) {
               room = k1 <= 15;
            }

            if (j1 >= 2 && j1 <= 10) {
               room = k1 <= 16;
            }

            if (j1 >= 11 && j1 <= 14) {
               room = k1 <= 15;
            }

            if (j1 == 15) {
               room = k1 <= 14;
            }

            if (j1 == 16) {
               room = k1 <= 13;
            }

            if (j1 == 17) {
               room = k1 <= 9;
            }

            if (room) {
               this.setBlockAndMetadata(world, i1, 7, j1, this.roofBlock, this.roofMeta);
            }
         }

         for(j1 = -2; j1 <= 16; ++j1) {
            room = false;
            if (j1 == -2) {
               room = k1 >= 7 && k1 <= 13;
            }

            if (j1 == -1) {
               room = k1 >= 6 && k1 <= 14;
            }

            if (j1 >= 0 && j1 <= 2) {
               room = k1 <= 14;
            }

            if (j1 >= 3 && j1 <= 9) {
               room = k1 <= 15;
            }

            if (j1 >= 10 && j1 <= 13) {
               room = k1 <= 14;
            }

            if (j1 == 14) {
               room = k1 <= 13;
            }

            if (j1 == 15) {
               room = k1 <= 12;
            }

            if (j1 == 16) {
               room = k1 <= 8;
            }

            if (room) {
               this.setBlockAndMetadata(world, i1, 8, j1, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, i1, 9, j1, this.roofSlabBlock, this.roofSlabMeta);
            }
         }
      }

      for(i1 = -6; i1 <= 6; ++i1) {
         k1 = Math.abs(i1);

         for(j1 = 1; j1 <= 15; ++j1) {
            room = false;
            if (j1 == 1 && k1 <= 1) {
               room = true;
            }

            if (j1 == 2 && k1 <= 2) {
               room = true;
            }

            if (j1 == 3 && k1 <= 3) {
               room = true;
            }

            if (j1 == 4 && k1 <= 4) {
               room = true;
            }

            if (j1 == 5 && k1 <= 5) {
               room = true;
            }

            if (j1 >= 6 && j1 <= 10 && k1 <= 6) {
               room = true;
            }

            if (j1 >= 11 && j1 <= 12 && k1 <= 5) {
               room = true;
            }

            if (j1 == 13 && k1 <= 4) {
               room = true;
            }

            if (j1 >= 14 && j1 <= 15 && k1 <= 2) {
               room = true;
            }

            if (room) {
               this.setBlockAndMetadata(world, i1, 1, j1, this.floorBlock, this.floorMeta);

               for(i1 = 2; i1 <= 5; ++i1) {
                  this.setAir(world, i1, i1, j1);
               }
            }
         }

         for(j1 = 2; j1 <= 4; ++j1) {
            if (k1 == 2) {
               this.setBlockAndMetadata(world, i1, j1, 1, this.brickBlock, this.brickMeta);
            }

            if (k1 == 4) {
               this.setBlockAndMetadata(world, i1, j1, 3, this.beamBlock, this.beamMeta);
            }

            if (k1 == 6) {
               this.setBlockAndMetadata(world, i1, j1, 5, this.beamBlock, this.beamMeta);
               this.setBlockAndMetadata(world, i1, j1, 11, this.beamBlock, this.beamMeta);
            }

            if (k1 == 5) {
               this.setBlockAndMetadata(world, i1, j1, 13, this.beamBlock, this.beamMeta);
            }

            if (k1 == 3) {
               this.setBlockAndMetadata(world, i1, j1, 14, this.beamBlock, this.beamMeta);
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         for(k1 = 11; k1 <= 15; ++k1) {
            this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
         }

         this.setBlockAndMetadata(world, i1, 5, 10, this.plankStairBlock, 6);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 1, this.plankBlock, this.plankMeta);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 2, this.plankStairBlock, 7);
      }

      this.setBlockAndMetadata(world, -2, 5, 3, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 2, 5, 3, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, -3, 5, 3, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 3, 5, 3, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, -3, 5, 4, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 3, 5, 4, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, -4, 5, 4, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 4, 5, 4, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, -4, 5, 5, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 4, 5, 5, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, -5, 5, 5, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 5, 5, 5, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, -5, 5, 6, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 5, 5, 6, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, -6, 5, 6, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 6, 5, 6, this.plankStairBlock, 7);

      for(i1 = 7; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, -6, 5, i1, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, 6, 5, i1, this.plankStairBlock, 5);
      }

      this.setBlockAndMetadata(world, 0, 4, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -6, 3, 6, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 6, 3, 6, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -6, 3, 10, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 6, 3, 10, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 5, 5, this.chandelierBlock, this.chandelierMeta);
      this.setBlockAndMetadata(world, -4, 5, 8, this.chandelierBlock, this.chandelierMeta);
      this.setBlockAndMetadata(world, 4, 5, 8, this.chandelierBlock, this.chandelierMeta);
      this.setBlockAndMetadata(world, -2, 3, 2, Blocks.field_150479_bC, 0);
      this.setBlockAndMetadata(world, 2, 3, 2, Blocks.field_150479_bC, 0);
      this.setBlockAndMetadata(world, -3, 3, 3, Blocks.field_150479_bC, 0);
      this.setBlockAndMetadata(world, 3, 3, 3, Blocks.field_150479_bC, 0);
      this.setBlockAndMetadata(world, -4, 3, 4, Blocks.field_150479_bC, 1);
      this.setBlockAndMetadata(world, 4, 3, 4, Blocks.field_150479_bC, 3);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = 1; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, i1, 2, k1, this.carpetBlock, this.carpetMeta);
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = 5; k1 <= 7; ++k1) {
            this.setBlockAndMetadata(world, i1, 2, k1, this.carpetBlock, this.carpetMeta);
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         k1 = Math.abs(i1);
         this.setBlockAndMetadata(world, i1, 2, 11, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, i1, 4, 11, this.fence2Block, this.fence2Meta);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, i1, 2, 9, this.fence2Block, this.fence2Meta);
         }

         if (k1 == 2) {
            this.placeBarrel(world, random, i1, 3, 11, 3, LOTRFoods.HOBBIT_DRINK);
         }

         if (k1 == 1) {
            this.placeMug(world, random, i1, 3, 11, 0, LOTRFoods.HOBBIT_DRINK);
         }
      }

      int[] var25;
      for(i1 = 12; i1 <= 13; ++i1) {
         var25 = new int[]{-3, 3};
         j1 = var25.length;

         for(i1 = 0; i1 < j1; ++i1) {
            i1 = var25[i1];
            this.setBlockAndMetadata(world, i1, 2, i1, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, i1, 4, i1, this.fence2Block, this.fence2Meta);
         }
      }

      this.setBlockAndMetadata(world, 3, 2, 12, this.fenceGate2Block, 1);

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, 15, this.plankStairBlock, 6);
      }

      this.setBlockAndMetadata(world, -2, 4, 14, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 4, 14, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -2, 2, 15, Blocks.field_150462_ai, 0);
      this.placeChest(world, random, -1, 2, 15, 2, LOTRChestContents.HOBBIT_HOLE_LARDER);
      this.setBlockAndMetadata(world, 0, 2, 15, this.plankBlock, this.plankMeta);
      this.placeFlowerPot(world, 0, 3, 15, new ItemStack(LOTRMod.shireHeather));
      this.setBlockAndMetadata(world, 1, 2, 15, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, 2, 2, 15, LOTRMod.hobbitOven, 2);
      var26 = new int[]{-7, 7};
      k1 = var26.length;

      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var26[j1];
         this.setBlockAndMetadata(world, i1, 1, 8, this.floorBlock, this.floorMeta);
         this.setBlockAndMetadata(world, i1, 2, 8, this.carpetBlock, this.carpetMeta);
         this.setAir(world, i1, 3, 8);
         this.setBlockAndMetadata(world, i1, 2, 7, this.plankStairBlock, 3);
         this.setBlockAndMetadata(world, i1, 3, 7, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 2, 9, this.plankStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 3, 9, this.plankStairBlock, 6);
      }

      for(i1 = 7; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, -6, 2, i1, this.carpetBlock, this.carpetMeta);
         this.setBlockAndMetadata(world, -5, 2, i1, this.carpetBlock, this.carpetMeta);
         this.setBlockAndMetadata(world, 5, 2, i1, this.carpetBlock, this.carpetMeta);
         this.setBlockAndMetadata(world, 6, 2, i1, this.carpetBlock, this.carpetMeta);
      }

      for(i1 = -15; i1 <= -8; ++i1) {
         for(k1 = 3; k1 <= 14; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, this.floorBlock, this.floorMeta);

            for(j1 = 1; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = -15; i1 <= -11; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, this.floorBlock, this.floorMeta);

            for(j1 = 1; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = -10; i1 <= -5; ++i1) {
         for(k1 = -2; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, this.floorBlock, this.floorMeta);

            for(j1 = 2; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = 1; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, -15, i1, 14, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, -9, i1, 14, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, -8, i1, 14, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -8, i1, 11, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, -8, i1, 5, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, -8, i1, 4, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -9, i1, 3, this.beamBlock, this.beamMeta);
      }

      this.setBlockAndMetadata(world, -8, 3, 6, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -8, 3, 10, Blocks.field_150478_aa, 4);

      for(i1 = 6; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, -8, 1, i1, this.floorStairBlock, 1);
         this.setBlockAndMetadata(world, -8, 5, i1, this.plankStairBlock, 5);
      }

      this.setBlockAndMetadata(world, -9, 1, 11, this.plank2Block, this.plank2Meta);

      for(i1 = 2; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -9, i1, 11, this.fence2Block, this.fence2Meta);
      }

      this.setBlockAndMetadata(world, -9, 5, 11, this.plank2Block, this.plank2Meta);

      for(i1 = 12; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, -9, 1, i1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, -8, 1, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -8, 2, i1, this.plankStairBlock, 5);
         this.placeFlowerPot(world, -8, 3, i1, this.getRandomFlower(world, random));
         this.setBlockAndMetadata(world, -8, 4, i1, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, -8, 5, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -9, 5, i1, this.plank2StairBlock, 5);
      }

      for(i1 = -14; i1 <= -10; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 14, this.plank2StairBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, 14, this.plank2StairBlock, 6);
      }

      for(i1 = -13; i1 <= -11; ++i1) {
         this.setBlockAndMetadata(world, i1, 2, 15, this.plankStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 3, 15, LOTRMod.barrel, 2);
         this.setBlockAndMetadata(world, i1, 4, 15, this.plankStairBlock, 6);
      }

      for(i1 = 9; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, -15, 1, i1, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, -15, 5, i1, this.plank2StairBlock, 4);
      }

      for(i1 = 10; i1 <= 12; ++i1) {
         this.spawnItemFrame(world, -16, 3, i1, 1, this.getTavernFramedItem(random));
      }

      for(i1 = -13; i1 <= -11; ++i1) {
         for(k1 = 10; k1 <= 12; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, this.plank2Block, this.plank2Meta);
            this.placePlateOrMug(world, random, i1, 2, k1);
         }
      }

      this.setBlockAndMetadata(world, -12, 5, 11, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, -13, 5, 11, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, -11, 5, 11, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, -12, 5, 10, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, -12, 5, 12, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, -12, 4, 11, this.chandelierBlock, this.chandelierMeta);

      for(i1 = -15; i1 <= -12; ++i1) {
         for(k1 = 6; k1 <= 8; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150417_aV, 0);

            for(j1 = 2; j1 <= 4; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
            }

            this.setBlockAndMetadata(world, i1, 5, k1, Blocks.field_150417_aV, 0);

            for(j1 = 6; j1 <= 10; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = -14; i1 <= -13; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, 7, LOTRMod.hearth, 0);
         this.setBlockAndMetadata(world, i1, 1, 7, Blocks.field_150480_ab, 0);

         for(k1 = 2; k1 <= 10; ++k1) {
            this.setAir(world, i1, k1, 7);
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -12, i1, 7, this.barsBlock, 0);
      }

      this.setBlockAndMetadata(world, -10, 5, 7, this.chandelierBlock, this.chandelierMeta);

      for(i1 = 2; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, -15, 1, i1, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, -15, 5, i1, this.plank2StairBlock, 4);
      }

      for(i1 = 1; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, -15, i1, 1, this.beamBlock, this.beamMeta);
      }

      this.setBlockAndMetadata(world, -14, 1, 1, this.plank2Block, this.plank2Meta);

      for(i1 = 2; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -14, i1, 1, this.fence2Block, this.fence2Meta);
      }

      this.setBlockAndMetadata(world, -14, 5, 1, this.plank2Block, this.plank2Meta);

      for(i1 = 3; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -13, 1, i1, this.plank2Block, this.plank2Meta);
         this.placePlateOrMug(world, random, -13, 2, i1);
      }

      this.setBlockAndMetadata(world, -13, 5, 4, this.chandelierBlock, this.chandelierMeta);

      for(i1 = -3; i1 <= 0; ++i1) {
         this.setBlockAndMetadata(world, -15, 1, i1, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, -15, 5, i1, this.plank2StairBlock, 4);
      }

      for(i1 = -2; i1 <= -1; ++i1) {
         for(k1 = -13; k1 <= -12; ++k1) {
            this.setBlockAndMetadata(world, k1, 1, i1, this.plank2Block, this.plank2Meta);
            this.placePlateOrMug(world, random, k1, 2, i1);
         }

         this.spawnItemFrame(world, -16, 3, i1, 1, this.getTavernFramedItem(random));
      }

      for(i1 = -14; i1 <= -12; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, -4, this.plank2StairBlock, 3);

         for(k1 = 2; k1 <= 4; ++k1) {
            this.setAir(world, i1, k1, -4);
         }

         this.setBlockAndMetadata(world, i1, 5, -4, this.plank2StairBlock, 7);
      }

      this.spawnItemFrame(world, -13, 3, -5, 0, this.getTavernFramedItem(random));
      this.setBlockAndMetadata(world, -12, 5, -1, this.chandelierBlock, this.chandelierMeta);

      for(i1 = -1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -10, 1, i1, this.floorStairBlock, 1);
      }

      this.setBlockAndMetadata(world, -10, 1, 3, this.floorStairBlock, 3);

      for(i1 = 2; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, -5, i1, 3, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, -5, i1, -2, this.beamBlock, this.beamMeta);
      }

      for(i1 = -8; i1 <= -6; ++i1) {
         this.setBlockAndMetadata(world, i1, 2, 3, this.plank2StairBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, 3, this.plank2StairBlock, 6);
      }

      this.setBlockAndMetadata(world, -7, 3, 4, LOTRMod.barrel, 2);
      this.setBlockAndMetadata(world, -7, 4, 4, this.plankStairBlock, 6);

      for(i1 = -1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -5, 2, i1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, -5, 5, i1, this.plank2StairBlock, 5);
      }

      this.setBlockAndMetadata(world, -4, 3, 2, this.plankStairBlock, 2);
      this.setBlockAndMetadata(world, -4, 4, 2, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, -4, 3, -1, this.plankStairBlock, 3);
      this.setBlockAndMetadata(world, -4, 4, -1, this.plankStairBlock, 7);
      this.placeFlowerPot(world, -4, 3, 1, this.getRandomFlower(world, random));
      this.placeFlowerPot(world, -4, 3, 0, this.getRandomFlower(world, random));
      this.setBlockAndMetadata(world, -4, 4, 1, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, -4, 4, 0, this.plankSlabBlock, this.plankSlabMeta | 8);

      for(i1 = -9; i1 <= -6; ++i1) {
         this.setBlockAndMetadata(world, i1, 2, -2, this.plank2StairBlock, 3);
         this.setBlockAndMetadata(world, i1, 5, -2, this.plank2StairBlock, 7);
      }

      this.setBlockAndMetadata(world, -10, 1, -2, this.plank2Block, this.plank2Meta);
      this.setBlockAndMetadata(world, -10, 2, -2, this.plank2Block, this.plank2Meta);

      for(i1 = 3; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -10, i1, -2, this.fence2Block, this.fence2Meta);
      }

      this.setBlockAndMetadata(world, -10, 5, -2, this.plank2Block, this.plank2Meta);

      for(i1 = -8; i1 <= -7; ++i1) {
         for(k1 = 0; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, i1, 2, k1, this.plank2Block, this.plank2Meta);
            this.placePlateOrMug(world, random, i1, 3, k1);
         }
      }

      this.setBlockAndMetadata(world, -8, 5, 1, this.chandelierBlock, this.chandelierMeta);

      for(i1 = 8; i1 <= 15; ++i1) {
         for(k1 = 3; k1 <= 14; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, this.floorBlock, this.floorMeta);

            for(j1 = 1; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = 11; i1 <= 15; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, this.floorBlock, this.floorMeta);

            for(j1 = 1; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = 5; i1 <= 10; ++i1) {
         for(k1 = -2; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, this.floorBlock, this.floorMeta);

            for(j1 = 2; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = 1; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, 15, i1, 14, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 9, i1, 14, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 8, i1, 14, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 8, i1, 11, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 8, i1, 5, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 8, i1, 4, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 9, i1, 3, this.beamBlock, this.beamMeta);
      }

      this.setBlockAndMetadata(world, 8, 3, 6, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 8, 3, 10, Blocks.field_150478_aa, 4);

      for(i1 = 6; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, 8, 1, i1, this.floorStairBlock, 0);
         this.setBlockAndMetadata(world, 8, 5, i1, this.plankStairBlock, 4);
      }

      this.setBlockAndMetadata(world, 9, 1, 11, this.plank2Block, this.plank2Meta);

      for(i1 = 2; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 9, i1, 11, this.fence2Block, this.fence2Meta);
      }

      this.setBlockAndMetadata(world, 9, 5, 11, this.plank2Block, this.plank2Meta);

      for(i1 = 12; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, 9, 1, i1, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, 8, 1, i1, this.plankBlock, this.plankMeta);

         for(k1 = 2; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, 8, k1, i1, Blocks.field_150342_X, 0);
         }

         this.setBlockAndMetadata(world, 8, 5, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 9, 5, i1, this.plank2StairBlock, 4);
      }

      for(i1 = 10; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 14, this.plank2StairBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, 14, this.plank2StairBlock, 6);
      }

      for(i1 = 10; i1 <= 14; ++i1) {
         for(k1 = 2; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 15, Blocks.field_150342_X, 0);
         }
      }

      for(i1 = 9; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, 15, 1, i1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, 15, 5, i1, this.plank2StairBlock, 5);
      }

      this.placeWallBanner(world, 16, 4, 11, LOTRItemBanner.BannerType.HOBBIT, 3);

      for(i1 = 11; i1 <= 13; ++i1) {
         for(k1 = 10; k1 <= 12; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, this.plank2Block, this.plank2Meta);
            this.placePlateOrMug(world, random, i1, 2, k1);
         }
      }

      this.setBlockAndMetadata(world, 12, 5, 11, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, 13, 5, 11, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, 11, 5, 11, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, 12, 5, 10, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, 12, 5, 12, this.fence2Block, this.fence2Meta);
      this.setBlockAndMetadata(world, 12, 4, 11, this.chandelierBlock, this.chandelierMeta);

      for(i1 = 12; i1 <= 15; ++i1) {
         for(k1 = 6; k1 <= 8; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150417_aV, 0);

            for(j1 = 2; j1 <= 4; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
            }

            this.setBlockAndMetadata(world, i1, 5, k1, Blocks.field_150417_aV, 0);

            for(j1 = 6; j1 <= 10; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = 13; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, 7, LOTRMod.hearth, 0);
         this.setBlockAndMetadata(world, i1, 1, 7, Blocks.field_150480_ab, 0);

         for(k1 = 2; k1 <= 10; ++k1) {
            this.setAir(world, i1, k1, 7);
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, 12, i1, 7, this.barsBlock, 0);
      }

      this.setBlockAndMetadata(world, 10, 5, 7, this.chandelierBlock, this.chandelierMeta);

      for(i1 = 2; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, 15, 1, i1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, 15, 5, i1, this.plank2StairBlock, 5);
      }

      for(i1 = 1; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, 15, i1, 1, this.beamBlock, this.beamMeta);
      }

      this.setBlockAndMetadata(world, 14, 1, 1, this.plank2Block, this.plank2Meta);

      for(i1 = 2; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 14, i1, 1, this.fence2Block, this.fence2Meta);
      }

      this.setBlockAndMetadata(world, 14, 5, 1, this.plank2Block, this.plank2Meta);

      for(i1 = 3; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 13, 1, i1, this.plank2Block, this.plank2Meta);
         this.placePlateOrMug(world, random, 13, 2, i1);
      }

      this.setBlockAndMetadata(world, 13, 5, 4, this.chandelierBlock, this.chandelierMeta);

      for(i1 = -3; i1 <= 0; ++i1) {
         this.setBlockAndMetadata(world, 15, 1, i1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, 15, 5, i1, this.plank2StairBlock, 5);
      }

      for(i1 = -2; i1 <= -1; ++i1) {
         for(k1 = 12; k1 <= 13; ++k1) {
            this.setBlockAndMetadata(world, k1, 1, i1, this.plank2Block, this.plank2Meta);
            this.placePlateOrMug(world, random, k1, 2, i1);
         }
      }

      this.placeWallBanner(world, 16, 4, -2, LOTRItemBanner.BannerType.HOBBIT, 3);

      for(i1 = 12; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, -4, this.plank2StairBlock, 3);

         for(k1 = 2; k1 <= 4; ++k1) {
            this.setAir(world, i1, k1, -4);
         }

         this.setBlockAndMetadata(world, i1, 5, -4, this.plank2StairBlock, 7);
      }

      this.placeWallBanner(world, 13, 4, -5, LOTRItemBanner.BannerType.HOBBIT, 0);
      this.setBlockAndMetadata(world, 12, 5, -1, this.chandelierBlock, this.chandelierMeta);

      for(i1 = -1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, 10, 1, i1, this.floorStairBlock, 0);
      }

      this.setBlockAndMetadata(world, 10, 1, 3, this.floorStairBlock, 3);

      for(i1 = 2; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, 5, i1, 3, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 5, i1, -2, this.beamBlock, this.beamMeta);
      }

      for(i1 = 6; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, i1, 2, 3, this.plank2StairBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, 3, this.plank2StairBlock, 6);
      }

      this.placeWallBanner(world, 7, 4, 4, LOTRItemBanner.BannerType.HOBBIT, 2);

      for(i1 = -1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, 5, 2, i1, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, 4, 3, i1, Blocks.field_150342_X, 0);
         this.setBlockAndMetadata(world, 4, 4, i1, Blocks.field_150342_X, 0);
         this.setBlockAndMetadata(world, 5, 5, i1, this.plank2StairBlock, 4);
      }

      for(i1 = 6; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, i1, 2, -2, this.plank2StairBlock, 3);
         this.setBlockAndMetadata(world, i1, 5, -2, this.plank2StairBlock, 7);
      }

      this.setBlockAndMetadata(world, 10, 1, -2, this.plank2Block, this.plank2Meta);
      this.setBlockAndMetadata(world, 10, 2, -2, this.plank2Block, this.plank2Meta);

      for(i1 = 3; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 10, i1, -2, this.fence2Block, this.fence2Meta);
      }

      this.setBlockAndMetadata(world, 10, 5, -2, this.plank2Block, this.plank2Meta);

      for(i1 = 7; i1 <= 8; ++i1) {
         for(k1 = 0; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, i1, 2, k1, this.plank2Block, this.plank2Meta);
            this.placePlateOrMug(world, random, i1, 3, k1);
         }
      }

      this.setBlockAndMetadata(world, 8, 5, 1, this.chandelierBlock, this.chandelierMeta);

      for(i1 = -3; i1 <= 4; ++i1) {
         for(k1 = 11; k1 <= 15; ++k1) {
            this.setBlockAndMetadata(world, i1, -4, k1, this.floorBlock, this.floorMeta);

            for(j1 = -3; j1 <= 0; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = -3; i1 <= 4; ++i1) {
         var25 = new int[]{10, 16};
         j1 = var25.length;

         for(i1 = 0; i1 < j1; ++i1) {
            i1 = var25[i1];
            this.setBlockAndMetadata(world, i1, -3, i1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, -2, i1, this.beamBlock, this.beamMeta | 4);
            this.setBlockAndMetadata(world, i1, -1, i1, this.plankBlock, this.plankMeta);
         }

         for(k1 = 11; k1 <= 13; ++k1) {
            if (i1 >= 0) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.beamBlock, this.beamMeta | 4);
            }
         }

         for(k1 = 14; k1 <= 15; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, this.beamBlock, this.beamMeta | 4);
         }
      }

      for(i1 = 11; i1 <= 15; ++i1) {
         var25 = new int[]{-4, 5};
         j1 = var25.length;

         for(i1 = 0; i1 < j1; ++i1) {
            i1 = var25[i1];
            this.setBlockAndMetadata(world, i1, -3, i1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, -2, i1, this.beamBlock, this.beamMeta | 8);
            this.setBlockAndMetadata(world, i1, -1, i1, this.plankBlock, this.plankMeta);
         }
      }

      for(i1 = -3; i1 <= -1; ++i1) {
         this.setBlockAndMetadata(world, -3, i1, 15, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 4, i1, 15, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 4, i1, 11, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 0, i1, 11, this.beamBlock, this.beamMeta);
      }

      this.placeBarrel(world, random, 4, -3, 14, 5, LOTRFoods.HOBBIT_DRINK);

      for(i1 = 12; i1 <= 13; ++i1) {
         this.placeChest(world, random, 4, -3, i1, 5, LOTRChestContents.HOBBIT_HOLE_LARDER);
      }

      for(i1 = 12; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, 4, -2, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.placeBarrel(world, random, 4, -1, i1, 5, LOTRFoods.HOBBIT_DRINK);
      }

      this.placeBarrel(world, random, 1, -3, 11, 3, LOTRFoods.HOBBIT_DRINK);

      for(i1 = 2; i1 <= 3; ++i1) {
         this.placeChest(world, random, i1, -3, 11, 3, LOTRChestContents.HOBBIT_HOLE_LARDER);
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, -2, 11, this.plankSlabBlock, this.plankSlabMeta | 8);
         Block cakeBlock = LOTRWorldGenHobbitStructure.getRandomCakeBlock(random);
         this.setBlockAndMetadata(world, i1, -1, 11, cakeBlock, 0);
      }

      for(i1 = 11; i1 <= 13; ++i1) {
         this.setAir(world, -2, 1, i1);
         this.setAir(world, -3, 1, i1);
         this.setAir(world, -3, 0, i1);
      }

      for(i1 = 10; i1 <= 12; ++i1) {
         this.setAir(world, -3, 0, i1);
      }

      this.setBlockAndMetadata(world, -3, 1, 14, this.floorBlock, this.floorMeta);

      for(i1 = -3; i1 <= -1; ++i1) {
         for(k1 = 11; k1 <= 12; ++k1) {
            for(j1 = -3; j1 <= -1; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = 0; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -2, 1 - i1, 14 - i1, this.floorStairBlock, 2);
      }

      for(i1 = -3; i1 <= -2; ++i1) {
         this.setAir(world, i1, -1, 11);
         this.setBlockAndMetadata(world, i1, -2, 11, this.floorBlock, this.floorMeta);
      }

      this.setAir(world, -3, -1, 12);
      this.setBlockAndMetadata(world, -3, -2, 12, this.floorStairBlock, 3);

      for(i1 = -2; i1 <= -1; ++i1) {
         this.setBlockAndMetadata(world, i1, -1, 13, this.floorStairBlock, 7);
      }

      for(i1 = 13; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, -3, -3, i1, this.floorBlock, this.floorMeta);
      }

      for(i1 = 13; i1 <= 15; ++i1) {
         this.setBlockAndMetadata(world, -2, -3, i1, this.floorStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -2, -1, 15, Blocks.field_150478_aa, 2);

      for(i1 = 11; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, -4, 0, i1, this.beamBlock, this.beamMeta | 8);
         this.setBlockAndMetadata(world, -1, 0, i1, this.beamBlock, this.beamMeta | 8);
      }

      for(i1 = -3; i1 <= -2; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, 10, this.beamBlock, this.beamMeta | 4);
      }

      LOTREntityHobbitBartender bartender = new LOTREntityHobbitBartender(world);
      bartender.setSpecificLocationName(this.tavernNameNPC);
      this.spawnNPCAndSetHome(bartender, world, 1, 2, 13, 2);
      var25 = new int[]{-10, 10};
      j1 = var25.length;

      for(i1 = 0; i1 < j1; ++i1) {
         i1 = var25[i1];
         int j1 = 1;
         int k1 = 7;
         hobbits = 3 + random.nextInt(6);

         for(l = 0; l < hobbits; ++l) {
            LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
            this.spawnNPCAndSetHome(hobbit, world, i1, j1, k1, 16);
         }

         if (random.nextInt(4) == 0) {
            LOTREntityHobbit shirriffChief = new LOTREntityHobbitShirriff(world);
            shirriffChief.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(shirriffChief, world, i1, j1, k1, 16);
         }
      }

      this.placeSign(world, -8, 4, 8, Blocks.field_150444_as, 5, LOTRNames.getHobbitTavernQuote(random));
      this.placeSign(world, 8, 4, 8, Blocks.field_150444_as, 4, LOTRNames.getHobbitTavernQuote(random));
      return true;
   }

   private void placePlateOrMug(World world, Random random, int i, int j, int k) {
      if (random.nextBoolean()) {
         this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.HOBBIT_DRINK);
      } else {
         this.placePlate(world, random, i, j, k, this.plateBlock, LOTRFoods.HOBBIT);
      }

   }

   private ItemStack getTavernFramedItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.leatherHat), LOTRItemLeatherHat.setFeatherColor(new ItemStack(LOTRMod.leatherHat), 16777215), LOTRItemLeatherHat.setHatColor(new ItemStack(LOTRMod.leatherHat), 2301981), LOTRItemLeatherHat.setFeatherColor(LOTRItemLeatherHat.setHatColor(new ItemStack(LOTRMod.leatherHat), 2301981), 3381529), new ItemStack(LOTRMod.hobbitPipe), new ItemStack(Items.field_151122_aG), new ItemStack(Items.field_151008_G), new ItemStack(Items.field_151041_m), new ItemStack(Items.field_151031_f), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.mugAle), new ItemStack(LOTRMod.mugCider), new ItemStack(LOTRMod.ceramicMug), new ItemStack(Items.field_151069_bo), new ItemStack(Items.field_151032_g), new ItemStack(LOTRMod.shireHeather), new ItemStack(LOTRMod.bluebell), new ItemStack(Blocks.field_150327_N, 1, 0), new ItemStack(Blocks.field_150328_O, 1, 0), new ItemStack(Blocks.field_150328_O, 1, 3)};
      return items[random.nextInt(items.length)].func_77946_l();
   }
}
