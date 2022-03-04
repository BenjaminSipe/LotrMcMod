package lotr.client.text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lotr.common.LOTRLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public abstract class TranslatableTextReloadListener extends ReloadListener {
   private static final String FALLBACK_LANG = "en_us";
   private final Minecraft mcInstance;
   private final String fileExtensionType;
   private final IReloadableResourceManager resourceManager;
   private Map loadedTextResources = new HashMap();

   public TranslatableTextReloadListener(Minecraft mc, String fileExtensionType) {
      this.mcInstance = mc;
      this.resourceManager = (IReloadableResourceManager)this.mcInstance.func_195551_G();
      this.resourceManager.func_219534_a(this);
      this.fileExtensionType = fileExtensionType;
   }

   protected final Object getOrLoadTextResource(ResourceLocation basePath) {
      return this.getOrLoadTextResource(basePath, this.getCurrentLanguage(), true, (Object)null);
   }

   protected final Object getOrLoadTextResource(ResourceLocation basePath, String lang, boolean loadErroredFallback, Object parentLoader) {
      basePath = this.convertToFullResourcePath(basePath);
      return this.loadedTextResources.computeIfAbsent(basePath, (bp) -> {
         return this.loadTextResource(bp, lang, loadErroredFallback, parentLoader);
      });
   }

   protected abstract ResourceLocation convertToFullResourcePath(ResourceLocation var1);

   private String getCurrentLanguage() {
      return this.mcInstance.func_135016_M().func_135041_c().getCode();
   }

   private Object loadTextResource(ResourceLocation basePath, String lang, boolean loadErroredFallback, Object parentLoader) {
      ResourceLocation langPath = this.getLangResourceLocation(basePath, lang);
      if (!this.resourceManager.func_219533_b(langPath)) {
         LOTRLog.warn("Couldn't find translatable resource file %s for current language %s - fallback to %s", basePath, lang, "en_us");
         langPath = this.getLangResourceLocation(basePath, "en_us");
      }

      try {
         IResource resource = this.resourceManager.func_199002_a(langPath);
         Throwable var40 = null;

         Object var10;
         try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.func_199027_b(), StandardCharsets.UTF_8));
            Throwable var9 = null;

            try {
               if (parentLoader == null) {
                  parentLoader = this.createNewParentLoader(basePath, lang);
               }

               var10 = this.loadResource(langPath, reader, parentLoader);
            } catch (Throwable var35) {
               var10 = var35;
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
            var40 = var37;
            throw var37;
         } finally {
            if (resource != null) {
               if (var40 != null) {
                  try {
                     resource.close();
                  } catch (Throwable var33) {
                     var40.addSuppressed(var33);
                  }
               } else {
                  resource.close();
               }
            }

         }

         return var10;
      } catch (Exception var39) {
         String errorMsg = String.format("Failed to load resource file %s!", langPath);
         LOTRLog.error(errorMsg);
         var39.printStackTrace();
         return loadErroredFallback ? this.loadErroredFallbackResource(langPath, errorMsg) : null;
      }
   }

   private ResourceLocation getLangResourceLocation(ResourceLocation basePath, String lang) {
      return new ResourceLocation(basePath.func_110624_b(), String.format("%s/%s%s", basePath.func_110623_a(), lang, this.fileExtensionType));
   }

   protected abstract Object loadResource(ResourceLocation var1, BufferedReader var2, Object var3);

   protected abstract Object createNewParentLoader(ResourceLocation var1, String var2);

   protected abstract Object loadErroredFallbackResource(ResourceLocation var1, String var2);

   protected Object func_212854_a_(IResourceManager resMgr, IProfiler profiler) {
      return null;
   }

   protected void func_212853_a_(Object prepared, IResourceManager resMgr, IProfiler profiler) {
      this.loadedTextResources.clear();
   }
}
