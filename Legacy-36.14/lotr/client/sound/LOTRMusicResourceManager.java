package lotr.client.sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleResource;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class LOTRMusicResourceManager implements IResourceManager {
   private Map resourceMap = new HashMap();

   public Set func_135055_a() {
      return this.resourceMap.entrySet();
   }

   public IResource func_110536_a(ResourceLocation resource) throws IOException {
      return (IResource)this.resourceMap.get(resource);
   }

   public List func_135056_b(ResourceLocation resource) throws IOException {
      List list = new ArrayList();
      list.add(this.func_110536_a(resource));
      return list;
   }

   public void registerMusicResources(ResourceLocation resource, InputStream in) {
      IResource ires = new SimpleResource(resource, in, (InputStream)null, (IMetadataSerializer)null);
      this.resourceMap.put(resource, ires);
   }
}
