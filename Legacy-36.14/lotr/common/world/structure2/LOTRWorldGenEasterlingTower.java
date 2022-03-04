package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.entity.npc.LOTREntityEasterlingArcher;
import lotr.common.entity.npc.LOTREntityEasterlingWarrior;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTower extends LOTRWorldGenEasterlingStructureTown {
   private boolean enableDoor = true;
   private boolean frontLadder = false;
   private boolean backLadder = false;
   private boolean leftLadder = false;
   private boolean rightLadder = false;

   public LOTRWorldGenEasterlingTower(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenEasterlingTower disableDoor() {
      this.enableDoor = false;
      return this;
   }

   public LOTRWorldGenEasterlingTower setFrontLadder() {
      this.frontLadder = true;
      return this;
   }

   public LOTRWorldGenEasterlingTower setBackLadder() {
      this.backLadder = true;
      return this;
   }

   public LOTRWorldGenEasterlingTower setLeftLadder() {
      this.leftLadder = true;
      return this;
   }

   public LOTRWorldGenEasterlingTower setRightLadder() {
      this.rightLadder = true;
      return this;
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.bedBlock = LOTRMod.strawBed;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 3);
      this.setupRandomBlocks(random);
      int soldiers;
      int l;
      int i2;
      if (this.restrictions) {
         for(soldiers = -3; soldiers <= 3; ++soldiers) {
            for(l = -3; l <= 3; ++l) {
               i2 = this.getTopBlock(world, soldiers, l) - 1;
               if (!this.isSurface(world, soldiers, i2, l)) {
                  return false;
               }
            }
         }
      }

      int k2;
      for(soldiers = -2; soldiers <= 2; ++soldiers) {
         for(l = -2; l <= 2; ++l) {
            i2 = Math.abs(soldiers);
            k2 = Math.abs(l);

            int j1;
            for(j1 = 1; j1 <= 15; ++j1) {
               this.setAir(world, soldiers, j1, l);
            }

            if (i2 == 2 && k2 == 2) {
               for(j1 = 13; (j1 >= 0 || !this.isOpaque(world, soldiers, j1, l)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, soldiers, j1, l, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, soldiers, j1 - 1, l);
               }
            } else {
               for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, soldiers, j1, l)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, soldiers, j1, l, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, soldiers, j1 - 1, l);
               }

               if (i2 != 2 && k2 != 2) {
                  this.setBlockAndMetadata(world, soldiers, 4, l, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, soldiers, 10, l, this.brickBlock, this.brickMeta);
               } else {
                  if (i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
                     for(j1 = 1; j1 <= 9; ++j1) {
                        this.setBlockAndMetadata(world, soldiers, j1, l, this.pillarBlock, this.pillarMeta);
                     }
                  } else {
                     for(j1 = 1; j1 <= 2; ++j1) {
                        this.setBlockAndMetadata(world, soldiers, j1, l, this.brickBlock, this.brickMeta);
                     }

                     int stairMeta = 0;
                     if (soldiers == -2) {
                        stairMeta = 1;
                     } else if (soldiers == 2) {
                        stairMeta = 0;
                     } else if (l == -2) {
                        stairMeta = 2;
                     } else if (l == 2) {
                        stairMeta = 3;
                     }

                     for(int j1 = 3; j1 <= 8; ++j1) {
                        if (j1 == 4) {
                           this.setBlockAndMetadata(world, soldiers, j1, l, this.brickRedStairBlock, stairMeta);
                        } else {
                           this.setBlockAndMetadata(world, soldiers, j1, l, this.brickStairBlock, stairMeta);
                        }
                     }

                     this.setBlockAndMetadata(world, soldiers, 9, l, this.brickBlock, this.brickMeta);
                  }

                  this.setBlockAndMetadata(world, soldiers, 10, l, this.brickRedBlock, this.brickRedMeta);
                  this.setBlockAndMetadata(world, soldiers, 11, l, this.fenceBlock, this.fenceMeta);
               }
            }
         }
      }

      for(soldiers = -1; soldiers <= 1; ++soldiers) {
         for(l = -1; l <= 1; ++l) {
            i2 = Math.abs(soldiers);
            k2 = Math.abs(l);
            if (i2 != 0 && k2 != 0) {
               this.setBlockAndMetadata(world, soldiers, 0, l, this.brickRedBlock, this.brickRedMeta);
            } else {
               this.setBlockAndMetadata(world, soldiers, 0, l, this.pillarBlock, this.pillarMeta);
            }
         }
      }

      if (this.enableDoor) {
         this.setBlockAndMetadata(world, 0, 0, -2, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, 0, 1, -2, this.doorBlock, 1);
         this.setBlockAndMetadata(world, 0, 2, -2, this.doorBlock, 8);
      }

      this.setBlockAndMetadata(world, -1, 3, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 1, 3, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -1, 3, 1, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 3, 1, Blocks.field_150478_aa, 4);
      this.placeWeaponRack(world, -1, 2, 0, 5, this.getEasterlingWeaponItem(random));
      this.placeArmorStand(world, 1, 1, 0, 1, (ItemStack[])null);

      for(soldiers = 1; soldiers <= 9; ++soldiers) {
         this.setBlockAndMetadata(world, 0, soldiers, 1, Blocks.field_150468_ap, 2);
      }

      this.setBlockAndMetadata(world, 0, 10, 1, this.trapdoorBlock, 9);
      this.setBlockAndMetadata(world, -1, 6, -1, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 0, 6, -1, this.plankSlabBlock, this.plankSlabMeta | 8);
      int[] var13 = new int[]{5, 7};
      l = var13.length;

      for(i2 = 0; i2 < l; ++i2) {
         k2 = var13[i2];
         this.setBlockAndMetadata(world, 0, k2, -1, this.bedBlock, 3);
         this.setBlockAndMetadata(world, -1, k2, -1, this.bedBlock, 11);
      }

      for(soldiers = 6; soldiers <= 9; ++soldiers) {
         this.setBlockAndMetadata(world, 1, soldiers, -1, Blocks.field_150468_ap, 3);
      }

      this.placeChest(world, random, 1, 5, -1, 3, LOTRChestContents.EASTERLING_TOWER);
      this.setBlockAndMetadata(world, -1, 8, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 8, 0, Blocks.field_150478_aa, 1);
      this.spawnItemFrame(world, -2, 7, 0, 1, this.getEasterlingFramedItem(random));
      this.spawnItemFrame(world, 2, 7, 0, 3, this.getEasterlingFramedItem(random));
      this.placeWallBanner(world, 0, 9, -2, this.bannerType, 2);
      this.setBlockAndMetadata(world, -3, 14, -3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -2, 13, -3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -1, 13, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 13, -3, this.roofSlabBlock, this.roofSlabMeta | 8);
      this.setBlockAndMetadata(world, 1, 13, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 2, 13, -3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 3, 14, -3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 3, 13, -2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 3, 13, -1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 3, 13, 0, this.roofSlabBlock, this.roofSlabMeta | 8);
      this.setBlockAndMetadata(world, 3, 13, 1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 3, 13, 2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 3, 14, 3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 2, 13, 3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 13, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 13, 3, this.roofSlabBlock, this.roofSlabMeta | 8);
      this.setBlockAndMetadata(world, -1, 13, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -2, 13, 3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -3, 14, 3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -3, 13, 2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -3, 13, 1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -3, 13, 0, this.roofSlabBlock, this.roofSlabMeta | 8);
      this.setBlockAndMetadata(world, -3, 13, -1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -3, 13, -2, this.roofStairBlock, 6);

      for(soldiers = -2; soldiers <= 2; ++soldiers) {
         this.setBlockAndMetadata(world, soldiers, 14, -2, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, soldiers, 14, 2, this.roofStairBlock, 3);
      }

      for(soldiers = -2; soldiers <= 2; ++soldiers) {
         this.setBlockAndMetadata(world, -2, 14, soldiers, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 2, 14, soldiers, this.roofStairBlock, 0);
      }

      for(soldiers = -1; soldiers <= 1; ++soldiers) {
         for(l = -1; l <= 1; ++l) {
            if (soldiers != 0 || l != 0) {
               this.setBlockAndMetadata(world, soldiers, 14, l, this.roofSlabBlock, this.roofSlabMeta | 8);
            }

            if (soldiers != 0 && l != 0) {
               this.setBlockAndMetadata(world, soldiers, 15, l, this.roofSlabBlock, this.roofSlabMeta);
            } else {
               this.setBlockAndMetadata(world, soldiers, 15, l, this.roofBlock, this.roofMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 16, 0, this.roofWallBlock, this.roofWallMeta);
      this.setBlockAndMetadata(world, 0, 17, 0, this.roofWallBlock, this.roofWallMeta);
      this.setBlockAndMetadata(world, -2, 12, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -1, 12, -2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 12, -2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 2, 12, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 12, 1, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -1, 12, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 12, 2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 2, 12, 1, Blocks.field_150478_aa, 4);
      if (this.frontLadder) {
         this.setBlockAndMetadata(world, 0, 11, -2, this.fenceGateBlock, 0);
         this.placeSideLadder(world, 0, 10, -3, 2);
      }

      if (this.backLadder) {
         this.setBlockAndMetadata(world, 0, 11, 2, this.fenceGateBlock, 2);
         this.placeSideLadder(world, 0, 10, 3, 3);
      }

      if (this.leftLadder) {
         this.setBlockAndMetadata(world, -2, 11, 0, this.fenceGateBlock, 3);
         this.placeSideLadder(world, -3, 10, 0, 5);
      }

      if (this.rightLadder) {
         this.setBlockAndMetadata(world, 2, 11, 0, this.fenceGateBlock, 1);
         this.placeSideLadder(world, 3, 10, 0, 4);
      }

      soldiers = 1 + random.nextInt(3);

      for(l = 0; l < soldiers; ++l) {
         LOTREntityEasterling soldier = random.nextInt(3) == 0 ? new LOTREntityEasterlingArcher(world) : new LOTREntityEasterlingWarrior(world);
         ((LOTREntityEasterling)soldier).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)soldier, world, 0, 1, 0, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
      respawner.setCheckRanges(16, -8, 8, 6);
      respawner.setSpawnRanges(3, -6, 6, 16);
      this.placeNPCRespawner(respawner, world, 0, 6, 0);
      return true;
   }

   private void placeSideLadder(World world, int i, int j, int k, int meta) {
      for(int j1 = j; !this.isOpaque(world, i, j1, k) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, i, j1, k, Blocks.field_150468_ap, meta);
      }

   }
}
