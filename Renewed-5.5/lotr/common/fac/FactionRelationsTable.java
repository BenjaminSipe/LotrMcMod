package lotr.common.fac;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.common.LOTRLog;
import lotr.common.resources.CombinableMappingsResource;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class FactionRelationsTable extends CombinableMappingsResource {
   public FactionRelationsTable(Map relations, int loadOrder, int numCombinedFrom) {
      super(relations, loadOrder, numCombinedFrom);
   }

   public static FactionRelationsTable read(FactionSettings facSettings, ResourceLocation relationsTableName, JsonObject json) {
      int loadOrder = json.get("load_order").getAsInt();
      Map relations = new HashMap();
      JsonObject relationsJson = json.get("relations").getAsJsonObject();
      Iterator var6 = relationsJson.entrySet().iterator();

      while(var6.hasNext()) {
         Entry entry = (Entry)var6.next();
         String key = (String)entry.getKey();
         JsonElement value = (JsonElement)entry.getValue();

         try {
            List splitKey = (List)Stream.of(key.split(",")).map(String::trim).collect(Collectors.toList());
            if (splitKey.size() == 2) {
               String facName1 = (String)splitKey.get(0);
               String facName2 = (String)splitKey.get(1);
               Faction fac1 = facSettings.getFactionByName(new ResourceLocation(facName1));
               Faction fac2 = facSettings.getFactionByName(new ResourceLocation(facName2));
               if (fac1 == null) {
                  LOTRLog.warn("Faction relations table %s references faction %s - but no such faction exists", relationsTableName, facName1);
               } else if (fac2 == null) {
                  LOTRLog.warn("Faction relations table %s references faction %s - but no such faction exists", relationsTableName, facName2);
               } else if (fac1 == fac2) {
                  LOTRLog.warn("Faction relations table %s cannot declare a faction's relation to itself (%s)", relationsTableName, facName1);
               } else {
                  FactionPair factionPair = FactionPair.of(fac1, fac2);
                  if (relations.containsKey(factionPair)) {
                     LOTRLog.warn("Faction relations table %s contains duplicate key for pair (%s, %s) - relations were already declared as %s", relationsTableName, facName1, facName2, ((FactionRelation)relations.get(factionPair)).codeName);
                  } else {
                     String relationName = value.getAsString();
                     FactionRelation relation = FactionRelation.forName(relationName);
                     if (relation != null) {
                        relations.put(factionPair, relation);
                     } else {
                        LOTRLog.warn("Faction relations table %s references relation %s - but no such relation exists", relationsTableName, relationName);
                     }
                  }
               }
            } else {
               LOTRLog.warn("Couldn't parse a faction relations key in table %s: key = %s - expected two factions separated by a comma", relationsTableName, key);
            }
         } catch (Exception var18) {
            LOTRLog.warn("Couldn't parse a faction relations line in table %s: key = %s, value = %s", relationsTableName, key, value);
            var18.printStackTrace();
         }
      }

      return new FactionRelationsTable(relations, loadOrder, 0);
   }

   public static FactionRelationsTable combine(List relationsTables) {
      return (FactionRelationsTable)combine(relationsTables, FactionRelationsTable::new);
   }

   public static FactionRelationsTable read(FactionSettings facSettings, PacketBuffer buf) {
      Map relations = new HashMap();
      int numRelations = buf.func_150792_a();

      int numCombinedFrom;
      for(numCombinedFrom = 0; numCombinedFrom < numRelations; ++numCombinedFrom) {
         int facId1 = buf.func_150792_a();
         int facId2 = buf.func_150792_a();
         int relationId = buf.func_150792_a();
         Faction fac1 = facSettings.getFactionByID(facId1);
         Faction fac2 = facSettings.getFactionByID(facId2);
         FactionRelation relation = FactionRelation.forNetworkID(relationId);
         if (fac1 == null) {
            LOTRLog.warn("Received a faction relation from server with a nonexistent faction 1 ID (%d)", facId1);
         } else if (fac2 == null) {
            LOTRLog.warn("Received a faction relation from server with a nonexistent faction 2 ID (%d)", facId2);
         } else if (relation == null) {
            LOTRLog.warn("Received a faction relation from server with a nonexistent relation ID (%d)", relationId);
         } else {
            relations.put(FactionPair.of(fac1, fac2), relation);
         }
      }

      numCombinedFrom = buf.func_150792_a();
      return new FactionRelationsTable(relations, 0, numCombinedFrom);
   }

   public void write(PacketBuffer buf) {
      buf.func_150787_b(this.mappings.size());
      this.mappings.forEach((factionPair, relation) -> {
         buf.func_150787_b(factionPair.getFirst().getAssignedId());
         buf.func_150787_b(factionPair.getSecond().getAssignedId());
         buf.func_150787_b(relation.networkID);
      });
      buf.func_150787_b(this.getNumCombinedFrom());
   }

   public FactionRelation getRelation(Faction fac1, Faction fac2) {
      if (!FactionPointers.UNALIGNED.matches(fac1) && !FactionPointers.UNALIGNED.matches(fac2)) {
         if (!FactionPointers.HOSTILE.matches(fac1) && !FactionPointers.HOSTILE.matches(fac2)) {
            if (fac1 == fac2) {
               return FactionRelation.ALLY;
            } else {
               FactionPair key = FactionPair.of(fac1, fac2);
               return this.mappings.containsKey(key) ? (FactionRelation)this.mappings.get(key) : FactionRelation.NEUTRAL;
            }
         } else {
            return FactionRelation.MORTAL_ENEMY;
         }
      } else {
         return FactionRelation.NEUTRAL;
      }
   }
}
