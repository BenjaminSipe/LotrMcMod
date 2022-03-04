package lotr.common.entity.npc.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRLog;
import lotr.common.fac.Faction;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketNPCEntitySettings;
import lotr.common.resources.InstancedJsonReloadListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistries;

public class NPCEntitySettingsManager extends InstancedJsonReloadListener {
   private static final String ENTITY_SETTINGS_FOLDER = "npcs/entity_settings";
   private static final NPCEntitySettingsManager CLIENT_INSTANCE;
   private static final NPCEntitySettingsManager SERVER_INSTANCE;
   private NPCEntitySettingsMap currentLoadedEntitySettings;

   private NPCEntitySettingsManager(LogicalSide side) {
      super("npcs/entity_settings", "NPCEntitySettings", side);
   }

   public static NPCEntitySettingsManager clientInstance() {
      return CLIENT_INSTANCE;
   }

   public static NPCEntitySettingsManager serverInstance() {
      return SERVER_INSTANCE;
   }

   public static NPCEntitySettingsManager sidedInstance(IWorldReader world) {
      return !world.func_201670_d() ? SERVER_INSTANCE : CLIENT_INSTANCE;
   }

   public static NPCEntitySettingsManager sidedInstance(LogicalSide side) {
      return side == LogicalSide.SERVER ? SERVER_INSTANCE : CLIENT_INSTANCE;
   }

   public void loadClientEntitySettingsFromServer(IResourceManager resMgr, NPCEntitySettingsMap settings) {
      this.currentLoadedEntitySettings = settings;
      this.logEntitySettingsLoad("Loaded clientside NPC entity settings from server", this.currentLoadedEntitySettings);
   }

   public NPCEntitySettingsMap getCurrentLoadedEntitySettings() {
      return this.currentLoadedEntitySettings;
   }

   public static NPCEntitySettings getEntityTypeSettings(Entity entity) {
      return sidedInstance((IWorldReader)entity.field_70170_p).getCurrentLoadedEntitySettings().getEntityTypeSettings(entity.func_200600_R());
   }

   public static Faction getEntityTypeFaction(Entity entity) {
      return getEntityTypeSettings(entity).getAssignedFaction(entity.field_70170_p);
   }

   private void logEntitySettingsLoad(String prefix, NPCEntitySettingsMap settings) {
      LOTRLog.info("%s - %d entity type settings", prefix, settings.getSize());
   }

   protected void apply(Map jsons, IResourceManager serverResMgr, IProfiler profiler) {
      this.currentLoadedEntitySettings = this.loadEntitySettingsFromJsons(serverResMgr, jsons);
      this.logEntitySettingsLoad("Loaded serverside NPC entity settings", this.currentLoadedEntitySettings);
   }

   private NPCEntitySettingsMap loadEntitySettingsFromJsons(IResourceManager resMgr, Map entityTypeJsons) {
      Map entityTypeMap = new HashMap();
      Iterator var4 = entityTypeJsons.entrySet().iterator();

      while(var4.hasNext()) {
         Entry entry = (Entry)var4.next();
         ResourceLocation res = (ResourceLocation)entry.getKey();
         JsonObject entityTypeJson = ((JsonElement)entry.getValue()).getAsJsonObject();

         try {
            EntityType entityType = lookupEntityTypeByName(res);
            if (entityType == null) {
               LOTRLog.error("Failed to load NPC entity settings for %s - no such entity type exists in the game!", res);
            } else {
               NPCEntitySettings entityTypeSettings = NPCEntitySettings.read(entityType, entityTypeJson);
               if (entityTypeSettings != null) {
                  entityTypeMap.put(entityType, entityTypeSettings);
               }
            }
         } catch (Exception var10) {
            LOTRLog.warn("Failed to load NPC entity settings for %s from file", res);
            var10.printStackTrace();
         }
      }

      return new NPCEntitySettingsMap(entityTypeMap);
   }

   public void sendEntitySettingsToPlayer(ServerPlayerEntity player) {
      SPacketNPCEntitySettings packet = new SPacketNPCEntitySettings(this.currentLoadedEntitySettings);
      LOTRPacketHandler.sendTo(packet, player);
   }

   public static EntityType lookupEntityTypeByName(ResourceLocation name) {
      return (EntityType)ForgeRegistries.ENTITIES.getValue(name);
   }

   static {
      CLIENT_INSTANCE = new NPCEntitySettingsManager(LogicalSide.CLIENT);
      SERVER_INSTANCE = new NPCEntitySettingsManager(LogicalSide.SERVER);
   }
}
