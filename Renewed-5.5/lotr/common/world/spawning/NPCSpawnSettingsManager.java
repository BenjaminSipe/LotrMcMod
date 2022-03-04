package lotr.common.world.spawning;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRLog;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.init.LOTRBiomes;
import lotr.common.resources.InstancedJsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.LogicalSide;

public class NPCSpawnSettingsManager extends InstancedJsonReloadListener {
   private static final String SPAWNS_FOLDER = "npcs/biome_npc_spawns";
   private static final String LISTS_SUBFOLDER = "lists/";
   public static final NPCSpawnSettingsManager INSTANCE = new NPCSpawnSettingsManager();
   private NPCSpawnSettings currentLoadedSpawns;

   private NPCSpawnSettingsManager() {
      super("npcs/biome_npc_spawns", "NPCSpawnSettings", LogicalSide.SERVER);
   }

   private NPCSpawnSettings loadSpawnsFromJsons(IResourceManager resMgr, FactionSettings factionSettings, Map listJsons, Map biomeJsons) {
      List spawnLists = new ArrayList();
      Iterator var6 = listJsons.entrySet().iterator();

      while(var6.hasNext()) {
         Entry entry = (Entry)var6.next();
         ResourceLocation res = (ResourceLocation)entry.getKey();
         ResourceLocation spawnListName = trimSubFolderResource(res, "lists/");
         JsonObject spawnListJson = (JsonObject)entry.getValue();

         try {
            NPCSpawnList spawnList = NPCSpawnList.read(spawnListName, spawnListJson);
            if (spawnList != null) {
               spawnLists.add(spawnList);
            }
         } catch (Exception var14) {
            LOTRLog.warn("Failed to load NPC spawn list %s from file", spawnListName);
            var14.printStackTrace();
         }
      }

      NPCSpawnSettings spawnSettings = new NPCSpawnSettings(spawnLists);
      Map biomeSpawnListsPre = new HashMap();
      Iterator var17 = biomeJsons.entrySet().iterator();

      while(var17.hasNext()) {
         Entry entry = (Entry)var17.next();
         ResourceLocation biomeName = (ResourceLocation)entry.getKey();
         JsonObject biomeSpawnListJson = (JsonObject)entry.getValue();

         try {
            BiomeNPCSpawnList.PreLoaded biomeSpawnList = BiomeNPCSpawnList.PreLoaded.read(spawnSettings, factionSettings, biomeName, biomeSpawnListJson);
            if (biomeSpawnList != null) {
               biomeSpawnListsPre.put(biomeName, biomeSpawnList);
            }
         } catch (Exception var13) {
            LOTRLog.warn("Failed to load biome NPC spawn list for %s from file", biomeName);
            var13.printStackTrace();
         }
      }

      Map biomeSpawnLists = new HashMap();
      Iterator var20 = biomeSpawnListsPre.entrySet().iterator();

      while(var20.hasNext()) {
         Entry e = (Entry)var20.next();
         ResourceLocation biomeName = (ResourceLocation)e.getKey();
         BiomeNPCSpawnList fullyLoaded = ((BiomeNPCSpawnList.PreLoaded)e.getValue()).resolveParentAndFinaliseAfterLoad(biomeSpawnListsPre);
         if (fullyLoaded != null) {
            biomeSpawnLists.put(biomeName, fullyLoaded);
         }
      }

      spawnSettings.setBiomeSpawnLists(biomeSpawnLists);
      return spawnSettings;
   }

   public NPCSpawnSettings getCurrentLoadedSpawns() {
      return this.currentLoadedSpawns;
   }

   public static BiomeNPCSpawnList getSpawnsForBiome(Biome biome, IWorld world) {
      return INSTANCE.getCurrentLoadedSpawns().getSpawnsForBiomeOrFallbackEmpty(LOTRBiomes.getBiomeRegistryName(biome, world));
   }

   protected void apply(Map jsons, IResourceManager serverResMgr, IProfiler profiler) {
      Map listJsons = this.filterDataJsonsBySubFolder(jsons, "lists/");
      Map biomeJsons = this.filterDataJsonsByRootFolderOnly(jsons);
      FactionSettings factionSettings = FactionSettingsManager.serverInstance().getCurrentLoadedFactions();
      this.currentLoadedSpawns = this.loadSpawnsFromJsons(serverResMgr, factionSettings, listJsons, biomeJsons);
      this.logSpawnsLoad("Loaded serverside NPC spawn settings", this.currentLoadedSpawns);
   }

   private void logSpawnsLoad(String prefix, NPCSpawnSettings spawns) {
      LOTRLog.info("%s - %d spawn lists, %d biome spawn tables", prefix, spawns.getSpawnLists().size(), spawns.getBiomeSpawnLists().size());
   }
}
