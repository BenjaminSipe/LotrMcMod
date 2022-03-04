package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityTauredainPyramidWraith;
import lotr.common.item.LOTRItemBanner;
import lotr.common.tileentity.LOTRTileEntityDartTrap;
import lotr.common.util.LOTRMazeGenerator;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenTauredainPyramid extends LOTRWorldGenStructureBase2 {
   public static int RADIUS = 60;
   private boolean isGolden;
   private boolean carson;

   public LOTRWorldGenTauredainPyramid(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int depth = 20;
      j -= depth - 1;
      this.setOriginAndRotation(world, i, j, k, rotation, this.usingPlayer != null ? RADIUS - depth : 0);
      this.isGolden = random.nextInt(20) == 0;
      this.carson = random.nextInt(200) == 0;
      int maze1R = 19;
      int maze1W = maze1R * 2 + 1;
      LOTRMazeGenerator maze1 = new LOTRMazeGenerator(maze1W, maze1W);
      maze1.setStart(maze1R + 0, maze1R + 4);
      int maze1CentreW = 3;

      int i2;
      for(int i1 = -maze1CentreW - 1; i1 <= maze1CentreW + 1; ++i1) {
         for(int k1 = -maze1CentreW - 1; k1 <= maze1CentreW + 1; ++k1) {
            i2 = Math.abs(i1);
            int k2 = Math.abs(k1);
            if (i2 <= maze1CentreW && k2 <= maze1CentreW) {
               maze1.clear(maze1R + i1, maze1R + k1);
            } else {
               maze1.exclude(maze1R + i1, maze1R + k1);
            }
         }
      }

      maze1.generate(random);
      maze1.selectOuterEndpoint(random);
      int[] maze1End = maze1.getEnd();
      int maze2R = 25;
      i2 = maze2R * 2 + 1;
      LOTRMazeGenerator maze2 = new LOTRMazeGenerator(i2, i2);
      maze2.setStart(maze1End[0] + (maze2.xSize - maze1.xSize), maze1End[1] + (maze2.zSize - maze1.zSize));
      maze2.generate(random);
      maze2.selectOuterEndpoint(random);
      int maze3R = 13;
      int maze3W = maze3R * 2 + 1;
      LOTRMazeGenerator maze3 = new LOTRMazeGenerator(maze3W, maze3W);
      maze3.setStart(maze3R + 0, maze3R + 2);
      int maze3CentreW = 1;

      int i1;
      int k1;
      int topHeight;
      for(int i1 = -maze3CentreW - 1; i1 <= maze3CentreW + 1; ++i1) {
         for(i1 = -maze3CentreW - 1; i1 <= maze3CentreW + 1; ++i1) {
            k1 = Math.abs(i1);
            topHeight = Math.abs(i1);
            if (k1 <= maze3CentreW && topHeight <= maze3CentreW) {
               maze3.clear(maze3R + i1, maze3R + 4 + i1);
            } else {
               maze3.exclude(maze3R + i1, maze3R + 4 + i1);
            }
         }
      }

      maze3.generate(random);
      maze3.selectOuterEndpoint(random);
      IInventory[] chests = new IInventory[4];

      for(i1 = 0; i1 < chests.length; ++i1) {
         chests[i1] = new InventoryBasic("drops", false, 27);
         LOTRChestContents itemPool = LOTRChestContents.TAUREDAIN_PYRAMID;
         topHeight = LOTRChestContents.getRandomItemAmount(itemPool, random);
         if (this.isGolden) {
            topHeight *= 3;
         }

         LOTRChestContents.fillInventory(chests[i1], random, itemPool, topHeight);
      }

      int stepY;
      if (this.carson) {
         IInventory[] var52 = chests;
         k1 = chests.length;

         for(topHeight = 0; topHeight < k1; ++topHeight) {
            IInventory chest = var52[topHeight];

            for(stepY = 0; stepY < chest.func_70302_i_(); ++stepY) {
               chest.func_70299_a(stepY, new ItemStack(Items.field_151015_O, 64));
            }
         }
      }

      if (this.restrictions) {
         for(i1 = -RADIUS; i1 <= RADIUS; ++i1) {
            for(k1 = -RADIUS; k1 <= RADIUS; ++k1) {
               topHeight = this.getTopBlock(world, i1, k1);
               Block block = this.getBlock(world, i1, topHeight - 1, k1);
               if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b && block != LOTRMod.mudGrass && block != LOTRMod.mud) {
                  return false;
               }
            }
         }
      }

      for(i1 = -RADIUS; i1 <= RADIUS; ++i1) {
         for(k1 = -RADIUS; k1 <= RADIUS; ++k1) {
            for(topHeight = 0; (this.getY(topHeight) >= this.originY || !this.isOpaque(world, i1, topHeight, k1)) && this.getY(topHeight) >= 0; --topHeight) {
               this.placeRandomBrick(world, random, i1, topHeight, k1);
               this.setGrassToDirt(world, i1, topHeight - 1, k1);
            }
         }
      }

      i1 = (RADIUS - 10) / 2;
      int var10000 = RADIUS - i1 * 2;
      topHeight = i1 * 2;

      int stepZ;
      int newX;
      int j1;
      int stepX;
      for(stepX = 0; stepX < i1; ++stepX) {
         for(stepY = stepX * 2; stepY <= stepX * 2 + 1; ++stepY) {
            stepZ = RADIUS - stepX * 2;

            for(newX = -stepZ; newX <= stepZ; ++newX) {
               for(j1 = -stepZ; j1 <= stepZ; ++j1) {
                  this.placeRandomBrick(world, random, newX, stepY, j1);
                  if ((Math.abs(newX) == stepZ - 1 || Math.abs(j1) == stepZ - 1) && random.nextInt(3) == 0) {
                     this.placeRandomBrick(world, random, newX, stepY + 1, j1);
                  }
               }
            }
         }
      }

      for(stepX = -2; stepX <= 2; ++stepX) {
         for(stepY = -2; stepY <= 2; ++stepY) {
            this.setBlockAndMetadata(world, stepX, topHeight, stepY, LOTRMod.brick4, 3);

            for(stepZ = topHeight + 1; stepZ <= topHeight + 6; ++stepZ) {
               if (Math.abs(stepX) == 2 && Math.abs(stepY) == 2) {
                  this.setBlockAndMetadata(world, stepX, stepZ, stepY, LOTRMod.pillar2, 12);
               } else {
                  this.setBlockAndMetadata(world, stepX, stepZ, stepY, LOTRMod.brick4, 4);
               }
            }

            this.setBlockAndMetadata(world, stepX, topHeight + 7, stepY, LOTRMod.brick4, 3);
         }
      }

      int[] var55 = new int[]{-10, 10};
      stepY = var55.length;

      int newZ;
      int i1;
      int k1;
      int maze3EndZ;
      for(stepZ = 0; stepZ < stepY; ++stepZ) {
         newX = var55[stepZ];
         int[] var56 = new int[]{-10, 10};
         newZ = var56.length;

         for(i1 = 0; i1 < newZ; ++i1) {
            k1 = var56[i1];
            this.setBlockAndMetadata(world, newX, topHeight, k1, LOTRMod.brick4, 3);

            for(maze3EndZ = topHeight + 1; maze3EndZ <= topHeight + 3; ++maze3EndZ) {
               this.setBlockAndMetadata(world, newX, maze3EndZ, k1, LOTRMod.pillar2, 12);
            }

            this.setBlockAndMetadata(world, newX, topHeight + 4, k1, LOTRMod.brick4, 3);
         }
      }

      this.generateMaze(world, random, 0, topHeight - 13, 0, maze1, 5, 1, false);
      stepX = 0;
      stepY = topHeight - 1;

      for(stepZ = 3; stepY >= topHeight - 13; stepZ = newZ) {
         newX = stepX;
         j1 = stepY;
         newZ = stepZ;
         if (stepX == -3 && stepZ == -3) {
            this.placeRandomBrick(world, random, stepX, stepY, stepZ);
            newZ = stepZ + 1;
         } else if (stepX == -3 && stepZ == 3) {
            this.placeRandomBrick(world, random, stepX, stepY, stepZ);
            newX = stepX + 1;
         } else if (stepX == 3 && stepZ == 3) {
            this.placeRandomBrick(world, random, stepX, stepY, stepZ);
            newZ = stepZ - 1;
         } else if (stepX == 3 && stepZ == -3) {
            this.placeRandomBrick(world, random, stepX, stepY, stepZ);
            newX = stepX - 1;
         } else if (stepZ == -3) {
            this.placeRandomStairs(world, random, stepX, stepY, stepZ, 1);
            newX = stepX - 1;
            j1 = stepY - 1;
         } else if (stepZ == 3) {
            this.placeRandomStairs(world, random, stepX, stepY, stepZ, 0);
            newX = stepX + 1;
            j1 = stepY - 1;
         } else if (stepX == -3) {
            this.placeRandomStairs(world, random, stepX, stepY, stepZ, 3);
            newZ = stepZ + 1;
            j1 = stepY - 1;
         } else if (stepX == 3) {
            this.placeRandomStairs(world, random, stepX, stepY, stepZ, 2);
            newZ = stepZ - 1;
            j1 = stepY - 1;
         }

         for(i1 = 1; i1 <= 3; ++i1) {
            this.setAir(world, stepX, stepY + i1, stepZ);
         }

         stepX = newX;
         stepY = j1;
      }

      this.setAir(world, stepX, stepY + 3, stepZ);

      for(newX = topHeight - 18 + 2; newX < topHeight - 13; ++newX) {
         this.setAir(world, maze1End[0] - (maze1.xSize - 1) / 2, newX, maze1End[1] - (maze1.zSize - 1) / 2);
      }

      this.generateMaze(world, random, 0, topHeight - 18, 0, maze2, 2, 1, false);
      int[] maze2End = maze2.getEnd();

      for(j1 = topHeight - 22; j1 < topHeight - 18; ++j1) {
         this.setAir(world, maze2End[0] - (maze2.xSize - 1) / 2, j1, maze2End[1] - (maze2.zSize - 1) / 2);
      }

      int chamberRMin = 22;
      int chamberRMax = 26;

      int roomBottom;
      int roomFloor;
      int j1;
      for(i1 = -chamberRMax - 1; i1 <= chamberRMax + 1; ++i1) {
         for(k1 = -chamberRMax - 1; k1 <= chamberRMax + 1; ++k1) {
            maze3EndZ = Math.abs(i1);
            roomBottom = Math.abs(k1);
            if (maze3EndZ == chamberRMax + 1 || roomBottom == chamberRMax + 1) {
               this.setBlockAndMetadata(world, i1, topHeight - 25, k1, LOTRMod.brick4, 4);
               this.setBlockAndMetadata(world, i1, topHeight - 24, k1, LOTRMod.brick4, 3);
            }

            if (maze3EndZ <= chamberRMax && roomBottom <= chamberRMax && (maze3EndZ >= chamberRMin || roomBottom >= chamberRMin)) {
               for(roomFloor = topHeight - 26; roomFloor < topHeight - 22; ++roomFloor) {
                  this.setAir(world, i1, roomFloor, k1);
               }

               if (maze3EndZ == chamberRMax && roomBottom == chamberRMax) {
                  this.setBlockAndMetadata(world, i1, topHeight - 26, k1, LOTRMod.hearth, 0);
                  this.setBlockAndMetadata(world, i1, topHeight - 25, k1, Blocks.field_150480_ab, 0);
               } else if (maze3EndZ >= chamberRMax - 1 && roomBottom >= chamberRMax - 1) {
                  this.setBlockAndMetadata(world, i1, topHeight - 26, k1, LOTRMod.brick4, 3);
               } else if (maze3EndZ >= chamberRMax - 2 && roomBottom >= chamberRMax - 2) {
                  this.setBlockAndMetadata(world, i1, topHeight - 26, k1, LOTRMod.slabSingle8, 4);
               }

               if (maze3EndZ == chamberRMax && roomBottom % 6 == 0 && roomBottom < chamberRMax - 4 || roomBottom == chamberRMax && maze3EndZ % 6 == 0 && maze3EndZ < chamberRMax - 4) {
                  Block pillarBlock = LOTRMod.pillar;
                  int pillarMeta = 14;
                  if (this.isGolden) {
                     pillarBlock = LOTRMod.pillar2;
                     pillarMeta = 11;
                  }

                  for(j1 = topHeight - 26; j1 < topHeight - 22; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, pillarBlock, pillarMeta);
                  }
               }
            }
         }
      }

      this.generateMaze(world, random, 0, topHeight - 35, 0, maze3, 4, 3, true);
      int[] maze3End = maze3.getEnd();
      k1 = maze3End[0] - (maze3.xSize - 1) / 2;
      maze3EndZ = maze3End[1] - (maze3.zSize - 1) / 2;
      k1 *= 3;
      maze3EndZ *= 3;

      int i1;
      int j1;
      for(roomBottom = 0; roomBottom <= 9; ++roomBottom) {
         for(roomFloor = -1; roomFloor <= 1; ++roomFloor) {
            j1 = topHeight - 36 + roomBottom;
            j1 = 13 + roomBottom;
            if (roomBottom > 0) {
               this.placeRandomStairs(world, random, roomFloor, j1, j1, 2);
            }

            for(i1 = 1; i1 <= 4; ++i1) {
               this.setAir(world, roomFloor, j1 + i1, j1);
            }

            if (roomBottom < 9) {
               this.placeRandomStairs(world, random, roomFloor, j1 + 5, j1, 7);
            }

            if (roomBottom <= 3) {
               for(i1 = 1; i1 < roomBottom; ++i1) {
                  this.placeRandomBrick(world, random, roomFloor, j1 - roomBottom + i1, j1);
               }
            }
         }
      }

      roomBottom = topHeight - 49;
      roomFloor = topHeight - 47;
      j1 = topHeight - 36;
      int roomPillarEdge = 32;

      int k1;
      int i2;
      int stepPlaceX;
      int actingRoomTop;
      for(i1 = -37; i1 <= 37; ++i1) {
         for(k1 = -37; k1 <= 37; ++k1) {
            i2 = Math.abs(i1);
            stepPlaceX = Math.abs(k1);
            actingRoomTop = j1;
            if (i2 != roomPillarEdge && stepPlaceX != roomPillarEdge) {
               actingRoomTop = j1 - random.nextInt(2);
            }

            int i3;
            for(i3 = roomFloor + 1; i3 < actingRoomTop; ++i3) {
               this.setAir(world, i1, i3, k1);
            }

            if (i2 <= roomPillarEdge && stepPlaceX <= roomPillarEdge) {
               int k3;
               int j1;
               if (i2 != roomPillarEdge && stepPlaceX != roomPillarEdge) {
                  if (i2 <= 10 && stepPlaceX <= 10) {
                     i3 = Math.max(i2, stepPlaceX);
                     k3 = (10 - Math.max(i3, 3)) / 2;

                     for(j1 = roomBottom + 1; j1 <= roomFloor; ++j1) {
                        this.placeRandomBrick(world, random, i1, j1, k1);
                     }

                     j1 = roomFloor + 1;
                     int lvlMax = j1 + k3;

                     for(int j1 = j1; j1 <= lvlMax; ++j1) {
                        this.placeRandomBrick(world, random, i1, j1, k1);
                     }

                     if (i3 > 3 && i3 % 2 == 0) {
                        this.setBlockAndMetadata(world, i1, lvlMax, k1, LOTRMod.brick4, 4);
                        if (i2 == stepPlaceX) {
                           this.setBlockAndMetadata(world, i1, lvlMax, k1, LOTRMod.pillar2, 11);
                           this.setBlockAndMetadata(world, i1, lvlMax + 1, k1, LOTRMod.pillar2, 11);
                           this.setBlockAndMetadata(world, i1, lvlMax + 2, k1, LOTRMod.tauredainDoubleTorch, 0);
                           this.setBlockAndMetadata(world, i1, lvlMax + 3, k1, LOTRMod.tauredainDoubleTorch, 1);
                        }
                     }

                     if (i3 > 3 && (i2 <= 1 || stepPlaceX <= 1)) {
                        if (i3 % 2 == 0) {
                           this.setBlockAndMetadata(world, i1, lvlMax, k1, LOTRMod.slabSingle8, 3);
                        } else {
                           this.setBlockAndMetadata(world, i1, lvlMax, k1, LOTRMod.brick4, 3);
                        }
                     }
                  } else {
                     for(i3 = roomBottom + 1; i3 <= roomFloor; ++i3) {
                        this.setBlockAndMetadata(world, i1, i3, k1, Blocks.field_150353_l, 0);
                     }

                     if (random.nextInt(300) == 0) {
                        this.setBlockAndMetadata(world, i1, actingRoomTop, k1, Blocks.field_150356_k, 0);
                     }

                     if (i2 != roomPillarEdge - 1 && stepPlaceX != roomPillarEdge - 1) {
                        if (i2 != roomPillarEdge - 2 && stepPlaceX != roomPillarEdge - 2) {
                           if (i2 != roomPillarEdge - 3 && stepPlaceX != roomPillarEdge - 3) {
                              if (random.nextInt(16) == 0) {
                                 this.placeRandomBrick(world, random, i1, roomFloor, k1);
                              }

                              if (random.nextInt(200) == 0) {
                                 Block pillarBlock = LOTRMod.pillar;
                                 int pillarMeta = 14;
                                 if (this.isGolden) {
                                    pillarBlock = LOTRMod.pillar2;
                                    pillarMeta = 11;
                                 }

                                 if (random.nextBoolean()) {
                                    pillarBlock = LOTRMod.pillar2;
                                    pillarMeta = 12;
                                 }

                                 for(j1 = roomBottom + 1; j1 < actingRoomTop; ++j1) {
                                    this.setBlockAndMetadata(world, i1, j1, k1, pillarBlock, pillarMeta);
                                 }
                              }
                           } else if (random.nextInt(4) == 0) {
                              this.setBlockAndMetadata(world, i1, roomFloor, k1, Blocks.field_150343_Z, 0);
                           }
                        } else if (random.nextInt(2) == 0) {
                           this.setBlockAndMetadata(world, i1, roomFloor, k1, Blocks.field_150343_Z, 0);
                        }
                     } else if (random.nextInt(4) > 0) {
                        this.setBlockAndMetadata(world, i1, roomFloor, k1, Blocks.field_150343_Z, 0);
                     }
                  }
               } else {
                  for(i3 = roomBottom + 1; i3 <= roomFloor + 1; ++i3) {
                     this.setBlockAndMetadata(world, i1, i3, k1, LOTRMod.brick4, 4);
                  }

                  this.placeRandomBrick(world, random, i1, actingRoomTop - 1, k1);
                  if (this.isGolden) {
                     this.setBlockAndMetadata(world, i1, actingRoomTop - 2, k1, LOTRMod.pillar2, 11);
                  } else {
                     this.setBlockAndMetadata(world, i1, actingRoomTop - 2, k1, LOTRMod.pillar, 14);
                  }

                  i3 = IntMath.mod(i1, 4);
                  k3 = IntMath.mod(k1, 4);
                  if (i2 == roomPillarEdge && k3 == 0 || stepPlaceX == roomPillarEdge && i3 == 0) {
                     for(j1 = roomFloor + 2; j1 <= actingRoomTop - 2; ++j1) {
                        if (this.isGolden) {
                           this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.pillar2, 11);
                        } else {
                           this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.pillar, 14);
                        }
                     }
                  }

                  if (i2 == roomPillarEdge) {
                     if (k3 == 1) {
                        this.placeRandomStairs(world, random, i1, actingRoomTop - 3, k1, 7);
                     } else if (k3 == 3) {
                        this.placeRandomStairs(world, random, i1, actingRoomTop - 3, k1, 6);
                     }
                  } else if (stepPlaceX == roomPillarEdge) {
                     if (i3 == 1) {
                        this.placeRandomStairs(world, random, i1, actingRoomTop - 3, k1, 4);
                     } else if (i3 == 3) {
                        this.placeRandomStairs(world, random, i1, actingRoomTop - 3, k1, 5);
                     }
                  }
               }
            } else {
               for(i3 = roomBottom + 1; i3 <= roomFloor + 1; ++i3) {
                  this.placeRandomBrick(world, random, i1, i3, k1);
               }
            }
         }
      }

      this.placePyramidBanner(world, 0, roomFloor + 6, 0);
      this.placeSpawnerChest(world, random, -1, roomFloor + 5, 0, LOTRMod.spawnerChestStone, 5, LOTREntityTauredainPyramidWraith.class, (LOTRChestContents)null);
      this.putInventoryInChest(world, -1, roomFloor + 5, 0, chests[0]);
      this.placeSpawnerChest(world, random, 1, roomFloor + 5, 0, LOTRMod.spawnerChestStone, 4, LOTREntityTauredainPyramidWraith.class, (LOTRChestContents)null);
      this.putInventoryInChest(world, 1, roomFloor + 5, 0, chests[1]);
      this.placeSpawnerChest(world, random, 0, roomFloor + 5, -1, LOTRMod.spawnerChestStone, 2, LOTREntityTauredainPyramidWraith.class, (LOTRChestContents)null);
      this.putInventoryInChest(world, 0, roomFloor + 5, -1, chests[2]);
      this.placeSpawnerChest(world, random, 0, roomFloor + 5, 1, LOTRMod.spawnerChestStone, 3, LOTREntityTauredainPyramidWraith.class, (LOTRChestContents)null);
      this.putInventoryInChest(world, 0, roomFloor + 5, 1, chests[3]);
      stepX = 1;
      stepY = topHeight - 36;
      stepZ = 0;

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            this.setAir(world, k1 + i1, stepY, maze3EndZ + k1);
            this.setAir(world, k1 + i1, stepY - 1, maze3EndZ + k1);
         }
      }

      this.placeRandomBrick(world, random, k1 + 1, stepY, maze3EndZ + 1);

      while(stepY > roomFloor + 1) {
         i1 = stepX;
         k1 = stepY;
         i2 = stepZ;
         stepPlaceX = stepX + k1;
         actingRoomTop = stepZ + maze3EndZ;
         if (stepX == -1 && stepZ == -1) {
            this.placeRandomBrick(world, random, stepPlaceX, stepY, actingRoomTop);
            i2 = stepZ + 1;
         } else if (stepX == -1 && stepZ == 1) {
            this.placeRandomBrick(world, random, stepPlaceX, stepY, actingRoomTop);
            i1 = stepX + 1;
         } else if (stepX == 1 && stepZ == 1) {
            this.placeRandomBrick(world, random, stepPlaceX, stepY, actingRoomTop);
            i2 = stepZ - 1;
         } else if (stepX == 1 && stepZ == -1) {
            this.placeRandomBrick(world, random, stepPlaceX, stepY, actingRoomTop);
            i1 = stepX - 1;
         } else if (stepZ == -1) {
            this.placeRandomStairs(world, random, stepPlaceX, stepY, actingRoomTop, 1);
            this.placeRandomStairs(world, random, stepPlaceX, stepY - 1, actingRoomTop, 4);
            i1 = stepX - 1;
            k1 = stepY - 1;
         } else if (stepZ == 1) {
            this.placeRandomStairs(world, random, stepPlaceX, stepY, actingRoomTop, 0);
            this.placeRandomStairs(world, random, stepPlaceX, stepY - 1, actingRoomTop, 5);
            i1 = stepX + 1;
            k1 = stepY - 1;
         } else if (stepX == -1) {
            this.placeRandomStairs(world, random, stepPlaceX, stepY, actingRoomTop, 3);
            this.placeRandomStairs(world, random, stepPlaceX, stepY - 1, actingRoomTop, 6);
            i2 = stepZ + 1;
            k1 = stepY - 1;
         } else if (stepX == 1) {
            this.placeRandomStairs(world, random, stepPlaceX, stepY, actingRoomTop, 2);
            this.placeRandomStairs(world, random, stepPlaceX, stepY - 1, actingRoomTop, 7);
            i2 = stepZ - 1;
            k1 = stepY - 1;
         }

         stepX = i1;
         stepY = k1;
         stepZ = i2;
      }

      for(i1 = roomFloor + 1; i1 <= topHeight - 32; ++i1) {
         this.setBlockAndMetadata(world, k1, i1, maze3EndZ, LOTRMod.pillar2, 12);
      }

      this.setBlockAndMetadata(world, k1 + 1, topHeight - 33, maze3EndZ, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, k1 - 1, topHeight - 33, maze3EndZ, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, k1, topHeight - 33, maze3EndZ + 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, k1, topHeight - 33, maze3EndZ - 1, Blocks.field_150478_aa, 4);
      return true;
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (this.isGolden) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 3);
      } else {
         if (random.nextBoolean()) {
            if (random.nextBoolean()) {
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 1);
            } else {
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 2);
            }
         } else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 0);
         }

      }
   }

   private void placeRandomWall(World world, Random random, int i, int j, int k) {
      if (this.isGolden) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 3);
      } else {
         if (random.nextBoolean()) {
            if (random.nextBoolean()) {
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 1);
            } else {
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 2);
            }
         } else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 0);
         }

      }
   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
      if (this.isGolden) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsTauredainBrickGold, meta);
      } else {
         if (random.nextBoolean()) {
            if (random.nextBoolean()) {
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsTauredainBrickMossy, meta);
            } else {
               this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsTauredainBrickCracked, meta);
            }
         } else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsTauredainBrick, meta);
         }

      }
   }

   private void placePyramidBanner(World world, int i, int j, int k) {
      this.setBlockAndMetadata(world, i, j - 1, k, Blocks.field_150340_R, 0);

      for(int j1 = 0; j1 <= 3; ++j1) {
         this.setAir(world, i, j + j1, k);
      }

      this.placeBanner(world, i, j, k, LOTRItemBanner.BannerType.TAUREDAIN, 0, true, 64);
   }

   private void generateMaze(World world, Random random, int i, int j, int k, LOTRMazeGenerator maze, int height, int scale, boolean traps) {
      int xr = (maze.xSize - 1) / 2;
      int zr = (maze.zSize - 1) / 2;
      i -= xr;
      k -= zr;
      int scaleR = (scale - 1) / 2;

      for(int pass = 0; pass <= 1; ++pass) {
         for(int i1 = 0; i1 < maze.xSize; ++i1) {
            for(int k1 = 0; k1 < maze.zSize; ++k1) {
               if (pass == 0) {
                  boolean path = maze.isPath(i1, k1);
                  if (path) {
                     for(int i2 = 0; i2 < scale; ++i2) {
                        for(int k2 = 0; k2 < scale; ++k2) {
                           for(int j1 = 0; j1 < height; ++j1) {
                              this.setAir(world, (i + i1) * scale - scaleR + i2, j + j1, (k + k1) * scale - scaleR + k2);
                           }
                        }
                     }
                  }
               }

               if (pass == 1) {
                  if (maze.isDeadEnd(i1, k1) && random.nextInt(3) == 0) {
                     this.setBlockAndMetadata(world, (i + i1) * scale - scaleR, j + 1, (k + k1) * scale - scaleR, Blocks.field_150478_aa, 0);
                  }

                  if (traps && !maze.isPath(i1, k1) && random.nextInt(4) == 0) {
                     List validDirs = new ArrayList();
                     if (i1 - 1 >= 0 && maze.isPath(i1 - 1, k1)) {
                        validDirs.add(ForgeDirection.WEST);
                     }

                     if (i1 + 1 < maze.xSize && maze.isPath(i1 + 1, k1)) {
                        validDirs.add(ForgeDirection.EAST);
                     }

                     if (k1 - 1 >= 0 && maze.isPath(i1, k1 - 1)) {
                        validDirs.add(ForgeDirection.NORTH);
                     }

                     if (k1 + 1 < maze.zSize && maze.isPath(i1, k1 + 1)) {
                        validDirs.add(ForgeDirection.SOUTH);
                     }

                     if (!validDirs.isEmpty()) {
                        ForgeDirection dir = (ForgeDirection)validDirs.get(random.nextInt(validDirs.size()));
                        this.placeDartTrap(world, random, (i + i1) * scale + dir.offsetX * scaleR, j + 0, (k + k1) * scale + dir.offsetZ * scaleR, dir.ordinal());
                     }
                  }
               }
            }
         }
      }

   }

   private void placeDartTrap(World world, Random random, int i, int j, int k, int meta) {
      Block dartTrapBlock = LOTRMod.tauredainDartTrap;
      if (this.isGolden) {
         dartTrapBlock = LOTRMod.tauredainDartTrapGold;
      }

      this.setBlockAndMetadata(world, i, j, k, dartTrapBlock, meta);
      TileEntity tileentity = this.getTileEntity(world, i, j, k);
      if (tileentity instanceof LOTRTileEntityDartTrap) {
         LOTRTileEntityDartTrap trap = (LOTRTileEntityDartTrap)tileentity;

         for(int l = 0; l < trap.func_70302_i_(); ++l) {
            if (random.nextBoolean()) {
               int darts = MathHelper.func_76136_a(random, 2, 6);
               if (random.nextBoolean()) {
                  trap.func_70299_a(l, new ItemStack(LOTRMod.tauredainDartPoisoned, darts));
               } else {
                  trap.func_70299_a(l, new ItemStack(LOTRMod.tauredainDart, darts));
               }
            }
         }
      }

   }
}
