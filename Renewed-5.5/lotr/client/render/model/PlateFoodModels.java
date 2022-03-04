package lotr.client.render.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import lotr.common.LOTRLog;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

public class PlateFoodModels implements ISelectiveResourceReloadListener {
   private static final ResourceLocation CONFIG_JSON = new ResourceLocation("lotr", "models/item/plate_food/config.json");
   private static final Gson JSON_READER = (new GsonBuilder()).create();
   public static final PlateFoodModels INSTANCE = new PlateFoodModels();
   private Map foodModels = new HashMap();

   private PlateFoodModels() {
   }

   public void setupAndLoadModels(Minecraft mc) {
      SimpleReloadableResourceManager resMgr = (SimpleReloadableResourceManager)mc.func_195551_G();
      resMgr.func_219534_a(this);
      this.loadModels(resMgr);
   }

   public PlateFoodModels.ModelAndHeight getSpecialModel(Item item) {
      return (PlateFoodModels.ModelAndHeight)this.foodModels.get(item);
   }

   private void loadModels(IResourceManager resMgr) {
      try {
         this.foodModels.clear();
         IResource configRes = resMgr.func_199002_a(CONFIG_JSON);
         InputStream in = configRes.func_199027_b();
         JsonObject configObj = (JsonObject)JSONUtils.func_193839_a(JSON_READER, new InputStreamReader(in), JsonObject.class);
         Iterator var5 = configObj.entrySet().iterator();

         while(var5.hasNext()) {
            Entry e = (Entry)var5.next();
            String itemName = (String)e.getKey();
            JsonObject modelObj = ((JsonElement)e.getValue()).getAsJsonObject();
            ResourceLocation itemRes = new ResourceLocation(itemName);
            if (ForgeRegistries.ITEMS.containsKey(itemRes)) {
               Item item = (Item)ForgeRegistries.ITEMS.getValue(itemRes);
               String modelName = modelObj.get("model").getAsString();
               float height = modelObj.has("height") ? modelObj.get("height").getAsFloat() : 0.0F;
               ResourceLocation modelRes = new ResourceLocation(modelName);
               ResourceLocation fullModelPath = new ResourceLocation(modelRes.func_110624_b(), String.format("models/%s.json", modelRes.func_110623_a()));
               if (resMgr.func_219533_b(fullModelPath)) {
                  this.foodModels.put(item, new PlateFoodModels.ModelAndHeight(modelRes, height));
               } else {
                  LOTRLog.warn("Plate food models config - no model file %s exists for item %s", modelName, itemName);
               }
            } else {
               LOTRLog.warn("Plate food models config - no item %s exists", itemName);
            }
         }

         var5 = this.foodModels.values().iterator();

         while(var5.hasNext()) {
            PlateFoodModels.ModelAndHeight model = (PlateFoodModels.ModelAndHeight)var5.next();
            ModelLoader.addSpecialModel(model.modelRes);
         }
      } catch (IOException var15) {
         LOTRLog.error("Failed to load plate special food models");
         var15.printStackTrace();
      }

   }

   public void onResourceManagerReload(IResourceManager resMgr, Predicate predicate) {
      if (predicate.test(VanillaResourceType.MODELS)) {
         this.loadModels(resMgr);
      }

   }

   public static class ModelAndHeight {
      public final ResourceLocation modelRes;
      public final float height;

      public ModelAndHeight(ResourceLocation res, float f) {
         this.modelRes = res;
         this.height = f;
      }
   }
}
