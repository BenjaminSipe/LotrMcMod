package lotr.common.world.map;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenRammasEchor extends LOTRWorldGenStructureBase2 {
   public static LOTRWorldGenRammasEchor INSTANCE = new LOTRWorldGenRammasEchor(false);
   private int centreX;
   private int centreZ;
   private int radius = 500;
   private int radiusSq;
   private double wallThick;
   private int wallTop;
   private int gateBottom;
   private int gateTop;

   private LOTRWorldGenRammasEchor(boolean flag) {
      super(flag);
      this.radiusSq = this.radius * this.radius;
      this.wallThick = 0.03D;
      this.wallTop = 85;
      this.gateBottom = 77;
      this.gateTop = 82;
      this.centreX = LOTRWaypoint.MINAS_TIRITH.getXCoord();
      this.centreZ = LOTRWaypoint.MINAS_TIRITH.getZCoord();
   }

   private double isPosInWall(int i, int k) {
      int dx = i - this.centreX;
      int dz = k - this.centreZ;
      int distSq = dx * dx + dz * dz;
      double circleDist = Math.abs((double)distSq / (double)this.radiusSq - 1.0D);
      return circleDist;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (this.isPosInWall(i + 8, k + 8) < this.wallThick * 3.0D) {
         for(int i1 = i; i1 <= i + 15; ++i1) {
            for(int k1 = k; k1 <= k + 15; ++k1) {
               double circleDist = this.isPosInWall(i1, k1);
               if (circleDist < this.wallThick) {
                  float roadNear = LOTRRoads.isRoadNear(i1, k1, 9);
                  boolean gate = roadNear >= 0.0F;
                  boolean fences = false;
                  boolean wallEdge = circleDist > 0.025D;
                  boolean ladder = false;

                  for(int j1 = this.wallTop; j1 > 0; --j1) {
                     if (fences) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150422_aJ, 0);
                     } else {
                        if (j1 >= this.wallTop && wallEdge) {
                           this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.brick2, 11);
                        } else if (j1 == this.wallTop && circleDist < 0.015D) {
                           this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150376_bx, 0);
                        } else {
                           this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.brick, 1);
                        }

                        if (wallEdge && j1 == this.wallTop && !ladder) {
                           this.setBlockAndMetadata(world, i1, j1 + 1, k1, LOTRMod.brick2, 11);
                           if (IntMath.mod(i1 + k1, 2) == 1) {
                              this.setBlockAndMetadata(world, i1, j1 + 2, k1, LOTRMod.slabSingle5, 3);
                           } else if (this.isTorchAt(i1, k1)) {
                              this.setBlockAndMetadata(world, i1, j1 + 2, k1, Blocks.field_150422_aJ, 0);
                              this.setBlockAndMetadata(world, i1, j1 + 3, k1, Blocks.field_150478_aa, 5);
                           }
                        }

                        if (ladder) {
                           this.setBlockAndMetadata(world, i1, j1, k1 - 1, Blocks.field_150468_ap, 2);
                        }
                     }

                     Block below = this.getBlock(world, i1, j1 - 1, k1);
                     this.setGrassToDirt(world, i1, j1 - 1, k1);
                     if (below == Blocks.field_150349_c || below == Blocks.field_150346_d || below == Blocks.field_150348_b) {
                        break;
                     }

                     if (gate) {
                        if (fences) {
                           if (j1 == this.gateBottom) {
                              break;
                           }
                        } else {
                           int lerpGateTop = this.gateBottom + Math.round((float)(this.gateTop - this.gateBottom) * MathHelper.func_76129_c(1.0F - roadNear));
                           if (j1 == lerpGateTop) {
                              if (circleDist <= 0.025D) {
                                 break;
                              }

                              fences = true;
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

   private boolean isTorchAt(int i, int k) {
      int torchRange = 12;
      int xmod = IntMath.mod(i, torchRange);
      int zmod = IntMath.mod(k, torchRange);
      return IntMath.mod(xmod + zmod, torchRange) == 0;
   }

   protected int getX(int x, int z) {
      return x;
   }

   protected int getZ(int x, int z) {
      return z;
   }

   protected int getY(int y) {
      return y;
   }

   protected int rotateMeta(Block block, int meta) {
      return meta;
   }
}
