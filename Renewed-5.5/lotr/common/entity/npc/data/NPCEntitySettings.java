package lotr.common.entity.npc.data;

import com.google.gson.JsonObject;
import java.util.Optional;
import java.util.function.BiFunction;
import lotr.common.LOTRLog;
import lotr.common.data.DataUtil;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointers;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldReader;

public class NPCEntitySettings {
   private final EntityType entityType;
   private final ResourceLocation factionName;
   private final float killAlignmentBonus;
   private final ResourceLocation speechbank;

   private NPCEntitySettings(EntityType entityType, ResourceLocation factionName, float killAlignmentBonus, ResourceLocation speechbank) {
      this.entityType = entityType;
      this.factionName = factionName;
      this.killAlignmentBonus = killAlignmentBonus;
      this.speechbank = speechbank;
   }

   public static NPCEntitySettings createEmptyFallbackSettings(EntityType entityType) {
      return new NPCEntitySettings(entityType, FactionPointers.UNALIGNED.getName(), 0.0F, (ResourceLocation)null);
   }

   public static NPCEntitySettings read(EntityType entityType, JsonObject json) {
      ResourceLocation factionName = null;
      if (json.has("faction")) {
         String factionNameJson = json.get("faction").getAsString();
         if (!factionNameJson.isEmpty()) {
            factionName = new ResourceLocation(factionNameJson);
         }
      }

      float killAlignmentBonus = 0.0F;
      if (json.has("kill_bonus")) {
         killAlignmentBonus = json.get("kill_bonus").getAsFloat();
      }

      ResourceLocation speechbank = null;
      if (json.has("speechbank")) {
         speechbank = new ResourceLocation(json.get("speechbank").getAsString());
      }

      return new NPCEntitySettings(entityType, factionName, killAlignmentBonus, speechbank);
   }

   public static NPCEntitySettings read(PacketBuffer buf) {
      ResourceLocation entityTypeName = buf.func_192575_l();
      buf.getClass();
      ResourceLocation factionName = (ResourceLocation)DataUtil.readNullableFromBuffer(buf, buf::func_192575_l);
      float killAlignmentBonus = buf.readFloat();
      buf.getClass();
      ResourceLocation speechbank = (ResourceLocation)DataUtil.readNullableFromBuffer(buf, buf::func_192575_l);
      EntityType entityType = NPCEntitySettingsManager.lookupEntityTypeByName(entityTypeName);
      if (entityType == null) {
         LOTRLog.warn("Received NPC entity type settings from server for a nonexistent entity type %s", entityTypeName);
         return null;
      } else {
         return new NPCEntitySettings(entityType, factionName, killAlignmentBonus, speechbank);
      }
   }

   public void write(PacketBuffer buf) {
      buf.func_192572_a(this.entityType.getRegistryName());
      DataUtil.writeNullableToBuffer(buf, this.factionName, (BiFunction)(PacketBuffer::func_192572_a));
      buf.writeFloat(this.killAlignmentBonus);
      DataUtil.writeNullableToBuffer(buf, this.speechbank, (BiFunction)(PacketBuffer::func_192572_a));
   }

   public EntityType getEntityType() {
      return this.entityType;
   }

   public Faction getAssignedFaction(IWorldReader world) {
      FactionSettings facSettings = FactionSettingsManager.sidedInstance(world).getCurrentLoadedFactions();
      if (this.factionName != null) {
         Faction fac = facSettings.getFactionByName(this.factionName);
         if (fac != null) {
            return fac;
         }

         LOTRLog.error("Entity type %s has assigned faction %s - but no such faction is loaded!", this.entityType.getRegistryName(), this.factionName);
      }

      return facSettings.getFactionByPointer(FactionPointers.UNALIGNED);
   }

   public float getKillAlignmentBonus() {
      return this.killAlignmentBonus;
   }

   public Optional getSpeechbank() {
      return Optional.ofNullable(this.speechbank);
   }
}
