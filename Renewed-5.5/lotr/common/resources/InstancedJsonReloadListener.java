package lotr.common.resources;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.packs.ModFileResourcePack;
import net.minecraftforge.fml.packs.ResourcePackLoader;
import org.apache.commons.lang3.tuple.Pair;

public abstract class InstancedJsonReloadListener extends JsonReloadListener {
   protected static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
   protected static final String JSON_EXTENSION = ".json";
   private final String rootFolder;
   protected final String loaderNameForLogging;
   protected final LogicalSide side;

   public InstancedJsonReloadListener(String folder, String name, LogicalSide side) {
      super(GSON, folder);
      this.rootFolder = folder;
      this.loaderNameForLogging = name;
      this.side = side;
   }

   public final LogicalSide getSide() {
      return this.side;
   }

   protected JsonObject loadDataJsonIfExists(Map jsons, ResourceLocation targetPath) {
      Optional optEntry = jsons.entrySet().stream().filter((entry) -> {
         ResourceLocation shortenedPath = (ResourceLocation)entry.getKey();
         return this.getPreparedPath(shortenedPath).equals(targetPath);
      }).findFirst();
      if (optEntry.isPresent()) {
         return ((JsonElement)((Entry)optEntry.get()).getValue()).getAsJsonObject();
      } else {
         LOTRLog.error("%s datapack load missing %s", this.loaderNameForLogging, targetPath);
         return null;
      }
   }

   protected Map filterDataJsonsBySubFolder(Map jsons, String subFolder) {
      Map rootJsons = (Map)jsons.entrySet().stream().filter((e) -> {
         ResourceLocation res = (ResourceLocation)e.getKey();
         String resPath = res.func_110623_a();
         return resPath.startsWith(subFolder) && !resPath.substring(subFolder.length()).contains("/");
      }).collect(jsonElemToObjMapCollector());
      this.extractAndApplyDataDirectorySettings(rootJsons, subFolder);
      return rootJsons;
   }

   protected Map filterDataJsonsByRootFolderOnly(Map jsons) {
      Map subJsons = (Map)jsons.entrySet().stream().filter((e) -> {
         ResourceLocation res = (ResourceLocation)e.getKey();
         return !res.func_110623_a().contains("/");
      }).collect(jsonElemToObjMapCollector());
      this.extractAndApplyDataDirectorySettings(subJsons, (String)null);
      return subJsons;
   }

   private void extractAndApplyDataDirectorySettings(Map jsons, String subFolder) {
      Entry settingsEntry = this.extractDataDirectorySettingsJson(jsons, subFolder);
      if (settingsEntry != null) {
         ResourceLocation settingsRes = (ResourceLocation)settingsEntry.getKey();
         JsonObject settingsJson = (JsonObject)settingsEntry.getValue();
         DataDirectorySettings settings = DataDirectorySettings.read(settingsRes, settingsJson);
         this.removeResourcesExcludedInSettings(jsons, subFolder, settings);
      }

   }

   private Entry extractDataDirectorySettingsJson(Map jsons, String subFolder) {
      Optional settingsResOpt = jsons.keySet().stream().filter((res) -> {
         return this.getPreparedPath(res).func_110623_a().endsWith("/_settings.json");
      }).findFirst();
      if (settingsResOpt.isPresent()) {
         ResourceLocation settingsRes = (ResourceLocation)settingsResOpt.get();
         JsonObject settingsJson = (JsonObject)jsons.remove(settingsRes);
         return Pair.of(settingsRes, settingsJson);
      } else {
         return null;
      }
   }

   private void removeResourcesExcludedInSettings(Map jsons, String subFolder, DataDirectorySettings settings) {
      int sizeBefore = jsons.size();
      Set toRemove = new HashSet();
      Iterator var6 = jsons.keySet().iterator();

      while(var6.hasNext()) {
         ResourceLocation res = (ResourceLocation)var6.next();
         if (settings.shouldExclude(trimSubFolderResource(res, subFolder))) {
            toRemove.add(res);
         }
      }

      toRemove.forEach(jsons::remove);
      int numRemoved = sizeBefore - jsons.size();
      LOTRLog.info("Excluded %d resources in folder '%s' based on the %s", numRemoved, this.getFullFolderName(subFolder), "_settings.json");
   }

   protected static ResourceLocation trimSubFolderResource(ResourceLocation res, String subFolder) {
      return subFolder == null ? res : new ResourceLocation(res.func_110624_b(), res.func_110623_a().substring(subFolder.length()));
   }

   protected Map loadJsonResourceVersionsFromAllDatapacks(Set jsonPaths, IResourceManager resMgr) {
      return (Map)jsonPaths.stream().collect(Collectors.toMap((res) -> {
         return res;
      }, (res) -> {
         ResourceLocation fullRes = this.getPreparedPath(res);

         try {
            return (List)resMgr.func_199004_b(fullRes).stream().map(IResource::func_199027_b).map((is) -> {
               Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
               JsonElement json = (JsonElement)JSONUtils.func_193839_a(GSON, reader, JsonElement.class);
               return json.getAsJsonObject();
            }).collect(Collectors.toList());
         } catch (Exception var5) {
            LOTRLog.error("Couldn't parse datapack variant file %s from %s", res, fullRes);
            var5.printStackTrace();
            return ImmutableList.of();
         }
      }));
   }

   protected Map asMapOfSingletonLists(Map map) {
      return (Map)map.entrySet().stream().collect(Collectors.toMap(Entry::getKey, (e) -> {
         return ImmutableList.of(e.getValue());
      }));
   }

   private String getFullFolderName(String subFolder) {
      return this.rootFolder + (subFolder == null ? "" : "/" + subFolder);
   }

   protected static Collector toMapCollector() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   protected static Collector jsonElemToObjMapCollector() {
      return Collectors.toMap(Entry::getKey, (e) -> {
         return ((JsonElement)e.getValue()).getAsJsonObject();
      });
   }

   protected JsonObject loadDefaultJson(ResourceLocation res) {
      try {
         Reader reader = new BufferedReader(new InputStreamReader(getDefaultDatapackResourceStream(res), StandardCharsets.UTF_8));
         Throwable var3 = null;

         JsonObject var5;
         try {
            JsonObject jsonObj = (JsonObject)JSONUtils.func_193839_a(GSON, reader, JsonObject.class);
            var5 = jsonObj;
         } catch (Throwable var15) {
            var3 = var15;
            throw var15;
         } finally {
            if (reader != null) {
               if (var3 != null) {
                  try {
                     reader.close();
                  } catch (Throwable var14) {
                     var3.addSuppressed(var14);
                  }
               } else {
                  reader.close();
               }
            }

         }

         return var5;
      } catch (Exception var17) {
         LOTRLog.warn("Failed to parse %s json resource: %s", this.loaderNameForLogging, res);
         var17.printStackTrace();
         return null;
      }
   }

   protected Map loadDefaultJsonsInSubFolder(String subFolder, int maxDepth) {
      String fullFolder = String.format("%s/%s", this.rootFolder, subFolder);
      Collection resources = getDefaultDatapackResourcesInFolder(fullFolder, maxDepth, (s) -> {
         return s.endsWith(".json");
      });
      Map jsons = (Map)resources.stream().collect(Collectors.toMap((res) -> {
         String resPath = res.func_110623_a();
         return new ResourceLocation(res.func_110624_b(), resPath.substring((this.rootFolder + "/").length(), resPath.indexOf(".json")));
      }, this::loadDefaultJson));
      this.extractDataDirectorySettingsJson(jsons, subFolder);
      return jsons;
   }

   private static InputStream getDefaultDatapackResourceStream(ResourceLocation res) {
      return LOTRMod.getDefaultModResourceStream(ResourcePackType.SERVER_DATA, res);
   }

   private static Collection getDefaultDatapackResourcesInFolder(String path, int maxDepth, Predicate filter) {
      String namespace = "lotr";
      ModFileResourcePack lotrAsPack = (ModFileResourcePack)ResourcePackLoader.getResourcePackFor(namespace).get();
      return lotrAsPack.func_225637_a_(ResourcePackType.SERVER_DATA, namespace, path, maxDepth, filter);
   }
}
