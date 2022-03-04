package lotr.common.world.structure2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorFarmhand;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class LOTRWorldGenGondorVillageFarm extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorVillageFarm(boolean flag) {
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
               this.setBlockAndMetadata(world, i1, j1, k1, this.rockBlock, this.rockMeta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 10; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (i2 == 5 && k2 == 5) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.rockBlock, this.rockMeta);
               this.setBlockAndMetadata(world, i1, 2, k1, this.rockSlabBlock, this.rockSlabMeta);
            } else if (i2 != 5 && k2 != 5) {
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150349_c, 0);
            } else {
               this.setBlockAndMetadata(world, i1, 1, k1, this.rockWallBlock, this.rockWallMeta);
               if (i2 == 3 || k2 == 3) {
                  this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150478_aa, 5);
               }

               if (i2 == 0 || k2 == 0) {
                  this.setAir(world, i1, 1, k1);
               }
            }
         }
      }

      return true;
   }

   public static class Tree extends LOTRWorldGenGondorVillageFarm {
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
                     this.setBlockAndMetadata(world, i1, 2, k1, this.rockWallBlock, this.rockWallMeta);
                     this.setBlockAndMetadata(world, i1, 3, k1, Blocks.field_150362_t, 4);
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
         treeList.add(LOTRTreeType.OAK);
         treeList.add(LOTRTreeType.OAK_LARGE);
         treeList.add(LOTRTreeType.BIRCH);
         treeList.add(LOTRTreeType.BIRCH_TALL);
         treeList.add(LOTRTreeType.BIRCH_LARGE);
         treeList.add(LOTRTreeType.BEECH);
         treeList.add(LOTRTreeType.BEECH_LARGE);
         treeList.add(LOTRTreeType.LEBETHRON);
         treeList.add(LOTRTreeType.LEBETHRON_LARGE);
         treeList.add(LOTRTreeType.CEDAR);
         treeList.add(LOTRTreeType.APPLE);
         treeList.add(LOTRTreeType.PEAR);
         treeList.add(LOTRTreeType.PLUM);
         treeList.add(LOTRTreeType.ALMOND);
         treeList.add(LOTRTreeType.OLIVE);
         return (LOTRTreeType)treeList.get(random.nextInt(treeList.size()));
      }
   }

   public static class Animals extends LOTRWorldGenGondorVillageFarm {
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
               EntityCreature animal = LOTRWorldGenGondorBarn.getRandomAnimal(world, random);
               j2 = 3 * (random.nextBoolean() ? 1 : -1);
               j3 = 3 * (random.nextBoolean() ? 1 : -1);
               this.spawnNPCAndSetHome(animal, world, j2, 1, j3, 0);
               animal.func_110177_bN();
            }

            return true;
         }
      }
   }

   public static class Crops extends LOTRWorldGenGondorVillageFarm {
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
                        this.setBlockAndMetadata(world, i1, 1, k1, this.rockBlock, this.rockMeta);
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
               LOTREntityGondorFarmhand farmhand = new LOTREntityGondorFarmhand(world);
               this.spawnNPCAndSetHome(farmhand, world, 0, 1, -1, 8);
               farmhand.seedsItem = this.seedItem;
            }

            return true;
         }
      }
   }
}
