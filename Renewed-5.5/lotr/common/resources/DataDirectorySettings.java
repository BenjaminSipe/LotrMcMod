package lotr.common.resources;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

public class DataDirectorySettings {
   public static final String SETTINGS_FILENAME = "_settings.json";
   private final Set removeNamespaces;
   private final Set removeResources;

   private DataDirectorySettings(Set removeNamespaces, Set removeResources) {
      this.removeNamespaces = removeNamespaces;
      this.removeResources = removeResources;
      this.validate();
   }

   private void validate() {
      this.removeNamespaces.forEach((namespace) -> {
         if (ResourceLocation.func_208304_a(namespace + ":test_resource_path") == null) {
            throw new IllegalArgumentException("Invalid namespace declaration: " + namespace);
         }
      });
   }

   public static DataDirectorySettings empty() {
      return new DataDirectorySettings(ImmutableSet.of(), ImmutableSet.of());
   }

   public static DataDirectorySettings read(ResourceLocation resourceName, JsonObject json) {
      Set removeNamespaces = new HashSet();
      JsonArray removeNamespacesArray = json.get("remove_namespaces").getAsJsonArray();
      Iterator var4 = removeNamespacesArray.iterator();

      while(var4.hasNext()) {
         JsonElement namespace = (JsonElement)var4.next();
         removeNamespaces.add(namespace.getAsString());
      }

      Set removeResources = new HashSet();
      JsonArray removeResourcesArray = json.get("remove_singles").getAsJsonArray();
      Iterator var6 = removeResourcesArray.iterator();

      while(var6.hasNext()) {
         JsonElement resource = (JsonElement)var6.next();
         removeResources.add(new ResourceLocation(resource.getAsString()));
      }

      return new DataDirectorySettings(removeNamespaces, removeResources);
   }

   public boolean shouldExclude(ResourceLocation resource) {
      return this.removeNamespaces.contains(resource.func_110624_b()) || this.removeResources.contains(resource);
   }

   public String toString() {
      return String.format("%s [removeNamespaces = %d, removeResources = %d]", "_settings.json", this.removeNamespaces.size(), this.removeResources.size());
   }
}
