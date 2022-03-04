package lotr.client.render.model.scatter;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import lotr.client.render.model.BlockModelQuadsHolder;
import lotr.common.LOTRLog;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.geometry.ISimpleModelGeometry;

public class ScatterUnbakedModel implements ISimpleModelGeometry {
   private static final String ALL_TEXTURE = "all";
   private final ScatterModelGenerator scatterModelGenerator;
   private final int numVariants = 64;

   public ScatterUnbakedModel(ScatterModelGenerator scatterModelGenerator) {
      this.scatterModelGenerator = scatterModelGenerator;
   }

   public void addQuads(IModelConfiguration owner, IModelBuilder modelBuilder, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ResourceLocation modelLocation) {
      List unbakedModelVariants = this.scatterModelGenerator.generateNRandomModels(64, "all");
      Iterator var8 = unbakedModelVariants.iterator();

      while(var8.hasNext()) {
         List variant = (List)var8.next();
         List generalQuads = Lists.newArrayList();
         Map builderFaceQuads = Maps.newEnumMap(Direction.class);
         Direction[] var12 = Direction.values();
         int var13 = var12.length;

         Direction direction;
         for(int var14 = 0; var14 < var13; ++var14) {
            direction = var12[var14];
            builderFaceQuads.put(direction, Lists.newArrayList());
         }

         Iterator var19 = variant.iterator();

         while(var19.hasNext()) {
            BlockPart blockpart = (BlockPart)var19.next();
            Iterator var22 = blockpart.field_178240_c.keySet().iterator();

            while(var22.hasNext()) {
               direction = (Direction)var22.next();
               BlockPartFace blockpartface = (BlockPartFace)blockpart.field_178240_c.get(direction);
               RenderMaterial material = owner.resolveTexture(blockpartface.field_178242_d);
               TextureAtlasSprite icon = (TextureAtlasSprite)spriteGetter.apply(material);
               if (blockpartface.field_178244_b == null) {
                  generalQuads.add(BlockModel.makeBakedQuad(blockpart, blockpartface, icon, direction, modelTransform, modelLocation));
               } else {
                  ((List)builderFaceQuads.get(modelTransform.func_225615_b_().rotateTransform(blockpartface.field_178244_b))).add(BlockModel.makeBakedQuad(blockpart, blockpartface, icon, direction, modelTransform, modelLocation));
               }
            }
         }

         BlockModelQuadsHolder bakedVariant = new BlockModelQuadsHolder(generalQuads, builderFaceQuads);
         ((ScatterUnbakedModel.Builder)modelBuilder).addVariantModel(bakedVariant);
      }

   }

   public Collection getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
      RenderMaterial allTexture = owner.resolveTexture("all");
      if (Objects.equals(allTexture.func_229313_b_().toString(), MissingTextureSprite.func_195675_b().toString())) {
         missingTextureErrors.add(Pair.of("all", owner.getModelName()));
      }

      return ImmutableSet.of(allTexture);
   }

   public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
      TextureAtlasSprite particle = (TextureAtlasSprite)spriteGetter.apply(owner.resolveTexture("particle"));
      ScatterUnbakedModel.Builder builder = (new ScatterUnbakedModel.Builder(owner, overrides)).setParticle(particle);
      this.addQuads(owner, builder, bakery, spriteGetter, modelTransform, modelLocation);
      return builder.build();
   }

   public static final class Builder implements IModelBuilder {
      private final List scatterVariantModels;
      private final ItemOverrideList builderItemOverrideList;
      private final boolean builderAmbientOcclusion;
      private TextureAtlasSprite particleTexture;
      private final boolean isSideLight;
      private final boolean builderGui3d;
      private final ItemCameraTransforms builderCameraTransforms;

      public Builder(IModelConfiguration model, ItemOverrideList overrides) {
         this(model.useSmoothLighting(), model.isSideLit(), model.isShadedInGui(), model.getCameraTransforms(), overrides);
      }

      public Builder(BlockModel model, ItemOverrideList overrides, boolean g3d) {
         this(model.func_178309_b(), model.func_230176_c_().func_230178_a_(), g3d, model.func_181682_g(), overrides);
      }

      private Builder(boolean ambOcc, boolean sideLight, boolean g3d, ItemCameraTransforms transform, ItemOverrideList overrides) {
         this.scatterVariantModels = new ArrayList();
         this.builderItemOverrideList = overrides;
         this.builderAmbientOcclusion = false;
         this.isSideLight = sideLight;
         this.builderGui3d = g3d;
         this.builderCameraTransforms = transform;
      }

      public ScatterUnbakedModel.Builder addFaceQuad(Direction facing, BakedQuad quad) {
         throw new UnsupportedOperationException("Add them through the BlockModelQuadsHolder list instead");
      }

      public ScatterUnbakedModel.Builder addGeneralQuad(BakedQuad quad) {
         throw new UnsupportedOperationException("Add them through the BlockModelQuadsHolder list instead");
      }

      public ScatterUnbakedModel.Builder addVariantModel(BlockModelQuadsHolder variantModel) {
         this.scatterVariantModels.add(variantModel);
         return this;
      }

      public ScatterUnbakedModel.Builder setParticle(TextureAtlasSprite texture) {
         this.particleTexture = texture;
         return this;
      }

      public IBakedModel build() {
         if (this.particleTexture == null) {
            throw new RuntimeException("Missing particle!");
         } else {
            return new ScatterBlockModel(this.scatterVariantModels, this.builderAmbientOcclusion, this.isSideLight, this.builderGui3d, this.particleTexture, this.builderCameraTransforms, this.builderItemOverrideList);
         }
      }
   }

   public static final class Loader implements IModelLoader {
      public static final ScatterUnbakedModel.Loader INSTANCE = new ScatterUnbakedModel.Loader();

      private Loader() {
      }

      public void func_195410_a(IResourceManager resourceManager) {
      }

      public ScatterUnbakedModel read(JsonDeserializationContext deserializationContext, JsonObject modelObj) {
         ScatterModelGenerator modelGenerator = null;
         if (modelObj.has("scatter_properties")) {
            modelGenerator = ScatterModelGenerator.parse(modelObj.get("scatter_properties").getAsJsonObject());
         }

         if (modelObj.has("parent")) {
            String parent = modelObj.get("parent").getAsString();
            ResourceLocation parentLocation = new ResourceLocation(parent);
            Set parentModels = new HashSet();
            parentModels.add(parentLocation);

            while(parentLocation != null) {
               IUnbakedModel parentModel = ModelLoader.instance().func_209597_a(parentLocation);
               if (!(parentModel instanceof BlockModel)) {
                  break;
               }

               BlockModel blockModel = (BlockModel)parentModel;
               if (modelGenerator == null && blockModel.customData.getCustomGeometry() instanceof ScatterUnbakedModel) {
                  ScatterUnbakedModel parentScatterModel = (ScatterUnbakedModel)blockModel.customData.getCustomGeometry();
                  if (parentScatterModel.scatterModelGenerator != null) {
                     modelGenerator = parentScatterModel.scatterModelGenerator;
                  }
               }

               parentLocation = blockModel.getParentLocation();
               if (parentModels.contains(parentLocation)) {
                  LOTRLog.error("Circular reference in scatter model 'parent' tree: %s already present", parentLocation);
                  break;
               }
            }
         }

         if (modelGenerator == null) {
            throw new IllegalArgumentException("Model does not define any scatter_properties or inherit from parent");
         } else {
            return new ScatterUnbakedModel(modelGenerator);
         }
      }
   }
}
