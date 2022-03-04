package lotr.client.render.model.connectedtex;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import lotr.client.render.model.BlockModelQuadsHolder;
import lotr.client.render.model.BlockModelUtil;
import lotr.client.render.model.DynamicTextureRepository;
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
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.geometry.ISimpleModelGeometry;

public class ConnectedTextureUnbakedModel implements ISimpleModelGeometry {
   private final List elements;
   private final Map texturesConnectionProperties;
   private final List itemConnectedContextPoints;
   private final ConnectedTexture3DContext.BlockConnectionType connectionType;

   public ConnectedTextureUnbakedModel(List elements, Map texturesConnectionProperties, List itemContext, ConnectedTexture3DContext.BlockConnectionType cType) {
      this.elements = elements;
      this.texturesConnectionProperties = texturesConnectionProperties;
      this.itemConnectedContextPoints = itemContext;
      this.connectionType = cType;
   }

   private Map getResolvedTextureConnectionProperties(IModelConfiguration owner) {
      Function textureResolver = (s) -> {
         return owner.resolveTexture(s).func_229313_b_();
      };
      return (Map)this.texturesConnectionProperties.entrySet().stream().collect(Collectors.toMap((e) -> {
         return (ResourceLocation)textureResolver.apply(e.getKey());
      }, (e) -> {
         return TextureConnectionProperties.resolveFrom((UnresolvedTextureConnectionProperties)e.getValue(), textureResolver);
      }));
   }

   public void addQuads(IModelConfiguration owner, IModelBuilder modelBuilder, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ResourceLocation modelLocation) {
   }

   public Collection getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
      Set textures = Sets.newHashSet();
      Iterator var5 = this.elements.iterator();

      Iterator var7;
      while(var5.hasNext()) {
         BlockPart part = (BlockPart)var5.next();

         RenderMaterial texture;
         for(var7 = part.field_178240_c.values().iterator(); var7.hasNext(); textures.add(texture)) {
            BlockPartFace face = (BlockPartFace)var7.next();
            texture = owner.resolveTexture(face.field_178242_d);
            if (Objects.equals(texture.func_229313_b_().toString(), MissingTextureSprite.func_195675_b().toString())) {
               missingTextureErrors.add(Pair.of(face.field_178242_d, owner.getModelName()));
            }
         }
      }

      Map resolvedConnectionProps = this.getResolvedTextureConnectionProperties(owner);
      Set allConnectedTextures = Sets.newHashSet();
      var7 = textures.iterator();

      while(var7.hasNext()) {
         RenderMaterial texture = (RenderMaterial)var7.next();
         ResourceLocation textureName = texture.func_229313_b_();
         if (!Objects.equals(textureName.toString(), MissingTextureSprite.func_195675_b().toString())) {
            TextureConnectionProperties connectionProperties = (TextureConnectionProperties)resolvedConnectionProps.getOrDefault(textureName, TextureConnectionProperties.defaultProps(textureName));
            List connected = DynamicTextureRepository.INSTANCE.generateAllConnectedTextures(connectionProperties);
            connected.forEach((connectedRes) -> {
               RenderMaterial connectedMaterial = new RenderMaterial(texture.func_229310_a_(), connectedRes);
               allConnectedTextures.add(connectedMaterial);
            });
         }
      }

      textures.addAll(allConnectedTextures);
      return textures;
   }

   public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
      TextureAtlasSprite particle = (TextureAtlasSprite)spriteGetter.apply(owner.resolveTexture("particle"));
      ConnectedTextureUnbakedModel.Builder builder = (new ConnectedTextureUnbakedModel.Builder(owner, overrides)).setParticle(particle);
      this.addQuads(owner, builder, bakery, spriteGetter, modelTransform, modelLocation);
      ConnectedTextureUnbakedModel.DeferredConnectedTextureModelBakery deferredBakery = new ConnectedTextureUnbakedModel.DeferredConnectedTextureModelBakery(owner, spriteGetter, modelTransform, modelLocation, this.elements, this.getResolvedTextureConnectionProperties(owner));
      builder.setDeferredConnectedModelBakery(deferredBakery);
      builder.setBlockstateRotation(modelTransform.func_225615_b_());
      builder.createItemConnectedContext(this.itemConnectedContextPoints);
      builder.setBlockConnectionType(this.connectionType);
      return builder.build();
   }

   public static final class Builder implements IModelBuilder {
      private final ItemOverrideList builderItemOverrideList;
      private final boolean builderAmbientOcclusion;
      private TextureAtlasSprite particleTexture;
      private final boolean isSideLight;
      private final boolean builderGui3d;
      private final ItemCameraTransforms builderCameraTransforms;
      private ConnectedTextureUnbakedModel.DeferredConnectedTextureModelBakery deferredConnectedModelBakery;
      private TransformationMatrix blockstateRotation;
      private ConnectedTexture3DContext itemContext;
      private ConnectedTexture3DContext.BlockConnectionType connectionType;

      public Builder(IModelConfiguration model, ItemOverrideList overrides) {
         this(model.useSmoothLighting(), model.isSideLit(), model.isShadedInGui(), model.getCameraTransforms(), overrides);
      }

      public Builder(BlockModel model, ItemOverrideList overrides, boolean g3d) {
         this(model.func_178309_b(), model.func_230176_c_().func_230178_a_(), g3d, model.func_181682_g(), overrides);
      }

      private Builder(boolean ambOcc, boolean sideLight, boolean g3d, ItemCameraTransforms transform, ItemOverrideList overrides) {
         this.builderItemOverrideList = overrides;
         this.builderAmbientOcclusion = ambOcc;
         this.isSideLight = sideLight;
         this.builderGui3d = g3d;
         this.builderCameraTransforms = transform;
      }

      public void setDeferredConnectedModelBakery(ConnectedTextureUnbakedModel.DeferredConnectedTextureModelBakery bakery) {
         this.deferredConnectedModelBakery = bakery;
      }

      public void setBlockstateRotation(TransformationMatrix rotation) {
         this.blockstateRotation = rotation;
      }

      public ConnectedTextureUnbakedModel.Builder addFaceQuad(Direction facing, BakedQuad quad) {
         throw new UnsupportedOperationException("Add them through the BlockModelQuadsHolder map instead");
      }

      public ConnectedTextureUnbakedModel.Builder addGeneralQuad(BakedQuad quad) {
         throw new UnsupportedOperationException("Add them through the BlockModelQuadsHolder map instead");
      }

      public ConnectedTextureUnbakedModel.Builder setParticle(TextureAtlasSprite texture) {
         this.particleTexture = texture;
         return this;
      }

      public ConnectedTextureUnbakedModel.Builder createItemConnectedContext(List points) {
         if (points == null) {
            this.itemContext = ConnectedTexture3DContext.newEmptyContext();
         } else {
            this.itemContext = ConnectedTexture3DContext.newContextFrom(points);
         }

         return this;
      }

      public ConnectedTextureUnbakedModel.Builder setBlockConnectionType(ConnectedTexture3DContext.BlockConnectionType type) {
         if (type == null) {
            this.connectionType = ConnectedTexture3DContext.BlockConnectionType.SAME_BLOCK;
         } else {
            this.connectionType = type;
         }

         return this;
      }

      public IBakedModel build() {
         if (this.particleTexture == null) {
            throw new RuntimeException("Missing particle!");
         } else {
            return new ConnectedTextureBlockModel(this.builderAmbientOcclusion, this.isSideLight, this.builderGui3d, this.particleTexture, this.builderCameraTransforms, this.builderItemOverrideList, this.deferredConnectedModelBakery, this.blockstateRotation, this.itemContext, this.connectionType);
         }
      }
   }

   public static final class Loader implements IModelLoader {
      public static final ConnectedTextureUnbakedModel.Loader INSTANCE = new ConnectedTextureUnbakedModel.Loader();

      private Loader() {
      }

      public void func_195410_a(IResourceManager resourceManager) {
      }

      public ConnectedTextureUnbakedModel read(JsonDeserializationContext deserializationContext, JsonObject modelObj) {
         List parts = Lists.newArrayList();
         Map texturesConnectionProperties = new HashMap();
         List itemContextPoints = null;
         ConnectedTexture3DContext.BlockConnectionType connectionType = null;
         if (modelObj.has("elements")) {
            Iterator var7 = JSONUtils.func_151214_t(modelObj, "elements").iterator();

            while(var7.hasNext()) {
               JsonElement element = (JsonElement)var7.next();
               parts.add(deserializationContext.deserialize(element, BlockPart.class));
            }
         }

         if (modelObj.has("texture_connection_properties")) {
            JsonObject texturePropsMap = JSONUtils.func_152754_s(modelObj, "texture_connection_properties");
            Iterator var15 = texturePropsMap.entrySet().iterator();

            while(var15.hasNext()) {
               Entry entry = (Entry)var15.next();
               String textureString = (String)entry.getKey();
               if (BlockModelUtil.validateTextureString(textureString)) {
                  UnresolvedTextureConnectionProperties properties = UnresolvedTextureConnectionProperties.read(textureString, ((JsonElement)entry.getValue()).getAsJsonObject());
                  texturesConnectionProperties.put(textureString, properties);
               } else {
                  LOTRLog.error("Texture name '%s' in texture_connection_properties is not a valid texture path or #reference", textureString);
               }
            }
         }

         if (modelObj.has("item_connected_context")) {
            itemContextPoints = this.loadItemContextParts(modelObj, "item_connected_context");
         }

         String parent;
         if (modelObj.has("block_connection_type")) {
            parent = modelObj.get("block_connection_type").getAsString();
            connectionType = ConnectedTexture3DContext.BlockConnectionType.getByName(parent);
            if (connectionType == null) {
               connectionType = ConnectedTexture3DContext.BlockConnectionType.SAME_BLOCK;
               LOTRLog.error("Connected texture model has unknown block_connection_type %s - defaulting to %s", parent, connectionType);
            }
         }

         if (modelObj.has("parent")) {
            parent = modelObj.get("parent").getAsString();
            ResourceLocation parentLocation = new ResourceLocation(parent);
            Set parentModels = new HashSet();
            parentModels.add(parentLocation);

            while(parentLocation != null) {
               IUnbakedModel parentModel = ModelLoader.instance().func_209597_a(parentLocation);
               if (!(parentModel instanceof BlockModel)) {
                  break;
               }

               BlockModel blockModel = (BlockModel)parentModel;
               if (blockModel.customData.getCustomGeometry() instanceof ConnectedTextureUnbakedModel) {
                  ConnectedTextureUnbakedModel parentCTM = (ConnectedTextureUnbakedModel)blockModel.customData.getCustomGeometry();
                  parts.addAll(parentCTM.elements);
                  parentCTM.texturesConnectionProperties.forEach(texturesConnectionProperties::putIfAbsent);
                  if (itemContextPoints == null && parentCTM.itemConnectedContextPoints != null) {
                     itemContextPoints = parentCTM.itemConnectedContextPoints;
                  }

                  if (connectionType == null && parentCTM.connectionType != null) {
                     connectionType = parentCTM.connectionType;
                  }
               } else {
                  parts.addAll(blockModel.func_178298_a());
               }

               parentLocation = blockModel.getParentLocation();
               if (parentModels.contains(parentLocation)) {
                  LOTRLog.error("Circular reference in connected texture model 'parent' tree: %s already present", parentLocation);
                  break;
               }
            }
         }

         return new ConnectedTextureUnbakedModel(parts, texturesConnectionProperties, itemContextPoints, connectionType);
      }

      private List loadItemContextParts(JsonObject modelObj, String arrayName) {
         List parts = Lists.newArrayList();
         Iterator var4 = JSONUtils.func_151214_t(modelObj, arrayName).iterator();

         while(var4.hasNext()) {
            JsonElement element = (JsonElement)var4.next();
            String poiName = element.getAsString();
            ConnectedTexture3DContext.PositionOfInterest poi = ConnectedTexture3DContext.PositionOfInterest.getByJsonName(poiName);
            if (poi == null) {
               LOTRLog.error("Error in connected texture model item_connected_context: no 'point of interest' named %s", poiName);
            } else {
               parts.add(poi);
            }
         }

         return parts;
      }
   }

   public static class DeferredConnectedTextureModelBakery {
      public final IModelConfiguration owner;
      public final Function spriteGetter;
      public final IModelTransform modelTransform;
      public final ResourceLocation modelLocation;
      private final List elements;
      private final Map texturesConnectionProperties;
      private final Map createdBakedModelsFor3dContext = new HashMap();

      public DeferredConnectedTextureModelBakery(IModelConfiguration owner, Function spriteGetter, IModelTransform modelTransform, ResourceLocation modelLocation, List elements, Map texturesConnectionProperties) {
         this.owner = owner;
         this.spriteGetter = spriteGetter;
         this.modelTransform = modelTransform;
         this.modelLocation = modelLocation;
         this.elements = elements;
         this.texturesConnectionProperties = texturesConnectionProperties;
      }

      public BlockModelQuadsHolder getOrCreateBakedModelFor3DContext(ConnectedTexture3DContext ctx3d) {
         int key = ctx3d.getCombinedBitFlags();
         return (BlockModelQuadsHolder)this.createdBakedModelsFor3dContext.computeIfAbsent(key, (i) -> {
            List generalQuads = Lists.newArrayList();
            Map builderFaceQuads = Maps.newEnumMap(Direction.class);
            Direction[] var5 = Direction.values();
            int var6 = var5.length;

            Direction direction;
            for(int var7 = 0; var7 < var6; ++var7) {
               direction = var5[var7];
               builderFaceQuads.put(direction, Lists.newArrayList());
            }

            Iterator var16 = this.elements.iterator();

            while(var16.hasNext()) {
               BlockPart blockpart = (BlockPart)var16.next();
               Iterator var19 = blockpart.field_178240_c.keySet().iterator();

               while(var19.hasNext()) {
                  direction = (Direction)var19.next();
                  BlockPartFace blockpartface = (BlockPartFace)blockpart.field_178240_c.get(direction);
                  RenderMaterial material = this.owner.resolveTexture(blockpartface.field_178242_d);
                  ResourceLocation texture = material.func_229313_b_();
                  TextureConnectionProperties connectionProperties = (TextureConnectionProperties)this.texturesConnectionProperties.getOrDefault(texture, TextureConnectionProperties.defaultProps(texture));
                  Set elementSet = ctx3d.getFace2DContext(direction).getTextureElements(connectionProperties.includeBaseElement());
                  ResourceLocation connectedTexture = DynamicTextureRepository.INSTANCE.getConnectedTexture(connectionProperties, elementSet);
                  TextureAtlasSprite icon = (TextureAtlasSprite)this.spriteGetter.apply(new RenderMaterial(material.func_229310_a_(), connectedTexture));
                  if (blockpartface.field_178244_b == null) {
                     generalQuads.add(BlockModel.makeBakedQuad(blockpart, blockpartface, icon, direction, this.modelTransform, this.modelLocation));
                  } else {
                     ((List)builderFaceQuads.get(this.modelTransform.func_225615_b_().rotateTransform(blockpartface.field_178244_b))).add(BlockModel.makeBakedQuad(blockpart, blockpartface, icon, direction, this.modelTransform, this.modelLocation));
                  }
               }
            }

            BlockModelQuadsHolder holder = new BlockModelQuadsHolder(generalQuads, builderFaceQuads);
            return holder;
         });
      }
   }
}
