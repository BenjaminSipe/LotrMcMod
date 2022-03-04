package lotr.common.world.mapgen.tpyr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRDimension;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenFarHaradJungle;
import lotr.common.world.biome.LOTRBiomeGenTauredainClearing;
import lotr.common.world.village.LOTRVillagePositionCache;
import lotr.common.world.village.LocationInfo;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

public class LOTRMapGenTauredainPyramid extends MapGenStructure {
   private static List spawnBiomes;
   private int spawnChance = 10;
   private static int minDist = 12;
   private static int separation = 24;

   private static void setupSpawnBiomes() {
      if (spawnBiomes == null) {
         spawnBiomes = new ArrayList();
         LOTRBiome[] var0 = LOTRDimension.MIDDLE_EARTH.biomeList;
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            LOTRBiome biome = var0[var2];
            boolean flag = false;
            if (biome instanceof LOTRBiomeGenFarHaradJungle && !(biome instanceof LOTRBiomeGenTauredainClearing)) {
               flag = true;
            }

            if (flag) {
               spawnBiomes.add(biome);
            }
         }
      }

   }

   protected boolean func_75047_a(int i, int k) {
      LOTRWorldChunkManager worldChunkMgr = (LOTRWorldChunkManager)this.field_75039_c.func_72959_q();
      LOTRVillagePositionCache cache = worldChunkMgr.getStructureCache(this);
      LocationInfo cacheLocation = cache.getLocationAt(i, k);
      if (cacheLocation != null) {
         return cacheLocation.isPresent();
      } else {
         setupSpawnBiomes();
         int i2 = MathHelper.func_76128_c((double)i / (double)separation);
         int k2 = MathHelper.func_76128_c((double)k / (double)separation);
         Random dRand = this.field_75039_c.func_72843_D(i2, k2, 190169976);
         i2 *= separation;
         k2 *= separation;
         i2 += dRand.nextInt(separation - minDist + 1);
         k2 += dRand.nextInt(separation - minDist + 1);
         if (i == i2 && k == k2) {
            int i1 = i * 16 + 8;
            int k1 = k * 16 + 8;
            if (this.field_75039_c.func_72959_q().func_76940_a(i1, k1, 0, spawnBiomes) && this.field_75038_b.nextInt(this.spawnChance) == 0) {
               return cache.markResult(i, k, LocationInfo.RANDOM_GEN_HERE).isPresent();
            }
         }

         return cache.markResult(i, k, LocationInfo.NONE_HERE).isPresent();
      }
   }

   protected StructureStart func_75049_b(int i, int j) {
      return new LOTRStructureTPyrStart(this.field_75039_c, this.field_75038_b, i, j);
   }

   public String func_143025_a() {
      return "LOTR.TPyr";
   }

   public static void register() {
      MapGenStructureIO.func_143034_b(LOTRStructureTPyrStart.class, "LOTR.TPyr");
      MapGenStructureIO.func_143031_a(LOTRComponentTauredainPyramid.class, "LOTR.TPyr.Pyramid");
   }
}
