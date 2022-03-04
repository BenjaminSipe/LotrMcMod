package lotr.common.entity.npc.data;

import java.util.HashMap;
import java.util.Map;
import lotr.common.data.DataUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketBuffer;
import org.apache.commons.lang3.tuple.Pair;

public class NPCEntitySettingsMap {
   private final Map entityTypeSettingsMap;

   public NPCEntitySettingsMap(Map map) {
      this.entityTypeSettingsMap = map;
   }

   public static NPCEntitySettingsMap read(PacketBuffer buf) {
      Map entityTypeSettingsMap = DataUtil.readNewMapFromBuffer(buf, HashMap::new, () -> {
         NPCEntitySettings settings = NPCEntitySettings.read(buf);
         return Pair.of(settings.getEntityType(), settings);
      });
      return new NPCEntitySettingsMap(entityTypeSettingsMap);
   }

   public void write(PacketBuffer buf) {
      DataUtil.writeMapToBuffer(buf, this.entityTypeSettingsMap, (entityType, settings) -> {
         settings.write(buf);
      });
   }

   public NPCEntitySettings getEntityTypeSettings(EntityType type) {
      return (NPCEntitySettings)this.entityTypeSettingsMap.getOrDefault(type, NPCEntitySettings.createEmptyFallbackSettings(type));
   }

   public int getSize() {
      return this.entityTypeSettingsMap.size();
   }
}
