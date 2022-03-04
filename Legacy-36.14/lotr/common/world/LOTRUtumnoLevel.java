package lotr.common.world;

import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public enum LOTRUtumnoLevel {
   ICE(13819887, 180, 240, 4, 4),
   OBSIDIAN(2104109, 92, 180, 6, 5),
   FIRE(6295040, 0, 92, 8, 7);

   public final int fogColor;
   public final int baseLevel;
   public final int topLevel;
   public final int corridorWidth;
   public final int corridorWidthStart;
   public final int corridorWidthEnd;
   public final int corridorHeight;
   public final int[] corridorBaseLevels;
   private static boolean initSpawnLists = false;
   private LOTRBiomeSpawnList npcSpawnList = new LOTRBiomeSpawnList("UtumnoLevel_" + this.name());
   public Block brickBlock;
   public int brickMeta;
   public Block brickStairBlock;
   public Block brickGlowBlock;
   public int brickGlowMeta;
   public Block tileBlock;
   public int tileMeta;
   public Block pillarBlock;
   public int pillarMeta;
   private static Random lightRand = new Random();
   private static NoiseGeneratorPerlin noiseGenXZ = new NoiseGeneratorPerlin(new Random(5628506078940526L), 1);
   private static NoiseGeneratorPerlin noiseGenY = new NoiseGeneratorPerlin(new Random(1820268708369704034L), 1);
   private static NoiseGeneratorPerlin corridorNoiseY = new NoiseGeneratorPerlin(new Random(89230369345425L), 1);
   private static NoiseGeneratorPerlin corridorNoiseX = new NoiseGeneratorPerlin(new Random(824595069307073L), 1);
   private static NoiseGeneratorPerlin corridorNoiseZ = new NoiseGeneratorPerlin(new Random(759206035530266067L), 1);

   private LOTRUtumnoLevel(int fog, int base, int top, int cWidth, int cHeight) {
      this.fogColor = fog;
      this.baseLevel = base;
      this.topLevel = top;
      this.corridorWidth = cWidth;
      this.corridorWidthStart = 8 - cWidth / 2;
      this.corridorWidthEnd = this.corridorWidthStart + cWidth;
      this.corridorHeight = cHeight;
      List baseLevels = new ArrayList();
      int y = this.baseLevel;

      while(true) {
         y += this.corridorHeight * 2;
         if (y >= top - 5) {
            this.corridorBaseLevels = Ints.toArray(baseLevels);
            return;
         }

         baseLevels.add(y);
      }
   }

   public int getLowestCorridorFloor() {
      return this.corridorBaseLevels[0] - 1;
   }

   public int getHighestCorridorRoof() {
      return this.corridorBaseLevels[this.corridorBaseLevels.length - 1] + this.corridorHeight;
   }

   public LOTRBiomeSpawnList getNPCSpawnList() {
      return this.npcSpawnList;
   }

   public static LOTRUtumnoLevel forY(int y) {
      LOTRUtumnoLevel[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRUtumnoLevel level = var1[var3];
         if (y >= level.baseLevel) {
            return level;
         }
      }

      return FIRE;
   }

   public static void setupLevels() {
      if (!initSpawnLists) {
         ICE.brickBlock = LOTRMod.utumnoBrick;
         ICE.brickMeta = 2;
         ICE.brickStairBlock = LOTRMod.stairsUtumnoBrickIce;
         ICE.brickGlowBlock = LOTRMod.utumnoBrick;
         ICE.brickGlowMeta = 3;
         ICE.tileBlock = LOTRMod.utumnoBrick;
         ICE.tileMeta = 6;
         ICE.pillarBlock = LOTRMod.utumnoPillar;
         ICE.pillarMeta = 1;
         OBSIDIAN.brickBlock = LOTRMod.utumnoBrick;
         OBSIDIAN.brickMeta = 4;
         OBSIDIAN.brickStairBlock = LOTRMod.stairsUtumnoBrickObsidian;
         OBSIDIAN.brickGlowBlock = LOTRMod.utumnoBrick;
         OBSIDIAN.brickGlowMeta = 5;
         OBSIDIAN.tileBlock = LOTRMod.utumnoBrick;
         OBSIDIAN.tileMeta = 7;
         OBSIDIAN.pillarBlock = LOTRMod.utumnoPillar;
         OBSIDIAN.pillarMeta = 2;
         FIRE.brickBlock = LOTRMod.utumnoBrick;
         FIRE.brickMeta = 0;
         FIRE.brickStairBlock = LOTRMod.stairsUtumnoBrickFire;
         FIRE.brickGlowBlock = LOTRMod.utumnoBrick;
         FIRE.brickGlowMeta = 1;
         FIRE.tileBlock = LOTRMod.utumnoBrick;
         FIRE.tileMeta = 8;
         FIRE.pillarBlock = LOTRMod.utumnoPillar;
         FIRE.pillarMeta = 0;
         ICE.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_ICE, 10));
         OBSIDIAN.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_OBSIDIAN, 10));
         FIRE.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_FIRE, 10));
         initSpawnLists = true;
      }
   }

   public static void generateTerrain(World world, Random rand, int chunkX, int chunkZ, Block[] blocks, byte[] metadata) {
      boolean hugeHoleChunk = rand.nextInt(16) == 0;
      boolean hugeRavineChunk = rand.nextInt(16) == 0;
      long seed = world.func_72905_C();
      seed *= (long)(chunkX / 2) * 67839703L + (long)(chunkZ / 2) * 368093693L;
      lightRand.setSeed(seed);
      boolean chunkHasGlowing = lightRand.nextInt(4) > 0;

      for(int i = 0; i < 16; ++i) {
         for(int k = 0; k < 16; ++k) {
            int blockX = chunkX * 16 + i;
            int blockZ = chunkZ * 16 + k;
            double genNoiseXZHere = noiseGenXZ.func_151601_a((double)blockX * 0.2D, (double)blockZ * 0.2D);
            double corridorNoiseYHere = corridorNoiseY.func_151601_a((double)blockX * 0.02D, (double)blockZ * 0.02D) * 0.67D + corridorNoiseY.func_151601_a((double)blockX * 0.1D, (double)blockZ * 0.1D) * 0.33D;
            double corridorNoiseXHere = corridorNoiseX.func_151601_a((double)blockX * 0.02D, (double)blockZ * 0.02D) * 0.67D + corridorNoiseX.func_151601_a((double)blockX * 0.1D, (double)blockZ * 0.1D) * 0.33D;
            double corridorNoiseZHere = corridorNoiseZ.func_151601_a((double)blockX * 0.02D, (double)blockZ * 0.02D) * 0.67D + corridorNoiseZ.func_151601_a((double)blockX * 0.1D, (double)blockZ * 0.1D) * 0.33D;

            for(int j = 255; j >= 0; --j) {
               LOTRUtumnoLevel utumnoLevel = forY(j);
               int blockIndex = (k * 16 + i) * 256 + j;
               int actingChunkZ;
               if (j > 0 + rand.nextInt(5) && j < 255 - rand.nextInt(3)) {
                  double genNoiseYHere = noiseGenY.func_151601_a((double)j * 0.4D, 0.0D);
                  double genNoise = (genNoiseXZHere + genNoiseYHere * 0.5D) / 1.5D;
                  if (genNoise > -0.2D) {
                     blocks[blockIndex] = utumnoLevel.brickBlock;
                     metadata[blockIndex] = (byte)utumnoLevel.brickMeta;
                     if (chunkHasGlowing) {
                        boolean glowing = false;
                        if (utumnoLevel == ICE && rand.nextInt(16) == 0) {
                           glowing = true;
                        } else if (utumnoLevel == OBSIDIAN && rand.nextInt(12) == 0) {
                           glowing = true;
                        } else if (utumnoLevel == FIRE && rand.nextInt(8) == 0) {
                           glowing = true;
                        }

                        if (glowing) {
                           blocks[blockIndex] = utumnoLevel.brickGlowBlock;
                           metadata[blockIndex] = (byte)utumnoLevel.brickGlowMeta;
                        }
                     }
                  } else if (utumnoLevel == ICE) {
                     if (genNoise < -0.5D) {
                        blocks[blockIndex] = Blocks.field_150348_b;
                        metadata[blockIndex] = 0;
                     } else {
                        blocks[blockIndex] = Blocks.field_150403_cj;
                        metadata[blockIndex] = 0;
                     }
                  } else if (utumnoLevel == OBSIDIAN) {
                     if (genNoise < -0.5D) {
                        blocks[blockIndex] = Blocks.field_150406_ce;
                        metadata[blockIndex] = 15;
                     } else {
                        blocks[blockIndex] = Blocks.field_150343_Z;
                        metadata[blockIndex] = 0;
                     }
                  } else if (utumnoLevel == FIRE) {
                     blocks[blockIndex] = Blocks.field_150343_Z;
                     metadata[blockIndex] = 0;
                  }

                  int levelFuzz = 2;
                  if (j <= utumnoLevel.getLowestCorridorFloor() - levelFuzz || j >= utumnoLevel.getHighestCorridorRoof() + levelFuzz) {
                     blocks[blockIndex] = utumnoLevel.brickBlock;
                     metadata[blockIndex] = (byte)utumnoLevel.brickMeta;
                  }

                  if (genNoise < 0.5D) {
                     int[] var32 = utumnoLevel.corridorBaseLevels;
                     actingChunkZ = var32.length;

                     for(int var34 = 0; var34 < actingChunkZ; ++var34) {
                        int corridorBase = var32[var34];
                        if (j == corridorBase - 1) {
                           blocks[blockIndex] = utumnoLevel.tileBlock;
                           metadata[blockIndex] = (byte)utumnoLevel.tileMeta;
                        }
                     }
                  }
               } else {
                  blocks[blockIndex] = Blocks.field_150357_h;
               }

               int actingY = j + (int)Math.round(corridorNoiseYHere * 1.15D);
               actingY = MathHelper.func_76125_a(actingY, 0, 255);
               int actingX = blockX + (int)Math.round(corridorNoiseXHere * 1.7D);
               int actingZ = blockZ + (int)Math.round(corridorNoiseZHere * 1.7D);
               int actingXInChunk = actingX & 15;
               int actingZInChunk = actingZ & 15;
               int actingChunkX = actingX / 16;
               actingChunkZ = actingZ / 16;
               boolean carveHugeHole = hugeHoleChunk && actingY >= utumnoLevel.corridorBaseLevels[0] && actingY < utumnoLevel.corridorBaseLevels[utumnoLevel.corridorBaseLevels.length - 1];
               boolean carveHugeRavine = hugeRavineChunk && actingY >= utumnoLevel.corridorBaseLevels[0] && actingY < utumnoLevel.corridorBaseLevels[utumnoLevel.corridorBaseLevels.length - 1];
               boolean carveCorridor = false;
               int[] var37 = utumnoLevel.corridorBaseLevels;
               int var38 = var37.length;

               for(int var39 = 0; var39 < var38; ++var39) {
                  int corridorBase = var37[var39];
                  carveCorridor = actingY >= corridorBase && actingY < corridorBase + utumnoLevel.corridorHeight;
                  if (carveCorridor) {
                     break;
                  }
               }

               if (carveHugeHole && chunkX % 2 == 0 && chunkZ % 2 == 0) {
                  if (i >= utumnoLevel.corridorWidthStart + 1 && i <= utumnoLevel.corridorWidthEnd - 1 && k >= utumnoLevel.corridorWidthStart + 1 && k <= utumnoLevel.corridorWidthEnd - 1) {
                     blocks[blockIndex] = Blocks.field_150350_a;
                     metadata[blockIndex] = 0;
                  } else if (i >= utumnoLevel.corridorWidthStart && i <= utumnoLevel.corridorWidthEnd && k >= utumnoLevel.corridorWidthStart && k <= utumnoLevel.corridorWidthEnd) {
                     blocks[blockIndex] = utumnoLevel.brickGlowBlock;
                     metadata[blockIndex] = (byte)utumnoLevel.brickGlowMeta;
                  }
               }

               if (chunkX % 2 == 0) {
                  if (carveCorridor && actingZInChunk >= utumnoLevel.corridorWidthStart && actingZInChunk <= utumnoLevel.corridorWidthEnd) {
                     blocks[blockIndex] = Blocks.field_150350_a;
                     metadata[blockIndex] = 0;
                  }

                  if (carveHugeRavine && actingXInChunk >= utumnoLevel.corridorWidthStart + 1 && actingXInChunk <= utumnoLevel.corridorWidthEnd - 1) {
                     blocks[blockIndex] = Blocks.field_150350_a;
                     metadata[blockIndex] = 0;
                  }
               }

               if (chunkZ % 2 == 0) {
                  if (carveCorridor && actingXInChunk >= utumnoLevel.corridorWidthStart && actingXInChunk <= utumnoLevel.corridorWidthEnd) {
                     blocks[blockIndex] = Blocks.field_150350_a;
                     metadata[blockIndex] = 0;
                  }

                  if (carveHugeRavine && actingZInChunk >= utumnoLevel.corridorWidthStart + 1 && actingZInChunk <= utumnoLevel.corridorWidthEnd - 1) {
                     blocks[blockIndex] = Blocks.field_150350_a;
                     metadata[blockIndex] = 0;
                  }
               }
            }
         }
      }

   }
}
