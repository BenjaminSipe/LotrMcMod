package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTRWorldGenCampBase extends LOTRWorldGenStructureBase2 {
   protected Block tableBlock;
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block fenceGateBlock;
   protected Block farmBaseBlock;
   protected int farmBaseMeta;
   protected Block farmCropBlock;
   protected int farmCropMeta;
   protected boolean hasOrcTorches = false;
   protected boolean hasSkulls = false;

   public LOTRWorldGenCampBase(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      this.tableBlock = Blocks.field_150462_ai;
      this.brickBlock = Blocks.field_150347_e;
      this.brickMeta = 0;
      this.brickSlabBlock = Blocks.field_150333_U;
      this.brickSlabMeta = 3;
      this.fenceBlock = Blocks.field_150422_aJ;
      this.fenceMeta = 0;
      this.fenceGateBlock = Blocks.field_150396_be;
      this.farmBaseBlock = Blocks.field_150458_ak;
      this.farmBaseMeta = 7;
      this.farmCropBlock = Blocks.field_150464_aj;
      this.farmCropMeta = 7;
   }

   protected abstract LOTRWorldGenStructureBase2 createTent(boolean var1, Random var2);

   protected abstract LOTREntityNPC getCampCaptain(World var1, Random var2);

   protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (this.restrictions) {
         if (!isSurfaceStatic(world, i, j - 1, k)) {
            return false;
         }

         if (world.func_147439_a(i, j, k).func_149688_o().func_76224_d()) {
            return false;
         }
      }

      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int groundRange = 12;

      int highestHeight;
      int k1;
      int l;
      int tentX;
      int i1;
      for(highestHeight = -groundRange; highestHeight <= groundRange; ++highestHeight) {
         for(k1 = -groundRange; k1 <= groundRange; ++k1) {
            l = Math.abs(highestHeight);
            tentX = Math.abs(k1);
            if (l * l + tentX * tentX < groundRange * groundRange) {
               i1 = this.getTopBlock(world, highestHeight, k1);
               if (this.getBlock(world, highestHeight, i1 - 1, k1) == Blocks.field_150349_c && random.nextInt(5) != 0) {
                  this.setBlockAndMetadata(world, highestHeight, i1 - 1, k1, Blocks.field_150346_d, 1);
               }
            }
         }
      }

      highestHeight = 0;

      for(k1 = -1; k1 <= 1; ++k1) {
         for(l = -1; l <= 1; ++l) {
            tentX = this.getTopBlock(world, k1, l);
            if (tentX > highestHeight) {
               highestHeight = tentX;
            }
         }
      }

      this.originY = this.getY(highestHeight);
      this.generateCentrepiece(world, random, 0, 0, 0);
      LOTREntityNPC captain = this.getCampCaptain(world, random);
      if (captain != null) {
         captain.spawnRidingHorse = false;
         this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 24);
      }

      int i1;
      int k1;
      int i2;
      for(l = 0; l < 4; ++l) {
         tentX = MathHelper.func_76136_a(random, -3, 3);
         i1 = MathHelper.func_76136_a(random, 6, 12);
         i1 = 0;
         k1 = 0;
         if (l == 0) {
            i1 = tentX;
            k1 = i1;
         } else if (l == 1) {
            i1 = i1;
            k1 = -tentX;
         } else if (l == 2) {
            i1 = -tentX;
            k1 = -i1;
         } else if (l == 3) {
            i1 = -i1;
            k1 = tentX;
         }

         i2 = this.getTopBlock(world, i1, k1);
         this.generateSubstructure(this.createTent(this.notifyChanges, random), world, random, i1, i2, k1, l);
      }

      int highestFarmHeight;
      int k2;
      int scarecrowZ;
      int[] farmCoords;
      if (this.hasOrcTorches) {
         farmCoords = new int[]{-2, 2};
         tentX = farmCoords.length;

         for(i1 = 0; i1 < tentX; ++i1) {
            i1 = farmCoords[i1];
            int[] var24 = new int[]{-2, 2};
            highestFarmHeight = var24.length;

            for(i2 = 0; i2 < highestFarmHeight; ++i2) {
               k2 = var24[i2];
               scarecrowZ = this.getTopBlock(world, i1, k2);
               this.placeOrcTorch(world, i1, scarecrowZ, k2);
            }
         }
      }

      byte range;
      if (this.generateFarm()) {
         farmCoords = null;
         range = 12;
         int minFarmRange = 5;

         label241:
         for(i1 = 0; i1 < 32; ++i1) {
            k1 = MathHelper.func_76136_a(random, -range, range);
            highestFarmHeight = MathHelper.func_76136_a(random, -range, range);
            i2 = k1 * k1 + highestFarmHeight * highestFarmHeight;
            if (i2 > minFarmRange * minFarmRange) {
               for(k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
                  for(scarecrowZ = highestFarmHeight - 2; scarecrowZ <= highestFarmHeight + 2; ++scarecrowZ) {
                     int j2 = this.getTopBlock(world, k2, scarecrowZ) - 1;
                     if (!this.isSurface(world, k2, j2, scarecrowZ) || !this.isAir(world, k2, j2 + 1, scarecrowZ) && !this.isReplaceable(world, k2, j2 + 1, scarecrowZ)) {
                        continue label241;
                     }
                  }
               }

               farmCoords = new int[]{k1, highestFarmHeight};
               break;
            }
         }

         if (farmCoords != null) {
            i1 = farmCoords[0];
            k1 = farmCoords[1];
            highestFarmHeight = this.getTopBlock(world, i1, k1);

            for(i2 = i1 - 2; i2 <= i1 + 2; ++i2) {
               for(k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
                  scarecrowZ = this.getTopBlock(world, i2, k2);
                  if (scarecrowZ > highestFarmHeight) {
                     highestFarmHeight = scarecrowZ;
                  }
               }
            }

            for(i2 = i1 - 2; i2 <= i1 + 2; ++i2) {
               for(k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
                  for(scarecrowZ = highestFarmHeight - 2; !this.isOpaque(world, i2, scarecrowZ, k2) && this.getY(scarecrowZ) >= 0; --scarecrowZ) {
                     this.setBiomeFiller(world, i2, scarecrowZ, k2);
                     this.setGrassToDirt(world, i2, scarecrowZ - 1, k2);
                  }

                  if (Math.abs(i2 - i1) != 2 && Math.abs(k2 - k1) != 2) {
                     if (i2 == i1 && k2 == k1) {
                        this.setBlockAndMetadata(world, i2, highestFarmHeight - 1, k2, Blocks.field_150355_j, 0);
                     } else {
                        this.setBlockAndMetadata(world, i2, highestFarmHeight, k2, this.farmCropBlock, this.farmCropMeta);
                        this.setBlockAndMetadata(world, i2, highestFarmHeight - 1, k2, this.farmBaseBlock, this.farmBaseMeta);
                        this.setGrassToDirt(world, i2, highestFarmHeight - 2, k2);
                     }
                  } else {
                     this.setBlockAndMetadata(world, i2, highestFarmHeight, k2, this.fenceBlock, this.fenceMeta);
                     this.setBiomeTop(world, i2, highestFarmHeight - 1, k2);
                     this.setGrassToDirt(world, i2, highestFarmHeight - 2, k2);
                  }
               }
            }

            i2 = random.nextInt(4);
            if (i2 == 0) {
               this.setBlockAndMetadata(world, i1, highestFarmHeight, k1 + 2, this.fenceGateBlock, 0);
            } else if (i2 == 1) {
               this.setBlockAndMetadata(world, i1 - 2, highestFarmHeight, k1, this.fenceGateBlock, 1);
            } else if (i2 == 2) {
               this.setBlockAndMetadata(world, i1, highestFarmHeight, k1 - 2, this.fenceGateBlock, 2);
            } else if (i2 == 3) {
               this.setBlockAndMetadata(world, i1 + 2, highestFarmHeight, k1, this.fenceGateBlock, 3);
            }

            k2 = i1 + (random.nextBoolean() ? -2 : 2);
            scarecrowZ = k1 + (random.nextBoolean() ? -2 : 2);
            this.setBlockAndMetadata(world, k2, highestFarmHeight + 1, scarecrowZ, this.fenceBlock, this.fenceMeta);
            if (this.hasOrcTorches) {
               this.setBlockAndMetadata(world, k2, highestFarmHeight + 2, scarecrowZ, Blocks.field_150325_L, 12);
               this.placeSkull(world, random, k2, highestFarmHeight + 3, scarecrowZ);
            } else {
               this.setBlockAndMetadata(world, k2, highestFarmHeight + 2, scarecrowZ, Blocks.field_150407_cf, 0);
               this.setBlockAndMetadata(world, k2, highestFarmHeight + 3, scarecrowZ, Blocks.field_150423_aK, random.nextInt(4));
            }
         }
      }

      if (this.hasSkulls) {
         for(l = 0; l < 6; ++l) {
            range = 8;
            i1 = MathHelper.func_76136_a(random, -range, range);
            i1 = MathHelper.func_76136_a(random, -range, range);
            if (i1 * i1 + i1 * i1 > 20) {
               k1 = this.getTopBlock(world, i1, i1);
               if (this.isSurface(world, i1, k1 - 1, i1) && this.isReplaceable(world, i1, k1, i1) && this.isAir(world, i1, k1 + 1, i1)) {
                  this.setBlockAndMetadata(world, i1, k1, i1, this.fenceBlock, this.fenceMeta);
                  this.placeSkull(world, random, i1, k1 + 1, i1);
               }
            }
         }

         for(l = 0; l < 6; ++l) {
            range = 12;
            i1 = MathHelper.func_76136_a(random, -range, range);
            i1 = MathHelper.func_76136_a(random, -range, range);
            if (i1 * i1 + i1 * i1 > 20) {
               k1 = this.getTopBlock(world, i1, i1);
               if (this.isSurface(world, i1, k1 - 1, i1) && this.isReplaceable(world, i1, k1, i1) && this.isAir(world, i1, k1 + 1, i1)) {
                  this.placeSkull(world, random, i1, k1, i1);
               }
            }
         }
      }

      this.placeNPCRespawner(world, random, 0, 0, 0);
      return true;
   }

   protected boolean generateFarm() {
      return true;
   }

   protected void generateCentrepiece(World world, Random random, int i, int j, int k) {
      for(int i1 = i - 1; i1 <= i + 1; ++i1) {
         for(int k1 = k - 1; k1 <= k + 1; ++k1) {
            for(int j1 = j - 1; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            this.setBlockAndMetadata(world, i1, j, k1, this.brickSlabBlock, this.brickSlabMeta);
            this.setGrassToDirt(world, i1, j - 1, k1);
         }
      }

      this.setBlockAndMetadata(world, i, j, k, this.tableBlock, 0);
   }
}
