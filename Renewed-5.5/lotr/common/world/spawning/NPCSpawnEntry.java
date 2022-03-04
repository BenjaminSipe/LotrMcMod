package lotr.common.world.spawning;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import lotr.common.LOTRLog;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraft.util.math.MathHelper;

public class NPCSpawnEntry extends Item {
   private final NPCEntityTypeProvider typeProvider;
   private final int minCount;
   private final int maxCount;

   private NPCSpawnEntry(NPCEntityTypeProvider typeProvider, int weight, int min, int max) {
      super(weight);
      this.typeProvider = typeProvider;
      this.minCount = min;
      this.maxCount = max;
   }

   public static NPCSpawnEntry read(ResourceLocation resourceName, JsonObject json) {
      NPCEntityTypeProvider typeProvider = null;
      if (json.has("entity_type")) {
         String typeName = json.get("entity_type").getAsString();
         ResourceLocation typeNameRes = new ResourceLocation(typeName);
         EntityType entityType = NPCEntitySettingsManager.lookupEntityTypeByName(typeNameRes);
         if (entityType == null) {
            LOTRLog.warn("Failed to load an entry within NPC spawn list %s - nonexistent single entity type %s", resourceName, typeName);
            return null;
         }

         typeProvider = new NPCEntityTypeProvider.Single(entityType);
      } else {
         if (!json.has("mixed_entity_types")) {
            LOTRLog.warn("Failed to load NPC spawn entry in list %s - found neither a single entity_type nor a mixed_entity_types array", resourceName);
            return null;
         }

         List mixedTypeEntries = new ArrayList();
         JsonArray mixedEntityTypes = json.get("mixed_entity_types").getAsJsonArray();
         Iterator var16 = mixedEntityTypes.iterator();

         while(var16.hasNext()) {
            JsonElement elem = (JsonElement)var16.next();
            JsonObject singleTypeJson = elem.getAsJsonObject();
            String typeName = singleTypeJson.get("entity_type").getAsString();
            ResourceLocation typeNameRes = new ResourceLocation(typeName);
            EntityType entityType = NPCEntitySettingsManager.lookupEntityTypeByName(typeNameRes);
            if (entityType == null) {
               LOTRLog.warn("Failed to load an entry within NPC spawn list %s - nonexistent entity type %s in mixed_entity_types", resourceName, typeName);
               return null;
            }

            int weight = singleTypeJson.get("weight").getAsInt();
            mixedTypeEntries.add(new NPCEntityTypeProvider.MixedEntry(entityType, weight));
         }

         typeProvider = new NPCEntityTypeProvider.Mixed(mixedTypeEntries);
      }

      int weight = json.get("weight").getAsInt();
      int minCount = json.get("min_count").getAsInt();
      int maxCount = json.get("max_count").getAsInt();
      return new NPCSpawnEntry((NPCEntityTypeProvider)typeProvider, weight, minCount, maxCount);
   }

   private int getRandomGroupSize(Random rand) {
      return MathHelper.func_76136_a(rand, this.minCount, this.maxCount);
   }

   public Stream getAllPossibleTypes() {
      return this.typeProvider.getAllPossibleTypes();
   }

   public static class EntryInContext {
      private final NPCSpawnEntry spawnEntry;
      private final boolean isConquestSpawn;

      public EntryInContext(NPCSpawnEntry entry, boolean conquest) {
         this.spawnEntry = entry;
         this.isConquestSpawn = conquest;
      }

      public int getRandomGroupSize(Random rand) {
         return this.spawnEntry.getRandomGroupSize(rand);
      }

      public EntityType getTypeToSpawn(Random rand) {
         return this.spawnEntry.typeProvider.getTypeToSpawn(rand);
      }

      public boolean isConquestSpawn() {
         return this.isConquestSpawn;
      }
   }
}
