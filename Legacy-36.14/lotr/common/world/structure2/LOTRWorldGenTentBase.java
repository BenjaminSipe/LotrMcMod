package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTRWorldGenTentBase extends LOTRWorldGenStructureBase2 {
   protected Block tentBlock;
   protected int tentMeta;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block tableBlock;
   protected LOTRChestContents chestContents;
   protected boolean hasOrcForge = false;
   protected boolean hasOrcTorches = false;

   public LOTRWorldGenTentBase(boolean flag) {
      super(flag);
   }

   protected boolean isOrcTent() {
      return true;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               j1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, j1, k1)) {
                  return false;
               }
            }
         }
      }

      int randomGround;
      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               BiomeGenBase biome = this.getBiome(world, i1, k1);
               if (biome instanceof LOTRBiomeGenMordor) {
                  randomGround = random.nextInt(3);
                  if (randomGround == 0) {
                     this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.rock, 0);
                  } else if (randomGround == 1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.mordorDirt, 0);
                  } else if (randomGround == 2) {
                     this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.mordorGravel, 0);
                  }
               } else {
                  randomGround = random.nextInt(3);
                  if (randomGround == 0) {
                     if (j1 == 0) {
                        this.setBiomeTop(world, i1, j1, k1);
                     } else {
                        this.setBiomeFiller(world, i1, j1, k1);
                     }
                  } else if (randomGround == 1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150351_n, 0);
                  } else if (randomGround == 2) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
                  }
               }

               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 3; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         int[] var13 = new int[]{-2, 2};
         j1 = var13.length;

         for(int var14 = 0; var14 < j1; ++var14) {
            randomGround = var13[var14];

            for(int j1 = 1; j1 <= 2; ++j1) {
               this.setBlockAndMetadata(world, randomGround, j1, i1, this.tentBlock, this.tentMeta);
            }

            this.setGrassToDirt(world, randomGround, 0, i1);
         }

         this.setBlockAndMetadata(world, -1, 3, i1, this.tentBlock, this.tentMeta);
         this.setBlockAndMetadata(world, 1, 3, i1, this.tentBlock, this.tentMeta);
         this.setBlockAndMetadata(world, 0, 4, i1, this.tentBlock, this.tentMeta);
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, -3, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 0, i1, 3, this.fenceBlock, this.fenceMeta);
      }

      if (this.hasOrcTorches) {
         this.placeOrcTorch(world, -1, 1, -3);
         this.placeOrcTorch(world, 1, 1, -3);
         this.placeOrcTorch(world, -1, 1, 3);
         this.placeOrcTorch(world, 1, 1, 3);
      } else {
         this.setBlockAndMetadata(world, -1, 2, -3, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, 1, 2, -3, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, -1, 2, 3, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, 1, 2, 3, Blocks.field_150478_aa, 1);
      }

      if (random.nextBoolean()) {
         if (this.hasOrcForge) {
            this.setBlockAndMetadata(world, -1, 1, 0, LOTRMod.orcForge, 4);
            this.setGrassToDirt(world, -1, 0, 0);
            this.setBlockAndMetadata(world, -1, 1, -1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -1, 1, 1, this.fenceBlock, this.fenceMeta);
         } else {
            this.placeChest(world, random, -1, 1, 0, 4, this.chestContents);
            this.setBlockAndMetadata(world, -1, 1, -1, Blocks.field_150462_ai, 0);
            this.setGrassToDirt(world, -1, 0, -1);
            this.setBlockAndMetadata(world, -1, 1, 1, this.tableBlock, 0);
            this.setGrassToDirt(world, -1, 0, 1);
         }
      } else if (this.hasOrcForge) {
         this.setBlockAndMetadata(world, 1, 1, 0, LOTRMod.orcForge, 5);
         this.setGrassToDirt(world, 1, 0, 0);
         this.setBlockAndMetadata(world, 1, 1, -1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 1, 1, 1, this.fenceBlock, this.fenceMeta);
      } else {
         this.placeChest(world, random, 1, 1, 0, 5, this.chestContents);
         this.setBlockAndMetadata(world, 1, 1, -1, Blocks.field_150462_ai, 0);
         this.setGrassToDirt(world, 1, 0, -1);
         this.setBlockAndMetadata(world, 1, 1, 1, this.tableBlock, 0);
         this.setGrassToDirt(world, 1, 0, 1);
      }

      return true;
   }
}
