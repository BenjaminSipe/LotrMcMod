package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenBigTrees extends WorldGenAbstractTree {
   private static final byte[] otherCoordPairs = new byte[]{2, 0, 0, 1, 2, 1};
   private Random rand = new Random();
   private World worldObj;
   private int[] basePos = new int[]{0, 0, 0};
   private int heightLimit;
   private int height;
   private double heightAttenuation = 0.618D;
   private double branchDensity = 1.0D;
   private double branchSlope = 0.381D;
   private double scaleWidth = 1.0D;
   private double leafDensity = 1.0D;
   private int heightLimitLimit = 12;
   private int leafDistanceLimit = 4;
   private int[][] leafNodes;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenBigTrees(boolean flag, Block block, int i, Block block1, int j) {
      super(flag);
      this.woodBlock = block;
      this.woodMeta = i;
      this.leafBlock = block1;
      this.leafMeta = i;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      this.worldObj = world;
      long l = random.nextLong();
      this.rand.setSeed(l);
      this.basePos[0] = i;
      this.basePos[1] = j;
      this.basePos[2] = k;
      if (this.heightLimit == 0) {
         this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
      }

      if (!this.validTreeLocation()) {
         return false;
      } else {
         this.generateLeafNodeList();
         this.generateLeaves();
         this.generateTrunk();
         this.generateLeafNodeBases();
         return true;
      }
   }

   private void generateLeafNodeList() {
      this.height = (int)((double)this.heightLimit * this.heightAttenuation);
      if (this.height >= this.heightLimit) {
         this.height = this.heightLimit - 1;
      }

      int i = (int)(1.382D + Math.pow(this.leafDensity * (double)this.heightLimit / 13.0D, 2.0D));
      if (i < 1) {
         i = 1;
      }

      int[][] aint = new int[i * this.heightLimit][4];
      int j = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
      int k = 1;
      int l = this.basePos[1] + this.height;
      int i1 = j - this.basePos[1];
      aint[0][0] = this.basePos[0];
      aint[0][1] = j;
      aint[0][2] = this.basePos[2];
      aint[0][3] = l;
      --j;

      while(true) {
         while(i1 >= 0) {
            int j1 = 0;
            float f = this.layerSize(i1);
            if (f < 0.0F) {
               --j;
               --i1;
            } else {
               for(double d0 = 0.5D; j1 < i; ++j1) {
                  double d1 = this.scaleWidth * (double)f * ((double)this.rand.nextFloat() + 0.328D);
                  double d2 = (double)this.rand.nextFloat() * 2.0D * 3.141592653589793D;
                  int k1 = MathHelper.func_76128_c(d1 * Math.sin(d2) + (double)this.basePos[0] + d0);
                  int l1 = MathHelper.func_76128_c(d1 * Math.cos(d2) + (double)this.basePos[2] + d0);
                  int[] aint1 = new int[]{k1, j, l1};
                  int[] aint2 = new int[]{k1, j + this.leafDistanceLimit, l1};
                  if (this.checkBlockLine(aint1, aint2) == -1) {
                     int[] aint3 = new int[]{this.basePos[0], this.basePos[1], this.basePos[2]};
                     double d3 = Math.sqrt(Math.pow((double)Math.abs(this.basePos[0] - aint1[0]), 2.0D) + Math.pow((double)Math.abs(this.basePos[2] - aint1[2]), 2.0D));
                     double d4 = d3 * this.branchSlope;
                     if ((double)aint1[1] - d4 > (double)l) {
                        aint3[1] = l;
                     } else {
                        aint3[1] = (int)((double)aint1[1] - d4);
                     }

                     if (this.checkBlockLine(aint3, aint1) == -1) {
                        aint[k][0] = k1;
                        aint[k][1] = j;
                        aint[k][2] = l1;
                        aint[k][3] = aint3[1];
                        ++k;
                     }
                  }
               }

               --j;
               --i1;
            }
         }

         this.leafNodes = new int[k][4];
         System.arraycopy(aint, 0, this.leafNodes, 0, k);
         return;
      }
   }

   private void genTreeLayer(int par1, int par2, int par3, float par4, byte par5, Block par6, int meta) {
      int i1 = (int)((double)par4 + 0.618D);
      byte b1 = otherCoordPairs[par5];
      byte b2 = otherCoordPairs[par5 + 3];
      int[] aint = new int[]{par1, par2, par3};
      int[] aint1 = new int[]{0, 0, 0};
      int j1 = -i1;
      int k1 = -i1;

      label33:
      for(aint1[par5] = aint[par5]; j1 <= i1; ++j1) {
         aint1[b1] = aint[b1] + j1;
         k1 = -i1;

         while(true) {
            while(true) {
               if (k1 > i1) {
                  continue label33;
               }

               double d0 = Math.pow((double)Math.abs(j1) + 0.5D, 2.0D) + Math.pow((double)Math.abs(k1) + 0.5D, 2.0D);
               if (d0 > (double)(par4 * par4)) {
                  ++k1;
               } else {
                  aint1[b2] = aint[b2] + k1;
                  Block block = this.worldObj.func_147439_a(aint1[0], aint1[1], aint1[2]);
                  if (block.func_149688_o() != Material.field_151579_a && !block.isLeaves(this.worldObj, aint1[0], aint1[1], aint1[2])) {
                     ++k1;
                  } else {
                     this.func_150516_a(this.worldObj, aint1[0], aint1[1], aint1[2], par6, meta);
                     ++k1;
                  }
               }
            }
         }
      }

   }

   private float layerSize(int par1) {
      if ((double)par1 < (double)((float)this.heightLimit) * 0.3D) {
         return -1.618F;
      } else {
         float f = (float)this.heightLimit / 2.0F;
         float f1 = (float)this.heightLimit / 2.0F - (float)par1;
         float f2;
         if (f1 == 0.0F) {
            f2 = f;
         } else if (Math.abs(f1) >= f) {
            f2 = 0.0F;
         } else {
            f2 = (float)Math.sqrt(Math.pow((double)Math.abs(f), 2.0D) - Math.pow((double)Math.abs(f1), 2.0D));
         }

         f2 *= 0.5F;
         return f2;
      }
   }

   private float leafSize(int par1) {
      return par1 >= 0 && par1 < this.leafDistanceLimit ? (par1 != 0 && par1 != this.leafDistanceLimit - 1 ? 3.0F : 2.0F) : -1.0F;
   }

   private void generateLeafNode(int i, int j, int k) {
      int j1 = j;

      for(int j2 = j + this.leafDistanceLimit; j1 < j2; ++j1) {
         float f = this.leafSize(j1 - j);
         this.genTreeLayer(i, j1, k, f, (byte)1, this.leafBlock, this.leafMeta);
      }

   }

   private void placeBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, Block block, int meta) {
      int[] aint2 = new int[]{0, 0, 0};
      byte b0 = 0;

      byte b1;
      for(b1 = 0; b0 < 3; ++b0) {
         aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];
         if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
            b1 = b0;
         }
      }

      if (aint2[b1] != 0) {
         byte b2 = otherCoordPairs[b1];
         byte b3 = otherCoordPairs[b1 + 3];
         byte b4;
         if (aint2[b1] > 0) {
            b4 = 1;
         } else {
            b4 = -1;
         }

         double d0 = (double)aint2[b2] / (double)aint2[b1];
         double d1 = (double)aint2[b3] / (double)aint2[b1];
         int[] aint3 = new int[]{0, 0, 0};
         int j = 0;

         for(int k = aint2[b1] + b4; j != k; j += b4) {
            aint3[b1] = MathHelper.func_76128_c((double)(par1ArrayOfInteger[b1] + j) + 0.5D);
            aint3[b2] = MathHelper.func_76128_c((double)par1ArrayOfInteger[b2] + (double)j * d0 + 0.5D);
            aint3[b3] = MathHelper.func_76128_c((double)par1ArrayOfInteger[b3] + (double)j * d1 + 0.5D);
            byte b5 = 0;
            int l = Math.abs(aint3[0] - par1ArrayOfInteger[0]);
            int i1 = Math.abs(aint3[2] - par1ArrayOfInteger[2]);
            int j1 = Math.max(l, i1);
            if (j1 > 0) {
               if (l == j1) {
                  b5 = 4;
               } else if (i1 == j1) {
                  b5 = 8;
               }
            }

            this.func_150516_a(this.worldObj, aint3[0], aint3[1], aint3[2], block, meta | b5);
         }
      }

   }

   private void generateLeaves() {
      int i = 0;

      for(int j = this.leafNodes.length; i < j; ++i) {
         int k = this.leafNodes[i][0];
         int l = this.leafNodes[i][1];
         int i1 = this.leafNodes[i][2];
         this.generateLeafNode(k, l, i1);
      }

   }

   private boolean leafNodeNeedsBase(int par1) {
      return (double)par1 >= (double)this.heightLimit * 0.2D;
   }

   private void generateTrunk() {
      int i = this.basePos[0];
      int j = this.basePos[1];
      int j1 = this.basePos[1] + this.height;
      int k = this.basePos[2];
      int[] aint = new int[]{i, j, k};
      int[] aint1 = new int[]{i, j1, k};
      this.placeBlockLine(aint, aint1, this.woodBlock, this.woodMeta);
      this.worldObj.func_147439_a(i, j - 1, k).onPlantGrow(this.worldObj, i, j - 1, k, i, j, k);
   }

   private void generateLeafNodeBases() {
      int i = 0;
      int j = this.leafNodes.length;

      for(int[] aint = new int[]{this.basePos[0], this.basePos[1], this.basePos[2]}; i < j; ++i) {
         int[] aint1 = this.leafNodes[i];
         int[] aint2 = new int[]{aint1[0], aint1[1], aint1[2]};
         aint[1] = aint1[3];
         int k = aint[1] - this.basePos[1];
         if (this.leafNodeNeedsBase(k)) {
            this.placeBlockLine(aint, aint2, this.woodBlock, this.woodMeta);
         }
      }

   }

   private int checkBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger) {
      int[] aint2 = new int[]{0, 0, 0};
      byte b0 = 0;

      byte b1;
      for(b1 = 0; b0 < 3; ++b0) {
         aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];
         if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
            b1 = b0;
         }
      }

      if (aint2[b1] == 0) {
         return -1;
      } else {
         byte b2 = otherCoordPairs[b1];
         byte b3 = otherCoordPairs[b1 + 3];
         byte b4;
         if (aint2[b1] > 0) {
            b4 = 1;
         } else {
            b4 = -1;
         }

         double d0 = (double)aint2[b2] / (double)aint2[b1];
         double d1 = (double)aint2[b3] / (double)aint2[b1];
         int[] aint3 = new int[]{0, 0, 0};
         int i = 0;

         int j;
         for(j = aint2[b1] + b4; i != j; i += b4) {
            aint3[b1] = par1ArrayOfInteger[b1] + i;
            aint3[b2] = MathHelper.func_76128_c((double)par1ArrayOfInteger[b2] + (double)i * d0);
            aint3[b3] = MathHelper.func_76128_c((double)par1ArrayOfInteger[b3] + (double)i * d1);
            Block block = this.worldObj.func_147439_a(aint3[0], aint3[1], aint3[2]);
            if (block.func_149688_o() != Material.field_151579_a && !block.isLeaves(this.worldObj, aint3[0], aint3[1], aint3[2])) {
               break;
            }
         }

         return i == j ? -1 : Math.abs(i);
      }
   }

   private boolean validTreeLocation() {
      int[] aint = new int[]{this.basePos[0], this.basePos[1], this.basePos[2]};
      int[] aint1 = new int[]{this.basePos[0], this.basePos[1] + this.heightLimit - 1, this.basePos[2]};
      Block block = this.worldObj.func_147439_a(this.basePos[0], this.basePos[1] - 1, this.basePos[2]);
      if (!block.canSustainPlant(this.worldObj, this.basePos[0], this.basePos[1] - 1, this.basePos[2], ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
         return false;
      } else {
         int j = this.checkBlockLine(aint, aint1);
         if (j == -1) {
            return true;
         } else if (j < 6) {
            return false;
         } else {
            this.heightLimit = j;
            return true;
         }
      }
   }
}
