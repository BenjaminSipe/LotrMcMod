package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDwarvenMineEntrance extends LOTRWorldGenStructureBase2 {
   private Block plankBlock;
   private int plankMeta;
   private Block plankSlabBlock;
   private int plankSlabMeta;
   private Block logBlock;
   private int logMeta;
   private Block fenceBlock;
   private int fenceMeta;
   public boolean isRuined = false;

   public LOTRWorldGenDwarvenMineEntrance(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.plankBlock = Blocks.field_150344_f;
      this.plankMeta = 1;
      this.plankSlabBlock = Blocks.field_150376_bx;
      this.plankSlabMeta = 1;
      this.logBlock = Blocks.field_150364_r;
      this.logMeta = 1;
      this.fenceBlock = Blocks.field_150422_aJ;
      this.fenceMeta = 1;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, this.usingPlayer != null ? 5 : 0);
      this.setupRandomBlocks(random);
      int coordDepth = 40;
      if (this.usingPlayer != null) {
         coordDepth = Math.max(this.getY(-30), 5);
      }

      int relDepth = coordDepth - this.originY;

      int i1;
      int k1;
      int i2;
      int k2;
      int j1;
      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -4; k1 <= 4; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);

            for(j1 = 1; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (this.isRuined) {
               if (i2 != 4 && k2 != 4) {
                  this.setAir(world, i1, 0, k1);
               } else {
                  this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.pillar, 0);
               }
            } else {
               this.setBlockAndMetadata(world, i1, 0, k1, this.plankBlock, this.plankMeta);
               if (i2 == 4 && k2 >= 2 || k2 == 4 && i2 >= 2) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
               }

               if (i2 == 4 && k2 == 3 || k2 == 4 && i2 == 3) {
                  for(j1 = 2; j1 <= 3; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                  }
               }

               if (i2 == 4 || k2 == 4) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.fenceBlock, this.fenceMeta);
               }

               if (i2 == 0 || k2 == 0) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.fenceBlock, this.fenceMeta);
               }

               if (i2 == 0 && k2 == 0) {
                  for(j1 = 1; j1 <= 3; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                  }
               }

               if (i2 == 4 || k2 == 4 || i2 == 0 || k2 == 0 || i2 + k2 <= 2) {
                  this.setBlockAndMetadata(world, i1, 5, k1, this.plankSlabBlock, this.plankSlabMeta);
               }
            }

            if (i2 == 4 && k2 == 4) {
               for(j1 = 1; j1 <= 3; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.pillar, 0);
               }

               if (!this.isRuined) {
                  this.setBlockAndMetadata(world, i1, 4, k1, LOTRMod.brick3, 12);
                  this.setBlockAndMetadata(world, i1, 5, k1, LOTRMod.pillar, 0);
               }
            }
         }
      }

      for(i1 = -1; i1 > relDepth && this.getY(i1) >= 0; --i1) {
         for(k1 = -4; k1 <= 4; ++k1) {
            for(i2 = -4; i2 <= 4; ++i2) {
               k2 = Math.abs(k1);
               j1 = Math.abs(i2);
               if (k2 != 4 && j1 != 4) {
                  this.setAir(world, k1, i1, i2);
               } else if (this.isRuined && random.nextInt(20) == 0) {
                  this.setAir(world, k1, i1, i2);
               } else if (this.isRuined && random.nextInt(4) == 0) {
                  this.setBlockAndMetadata(world, k1, i1, i2, LOTRMod.brick4, 5);
               } else {
                  this.setBlockAndMetadata(world, k1, i1, i2, LOTRMod.brick, 6);
               }
            }
         }

         this.setBlockAndMetadata(world, -3, i1, -3, LOTRMod.pillar, 0);
         this.setBlockAndMetadata(world, -3, i1, 3, LOTRMod.pillar, 0);
         this.setBlockAndMetadata(world, 3, i1, -3, LOTRMod.pillar, 0);
         this.setBlockAndMetadata(world, 3, i1, 3, LOTRMod.pillar, 0);
         if (!this.isRuined && IntMath.mod(i1, 6) == 3) {
            this.setBlockAndMetadata(world, -3, i1, -3, LOTRMod.brick3, 12);
            this.setBlockAndMetadata(world, -3, i1, 3, LOTRMod.brick3, 12);
            this.setBlockAndMetadata(world, 3, i1, -3, LOTRMod.brick3, 12);
            this.setBlockAndMetadata(world, 3, i1, 3, LOTRMod.brick3, 12);
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            if (!this.isOpaque(world, i1, relDepth, k1)) {
               this.setBlockAndMetadata(world, i1, relDepth, k1, Blocks.field_150348_b, 0);
            }
         }
      }

      if (!this.isRuined) {
         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               this.setBlockAndMetadata(world, i1, relDepth, k1, LOTRMod.pillar, 0);
            }
         }
      } else {
         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               i2 = 0;
               if (random.nextInt(5) == 0) {
                  i2 += 1 + random.nextInt(1);
               }

               for(k2 = 0; k2 <= i2; ++k2) {
                  if (random.nextBoolean()) {
                     this.setBlockAndMetadata(world, i1, relDepth + i2, k1, LOTRMod.pillar, 0);
                  } else {
                     this.setBlockAndMetadata(world, i1, relDepth + i2, k1, Blocks.field_150348_b, 0);
                  }
               }
            }
         }
      }

      if (!this.isRuined) {
         for(i1 = 1; i1 > relDepth && this.getY(i1) >= 0; --i1) {
            this.setBlockAndMetadata(world, 0, i1, 0, this.logBlock, this.logMeta);
            this.setBlockAndMetadata(world, 0, i1, -1, Blocks.field_150468_ap, 2);
            this.setBlockAndMetadata(world, 0, i1, 1, Blocks.field_150468_ap, 3);
            this.setBlockAndMetadata(world, -1, i1, 0, Blocks.field_150468_ap, 5);
            this.setBlockAndMetadata(world, 1, i1, 0, Blocks.field_150468_ap, 4);
         }
      }

      for(i1 = relDepth + 1; i1 <= relDepth + 3; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            this.setAir(world, k1, i1, -4);
            this.setAir(world, k1, i1, 4);
         }

         for(k1 = -1; k1 <= 1; ++k1) {
            this.setAir(world, -4, i1, k1);
            this.setAir(world, 4, i1, k1);
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -4, relDepth + 1, i1, LOTRMod.slabSingle, 15);
         this.setBlockAndMetadata(world, 4, relDepth + 1, i1, LOTRMod.slabSingle, 15);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, relDepth + 1, -4, LOTRMod.slabSingle, 15);
      }

      if (!this.isRuined || random.nextInt(3) == 0) {
         this.setBlockAndMetadata(world, -4, relDepth + 1, 0, LOTRMod.dwarvenForge, 4);
      }

      if (!this.isRuined || random.nextInt(3) == 0) {
         this.setBlockAndMetadata(world, 4, relDepth + 1, 0, LOTRMod.dwarvenForge, 5);
      }

      if (!this.isRuined || random.nextInt(3) == 0) {
         this.setBlockAndMetadata(world, 0, relDepth + 1, -4, LOTRMod.dwarvenForge, 3);
      }

      return true;
   }
}
