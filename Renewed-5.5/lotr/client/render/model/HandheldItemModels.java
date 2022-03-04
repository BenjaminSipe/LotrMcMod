package lotr.client.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

public class HandheldItemModels implements ISelectiveResourceReloadListener {
   public static final HandheldItemModels INSTANCE = new HandheldItemModels();
   private static final String HANDHELD_SUFFIX = "handheld";
   private List specialHandheldItemNames = new ArrayList();

   private HandheldItemModels() {
   }

   public void setupAndDetectModels(Minecraft mc) {
      SimpleReloadableResourceManager resMgr = (SimpleReloadableResourceManager)mc.func_195551_G();
      resMgr.func_219534_a(this);
      this.detectSpecialHandhelds(resMgr);
   }

   public void onResourceManagerReload(IResourceManager resMgr, Predicate predicate) {
      if (predicate.test(VanillaResourceType.MODELS)) {
         this.detectSpecialHandhelds(resMgr);
      }

   }

   private void detectSpecialHandhelds(IResourceManager resMgr) {
      this.specialHandheldItemNames.clear();
      Iterator var2 = ForgeRegistries.ITEMS.getKeys().iterator();

      while(var2.hasNext()) {
         ResourceLocation itemName = (ResourceLocation)var2.next();
         ResourceLocation fullHandheldModelPath = new ResourceLocation(itemName.func_110624_b(), String.format("models/item/%s_%s.json", itemName.func_110623_a(), "handheld"));
         if (resMgr.func_219533_b(fullHandheldModelPath)) {
            this.addSpecialHandheld(itemName);
         }
      }

      LOTRLog.info("Automatically detected and registered %d special handheld items!", this.specialHandheldItemNames.size());
   }

   private void addSpecialHandheld(ResourceLocation itemName) {
      this.specialHandheldItemNames.add(itemName);
      ModelLoader.addSpecialModel(this.getHandheldModelLocation(itemName));
   }

   private ModelResourceLocation getHandheldModelLocation(ResourceLocation itemName) {
      return new ModelResourceLocation(String.format("%s_%s", itemName, "handheld"), "inventory");
   }

   public void onModelBake(ModelBakeEvent event) {
      Map modelMap = event.getModelRegistry();
      Iterator var3 = this.specialHandheldItemNames.iterator();

      while(var3.hasNext()) {
         ResourceLocation itemName = (ResourceLocation)var3.next();
         ResourceLocation modelName = new ModelResourceLocation(itemName, "inventory");
         ResourceLocation handheldModelName = this.getHandheldModelLocation(itemName);
         IBakedModel defaultModel = (IBakedModel)modelMap.get(modelName);
         IBakedModel handheldModel = (IBakedModel)modelMap.get(handheldModelName);
         if (defaultModel == null) {
            throw new IllegalStateException("Could not find default inventory model for " + modelName);
         }

         if (handheldModel == null) {
            throw new IllegalStateException("Could not find handheld model for " + handheldModelName);
         }

         HandheldItemModels.HandheldWrapperModel.remapHandheldModelOverrides(modelName, defaultModel, handheldModel);
         IBakedModel wrapperModel = new HandheldItemModels.HandheldWrapperModel(defaultModel, handheldModel);
         modelMap.put(modelName, wrapperModel);
      }

   }

   private static class HandheldWrapperModel implements IBakedModel {
      private final IBakedModel defaultModel;
      private final IBakedModel handheldModel;

      public HandheldWrapperModel(IBakedModel defaultModel, IBakedModel handheldModel) {
         this.defaultModel = defaultModel;
         this.handheldModel = handheldModel;
      }

      public static void remapHandheldModelOverrides(ResourceLocation modelName, IBakedModel defaultModel, IBakedModel handheldModel) {
         try {
            ItemOverrideList overrides = handheldModel.func_188617_f();
            Field f_overrideBakedModels = ObfuscationReflectionHelper.findField(ItemOverrideList.class, "field_209582_c");
            LOTRUtil.unlockFinalField(f_overrideBakedModels);
            List overrideModels = (List)f_overrideBakedModels.get(overrides);
            List remappedOverrideModels = (List)overrideModels.stream().map((handheldOverride) -> {
               return new HandheldItemModels.HandheldWrapperModel(defaultModel, handheldOverride);
            }).collect(Collectors.toList());
            f_overrideBakedModels.set(overrides, remappedOverrideModels);
         } catch (Exception var7) {
            LOTRLog.error("Failed to remap handheld model overrides for model %s", modelName);
            var7.printStackTrace();
         }

      }

      public List func_200117_a(BlockState state, Direction cullFace, Random rand) {
         return this.getQuads(state, cullFace, rand, EmptyModelData.INSTANCE);
      }

      public List getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
         return this.defaultModel.getQuads(state, side, rand, extraData);
      }

      public boolean func_177555_b() {
         return this.defaultModel.func_177555_b();
      }

      public boolean func_177556_c() {
         return this.defaultModel.func_177556_c();
      }

      public boolean func_230044_c_() {
         return this.defaultModel.func_230044_c_();
      }

      public boolean func_188618_c() {
         return this.defaultModel.func_188618_c();
      }

      public TextureAtlasSprite func_177554_e() {
         return this.getParticleTexture(EmptyModelData.INSTANCE);
      }

      public TextureAtlasSprite getParticleTexture(IModelData extraData) {
         return this.defaultModel.getParticleTexture(extraData);
      }

      public ItemOverrideList func_188617_f() {
         return this.handheldModel.func_188617_f();
      }

      public IBakedModel handlePerspective(TransformType transformType, MatrixStack mat) {
         IBakedModel modelToUse = this.defaultModel;
         if (transformType == TransformType.FIRST_PERSON_LEFT_HAND || transformType == TransformType.FIRST_PERSON_RIGHT_HAND || transformType == TransformType.THIRD_PERSON_LEFT_HAND || transformType == TransformType.THIRD_PERSON_RIGHT_HAND) {
            modelToUse = this.handheldModel;
         }

         return ForgeHooksClient.handlePerspective(modelToUse, transformType, mat);
      }
   }
}
