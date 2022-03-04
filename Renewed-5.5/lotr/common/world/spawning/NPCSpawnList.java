package lotr.common.world.spawning;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;

public class NPCSpawnList {
   private final List entries;
   private final ResourceLocation resourceName;

   private NPCSpawnList(ResourceLocation resourceName, List entries) {
      this.resourceName = resourceName;
      this.entries = entries;
   }

   public static NPCSpawnList read(ResourceLocation resourceName, JsonObject json) {
      JsonArray entryArray = json.get("entries").getAsJsonArray();
      List entries = new ArrayList();
      Iterator var4 = entryArray.iterator();

      while(var4.hasNext()) {
         JsonElement elem = (JsonElement)var4.next();
         NPCSpawnEntry entry = NPCSpawnEntry.read(resourceName, elem.getAsJsonObject());
         if (entry != null) {
            entries.add(entry);
         }
      }

      return new NPCSpawnList(resourceName, entries);
   }

   public ResourceLocation getName() {
      return this.resourceName;
   }

   public List getReadOnlyList() {
      return new ArrayList(this.entries);
   }

   public NPCSpawnEntry getRandomSpawnEntry(Random rand) {
      return (NPCSpawnEntry)WeightedRandom.func_76271_a(rand, this.entries);
   }
}
