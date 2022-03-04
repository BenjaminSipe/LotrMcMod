package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenRhudaurCastle extends LOTRWorldGenStructureBase2 {
   private Block brickBlock;
   private int brickMeta;
   private Block brickSlabBlock;
   private int brickSlabMeta;
   private Block brickCrackedBlock;
   private int brickCrackedMeta;
   private Block brickCrackedSlabBlock;
   private int brickCrackedSlabMeta;

   public LOTRWorldGenRhudaurCastle(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      if (random.nextBoolean()) {
         this.brickBlock = Blocks.field_150417_aV;
         this.brickMeta = 0;
         this.brickSlabBlock = Blocks.field_150333_U;
         this.brickSlabMeta = 5;
         this.brickCrackedBlock = Blocks.field_150417_aV;
         this.brickCrackedMeta = 2;
         this.brickCrackedSlabBlock = LOTRMod.slabSingleV;
         this.brickCrackedSlabMeta = 1;
      } else {
         this.brickBlock = LOTRMod.brick2;
         this.brickMeta = 0;
         this.brickSlabBlock = LOTRMod.slabSingle3;
         this.brickSlabMeta = 3;
         this.brickCrackedBlock = LOTRMod.brick2;
         this.brickCrackedMeta = 1;
         this.brickCrackedSlabBlock = LOTRMod.slabSingle3;
         this.brickCrackedSlabMeta = 4;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int width = MathHelper.func_76136_a(random, 6, 15);
      int height = MathHelper.func_76136_a(random, 3, 8);

      int i1;
      int k1;
      int i2;
      int k2;
      for(i1 = -width; i1 <= width; ++i1) {
         for(k1 = -width; k1 <= width; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            if (i2 != width && k2 != width) {
               int j1;
               if (random.nextInt(16) == 0) {
                  j1 = this.getTopBlock(world, i1, k1) - 1;
                  if (this.isSurface(world, i1, j1, k1)) {
                     if (random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150351_n, 0);
                     } else {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150347_e, 0);
                     }
                  }
               }

               if (random.nextInt(50) == 0) {
                  j1 = this.getTopBlock(world, i1, k1) - 1;
                  if (this.isSurface(world, i1, j1, k1)) {
                     if (random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, i1, j1 + 1, k1, this.brickCrackedSlabBlock, this.brickCrackedSlabMeta);
                     } else {
                        this.setBlockAndMetadata(world, i1, j1 + 1, k1, this.brickSlabBlock, this.brickSlabMeta);
                     }

                     this.setGrassToDirt(world, i1, j1, k1);
                  }
               }
            } else {
               float f = MathHelper.func_151240_a(random, 0.7F, 1.0F);
               int h = Math.round((float)height * f);
               int b = 1;
               if (k1 == -width && i2 <= 1) {
                  b = 4;
               }

               if (IntMath.mod(i2 + k2, 2) == IntMath.mod(width, 2)) {
                  ++h;
               }

               int top = this.getTopBlock(world, i1, k1) - 1;
               boolean foundSurface = false;

               int j1;
               for(j1 = top; j1 >= top - 16; --j1) {
                  if (this.isSurface(world, i1, j1, k1)) {
                     foundSurface = true;
                     break;
                  }
               }

               if (foundSurface) {
                  for(int j2 = b; j2 <= h; ++j2) {
                     int j3 = j1 + j2;
                     boolean low = j2 < (int)((float)height * 0.5F) && j2 < h;
                     if (!low || random.nextInt(40) != 0) {
                        boolean slab = low && random.nextInt(20) == 0 || j2 == h && random.nextInt(5) == 0;
                        boolean cracked = random.nextInt(4) == 0;
                        if (cracked) {
                           if (slab) {
                              this.setBlockAndMetadata(world, i1, j3, k1, this.brickCrackedSlabBlock, this.brickCrackedSlabMeta);
                           } else {
                              this.setBlockAndMetadata(world, i1, j3, k1, this.brickCrackedBlock, this.brickCrackedMeta);
                           }
                        } else if (slab) {
                           this.setBlockAndMetadata(world, i1, j3, k1, this.brickSlabBlock, this.brickSlabMeta);
                        } else {
                           this.setBlockAndMetadata(world, i1, j3, k1, this.brickBlock, this.brickMeta);
                        }

                        if (j2 == 1) {
                           this.setGrassToDirt(world, i1, j3 - 1, k1);
                        }
                     }
                  }
               }
            }
         }
      }

      i1 = width - 1;
      k1 = width - 1;
      if (random.nextBoolean()) {
         i1 *= -1;
      }

      if (random.nextBoolean()) {
         k1 *= -1;
      }

      i2 = this.getTopBlock(world, i1, k1);
      if (this.isSurface(world, i1, i2 - 1, k1)) {
         k2 = Direction.field_71582_c[random.nextInt(4)];
         this.setBlockAndMetadata(world, i1, i2, k1, LOTRMod.chestStone, k2);
         this.fillChest(world, random, i1, i2, k1, LOTRChestContents.RUINED_HOUSE, 5);
         this.fillChest(world, random, i1, i2, k1, LOTRChestContents.ORC_DUNGEON, 4);
         this.fillChest(world, random, i1, i2, k1, LOTRChestContents.DUNEDAIN_TOWER, 4);
      }

      return true;
   }
}
