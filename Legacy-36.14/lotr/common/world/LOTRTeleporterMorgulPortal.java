package lotr.common.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraft.world.Teleporter.PortalPosition;

public class LOTRTeleporterMorgulPortal extends Teleporter {
   private final WorldServer theWorld;
   private final LongHashMap portalPositions = new LongHashMap();
   private final List portalTimes = new ArrayList();
   private Random rand = new Random();

   public LOTRTeleporterMorgulPortal(WorldServer world) {
      super(world);
      this.theWorld = world;
   }

   public void func_77185_a(Entity entity, double d, double d1, double d2, float f) {
      if (!this.func_77184_b(entity, d, d1, d2, f)) {
         this.func_85188_a(entity);
         this.func_77184_b(entity, d, d1, d2, f);
      }

   }

   public boolean func_77184_b(Entity entity, double d, double d1, double d2, float f) {
      short range = 128;
      double distanceToPortal = -1.0D;
      int i = 0;
      int j = 0;
      int k = 0;
      int posX = MathHelper.func_76128_c(entity.field_70165_t);
      int posZ = MathHelper.func_76128_c(entity.field_70161_v);
      long chunkLocation = ChunkCoordIntPair.func_77272_a(posX, posZ);
      boolean isChunkLocationInPortalPositions = true;
      int i1;
      if (this.portalPositions.func_76161_b(chunkLocation)) {
         PortalPosition portalposition = (PortalPosition)this.portalPositions.func_76164_a(chunkLocation);
         distanceToPortal = 0.0D;
         i = portalposition.field_71574_a;
         j = portalposition.field_71572_b;
         k = portalposition.field_71573_c;
         portalposition.field_85087_d = this.theWorld.func_82737_E();
         isChunkLocationInPortalPositions = false;
      } else {
         for(i1 = posX - range; i1 <= posX + range; ++i1) {
            double xDistance = (double)i1 + 0.5D - entity.field_70165_t;

            for(int k1 = posZ - range; k1 <= posZ + range; ++k1) {
               double zDistance = (double)k1 + 0.5D - entity.field_70161_v;

               for(int j1 = this.theWorld.func_72940_L() - 1; j1 >= 0; --j1) {
                  boolean portalHere = true;

                  for(int i2 = i1 - 1; i2 <= i1 + 1; ++i2) {
                     for(int k2 = k1 - 1; k2 <= k1 + 1; ++k2) {
                        if (this.theWorld.func_147439_a(i2, j1, k2) != LOTRMod.morgulPortal) {
                           portalHere = false;
                        }
                     }
                  }

                  if (portalHere) {
                     double yDistance = (double)j1 + 0.5D - entity.field_70163_u;
                     double distanceSq = xDistance * xDistance + yDistance * yDistance + zDistance * zDistance;
                     if (distanceToPortal < 0.0D || distanceSq < distanceToPortal) {
                        distanceToPortal = distanceSq;
                        i = i1;
                        j = j1;
                        k = k1;
                     }
                  }
               }
            }
         }
      }

      if (distanceToPortal >= 0.0D) {
         if (isChunkLocationInPortalPositions) {
            this.portalPositions.func_76163_a(chunkLocation, new PortalPosition(this, i, j, k, this.theWorld.func_82737_E()));
            this.portalTimes.add(chunkLocation);
         }

         i1 = this.rand.nextInt(4);
         switch(i1) {
         case 0:
            entity.func_70012_b((double)(i - 2) + 0.5D, (double)(j + 1), (double)(k - 1) + 0.25D + (double)(this.rand.nextFloat() * 2.0F), entity.field_70177_z, entity.field_70125_A);
            break;
         case 1:
            entity.func_70012_b((double)(i + 2) + 0.5D, (double)(j + 1), (double)(k - 1) + 0.25D + (double)(this.rand.nextFloat() * 2.0F), entity.field_70177_z, entity.field_70125_A);
            break;
         case 2:
            entity.func_70012_b((double)(i - 1) + 0.25D + (double)(this.rand.nextFloat() * 2.0F), (double)(j + 1), (double)(k - 2) + 0.25D, entity.field_70177_z, entity.field_70125_A);
            break;
         case 3:
            entity.func_70012_b((double)(i - 1) + 0.25D + (double)(this.rand.nextFloat() * 2.0F), (double)(j + 1), (double)(k + 2) + 0.25D, entity.field_70177_z, entity.field_70125_A);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_85188_a(Entity entity) {
      byte range = 16;
      double distanceToPortal = -1.0D;
      int i = MathHelper.func_76128_c(entity.field_70165_t);
      int j = MathHelper.func_76128_c(entity.field_70163_u);
      int k = MathHelper.func_76128_c(entity.field_70161_v);
      int posX = i;
      int posY = j;
      int posZ = k;

      int i1;
      int j1;
      int i2;
      int k2;
      int j2;
      for(i1 = i - range; i1 <= i + range; ++i1) {
         double xDistance = (double)i1 + 0.5D - entity.field_70165_t;

         for(int k1 = k - range; k1 <= k + range; ++k1) {
            double zDistance = (double)k1 + 0.5D - entity.field_70161_v;

            label188:
            for(j1 = this.theWorld.func_72940_L() - 1; j1 >= 0; --j1) {
               if (this.theWorld.func_147437_c(i1, j1, k1)) {
                  while(j1 > 0 && this.theWorld.func_147437_c(i1, j1 - 1, k1)) {
                     --j1;
                  }

                  for(i2 = i1 - 2; i2 <= i1 + 2; ++i2) {
                     for(k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
                        for(j2 = -1; j2 <= 3; ++j2) {
                           int j3 = j1 + j2;
                           if (j2 < 0 && !LOTRMod.isOpaque(this.theWorld, i2, j3, k2) || j2 >= 0 && !this.theWorld.func_147437_c(i2, j3, k2)) {
                              continue label188;
                           }
                        }
                     }
                  }

                  double yDistance = (double)j1 + 0.5D - entity.field_70163_u;
                  double distanceSq = xDistance * xDistance + yDistance * yDistance + zDistance * zDistance;
                  if (distanceToPortal < 0.0D || distanceSq < distanceToPortal) {
                     distanceToPortal = distanceSq;
                     posX = i1;
                     posY = j1;
                     posZ = k1;
                  }
               }
            }
         }
      }

      i1 = posX;
      int actualPosY = posY;
      int actualPosZ = posZ;
      boolean generateRockBelow = false;
      if (distanceToPortal < 0.0D) {
         if (posY < 70) {
            posY = 70;
         }

         if (posY > this.theWorld.func_72940_L() - 10) {
            posY = this.theWorld.func_72940_L() - 10;
         }

         actualPosY = posY;
         generateRockBelow = true;
      }

      int k1;
      int i1;
      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            for(j1 = generateRockBelow ? -1 : 0; j1 <= 3; ++j1) {
               i2 = i1 + i1;
               k2 = actualPosY + j1;
               j2 = actualPosZ + k1;
               if (j1 > 0) {
                  if (Math.abs(i1) == 2 && Math.abs(k1) == 2) {
                     this.theWorld.func_147465_d(i2, k2, j2, LOTRMod.guldurilBrick, 0, 2);
                  } else {
                     this.theWorld.func_147465_d(i2, k2, j2, Blocks.field_150350_a, 0, 2);
                  }
               } else if (j1 < 0 && generateRockBelow) {
                  this.theWorld.func_147465_d(i2, k2, j2, LOTRMod.rock, 0, 2);
               } else {
                  boolean isFrame = i1 == -2 || i1 == 2 || k1 == -2 || k1 == 2;
                  this.theWorld.func_147465_d(i2, k2, j2, isFrame ? LOTRMod.rock : LOTRMod.morgulPortal, 0, 2);
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            for(j1 = generateRockBelow ? -2 : -1; j1 <= 4; ++j1) {
               i2 = i1 + i1;
               k2 = actualPosY + j1;
               j2 = actualPosZ + k1;
               this.theWorld.func_147459_d(i2, k2, j2, this.theWorld.func_147439_a(i2, k2, j2));
            }
         }
      }

      return true;
   }

   public void func_85189_a(long time) {
      if (time % 100L == 0L) {
         Iterator iterator = this.portalTimes.iterator();
         long j = time - 600L;

         while(true) {
            Long olong;
            PortalPosition portalposition;
            do {
               if (!iterator.hasNext()) {
                  return;
               }

               olong = (Long)iterator.next();
               portalposition = (PortalPosition)this.portalPositions.func_76164_a(olong);
            } while(portalposition != null && portalposition.field_85087_d >= j);

            iterator.remove();
            this.portalPositions.func_76159_d(olong);
         }
      }
   }
}
