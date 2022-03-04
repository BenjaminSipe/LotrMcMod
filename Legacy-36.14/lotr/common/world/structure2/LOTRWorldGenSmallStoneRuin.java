package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenSmallStoneRuin extends LOTRWorldGenStructureBase2 {
   private Block brickBlock;
   private int brickMeta;
   private Block plankBlock;
   private int plankMeta;
   private Block plankSlabBlock;
   private int plankSlabMeta;
   private Block woodBeamBlock;
   private int woodBeamMeta;
   private Block barsBlock;

   public LOTRWorldGenSmallStoneRuin(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      LOTRWorldGenSmallStoneRuin.RuinType ruinType = LOTRWorldGenSmallStoneRuin.RuinType.getRandomType(random);
      this.setOriginAndRotation(world, i, j, k, rotation, ruinType.centreShift);
      int radius = ruinType.checkRadius;
      int i1;
      int k1;
      int i1;
      int k1;
      int i1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i1 = -radius; i1 <= radius; ++i1) {
            for(k1 = -radius; k1 <= radius; ++k1) {
               if (!this.isSuitableSpawnBlock(world, i1, k1)) {
                  return false;
               }

               i1 = this.getTopBlock(world, i1, k1) - 1;
               if (i1 < i1) {
                  i1 = i1;
               }

               if (i1 > k1) {
                  k1 = i1;
               }

               if (k1 - i1 > 7) {
                  return false;
               }
            }
         }
      }

      if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.COLUMN) {
         this.brickBlock = Blocks.field_150417_aV;
         this.brickMeta = 0;
         this.layFoundation(world, 0, 0, 0, this.brickBlock, this.brickMeta);
         this.layFoundation(world, -1, 0, 0, this.brickBlock, this.brickMeta);
         this.layFoundation(world, 1, 0, 0, this.brickBlock, this.brickMeta);
         this.layFoundation(world, 0, 0, -1, this.brickBlock, this.brickMeta);
         this.layFoundation(world, 0, 0, 1, this.brickBlock, this.brickMeta);
         i1 = 3 + random.nextInt(3);

         for(k1 = 1; k1 <= i1; ++k1) {
            if (random.nextInt(3) == 0) {
               this.setBlockAndMetadata(world, 0, k1, 0, Blocks.field_150417_aV, 3);
            } else {
               this.setBlockAndMetadata(world, 0, k1, 0, this.brickBlock, this.brickMeta);
            }
         }

         this.setBlockAndMetadata(world, -1, 1, 0, Blocks.field_150390_bg, 1);
         this.setBlockAndMetadata(world, 1, 1, 0, Blocks.field_150390_bg, 0);
         this.setBlockAndMetadata(world, 0, 1, -1, Blocks.field_150390_bg, 2);
         this.setBlockAndMetadata(world, 0, 1, 1, Blocks.field_150390_bg, 3);
      } else {
         int k1;
         if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.ROOM) {
            for(i1 = -2; i1 <= 2; ++i1) {
               for(k1 = -2; k1 <= 2; ++k1) {
                  i1 = Math.abs(i1);
                  k1 = Math.abs(k1);
                  this.layFoundationRandomStoneBrick(world, random, i1, 0, k1);
                  if (i1 <= 1 && k1 <= 1) {
                     this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150347_e, 0);
                  }

                  if ((i1 == 2 || k1 == 2) && (i1 != 0 || k1 != -2)) {
                     i1 = 1 + random.nextInt(3);

                     for(k1 = 1; k1 <= i1; ++k1) {
                        this.placeRandomStoneBrick(world, random, i1, k1, k1);
                     }
                  }
               }
            }

            if (random.nextInt(4) == 0) {
               this.placeChest(world, random, 0, 1, 1, LOTRMod.chestStone, 2, LOTRChestContents.RUINED_HOUSE);
            }
         } else {
            int j1;
            if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.BAR_TOWER) {
               i1 = random.nextInt(2);
               if (i1 == 0) {
                  this.barsBlock = Blocks.field_150411_aY;
               } else if (i1 == 1) {
                  this.barsBlock = LOTRMod.bronzeBars;
               }

               for(k1 = -2; k1 <= 2; ++k1) {
                  for(i1 = -2; i1 <= 2; ++i1) {
                     k1 = Math.abs(k1);
                     i1 = Math.abs(i1);
                     if (k1 == 2 && i1 <= 1 || i1 == 2 && k1 <= 1) {
                        this.layFoundationRandomStoneBrick(world, random, k1, 0, i1);
                        k1 = 4 + random.nextInt(3);

                        for(j1 = 1; j1 <= k1; ++j1) {
                           this.placeRandomStoneBrick(world, random, k1, j1, i1);
                        }
                     }
                  }
               }

               for(k1 = 1; k1 <= 2; ++k1) {
                  this.setAir(world, 0, k1, -2);
                  this.setBlockAndMetadata(world, 0, k1, 2, this.barsBlock, 0);
                  this.setBlockAndMetadata(world, -2, k1, 0, this.barsBlock, 0);
                  this.setBlockAndMetadata(world, 2, k1, 0, this.barsBlock, 0);
               }

               this.setBlockAndMetadata(world, 0, 3, -2, Blocks.field_150333_U, 8);
               int[] var24 = new int[]{-1, 1};
               i1 = var24.length;

               for(k1 = 0; k1 < i1; ++k1) {
                  i1 = var24[k1];
                  int k1 = 1;
                  j1 = this.getTopBlock(world, i1, k1);
                  if (random.nextInt(10) == 0) {
                     this.placeChest(world, random, i1, j1, k1, LOTRMod.chestStone, 2, LOTRChestContents.RUINED_HOUSE);
                  }
               }
            } else {
               int j1;
               int randomWall;
               int stairDir;
               if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.PIT_MINE) {
                  for(i1 = -2; i1 <= 2; ++i1) {
                     for(k1 = -2; k1 <= 2; ++k1) {
                        i1 = Math.abs(i1);
                        k1 = Math.abs(k1);
                        if (i1 == 2 || k1 == 2) {
                           i1 = this.getTopBlock(world, i1, k1);
                           i1 -= random.nextInt(3);

                           for(k1 = 1; k1 >= i1; --k1) {
                              this.placeRandomStoneBrick(world, random, i1, k1, k1);
                              this.setGrassToDirt(world, i1, k1 - 1, k1);
                           }
                        }

                        if (i1 == 2 && k1 == 2) {
                           i1 = random.nextInt(3);

                           for(k1 = 1; k1 <= 1 + i1; ++k1) {
                              this.placeRandomStoneBrick(world, random, i1, k1, k1);
                              this.setGrassToDirt(world, i1, k1 - 1, k1);
                           }
                        }
                     }
                  }

                  i1 = 4 + random.nextInt(5);
                  k1 = 2 + random.nextInt(3);
                  i1 = 60 - random.nextInt(5);
                  i1 -= this.originY;
                  k1 = i1 - k1 - 1;

                  for(i1 = -1; i1 <= 1; ++i1) {
                     for(k1 = -1; k1 <= 1; ++k1) {
                        j1 = Math.abs(i1);
                        j1 = Math.abs(k1);
                        randomWall = this.getTopBlock(world, i1, k1);

                        for(stairDir = randomWall; stairDir >= i1; --stairDir) {
                           this.setAir(world, i1, stairDir, k1);
                           if (j1 == 1 && j1 == 1 && random.nextInt(10) == 0) {
                              this.setBlockAndMetadata(world, i1, stairDir, k1, Blocks.field_150333_U, 0);
                           }
                        }
                     }
                  }

                  for(i1 = -i1; i1 <= i1; ++i1) {
                     for(k1 = -i1; k1 <= i1; ++k1) {
                        j1 = Math.abs(i1);
                        j1 = Math.abs(k1);
                        randomWall = random.nextInt(5);
                        if (randomWall == 0) {
                           this.placeRandomStoneBrick(world, random, i1, k1, k1);
                        } else if (randomWall == 1) {
                           this.setBlockAndMetadata(world, i1, k1, k1, Blocks.field_150347_e, 0);
                        } else if (randomWall == 2) {
                           this.setBlockAndMetadata(world, i1, k1, k1, Blocks.field_150348_b, 0);
                        } else if (randomWall == 3) {
                           this.setBlockAndMetadata(world, i1, k1, k1, Blocks.field_150351_n, 0);
                        } else if (randomWall == 4) {
                           this.setBlockAndMetadata(world, i1, k1, k1, Blocks.field_150346_d, 0);
                        }

                        for(stairDir = k1 + 1; stairDir <= k1 + k1; ++stairDir) {
                           this.setAir(world, i1, stairDir, k1);
                        }

                        if (j1 == 2 && j1 == 2) {
                           for(stairDir = k1 + 1; stairDir <= k1 + k1; ++stairDir) {
                              this.setBlockAndMetadata(world, i1, stairDir, k1, LOTRMod.woodBeamV1, 0);
                           }
                        } else if (j1 <= 2 && j1 <= 2 && (j1 == 2 || j1 == 2)) {
                           if (random.nextInt(4) != 0) {
                              this.setBlockAndMetadata(world, i1, k1 + k1, k1, Blocks.field_150422_aJ, 0);
                           }
                        } else if (random.nextInt(60) == 0) {
                           this.placeSkull(world, random, i1, k1 + 1, k1);
                        } else if (random.nextInt(120) == 0) {
                           stairDir = Direction.field_71582_c[random.nextInt(4)];
                           this.placeChest(world, random, i1, k1 + 1, k1, stairDir, LOTRChestContents.RUINED_HOUSE);
                        }
                     }
                  }
               } else if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.PLINTH) {
                  for(i1 = -3; i1 <= 2; ++i1) {
                     for(k1 = -3; k1 <= 2; ++k1) {
                        this.layFoundation(world, i1, 0, k1, Blocks.field_150347_e, 0);
                     }
                  }

                  for(i1 = -2; i1 <= 1; ++i1) {
                     for(k1 = -2; k1 <= 1; ++k1) {
                        this.placeRandomStoneBrick(world, random, i1, 1, k1);
                     }
                  }

                  int[] var25 = new int[]{-3, 2};
                  k1 = var25.length;

                  for(i1 = 0; i1 < k1; ++i1) {
                     k1 = var25[i1];
                     this.setBlockAndMetadata(world, k1, 1, -2, Blocks.field_150390_bg, 2);
                     this.setBlockAndMetadata(world, k1, 1, -1, Blocks.field_150333_U, 8);
                     this.setBlockAndMetadata(world, k1, 1, 0, Blocks.field_150333_U, 8);
                     this.setBlockAndMetadata(world, k1, 1, 1, Blocks.field_150390_bg, 3);
                  }

                  var25 = new int[]{-3, 2};
                  k1 = var25.length;

                  for(i1 = 0; i1 < k1; ++i1) {
                     k1 = var25[i1];
                     this.setBlockAndMetadata(world, -2, 1, k1, Blocks.field_150390_bg, 1);
                     this.setBlockAndMetadata(world, -1, 1, k1, Blocks.field_150333_U, 8);
                     this.setBlockAndMetadata(world, 0, 1, k1, Blocks.field_150333_U, 8);
                     this.setBlockAndMetadata(world, 1, 1, k1, Blocks.field_150390_bg, 0);
                  }

                  for(i1 = -1; i1 <= 0; ++i1) {
                     for(k1 = -1; k1 <= 0; ++k1) {
                        if (random.nextInt(3) == 0) {
                           i1 = 2 + random.nextInt(4);

                           for(k1 = 2; k1 < 2 + i1; ++k1) {
                              this.setBlockAndMetadata(world, i1, k1, k1, LOTRMod.pillar2, 2);
                           }

                           this.setBlockAndMetadata(world, i1, 2 + i1, k1, Blocks.field_150390_bg, random.nextInt(4));
                        }
                     }
                  }
               } else {
                  byte r1;
                  if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.RUBBLE) {
                     i1 = 3 + random.nextInt(5);
                     r1 = 2;

                     for(i1 = -i1; i1 <= i1; ++i1) {
                        for(k1 = -i1; k1 <= i1; ++k1) {
                           i1 = i1 * i1 + k1 * k1;
                           if (i1 < i1 * i1) {
                              k1 = this.getTopBlock(world, i1, k1) - 1;
                              if (this.isSuitableSpawnBlock(world, i1, k1)) {
                                 if (random.nextInt(3) == 0) {
                                    if (random.nextBoolean()) {
                                       this.setBlockAndMetadata(world, i1, k1, k1, Blocks.field_150347_e, 0);
                                    } else {
                                       this.placeRandomStoneBrick(world, random, i1, k1, k1);
                                    }

                                    this.setGrassToDirt(world, i1, k1 - 1, k1);
                                 }

                                 if (i1 > r1 * r1 && random.nextInt(6) == 0) {
                                    j1 = 1 + random.nextInt(4);

                                    for(j1 = k1 + 1; j1 <= k1 + j1; ++j1) {
                                       this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150348_b, 0);
                                       this.setGrassToDirt(world, i1, j1 - 1, k1);
                                    }
                                 }
                              }
                           }
                        }
                     }

                     if (random.nextInt(6) == 0) {
                        for(i1 = -1; i1 <= 1; ++i1) {
                           for(k1 = -1; k1 <= 1; ++k1) {
                              this.layFoundation(world, i1, 0, k1, Blocks.field_150334_T, 0);
                              this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150333_U, 0);
                           }
                        }

                        this.setBlockAndMetadata(world, 0, 1, 0, Blocks.field_150417_aV, 3);
                        this.placeChest(world, random, 0, 0, 0, LOTRMod.chestStone, 2, LOTRChestContents.RUINED_HOUSE);
                     }
                  } else {
                     boolean rotten;
                     if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.QUARRY) {
                        int r = 9;

                        for(k1 = -r; k1 <= r; ++k1) {
                           for(i1 = -r; i1 <= r; ++i1) {
                              for(k1 = r; k1 >= 1; --k1) {
                                 i1 = k1 - -5;
                                 k1 = k1 * k1 + i1 * i1 + i1 * i1;
                                 if (k1 < r * r) {
                                    boolean grass = !this.isOpaque(world, k1, k1 + 1, i1);
                                    this.setBlockAndMetadata(world, k1, k1, i1, (Block)(grass ? Blocks.field_150349_c : Blocks.field_150346_d), 0);
                                    this.setGrassToDirt(world, k1, k1 - 1, i1);
                                    if (k1 == 1) {
                                       this.layFoundation(world, k1, 0, i1, Blocks.field_150346_d, 0);
                                    }
                                 }
                              }
                           }
                        }

                        r = 5;

                        for(k1 = -r; k1 <= r; ++k1) {
                           for(i1 = -r; i1 <= r; ++i1) {
                              for(k1 = -r; k1 <= r; ++k1) {
                                 i1 = k1 - 1;
                                 k1 = k1 * k1 + i1 * i1 + i1 * i1;
                                 if (k1 < r * r) {
                                    if (random.nextInt(4) == 0) {
                                       this.setBlockAndMetadata(world, k1, k1, i1, Blocks.field_150347_e, 0);
                                    } else {
                                       this.setBlockAndMetadata(world, k1, k1, i1, Blocks.field_150348_b, 0);
                                    }

                                    this.setGrassToDirt(world, k1, k1 - 1, i1);
                                 }
                              }
                           }
                        }

                        r = 5;
                        r1 = 3;

                        for(i1 = -r; i1 <= r; ++i1) {
                           for(k1 = -r; k1 <= r; ++k1) {
                              for(i1 = -r; i1 <= r; ++i1) {
                                 k1 = i1 - 2;
                                 j1 = i1 - 1;
                                 j1 = k1 - 2;
                                 randomWall = k1 * k1 + j1 * j1 + j1 * j1;
                                 if (randomWall < r1 * r1) {
                                    this.setAir(world, i1, i1, k1);
                                    if (this.getBlock(world, i1, i1 - 1, k1) == Blocks.field_150346_d) {
                                       this.setBlockAndMetadata(world, i1, i1 - 1, k1, Blocks.field_150349_c, 0);
                                    }
                                 }
                              }
                           }
                        }

                        rotten = random.nextBoolean();

                        for(k1 = -1; k1 <= 3; ++k1) {
                           if (rotten) {
                              this.setBlockAndMetadata(world, 1, k1, 1, LOTRMod.woodBeamRotten, 0);
                           } else {
                              this.setBlockAndMetadata(world, 1, k1, 1, LOTRMod.woodBeamV1, 0);
                           }
                        }

                        if (rotten) {
                           this.setBlockAndMetadata(world, 4, 1, 3, LOTRMod.stairsRotten, 1);
                           this.setBlockAndMetadata(world, 4, 0, 3, LOTRMod.stairsRotten, 4);
                           this.setBlockAndMetadata(world, 3, 0, 3, LOTRMod.stairsRotten, 1);
                           this.setBlockAndMetadata(world, 3, -1, 3, LOTRMod.stairsRotten, 4);
                           this.setBlockAndMetadata(world, 2, -1, 3, LOTRMod.planksRotten, 0);
                           this.setBlockAndMetadata(world, 2, -1, 2, LOTRMod.stairsRotten, 2);
                           this.setBlockAndMetadata(world, 5, 2, 2, LOTRMod.stairsRotten, 3);
                           this.setGrassToDirt(world, 5, 1, 2);
                        } else {
                           this.setBlockAndMetadata(world, 4, 1, 3, Blocks.field_150476_ad, 1);
                           this.setBlockAndMetadata(world, 4, 0, 3, Blocks.field_150476_ad, 4);
                           this.setBlockAndMetadata(world, 3, 0, 3, Blocks.field_150476_ad, 1);
                           this.setBlockAndMetadata(world, 3, -1, 3, Blocks.field_150476_ad, 4);
                           this.setBlockAndMetadata(world, 2, -1, 3, Blocks.field_150344_f, 0);
                           this.setBlockAndMetadata(world, 2, -1, 2, Blocks.field_150476_ad, 2);
                           this.setBlockAndMetadata(world, 5, 2, 2, Blocks.field_150476_ad, 3);
                           this.setGrassToDirt(world, 5, 1, 2);
                        }

                        for(k1 = -2; k1 <= 2; ++k1) {
                           for(i1 = -2; i1 <= 2; ++i1) {
                              k1 = Math.abs(k1);
                              j1 = Math.abs(i1);
                              if ((k1 == 2 || j1 == 2) && random.nextBoolean()) {
                                 if (rotten) {
                                    this.setBlockAndMetadata(world, k1, 6, i1, LOTRMod.fenceRotten, 0);
                                 } else {
                                    this.setBlockAndMetadata(world, k1, 6, i1, Blocks.field_150422_aJ, 0);
                                 }
                              }
                           }
                        }
                     } else if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.OBELISK) {
                        i1 = radius;
                        int centreWidth = true;

                        for(i1 = -radius; i1 <= i1; ++i1) {
                           for(k1 = -i1; k1 <= i1; ++k1) {
                              i1 = this.getTopBlock(world, i1, k1) - 1;
                              if (this.isSuitableSpawnBlock(world, i1, k1)) {
                                 if (random.nextInt(3) == 0) {
                                    if (random.nextBoolean()) {
                                       this.setBlockAndMetadata(world, i1, i1, k1, Blocks.field_150347_e, 0);
                                    } else {
                                       this.setBlockAndMetadata(world, i1, i1, k1, Blocks.field_150351_n, 0);
                                    }

                                    this.setGrassToDirt(world, i1, i1 - 1, k1);
                                 }

                                 if ((i1 >= 4 || k1 >= 4) && random.nextInt(6) == 0) {
                                    this.setGrassToDirt(world, i1, i1, k1);
                                    this.placeRandomStoneBrick(world, random, i1, i1 + 1, k1);
                                    if (random.nextInt(3) == 0) {
                                       k1 = random.nextInt(3);
                                       if (k1 == 0) {
                                          this.placeRandomStoneBrick(world, random, i1, i1 + 2, k1);
                                       } else if (k1 == 1) {
                                          this.setBlockAndMetadata(world, i1, i1 + 2, k1, Blocks.field_150390_bg, random.nextInt(4));
                                       } else if (k1 == 2) {
                                          this.setBlockAndMetadata(world, i1, i1 + 2, k1, LOTRMod.wallStoneV, 1);
                                       }
                                    }
                                 }
                              }
                           }
                        }

                        for(i1 = -1; i1 <= 1; ++i1) {
                           for(k1 = -1; k1 <= 1; ++k1) {
                              i1 = Math.abs(i1);
                              k1 = Math.abs(k1);
                              this.layFoundationRandomStoneBrick(world, random, i1, 0, k1);
                              this.placeRandomStoneBrick(world, random, i1, 1, k1);
                              if (i1 == 0 || k1 == 0) {
                                 this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150417_aV, 3);
                                 this.placeRandomStoneBrick(world, random, i1, 2, k1);
                              }
                           }
                        }

                        this.setBlockAndMetadata(world, -1, 3, 0, Blocks.field_150390_bg, 1);
                        this.setBlockAndMetadata(world, 1, 3, 0, Blocks.field_150390_bg, 0);
                        this.setBlockAndMetadata(world, 0, 3, -1, Blocks.field_150390_bg, 2);
                        this.setBlockAndMetadata(world, 0, 3, 1, Blocks.field_150390_bg, 3);
                        i1 = 4 + random.nextInt(4);

                        for(k1 = 3; k1 <= i1; ++k1) {
                           this.placeRandomStoneBrick(world, random, 0, k1, 0);
                        }

                        for(k1 = i1 + 1; k1 <= i1 + 2; ++k1) {
                           this.setBlockAndMetadata(world, 0, k1, 0, LOTRMod.wallStoneV, 1);
                        }
                     } else if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.WELL) {
                        i1 = 4 + random.nextInt(5);
                        k1 = -i1 - 1;
                        rotten = random.nextBoolean();
                        k1 = 2 + random.nextInt(5);
                        if (k1 > i1) {
                           k1 = i1;
                        }

                        for(i1 = -2; i1 <= 1; ++i1) {
                           for(k1 = -2; k1 <= 1; ++k1) {
                              if (i1 != -2 && i1 != 1 && k1 != -2 && k1 != 1) {
                                 for(j1 = 1; j1 >= k1; --j1) {
                                    if (rotten && j1 <= k1 + k1) {
                                       this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150355_j, 0);
                                    } else {
                                       this.setAir(world, i1, j1, k1);
                                    }
                                 }

                                 this.setGrassToDirt(world, i1, k1 - 1, k1);
                                 this.setBlockAndMetadata(world, i1, k1, k1, Blocks.field_150348_b, 0);
                                 this.setBlockAndMetadata(world, i1, k1 + 1, k1, Blocks.field_150351_n, 0);
                                 if (random.nextBoolean()) {
                                    this.setBlockAndMetadata(world, i1, k1 + 2, k1, Blocks.field_150351_n, 0);
                                 }

                                 if (random.nextInt(8) == 0) {
                                    j1 = Direction.field_71582_c[random.nextInt(4)];
                                    this.placeChest(world, random, i1, k1 + 1, k1, LOTRMod.chestStone, j1, LOTRChestContents.RUINED_HOUSE);
                                 }

                                 if (random.nextInt(3) == 0) {
                                    this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.fenceRotten, 0);
                                 }
                              } else {
                                 this.placeRandomStoneBrick(world, random, i1, 1, k1);
                                 this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150334_T, 0);

                                 for(j1 = -1; j1 >= k1; --j1) {
                                    this.placeRandomStoneBrick(world, random, i1, j1, k1);
                                 }

                                 this.setGrassToDirt(world, i1, k1 - 1, k1);
                              }
                           }
                        }

                        this.setBlockAndMetadata(world, -2, 1, -2, Blocks.field_150333_U, 0);
                        this.setBlockAndMetadata(world, 1, 1, -2, Blocks.field_150333_U, 0);
                        this.setBlockAndMetadata(world, -2, 1, 1, Blocks.field_150333_U, 0);
                        this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150333_U, 0);
                     } else if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.TURRET) {
                        i1 = random.nextInt(3);
                        if (i1 == 0) {
                           this.plankBlock = Blocks.field_150344_f;
                           this.plankMeta = 0;
                           this.plankSlabBlock = Blocks.field_150376_bx;
                           this.plankSlabMeta = 0;
                           this.woodBeamBlock = LOTRMod.woodBeamV1;
                           this.woodBeamMeta = 0;
                        } else if (i1 == 1) {
                           this.plankBlock = Blocks.field_150344_f;
                           this.plankMeta = 1;
                           this.plankSlabBlock = Blocks.field_150376_bx;
                           this.plankSlabMeta = 1;
                           this.woodBeamBlock = LOTRMod.woodBeamV1;
                           this.woodBeamMeta = 1;
                        } else if (i1 == 2) {
                           this.plankBlock = LOTRMod.planksRotten;
                           this.plankMeta = 0;
                           this.plankSlabBlock = LOTRMod.rottenSlabSingle;
                           this.plankSlabMeta = 0;
                           this.woodBeamBlock = LOTRMod.woodBeamRotten;
                           this.woodBeamMeta = 0;
                        }

                        k1 = random.nextInt(2);
                        if (k1 == 0) {
                           this.barsBlock = Blocks.field_150411_aY;
                        } else if (k1 == 1) {
                           this.barsBlock = LOTRMod.bronzeBars;
                        }

                        for(i1 = -4; i1 <= 4; ++i1) {
                           for(k1 = -4; k1 <= 4; ++k1) {
                              i1 = Math.abs(i1);
                              k1 = Math.abs(k1);
                              this.layFoundationRandomStoneBrick(world, random, i1, 0, k1);
                              this.placeRandomStoneBrick(world, random, i1, 1, k1);
                              if (i1 <= 3 && k1 <= 3) {
                                 if (i1 == 3 && k1 == 3) {
                                    this.placeRandomStoneBrick(world, random, i1, 2, k1);

                                    for(j1 = 3; j1 <= 5; ++j1) {
                                       this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                                    }

                                    this.placeRandomStoneBrick(world, random, i1, 6, k1);
                                 } else if (i1 != 3 && k1 != 3) {
                                    if (random.nextInt(4) == 0) {
                                       if (random.nextBoolean()) {
                                          this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150351_n, 0);
                                       } else {
                                          this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150347_e, 0);
                                       }
                                    } else if (random.nextInt(4) == 0) {
                                       this.setAir(world, i1, 1, k1);
                                    } else {
                                       this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
                                    }

                                    for(j1 = 2; j1 <= 5; ++j1) {
                                       this.setAir(world, i1, j1, k1);
                                    }

                                    if (random.nextInt(5) == 0) {
                                       this.setAir(world, i1, 6, k1);
                                    } else {
                                       this.setBlockAndMetadata(world, i1, 6, k1, this.plankSlabBlock, this.plankSlabMeta);
                                    }
                                 } else {
                                    for(j1 = 2; j1 <= 6; ++j1) {
                                       if (random.nextInt(8) == 0) {
                                          this.setAir(world, i1, j1, k1);
                                       } else {
                                          this.placeRandomStoneBrick(world, random, i1, j1, k1);
                                       }
                                    }

                                    if (i1 <= 1 || k1 <= 1) {
                                       if (random.nextInt(4) == 0) {
                                          this.setAir(world, i1, 4, k1);
                                       } else {
                                          this.setBlockAndMetadata(world, i1, 4, k1, this.barsBlock, 0);
                                       }
                                    }
                                 }
                              }

                              if ((i1 == 4 || k1 == 4) && random.nextInt(3) != 0) {
                                 if (random.nextInt(3) == 0) {
                                    if (random.nextBoolean()) {
                                       this.setBlockAndMetadata(world, i1, 7, k1, Blocks.field_150390_bg, random.nextInt(4));
                                    } else {
                                       this.setBlockAndMetadata(world, i1, 7, k1, Blocks.field_150333_U, 0);
                                    }
                                 } else {
                                    this.placeRandomStoneBrick(world, random, i1, 7, k1);
                                 }
                              }
                           }
                        }

                        for(i1 = -4; i1 <= 4; ++i1) {
                           this.setBlockAndMetadata(world, i1, 2, -4, Blocks.field_150390_bg, 2);
                           this.setBlockAndMetadata(world, i1, 2, 4, Blocks.field_150390_bg, 3);
                           this.setBlockAndMetadata(world, i1, 6, -4, Blocks.field_150390_bg, 6);
                           this.setBlockAndMetadata(world, i1, 6, 4, Blocks.field_150390_bg, 7);
                        }

                        for(i1 = -3; i1 <= 3; ++i1) {
                           this.setBlockAndMetadata(world, -4, 2, i1, Blocks.field_150390_bg, 1);
                           this.setBlockAndMetadata(world, 4, 2, i1, Blocks.field_150390_bg, 0);
                           this.setBlockAndMetadata(world, -4, 6, i1, Blocks.field_150390_bg, 5);
                           this.setBlockAndMetadata(world, 4, 6, i1, Blocks.field_150390_bg, 4);
                        }

                        if (random.nextInt(3) == 0) {
                           this.setBlockAndMetadata(world, 0, 1, 2, this.plankBlock, this.plankMeta);
                           this.placeChest(world, random, 0, 2, 2, 2, LOTRChestContents.RUINED_HOUSE);
                        }

                        if (random.nextInt(3) == 0) {
                           this.placeRandomStoneBrick(world, random, 0, 6, 3);
                           this.placeChest(world, random, 0, 7, 3, 2, LOTRChestContents.RUINED_HOUSE);
                        }
                     } else if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.WALLS) {
                        i1 = 3 + random.nextInt(7);
                        k1 = 2 + random.nextInt(3);
                        i1 = 2 + random.nextInt(6);
                        k1 = 2 + random.nextInt(7);

                        for(i1 = -k1; i1 <= k1; ++i1) {
                           for(k1 = -i1; k1 <= i1; ++k1) {
                              j1 = Math.abs(i1);
                              j1 = Math.abs(k1);
                              if (this.isSuitableSpawnBlock(world, i1, k1)) {
                                 randomWall = this.getTopBlock(world, i1, k1) - 1;
                                 if (j1 == k1) {
                                    stairDir = i1 - random.nextInt(3);
                                    if (random.nextInt(8) == 0) {
                                       stairDir = random.nextInt(3);
                                    }

                                    float factor = (float)j1 / (float)i1;
                                    factor = 1.0F / (factor + 0.01F);
                                    factor *= 0.5F + random.nextFloat() * 0.5F;
                                    factor = Math.min(factor, 1.0F);
                                    stairDir = (int)((float)stairDir * factor);
                                    int top = randomWall + stairDir;

                                    int h1;
                                    for(h1 = randomWall + 1; h1 <= top; ++h1) {
                                       if (random.nextInt(8) == 0) {
                                          this.setBlockAndMetadata(world, i1, h1, k1, Blocks.field_150390_bg, random.nextInt(8));
                                       } else if (random.nextInt(8) == 0) {
                                          this.setBlockAndMetadata(world, i1, h1, k1, Blocks.field_150347_e, 0);
                                       } else {
                                          this.placeRandomStoneBrick(world, random, i1, h1, k1);
                                       }

                                       this.setGrassToDirt(world, i1, h1 - 1, k1);
                                    }

                                    if (j1 < i1 / 2 && stairDir >= 3 && stairDir >= i1 - 3 && random.nextBoolean()) {
                                       h1 = top - random.nextInt(2);
                                       int w = random.nextInt(k1 * 2);

                                       for(int w1 = 1; w1 <= w; ++w1) {
                                          if (i1 < 0) {
                                             this.setBlockAndMetadata(world, i1 + w1, h1, k1, Blocks.field_150333_U, 8);
                                          } else {
                                             this.setBlockAndMetadata(world, i1 - w1, h1, k1, Blocks.field_150333_U, 8);
                                          }

                                          if (random.nextInt(4) == 0) {
                                             break;
                                          }
                                       }
                                    }
                                 } else {
                                    if (random.nextInt(5) != 0) {
                                       if (random.nextInt(4) == 0) {
                                          this.setBlockAndMetadata(world, i1, randomWall, k1, Blocks.field_150341_Y, 0);
                                       } else {
                                          this.setBlockAndMetadata(world, i1, randomWall, k1, Blocks.field_150347_e, 0);
                                       }
                                    }

                                    if (j1 == k1 - 1 && random.nextInt(Math.max(2, k1 / 2)) == 0 || random.nextInt(k1) == 0) {
                                       stairDir = 1;
                                       if (random.nextInt(4) == 0) {
                                          ++stairDir;
                                       }

                                       this.setGrassToDirt(world, i1, randomWall, k1);

                                       for(int j2 = randomWall + 1; j2 <= randomWall + stairDir; ++j2) {
                                          this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150351_n, 0);
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     } else if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.SHRINE) {
                        for(i1 = -4; i1 <= 4; ++i1) {
                           for(k1 = -4; k1 <= 4; ++k1) {
                              i1 = Math.abs(i1);
                              k1 = Math.abs(k1);
                              this.layFoundationRandomStoneBrick(world, random, i1, 0, k1);
                              if (i1 <= 3 && k1 <= 3) {
                                 if (i1 <= 1 && k1 <= 1) {
                                    this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150334_T, 0);
                                    this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150333_U, 0);
                                 } else if (random.nextInt(16) == 0) {
                                    this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150351_n, 0);
                                 } else if (random.nextInt(16) == 0) {
                                    this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150347_e, 0);
                                 } else if (random.nextInt(16) == 0) {
                                    this.setBiomeTop(world, i1, 1, k1);
                                 } else if (i1 <= 2 && k1 <= 2) {
                                    this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150334_T, 0);
                                 } else {
                                    this.placeRandomStoneBrick(world, random, i1, 1, k1);
                                 }
                              }

                              if (random.nextInt(5) != 0) {
                                 if (i1 == -4) {
                                    this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150390_bg, 1);
                                 } else if (i1 == 4) {
                                    this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150390_bg, 0);
                                 } else if (k1 == -4) {
                                    this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150390_bg, 2);
                                 } else if (k1 == 4) {
                                    this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150390_bg, 3);
                                 }
                              }

                              if (i1 == 3 && k1 == 3) {
                                 i1 = 2 + random.nextInt(4);
                                 k1 = 1 + i1;

                                 for(j1 = 2; j1 <= k1; ++j1) {
                                    this.placeRandomStoneBrick(world, random, i1, j1, k1);
                                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                                 }

                                 if (i1 >= 4) {
                                    this.setBlockAndMetadata(world, i1 - 1, 1 + i1, k1 - 1, Blocks.field_150390_bg, 6);
                                    this.setBlockAndMetadata(world, i1, 1 + i1, k1 - 1, Blocks.field_150390_bg, 6);
                                    this.setBlockAndMetadata(world, i1 + 1, 1 + i1, k1 - 1, Blocks.field_150390_bg, 6);
                                    this.setBlockAndMetadata(world, i1 + 1, 1 + i1, k1, Blocks.field_150390_bg, 4);
                                    this.setBlockAndMetadata(world, i1 + 1, 1 + i1, k1 + 1, Blocks.field_150390_bg, 7);
                                    this.setBlockAndMetadata(world, i1, 1 + i1, k1 + 1, Blocks.field_150390_bg, 7);
                                    this.setBlockAndMetadata(world, i1 - 1, 1 + i1, k1 + 1, Blocks.field_150390_bg, 7);
                                    this.setBlockAndMetadata(world, i1 - 1, 1 + i1, k1, Blocks.field_150390_bg, 5);
                                 }
                              }

                              if ((i1 == 3 && k1 <= 1 || k1 == 3 && i1 <= 1) && random.nextInt(4) != 0) {
                                 this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150333_U, 0);
                                 this.setGrassToDirt(world, i1, 1, k1);
                              }
                           }
                        }

                        this.setBlockAndMetadata(world, 0, 2, 0, Blocks.field_150417_aV, 3);
                        this.placeChest(world, random, 0, 1, 0, LOTRMod.chestStone, 2, LOTRChestContents.RUINED_HOUSE);
                     } else if (ruinType == LOTRWorldGenSmallStoneRuin.RuinType.BRICK_HOUSE) {
                        i1 = MathHelper.func_76136_a(random, 3, 5);
                        k1 = MathHelper.func_76136_a(random, 1, 4);

                        for(i1 = -i1; i1 <= i1; ++i1) {
                           for(k1 = -i1; k1 <= i1; ++k1) {
                              i1 = Math.abs(i1);
                              k1 = Math.abs(k1);
                              this.layFoundation(world, i1, 0, k1, Blocks.field_150347_e, 0);
                              j1 = random.nextInt(5);
                              if (j1 == 0) {
                                 this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150347_e, 0);
                              } else if (j1 == 1) {
                                 this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150341_Y, 0);
                              } else if (j1 == 2) {
                                 this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150351_n, 0);
                              } else if (j1 == 3) {
                                 this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150346_d, 1);
                              } else if (j1 == 4) {
                                 this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150336_V, 0);
                              }

                              if (i1 != i1 && k1 != i1) {
                                 if (i1 != i1 - 1 && k1 != i1 - 1) {
                                    if (random.nextInt(8) == 0) {
                                       j1 = random.nextInt(2);
                                       if (j1 == 0) {
                                          this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.rottenSlabSingle, 0);
                                       } else if (j1 == 1) {
                                          this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150333_U, 4);
                                       }
                                    }
                                 } else if (random.nextInt(3) == 0) {
                                    j1 = random.nextInt(4);
                                    if (j1 == 0) {
                                       this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150336_V, 0);
                                    } else if (j1 == 1) {
                                       randomWall = random.nextInt(8);
                                       this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150389_bf, randomWall);
                                    } else if (j1 == 2) {
                                       this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.planksRotten, 0);
                                    } else if (j1 == 3) {
                                       this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.fenceRotten, 0);
                                    }
                                 }
                              } else if (random.nextInt(10) != 0) {
                                 for(j1 = 1; j1 <= k1; ++j1) {
                                    if (random.nextInt(6) != 0) {
                                       if (random.nextInt(3) == 0) {
                                          if (random.nextBoolean()) {
                                             this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.redBrick, 0);
                                          } else {
                                             this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.redBrick, 1);
                                          }
                                       } else {
                                          this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150336_V, 0);
                                       }
                                    } else {
                                       randomWall = random.nextInt(7);
                                       if (randomWall == 0) {
                                          this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150334_T, 0);
                                       } else if (randomWall == 1) {
                                          this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.pillar2, 3);
                                       } else if (randomWall == 2) {
                                          this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.clayTile, 0);
                                       } else if (randomWall == 3) {
                                          stairDir = random.nextInt(8);
                                          this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150389_bf, stairDir);
                                       } else if (randomWall == 4) {
                                          stairDir = random.nextInt(8);
                                          this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.stairsClayTile, stairDir);
                                       } else if (randomWall == 5) {
                                          this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
                                       } else if (randomWall == 6) {
                                          this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.wallStoneV, 6);
                                       }
                                    }

                                    if (random.nextInt(6) == 0) {
                                       break;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   private boolean isSuitableSpawnBlock(World world, int i, int k) {
      int j = this.getTopBlock(world, i, k);
      if (!this.isSurface(world, i, j - 1, k)) {
         return false;
      } else {
         Block above = this.getBlock(world, i, j, k);
         return !above.func_149688_o().func_76224_d();
      }
   }

   private void layFoundation(World world, int i, int j, int k, Block block, int meta) {
      for(int j1 = j; (j1 >= 0 || !this.isOpaque(world, i, j1, k)) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, i, j1, k, block, meta);
         this.setGrassToDirt(world, i, j1 - 1, k);
      }

   }

   private void layFoundationRandomStoneBrick(World world, Random random, int i, int j, int k) {
      for(int j1 = j; (j1 >= 0 || !this.isOpaque(world, i, j1, k)) && this.getY(j1) >= 0; --j1) {
         this.placeRandomStoneBrick(world, random, i, j1, k);
         this.setGrassToDirt(world, i, j1 - 1, k);
      }

   }

   private void placeRandomStoneBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(4) == 0) {
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 1);
         } else {
            this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 2);
         }
      } else {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 0);
      }

   }

   private static enum RuinType {
      COLUMN(0, 1),
      ROOM(3, 2),
      BAR_TOWER(3, 2),
      PIT_MINE(0, 2),
      PLINTH(0, 3),
      RUBBLE(0, 0),
      QUARRY(0, 7),
      OBELISK(0, 5),
      WELL(0, 2),
      TURRET(5, 4),
      WALLS(0, 3),
      SHRINE(0, 4),
      BRICK_HOUSE(0, 5);

      private int centreShift;
      private int checkRadius;

      private RuinType(int i, int j) {
         this.centreShift = i;
         this.checkRadius = j;
      }

      public static LOTRWorldGenSmallStoneRuin.RuinType getRandomType(Random random) {
         return values()[random.nextInt(values().length)];
      }
   }
}
