package lotr.common.world.map;

import lotr.common.LOTRConfig;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public enum LOTRFixedStructures {
   SPAWN(809.5D, 729.5D),
   UTUMNO_ENTRANCE(1139.0D, 394.0D),
   MORDOR_CHERRY_TREE(1630.0D, 1170.0D);

   public int xCoord;
   public int zCoord;
   public static long nanoTimeElapsed;

   private LOTRFixedStructures(double x, double z) {
      this.xCoord = LOTRWaypoint.mapToWorldX(x);
      this.zCoord = LOTRWaypoint.mapToWorldZ(z);
   }

   public boolean isAt(World world, int x, int z) {
      if (!hasMapFeatures(world)) {
         return false;
      } else {
         return this.xCoord == x && this.zCoord == z;
      }
   }

   public double distanceSqTo(EntityLivingBase entity) {
      double dx = entity.field_70165_t - ((double)this.xCoord + 0.5D);
      double dz = entity.field_70161_v - ((double)this.zCoord + 0.5D);
      return dx * dx + dz * dz;
   }

   public static boolean generatesAt(int i, int k, int x, int z) {
      return i >> 4 == x >> 4 && k >> 4 == z >> 4;
   }

   public static boolean generatesAtMapImageCoords(int i, int k, int x, int z) {
      x = LOTRWaypoint.mapToWorldX((double)x);
      z = LOTRWaypoint.mapToWorldZ((double)z);
      return generatesAt(i, k, x, z);
   }

   public static boolean[] _mountainNear_structureNear(World world, int x, int z) {
      long l = System.nanoTime();
      boolean mountainNear = false;
      boolean structureNear = false;
      if (hasMapFeatures(world)) {
         if (LOTRMountains.mountainAt(x, z)) {
            mountainNear = true;
         }

         structureNear = structureNear(world, x, z, 256);
         if (!structureNear) {
            LOTRWaypoint[] var7 = LOTRWaypoint.values();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               LOTRWaypoint wp = var7[var9];
               double dx = (double)(x - wp.getXCoord());
               double dz = (double)(z - wp.getZCoord());
               double distSq = dx * dx + dz * dz;
               double range = 256.0D;
               double rangeSq = range * range;
               if (distSq < rangeSq) {
                  structureNear = true;
                  break;
               }
            }
         }

         if (!structureNear && LOTRRoads.isRoadNear(x, z, 32) >= 0.0F) {
            structureNear = true;
         }
      }

      nanoTimeElapsed += System.nanoTime() - l;
      return new boolean[]{mountainNear, structureNear};
   }

   public static boolean structureNear(World world, int x, int z, int range) {
      LOTRFixedStructures[] var4 = values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         LOTRFixedStructures str = var4[var6];
         double dx = (double)(x - str.xCoord);
         double dz = (double)(z - str.zCoord);
         double distSq = dx * dx + dz * dz;
         double rangeSq = (double)(range * range);
         if (distSq < rangeSq) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasMapFeatures(World world) {
      if (!LOTRConfig.generateMapFeatures) {
         return false;
      } else {
         return world.func_72912_H().func_76067_t() != LOTRMod.worldTypeMiddleEarthClassic;
      }
   }
}
