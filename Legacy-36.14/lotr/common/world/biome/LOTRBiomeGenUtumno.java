package lotr.common.world.biome;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenSkullPile;
import lotr.common.world.feature.LOTRWorldGenStalactites;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBiomeGenUtumno extends LOTRBiome {
   private static List utumnoBiomes = new ArrayList();
   private LOTRWorldGenStalactites stalactiteGen;
   private LOTRWorldGenStalactites stalactiteIceGen;
   private LOTRWorldGenStalactites stalactiteObsidianGen;
   private LOTRBiomeSpawnList spawnableGuestList;

   public LOTRBiomeGenUtumno(int i) {
      super(i, false, LOTRDimension.UTUMNO);
      this.stalactiteGen = new LOTRWorldGenStalactites(LOTRMod.stalactite);
      this.stalactiteIceGen = new LOTRWorldGenStalactites(LOTRMod.stalactiteIce);
      this.stalactiteObsidianGen = new LOTRWorldGenStalactites(LOTRMod.stalactiteObsidian);
      this.spawnableGuestList = new LOTRBiomeSpawnList(this);
      utumnoBiomes.add(this);
      this.func_76745_m();
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.clear();
      this.field_82914_M.clear();
      this.npcSpawnList.clear();
      this.biomeColors.setGrass(0);
      this.biomeColors.setFoliage(0);
      this.biomeColors.setSky(0);
      this.biomeColors.setFoggy(true);
      this.biomeColors.setWater(0);
      LOTRBiomeSpawnList.FactionContainer var10000 = this.spawnableGuestList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.spawnableGuestList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_GUESTS, 10);
      var10000.add(var10001);
   }

   public LOTRBiomeSpawnList getNPCSpawnList(World world, Random random, int i, int j, int k, LOTRBiomeVariant variant) {
      return random.nextInt(1000) == 0 ? this.spawnableGuestList : LOTRUtumnoLevel.forY(j).getNPCSpawnList();
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.UTUMNO.getSubregion("utumno");
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      this.generateHoles(world, random, i, k);
      this.generatePits(world, random, i, k);
      this.generateBridges(world, random, i, k);
      this.generateStairs(world, random, i, k);
      this.generatePillars(world, random, i, k);
      this.generatePortalBases(world, random, i, k);
      this.generateBars(world, random, i, k);
      this.generateStalactites(world, random, i, k);
      this.generateSkulls(world, random, i, k);
   }

   private void generateHoles(World world, Random random, int i, int k) {
      for(int l = 0; l < 8; ++l) {
         int i1 = i + 8 + random.nextInt(16);
         int k1 = k + 8 + random.nextInt(16);
         int j1 = MathHelper.func_76136_a(random, 20, 240);
         if (world.func_147437_c(i1, j1, k1)) {
            LOTRUtumnoLevel level = LOTRUtumnoLevel.forY(j1);

            for(int j2 = j1; j2 >= level.corridorBaseLevels[0] - 1 && (!world.func_147437_c(i1, j2, k1) || random.nextInt(10) != 0) && LOTRUtumnoLevel.forY(j2 - 1) == level; --j2) {
               for(int i2 = i1 - 1; i2 <= i1; ++i2) {
                  for(int k2 = k1 - 1; k2 <= k1; ++k2) {
                     world.func_147468_f(i2, j2, k2);
                  }
               }
            }
         }
      }

   }

   private void generatePits(World world, Random random, int i, int k) {
      if (random.nextInt(5) == 0) {
         int i1 = i + 8 + random.nextInt(16);
         int k1 = k + 8 + random.nextInt(16);
         int j1 = MathHelper.func_76136_a(random, 20, 220);
         if (world.func_147437_c(i1, j1, k1)) {
            int radius = 8 + random.nextInt(30);
            LOTRUtumnoLevel level = LOTRUtumnoLevel.forY(j1);
            int yMin = Math.max(j1 - radius, level.baseLevel + 5);
            int yMax = Math.min(j1 + radius, level.topLevel - 5);

            for(int i2 = i1 - radius; i2 <= i1 + radius; ++i2) {
               for(int j2 = yMin; j2 <= yMax; ++j2) {
                  for(int k2 = k1 - radius; k2 <= k1 + radius; ++k2) {
                     int i3 = Math.abs(i2 - i1);
                     int j3 = Math.abs(j2 - j1);
                     int k3 = Math.abs(k2 - k1);
                     double dist = (double)(i3 * i3 + j3 * j3 + k3 * k3);
                     if (dist < (double)((radius - 5) * (radius - 5))) {
                        world.func_147468_f(i2, j2, k2);
                     } else if (dist < (double)(radius * radius) && random.nextInt(6) == 0) {
                        world.func_147468_f(i2, j2, k2);
                     }
                  }
               }
            }
         }
      }

   }

   private void generateBridges(World world, Random random, int i, int k) {
      label85:
      for(int l = 0; l < 20; ++l) {
         int i1 = i + 8 + random.nextInt(16);
         int k1 = k + 8 + random.nextInt(16);
         LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.values()[random.nextInt(LOTRUtumnoLevel.values().length)];
         int j1 = utumnoLevel.corridorBaseLevels[random.nextInt(utumnoLevel.corridorBaseLevels.length)] - 1;
         int fuzz = 2;

         for(int j2 = j1 - fuzz; j2 <= j1 + fuzz; ++j2) {
            Block block = world.func_147439_a(i1, j2, k1);
            world.func_72805_g(i1, j2, k1);
            if (block.func_149662_c() && world.func_147437_c(i1, j2 + 1, k1) && (world.func_147437_c(i1 - 1, j2, k1) || world.func_147437_c(i1 + 1, j2, k1) || world.func_147437_c(i1, j2, k1 - 1) || world.func_147437_c(i1, j2, k1 + 1))) {
               int[] bridge = this.searchForBridge(world, i1, j2, k1, -1, 0);
               if (bridge == null) {
                  bridge = this.searchForBridge(world, i1, j2, k1, 1, 0);
                  if (bridge == null) {
                     bridge = this.searchForBridge(world, i1, j2, k1, 0, -1);
                     if (bridge == null) {
                        bridge = this.searchForBridge(world, i1, j2, k1, 0, 1);
                     }
                  }
               }

               if (bridge != null) {
                  int xRange = bridge[0];
                  int zRange = bridge[1];
                  int startX = i1;
                  int endX = i1;
                  int startZ = k1;
                  int endZ = k1;
                  if (xRange >= 0) {
                     endX = i1 + xRange;
                  } else {
                     startX = i1 - -xRange;
                  }

                  if (zRange >= 0) {
                     endZ = k1 + zRange;
                  } else {
                     startZ = k1 - -zRange;
                  }

                  int x;
                  if (xRange == 0) {
                     x = random.nextInt(3);
                     startX -= x;
                     endX += x;
                  }

                  if (zRange == 0) {
                     x = random.nextInt(3);
                     startZ -= x;
                     endZ += x;
                  }

                  x = startX;

                  while(true) {
                     if (x > endX) {
                        continue label85;
                     }

                     for(int z = startZ; z <= endZ; ++z) {
                        if (random.nextInt(8) != 0) {
                           world.func_147465_d(x, j2, z, utumnoLevel.brickBlock, utumnoLevel.brickMeta, 2);
                        }
                     }

                     ++x;
                  }
               }
            }
         }
      }

   }

   private int[] searchForBridge(World world, int i, int j, int k, int xDirection, int zDirection) {
      LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.forY(j);
      int maxBridgeLength = 16;
      int minBridgeLength = 2 + utumnoLevel.corridorWidth / 2;
      int foundAir = 0;
      int foundBrick = 0;
      int x = 0;
      int z = 0;

      while(Math.abs(x) < maxBridgeLength && Math.abs(z) < maxBridgeLength) {
         if (xDirection == -1) {
            --x;
         }

         if (xDirection == 1) {
            ++x;
         }

         if (zDirection == -1) {
            --z;
         }

         if (zDirection == 1) {
            ++z;
         }

         int i1 = i + x;
         int k1 = k + z;
         if (foundAir == 0 && world.func_147437_c(i1, j, k1)) {
            if (xDirection == 0) {
               foundAir = z;
            } else if (zDirection == 0) {
               foundAir = x;
            }
         }

         if (foundAir != 0 && world.func_147439_a(i1, j, k1).func_149662_c()) {
            if (xDirection == 0) {
               foundBrick = z;
            } else if (zDirection == 0) {
               foundBrick = x;
            }
            break;
         }
      }

      return foundBrick != 0 && Math.abs(foundBrick - foundAir) >= minBridgeLength ? new int[]{x, z} : null;
   }

   private void generateStairs(World world, Random random, int i, int k) {
      label102:
      for(int l = 0; l < 8; ++l) {
         int i1 = i + 8 + random.nextInt(16);
         int k1 = k + 8 + random.nextInt(16);
         LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.values()[random.nextInt(LOTRUtumnoLevel.values().length)];
         int j1 = utumnoLevel.corridorBaseLevels[1 + random.nextInt(utumnoLevel.corridorBaseLevels.length - 1)] - 1;
         int fuzz = 2;

         for(int j2 = j1 - fuzz; j2 <= j1 + fuzz; ++j2) {
            if (world.func_147439_a(i1, j2, k1).func_149662_c() && world.func_147437_c(i1, j2 + 1, k1)) {
               int xDirection = 0;
               int zDirection = 0;
               int stairMeta = false;
               int stairMeta;
               if (random.nextBoolean()) {
                  xDirection = random.nextBoolean() ? 1 : -1;
                  stairMeta = xDirection > 0 ? 1 : 0;
               } else {
                  zDirection = random.nextBoolean() ? 1 : -1;
                  stairMeta = zDirection > 0 ? 3 : 2;
               }

               int stairX = i1;
               int stairY = j2;
               int stairZ = k1;
               int minStairRange = 6;
               int maxStairRange = 20;
               int stairWidth = 1 + random.nextInt(3);
               int stairHeight = stairWidth + 2;
               int stair = 0;

               while(true) {
                  for(int w = 0; w < stairWidth; ++w) {
                     int i2 = stairX + w * zDirection;
                     int k2 = stairZ + w * xDirection;
                     world.func_147465_d(i2, stairY, k2, utumnoLevel.brickStairBlock, stairMeta, 2);
                     if (world.func_147437_c(i2, stairY - 1, k2)) {
                        world.func_147465_d(i2, stairY - 1, k2, utumnoLevel.brickStairBlock, stairMeta ^ 1 | 4, 2);
                     }

                     for(int j3 = stairY + 1; j3 <= stairY + stairHeight; ++j3) {
                        world.func_147449_b(i2, j3, k2, Blocks.field_150350_a);
                     }
                  }

                  ++stair;
                  if (stair >= maxStairRange || stair >= minStairRange && random.nextInt(10) == 0) {
                     continue label102;
                  }

                  if (xDirection == -1) {
                     --stairX;
                  }

                  if (xDirection == 1) {
                     ++stairX;
                  }

                  if (zDirection == -1) {
                     --stairZ;
                  }

                  if (zDirection == 1) {
                     ++stairZ;
                  }

                  --stairY;
                  if (stairY <= utumnoLevel.corridorBaseLevels[0]) {
                     continue label102;
                  }
               }
            }
         }
      }

   }

   private void generatePillars(World world, Random random, int i, int k) {
      for(int l = 0; l < 40; ++l) {
         int i1 = i + 8 + random.nextInt(16);
         int k1 = k + 8 + random.nextInt(16);
         LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.values()[random.nextInt(LOTRUtumnoLevel.values().length)];
         int j1 = utumnoLevel.corridorBaseLevels[random.nextInt(utumnoLevel.corridorBaseLevels.length)];
         int pillarHeight = MathHelper.func_76136_a(random, 1, utumnoLevel.corridorHeight);
         int fuzz = 2;

         for(int j2 = j1 - fuzz; j2 <= j1 + fuzz; ++j2) {
            if (!world.func_147437_c(i1, j2 - 1, k1)) {
               boolean generated = false;

               for(int j3 = j2; j3 <= j2 + pillarHeight && world.func_147437_c(i1, j3, k1); ++j3) {
                  world.func_147465_d(i1, j3, k1, utumnoLevel.pillarBlock, utumnoLevel.pillarMeta, 2);
                  generated = true;
               }

               if (generated) {
                  break;
               }
            }
         }
      }

   }

   private void generatePortalBases(World world, Random random, int i, int k) {
      for(int l = 0; l < 1; ++l) {
         int i1 = i + 8 + random.nextInt(16);
         int k1 = k + 8 + random.nextInt(16);
         float f = random.nextFloat();
         LOTRUtumnoLevel utumnoLevel;
         if (f < 0.15F) {
            utumnoLevel = LOTRUtumnoLevel.ICE;
         } else if (f < 0.5F) {
            utumnoLevel = LOTRUtumnoLevel.OBSIDIAN;
         } else {
            utumnoLevel = LOTRUtumnoLevel.FIRE;
         }

         int j1 = utumnoLevel.corridorBaseLevels[random.nextInt(utumnoLevel.corridorBaseLevels.length)];
         int fuzz = 2;

         for(int j2 = j1 - fuzz; j2 <= j1 + fuzz; ++j2) {
            if (world.func_147437_c(i1, j2, k1) && World.func_147466_a(world, i1, j2 - 1, k1)) {
               world.func_147465_d(i1, j2, k1, LOTRMod.utumnoReturnPortalBase, 0, 2);
               break;
            }
         }
      }

   }

   private void generateBars(World world, Random random, int i, int k) {
      for(int l = 0; l < 200; ++l) {
         int i1 = i + 8 + random.nextInt(16);
         int k1 = k + 8 + random.nextInt(16);
         int j1 = MathHelper.func_76136_a(random, 4, 250);
         if (world.func_147439_a(i1, j1, k1).func_149662_c()) {
            int barWidth = 1 + random.nextInt(3);
            int barHeight = 2 + random.nextInt(3);
            boolean fire = random.nextInt(3) == 0;
            int facingX = 0;
            int facingZ = 0;
            if (random.nextBoolean()) {
               facingX = random.nextBoolean() ? -1 : 1;
            } else {
               facingZ = random.nextBoolean() ? -1 : 1;
            }

            boolean generate = true;

            int pass;
            int y;
            int i2;
            int j2;
            int k2;
            int i3;
            label104:
            for(pass = 0; pass <= 1; ++pass) {
               for(y = 0; y < barWidth; ++y) {
                  for(i2 = -1; i2 < barHeight + 1; ++i2) {
                     j2 = i1 + y * facingZ;
                     k2 = j1 + i2;
                     i3 = k1 + y * facingX;
                     boolean flag = true;
                     if (!world.func_147439_a(j2, k2, i3).func_149662_c()) {
                        flag = false;
                     }

                     if (i2 >= 0 && i2 < barHeight && !world.func_147437_c(j2 + facingX, k2, i3 + facingZ)) {
                        flag = false;
                     }

                     if (!flag) {
                        if (pass != 0) {
                           generate = false;
                           break label104;
                        }

                        generate = true;
                        int fx = facingX;
                        facingX = facingZ;
                        facingZ = fx;
                        continue label104;
                     }
                  }
               }
            }

            if (generate) {
               for(pass = 0; pass < barWidth; ++pass) {
                  for(y = 0; y < barHeight; ++y) {
                     i2 = i1 + pass * facingZ;
                     j2 = j1 + y;
                     k2 = k1 + pass * facingX;
                     world.func_147465_d(i2, j2, k2, LOTRMod.orcSteelBars, 0, 2);
                     if (fire && y == 0) {
                        i3 = i2 - facingX;
                        int k3 = k2 - facingZ;
                        if (world.func_147439_a(i3, j2, k3).func_149662_c()) {
                           world.func_147465_d(i3, j2 - 1, k3, LOTRMod.hearth, 0, 2);
                           world.func_147465_d(i3, j2, k3, Blocks.field_150480_ab, 0, 2);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void generateStalactites(World world, Random random, int i, int k) {
      int l;
      int i1;
      int k1;
      int j1;
      for(l = 0; l < 2; ++l) {
         i1 = i + 8 + random.nextInt(16);
         k1 = k + 8 + random.nextInt(16);
         j1 = MathHelper.func_76136_a(random, LOTRUtumnoLevel.ICE.baseLevel, LOTRUtumnoLevel.ICE.topLevel);
         if (random.nextBoolean()) {
            this.stalactiteGen.func_76484_a(world, random, i1, j1, k1);
         } else {
            this.stalactiteIceGen.func_76484_a(world, random, i1, j1, k1);
         }
      }

      for(l = 0; l < 2; ++l) {
         i1 = i + 8 + random.nextInt(16);
         k1 = k + 8 + random.nextInt(16);
         j1 = MathHelper.func_76136_a(random, LOTRUtumnoLevel.OBSIDIAN.baseLevel, LOTRUtumnoLevel.OBSIDIAN.topLevel);
         if (random.nextBoolean()) {
            this.stalactiteGen.func_76484_a(world, random, i1, j1, k1);
         } else {
            this.stalactiteObsidianGen.func_76484_a(world, random, i1, j1, k1);
         }
      }

      for(l = 0; l < 2; ++l) {
         i1 = i + 8 + random.nextInt(16);
         k1 = k + 8 + random.nextInt(16);
         j1 = MathHelper.func_76136_a(random, LOTRUtumnoLevel.FIRE.baseLevel, LOTRUtumnoLevel.FIRE.topLevel);
         this.stalactiteObsidianGen.func_76484_a(world, random, i1, j1, k1);
      }

   }

   private void generateSkulls(World world, Random random, int i, int k) {
      for(int l = 0; l < 4; ++l) {
         int i1 = i + random.nextInt(16) + 8;
         int k1 = k + random.nextInt(16) + 8;
         int j1 = MathHelper.func_76136_a(random, 4, 250);
         (new LOTRWorldGenSkullPile()).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public boolean canSpawnHostilesInDay() {
      return true;
   }

   public static void updateFogColor(int i, int j, int k) {
      LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.forY(j);
      Iterator var4 = utumnoBiomes.iterator();

      while(var4.hasNext()) {
         LOTRBiome biome = (LOTRBiome)var4.next();
         biome.biomeColors.setFog(utumnoLevel.fogColor);
      }

   }
}
