package lotr.common.entity.npc.data.name;

import com.google.common.collect.ImmutableList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class NameBankManager extends ReloadListener {
   public static final NameBankManager INSTANCE = new NameBankManager();
   private static final String NAME_BANK_DIR = "npcs/names";
   private static final String NAME_BANK_FILE_TYPE = ".txt";
   private static final NameBank MISSING_NAME_BANK = new NameBank(new ResourceLocation("lotr", "npcs/names/missing_name_bank"), ImmutableList.of("???"));
   private Map loadedNameBanks;

   protected Map prepare(IResourceManager resMgr, IProfiler profiler) {
      Map map = new HashMap();
      Iterator var4 = resMgr.func_199003_a("npcs/names", (filename) -> {
         return filename.endsWith(".txt");
      }).iterator();

      while(var4.hasNext()) {
         ResourceLocation res = (ResourceLocation)var4.next();

         try {
            IResource resource = resMgr.func_199002_a(res);
            Throwable var7 = null;

            try {
               BufferedReader reader = new BufferedReader(new InputStreamReader(resource.func_199027_b(), StandardCharsets.UTF_8));
               Throwable var9 = null;

               try {
                  List names = (List)reader.lines().map(String::trim).collect(Collectors.toList());
                  if (!names.isEmpty()) {
                     NameBank bank = new NameBank(res, names);
                     map.put(res, bank);
                  } else {
                     LOTRLog.error("Failed to load name bank %s - name list was empty", res);
                  }
               } catch (Throwable var35) {
                  var9 = var35;
                  throw var35;
               } finally {
                  if (reader != null) {
                     if (var9 != null) {
                        try {
                           reader.close();
                        } catch (Throwable var34) {
                           var9.addSuppressed(var34);
                        }
                     } else {
                        reader.close();
                     }
                  }

               }
            } catch (Throwable var37) {
               var7 = var37;
               throw var37;
            } finally {
               if (resource != null) {
                  if (var7 != null) {
                     try {
                        resource.close();
                     } catch (Throwable var33) {
                        var7.addSuppressed(var33);
                     }
                  } else {
                     resource.close();
                  }
               }

            }
         } catch (IOException var39) {
            LOTRLog.error("Failed to load name bank %s from file", res);
            var39.printStackTrace();
         }
      }

      return map;
   }

   protected void apply(Map map, IResourceManager resMgr, IProfiler profiler) {
      this.loadedNameBanks = map;
   }

   public NameBank fetchLoadedNameBank(ResourceLocation bankName) {
      if (this.loadedNameBanks.containsKey(bankName)) {
         return (NameBank)this.loadedNameBanks.get(bankName);
      } else {
         LOTRLog.warn("Failed to fetch name bank %s - not loaded", bankName);
         return MISSING_NAME_BANK;
      }
   }

   public static ResourceLocation fullPath(String filename) {
      return new ResourceLocation("lotr", String.format("%s/%s%s", "npcs/names", filename, ".txt"));
   }
}
