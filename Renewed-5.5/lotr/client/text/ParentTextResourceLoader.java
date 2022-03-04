package lotr.client.text;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

public abstract class ParentTextResourceLoader {
   private final ResourceLocation topLevelResource;
   private final Set inheritanceRecord;
   protected final String langCode;

   public ParentTextResourceLoader(ResourceLocation topLevelResource, String langCode) {
      this.topLevelResource = topLevelResource;
      this.inheritanceRecord = new HashSet();
      this.inheritanceRecord.add(topLevelResource);
      this.langCode = langCode;
   }

   public abstract Optional getOrLoadParentResource(ResourceLocation var1);

   protected final void checkInheritanceRecord(ResourceLocation parent) {
      if (this.inheritanceRecord.contains(parent)) {
         throw this.createCircularReferenceException(this.topLevelResource, parent);
      } else {
         this.inheritanceRecord.add(parent);
      }
   }

   protected abstract IllegalArgumentException createCircularReferenceException(ResourceLocation var1, ResourceLocation var2);

   public static class NoopParentLoader extends ParentTextResourceLoader {
      public NoopParentLoader() {
         super((ResourceLocation)null, (String)null);
      }

      public Optional getOrLoadParentResource(ResourceLocation parent) {
         throw new UnsupportedOperationException();
      }

      protected IllegalArgumentException createCircularReferenceException(ResourceLocation topLevelPath, ResourceLocation parentPath) {
         throw new UnsupportedOperationException();
      }
   }
}
