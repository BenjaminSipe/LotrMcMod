package lotr.client.render.model.connectedtex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.util.ResourceLocation;

public class TextureConnectionProperties {
   private final ResourceLocation textureName;
   private final Optional elementPathOverrides;
   private final boolean includeBaseElement;
   private final boolean makeFromSingleIcon;

   public TextureConnectionProperties(ResourceLocation textureName, Optional elementPathOverrides, boolean includeBaseElement, boolean makeFromSingleIcon) {
      this.textureName = textureName;
      this.elementPathOverrides = elementPathOverrides;
      this.includeBaseElement = includeBaseElement;
      this.makeFromSingleIcon = makeFromSingleIcon;
   }

   public static TextureConnectionProperties defaultProps(ResourceLocation textureName) {
      return new TextureConnectionProperties(textureName, Optional.empty(), true, false);
   }

   public static TextureConnectionProperties resolveFrom(UnresolvedTextureConnectionProperties unresolved, Function textureResolver) {
      return new TextureConnectionProperties((ResourceLocation)textureResolver.apply(unresolved.textureName), resolveUnresolvedElementPathOverrides(unresolved.elementPathOverrides, textureResolver), unresolved.includeBaseElement, unresolved.makeFromSingleIcon);
   }

   private static Optional resolveUnresolvedElementPathOverrides(Optional unresolvedOpt, Function textureResolver) {
      if (unresolvedOpt.isPresent()) {
         Map unresolvedMap = (Map)unresolvedOpt.get();
         Map resolvedMap = (Map)unresolvedMap.entrySet().stream().collect(Collectors.toMap(Entry::getKey, (e) -> {
            return (ResourceLocation)textureResolver.apply(e.getValue());
         }));
         return Optional.of(resolvedMap);
      } else {
         return Optional.empty();
      }
   }

   public ResourceLocation getBaseTextureName() {
      return this.textureName;
   }

   public Optional getElementIconPath(ConnectedTextureElement element) {
      if (element == ConnectedTextureElement.BASE) {
         throw new IllegalArgumentException("This method should not be used to determine the base icon - this is a development error");
      } else {
         return this.elementPathOverrides.isPresent() ? Optional.ofNullable(((Map)this.elementPathOverrides.get()).get(element)) : Optional.of(new ResourceLocation(this.textureName + element.getDefaultIconSuffix()));
      }
   }

   public boolean includeBaseElement() {
      return this.includeBaseElement;
   }

   public boolean makeFromSingleIcon() {
      return this.makeFromSingleIcon;
   }

   public ResourceLocation getCanonicalCacheKey() {
      return new ResourceLocation(this.textureName.func_110624_b(), String.format("%s.connectedproperties__overrides_%s__includebaseelement_%s__makefromsingleicon_%s", this.textureName.func_110623_a(), this.getCanonicalFormForElementPathOverrides(), this.includeBaseElement, this.makeFromSingleIcon));
   }

   private String getCanonicalFormForElementPathOverrides() {
      if (this.elementPathOverrides.isPresent()) {
         Map map = (Map)this.elementPathOverrides.get();
         List sortedKeys = new ArrayList(map.keySet());
         Collections.sort(sortedKeys, Comparator.comparingInt((elem) -> {
            return elem.ordinal();
         }));
         return (String)sortedKeys.stream().map((elem) -> {
            String elemName = elem.elementName;
            String pathOverride = ((ResourceLocation)map.get(elem)).toString().replace(':', '.');
            return elemName + "_" + pathOverride;
         }).collect(Collectors.joining("_"));
      } else {
         return "none";
      }
   }
}
