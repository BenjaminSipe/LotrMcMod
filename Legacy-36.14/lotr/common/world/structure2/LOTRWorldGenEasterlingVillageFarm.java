package lotr.common.world.structure2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEasterlingFarmer;
import lotr.common.entity.npc.LOTREntityEasterlingFarmhand;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class LOTRWorldGenEasterlingVillageFarm extends LOTRWorldGenEasterlingStructure {
   public LOTRWorldGenEasterlingVillageFarm(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -5; i2 <= 5; ++i2) {
            for(k2 = -5; k2 <= 5; ++k2) {
               j1 = this.getTopBlock(world, i2, k2) - 1;
               if (!this.isSurface(world, i2, j1, k2)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 4) {
                  return false;
               }
            }
         }
      }

      if (this.restrictions) {
         i1 = 0;

         for(k1 = -6; k1 <= 6; ++k1) {
            for(i2 = -6; i2 <= 6; ++i2) {
               k2 = Math.abs(k1);
               j1 = Math.abs(i2);
               if (k2 == 6 && j1 == 0 || j1 == 6 && k2 == 0) {
                  int j1 = this.getTopBlock(world, k1, i2) - 1;
                  if (this.isSurface(world, k1, j1, i2) && j1 > i1) {
                     i1 = j1;
                  }
               }
            }
         }

         this.originY = this.getY(i1);
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         for(k1 = -5; k1 <= 5; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);

            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 10; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (i2 == 5 && k2 == 5) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, 2, k1, this.brickSlabBlock, this.brickSlabMeta);
            } else if (i2 != 5 && k2 != 5) {
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150349_c, 0);
            } else {
               this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
               if (i2 == 2 || k2 == 2) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, i1, 2, k1, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, i1, 3, k1, this.brickWallBlock, this.brickWallMeta);
                  this.setBlockAndMetadata(world, i1, 4, k1, Blocks.field_150478_aa, 5);
               }

               if (i2 == 0 || k2 == 0) {
                  this.setAir(world, i1, 1, k1);
               }
            }
         }
      }

      return true;
   }

   public static class Tree extends LOTRWorldGenEasterlingVillageFarm {
      public Tree(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
         } else {
            int i1;
            int k1;
            for(i1 = -5; i1 <= 5; ++i1) {
               for(k1 = -5; k1 <= 5; ++k1) {
                  int i2 = Math.abs(i1);
                  int k2 = Math.abs(k1);
                  if (i2 == 5 && k2 == 5) {
                     this.setBlockAndMetadata(world, i1, 2, k1, this.brickWallBlock, this.brickWallMeta);
                     this.setBlockAndMetadata(world, i1, 3, k1, LOTRMod.leaves6, 6);
                  }
               }
            }

            for(i1 = 0; i1 < 16; ++i1) {
               LOTRTreeType tree = getRandomTree(random);
               WorldGenerator treeGen = tree.create(this.notifyChanges, random);
               if (treeGen != null) {
                  int i1 = 0;
                  int j1 = 1;
                  int k1 = 0;
                  if (treeGen.func_76484_a(world, random, this.getX(i1, k1), this.getY(j1), this.getZ(i1, k1))) {
                     break;
                  }
               }
            }

            for(i1 = -4; i1 <= 4; ++i1) {
               for(k1 = -4; k1 <= 4; ++k1) {
                  int j1 = 1;
                  if (!this.isOpaque(world, i1, j1, k1) && random.nextInt(8) == 0) {
                     this.plantFlower(world, random, i1, j1, k1);
                  }
               }
            }

            return true;
         }
      }

      public static LOTRTreeType getRandomTree(Random random) {
         List treeList = new ArrayList();
         treeList.add(LOTRTreeType.BEECH);
         treeList.add(LOTRTreeType.BEECH_LARGE);
         treeList.add(LOTRTreeType.MAPLE);
         treeList.add(LOTRTreeType.MAPLE_LARGE);
         treeList.add(LOTRTreeType.CYPRESS);
         treeList.add(LOTRTreeType.ALMOND);
         treeList.add(LOTRTreeType.OLIVE);
         treeList.add(LOTRTreeType.DATE_PALM);
         treeList.add(LOTRTreeType.POMEGRANATE);
         return (LOTRTreeType)treeList.get(random.nextInt(treeList.size()));
      }
   }

   public static class Animals extends LOTRWorldGenEasterlingVillageFarm {
      public Animals(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
         } else {
            int i1;
            for(i1 = -1; i1 <= 1; ++i1) {
               this.setBlockAndMetadata(world, i1, 1, -5, this.fenceGateBlock, 0);
               this.setBlockAndMetadata(world, i1, 1, 5, this.fenceGateBlock, 2);
            }

            for(i1 = -1; i1 <= 1; ++i1) {
               this.setBlockAndMetadata(world, -5, 1, i1, this.fenceGateBlock, 1);
               this.setBlockAndMetadata(world, 5, 1, i1, this.fenceGateBlock, 3);
            }

            int k1;
            int j2;
            int j3;
            for(i1 = -1; i1 <= 1; ++i1) {
               for(k1 = -1; k1 <= 1; ++k1) {
                  if (random.nextInt(3) == 0) {
                     int j1 = 1;
                     j2 = 1;
                     if (i1 == 0 && k1 == 0 && random.nextBoolean()) {
                        ++j2;
                     }

                     for(j3 = j1; j3 <= j2; ++j3) {
                        this.setBlockAndMetadata(world, i1, j3, k1, Blocks.field_150407_cf, 0);
                     }
                  }
               }
            }

            i1 = 4 + random.nextInt(5);

            for(k1 = 0; k1 < i1; ++k1) {
               EntityCreature animal = getRandomAnimal(world, random);
               j2 = 3 * (random.nextBoolean() ? 1 : -1);
               j3 = 3 * (random.nextBoolean() ? 1 : -1);
               this.spawnNPCAndSetHome(animal, world, j2, 1, j3, 0);
               animal.func_110177_bN();
            }

            return true;
         }
      }

      private static EntityAnimal getRandomAnimal(World world, Random random) {
         int animal = random.nextInt(4);
         if (animal == 0) {
            return new EntityCow(world);
         } else if (animal == 1) {
            return new EntityPig(world);
         } else if (animal == 2) {
            return new EntitySheep(world);
         } else {
            return animal == 3 ? new EntityChicken(world) : null;
         }
      }
   }

   public static class Crops extends LOTRWorldGenEasterlingVillageFarm {
      public Crops(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
         } else {
            int i1;
            int k1;
            for(i1 = -4; i1 <= 4; ++i1) {
               for(k1 = -4; k1 <= 4; ++k1) {
                  int i2 = Math.abs(i1);
                  int k2 = Math.abs(k1);
                  if (i2 <= 2 && k2 <= 2) {
                     if (i2 == 0 && k2 == 0) {
                        this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150355_j, 0);
                        this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                        this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150407_cf, 0);
                        this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
                        this.setBlockAndMetadata(world, i1, 4, k1, Blocks.field_150407_cf, 0);
                        this.setBlockAndMetadata(world, i1, 5, k1, Blocks.field_150423_aK, 2);
                     } else {
                        this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150458_ak, 7);
                        this.setBlockAndMetadata(world, i1, 1, k1, this.cropBlock, this.cropMeta);
                     }
                  } else {
                     this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
                  }
               }
            }

            this.setBlockAndMetadata(world, 0, 1, -5, this.fenceGateBlock, 0);
            this.setBlockAndMetadata(world, 0, 1, 5, this.fenceGateBlock, 2);
            this.setBlockAndMetadata(world, -5, 1, 0, this.fenceGateBlock, 1);
            this.setBlockAndMetadata(world, 5, 1, 0, this.fenceGateBlock, 3);
            i1 = 1 + random.nextInt(2);

            for(k1 = 0; k1 < i1; ++k1) {
               LOTREntityEasterlingFarmhand farmhand = new LOTREntityEasterlingFarmhand(world);
               this.spawnNPCAndSetHome(farmhand, world, 0, 1, -1, 8);
               farmhand.seedsItem = this.seedItem;
            }

            if (random.nextInt(3) == 0) {
               LOTREntityEasterlingFarmer farmer = new LOTREntityEasterlingFarmer(world);
               this.spawnNPCAndSetHome(farmer, world, 0, 1, -1, 8);
            }

            return true;
         }
      }
   }
}
