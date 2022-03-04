package lotr.common.world.mapgen.dwarvenmine;

import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRDimension;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenBlueMountains;
import lotr.common.world.biome.LOTRBiomeGenBlueMountainsFoothills;
import lotr.common.world.biome.LOTRBiomeGenErebor;
import lotr.common.world.biome.LOTRBiomeGenGreyMountains;
import lotr.common.world.biome.LOTRBiomeGenIronHills;
import lotr.common.world.village.LOTRVillagePositionCache;
import lotr.common.world.village.LocationInfo;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

public class LOTRMapGenDwarvenMine extends MapGenStructure {
   private static List spawnBiomes;
   private int spawnChance = 150;
   private static List spawnBiomesRuined;
   private int spawnChanceRuined = 500;

   private static void setupSpawnBiomes() {
      if (spawnBiomes == null) {
         spawnBiomes = new ArrayList();
         spawnBiomesRuined = new ArrayList();
         LOTRBiome[] var0 = LOTRDimension.MIDDLE_EARTH.biomeList;
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            LOTRBiome biome = var0[var2];
            boolean mine = false;
            boolean ruined = false;
            if (biome instanceof LOTRBiomeGenIronHills) {
               mine = true;
            }

            if (biome instanceof LOTRBiomeGenBlueMountains && !(biome instanceof LOTRBiomeGenBlueMountainsFoothills)) {
               mine = true;
            }

            if (biome instanceof LOTRBiomeGenGreyMountains) {
               ruined = true;
            }

            if (biome instanceof LOTRBiomeGenErebor) {
               mine = true;
            }

            if (mine) {
               spawnBiomes.add(biome);
            }

            if (ruined) {
               spawnBiomesRuined.add(biome);
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
         int i1 = i * 16 + 8;
         int k1 = k * 16 + 8;
         setupSpawnBiomes();
         if (this.field_75039_c.func_72959_q().func_76940_a(i1, k1, 0, spawnBiomes)) {
            if (this.field_75038_b.nextInt(this.spawnChance) == 0) {
               return cache.markResult(i, k, LocationInfo.RANDOM_GEN_HERE).isPresent();
            }
         } else if (this.field_75039_c.func_72959_q().func_76940_a(i1, k1, 0, spawnBiomesRuined) && this.field_75038_b.nextInt(this.spawnChanceRuined) == 0) {
            return cache.markResult(i, k, LocationInfo.RANDOM_GEN_HERE).isPresent();
         }

         return cache.markResult(i, k, LocationInfo.NONE_HERE).isPresent();
      }
   }

   protected StructureStart func_75049_b(int i, int k) {
      int i1 = i * 16 + 8;
      int k1 = k * 16 + 8;
      BiomeGenBase biome = this.field_75039_c.func_72959_q().func_76935_a(i1, k1);
      boolean ruined = spawnBiomesRuined.contains(biome);
      return new LOTRStructureDwarvenMineStart(this.field_75039_c, this.field_75038_b, i, k, ruined);
   }

   public String func_143025_a() {
      return "LOTR.DwarvenMine";
   }

   public static void register() {
      MapGenStructureIO.func_143034_b(LOTRStructureDwarvenMineStart.class, "LOTR.DwarvenMine");
      MapGenStructureIO.func_143031_a(LOTRComponentDwarvenMineEntrance.class, "LOTR.DwarvenMine.Entrance");
      MapGenStructureIO.func_143031_a(LOTRComponentDwarvenMineCorridor.class, "LOTR.DwarvenMine.Corridor");
      MapGenStructureIO.func_143031_a(LOTRComponentDwarvenMineCrossing.class, "LOTR.DwarvenMine.Crossing");
      MapGenStructureIO.func_143031_a(LOTRComponentDwarvenMineStairs.class, "LOTR.DwarvenMine.Stairs");
   }
}
