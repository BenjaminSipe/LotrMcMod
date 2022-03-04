package lotr.client.render.model;

import com.google.common.collect.Sets;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;

public class DynamicTextureResourcePack implements IResourcePack {
   private final ResourcePackType packType;
   private final String namespace;
   private final Map inputStreams = new HashMap();
   public final ResourceLocation packIsLoadedMarkerResource;
   private final Map textureSetsLoadedMarkers = new HashMap();

   public DynamicTextureResourcePack(ResourcePackType type, String s) {
      this.packType = type;
      this.namespace = s;
      this.packIsLoadedMarkerResource = new ResourceLocation(this.namespace, "dynamic_tex_virtual_resource_pack_is_loaded_marker");
   }

   public void addDynamicTexture(ResourceLocation baseSetPath, ResourceLocation dynamicFullPath, DynamicTexture tex) {
      NativeImage image = tex.func_195414_e();
      Supplier sup = () -> {
         try {
            return new ByteArrayInputStream(image.func_227796_e_());
         } catch (Exception var3) {
            LOTRLog.error("Failed to setup dynamic texture resource: %s", dynamicFullPath);
            var3.printStackTrace();
            return new ByteArrayInputStream(new byte[0]);
         }
      };
      this.inputStreams.put(dynamicFullPath, sup);
      if (!this.textureSetsLoadedMarkers.containsKey(baseSetPath)) {
         this.textureSetsLoadedMarkers.put(baseSetPath, createDynamicTextureSetIsLoadedMarker(baseSetPath));
      }

   }

   public static ResourceLocation createDynamicTextureSetIsLoadedMarker(ResourceLocation baseSetPath) {
      return new ResourceLocation(baseSetPath.func_110624_b(), baseSetPath.func_110623_a() + "set_loaded_marker");
   }

   public void close() {
      this.inputStreams.clear();
   }

   public InputStream func_195763_b(String fileName) throws IOException {
      throw new UnsupportedOperationException();
   }

   public InputStream func_195761_a(ResourcePackType type, ResourceLocation location) throws IOException {
      if (type == this.packType) {
         return (InputStream)((Supplier)this.inputStreams.get(location)).get();
      } else {
         throw new FileNotFoundException(String.format("'%s' in ResourcePack '%s'", getFullPath(type, location), this.func_195762_a()));
      }
   }

   public Collection func_225637_a_(ResourcePackType type, String namespace, String path, int maxDepth, Predicate filter) {
      return (Collection)(type == this.packType ? (Collection)this.inputStreams.keySet().stream().filter((res) -> {
         if (!res.func_110624_b().equals(namespace)) {
            return false;
         } else {
            String resPath = res.func_110623_a();
            String[] pathElements = resPath.split("/");
            return resPath.startsWith(path) && pathElements.length >= maxDepth + 1 && filter.test(pathElements[pathElements.length - 1]);
         }
      }).collect(Collectors.toList()) : Collections.emptySet());
   }

   public boolean func_195764_b(ResourcePackType type, ResourceLocation location) {
      if (type != this.packType) {
         return false;
      } else {
         return this.inputStreams.containsKey(location) || this.packIsLoadedMarkerResource.equals(location) || this.textureSetsLoadedMarkers.containsValue(location);
      }
   }

   public Set func_195759_a(ResourcePackType type) {
      return (Set)(type == this.packType ? Sets.newHashSet(new String[]{this.namespace}) : Collections.emptySet());
   }

   public Object func_195760_a(IMetadataSectionSerializer deserializer) throws IOException {
      return null;
   }

   public String func_195762_a() {
      return String.format("%s:%s virtual pack for dynamic textures", "lotr", this.namespace);
   }

   private static String getFullPath(ResourcePackType type, ResourceLocation location) {
      return String.format("%s/%s/%s", type.func_198956_a(), location.func_110624_b(), location.func_110623_a());
   }
}
